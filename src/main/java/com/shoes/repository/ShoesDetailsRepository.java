package com.shoes.repository;

import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.SDTest;
import com.shoes.service.dto.ShoesDetailsDTO;
import com.shoes.service.dto.ShopShoesDTO;
import com.shoes.service.dto.TestingProjection;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA repository for the ShoesDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesDetailsRepository extends JpaRepository<ShoesDetails, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE ShoesDetails sd SET sd.status = 0 WHERE sd.id = :shoesDetailsId")
    int softDeleteShoesDetailsById(@Param("shoesDetailsId") Long shoesDetailsId);

    List<ShoesDetails> findAllByShoes_IdInAndStatus(List<Long> ids, Integer status);
    ShoesDetails findByIdAndStatus(Long id, Integer status);

    @Query(
        value = "select sd.*\n" +
        "from shoes s join shoes_details sd on sd.shoes_id = s.id where s.id in :ids and sd.status = :status group by s.id,sd.color_id",
        nativeQuery = true
    )
    List<ShoesDetails> findShoesDetailsGroupByColor(@Param("ids") List<Long> ids, @Param("status") Integer status);

    @Query(
        nativeQuery = true,
        value = "SELECT \n" +
        "CONCAT(br.name, ' ', sh.name) as name,  " +
        "sd.* ,\n" +
        "iu.path\n" +
        "FROM\n" +
        "    `shoes-store`.shoes_details sd\n" +
        "JOIN (\n" +
        "    SELECT\n" +
        "        shoes_id,\n" +
        "        brand_id,\n" +
        "        MIN(price) AS lowest_price\n" +
        "    FROM\n" +
        "        `shoes-store`.shoes_details\n" +
        "    GROUP BY\n" +
        "        shoes_id, brand_id\n" +
        ") min_prices ON sd.shoes_id = min_prices.shoes_id\n" +
        "    AND sd.brand_id = min_prices.brand_id\n" +
        "    AND sd.price = min_prices.lowest_price\n" +
        "    AND sd.size_id in (:idSizes)" +
        "JOIN\n" +
        "    `shoes-store`.shoes_file_upload_mapping sfum ON sd.id = sfum.shoes_details_id\n" +
        "JOIN\n" +
        "    `shoes-store`.file_upload iu ON sfum.file_upload_id = iu.id\n " +
        "JOIN\n" +
        "    `shoes-store`.shoes sh ON sd.shoes_id = sh.id and sh.status = 1\n" +
        "JOIN\n" +
        "    `shoes-store`.brand br ON sd.brand_id = br.id  " +
        "AND iu.status = 1 " +
        "WHERE sd.status = 1 " +
        "GROUP BY shoes_id, brand_id\n"
    )
    List<ShopShoesDTO> findDistinctByShoesAndBrandOrderBySellPriceDesc(@Param("idSizes") List<Long> idSizes);

    @Query(value = "select * from shoes_details order by created_date desc limit 10", nativeQuery = true)
    List<ShoesDetails> getNewShoesDetail();
}

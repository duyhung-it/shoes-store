package com.shoes.repository;

import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.ShoesDetailsDTO;
import com.shoes.service.dto.ShopShoesDTO;
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
        "CONCAT(br.name, ' ', sh.name) as name, br.name as brandName , " +
        "sd.* ,\n" +
        "iu.path, \n" +
        "GROUP_CONCAT(distinct sz.id) as sizes,\n" +
        "GROUP_CONCAT(distinct cl.id) as colors,\n " +
        "GROUP_CONCAT(distinct cl.name) as color_names ," +
        "GROUP_CONCAT(distinct iu.path) as paths " +
        "FROM\n" +
        "    `shoes-store`.shoes_details sd\n" +
        "JOIN (\n" +
        "    SELECT\n" +
        "        shoes_id,\n" +
        "        brand_id,\n" +
        "        MIN(price) AS lowest_price\n" +
        "    FROM\n" +
        "        `shoes-store`.shoes_details \n" +
        "WHERE status = 1 " +
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
        "AND iu.status = 1 " +
        "JOIN\n" +
        "    `shoes-store`.shoes sh ON sd.shoes_id = sh.id and sh.status = 1\n" +
        "JOIN\n " +
        "    `shoes-store`.brand br ON sd.brand_id = br.id\n " +
        " JOIN\n" +
        " `shoes-store`.size sz ON sd.size_id = sz.id\n" +
        "JOIN\n" +
        "`shoes-store`.color cl ON sd.color_id = cl.id\n " +
        "WHERE sd.status = 1 " +
        "GROUP BY shoes_id, brand_id\n"
    )
    List<ShopShoesDTO> findDistinctByShoesAndBrandOrderBySellPriceDesc(@Param("idSizes") List<Long> idSizes);

    @Query(
        nativeQuery = true,
        value = "    \n" +
        "SELECT\n" +
        "    size_ids,\n" +
        "    size_names,\n" +
        "    color_ids,\n" +
        "    color_names,\n" +
        "    sd.*,\n" +
        "    CONCAT(sh.name, ' ', br.name) as name,\n" +
        "    iu.path,\n" +
        "    GROUP_CONCAT(iu.path) as paths ,\n" +
        "    (SELECT CAST(COALESCE(avg(fb.rate), 5) AS SIGNED)\n" +
        "FROM feed_back fb\n" +
        "JOIN shoes_details ad ON fb.shoes_id = ad.id\n" +
        "JOIN jhi_user u ON fb.user_id = u.id\n" +
        "WHERE ad.shoes_id = :shid AND sd.brand_id = :brid AND fb.status = 1 ) as rating\n" +
        "FROM\n" +
        "    (\n" +
        "        SELECT\n" +
        "            GROUP_CONCAT(DISTINCT sz.id) as size_ids,\n" +
        "            GROUP_CONCAT(DISTINCT sz.name) as size_names\n" +
        "        FROM\n" +
        "            `shoes-store`.shoes_details sd\n" +
        "            JOIN `shoes-store`.size sz ON sd.size_id = sz.id\n" +
        "            JOIN `shoes-store`.color cl ON sd.color_id = cl.id and cl.id = :clid \n" +
        "        WHERE\n" +
        "            sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1\n" +
        "    ) size_subquery,\n" +
        "    (\n" +
        "        SELECT\n" +
        "            GROUP_CONCAT(DISTINCT cl.id) as color_ids,\n" +
        "            GROUP_CONCAT(DISTINCT cl.name) as color_names\n" +
        "        FROM\n" +
        "            `shoes-store`.shoes_details sd\n" +
        "            JOIN `shoes-store`.color cl ON sd.color_id = cl.id\n" +
        "        WHERE\n" +
        "            sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1\n" +
        "    ) color_subquery\n" +
        "JOIN `shoes-store`.shoes_details sd ON 1 = 1\n" +
        "JOIN `shoes-store`.shoes_file_upload_mapping sfum ON sd.id = sfum.shoes_details_id\n" +
        "JOIN `shoes-store`.file_upload iu ON sfum.file_upload_id = iu.id\n" +
        "JOIN `shoes-store`.shoes sh ON sd.shoes_id = sh.id and sh.status = 1\n" +
        "JOIN `shoes-store`.brand br ON sd.brand_id = br.id \n" +
        "JOIN `shoes-store`.size sz ON sd.size_id = sz.id and (:siid IS NULL OR sz.id = :siid) \n" +
        "JOIN `shoes-store`.color cl ON sd.color_id = cl.id and cl.id = :clid \n" +
        "WHERE\n" +
        "    sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1;\n"
    )
    ShopShoesDTO findDistinctByShoesAndBrandOrderBySellPriceDescOne(
        @Param("shid") Integer shid,
        @Param("brid") Integer brid,
        @Param("siid") Integer siid,
        @Param("clid") Integer clid
    );

    @Query(
        value = "SELECT b.*,p.path\n" +
        "FROM file_upload p\n" +
        "join (\n" +
        "    WITH shoes_file_upload_mapping AS (\n" +
        "    SELECT \n" +
        "        *,\n" +
        "        ROW_NUMBER() OVER(PARTITION BY shoes_details_id  ORDER BY id desc) AS rn\n" +
        "    FROM \n" +
        "        shoes_file_upload_mapping\n" +
        "        )\n" +
        "        SELECT * \n" +
        "FROM shoes_file_upload_mapping\n" +
        "WHERE rn = 1\n" +
        ") s on p.id = s.file_upload_id\n" +
        "JOIN (\n" +
        "    WITH shoes_details AS (\n" +
        "    SELECT \n" +
        "        *,\n" +
        "        ROW_NUMBER() OVER(PARTITION BY shoes_id, brand_id ORDER BY id desc) AS rn\n" +
        "    FROM \n" +
        "        shoes_details\n" +
        ")\n" +
        "SELECT * \n" +
        "FROM shoes_details\n" +
        "WHERE rn = 1\n" +
        ") b ON s.shoes_details_id = b.id\n" +
        "order by created_date desc limit 10",
        nativeQuery = true
    )
    List<ShoesDetails> getNewShoesDetail();

    @Query(
        value = "select sd.*  from order_details od \n" +
        "join jhi_order jo on jo.id = od.order_id\n" +
        "join shoes_details sd on sd.id = od.shoes_details_id \n" +
        "where od.status <> -1\n" +
        "group by od.shoes_details_id \n" +
        "order by sum(od.quantity) desc limit 6",
        nativeQuery = true
    )
    List<ShoesDetails> getTopBestSelling();
}

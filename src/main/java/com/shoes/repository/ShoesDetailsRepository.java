package com.shoes.repository;

import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.ShoesDetailDTOCustom;
import com.shoes.service.dto.ShoesDetailsDTO;
import com.shoes.service.dto.ShoesVariant;
import com.shoes.service.dto.ShopShoesDTO;
import java.math.BigDecimal;
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
        "CONCAT(br.name, ' ', sh.name) as name, br.name as brandName ,sh.code as shoesCode, " +
        "sd.* ,\n" +
        "iu.path, \n" +
        "GROUP_CONCAT(distinct sz.id order by sz.id) as sizes,\n" +
        "GROUP_CONCAT(distinct cl.id order by cl.id) as colors,\n " +
        "GROUP_CONCAT(distinct cl.name order by cl.id) as color_names ," +
        "GROUP_CONCAT(distinct iu.path) as paths ," +
        "GROUP_CONCAT(distinct d.name) as discount_name ," +
        "GROUP_CONCAT(distinct d.discount_method) as discount_method ,  " +
        "dsd.discount_amount as discount_amount , " +
        "CAST(COALESCE(avg(fb.rate), 0) AS SIGNED ) as rating  " +
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
        "LEFT JOIN\n" +
        "    `shoes-store`.shoes_file_upload_mapping sfum ON sd.id = sfum.shoes_details_id\n" +
        "LEFT JOIN\n" +
        "    `shoes-store`.file_upload iu ON sfum.file_upload_id = iu.id\n " +
        "AND iu.status = 1 " +
        "JOIN\n " +
        "    `shoes-store`.brand br ON sd.brand_id = br.id\n " +
        "JOIN\n" +
        "    `shoes-store`.shoes sh ON sd.shoes_id = sh.id and sh.status = 1\n" +
        "LEFT JOIN `shoes-store`.discount_shoes_details dsd ON dsd.shoes_details_id = sd.shoes_id\n" +
        "    AND dsd.status = 1 AND dsd.brand_id = sd.brand_id\n" +
        "LEFT JOIN `shoes-store`.discount d ON dsd.discount_id = d.id\n" +
        "    AND d.start_date <= NOW() AND d.end_date >= NOW() AND d.status = 1 " +
        "JOIN\n" +
        " `shoes-store`.size sz ON sd.size_id = sz.id\n" +
        "JOIN\n" +
        "`shoes-store`.color cl ON sd.color_id = cl.id\n " +
        "LEFT JOIN `shoes-store`.feed_back fb ON fb.shoes_id in (select id from shoes_details where brand_id = sd.brand_id and shoes_id = sd.shoes_id)  and fb.status = 1 " +
        "WHERE sd.status = 1 AND (sd.brand_id = :idBrands OR :idBrands IS NULL) and sd.price between :startPrice and :endPrice " +
        "GROUP BY shoes_id, brand_id\n"
    )
    List<ShopShoesDTO> findDistinctByShoesAndBrandOrderBySellPriceDesc(
        @Param("idSizes") List<Long> idSizes,
        @Param("idBrands") Long brandId,
        @Param("startPrice") BigDecimal startPrice,
        @Param("endPrice") BigDecimal endPrice
    );

    @Query(
        nativeQuery = true,
        value = "    \n" +
        "SELECT\n" +
        "  size_ids,\n" +
        "  size_names,\n" +
        "  color_ids,\n" +
        "  color_names,\n " +
        "  sz.name as size_name,\n" +
        "  cl.name as color_name ," +
        "  sd.*,\n" +
        "  CONCAT(br.name, ' ', sh.name) as name,\n" +
        "  iu.path,\n" +
        "  GROUP_CONCAT(iu.path) as paths ,\n " +
        "GROUP_CONCAT(distinct d.name) as discount_name ," +
        "GROUP_CONCAT(distinct d.discount_method) as discount_method ,  " +
        "GROUP_CONCAT(distinct dsd.discount_amount) as discount_amount ,  " +
        "GROUP_CONCAT(distinct dsd.discount_amount) as discount_amount_3_4 ,  " +
        " (SELECT CAST(COALESCE(avg(fb.rate), 0) AS SIGNED)\n" +
        "FROM feed_back fb\n" +
        "JOIN shoes_details ad ON fb.shoes_id in (select id from shoes_details where brand_id = sd.brand_id and shoes_id = sd.shoes_id)  AND fb.status = 1 ) as rating\n" +
        "FROM\n" +
        "    (\n" +
        "        SELECT\n" +
        "            GROUP_CONCAT(DISTINCT sz.id order by sz.id) as size_ids,\n" +
        "            GROUP_CONCAT(DISTINCT sz.name order by sz.id) as size_names\n" +
        "        FROM\n" +
        "            `shoes-store`.shoes_details sd\n" +
        "            JOIN `shoes-store`.size sz ON sd.size_id = sz.id\n" +
        "            JOIN `shoes-store`.color cl ON sd.color_id = cl.id and cl.id = :clid \n" +
        "        WHERE\n" +
        "            sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1\n" +
        "    ) size_subquery,\n" +
        "    (\n" +
        "        SELECT\n" +
        "            GROUP_CONCAT(DISTINCT cl.id order by cl.id) as color_ids,\n" +
        "            GROUP_CONCAT(DISTINCT cl.name order by cl.id) as color_names\n" +
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
        "LEFT JOIN `shoes-store`.discount_shoes_details dsd\n" +
        "ON dsd.shoes_details_id = sd.shoes_id and dsd.status = 1 and dsd.brand_id = sd.brand_id\n" +
        "LEFT JOIN `shoes-store`.discount d \n" +
        "ON dsd.discount_id = d.id and d.start_date <= now() and d.end_date >= now() and d.status = 1 " +
        "WHERE  " +
        "sd.brand_id = :brid and sd.shoes_id = :shid and sd.status = 1;\n"
    )
    ShopShoesDTO findDistinctByShoesAndBrandOrderBySellPriceDescOne(
        @Param("shid") Integer shid,
        @Param("brid") Integer brid,
        @Param("siid") Integer siid,
        @Param("clid") Integer clid
    );

    @Query(
        value = "select fu.path,sd.price,s.name,s.id as idsh,sz.id as idsz,c.id as idc,b.id as idb,\n" +
        "    coalesce(max(discount_method),min(discount_method)) AS discountmethod,\n" +
        "    max(\n" +
        "    case\n" +
        "    when discount_method is not null && (discount_method = 3 || discount_method = 4)  then dsd.discount_amount\n" +
        "    else null\n" +
        "    end \n" +
        "    ) as discountamount_3_4,\n" +
        "    coalesce(max(d.discount_amount),min(d.discount_amount)) AS discountamount_1_2\n" +
        "from (\n" +
        "    WITH shoes_details AS (\n" +
        "        SELECT\n" +
        "            *,\n" +
        "            ROW_NUMBER() OVER(PARTITION BY shoes_id, brand_id ORDER BY id DESC) AS rn\n" +
        "        FROM\n" +
        "            shoes_details\n" +
        "    )\n" +
        "    SELECT *\n" +
        "    FROM shoes_details\n" +
        "    WHERE rn = 1\n" +
        ") sd\n" +
        "join shoes s on  sd.shoes_id = s.id\n" +
        "join size sz on sd.size_id = sz.id\n" +
        "join color c on sd.color_id = c.id\n" +
        "join brand b on sd.brand_id = b.id\n" +
        "LEFT JOIN discount_shoes_details AS dsd \n" +
        "ON sd.shoes_id  = dsd.shoes_details_id and dsd.status = 1 and dsd.brand_id = b.id\n" +
        "LEFT JOIN discount AS d \n" +
        "ON dsd.discount_id = d.id and d.start_date <= now() and d.end_date >= now() and d.status = 1\n" +
        "join (\n" +
        "\twith shoes_file_upload_mapping as(\n" +
        "\t\tselect * ,row_number() over(partition by shoes_details_id order by id) as rn\n" +
        "        from shoes_file_upload_mapping\n" +
        "    )\n" +
        "    select * from shoes_file_upload_mapping\n" +
        "    where rn = 1\n" +
        ") sfum on sd.id = sfum.shoes_details_id\n" +
        "join file_upload fu on sfum.file_upload_id = fu.id \n" +
        "GROUP BY sd.shoes_id,sd.brand_id\n" +
        "ORDER BY sd.created_date DESC\n" +
        "LIMIT 10;",
        nativeQuery = true
    )
    List<ShoesDetailDTOCustom> getNewShoesDetail();

    @Query(
        value = "SELECT fu.path, sd.price, s.name, s.id AS idsh, sz.id AS idsz, c.id AS idc, b.id AS idb,\n" +
        "\t\td.discount_method as discountmethod,dsd.discount_amount as discountamount_3_4,\n" +
        "        d.discount_amount as discountamount_1_2\n" +
        "FROM (\n" +
        "    SELECT *\n" +
        "    FROM (\n" +
        "        SELECT *,\n" +
        "               ROW_NUMBER() OVER(PARTITION BY shoes_id, brand_id ORDER BY id DESC) AS rn\n" +
        "        FROM shoes_details\n" +
        "    ) AS shoes_details\n" +
        "    WHERE rn = 1\n" +
        ") AS sd\n" +
        "JOIN shoes AS s ON sd.shoes_id = s.id\n" +
        "JOIN size AS sz ON sd.size_id = sz.id\n" +
        "JOIN color AS c ON sd.color_id = c.id\n" +
        "JOIN brand AS b ON sd.brand_id = b.id\n" +
        "JOIN discount_shoes_details AS dsd \n" +
        "ON sd.shoes_id  = dsd.shoes_details_id and dsd.status = 1 and dsd.brand_id = b.id\n" +
        "JOIN discount AS d \n" +
        "ON dsd.discount_id = d.id and d.start_date <= now() and d.end_date >= now() and d.status = 1\n" +
        "JOIN (\n" +
        "    SELECT *\n" +
        "    FROM (\n" +
        "        SELECT *,\n" +
        "               ROW_NUMBER() OVER(PARTITION BY shoes_details_id ORDER BY id) AS rn\n" +
        "        FROM shoes_file_upload_mapping\n" +
        "    ) AS shoes_file_upload_mapping\n" +
        "    WHERE rn = 1\n" +
        ") AS sfum ON sd.id = sfum.shoes_details_id\n" +
        "JOIN file_upload AS fu ON sfum.file_upload_id = fu.id\n" +
        "ORDER BY dsd.created_date DESC\n" +
        "LIMIT 10;\n",
        nativeQuery = true
    )
    List<ShoesDetailDTOCustom> getNewDiscountShoesDetail();

    @Query(
        value = "SELECT \n" +
        "    fu.path,\n" +
        "    sd.price,\n" +
        "    s.name,\n" +
        "    sd.id AS shoesdetailid,\n" +
        "    s.id AS idsh,\n" +
        "    sz.id AS idsz,\n" +
        "    c.id AS idc,\n" +
        "    b.id AS idb,\n" +
        "    sum(od.quantity) as totalQuantity\n" +
        "FROM \n" +
        "    shoes_details sd\n" +
        "JOIN \n" +
        "    shoes s ON sd.shoes_id = s.id\n" +
        "JOIN \n" +
        "    size sz ON sd.size_id = sz.id\n" +
        "JOIN \n" +
        "    color c ON sd.color_id = c.id\n" +
        "JOIN \n" +
        "    brand b ON sd.brand_id = b.id\n" +
        "JOIN order_details od on od.shoes_details_id = sd.id\n" +
        "JOIN (\n" +
        "    SELECT \n" +
        "        sfum.*,\n" +
        "        ROW_NUMBER() OVER (PARTITION BY shoes_details_id ORDER BY id) AS rn\n" +
        "    FROM \n" +
        "        shoes_file_upload_mapping sfum\n" +
        ") sfum ON sd.id = sfum.shoes_details_id\n" +
        "JOIN \n" +
        "    file_upload fu ON sfum.file_upload_id = fu.id \n" +
        "WHERE rn = 1 \n" +
        "GROUP BY sd.id\n" +
        "order by totalQuantity desc\n" +
        "limit 10\n",
        nativeQuery = true
    )
    List<ShoesDetailDTOCustom> getBestSeller();

    @Query(
        value = "SELECT  \n" +
        "    CONCAT(br.name, ' ', sh.name) as name, \n" +
        "    sd.*, \n" +
        "    iu.path as path,\n" +
        "    cl.name as color_name,\n" +
        "    sz.name as size_name\n" +
        "FROM \n" +
        "    `shoes-store`.shoes_details sd\n" +
        "LEFT JOIN\n" +
        "    `shoes-store`.shoes_file_upload_mapping sfum ON sd.id = sfum.shoes_details_id\n" +
        "LEFT JOIN\n" +
        "    `shoes-store`.file_upload iu ON sfum.file_upload_id = iu.id\n" +
        "    AND iu.status = 1 \n" +
        "JOIN\n" +
        "    `shoes-store`.brand br ON sd.brand_id = br.id \n" +
        "JOIN\n" +
        "    `shoes-store`.shoes sh ON sd.shoes_id = sh.id and sh.status = 1\n" +
        "JOIN\n" +
        "    `shoes-store`.size sz ON sd.size_id = sz.id\n" +
        "JOIN\n" +
        "    `shoes-store`.color cl ON sd.color_id = cl.id \n" +
        "group by sd.id;",
        nativeQuery = true
    )
    List<ShoesVariant> getAllVariant();

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

    @Query(
        value = "SELECT\n" +
        "CONCAT(br.name, ' ', sh.name) as name, br.name as brandName , \n" +
        "sd.*,\n" +
        "iu.path," +
        "d.name as discount_name ,\n" +
        "case \n" +
        "\twhen d.discount_method = 1 or d.discount_method = 2 then d.discount_amount\n" +
        "\twhen d.discount_method = 3 or d.discount_method = 4 then dsd.discount_amount\n" +
        "end as discount_amount,\n" +
        "d.discount_method\n" +
        "FROM\n" +
        "`shoes-store`.shoes_details sd\n" +
        "JOIN \n" +
        "    `shoes-store`.brand br ON sd.brand_id = br.id \n" +
        "join `shoes-store`.discount_shoes_details dsd\n" +
        "on dsd.shoes_details_id = sd.shoes_id and dsd.status = 1 and dsd.brand_id = br.id\n" +
        "join `shoes-store`.discount d \n" +
        "on dsd.discount_id = d.id and d.start_date <= now() and d.end_date >= now() and d.status = 1\n" +
        "JOIN\n" +
        "    `shoes-store`.shoes_file_upload_mapping sfum ON sd.id = sfum.shoes_details_id\n" +
        "JOIN\n" +
        "    `shoes-store`.file_upload iu ON sfum.file_upload_id = iu.id \n" +
        "AND iu.status = 1 \n" +
        "JOIN\n" +
        "    `shoes-store`.shoes sh ON sd.shoes_id = sh.id and sh.status = 1\n" +
        "WHERE sd.status = 1 \n" +
        "group by sh.id ,br.id",
        nativeQuery = true
    )
    List<ShopShoesDTO> getShoesDiscount();

    @Query(
        value = "    SELECT\n" +
        "   sd.*     " +
        "    FROM\n" +
        "        `shoes-store`.shoes_details sd \n" +
        "WHERE status = 1 and shoes_id = :shoesId " +
        "    GROUP BY\n" +
        "        shoes_id, brand_id order by MIN(price) limit 1 \n",
        nativeQuery = true
    )
    ShoesDetails getMinPrice(@Param("shoesId") Long shoesId);
}

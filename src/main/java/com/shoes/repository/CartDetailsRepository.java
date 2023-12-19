package com.shoes.repository;

import com.shoes.domain.Cart;
import com.shoes.domain.CartDetails;
import com.shoes.service.dto.CartDetailDTO;
import com.shoes.service.dto.CartDetailsDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CartDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {
    List<CartDetails> findCartDetailsByCart(Cart cart);

    @Query(
        value = "SELECT \n" +
        "    cd.*, fu.path, sd.price, s.name AS nameshoes,\n" +
        "    sz.name AS namesize, c.name AS namecolor, sd.id AS shoesdetailid, sd.quantity AS quantityShoesDetail,\n" +
        "    s.id AS idsh, sz.id AS idsz, c.id AS idc, b.id AS idb,\n" +
        "    coalesce(max(discount_method),min(discount_method)) AS discountmethod,\n" +
        "    max(\n" +
        "    case\n" +
        "    when discount_method is not null then dsd.discount_amount\n" +
        "    else null\n" +
        "    end \n" +
        "    ) as discountamount_3_4,\n" +
        "    coalesce(max(d.discount_amount),min(d.discount_amount)) AS discountamount_1_2\n" +
        "FROM \n" +
        "    cart_details cd\n" +
        "JOIN \n" +
        "    shoes_details sd ON cd.shoes_details_id = sd.id\n" +
        "JOIN \n" +
        "    shoes s ON sd.shoes_id = s.id\n" +
        "JOIN \n" +
        "    size sz ON sd.size_id = sz.id\n" +
        "JOIN \n" +
        "    color c ON sd.color_id = c.id\n" +
        "JOIN \n" +
        "    brand b ON sd.brand_id = b.id\n" +
        "LEFT JOIN discount_shoes_details\n" +
        "    dsd ON dsd.shoes_details_id = sd.shoes_id \n" +
        "    AND dsd.status = 1 \n" +
        "    AND dsd.brand_id = b.id\n" +
        "LEFT JOIN \n" +
        "    discount d ON dsd.discount_id = d.id \n" +
        "    AND d.start_date <= NOW() \n" +
        "    AND d.end_date >= NOW() \n" +
        "    AND d.status = 1\n" +
        "JOIN (\n" +
        "    WITH shoes_file_upload_mapping AS (\n" +
        "        SELECT \n" +
        "            *, ROW_NUMBER() OVER (PARTITION BY shoes_details_id ORDER BY id) AS rn\n" +
        "        FROM \n" +
        "            shoes_file_upload_mapping\n" +
        "    )\n" +
        "    SELECT * FROM shoes_file_upload_mapping\n" +
        ") sfum ON sd.id = sfum.shoes_details_id\n" +
        "JOIN \n" +
        "    file_upload fu ON sfum.file_upload_id = fu.id \n" +
        "WHERE \n" +
        "    cd.cart_id = 1\n" +
        "    AND rn = 1\n" +
        "GROUP BY sd.id\n",
        nativeQuery = true
    )
    List<CartDetailDTO> findCartDetailsByCart_Id(@Param("idCart") Long idCart);

    Long countByCart(Cart cart);
    CartDetails findByIdAndStatus(long id, Integer status);
}

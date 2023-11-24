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

    @Query(value = "select cd.*,fu.path,sd.price,s.name as nameshoes," +
        "sz.name as namesize,c.name as namecolor,sd.id as shoesdetailid,sd.quantity as quantityShoesDetail\n" +
        "from cart_details cd\n" +
        "join shoes_details sd on cd.shoes_details_id = sd.id\n" +
        "join shoes s on  sd.shoes_id = s.id\n" +
        "join size sz on sd.size_id = sz.id\n" +
        "join color c on sd.color_id = c.id\n" +
        "join (\n" +
        "\twith shoes_file_upload_mapping as(\n" +
        "\t\tselect * ,row_number() over(partition by shoes_details_id order by id) as rn\n" +
        "        from shoes_file_upload_mapping\n" +
        "    )\n" +
        "    select * from shoes_file_upload_mapping\n" +
        ") sfum on sd.id = sfum.shoes_details_id\n" +
        "join file_upload fu on sfum.file_upload_id = fu.id \n" +
        "where cd.cart_id = :idCart && rn =1",nativeQuery = true)
    List<CartDetailDTO> findCartDetailsByCart_Id(@Param("idCart") Long idCart);

    Long countByCart(Cart cart);
}

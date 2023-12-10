package com.shoes.repository;

import com.shoes.domain.OrderDetails;
import com.shoes.service.dto.OrderDetailDTOInterface;
import com.shoes.service.dto.OrderDetailsDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findAllByOrder_IdAndStatus(Long id, Integer active);
    List<OrderDetails> findAllByOrder_IdInAndStatus(List<Long> orderId, Integer active);

    @Query(
        value = "SELECT cd.*,fu.path,sd.price as priceShoes,s.name as nameShoes," +
        "sz.name as nameSize,c.name as nameColor, b.name as nameBrand,sd.id as shoesdetailid,sd.quantity as quantityShoesDetail," +
        "s.id as idsh,sz.id as idsz,c.id as idc,b.id as idb\n" +
        "FROM \n" +
        "    order_details cd\n" +
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
        "JOIN (\n" +
        "    SELECT \n" +
        "        *, \n" +
        "        ROW_NUMBER() OVER (PARTITION BY shoes_details_id ORDER BY id) AS rn\n" +
        "    FROM \n" +
        "        shoes_file_upload_mapping\n" +
        ") sfum ON sd.id = sfum.shoes_details_id\n" +
        "JOIN \n" +
        "    file_upload fu ON sfum.file_upload_id = fu.id\n" +
        "WHERE \n" +
        "   cd.order_id = :id AND rn = 1\n", // Change && to AND
        nativeQuery = true
    )
    List<OrderDetailDTOInterface> findAllByOrder_Id(@Param("id") Long id);
}

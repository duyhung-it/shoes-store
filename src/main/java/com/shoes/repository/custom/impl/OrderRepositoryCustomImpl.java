package com.shoes.repository.custom.impl;

import com.shoes.repository.custom.OrderRepositoryCustom;
import com.shoes.service.dto.OrderDTO;
import com.shoes.service.dto.OrderSearchReqDTO;
import com.shoes.service.dto.OrderSearchResDTO;
import com.shoes.service.dto.OrderStatusDTO;
import com.shoes.util.DataUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderSearchResDTO> search(OrderSearchReqDTO orderSearchReqDTO) {
        Query query = this.buildQuery(orderSearchReqDTO);
        List<OrderSearchResDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<OrderStatusDTO> getQuantityOrders() {
        Query query = this.buildQueryGetQuantity();
        List<OrderStatusDTO> list = query.getResultList();
        return list;
    }

    public Query buildQuery(OrderSearchReqDTO orderSearchReqDTO) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder(
            "select o.id,o.code,o.phone ,o.received_by as receivedBy, o.last_modified_by as lastModifiedBy , o.created_date as createdDate , o.total_price as totalPrice ,o.owner_id as idCustomer ,concat(ju.first_name , ' ' ,ju.last_name) as customer, o.status  from `shoes-store`.jhi_order o   \n" +
            "left join `shoes-store`.jhi_user ju on ju.id = o.owner_id \n" +
            " where 1 = 1"
        );
        if (StringUtils.isNotBlank(orderSearchReqDTO.getSearchText())) {
            query.append(" and (o.code like :searchText ) ");
            params.put("searchText", DataUtils.makeLikeStr(DataUtils.likeSpecialToStr(orderSearchReqDTO.getSearchText())));
        }
        if (Objects.nonNull(orderSearchReqDTO.getStatus())) {
            query.append(" and o.status = :status");
            params.put("status", orderSearchReqDTO.getStatus());
        }
        query.append(" order by o.last_modified_date desc");
        Query query1 = entityManager.createNativeQuery(query.toString(), "orders_result");
        params.forEach(query1::setParameter);
        return query1;
    }

    public Query buildQueryGetQuantity() {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder(
            " select jo.status ,count(*) as quantity from `shoes-store`.jhi_order jo\n" + " group by jo.status "
        );
        Query query1 = entityManager.createNativeQuery(query.toString(), "orders_quantity_result");
        params.forEach(query1::setParameter);
        return query1;
    }
}

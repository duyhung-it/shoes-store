package com.shoes.repository.custom.impl;

import com.shoes.repository.custom.OrderReturnCustomRepository;
import com.shoes.service.dto.OrderReturnSearchResDTO;
import com.shoes.service.dto.OrderSearchReqDTO;
import com.shoes.service.dto.OrderSearchResDTO;
import com.shoes.util.DataUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;

public class OrderReturnCustomRepositoryImpl implements OrderReturnCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderReturnSearchResDTO> search(OrderSearchReqDTO orderSearchReqDTO) {
        Query query = this.buildQuery(orderSearchReqDTO);
        List<OrderReturnSearchResDTO> list = query.getResultList();
        return list;
    }

    public Query buildQuery(OrderSearchReqDTO orderSearchReqDTO) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder(
            "select or2.id,or2.code,ju.login,jo.phone,or2.status ,or2.last_modified_by,or2.created_date  from order_return or2 \n" +
            "join jhi_order jo on jo.id = or2.order_id \n" +
            "left join jhi_user ju on ju.id = jo.owner_id" +
            " where 1 = 1"
        );
        if (StringUtils.isNotBlank(orderSearchReqDTO.getSearchText())) {
            query.append(" and (or2.code like :searchText ) ");
            params.put("searchText", DataUtils.makeLikeStr(DataUtils.likeSpecialToStr(orderSearchReqDTO.getSearchText())));
        }
        if (Objects.nonNull(orderSearchReqDTO.getStatus())) {
            query.append(" and or2.status = :status");
            params.put("status", orderSearchReqDTO.getStatus());
        }
        query.append(" order by or2.last_modified_date desc");
        Query query1 = entityManager.createNativeQuery(query.toString(), "orderReturnResult");
        params.forEach(query1::setParameter);
        return query1;
    }
}

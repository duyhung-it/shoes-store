package com.shoes.repository.custom.impl;

import com.shoes.repository.custom.DiscountRepositoryCustom;
import com.shoes.service.dto.DiscountSearchDTO;
import com.shoes.util.DataUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class DiscountRepositoryCustomImpl implements DiscountRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DiscountSearchDTO> search(String searchText) {
        Query query = buildQuery(searchText);
        return query.getResultList();
    }

    Query buildQuery(String searchText) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder(
            "select d.id, d.code, d.name,d.discount_method as discountMethod,\n" +
            "    case\n" +
            "    \twhen d.discount_method = 1 then 'Giảm tiền cho tất cả giày'\n" +
            "    \twhen d.discount_method = 2 then 'Giảm % cho tất cả giày'\n" +
            "    \twhen d.discount_method = 3 then 'Giảm tiền cho từng giày'\n" +
            "    \twhen d.discount_method = 4 then 'Giảm % cho từng giày'\n" +
            "    end as discountMethodName,\n" +
            "    case\n" +
            "    \twhen d.discount_status = 0 then 'Chưa áp dụng'\n" +
            "    \twhen d.discount_status = 1 then 'Đang áp dụng'\n" +
            "    \twhen d.discount_status = 2 then 'Hết hạn'\n" +
            "    end as status,\n" +
            "    d.start_date as startDate, d.end_date as endDate,\n" +
            "    d.last_modified_date as lastModifiedDate,d.last_modified_by as lastModifiedBy\n" +
            "    from discount d \n" +
            "    where d.status = 1"
        );
        if (!DataUtils.isNullOrEmpty(searchText)) {
            sql.append(" and (d.code like :searchText or d.name like :searchText) ");
            params.put("searchText", DataUtils.makeLikeStr(DataUtils.likeSpecialToStr(searchText)));
        }
        Query query = entityManager.createNativeQuery(sql.toString(), "discount_search_result");
        params.forEach(query::setParameter);
        return query;
    }
}

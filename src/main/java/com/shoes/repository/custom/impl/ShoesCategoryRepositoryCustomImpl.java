package com.shoes.repository.custom.impl;

import com.shoes.repository.custom.ShoesCategoryRepositoryCustom;
import com.shoes.service.dto.ShoesCategorySearchResDTO;
import com.shoes.util.DataUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class ShoesCategoryRepositoryCustomImpl implements ShoesCategoryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ShoesCategorySearchResDTO> search(String searchText, Pageable pageable) {
        Query query = buildQuery(searchText, pageable);
        List<ShoesCategorySearchResDTO> list = query.getResultList();
        return new PageImpl<>(list, pageable, list.size());
    }

    protected Query buildQuery(String searchText, Pageable pageable) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder(
            "select sc.id,sc.code,sc.name,sc.status,sc.last_modified_by,sc.last_modified_date from shoes_category sc where sc.status <> -1"
        );
        if (StringUtils.isNotBlank(searchText)) {
            sql.append(" and (sc.code like :search or sc.name like :search) ");
            params.put("search", DataUtils.makeLikeStr(DataUtils.likeSpecialToStr(searchText)));
        }

        if (pageable != null) {
            appendSorting(fieldSorts(), pageable, sql);
        }
        Query query = entityManager.createNativeQuery(sql.toString(), "shoes_category_result");

        if (pageable != null) {
            query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            query.setMaxResults(pageable.getPageSize());
        }

        params.forEach(query::setParameter);
        return query;
    }

    private void appendSorting(Map<String, String> fieldSorts, Pageable pageable, StringBuilder sql) {
        Sort sort = pageable.getSort();
        if (!sort.isEmpty()) {
            sql.append("\n ORDER BY ");
            sort.forEach(value ->
                sql.append(fieldSorts.get(value.getProperty())).append(" ").append(value.getDirection().name()).append(",")
            );
            sql.deleteCharAt(sql.length() - 1);
        } else {
            sql.append(" ORDER BY sc.last_modified_date DESC");
        }
    }

    private Map<String, String> fieldSorts() {
        Map<String, String> fieldSorts = new HashMap<>();
        fieldSorts.put("code", "sc.code");
        fieldSorts.put("name", "sc.name");
        fieldSorts.put("lastModifiedBy", "sc.last_modified_by");
        fieldSorts.put("lastModifiedDate", "sc.last_modified_date");
        return fieldSorts;
    }
}

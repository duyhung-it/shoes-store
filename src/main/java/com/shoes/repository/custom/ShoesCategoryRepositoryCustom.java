package com.shoes.repository.custom;

import com.shoes.service.dto.ShoesCategorySearchResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShoesCategoryRepositoryCustom {
    Page<ShoesCategorySearchResDTO> search(String searchText, Pageable pageable);
}

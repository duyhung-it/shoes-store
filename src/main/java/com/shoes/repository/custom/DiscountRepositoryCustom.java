package com.shoes.repository.custom;

import com.shoes.service.dto.DiscountDTO;
import com.shoes.service.dto.DiscountSearchDTO;
import java.util.List;

public interface DiscountRepositoryCustom {
    List<DiscountSearchDTO> search(String searchText);
}

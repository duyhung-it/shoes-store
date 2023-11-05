package com.shoes.repository.custom;

import com.shoes.service.dto.OrderSearchReqDTO;
import com.shoes.service.dto.OrderSearchResDTO;
import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderSearchResDTO> search(OrderSearchReqDTO orderSearchReqDTO);
}

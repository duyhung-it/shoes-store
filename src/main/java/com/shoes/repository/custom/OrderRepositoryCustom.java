package com.shoes.repository.custom;

import com.shoes.service.dto.OrderSearchReqDTO;
import com.shoes.service.dto.OrderSearchResDTO;
import com.shoes.service.dto.OrderStatusDTO;
import java.util.List;

public interface OrderRepositoryCustom {
    List<OrderSearchResDTO> search(OrderSearchReqDTO orderSearchReqDTO);
    List<OrderStatusDTO> getQuantityOrders();
}

package com.shoes.repository.custom;

import com.shoes.service.dto.OrderReturnSearchResDTO;
import com.shoes.service.dto.OrderSearchReqDTO;
import java.util.List;

public interface OrderReturnCustomRepository {
    List<OrderReturnSearchResDTO> search(OrderSearchReqDTO orderSearchReqDTO);
}

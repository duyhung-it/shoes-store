package com.shoes.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturnReqDTO {

    private Long id;
    private Long orderId;
    private List<OrderReturnDetailsDTO> returnOrderDetails;
}

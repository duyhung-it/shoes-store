package com.shoes.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOrderReturnDTO {

    Long id;
    List<OrderReturnDetailsDTO> list;
}

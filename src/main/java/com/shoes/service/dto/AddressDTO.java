package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO for the {@link com.shoes.domain.Address} entity.
 */
@Data
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressDTO implements Serializable {

    private Long id;

    private Integer province;
    private String provinceName;

    private Integer district;
    private String districtName;

    private Integer ward;
    private String wardName;

    private String addressDetails;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}

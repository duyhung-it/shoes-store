package com.shoes.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the {@link com.shoes.domain.ShoesDetails} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesDetailsDTO implements Serializable {

    private Long id;

    private String code;

    private BigDecimal price;

    private BigDecimal import_price;

    private BigDecimal tax;

    private Long quantity;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ShoesDTO shoes;

    private BrandDTO brand;

    private SizeDTO size;

    private ColorDTO color;

    private List<String> imgPath;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoesDetailsDTO)) {
            return false;
        }

        ShoesDetailsDTO shoesDetailsDTO = (ShoesDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shoesDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}

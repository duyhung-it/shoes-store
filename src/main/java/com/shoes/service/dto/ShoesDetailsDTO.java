package com.shoes.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import com.shoes.domain.ShoesDetails;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the {@link com.shoes.domain.ShoesDetails} entity.
 */
@Data
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

    public ShoesDetailsDTO() {
    }

    public ShoesDetailsDTO(ShoesDetails shoesDetails) {
        this.id = shoesDetails.getId();
        this.code = shoesDetails.getCode();
        this.price = shoesDetails.getPrice();
        this.import_price = shoesDetails.getImport_price();
        this.tax = shoesDetails.getTax();
        this.quantity = shoesDetails.getQuantity();
        this.status = shoesDetails.getStatus();
        this.description = shoesDetails.getDescription();
        this.shoes = new ShoesDTO(shoesDetails.getShoes());
        this.brand = new BrandDTO(shoesDetails.getBrand());
        this.size = new SizeDTO(shoesDetails.getSize());
        this.color = new ColorDTO(shoesDetails.getColor());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ShoesDTO getShoes() {
        return shoes;
    }

    public void setShoes(ShoesDTO shoes) {
        this.shoes = shoes;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public SizeDTO getSize() {
        return size;
    }

    public void setSize(SizeDTO size) {
        this.size = size;
    }

    public ColorDTO getColor() {
        return color;
    }

    public void setColor(ColorDTO color) {
        this.color = color;
    }

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

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoesDetailsDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", shoes=" + getShoes() +
            ", brand=" + getBrand() +
            ", size=" + getSize() +
            ", color=" + getColor() +
            "}";
    }
}

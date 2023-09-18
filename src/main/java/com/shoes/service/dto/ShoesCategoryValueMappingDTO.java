package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.shoes.domain.ShoesCategoryValueMapping} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesCategoryValueMappingDTO implements Serializable {

    private Long id;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ShoesCategoryValueDTO category;

    private ShoesDetailsDTO shoesDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ShoesCategoryValueDTO getCategory() {
        return category;
    }

    public void setCategory(ShoesCategoryValueDTO category) {
        this.category = category;
    }

    public ShoesDetailsDTO getShoesDetails() {
        return shoesDetails;
    }

    public void setShoesDetails(ShoesDetailsDTO shoesDetails) {
        this.shoesDetails = shoesDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoesCategoryValueMappingDTO)) {
            return false;
        }

        ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO = (ShoesCategoryValueMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shoesCategoryValueMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoesCategoryValueMappingDTO{" +
            "id=" + getId() +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", category=" + getCategory() +
            ", shoesDetails=" + getShoesDetails() +
            "}";
    }
}

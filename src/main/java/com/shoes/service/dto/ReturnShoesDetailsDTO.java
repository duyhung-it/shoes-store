package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.shoes.domain.ReturnShoesDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReturnShoesDetailsDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Integer price;

    private Integer discount;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ShoesDetailsDTO shoesDetails;

    private ReturnOrderDetailsDTO returnOrderDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
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

    public ShoesDetailsDTO getShoesDetails() {
        return shoesDetails;
    }

    public void setShoesDetails(ShoesDetailsDTO shoesDetails) {
        this.shoesDetails = shoesDetails;
    }

    public ReturnOrderDetailsDTO getReturnOrderDetails() {
        return returnOrderDetails;
    }

    public void setReturnOrderDetails(ReturnOrderDetailsDTO returnOrderDetails) {
        this.returnOrderDetails = returnOrderDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReturnShoesDetailsDTO)) {
            return false;
        }

        ReturnShoesDetailsDTO returnShoesDetailsDTO = (ReturnShoesDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, returnShoesDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReturnShoesDetailsDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", discount=" + getDiscount() +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", shoesDetails=" + getShoesDetails() +
            ", returnOrderDetails=" + getReturnOrderDetails() +
            "}";
    }
}

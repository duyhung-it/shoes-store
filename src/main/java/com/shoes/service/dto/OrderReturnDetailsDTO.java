package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.shoes.domain.OrderReturnDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderReturnDetailsDTO implements Serializable {

    private Long id;

    private Integer returnQuantity;

    private Integer errorQuantity;

    private Integer type;

    private String reason;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private OrderDetailsDTO orderDetails;

    private ReturnOrderDTO returnOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Integer getErrorQuantity() {
        return errorQuantity;
    }

    public void setErrorQuantity(Integer errorQuantity) {
        this.errorQuantity = errorQuantity;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public OrderDetailsDTO getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetailsDTO orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ReturnOrderDTO getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(ReturnOrderDTO returnOrder) {
        this.returnOrder = returnOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderReturnDetailsDTO)) {
            return false;
        }

        OrderReturnDetailsDTO orderReturnDetailsDTO = (OrderReturnDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderReturnDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderReturnDetailsDTO{" +
            "id=" + getId() +
            ", returnQuantity=" + getReturnQuantity() +
            ", errorQuantity=" + getErrorQuantity() +
            ", type=" + getType() +
            ", reason='" + getReason() + "'" +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", orderDetails=" + getOrderDetails() +
            ", returnOrder=" + getReturnOrder() +
            "}";
    }
}

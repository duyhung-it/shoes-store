package com.shoes.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A OrderReturnDetails.
 */
@Entity
@Table(name = "order_return_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderReturnDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "return_quantity")
    private Integer returnQuantity;

    @Column(name = "error_quantity")
    private Integer errorQuantity;

    @Column(name = "type")
    private Integer type;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @ManyToOne
    private OrderDetails orderDetails;

    @ManyToOne
    private ReturnOrder returnOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrderReturnDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReturnQuantity() {
        return this.returnQuantity;
    }

    public OrderReturnDetails returnQuantity(Integer returnQuantity) {
        this.setReturnQuantity(returnQuantity);
        return this;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public Integer getErrorQuantity() {
        return this.errorQuantity;
    }

    public OrderReturnDetails errorQuantity(Integer errorQuantity) {
        this.setErrorQuantity(errorQuantity);
        return this;
    }

    public void setErrorQuantity(Integer errorQuantity) {
        this.errorQuantity = errorQuantity;
    }

    public Integer getType() {
        return this.type;
    }

    public OrderReturnDetails type(Integer type) {
        this.setType(type);
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReason() {
        return this.reason;
    }

    public OrderReturnDetails reason(String reason) {
        this.setReason(reason);
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return this.status;
    }

    public OrderReturnDetails status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OrderReturnDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public OrderReturnDetails createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public OrderReturnDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public OrderReturnDetails lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public OrderDetails getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderReturnDetails orderDetails(OrderDetails orderDetails) {
        this.setOrderDetails(orderDetails);
        return this;
    }

    public ReturnOrder getReturnOrder() {
        return this.returnOrder;
    }

    public void setReturnOrder(ReturnOrder returnOrder) {
        this.returnOrder = returnOrder;
    }

    public OrderReturnDetails returnOrder(ReturnOrder returnOrder) {
        this.setReturnOrder(returnOrder);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderReturnDetails)) {
            return false;
        }
        return id != null && id.equals(((OrderReturnDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderReturnDetails{" +
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
            "}";
    }
}

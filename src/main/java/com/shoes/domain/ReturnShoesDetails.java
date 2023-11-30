package com.shoes.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ReturnShoesDetails.
 */
@Entity
@Table(name = "return_shoes_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReturnShoesDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;

    @Column(name = "discount")
    private Integer discount;

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
    private ShoesDetails shoesDetails;

    @ManyToOne
    private ReturnOrderDetails returnOrderDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReturnShoesDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public ReturnShoesDetails quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return this.price;
    }

    public ReturnShoesDetails price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return this.discount;
    }

    public ReturnShoesDetails discount(Integer discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getStatus() {
        return this.status;
    }

    public ReturnShoesDetails status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ReturnShoesDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public ReturnShoesDetails createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ReturnShoesDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public ReturnShoesDetails lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ShoesDetails getShoesDetails() {
        return this.shoesDetails;
    }

    public void setShoesDetails(ShoesDetails shoesDetails) {
        this.shoesDetails = shoesDetails;
    }

    public ReturnShoesDetails shoesDetails(ShoesDetails shoesDetails) {
        this.setShoesDetails(shoesDetails);
        return this;
    }

    public ReturnOrderDetails getReturnOrderDetails() {
        return this.returnOrderDetails;
    }

    public void setReturnOrderDetails(ReturnOrderDetails returnOrderDetails) {
        this.returnOrderDetails = returnOrderDetails;
    }

    public ReturnShoesDetails returnOrderDetails(ReturnOrderDetails returnOrderDetails) {
        this.setReturnOrderDetails(returnOrderDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReturnShoesDetails)) {
            return false;
        }
        return id != null && id.equals(((ReturnShoesDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReturnShoesDetails{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", discount=" + getDiscount() +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}

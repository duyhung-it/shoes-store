package com.shoes.domain;

import com.shoes.service.dto.OrderReturnSearchResDTO;
import com.shoes.service.dto.OrderSearchResDTO;
import com.shoes.service.dto.OrderStatusDTO;
import com.shoes.service.dto.RevenueDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import lombok.Getter;

/**
 * A OrderReturn.
 */
@Getter
@Entity
@Table(name = "order_return")
@SqlResultSetMappings(
    value = {
        @SqlResultSetMapping(
            name = "orderReturnResult",
            classes = {
                @ConstructorResult(
                    targetClass = OrderReturnSearchResDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "order_return_code", type = String.class),
                        @ColumnResult(name = "order_code", type = String.class),
                        @ColumnResult(name = "login", type = String.class),
                        @ColumnResult(name = "phone", type = String.class),
                        @ColumnResult(name = "status", type = Integer.class),
                        @ColumnResult(name = "last_modified_by", type = String.class),
                        @ColumnResult(name = "created_date", type = Instant.class),
                    }
                ),
            }
        ),
    }
)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderReturn extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

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
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public OrderReturn id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderReturn code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OrderReturn title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OrderReturn description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderReturn status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public OrderReturn createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public OrderReturn createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public OrderReturn lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public OrderReturn lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderReturn order(Order order) {
        this.setOrder(order);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderReturn)) {
            return false;
        }
        return id != null && id.equals(((OrderReturn) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderReturn{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}

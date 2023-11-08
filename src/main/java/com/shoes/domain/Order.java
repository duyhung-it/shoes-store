package com.shoes.domain;

import com.shoes.service.dto.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMappings(
    value = {
        @SqlResultSetMapping(
            name = "orders_result",
            classes = {
                @ConstructorResult(
                    targetClass = OrderSearchResDTO.class,
                    columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "idCustomer", type = Long.class),
                        @ColumnResult(name = "customer", type = String.class),
                        @ColumnResult(name = "phone", type = String.class),
                        @ColumnResult(name = "receivedBy", type = String.class),
                        @ColumnResult(name = "totalPrice", type = BigDecimal.class),
                        @ColumnResult(name = "status", type = Integer.class),
                        @ColumnResult(name = "createdDate", type = Instant.class),
                        @ColumnResult(name = "lastModifiedBy", type = String.class),
                    }
                ),
            }
        ),
        @SqlResultSetMapping(
            name = "orders_quantity_result",
            classes = {
                @ConstructorResult(
                    targetClass = OrderStatusDTO.class,
                    columns = {
                        @ColumnResult(name = "status", type = Integer.class), @ColumnResult(name = "quantity", type = Integer.class),
                    }
                ),
            }
        ),
    }
)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "payment_method")
    private Integer paymentMethod;

    @Column(name = "ship_price", precision = 21, scale = 2)
    private BigDecimal shipPrice;

    @Column(name = "total_price", precision = 21, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "received_by")
    private String receivedBy;

    @Column(name = "received_date")
    private Instant receivedDate;

    @Column(name = "shipped_date")
    private Instant shippedDate;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private User owner;

    @ManyToOne
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address userAddress;
}

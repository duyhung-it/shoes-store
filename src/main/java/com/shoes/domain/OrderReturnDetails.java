package com.shoes.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A OrderReturnDetails.
 */
@Entity
@Table(name = "order_return_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderReturnDetails extends AbstractAuditingEntity<Long> implements Serializable {

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
    private OrderReturn orderReturn;
}

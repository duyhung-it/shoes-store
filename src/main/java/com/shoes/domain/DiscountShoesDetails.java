package com.shoes.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DiscountShoesDetails.
 */
@Entity
@Table(name = "discount_shoes_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscountShoesDetails extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "discount_amount", precision = 21, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private Discount discount;

    @ManyToOne
    private Shoes shoesDetails;

    @Column(name = "brand_id")
    private Long brandId;
}

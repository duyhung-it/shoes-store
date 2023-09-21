package com.shoes.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A ShoesDetails.
 */
@Entity
@Table(name = "shoes_details")
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoesDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private Shoes shoes;

    @ManyToOne
    private Brand brand;
}

package com.shoes.domain;

import com.shoes.service.dto.ShoesDetailsDTO;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A FeedBack.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feed_back")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedBack extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private User user;

    @ManyToOne
    private ShoesDetails shoes;
    // jhipster-needle-entity-add-field - JHipster will add fields here

}

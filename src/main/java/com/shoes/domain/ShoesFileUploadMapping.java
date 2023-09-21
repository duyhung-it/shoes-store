package com.shoes.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A ShoesFileUploadMapping.
 */
@Entity
@Table(name = "shoes_file_upload_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesFileUploadMapping extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private FileUpload fileUpload;

    @ManyToOne
    private ShoesDetails shoesDetails;
}

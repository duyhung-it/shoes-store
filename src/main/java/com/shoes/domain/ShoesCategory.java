package com.shoes.domain;

import com.shoes.service.dto.ShoesCategoryDTO;
import com.shoes.service.dto.ShoesCategorySearchResDTO;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A ShoesCategory.
 */
@Entity
@Table(name = "shoes_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMappings(
    value = @SqlResultSetMapping(
        name = "shoes_category_result",
        classes = {
            @ConstructorResult(
                targetClass = ShoesCategorySearchResDTO.class,
                columns = {
                    @ColumnResult(name = "id", type = Long.class),
                    @ColumnResult(name = "code", type = String.class),
                    @ColumnResult(name = "name", type = String.class),
                    @ColumnResult(name = "status", type = Integer.class),
                    @ColumnResult(name = "last_modified_by", type = String.class),
                    @ColumnResult(name = "last_modified_date", type = Instant.class),
                }
            ),
        }
    )
)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesCategory extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Integer status;
}

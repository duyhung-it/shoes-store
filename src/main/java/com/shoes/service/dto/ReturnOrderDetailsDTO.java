package com.shoes.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.shoes.domain.ReturnOrderDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReturnOrderDetailsDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReturnOrderDetailsDTO)) {
            return false;
        }

        ReturnOrderDetailsDTO returnOrderDetailsDTO = (ReturnOrderDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, returnOrderDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReturnOrderDetailsDTO{" +
            "id=" + getId() +
            "}";
    }
}

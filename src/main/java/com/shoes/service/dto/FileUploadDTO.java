package com.shoes.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.shoes.domain.FileUpload} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileUploadDTO implements Serializable {

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
        if (!(o instanceof FileUploadDTO)) {
            return false;
        }

        FileUploadDTO fileUploadDTO = (FileUploadDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileUploadDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileUploadDTO{" +
            "id=" + getId() +
            "}";
    }
}

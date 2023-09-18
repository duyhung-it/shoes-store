package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.shoes.domain.ShoesFileUploadMapping} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesFileUploadMappingDTO implements Serializable {

    private Long id;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private FileUploadDTO fileUpload;

    private ShoesDetailsDTO shoesDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public FileUploadDTO getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(FileUploadDTO fileUpload) {
        this.fileUpload = fileUpload;
    }

    public ShoesDetailsDTO getShoesDetails() {
        return shoesDetails;
    }

    public void setShoesDetails(ShoesDetailsDTO shoesDetails) {
        this.shoesDetails = shoesDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShoesFileUploadMappingDTO)) {
            return false;
        }

        ShoesFileUploadMappingDTO shoesFileUploadMappingDTO = (ShoesFileUploadMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, shoesFileUploadMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShoesFileUploadMappingDTO{" +
            "id=" + getId() +
            ", status=" + getStatus() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", fileUpload=" + getFileUpload() +
            ", shoesDetails=" + getShoesDetails() +
            "}";
    }
}

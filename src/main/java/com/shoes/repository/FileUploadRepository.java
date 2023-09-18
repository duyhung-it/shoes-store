package com.shoes.repository;

import com.shoes.domain.FileUpload;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FileUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {}

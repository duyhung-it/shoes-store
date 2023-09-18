package com.shoes.service.impl;

import com.shoes.domain.FileUpload;
import com.shoes.repository.FileUploadRepository;
import com.shoes.service.FileUploadService;
import com.shoes.service.dto.FileUploadDTO;
import com.shoes.service.mapper.FileUploadMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileUpload}.
 */
@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

    private final Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    private final FileUploadRepository fileUploadRepository;

    private final FileUploadMapper fileUploadMapper;

    public FileUploadServiceImpl(FileUploadRepository fileUploadRepository, FileUploadMapper fileUploadMapper) {
        this.fileUploadRepository = fileUploadRepository;
        this.fileUploadMapper = fileUploadMapper;
    }

    @Override
    public FileUploadDTO save(FileUploadDTO fileUploadDTO) {
        log.debug("Request to save FileUpload : {}", fileUploadDTO);
        FileUpload fileUpload = fileUploadMapper.toEntity(fileUploadDTO);
        fileUpload = fileUploadRepository.save(fileUpload);
        return fileUploadMapper.toDto(fileUpload);
    }

    @Override
    public FileUploadDTO update(FileUploadDTO fileUploadDTO) {
        log.debug("Request to update FileUpload : {}", fileUploadDTO);
        FileUpload fileUpload = fileUploadMapper.toEntity(fileUploadDTO);
        // no save call needed as we have no fields that can be updated
        return fileUploadMapper.toDto(fileUpload);
    }

    @Override
    public Optional<FileUploadDTO> partialUpdate(FileUploadDTO fileUploadDTO) {
        log.debug("Request to partially update FileUpload : {}", fileUploadDTO);

        return fileUploadRepository
            .findById(fileUploadDTO.getId())
            .map(existingFileUpload -> {
                fileUploadMapper.partialUpdate(existingFileUpload, fileUploadDTO);

                return existingFileUpload;
            })
            // .map(fileUploadRepository::save)
            .map(fileUploadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileUploads");
        return fileUploadRepository.findAll(pageable).map(fileUploadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileUploadDTO> findOne(Long id) {
        log.debug("Request to get FileUpload : {}", id);
        return fileUploadRepository.findById(id).map(fileUploadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileUpload : {}", id);
        fileUploadRepository.deleteById(id);
    }
}

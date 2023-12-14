package com.shoes.web.rest;

import com.shoes.config.Constants;
import com.shoes.domain.FileUpload;
import com.shoes.domain.ShoesDetails;
import com.shoes.domain.ShoesFileUploadMapping;
import com.shoes.domain.Size;
import com.shoes.repository.ShoesDetailsRepository;
import com.shoes.repository.ShoesFileUploadMappingRepository;
import com.shoes.repository.SizeRepository;
import com.shoes.service.FileUploadService;
import com.shoes.service.ShoesDetailsService;
import com.shoes.service.ShoesFileUploadMappingService;
import com.shoes.service.dto.*;
import com.shoes.service.mapper.FileUploadMapper;
import com.shoes.service.mapper.ShoesDetailsMapper;
import com.shoes.util.AWSS3Util;
import com.shoes.util.DataUtils;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shoes.domain.ShoesDetails}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShoesDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ShoesDetailsResource.class);

    private static final String ENTITY_NAME = "shoesDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileUploadResource fileUploadResource;

    private final ShoesDetailsService shoesDetailsService;
    private final SizeRepository sizeRepository;
    private final ShoesDetailsRepository shoesDetailsRepository;
    private final FileUploadService fileUploadService;
    private final FileUploadMapper fileUploadMapper;
    private final ShoesFileUploadMappingService shoesFileUploadMappingService;
    private final ShoesDetailsMapper shoesDetailsMapper;
    private final ShoesFileUploadMappingRepository shoesFileUploadMappingRepository;

    /**
     * {@code POST  /shoes-details} : Create a new shoesDetails.
     *
     * @param shoesDetailsDTO the shoesDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoesDetailsDTO, or with status {@code 400 (Bad Request)} if the shoesDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoes-details")
    public ResponseEntity<ShoesDetailsDTO> createShoesDetails(@RequestBody ShoesDetailsDTO shoesDetailsDTO) throws URISyntaxException {
        if (shoesDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoesDetailsDTO result = shoesDetailsService.save(shoesDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/shoes-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/shoes-details-image")
    public ResponseEntity<ShoesDetailsDTO> createShoesDetailsImages(
        @RequestPart ShoesDetailsDTO shoesDetailsDTO,
        @RequestPart MultipartFile[] images
    ) throws URISyntaxException {
        // Kiểm tra nếu shoesDetailsDTO đã có ID
        if (shoesDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // Lưu thông tin giày vào cơ sở dữ liệu
        ShoesDetailsDTO result = shoesDetailsService.save(shoesDetailsDTO);
        // Kiểm tra nếu mảng images là null
        if (images == null) {
            throw new BadRequestAlertException("Null image", ENTITY_NAME, "idexists");
        } else {
            // Lặp qua từng ảnh và thực hiện các bước lưu và tải lên S3
            for (MultipartFile image : images) {
                FileUploadDTO fileUploadDTO = uploadNewToS3(image);
                // Tạo ShoesFileUploadMappingDTO và lưu vào cơ sở dữ liệu
                ShoesFileUploadMappingDTO shoesFileUploadMappingDTO = new ShoesFileUploadMappingDTO(
                    Constants.STATUS.ACTIVE,
                    fileUploadDTO,
                    result
                );
                ShoesFileUploadMappingDTO savedMapping = fileUploadMapping(shoesFileUploadMappingDTO);
            }
        }

        // Trả về thông báo tạo thành công và thông tin giày đã lưu
        return ResponseEntity
            .created(new URI("/api/shoes-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @Transactional(rollbackFor = Exception.class)
    public ShoesFileUploadMappingDTO fileUploadMapping(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        try {
            validateNewMapping(shoesFileUploadMappingDTO);
            ShoesFileUploadMappingDTO result = shoesFileUploadMappingService.save(shoesFileUploadMappingDTO);
            return result;
        } catch (BadRequestAlertException e) {
            // Handle the exception appropriately, log it, and rethrow or return null as needed
            log.error("Error while processing file upload mapping", e);
            throw e;
        } catch (Exception e) {
            // Handle unexpected exceptions, log them, and rethrow or return null as needed
            log.error("Unexpected error while processing file upload mapping", e);
            throw new RuntimeException("Unexpected error while processing file upload mapping", e);
        }
    }

    private void validateNewMapping(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        if (shoesFileUploadMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesFileUploadMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public FileUploadDTO uploadNewToS3(MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File fileOut = DataUtils.multipartFileToFile(file);
            // Build S3 path
            String s3Path = "images/" + Constants.KEY_UPLOAD + file.getOriginalFilename();
            String s3Url = "https://duyhung-bucket.s3.ap-southeast-1.amazonaws.com/" + s3Path;
            // Check if the file with the same path exists in S3
            if (s3Url == null) {
                throw new IllegalArgumentException("s3Url is null");
            }
            Optional<FileUploadDTO> existingFileUpload = fileUploadService.findOneByPath(s3Url);
            if (existingFileUpload.isPresent()) {
                // File already exists in S3, return the existing FileUploadDTO
                return existingFileUpload.get();
            } else {
                // File doesn't exist in S3, upload it and save FileUploadDTO
                FileUpload fileUpload = new FileUpload(null, s3Url, s3Path, Constants.STATUS.ACTIVE);
                FileUploadDTO fileUploadDTO = fileUploadService.save(fileUploadMapper.toDto(fileUpload));
                // Upload to S3

                new AWSS3Util().uploadPhoto(s3Path, fileOut);
                return fileUploadDTO;
            }
        } catch (IOException e) {
            // Handle the exception appropriately, log it, and rethrow or return null as needed
            log.error("Error while processing file for S3 upload", e);
            throw new RuntimeException("Error while processing file for S3 upload", e);
        }
    }

    /**
     * {@code PUT  /shoes-details/:id} : Updates an existing shoesDetails.
     *
     * @param id the id of the shoesDetailsDTO to save.
     * @param shoesDetailsDTO the shoesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the shoesDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoes-details/{id}")
    public ResponseEntity<ShoesDetailsDTO> updateShoesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesDetailsDTO shoesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoesDetails : {}, {}", id, shoesDetailsDTO);
        if (shoesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoesDetailsDTO result = shoesDetailsService.update(shoesDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shoes-details/:id} : Partial updates given fields of an existing shoesDetails, field will ignore if it is null
     *
     * @param id the id of the shoesDetailsDTO to save.
     * @param shoesDetailsDTO the shoesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the shoesDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoesDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shoes-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoesDetailsDTO> partialUpdateShoesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesDetailsDTO shoesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoesDetails partially : {}, {}", id, shoesDetailsDTO);
        if (shoesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoesDetailsDTO> result = shoesDetailsService.partialUpdate(shoesDetailsDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shoes-details} : get all the shoesDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoesDetails in body.
     */
    @GetMapping("/shoes-details")
    public ResponseEntity<List<ShoesDetailsDTO>> getAllShoesDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        List<ShoesDetailsDTO> page = shoesDetailsService.findAll(pageable);
        for (ShoesDetailsDTO x : page) {
            ShoesDetails shoesDetails = ShoesDetailsMapper.INSTANCE.toEntity(x);
            x.setImgPath(getAllFilePathsForShoesDetails(shoesDetails));
        }
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/shoes-details-variants")
    public ResponseEntity<List<ShoesVariant>> getAllShoesVariants() {
        List<ShoesVariant> page = shoesDetailsRepository.getAllVariant();
        return ResponseEntity.ok().body(page);
    }

    public List<String> getAllFilePathsForShoesDetails(ShoesDetails shoesDetails) {
        List<ShoesFileUploadMapping> mappings = shoesFileUploadMappingRepository.findByShoesDetails(shoesDetails);
        List<String> filePaths = mappings.stream().map(mapping -> mapping.getFileUpload().getPath()).collect(Collectors.toList());
        return filePaths;
    }

    /**
     * {@code GET  /shoes-details/:id} : get the "id" shoesDetails.
     *
     * @param id the id of the shoesDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoesDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shoes-details/{id}")
    public ResponseEntity<ShoesDetailsDTO> getShoesDetails(@PathVariable Long id) {
        Optional<ShoesDetailsDTO> shoesDetailsDTO = shoesDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoesDetailsDTO);
    }

    /**
     * {@code DELETE  /shoes-details/:id} : delete the "id" shoesDetails.
     *
     * @param id the id of the shoesDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoes-details/{id}")
    public ResponseEntity<Void> deleteShoesDetails(@PathVariable Long id) {
        shoesDetailsService.deleteSoft(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/shoes-details/shop")
    public ResponseEntity<List<ShopShoesDTO>> testing(@RequestBody SearchSDsResponse response) {
        List<Long> ids = response.getSizeIds();
        if (response.getSizeIds().isEmpty()) {
            ids = sizeRepository.findAll().stream().map(Size::getId).collect(Collectors.toList());
        }
        return ResponseEntity
            .ok()
            .body(
                shoesDetailsRepository.findDistinctByShoesAndBrandOrderBySellPriceDesc(
                    ids,
                    response.getBrandId(),
                    response.getStartPrice(),
                    response.getEndPrice()
                )
            );
    }

    @PostMapping("/shoes-details/shop/detail")
    public ResponseEntity<ShopShoesDTO> getShopShoesById(@RequestBody FindingOneDtos x) {
        ShopShoesDTO shopShoesDTO = shoesDetailsRepository.findDistinctByShoesAndBrandOrderBySellPriceDescOne(
            x.getShid(),
            x.getBrid(),
            x.getSiid(),
            x.getClid()
        );
        if (shopShoesDTO != null) {
            return ResponseEntity.ok().body(shopShoesDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/shoes-details/new")
    public ResponseEntity<List<ShoesDetailDTOCustom>> getNewShoesDetail() {
        List<ShoesDetailDTOCustom> shoesDetailsDTOs = shoesDetailsService.getNewShoesDetail();
        return ResponseEntity.ok().body(shoesDetailsDTOs);
    }

    @GetMapping("/shoes-details/newDiscount")
    public ResponseEntity<List<ShoesDetailDTOCustom>> getNewDiscountShoesDetail() {
        List<ShoesDetailDTOCustom> shoesDetailsDTOs = shoesDetailsService.getNewDiscountShoesDetail();
        return ResponseEntity.ok().body(shoesDetailsDTOs);
    }

    @GetMapping("/shoes-details/discount")
    public ResponseEntity<List<ShopShoesDTO>> getShoesDiscount() {
        List<ShopShoesDTO> shoesDetailsDTOs = shoesDetailsService.getDiscountShoes();
        return ResponseEntity.ok().body(shoesDetailsDTOs);
    }
}

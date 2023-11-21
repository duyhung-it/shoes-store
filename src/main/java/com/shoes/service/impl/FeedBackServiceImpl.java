package com.shoes.service.impl;

import com.shoes.domain.FeedBack;
import com.shoes.repository.FeedBackRepository;
import com.shoes.service.FeedBackService;
import com.shoes.service.dto.FeedBackDTO;
import com.shoes.service.mapper.FeedBackMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FeedBack}.
 */
@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    private final Logger log = LoggerFactory.getLogger(FeedBackServiceImpl.class);

    private final FeedBackRepository feedBackRepository;

    private final FeedBackMapper feedBackMapper;

    public FeedBackServiceImpl(FeedBackRepository feedBackRepository, FeedBackMapper feedBackMapper) {
        this.feedBackRepository = feedBackRepository;
        this.feedBackMapper = feedBackMapper;
    }

    @Override
    public FeedBackDTO save(FeedBackDTO feedBackDTO) {
        log.debug("Request to save FeedBack : {}", feedBackDTO);
        FeedBack feedBack = feedBackMapper.toEntity(feedBackDTO);
        feedBack = feedBackRepository.save(feedBack);
        return feedBackMapper.toDto(feedBack);
    }

    @Override
    public FeedBackDTO update(FeedBackDTO feedBackDTO) {
        log.debug("Request to update FeedBack : {}", feedBackDTO);
        FeedBack feedBack = feedBackMapper.toEntity(feedBackDTO);
        feedBack = feedBackRepository.save(feedBack);
        return feedBackMapper.toDto(feedBack);
    }

    @Override
    public Optional<FeedBackDTO> partialUpdate(FeedBackDTO feedBackDTO) {
        log.debug("Request to partially update FeedBack : {}", feedBackDTO);

        return feedBackRepository
            .findById(feedBackDTO.getId())
            .map(existingFeedBack -> {
                feedBackMapper.partialUpdate(existingFeedBack, feedBackDTO);

                return existingFeedBack;
            })
            .map(feedBackRepository::save)
            .map(feedBackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedBackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FeedBacks");
        return feedBackRepository.findAll(pageable).map(feedBackMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedBackDTO> findOne(Long id) {
        log.debug("Request to get FeedBack : {}", id);
        return feedBackRepository.findById(id).map(feedBackMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FeedBack : {}", id);
        feedBackRepository.deleteById(id);
    }
}

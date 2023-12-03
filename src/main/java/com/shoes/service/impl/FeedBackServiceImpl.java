package com.shoes.service.impl;

import com.shoes.domain.FeedBack;
import com.shoes.repository.FeedBackRepository;
import com.shoes.service.FeedBackService;
import com.shoes.service.dto.FeedBackDTO;
import com.shoes.service.mapper.FeedBackMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<FeedBackDTO> findAll() {
        log.debug("Request to get all FeedBacks");
        return feedBackRepository.findAll().stream().map(feedBackMapper::toDto).collect(Collectors.toList());
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

    @Override
    public FeedBackDTO updateFeedbackStatus(Long id, Integer status) {
        feedBackRepository.updateStatus(id, status);
        FeedBack feedBack = feedBackRepository.findById(id).orElse(null);
        return feedBackMapper.toDto(feedBack);
    }
}

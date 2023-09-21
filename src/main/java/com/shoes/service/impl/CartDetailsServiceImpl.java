package com.shoes.service.impl;

import com.shoes.domain.CartDetails;
import com.shoes.repository.CartDetailsRepository;
import com.shoes.service.CartDetailsService;
import com.shoes.service.dto.CartDetailsDTO;
import com.shoes.service.mapper.CartDetailsMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CartDetails}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CartDetailsServiceImpl implements CartDetailsService {

    private final Logger log = LoggerFactory.getLogger(CartDetailsServiceImpl.class);

    private final CartDetailsRepository cartDetailsRepository;

    private final CartDetailsMapper cartDetailsMapper;

    @Override
    public CartDetailsDTO save(CartDetailsDTO cartDetailsDTO) {
        log.debug("Request to save CartDetails : {}", cartDetailsDTO);
        CartDetails cartDetails = cartDetailsMapper.toEntity(cartDetailsDTO);
        cartDetails = cartDetailsRepository.save(cartDetails);
        return cartDetailsMapper.toDto(cartDetails);
    }

    @Override
    public CartDetailsDTO update(CartDetailsDTO cartDetailsDTO) {
        log.debug("Request to update CartDetails : {}", cartDetailsDTO);
        CartDetails cartDetails = cartDetailsMapper.toEntity(cartDetailsDTO);
        cartDetails = cartDetailsRepository.save(cartDetails);
        return cartDetailsMapper.toDto(cartDetails);
    }

    @Override
    public Optional<CartDetailsDTO> partialUpdate(CartDetailsDTO cartDetailsDTO) {
        log.debug("Request to partially update CartDetails : {}", cartDetailsDTO);

        return cartDetailsRepository
            .findById(cartDetailsDTO.getId())
            .map(existingCartDetails -> {
                cartDetailsMapper.partialUpdate(existingCartDetails, cartDetailsDTO);

                return existingCartDetails;
            })
            .map(cartDetailsRepository::save)
            .map(cartDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CartDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CartDetails");
        return cartDetailsRepository.findAll(pageable).map(cartDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CartDetailsDTO> findOne(Long id) {
        log.debug("Request to get CartDetails : {}", id);
        return cartDetailsRepository.findById(id).map(cartDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CartDetails : {}", id);
        cartDetailsRepository.deleteById(id);
    }
}

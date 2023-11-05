package com.shoes.service;

import com.shoes.service.dto.ApiRequestDTO;
import com.shoes.service.dto.BankDTO;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Service Interface for create QR Code.
 */
public interface BankService {
    /**
     * List information all bank
     * @return the list bank entity.
     */
    List<BankDTO> findAll() throws JSONException, IOException, InterruptedException;
    /**
     * create QR code
     * @param apiRequest the api request call api.
     * @return the url image QR Code.
     */
    String createQR(ApiRequestDTO apiRequest) throws IOException, InterruptedException, JSONException;
}

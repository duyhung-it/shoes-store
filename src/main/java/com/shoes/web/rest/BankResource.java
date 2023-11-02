package com.shoes.web.rest;

import com.shoes.service.BankService;
import com.shoes.service.dto.ApiRequestDTO;
import com.shoes.service.dto.BankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

/**
 * REST controller for create QR code.
 */
@RestController
@RequestMapping("/api")
public class BankResource {
    @Autowired
    private BankService bankService;

    /**
     * {@code GET  /banks} : List information all banks.
     * @throws IOException {@code 400 (Bad Request)} if the url call api not found.
     * @throws InterruptedException {@code 400 (Bad Request)} if the url call api not found.
     * @throws JSONException {@code 400 (Bad Request)} if conversion from Response to Json throws an exception.
     */
    @GetMapping("/banks")
    public ResponseEntity<List<BankDTO>> listAllBank() {
        try{
            return ResponseEntity.ok().body(bankService.findAll());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Url API not found"
            );
        }catch (JSONException e){
            e.printStackTrace();
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Data type conversion failed"
            );
        }
    }

    /**
     * {@code PUT /banks} : create QR code.
     * @throws IOException {@code 400 (Bad Request)} if the url call api not found.
     * @throws InterruptedException {@code 400 (Bad Request)} if the url not found or body call api is incorrect.
     * @throws JSONException {@code 400 (Bad Request)} if conversion from Response to Json throws an exception.
     */
    @PutMapping("/banks")
    public ResponseEntity<String> createQR(@RequestBody ApiRequestDTO apiRequest){
        try{
            return ResponseEntity.ok().body(bankService.createQR(apiRequest));
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Url API not found or request body API is incorrect"
            );
        }catch (JSONException e){
            e.printStackTrace();
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Data type conversion failed"
            );
        }
    }
}

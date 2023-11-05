package com.shoes.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoes.domain.Color;
import com.shoes.service.BankService;
import com.shoes.service.dto.ApiRequestDTO;
import com.shoes.service.dto.BankDTO;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for create QR Code.
 */
@Service
public class BankServiceImpl implements BankService {
    @Override
    public List<BankDTO> findAll() throws JSONException, IOException, InterruptedException {
        List<BankDTO> banks = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.vietqr.io/v2/banks"))
            .method("GET", HttpRequest.BodyPublishers.noBody())
            .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray data = jsonObject.getJSONArray("data");
        for(int i = 0; i < data.length(); i++){
            banks.add(new BankDTO());
            banks.get(i).setId(Integer.parseInt(data.getJSONObject(i).get("id").toString()));
            banks.get(i).setName(data.getJSONObject(i).get("name").toString());
            banks.get(i).setBin(data.getJSONObject(i).get("bin").toString());
            banks.get(i).setCode(data.getJSONObject(i).get("code").toString());
        }
        return banks;
    }

    @Override
    public String createQR(ApiRequestDTO apiRequest) throws IOException, InterruptedException, JSONException {
        apiRequest.setFormat("text");
        apiRequest.setTemplate("compact");
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(apiRequest);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.vietqr.io/v2/generate"))
            .method("POST", HttpRequest.BodyPublishers.ofString(body))
            .headers("Accept","application/json")
            .headers("Content-Type","application/json; charset=utf-8")
            .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();
        return new JSONObject(responseBody).getJSONObject("data").get("qrDataURL").toString();
    }
}

package com.example.currencyapp.service;

import com.example.currencyapp.model.CurrencyRequest;
import com.example.currencyapp.repository.CurrencyRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyRequestRepository currencyRequestRepository;

    private static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/%s?format=json";

    public double getCurrentCurrencyValue(String currencyCode) {
        String url = String.format(NBP_API_URL, currencyCode);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || response.isEmpty()) {
                throw new RuntimeException("Invalid response from NBP API");
            }
            List<Map<String, Object>> rates = (List<Map<String, Object>>) response.get("rates");
            if (rates == null || rates.isEmpty()) {
                throw new RuntimeException("No rates found in the response");
            }
            return (double) rates.get(0).get("mid");
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch currency value", e);
        }
    }

    public void saveCurrencyRequest(String currency, String name, double value) {
        CurrencyRequest currencyRequest = new CurrencyRequest();
        currencyRequest.setCurrency(currency);
        currencyRequest.setName(name);
        currencyRequest.setRequestDate(LocalDateTime.now());
        currencyRequest.setWartosc(value);
        currencyRequestRepository.save(currencyRequest);
    }

    public List<CurrencyRequest> getAllCurrencyRequests() {
        return currencyRequestRepository.findAll();
    }
}


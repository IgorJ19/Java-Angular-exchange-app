package com.example.currencyapp.controller;

import com.example.currencyapp.model.CurrencyRequest;
import com.example.currencyapp.repository.CurrencyRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyRequestRepository repository;

    CurrencyController(final CurrencyRequestRepository repo){
        this.repository = (CurrencyRequestRepository) repo;
    }

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @PostMapping("/get-current-currency-value-command")
    public ResponseEntity<?> getCurrentCurrencyValue(@RequestBody Map<String, String> request) {
        String currency = request.get("currency");
        String name = request.get("name");

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";

        try {
            ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
            Object[] rateTables = response.getBody();

            if (rateTables != null && rateTables.length > 0) {
                Map<String, Object> rateTable = (Map<String, Object>) rateTables[0];
                List<Map<String, Object>> rates = (List<Map<String, Object>>) rateTable.get("rates");

                for (Map<String, Object> rate : rates) {
                    if (rate.get("code").equals(currency)) {
                        double value = (double) rate.get("mid");

                        CurrencyRequest currencyRequest = new CurrencyRequest();
                        currencyRequest.setCurrency(currency);
                        currencyRequest.setName(name);
                        currencyRequest.setRequestDate(LocalDateTime.now());
                        currencyRequest.setWartosc(value);

                        repository.save(currencyRequest);

                        return ResponseEntity.ok(Map.of("value", value));
                    }
                }
            }
            return ResponseEntity.status(404).body("Currency not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching currency data");
        }
    }

        @GetMapping("/requests")
        public ResponseEntity<List<CurrencyRequest>> getAllRequests() {
                logger.warn("Uwidaczniam dane!");
                return ResponseEntity.ok(repository.findAll());
        }

        @DeleteMapping("/requests/{id}")
        ResponseEntity<Optional<CurrencyRequest>> usunWybrane(@PathVariable long id) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
}


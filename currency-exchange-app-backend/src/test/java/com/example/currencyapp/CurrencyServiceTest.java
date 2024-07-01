package com.example.currencyapp;

import com.example.currencyapp.model.CurrencyRequest;
import com.example.currencyapp.repository.CurrencyRequestRepository;
import com.example.currencyapp.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyRequestRepository currencyRequestRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentCurrencyValue() {
        // Mock response from NBP API
        String currencyCode = "EUR";
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/A/%s?format=json", currencyCode);
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("rates", Collections.singletonList(Collections.singletonMap("mid", 4.2954)));
        when(restTemplate.getForObject(eq(url), eq(Map.class))).thenReturn(mockResponse);

        // Execute the service call
        double value = currencyService.getCurrentCurrencyValue(currencyCode);

        // Verify the result
        assertEquals(4.2954, value);
    }

    @Test
    void testGetCurrentCurrencyValue_InvalidCurrency() {
        // Mock response from NBP API
        String currencyCode = "INVALID";
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/A/%s?format=json", currencyCode);
        when(restTemplate.getForObject(eq(url), eq(Map.class))).thenThrow(new RuntimeException("Invalid currency"));

        // Execute the service call and expect an exception
        assertThrows(RuntimeException.class, () -> currencyService.getCurrentCurrencyValue(currencyCode));
    }

    @Test
    void testSaveCurrencyRequest() {
        // Execute the service call
        currencyService.saveCurrencyRequest("EUR", "Jan Nowak", 4.2954);

        // Verify the save method was called
        verify(currencyRequestRepository, times(1)).save(any(CurrencyRequest.class));
    }

    @Test
    void testGetAllCurrencyRequests() {
        // Mock the repository findAll method
        CurrencyRequest request = new CurrencyRequest();
        request.setCurrency("EUR");
        request.setName("Jan Nowak");
        request.setRequestDate(LocalDateTime.now());
        request.setWartosc(4.2954);
        when(currencyRequestRepository.findAll()).thenReturn(Collections.singletonList(request));

        // Execute the service call
        List<CurrencyRequest> requests = currencyService.getAllCurrencyRequests();

        // Verify the result
        assertNotNull(requests);
        assertEquals(1, requests.size());
        assertEquals("EUR", requests.get(0).getCurrency());
        assertEquals("Jan Nowak", requests.get(0).getName());
        assertEquals(4.2954, requests.get(0).getWartosc());

    }
}
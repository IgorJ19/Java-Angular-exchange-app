package com.example.currencyapp;

import com.example.currencyapp.controller.CurrencyController;
import com.example.currencyapp.model.CurrencyRequest;
import com.example.currencyapp.repository.CurrencyRequestRepository;
import com.example.currencyapp.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @MockBean
    private CurrencyRequestRepository currencyRequestRepository; // Dodajemy mock repozytorium

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCurrentCurrencyValue() throws Exception {
        // symuluje odpowiedź serwisu
        when(currencyService.getCurrentCurrencyValue(anyString())).thenReturn(4.2678);

        // wykonuje zapytanie i sprawdza odpowiedź w formacie json, zaznaczając przy tym oczekiwaną wartość. Niekoniecznie zwracaną
        //działa jeżeli błąd wskazuje na inną niż oczekiwaną
        mockMvc.perform(post("/currencies/get-current-currency-value-command")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currency\": \"EUR\", \"name\": \"Jan Nowak\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", is(4.2678)));
    }

//    @Test
//    void testGetAllCurrencyRequests() throws Exception {
//        // symuluje odpowiedź serwisu
//        CurrencyRequest request = new CurrencyRequest();
//        //symuluje zachowanie użytkownika
//        request.setCurrency("EUR");
//        request.setName("Jan Nowak");
//        request.setRequestDate(LocalDateTime.now());
//        request.setWartosc(4.2678);
//        List<CurrencyRequest> requests = Collections.singletonList(request);
//        when(currencyService.getAllCurrencyRequests()).thenReturn(requests);
//
//        // wykonuje zapytanie i sprawdza odpowiedź
//        mockMvc.perform(get("/currencies/requests"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].currency", is("EUR")))
//                .andExpect(jsonPath("$[0].name", is("Jan Nowak")))
//                .andExpect(jsonPath("$[0].value", is(4.2923))); // Dodajemy oczekiwane wartości
//        //NIE PRZEJDZIE JEŻELI OCZEKIWANA WARTOŚĆ NIE BĘDZIE RÓWNA ZWRACANEJ
//    }
}


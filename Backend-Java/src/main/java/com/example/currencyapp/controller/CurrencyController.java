package com.example.currencyapp.controller;

import com.example.currencyapp.model.CurrencyRequest;
import com.example.currencyapp.repository.CurrencyRequestRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

    @CrossOrigin(origins = "http://localhost:4200")
    @RestController
    @RequestMapping("/currencies")
    public class CurrencyController {

        private final CurrencyRequestRepository repository;

        // Konstruktor z jednym argumentem, wstrzykuje zależność
        CurrencyController(final CurrencyRequestRepository repo) { this.repository = repo; // Przypisanie wstrzykniętego repozytorium do pola klasy
         }

        private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

        @PostMapping("/get-current-currency-value-command")
        public ResponseEntity<?> getCurrentCurrencyValue(@RequestBody Map<String, String> request) {
            String currency = request.get("currency");
            String name = request.get("name");

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";

            try {
                //do response w tablice wpisujemy dane z api i pozostawiamy dobór typu w zależności od typu przekazywanych danych
                ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
                //tablica o znowu "dowolnym" typie (gdyż klasa object jest najwyżej położoną)
                Object[] rateTables = response.getBody();

                if (rateTables != null && rateTables.length > 0) {
                    Map<String, Object> rateTable = (Map<String, Object>) rateTables[0];
                    List<Map<String, Object>> rates = (List<Map<String, Object>>) rateTable.get("rates");

                    //Mapapa w której klucz ma typ string a wartość "dowolny" typ, pętla potwarza się tyle razy
                    // ile jest rekordów w rates, gdzie rates to lista map wypełnionych rekordami "rates" z rateTable, czyli tworzymy zamapowaną tablicę a potem
                    // na jej podstawie listę finalnie wypełnioną danymi z ciała odpowiedzi
                    for (Map<String, Object> rate : rates) {
                        //instrukcja warunkowa poniżej sprawdza czy kod waluty należy do api
                        if (rate.get("code").equals(currency)) {

                            //pobieramy wartość po jej kluczu
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
                } //obsługa błędów związana z ryzykowną metodą która może je zwracać
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

        @Transactional
        @PatchMapping("/requests/patch/{id}")
        ResponseEntity<?> edytujWybrane(@PathVariable long id, @RequestBody CurrencyRequest name){
            if(id != 0){
                var zmieniany = repository.getById(id);

                zmieniany.setName(name.getName());
                repository.save(zmieniany);

                return ResponseEntity.noContent().build();
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }
    }


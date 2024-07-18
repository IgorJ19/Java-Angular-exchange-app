package com.example.currencyapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyApplicationTests {

	@Test
	void contextLoads() {
	}

}

/*
Sprawdza, czy kontekst aplikacji ładuje się poprawnie. Służy do weryfikacji,
czy konfiguracja aplikacji jest poprawna. Test sprawdza tylko, czy kontekst aplikacji opartej na
Spring Boot ładuje się poprawnie. Logi wskazują na kilka kluczowych punktów inicjalizacji, w
 tym wykrywanie konfiguracji aplikacji, inicjalizację repozytoriów JPA oraz problemy z uzyskaniem
 połączenia do bazy danych.  */
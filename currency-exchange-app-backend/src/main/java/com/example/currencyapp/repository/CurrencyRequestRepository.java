package com.example.currencyapp.repository;

import com.example.currencyapp.model.CurrencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrencyRequestRepository extends JpaRepository<CurrencyRequest, Long> { }

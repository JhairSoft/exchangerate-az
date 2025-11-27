package com.jhair.exchangerate.repository;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.jhair.exchangerate.model.Rate;

import reactor.core.publisher.Mono;

@Repository
public interface RateRepository extends R2dbcRepository<Rate, UUID>{
    
    Mono<Rate> findTopByOriginCurrencyAndFinalCurrencyOrderByDateDesc(String originCurrency, String finalCurrency);
}

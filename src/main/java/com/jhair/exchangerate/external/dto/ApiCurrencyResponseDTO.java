package com.jhair.exchangerate.external.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record ApiCurrencyResponseDTO(
        boolean success,
        Long timestamp,
        String base,
        LocalDate date,
        Map<String, BigDecimal> rates) {

}

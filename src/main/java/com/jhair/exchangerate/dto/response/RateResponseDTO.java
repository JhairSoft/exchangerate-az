package com.jhair.exchangerate.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RateResponseDTO(
        UUID id,
        String originCurrency,
        String finalCurrency,
        LocalDate date,
        BigDecimal value) {

}

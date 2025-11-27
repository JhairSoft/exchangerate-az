package com.jhair.exchangerate.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PatchRateRequestDTO(
        String originCurrency,
        String finalCurrency,
        LocalDate date,
        BigDecimal value) {

}

package com.jhair.exchangerate.external.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.jhair.exchangerate.external.dto.ApiCurrencyResponseDTO;
import com.jhair.exchangerate.model.Rate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApiCurrencyMapper {
    
    @Mapping(source = "dto.date", target = "date")
    @Mapping(source = "originCurrency", target = "originCurrency")
    @Mapping(source = "finalCurrency", target = "finalCurrency")
    @Mapping(source = "value", target = "value")
    @Mapping(target = "id", ignore = true)
    Rate toEntity(ApiCurrencyResponseDTO dto, String originCurrency, String finalCurrency, BigDecimal value);
}

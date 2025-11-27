package com.jhair.exchangerate.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jhair.exchangerate.dto.request.CreateRateRequestDTO;
import com.jhair.exchangerate.dto.request.PatchRateRequestDTO;
import com.jhair.exchangerate.dto.request.UpdateRateRequestDTO;
import com.jhair.exchangerate.dto.response.RateResponseDTO;
import com.jhair.exchangerate.exception.ResourceNotFoundException;
import com.jhair.exchangerate.external.client.CurrencyApiClient;
import com.jhair.exchangerate.external.mapper.CurrencyApiMapper;
import com.jhair.exchangerate.mapper.RateMapper;
import com.jhair.exchangerate.model.Rate;
import com.jhair.exchangerate.repository.RateRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final RateMapper mapper;
    private final CurrencyApiClient currencyApiClient;
    private final CurrencyApiMapper currencyApiMapper;

    public Flux<RateResponseDTO> getAll() {
        return rateRepository.findAll()
                .map(mapper::toDto);
    }

    public Mono<RateResponseDTO> getById(UUID id) {
        return rateRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró el Id : " + id)))
                .map(mapper::toDto);
    }

    public Mono<RateResponseDTO> getExchangeRate(String originCurrency, String finalCurrency) {
        return rateRepository.findTopByOriginCurrencyAndFinalCurrencyOrderByDateDesc(originCurrency, finalCurrency)
                .map(mapper::toDto)
                .switchIfEmpty(Mono.defer(() -> findAndSaveExchangeRate(originCurrency, finalCurrency)));
    }

    public Mono<RateResponseDTO> findAndSaveExchangeRate(String originCurrency, String finalCurrency) {
        return currencyApiClient.fetchExternalRate(originCurrency, finalCurrency)
                .flatMap(foundApiRespondDto -> 
                    Mono.justOrEmpty(foundApiRespondDto.rates().get(finalCurrency))
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró el tipo de cambio")))
                        .flatMap(foundRate -> {
                            Rate newRate = currencyApiMapper.toEntity(foundApiRespondDto, originCurrency, finalCurrency, foundRate);
                            return rateRepository.save(newRate);
                        }))
                .map(mapper::toDto);
    }

    public Mono<RateResponseDTO> create(CreateRateRequestDTO dto) {
        Rate newRate = mapper.toEntity(dto);
        return rateRepository.save(newRate)
                .map(mapper::toDto);
    }

    public Mono<RateResponseDTO> update(UUID id, UpdateRateRequestDTO dto) {
        return rateRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró el Id : " + id)))
                .flatMap(foundRate -> {
                    mapper.updateFromDto(dto, foundRate);
                    return rateRepository.save(foundRate);
                })
                .map(mapper::toDto);
    }

    public Mono<RateResponseDTO> patch(UUID id, PatchRateRequestDTO dto) {
        return rateRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró el Id : " + id)))
                .flatMap(foundRate -> {
                    mapper.patchFromDto(dto, foundRate);
                    return rateRepository.save(foundRate);
                })
                .map(mapper::toDto);
    }

    public Mono<Void> delete(UUID id) {
        return rateRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró el Id : " + id)))
                .flatMap(rateRepository::delete);
    }

}

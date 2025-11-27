package com.jhair.exchangerate.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jhair.exchangerate.dto.request.CreateRateRequestDTO;
import com.jhair.exchangerate.dto.request.PatchRateRequestDTO;
import com.jhair.exchangerate.dto.request.UpdateRateRequestDTO;
import com.jhair.exchangerate.dto.response.RateResponseDTO;
import com.jhair.exchangerate.service.RateService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("exchangerate")
public class RateController {

    private final RateService rateService;

    @GetMapping
    public Flux<RateResponseDTO> getAll() {
        return rateService.getAll();
    }

    @GetMapping("{id}")
    public Mono<RateResponseDTO> getById(@PathVariable UUID id) {
        return rateService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<RateResponseDTO> create(@RequestBody CreateRateRequestDTO dto) {
        return rateService.create(dto);
    }

    @PutMapping("{id}")
    public Mono<RateResponseDTO> update(@PathVariable UUID id,@RequestBody UpdateRateRequestDTO dto){
        return rateService.update(id, dto);
    }

    @PatchMapping("{id}")
    public Mono<RateResponseDTO> patch(@PathVariable UUID id, @RequestBody PatchRateRequestDTO dto){
        return rateService.patch(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id){
        return rateService.delete(id);
    }

}

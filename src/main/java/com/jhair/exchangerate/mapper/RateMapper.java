package com.jhair.exchangerate.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.jhair.exchangerate.dto.request.CreateRateRequestDTO;
import com.jhair.exchangerate.dto.request.PatchRateRequestDTO;
import com.jhair.exchangerate.dto.request.UpdateRateRequestDTO;
import com.jhair.exchangerate.dto.response.RateResponseDTO;
import com.jhair.exchangerate.model.Rate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RateMapper {

    RateResponseDTO toDto(Rate entity);

    Rate toEntity(CreateRateRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(UpdateRateRequestDTO dto, @MappingTarget Rate entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchFromDto(PatchRateRequestDTO dto, @MappingTarget Rate entity);
}

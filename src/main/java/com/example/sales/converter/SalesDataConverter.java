package com.example.sales.converter;

import com.example.sales.dto.SalesDataDto;
import com.example.sales.entity.SalesDataEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesDataConverter {

    public List<SalesDataEntity> convertSalesDataDtoToEntityList(List<SalesDataDto> salesDataDtoList) {
        return salesDataDtoList.stream()
                .map(this::convertSalesDataDtoToEntity)
                .collect(Collectors.toList());
    }

    public SalesDataEntity convertSalesDataDtoToEntity(SalesDataDto dto) {
        return SalesDataEntity.builder()
                .id(dto.getId())
                .trackingId(dto.getTrackingId())
                .visitDate(dto.getVisitDate())
                .saleDate(dto.getSaleDate())
                .salePrice(dto.getSalePrice())
                .product(dto.getProduct())
                .commissionAmount(dto.getCommissionAmount())
                .build();

    }
}

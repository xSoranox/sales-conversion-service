package com.example.sales.service;

import com.example.sales.entity.SalesDataEntity;
import com.example.sales.enums.CodeEnum;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ConversionService {

    private SalesDataValidator salesDataValidator;
    private final SalesDataJpaRepository salesDataJpaRepository;

    public Long getConversionByCodeAndData(CodeEnum code, LocalDateTime visitDate, LocalDateTime saleDate) {
        List<SalesDataEntity> salesDataEntityList = salesDataJpaRepository.findByTrackingIdInAndVisitDateBetween(
                code.getTrackingIdStringList(), visitDate, saleDate);

        return salesDataEntityList.stream()
                .filter(saleDateEntity -> salesDataValidator.hasSaleDate(saleDateEntity.getSaleDate()))
                .map(entity -> Duration.between(entity.getVisitDate(), entity.getSaleDate()))
                .reduce(Duration.ZERO, Duration::plus)
                .toMinutes();
    }

}

package com.example.sales.service;

import com.example.sales.entity.SalesDataEntity;
import com.example.sales.enums.CodeEnum;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CommissionService {

    private final SalesDataValidator salesDataValidator;
    private final SalesDataJpaRepository salesDataJpaRepository;

    public BigDecimal getTotalCommission(CodeEnum code, LocalDateTime visitDate, LocalDateTime saleDate) {
        List<SalesDataEntity> salesDataEntityList = salesDataJpaRepository.findByTrackingIdInAndVisitDateBetween(
                code.getTrackingIdStringList(), visitDate, saleDate);

        return salesDataEntityList.stream()
                .map(SalesDataEntity::getCommissionAmount)
                .filter(salesDataValidator::hasPositiveCommission)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

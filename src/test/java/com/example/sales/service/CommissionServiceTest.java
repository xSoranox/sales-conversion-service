package com.example.sales.service;

import com.example.sales.entity.SalesDataEntity;
import com.example.sales.enums.CodeEnum;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommissionServiceTest {

    @Mock
    private SalesDataValidator salesDataValidator;

    @Mock
    private SalesDataJpaRepository salesDataJpaRepository;
    @Mock
    private SalesDataEntity entity1;
    @Mock
    private SalesDataEntity entity2;

    @InjectMocks
    private CommissionService commissionService;

    private final LocalDateTime visitDate = LocalDateTime.of(2023, 1, 1, 0, 0);
    private final LocalDateTime saleDate = LocalDateTime.of(2023, 12, 31, 23, 59);

    @Test
    void getTotalCommission_returnsSumOfPositiveCommissions() {
        when(entity1.getCommissionAmount()).thenReturn(new BigDecimal("10.5"));
        when(entity2.getCommissionAmount()).thenReturn(new BigDecimal("5"));

        List<SalesDataEntity> entities = List.of(entity1, entity2);

        when(salesDataJpaRepository.findByTrackingIdInAndVisitDateBetween(any(), any(), any()))
                .thenReturn(entities);

        when(salesDataValidator.hasPositiveCommission(new BigDecimal("10.5"))).thenReturn(true);
        when(salesDataValidator.hasPositiveCommission(new BigDecimal("5"))).thenReturn(true);

        BigDecimal totalCommission = commissionService.getTotalCommission(CodeEnum.ABB, visitDate, saleDate);

        assertEquals(new BigDecimal("15.5"), totalCommission);
    }

    @Test
    void getTotalCommission_ignoresNonPositiveCommissions() {
        when(entity1.getCommissionAmount()).thenReturn(new BigDecimal("0"));
        when(entity2.getCommissionAmount()).thenReturn(new BigDecimal("-5"));

        List<SalesDataEntity> entities = List.of(entity1, entity2);

        when(salesDataJpaRepository.findByTrackingIdInAndVisitDateBetween(
                eq(CodeEnum.ABB.getTrackingIdStringList()), eq(visitDate), eq(saleDate)))
                .thenReturn(entities);

        when(salesDataValidator.hasPositiveCommission(new BigDecimal("0"))).thenReturn(false);
        when(salesDataValidator.hasPositiveCommission(new BigDecimal("-5"))).thenReturn(false);

        BigDecimal totalCommission = commissionService.getTotalCommission(CodeEnum.ABB, visitDate, saleDate);

        assertEquals(BigDecimal.ZERO, totalCommission);
    }

    @Test
    void getTotalCommission_handlesEmptyList() {
        when(salesDataJpaRepository.findByTrackingIdInAndVisitDateBetween(
                eq(CodeEnum.ABB.getTrackingIdStringList()), eq(visitDate), eq(saleDate)))
                .thenReturn(List.of());

        BigDecimal totalCommission = commissionService.getTotalCommission(CodeEnum.ABB, visitDate, saleDate);

        assertEquals(BigDecimal.ZERO, totalCommission);
    }
}
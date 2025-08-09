package com.example.sales.service;

import com.example.sales.entity.SalesDataEntity;
import com.example.sales.enums.CodeEnum;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {
    @Mock
    private SalesDataValidator salesDataValidator;
    @Mock
    private SalesDataJpaRepository salesDataJpaRepository;
    @Mock
    private SalesDataEntity entity1;
    @Mock
    private SalesDataEntity entity2;

    @InjectMocks
    private ConversionService conversionService;

    private final LocalDateTime visitDate = LocalDateTime.of(2023, 1, 1, 12, 0);
    private final LocalDateTime saleDate = LocalDateTime.of(2023, 1, 1, 14, 30); // 2.5 часа позже

    @Test
    void getConversionByCodeAndData_returnsSumOfDurationsInMinutes() {

        when(entity1.getVisitDate()).thenReturn(visitDate);
        when(entity1.getSaleDate()).thenReturn(saleDate);
        when(entity2.getVisitDate()).thenReturn(visitDate.plusHours(1));
        when(entity2.getSaleDate()).thenReturn(saleDate.plusHours(1));

        List<SalesDataEntity> entities = List.of(entity1, entity2);

        when(salesDataJpaRepository.findByTrackingIdInAndVisitDateBetween(
                eq(CodeEnum.ABB.getTrackingIdStringList()), eq(visitDate), eq(saleDate)))
                .thenReturn(entities);

        when(salesDataValidator.hasSaleDate(any())).thenReturn(true);

        Long totalMinutes = conversionService.getConversionByCodeAndData(CodeEnum.ABB, visitDate, saleDate);

        assertEquals(300L, totalMinutes);
    }

    @Test
    void getConversionByCodeAndData_ignoresEntitiesWithoutSaleDate() {
        when(entity1.getVisitDate()).thenReturn(visitDate);
        when(entity1.getSaleDate()).thenReturn(saleDate);
        when(entity2.getSaleDate()).thenReturn(null);

        List<SalesDataEntity> entities = List.of(entity1, entity2);

        when(salesDataJpaRepository.findByTrackingIdInAndVisitDateBetween(
                eq(CodeEnum.ABB.getTrackingIdStringList()), eq(visitDate), eq(saleDate)))
                .thenReturn(entities);

        when(salesDataValidator.hasSaleDate(saleDate)).thenReturn(true);
        when(salesDataValidator.hasSaleDate(null)).thenReturn(false);

        Long totalMinutes = conversionService.getConversionByCodeAndData(CodeEnum.ABB, visitDate, saleDate);

        assertEquals(150L, totalMinutes);
    }

}
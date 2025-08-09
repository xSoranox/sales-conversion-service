package com.example.sales.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.example.sales.entity.SalesDataEntity;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private SalesDataValidator salesDataValidator;
    @Mock
    private SalesDataJpaRepository salesDataJpaRepository;
    @Mock
    private SalesDataEntity entity1;
    @Mock
    private SalesDataEntity entity2;
    @Mock
    private SalesDataEntity entity3;

    @InjectMocks
    private ProductService productService;

    private final LocalDateTime visitDate = LocalDateTime.of(2023, 1, 1, 0, 0);
    private final LocalDateTime saleDate = LocalDateTime.of(2023, 1, 31, 23, 59);

    @Test
    void getProduct_returnsSumOfSalePricesGroupedByProduct() {
        when(entity1.getProduct()).thenReturn("ProductA");
        when(entity1.getSalePrice()).thenReturn(new BigDecimal("10.5"));

        when(entity2.getProduct()).thenReturn("ProductA");
        when(entity2.getSalePrice()).thenReturn(new BigDecimal("4.5"));

        when(entity3.getProduct()).thenReturn("ProductB");
        when(entity3.getSalePrice()).thenReturn(new BigDecimal("7.0"));

        List<SalesDataEntity> entities = List.of(entity1, entity2, entity3);

        when(salesDataJpaRepository.findByVisitDateBetween(visitDate, saleDate)).thenReturn(entities);

        when(salesDataValidator.hasPositiveSalePrice(new BigDecimal("10.5"))).thenReturn(true);
        when(salesDataValidator.hasPositiveSalePrice(new BigDecimal("4.5"))).thenReturn(true);
        when(salesDataValidator.hasPositiveSalePrice(new BigDecimal("7.0"))).thenReturn(true);

        Map<String, BigDecimal> result = productService.getProduct(visitDate, saleDate);

        assertEquals(2, result.size());
        assertEquals(new BigDecimal("15.0"), result.get("ProductA"));
        assertEquals(new BigDecimal("7.0"), result.get("ProductB"));
    }

    @Test
    void getProduct_filtersOutEntitiesWithNonPositiveSalePrice() {
        when(entity1.getProduct()).thenReturn("ProductA");
        when(entity1.getSalePrice()).thenReturn(new BigDecimal("10"));

        when(entity2.getSalePrice()).thenReturn(new BigDecimal("0"));

        List<SalesDataEntity> entities = List.of(entity1, entity2);

        when(salesDataJpaRepository.findByVisitDateBetween(visitDate, saleDate)).thenReturn(entities);

        when(salesDataValidator.hasPositiveSalePrice(new BigDecimal("10"))).thenReturn(true);
        when(salesDataValidator.hasPositiveSalePrice(new BigDecimal("0"))).thenReturn(false);

        Map<String, BigDecimal> result = productService.getProduct(visitDate, saleDate);

        assertEquals(1, result.size());
        assertEquals(new BigDecimal("10"), result.get("ProductA"));
        assertNull(result.get("ProductB"));
    }
}
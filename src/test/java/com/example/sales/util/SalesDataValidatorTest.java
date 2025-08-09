package com.example.sales.util;

import com.example.sales.dto.SalesDataDto;
import com.example.sales.exception.InvalidSalesDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SalesDataValidatorTest {

    private SalesDataValidator validator;
    private SalesDataDto dto;

    @BeforeEach
    void setup() {
        validator = new SalesDataValidator();
        dto = new SalesDataDto();
    }

    @Test
    void validate_validDto_noException() {
        dto.setTrackingId("track1");
        dto.setProduct("product1");
        dto.setVisitDate(LocalDateTime.now());
        dto.setSaleDate(LocalDateTime.now().plusDays(1));
        dto.setSalePrice(BigDecimal.TEN);

        assertDoesNotThrow(() -> validator.validate(dto));
    }

    @Test
    void validate_nullTrackingId_throwsException() {
        dto.setTrackingId(null);
        dto.setProduct("product");
        dto.setVisitDate(LocalDateTime.now());

        InvalidSalesDataException ex = assertThrows(InvalidSalesDataException.class,
                () -> validator.validate(dto));
        assertEquals("TrackingId, Product and VisitDate cannot be null", ex.getMessage());
    }

    @Test
    void validate_nullProduct_throwsException() {
        dto.setTrackingId("track");
        dto.setProduct(null);
        dto.setVisitDate(LocalDateTime.now());

        InvalidSalesDataException ex = assertThrows(InvalidSalesDataException.class,
                () -> validator.validate(dto));
        assertEquals("TrackingId, Product and VisitDate cannot be null", ex.getMessage());
    }

    @Test
    void validate_nullVisitDate_throwsException() {
        dto.setTrackingId("track");
        dto.setProduct("product");
        dto.setVisitDate(null);

        InvalidSalesDataException ex = assertThrows(InvalidSalesDataException.class,
                () -> validator.validate(dto));
        assertEquals("TrackingId, Product and VisitDate cannot be null", ex.getMessage());
    }

    @Test
    void validate_saleDateWithoutSalePrice_throwsException() {
        dto.setTrackingId("track");
        dto.setProduct("product");
        dto.setVisitDate(LocalDateTime.now());
        dto.setSaleDate(LocalDateTime.now());
        dto.setSalePrice(null);

        InvalidSalesDataException ex = assertThrows(InvalidSalesDataException.class,
                () -> validator.validate(dto));
        assertEquals("SaleDate and SalePrice must both be present or both be null", ex.getMessage());
    }

    @Test
    void validate_salePriceWithoutSaleDate_throwsException() {
        dto.setTrackingId("track");
        dto.setProduct("product");
        dto.setVisitDate(LocalDateTime.now());
        dto.setSaleDate(null);
        dto.setSalePrice(BigDecimal.ONE);

        InvalidSalesDataException ex = assertThrows(InvalidSalesDataException.class,
                () -> validator.validate(dto));
        assertEquals("SaleDate and SalePrice must both be present or both be null", ex.getMessage());
    }

    @Test
    void validate_saleDateOlderThanVisitDate_throwsException() {
        LocalDateTime visitDate = LocalDateTime.now();
        LocalDateTime saleDate = visitDate.minusDays(1);

        dto.setTrackingId("track");
        dto.setProduct("product");
        dto.setVisitDate(visitDate);
        dto.setSaleDate(saleDate);
        dto.setSalePrice(BigDecimal.ONE);

        InvalidSalesDataException ex = assertThrows(InvalidSalesDataException.class,
                () -> validator.validate(dto));
        assertEquals("Sale date must be before visit date", ex.getMessage());
    }
}
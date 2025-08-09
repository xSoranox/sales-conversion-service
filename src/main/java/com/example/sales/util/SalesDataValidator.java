package com.example.sales.util;

import com.example.sales.dto.SalesDataDto;
import com.example.sales.exception.InvalidSalesDataException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class SalesDataValidator {

    public void validate(SalesDataDto dto) {
        BigDecimal salePrice = dto.getSalePrice();
        LocalDateTime saleDate = dto.getSaleDate();
        LocalDateTime visitDate = dto.getVisitDate();
        if (!hasTrackingId(dto.getTrackingId()) || !hasProduct(dto.getProduct()) || !hasVisitDate(visitDate)) {
            throw new InvalidSalesDataException("TrackingId, Product and VisitDate cannot be null");
        }
        if (hasSaleDateWithoutSalePrice(saleDate, salePrice) || hasSalePriceWithoutSaleDate(saleDate, salePrice)) {
            throw new InvalidSalesDataException("SaleDate and SalePrice must both be present or both be null");
        }
        if (isSaleDateOlderThanVisitDate(saleDate, visitDate)) {
            throw new InvalidSalesDataException("Sale date must be before visit date");
        }

    }

    public boolean hasSaleDate(LocalDateTime saleDate) {
        return saleDate != null;
    }

    public boolean hasPositiveSalePrice(BigDecimal salePrice) {
        return salePrice != null && salePrice.compareTo(BigDecimal.ZERO) != 0;
    }

    public boolean hasPositiveCommission(BigDecimal commissionAmount) {
        return commissionAmount != null && commissionAmount.compareTo(BigDecimal.ZERO) != 0;
    }

    private boolean hasSaleDateWithoutSalePrice(LocalDateTime saleDate, BigDecimal salePrice) {
        return hasSaleDate(saleDate) && !hasSalePrice(salePrice);
    }

    private boolean hasSalePriceWithoutSaleDate(LocalDateTime saleDate, BigDecimal salePrice) {
        return hasSalePrice(salePrice) && !hasSaleDate(saleDate);
    }

    private boolean isSaleDateOlderThanVisitDate(LocalDateTime saleDate, LocalDateTime visitDate) {
        return hasSaleDate(saleDate) && saleDate.isBefore(visitDate);
    }

    private boolean hasSalePrice(BigDecimal salePrice) {
        return salePrice != null;
    }

    private boolean hasTrackingId(String trackingId) {
        return trackingId != null;
    }

    private boolean hasProduct(String product) {
        return product != null;
    }

    private boolean hasVisitDate(LocalDateTime visitDate) {
        return visitDate != null;
    }
}
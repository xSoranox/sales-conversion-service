package com.example.sales.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SalesDataDto {
    Integer id;
    String trackingId;
    LocalDateTime visitDate;
    LocalDateTime saleDate;
    BigDecimal salePrice;
    String product;
    BigDecimal commissionAmount;
}

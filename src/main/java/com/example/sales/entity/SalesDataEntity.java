package com.example.sales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales_dat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesDataEntity {
    @Id
    private Integer id;

    @Column(name = "tracking_id")
    private String trackingId;

    @Column(name = "visit_date")
    private LocalDateTime visitDate;

    @Column(name = "sale_date")
    private LocalDateTime saleDate;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "product")
    private String product;

    @Column(name = "commission_amount")
    private BigDecimal commissionAmount;
}

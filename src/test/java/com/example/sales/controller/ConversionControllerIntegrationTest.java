package com.example.sales.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.sales.entity.SalesDataEntity;
import com.example.sales.repository.SalesDataJpaRepository;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SalesDataJpaRepository salesDataJpaRepository;

    @BeforeEach
    void setup() {
        salesDataJpaRepository.deleteAll();
        initBase();
    }

    @Test
    void getTotalCommission_shouldReturnSumOfCommissions() throws Exception {
        mockMvc.perform(get("/api/total-commission")
                        .param("code", "ABB")
                        .param("visitDate", "2025-08-01T00:00:00")
                        .param("saleDate", "2025-08-04T00:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("41.50"));
    }

    @Test
    void getConversionRate_shouldReturnDurationInMinutes() throws Exception {
        mockMvc.perform(get("/api/conversion-rate")
                        .param("code", "ABB")
                        .param("visitDate", "2025-08-01T00:00:00")
                        .param("saleDate", "2025-08-04T00:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("10560"));
    }

    @Test
    void getProduct_shouldReturnGroupedProductsWithTotalSalePrice() throws Exception {
        mockMvc.perform(get("/api/product")
                        .param("visitDate", "2025-08-01T00:00:00")
                        .param("saleDate", "2025-08-04T00:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Product1").value("300.0"))
                .andExpect(jsonPath("$.Product2").value("400.0"));
    }

    private void initBase() {

        salesDataJpaRepository.save(
                SalesDataEntity.builder()
                        .id(1)
                        .trackingId("ABB001")
                        .visitDate(LocalDateTime.parse("2025-08-01T10:00:00"))
                        .saleDate(LocalDateTime.parse("2025-08-02T12:00:00"))
                        .salePrice(BigDecimal.valueOf(100))
                        .product("Product1")
                        .commissionAmount(BigDecimal.valueOf(10.5))
                        .build()
        );

        salesDataJpaRepository.save(
                SalesDataEntity.builder()
                        .id(2)
                        .trackingId("ABB001")
                        .visitDate(LocalDateTime.parse("2025-08-01T10:00:00"))
                        .saleDate(LocalDateTime.parse("2025-08-02T12:00:00"))
                        .salePrice(BigDecimal.valueOf(100))
                        .product("Product1")
                        .commissionAmount(BigDecimal.valueOf(10.5))
                        .build()
        );

        salesDataJpaRepository.save(
                SalesDataEntity.builder()
                        .id(3)
                        .trackingId("ABB001")
                        .visitDate(LocalDateTime.parse("2025-08-01T10:00:00"))
                        .saleDate(LocalDateTime.parse("2025-08-02T12:00:00"))
                        .salePrice(BigDecimal.valueOf(100))
                        .product("Product1")
                        .commissionAmount(BigDecimal.valueOf(10.5))
                        .build()
        );

        salesDataJpaRepository.save(
                SalesDataEntity.builder()
                        .id(4)
                        .trackingId("ABB002")
                        .visitDate(LocalDateTime.parse("2025-08-01T11:00:00"))
                        .saleDate(LocalDateTime.parse("2025-08-03T12:00:00"))
                        .salePrice(BigDecimal.valueOf(200))
                        .product("Product2")
                        .commissionAmount(BigDecimal.valueOf(5))
                        .build()
        );

        salesDataJpaRepository.save(
                SalesDataEntity.builder()
                        .id(5)
                        .trackingId("ABB002")
                        .visitDate(LocalDateTime.parse("2025-08-01T11:00:00"))
                        .saleDate(LocalDateTime.parse("2025-08-03T12:00:00"))
                        .salePrice(BigDecimal.valueOf(200))
                        .product("Product2")
                        .commissionAmount(BigDecimal.valueOf(5))
                        .build()
        );
    }
}
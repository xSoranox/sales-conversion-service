package com.example.sales.controller;


import com.example.sales.enums.CodeEnum;
import com.example.sales.service.CommissionService;
import com.example.sales.service.ConversionService;
import com.example.sales.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConversionController {

    private final ConversionService conversionService;
    private final CommissionService commissionService;
    private final ProductService productService;

    @GetMapping("/conversion-rate")
    @ResponseBody
    public ResponseEntity<Long> getConversionRate(
            @RequestParam CodeEnum code,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime saleDate) {
        log.info("Request conversion rate for code={}, start={}, end={}", code, visitDate, saleDate);

        return ResponseEntity.ok(conversionService.getConversionByCodeAndData(code, visitDate, saleDate));
    }

    @GetMapping("/total-commission")
    @ResponseBody
    public ResponseEntity<BigDecimal> getTotalCommission(
            @RequestParam CodeEnum code,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime saleDate) {
        log.info("total commission for code={}, start={}, end={}", code, visitDate, saleDate);

        return ResponseEntity.ok(commissionService.getTotalCommission(code, visitDate, saleDate));
    }

    @GetMapping("/product")
    @ResponseBody
    public ResponseEntity<Map<String, BigDecimal>> getProduct(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime visitDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime saleDate) {
        log.info("product for start={}, end={}", visitDate, saleDate);

        return ResponseEntity.ok(productService.getProduct(visitDate, saleDate));
    }
}

package com.example.sales.service;

import com.example.sales.entity.SalesDataEntity;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final SalesDataValidator salesDataValidator;
    private final SalesDataJpaRepository salesDataJpaRepository;

    public Map<String, BigDecimal> getProduct(LocalDateTime visitDate, LocalDateTime saleDate) {
        List<SalesDataEntity> salesDataEntityList = salesDataJpaRepository.findByVisitDateBetween(visitDate, saleDate);

        return salesDataEntityList.stream()
                .filter(entity -> salesDataValidator.hasPositiveSalePrice(entity.getSalePrice()))
                .collect(Collectors.groupingBy(
                        SalesDataEntity::getProduct,
                        Collectors.mapping(
                                SalesDataEntity::getSalePrice,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }
}

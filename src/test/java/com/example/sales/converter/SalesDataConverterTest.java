package com.example.sales.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.sales.dto.SalesDataDto;
import com.example.sales.entity.SalesDataEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SalesDataConverterTest {

    private final SalesDataConverter converter = new SalesDataConverter();

    @Test
    void convertSalesDataDtoToEntity_shouldMapAllFields() {
        SalesDataDto dto = new SalesDataDto();
        dto.setId(123);
        dto.setTrackingId("TRK123");
        dto.setVisitDate(LocalDateTime.of(2023, 8, 9, 12, 0));
        dto.setSaleDate(LocalDateTime.of(2023, 8, 10, 15, 30));
        dto.setSalePrice(new BigDecimal("100.50"));
        dto.setProduct("ProductX");
        dto.setCommissionAmount(new BigDecimal("15.75"));

        SalesDataEntity entity = converter.convertSalesDataDtoToEntity(dto);

        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getTrackingId()).isEqualTo(dto.getTrackingId());
        assertThat(entity.getVisitDate()).isEqualTo(dto.getVisitDate());
        assertThat(entity.getSaleDate()).isEqualTo(dto.getSaleDate());
        assertThat(entity.getSalePrice()).isEqualTo(dto.getSalePrice());
        assertThat(entity.getProduct()).isEqualTo(dto.getProduct());
        assertThat(entity.getCommissionAmount()).isEqualTo(dto.getCommissionAmount());
    }

    @Test
    void convertSalesDataDtoToEntityList_shouldConvertListCorrectly() {
        SalesDataDto dto1 = new SalesDataDto();
        dto1.setId(1);
        SalesDataDto dto2 = new SalesDataDto();
        dto2.setId(2);

        List<SalesDataDto> dtos = List.of(dto1, dto2);

        List<SalesDataEntity> entities = converter.convertSalesDataDtoToEntityList(dtos);

        assertThat(entities).hasSize(2);
        assertThat(entities.get(0).getId()).isEqualTo(1);
        assertThat(entities.get(1).getId()).isEqualTo(2);
    }
}
package com.example.sales.sheduler;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.sales.converter.SalesDataConverter;
import com.example.sales.dto.SalesDataDto;
import com.example.sales.entity.SalesDataEntity;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SalesDataJpaRepository salesDataJpaRepository;

    @Mock
    private SalesDataConverter salesDataConverter;

    @Mock
    private SalesDataValidator salesDataValidator;

    @InjectMocks
    private SchedulerService schedulerService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(schedulerService, "junoBaseUrl", "http://localhost:8081");
    }

    @Test
    void pollNewData_fetchesAndSavesSalesData() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        SalesDataDto dto = new SalesDataDto();
        dto.setTrackingId("someTrackingId");
        SalesDataDto[] responseArray = new SalesDataDto[] { dto };
        ResponseEntity<SalesDataDto[]> responseEntity = ResponseEntity.ok(responseArray);

        when(restTemplate.getForEntity(anyString(), eq(SalesDataDto[].class))).thenReturn(responseEntity);

        SalesDataEntity entity = new SalesDataEntity();
        List<SalesDataEntity> entityList = List.of(entity);
        when(salesDataConverter.convertSalesDataDtoToEntityList(anyList())).thenReturn(entityList);

        when(salesDataJpaRepository.saveAll(entityList)).thenReturn(entityList);

        schedulerService.pollNewData();

        verify(restTemplate).getForEntity(contains(yesterday.toString()), eq(SalesDataDto[].class));

        verify(salesDataJpaRepository).saveAll(entityList);
    }
}
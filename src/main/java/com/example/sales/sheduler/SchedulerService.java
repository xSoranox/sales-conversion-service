package com.example.sales.sheduler;

import com.example.sales.converter.SalesDataConverter;
import com.example.sales.dto.SalesDataDto;
import com.example.sales.entity.SalesDataEntity;
import com.example.sales.enums.AbbEnum;
import com.example.sales.enums.EkwEnum;
import com.example.sales.enums.TbsEnum;
import com.example.sales.repository.SalesDataJpaRepository;
import com.example.sales.util.SalesDataValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {

    private final SalesDataValidator salesDataValidator;
    private final RestTemplate restTemplate;
    private final SalesDataJpaRepository salesDataJpaRepository;
    private final SalesDataConverter salesDataConverter;
    @Value("${juno.api.base-url}")
    private String junoBaseUrl;

    @Scheduled(fixedRate = 300000)
    public void pollNewData() {
        LocalDate today = LocalDate.now();
        fetchAndSaveSalesData(today.minusDays(1), today);
    }

    private void fetchAndSaveSalesData(LocalDate fromDate, LocalDate toDate) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(junoBaseUrl + "/sales-data")
                .queryParam("fromDate", fromDate.toString())
                .queryParam("toDate", toDate.toString());

        log.info("Fetching sales data from {} to {}", fromDate, toDate);

        ResponseEntity<SalesDataDto[]> response = restTemplate.getForEntity(uriBuilder.toUriString(), SalesDataDto[].class);
        SalesDataDto[] salesData = response.getBody();

        if (salesData != null) {
            List<SalesDataDto> salesDataList = new ArrayList<>(Arrays.asList(salesData));

            Set<String> filteredSet = addFilter();

            List<SalesDataDto> filteredDtoList = salesDataList.stream()
                    .filter(dto -> filteredSet.contains(dto.getTrackingId()))
                    .peek(salesDataValidator::validate)
                    .collect(Collectors.toList());

            List<SalesDataEntity> filteredEntityList = salesDataConverter.convertSalesDataDtoToEntityList(filteredDtoList);

            salesDataJpaRepository.saveAll(filteredEntityList);

            log.info("saved items: {} ", filteredEntityList.size());

        } else {
            log.warn("Received empty sales data from API");
        }
    }

    private Set<String> addFilter() {
        return Stream.of(
                        Arrays.stream(AbbEnum.values()).map(AbbEnum::getTrackingId),
                        Arrays.stream(EkwEnum.values()).map(EkwEnum::getTrackingId),
                        Arrays.stream(TbsEnum.values()).map(TbsEnum::getTrackingId)
                )
                .flatMap(s -> s)
                .collect(Collectors.toSet());
    }
}
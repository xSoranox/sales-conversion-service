package com.example.sales.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public enum CodeEnum {
    ABB(AbbEnum.values()),
    EKW(EkwEnum.values()),
    TBS(EkwEnum.values());

    private final Enum<?>[] values;

    CodeEnum(Enum<?>[] values) {
        this.values = values;
    }

    public List<String> getTrackingIdStringList() {
        return Arrays.stream(values)
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}

package com.example.sales.enums;

public enum EkwEnum {

    EKW001("EKW001"),
    EKW002("EKW002"),
    EKW003("EKW003"),
    EKW004("EKW004"),
    EKW005("EKW005");

    private final String trackingId;

    EkwEnum(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTrackingId() {
        return trackingId;
    }
}

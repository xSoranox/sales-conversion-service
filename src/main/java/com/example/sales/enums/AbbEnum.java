package com.example.sales.enums;

public enum AbbEnum {

    ABB001("ABB001"),
    ABB002("ABB002"),
    ABB003("ABB003"),
    ABB004("ABB004"),
    ABB005("ABB005");


    private final String trackingId;

    AbbEnum(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTrackingId() {
        return trackingId;
    }
}

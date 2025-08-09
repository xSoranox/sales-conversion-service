package com.example.sales.enums;

public enum TbsEnum {

    TBS001("TBS001"),
    TBS002("TBS002"),
    TBS003("TBS003"),
    TBS004("TBS004"),
    TBS005("TBS005");

    private final String trackingId;

    TbsEnum(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTrackingId() {
        return trackingId;
    }
}

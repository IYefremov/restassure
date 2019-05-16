package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum InspectionStatus {

    APPROVED("Approved"),
    NEW("New"),
    DECLINED("Declined"),
    SKIPPED("Skipped");;

    private final String status;

    private InspectionStatus(final String statusValue) {
        this.status = statusValue;
    }

    public String getStatus() {
        return status;
    }

    public static InspectionStatus getStatus(final String statusValue) {
        if (statusValue != null) {
            for (InspectionStatus value : InspectionStatus.values()) {
                if (statusValue.equalsIgnoreCase(value.status)) {
                    return value;
                }
            }
        }
        return null;
    }
}

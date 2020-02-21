package com.cyberiansoft.test.dataclasses;

public enum InspectionStatus {

    APPROVED("Approved"),
    ARCHIVED("Archived"),
    NEW("New"),
    DECLINED("Declined"),
    DRAFT("Draft"),
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

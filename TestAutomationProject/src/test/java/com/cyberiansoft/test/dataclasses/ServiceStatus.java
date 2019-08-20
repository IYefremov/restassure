package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ServiceStatus {

    APPROVED("Approved"),
    NEW("New"),
    DECLINED("Declined"),
    SKIPPED("Skipped"),
    PROBLEM("PROBLEM"),
    ACTIVE("ACTIVE"),
    STARTED("STARTED"),
    ORDERED("Ordered"),
    RECEIVED("Received"),
    REFUSED("Refused"),
    COMPLETED("COMPLETED");


    private final String status;

    ServiceStatus(final String statusValue) {
        this.status = statusValue;
    }

    @JsonProperty("status1")
    public String getStatus() {
        return status;
    }

    public static ServiceStatus getStatus(final String statusValue) {
        if (statusValue != null) {
            for (ServiceStatus value : ServiceStatus.values()) {
                if (statusValue.equalsIgnoreCase(value.status)) {
                    return value;
                }
            }
        }
        return null;
    }

}

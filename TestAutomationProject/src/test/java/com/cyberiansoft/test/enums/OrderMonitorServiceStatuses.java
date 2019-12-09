package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum OrderMonitorServiceStatuses {

    COMPLETED("Completed"),
    ACTIVE("Active"),
    RECEIVED("Received"),
    REORDERED("Re-Ordered"),
    ORDERED("Ordered"),
    SKIPPED("Skipped"),
    PROBLEM("Problem"),
    AUDITED("Audited"),
    REFUSED("Refused"),
    REWORK("Rework"),
    QUEUED("Queued");

    private String value;

    OrderMonitorServiceStatuses(String value) {
        this.value = value;
    }
}

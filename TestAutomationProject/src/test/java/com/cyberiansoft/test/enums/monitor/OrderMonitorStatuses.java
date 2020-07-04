package com.cyberiansoft.test.enums.monitor;

import lombok.Getter;

@Getter
public enum OrderMonitorStatuses {

    COMPLETED("Completed"),
    ACTIVE("Active"),
    QUEUED("Queued"),
    SKIPPED("Skipped"),
    RUNNING("Running");

    private String value;

    OrderMonitorStatuses(String value) {
        this.value = value;
    }
}

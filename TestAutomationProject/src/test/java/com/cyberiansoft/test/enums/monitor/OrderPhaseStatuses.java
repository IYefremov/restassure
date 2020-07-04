package com.cyberiansoft.test.enums.monitor;

import lombok.Getter;

@Getter
public enum OrderPhaseStatuses {

    COMPLETED("Completed"),
    ACTIVE("Active"),
    SKIPPED("Skipped"),
    REFUSED("Refused");

    private String value;

    OrderPhaseStatuses(String value) {
        this.value = value;
    }
}

package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum PhaseName {
    COMPLETED("COMPLETED"),
    PROBLEM("PROBLEM"),
    ACTIVE("ACTIVE");

    @Getter
    private String value;

    PhaseName(String statusString) {
        this.value = statusString;
    }
}

package com.cyberiansoft.test.vnext.enums.partservice;

import lombok.Getter;

public enum PdrSeverity {
    LIGHT("LIGHT"),
    HEAVY("HEAVY");

    @Getter
    private String value;

    PdrSeverity(String statusString) {
        this.value = statusString;
    }
}

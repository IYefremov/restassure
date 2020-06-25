package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum ServiceOrTaskStatus {
    ACTIVE("Active"),
    AUDITED("Audited"),
    REFUSED("Refused"),
    RECEIVED("Received");

    @Getter
    private String statusName;

    ServiceOrTaskStatus(String statusName) {
        this.statusName = statusName;
    }
}

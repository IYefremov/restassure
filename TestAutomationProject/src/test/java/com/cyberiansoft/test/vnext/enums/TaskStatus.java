package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum TaskStatus {
    ACTIVE("Active"),
    AUDITED("Audited"),
    REFUSED("Refused");

    @Getter
    private String statusName;

    TaskStatus(String statusName) {
        this.statusName = statusName;
    }
}

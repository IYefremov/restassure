package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum WorkOrderStatuses {

    ON_HOLD("On Hold"),
    APPROVED("Approved"),
    NEW("New");

    private String value;

    WorkOrderStatuses(String value) {
        this.value = value;
    }
}

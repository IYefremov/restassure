package com.cyberiansoft.test.enums.servicerequests;

import lombok.Getter;

@Getter
public enum SRPhase {

    ACCEPTANCE("Acceptance"),
    ESTIMATING("Estimating"),
    WORK("Work"),
    DELIVERY("Delivery");

    private String value;

    SRPhase(String value) {
        this.value = value;
    }
}

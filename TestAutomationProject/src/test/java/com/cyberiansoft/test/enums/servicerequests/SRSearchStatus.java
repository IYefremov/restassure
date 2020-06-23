package com.cyberiansoft.test.enums.servicerequests;

import lombok.Getter;

@Getter
public enum SRSearchStatus {

    WAITING_FOR_ACCEPTANCE("Waiting For Acceptance");

    private String value;

    SRSearchStatus(String value) {
        this.value = value;
    }
}

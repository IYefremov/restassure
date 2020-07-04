package com.cyberiansoft.test.enums.servicerequests;

import lombok.Getter;

@Getter
public enum ServiceRequestStatus {

    ON_HOLD("On Hold"),
    SCHEDULED("Scheduled"),
    PROPOSED("Proposed"),
    REJECTED("Rejected"),
    CLOSED("Closed");

    private String value;

    ServiceRequestStatus(String value) {
        this.value = value;
    }

}

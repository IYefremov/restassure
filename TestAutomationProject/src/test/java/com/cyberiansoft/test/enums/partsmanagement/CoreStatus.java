package com.cyberiansoft.test.enums.partsmanagement;

import lombok.Getter;

@Getter
public enum CoreStatus {

    NA("N/A"),
    RETURN_TO_VENDOR("Return to Vendor"),
    RTV_COMPLETE("RTV Complete");

    private String status;

    CoreStatus(final String status) {
        this.status = status;
    }
}

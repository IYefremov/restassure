package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum PartStatuses {

    ALL("All"),
    OPEN("Open"),
    ON_HOLD("On Hold"),
    QUOTE_RECEIVED("Quote Received"),
    ORDERED("Ordered"),
    RE_ORDERED("Re-Ordered"),
    RECEIVED("Received"),
    RTV_COMPLETE("RTV Complete"),
    REFUSED("Refused"),
    AUDITED("Audited"),
    INSTALLED("Installed"),
    PARTIALLY_RECEIVED("Partially Received"),
    OTHER("Other"),
    QUOTE_REQUESTED("Quote Requested");

    private String status;

    PartStatuses(final String status) {
        this.status = status;
    }
}
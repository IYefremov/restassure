package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum PartStatuses {

    ALL("All"),
    OPEN("Open"),
    ON_HOLD("On Hold"),
    RETURN_TO_VENDOR("Return to Vendor (RTV)"),
    QUOTE_RECEIVED("Quote Received"),
    ORDERED("Ordered"),
    BILLED("Billed"),
    PARTIALLY_RECEIVED("Partially Received"),
    OTHER("Other"),
    QUOTE_REQUESTED("Quote Requested"),
    RE_ORDERED("Re-Ordered"),
    RTV_COMPLETE("RTV Complete"),
    RECEIVED("Received"),
    INSTALLED("Installed"),
    REFUSED("Refused"),
    AUDITED("Audited"),
    PROBLEM("Problem");

    private String status;

    PartStatuses(final String status) {
        this.status = status;
    }
}
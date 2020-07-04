package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum InvoiceStatus {

    APPROVED ("Approved"),
    NEW( "New"),
    DRAFT("Draft");

    @Getter
    private String statusString;

    InvoiceStatus(String statusString) {
        this.statusString = statusString;
    }
}

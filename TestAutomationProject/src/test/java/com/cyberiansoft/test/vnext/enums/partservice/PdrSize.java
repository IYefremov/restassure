package com.cyberiansoft.test.vnext.enums.partservice;

import lombok.Getter;

public enum PdrSize {
    DIME("DIME"),
    QTR("QTR");

    @Getter
    private String value;

    PdrSize(String statusString) {
        this.value = statusString;
    }
}

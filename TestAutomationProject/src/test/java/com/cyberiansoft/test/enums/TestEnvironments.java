package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum TestEnvironments {

    QC("QC"),
    DEV("DEV"),
    QC1("QC1"),
    UAT("UAT");

    private String name;

    TestEnvironments(final String name) {
        this.name = name;
    }
}

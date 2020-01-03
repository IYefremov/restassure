package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum OrderMonitorFlags {

    WHITE("White"),
    RED("Important"),
    ORANGE("Low priority"),
    YELLOW("Yellow"),
    GREEN("Green"),
    BLUE("Blue"),
    VIOLET("Test");

    private String flag;

    OrderMonitorFlags(final String flag) {
        this.flag = flag;
    }
}

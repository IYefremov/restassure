package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum OrderPriority {
    ALL("All"),
    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High");

    private String value;

    OrderPriority(String value) {
        this.value = value;
    }
}

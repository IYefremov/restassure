package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum OrderPriority {
    ALL("all"),
    LOW("low"),
    NORMAL("normal"),
    HIGH("high");

    private String value;

    OrderPriority(String value) {
        this.value = value;
    }
}

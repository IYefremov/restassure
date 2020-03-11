package com.cyberiansoft.test.enums.partsmanagement;

import lombok.Getter;

@Getter
public enum PartCondition {

    NEW("New"),
    USED("Used"),
    REMANUFACTURED("Remanufactured"),
    RECONDITIONED("Reconditioned");

    private String value;

    PartCondition(final String value) {
        this.value = value;
    }
}

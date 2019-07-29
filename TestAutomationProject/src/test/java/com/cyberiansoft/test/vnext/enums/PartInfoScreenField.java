package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum PartInfoScreenField {
    CATEGORY("Category"),
    SUB_CATEGORY("SubCategory"),
    PART_NAME("PartName"),
    PART_POSITION("PartPosition"),
    OEM("PartOEM");

    @Getter
    private String value;

    PartInfoScreenField(String statusString) {
        this.value = statusString;
    }
}

package com.cyberiansoft.test.vnext.enums.partservice;

import lombok.Getter;

public enum PartInfoScreenField {
    CATEGORY("Category"),
    SUB_CATEGORY("SubCategory"),
    PART_NAME("PartName"),
    PART_POSITION("PartPosition"),
    OEM("PartOEM"),
    SIZE("Size"),
    SEVERITY("Severity");

    @Getter
    private String value;

    PartInfoScreenField(String statusString) {
        this.value = statusString;
    }
}

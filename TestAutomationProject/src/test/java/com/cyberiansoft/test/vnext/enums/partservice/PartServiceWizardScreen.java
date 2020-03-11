package com.cyberiansoft.test.vnext.enums.partservice;

import lombok.Getter;

public enum PartServiceWizardScreen {
    CATEGORY("Category"),
    SUB_CATEGORY("Sub-Category"),
    PART_NAME("Part Name"),
    PART_POSITION("Part Position"),
    SIZE("Select Size"),
    SEVERITY("Select Severity");

    @Getter
    private String value;

    PartServiceWizardScreen(String statusString) {
        this.value = statusString;
    }
}

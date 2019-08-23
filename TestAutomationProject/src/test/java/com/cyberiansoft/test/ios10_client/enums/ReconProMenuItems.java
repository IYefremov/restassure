package com.cyberiansoft.test.ios10_client.enums;

import lombok.Getter;

public enum ReconProMenuItems {

    PAY("Pay"),
    CREATE_WORKORDER("Create\nWO"),
    CREATE_INPECTION("Create\nInspection"),
    DETAILS("Details"),
    SUMMARY("Summary");

    @Getter
    private String menuItemName;

    ReconProMenuItems(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}

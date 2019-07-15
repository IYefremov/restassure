package com.cyberiansoft.test.vnextbo.enums;

import lombok.Getter;

@Getter
public enum MainMenuItems {
    MONITOR("Monitor"),
    OPERATIONS("Operations"),
    SETTINGS("Settings"),
    QUICK_BOOKS_INTEGRATION("QuickBooks Integration"),
    ADD_ONS("Add-ons");

    private String menu;

    MainMenuItems(String menu) {
        this.menu = menu;
    }
}
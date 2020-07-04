package com.cyberiansoft.test.vnextbo.enums;

import lombok.Getter;

@Getter
public enum MainMenuItems {
    OPERATIONS("Operations"),
    SETTINGS("Settings"),
    MONITOR("Monitor"),
    REPORTS("Reports"),
    QUICK_BOOKS_INTEGRATION("QuickBooks Integration"),
    ADD_ONS("Add-ons");

    private String menu;

    MainMenuItems(String menu) {
        this.menu = menu;
    }
}
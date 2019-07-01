package com.cyberiansoft.test.enums;

import lombok.Getter;

public enum MenuItems {
    EDIT("Edit"),
    ASSIGN_TECH(""),
    START("Start"),
    STOP("Stop"),
    COMPLETE("Complete"),
    CHANGE_STATUS("Change Status"),
    NOTES("Notes"),
    CHANGE_DEPARTMENT(""),
    REPORT_PROBLEM("Report Problem"),
    RESOLVE_PROBLEM("Resolve Problem"),
    FOCUS_MODE("Focus mode");

    @Getter
    private String menuItemDataName;

    MenuItems(String menuItemDataName) {
        this.menuItemDataName = menuItemDataName;
    }
}

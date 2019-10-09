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
    FOCUS_MODE_ON("TURN OFF\nFocus mode"),
    FOCUS_MODE_OFF("TURN ON\nFocus mode"),
    TIME_REPORT("Time Report"),
    RESET_START_DATE("Reset Start Date");

    @Getter
    private String menuItemDataName;

    MenuItems(String menuItemDataName) {
        this.menuItemDataName = menuItemDataName;
    }
}

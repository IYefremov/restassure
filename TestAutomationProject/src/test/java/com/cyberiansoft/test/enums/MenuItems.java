package com.cyberiansoft.test.enums;

import lombok.Getter;

public enum MenuItems {
    EDIT("edit"),
    ASSIGN_TECH(""),
    START("start"),
    COMPLETE("complete"),
    CHANGE_STATUS(""),
    NOTES("notes"),
    CHANGE_DEPARTMENT(""),
    REPORT_PROBLEM("reportProblem"),
    RESOLVE_PROBLEM("resolveProblem");

    @Getter
    private String menuItemDataName;

    MenuItems(String menuItemDataName) {
        this.menuItemDataName = menuItemDataName;
    }
}

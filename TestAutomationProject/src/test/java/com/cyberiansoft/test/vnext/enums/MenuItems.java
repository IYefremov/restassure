package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum MenuItems {
    EDIT("edit"),
    ASSIGN_TECH(""),
    START("start"),
    COMPLETE("complete"),
    CHANGE_STATUS(""),
    NOTES(""),
    CHANGE_DEPARTMENT("");

    @Getter
    private String menuItemDataName;

    MenuItems(String menuItemDataName) {
        this.menuItemDataName = menuItemDataName;
    }
}

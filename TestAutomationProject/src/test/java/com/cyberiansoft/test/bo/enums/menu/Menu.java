package com.cyberiansoft.test.bo.enums.menu;

import lombok.Getter;

@Getter
public enum Menu {

    SUPER_USER("Super User"),
    OPERATIONS("Operations"),
    MONITOR("Monitor"),
    COMPANY("Company"),
    MISCELLANEOUS("Miscellaneous"),
    REPORTS("Reports"),
    TIMESHEETS("Timesheets"),
    FAVORITES("Favorites");

    private String value;

    Menu(String value) {
        this.value = value;
    }
}

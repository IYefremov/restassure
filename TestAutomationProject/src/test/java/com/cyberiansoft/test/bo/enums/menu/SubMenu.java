package com.cyberiansoft.test.bo.enums.menu;

import lombok.Getter;

@Getter
public enum SubMenu {

    //SUPER USER
    APPLICATIONS("Applications"),
    SUBSCRIBE("Subscribe"),

    //COMPANY
    TEAMS("Teams"),
    USERS("Users"),
    CLIENTS("Clients"),
    COMPANY_INFO("Company Info");

    final private String value;

    SubMenu(String value) {
        this.value = value;
    }
}

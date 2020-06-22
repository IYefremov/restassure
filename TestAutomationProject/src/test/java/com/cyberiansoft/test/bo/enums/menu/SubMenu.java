package com.cyberiansoft.test.bo.enums.menu;

import lombok.Getter;

@Getter
public enum SubMenu {

    //SUPER USER
    APPLICATIONS("Applications"),
    SUBSCRIBE("Subscribe"),

    //OPERATIONS
    SERVICE_REQUESTS_LIST("Service Requests List"),

    //MONITOR
    REPAIR_ORDERS("Repair Orders"),

    //COMPANY
    TEAMS("Teams"),
    USERS("Users"),
    CLIENTS("Clients"),
    WORK_ORDER_TYPES("Work Order Types"),
    REPAIR_LOCATIONS("Repair Locations"),
    COMPANY_INFO("Company Info");

    final private String value;

    SubMenu(String value) {
        this.value = value;
    }
}

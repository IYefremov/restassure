package com.cyberiansoft.test.enums;

import lombok.Getter;

public enum MenuItems {
    EDIT("Edit"),
    SAVE("Save Inspection"),
    CANCEL("Cancel Inspection"),
    EMAIL("Email Inspection"),
    APPROVE("Approve"),
    VIEW("View"),
    ARCHIVE("Archive"),
    CHANGE_CUSTOMER("Change Customer"),
    SET_AS_DEFAULT("Set as Default"),
    ASSIGN_TECH("Assign Tech"),
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
    PAY("Pay"),
    PAY_CREDIT_CARD("Pay Credit Card"),
    PAY_CASH_CHECK("Pay Cash/Check"),
    PAY_PO_RO("Pay PO/RO"),
    SUPPLEMENT("Supplement"),
    VIEW_SUPPLEMENT("View Supplement"),
    ADD_SUPPLEMENT("Add Supplement"),
    CREATE_WORK_ORDER("Create Work Order"),
    CHANGE_PO("Change PO#"),
    RESET_START_DATE("Reset Start Date");


    @Getter
    private String menuItemDataName;

    MenuItems(String menuItemDataName) {
        this.menuItemDataName = menuItemDataName;
    }
}

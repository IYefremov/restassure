package com.cyberiansoft.test.enums;

import lombok.Getter;

public enum MenuItems {
    EDIT("Edit"),
    DELETE("Delete"),
    SAVE_INPSECTION("Save Inspection"),
    CANCEL_INPSECTION("Cancel Inspection"),
    CREATE_INPSECTION("Create Inspection"),
    CANCEL_INVOICE("Cancel Invoice"),
    EMAIL_INPSECTION("Email Inspection"),
    EMAIL_INVOICE("Email Invoice"),
    EMAIL_SUPPLEMENT("Email Supplement"),
    APPROVE("Approve"),
    VIEW("View"),
    VOID("Void"),
    ARCHIVE("Archive"),
    CHANGE_CUSTOMER("Change Customer"),
    SET_AS_DEFAULT("Set as Default"),
    CUSTOMER_CONTACTS("Customer Contacts"),
    ASSIGN_TECH("Assign Tech"),
    START("Start"),
    START_RO("Start RO"),
    STOP("Stop"),
    SCAN("Scan"),
    COMPLETE("Complete"),
    CHANGE_STATUS("Change Status"),
    NOTES("Notes"),
    REFRESH_PICTURES("Refresh Pictures"),
    CHANGE_DEPARTMENT("Change Department"),
    REPORT_PROBLEM("Report Problem"),
    RESOLVE_PROBLEM("Resolve Problem"),
    FOCUS_MODE_ON("TURN OFF\nFocus mode"),
    FOCUS_MODE_OFF("TURN ON\nFocus mode"),
    FOCUS_MODE("Focus mode"),
    TIME_REPORT("Time Report"),
    HOME("Home"),
    PAY("Pay"),
    PAY_CREDIT_CARD("Pay Credit Card"),
    PAY_CASH_CHECK("Pay Cash/Check"),
    PAY_PO_RO("Pay PO/RO"),
    SUPPLEMENT("Supplement"),
    VIEW_SUPPLEMENT("View Supplement"),
    ADD_SUPPLEMENT("Add Supplement"),
    CREATE_WORK_ORDER("Create Work Order"),
    CREATE_INVOICE("Create Invoice"),
    CHANGE_PO("Change PO#"),
    RESET_START_DATE("Reset Start Date");

    @Getter
    private String menuItemDataName;

    MenuItems(String menuItemDataName) {
        this.menuItemDataName = menuItemDataName;
    }
}

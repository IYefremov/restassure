package com.cyberiansoft.test.ios10_client.enums;

import lombok.Getter;

public enum ReconProMenuItems {

    PAY("Pay"),
    COPY("Copy"),
    CLOSE("Close"),
    EDIT("Edit"),
    CREATE_WORKORDER("Create\nWO"),
    CREATE_WORKORDER_HD("Create Work Order"),
    CREATE_INPECTION("Create\nInspection"),
    CREATE_SERVICE_REQUEST("Create\nSR"),
    CHANGE_PO("Change\nPO#"),
    DETAILS("Details"),
    DELETE("Delete"),
    COPY_VEHICLE("Copy\nVehicle"),
    COPY_SERVICES("Copy\nServices"),
    SEND_EMAIL("Send\nEmail"),
    NEW_INSPECTION("New\nInspection"),
    CHECK_IN("Check In"),
    CHANGE_STATUS("Change\nStatus"),
    UNDO_CHECK_IN("Undo\nCheck In"),
    NOTES("Notes"),
    REFRESH_PICTURES("Refresh\nPictures"),
    SHOW_PICTURES("Show\nPictures"),
    APPROVE("Approve"),
    APPOINTMENTS("Appointments"),
    CHANGE_CUSTOMER("Change\nCustomer"),
    SHOW_WOS("Show\nWOs"),
    ASSIGN("Assign"),
    ARCHIVE("Archive"),
    VOID("Void"),
    MONITOR("Monitor"),
    SUMMARY("Summary");

    @Getter
    private String menuItemName;

    ReconProMenuItems(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}

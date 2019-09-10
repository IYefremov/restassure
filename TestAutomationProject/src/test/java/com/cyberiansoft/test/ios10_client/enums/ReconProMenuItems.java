package com.cyberiansoft.test.ios10_client.enums;

import lombok.Getter;

public enum ReconProMenuItems {

    PAY("Pay"),
    COPY("Copy"),
    EDIT("Edit"),
    CREATE_WORKORDER("Create\nWO"),
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
    APPROVE("Approve"),
    CHANGE_CUSTOMER("Change\nCustomer"),
    SHOW_WOS("Show\nWOs"),
    ASSIGN("Assign"),
    ARCHIVE("Archive"),
    SUMMARY("Summary");

    @Getter
    private String menuItemName;

    ReconProMenuItems(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}

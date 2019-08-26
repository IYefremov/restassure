package com.cyberiansoft.test.ios10_client.enums;

import lombok.Getter;

public enum ReconProMenuItems {

    PAY("Pay"),
    CREATE_WORKORDER("Create\nWO"),
    CREATE_INPECTION("Create\nInspection"),
    DETAILS("Details"),
    COPY_VEHICLE("Copy\nVehicle"),
    COPY_SERVICES("Copy\nServices"),
    SEND_EMAIL("Send\nEmail"),
    CHECK_IN("Check In"),
    UNDO_CHECK_IN("Undo\nCheck In"),
    SUMMARY("Summary");

    @Getter
    private String menuItemName;

    ReconProMenuItems(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}

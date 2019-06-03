package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum RepairOrderStatus {
    All("All"),
    COMPLETED_ALL("Completed - All");

    @Getter
    private String statusString;

    RepairOrderStatus(String statusString) {
        this.statusString = statusString;
    }
}

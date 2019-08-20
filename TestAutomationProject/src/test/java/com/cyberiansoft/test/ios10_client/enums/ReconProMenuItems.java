package com.cyberiansoft.test.ios10_client.enums;

import lombok.Getter;

public enum ReconProMenuItems {

    PAY("Pay");

    @Getter
    private String menuItemName;

    ReconProMenuItems(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}

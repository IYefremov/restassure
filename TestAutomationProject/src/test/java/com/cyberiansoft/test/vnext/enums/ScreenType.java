package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum ScreenType {
    SERVICES("Services"),
    CLAIM("Claim"),
    VEHICLE_INFO("Vehicle Info");

    @Getter
    private String screenIndentificator;

    ScreenType(String screenIndentificator) {
        this.screenIndentificator = screenIndentificator;
    }
}

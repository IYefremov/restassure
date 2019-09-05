package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum ScreenType {
    SERVICES("Services"),
    CLAIM("Claim"),
    WORKORDER_SUMMARY("Summary"),
    VISUAL("Visual"),
    VEHICLE_INFO("Vehicle Info"),
    QUESTIONS("Questions"),
    ;

    @Getter
    private String screenIdentificator;

    ScreenType(String screenIdentificator) {
        this.screenIdentificator = screenIdentificator;
    }
}

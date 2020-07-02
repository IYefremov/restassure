package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum RepairOrderBackGroundColors {

    GREEN("rgba(98, 190, 33, 0.1)");

    @Getter
    private String backGroundColor;

    RepairOrderBackGroundColors(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }
}

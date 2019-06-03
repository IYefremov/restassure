package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

import java.util.Arrays;

public enum RepairOrderFlag {
    WHITE("white-flag"),
    RED("red-flag"),
    YELLOW("yellow-flag"),
    GREEN("green-flag");

    @Getter
    private String flagData;

    RepairOrderFlag(String flagData) {
        this.flagData = flagData;
    }

    public static RepairOrderFlag getFlagFromString(String flagStringValue) {
        return Arrays.stream(RepairOrderFlag.values())
                .filter(repairOrderFlag -> repairOrderFlag.getFlagData().equals(flagStringValue))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Repair order flag not found " + flagStringValue));
    }
}

package com.cyberiansoft.test.vnext.enums;

import lombok.Getter;

public enum MonitorRole {
    EMPLOYEE("Employee"),
    INSPECTOR("Inspector"),
    MANAGER("Manager");

    @Getter
    private String roleName;

    MonitorRole(String monitorRole) {
        this.roleName = monitorRole;
    }
}

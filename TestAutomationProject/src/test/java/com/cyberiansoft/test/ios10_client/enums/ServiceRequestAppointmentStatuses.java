package com.cyberiansoft.test.ios10_client.enums;

import lombok.Getter;

public enum  ServiceRequestAppointmentStatuses {

    SCHEDULED("SCHEDULED"),
    COMPLETED("COMPLETED");

    @Getter
    private String appointmentStatus;

    ServiceRequestAppointmentStatuses(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }
}

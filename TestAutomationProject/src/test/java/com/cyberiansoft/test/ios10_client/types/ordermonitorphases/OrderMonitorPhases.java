package com.cyberiansoft.test.ios10_client.types.ordermonitorphases;

public enum OrderMonitorPhases {
    QUEUED("Queued"),
    COMPLETED("Completed"),
    SKIPPED("Skipped"),
    ACTIVE("Active");

    private final String phases;

    OrderMonitorPhases(final String phaseName) {
        this.phases = phaseName;
    }

    public String getrderMonitorPhaseName() {
        return phases;
    }
}

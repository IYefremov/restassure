package com.cyberiansoft.test.dataclasses;

public enum WorkOrderStatuses {

    NEW("New"),
    APPROVED("Approved"),
    DRAFT("Draft");

    private final String woStatus;

    WorkOrderStatuses(final String workOrderStatus) {
        this.woStatus = workOrderStatus;
    }

    public String getWorkOrderStatusValue() {
        return woStatus;
    }
}

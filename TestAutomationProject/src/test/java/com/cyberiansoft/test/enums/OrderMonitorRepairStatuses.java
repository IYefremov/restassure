package com.cyberiansoft.test.enums;

import lombok.Getter;

@Getter
public enum OrderMonitorRepairStatuses {

    All("All"),
    IN_PROGRESS_ALL("In Progress - All"),
    IN_PROGRESS_ACTIVE("In Progress - Active"),
    IN_PROGRESS_QUEUED("In Progress - Queued"),
    IN_PROGRESS_REWORK("In Progress - Rework"),
    IN_PROGRESS_ON_HOLD("In Progress - On Hold"),
    IN_PROGRESS_NOT_ON_HOLD("In Progress - Not On Hold"),
    IN_PROGRESS_QA_READY("In Progress - QA Ready"),
    COMPLETED_QA_READY("Completed - QA Ready"),
    COMPLETED_NOT_BILLED("Completed - Not Billed"),
    COMPLETED_ON_SITE("Completed - On Site"),
    COMPLETED_CLOSED("Completed - Closed"),
    COMPLETED_ALL("Completed - All");

    private String value;

    OrderMonitorRepairStatuses(String value) {
        this.value = value;
    }
}

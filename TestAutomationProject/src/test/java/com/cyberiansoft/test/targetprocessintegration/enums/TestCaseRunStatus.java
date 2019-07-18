package com.cyberiansoft.test.targetprocessintegration.enums;

import lombok.Getter;

public enum TestCaseRunStatus {

    PASSED("Passed"),
    FAILED("Failed"),
    ON_HOLD("On Hold"),
    BLOCKED("Blocked"),
    NOT_RUN("Not Run");


    @Getter
    private String runStatus;

    TestCaseRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }
}

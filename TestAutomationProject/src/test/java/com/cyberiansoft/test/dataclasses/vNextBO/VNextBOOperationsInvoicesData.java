package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOOperationsInvoicesData {

    @JsonProperty("status")
    private String status;

    @JsonProperty("status2")
    private String status2;

    public String getStatus() {
        return status;
    }

    public String getStatus2() {
        return status2;
    }
}
package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOOperationsInvoicesData {

    @JsonProperty("status")
    private String status;

    public String getStatus() {
        return status;
    }
}
package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOOperationsInvoicesData {

    @JsonProperty("status")
    private String status;

    @JsonProperty("status2")
    private String status2;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("fromDate")
    private String fromDate;
}
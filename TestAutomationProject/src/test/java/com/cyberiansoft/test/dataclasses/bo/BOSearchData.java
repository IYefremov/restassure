package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BOSearchData {

    @JsonProperty("packageValue")
    private String packageValue;

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("timeFrame")
    private String timeFrame;

    @JsonProperty("dateFrom")
    private String dateFrom;

    @JsonProperty("dateTo")
    private String dateTo;
}
package com.cyberiansoft.test.dataclasses.vNextBO.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOReportsData {

    @JsonProperty("report")
    private String report;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("team")
    private String team;

    @JsonProperty("employee")
    private String employee;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("invalidEmails")
    private String[] invalidEmails;

    @JsonProperty("invalidPhones")
    private String[] invalidPhones;
}

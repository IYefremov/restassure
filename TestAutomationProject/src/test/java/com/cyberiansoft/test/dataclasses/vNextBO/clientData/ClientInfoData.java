package com.cyberiansoft.test.dataclasses.vNextBO.clientData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ClientInfoData {

    @JsonProperty("clientType")
    private String clientType;

    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;
}
package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientsData {

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("clientType")
    private String clientType;

    public String getClientName() {
        return clientName;
    }

    public String getClientType() {
        return clientType;
    }
}

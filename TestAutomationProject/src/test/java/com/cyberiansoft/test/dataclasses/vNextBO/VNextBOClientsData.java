package com.cyberiansoft.test.dataclasses.vNextBO;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AccountInfoData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.AddressData;
import com.cyberiansoft.test.dataclasses.vNextBO.clientData.EmailOptionsData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOClientsData {

    @JsonProperty("types")
    private String[] types;

    @JsonProperty("search")
    private String search;

    @JsonProperty("defaultArea")
    private String defaultArea;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("employee")
    private Employee employee;

    @JsonProperty("accountInfoData")
    private AccountInfoData accountInfoData;

    @JsonProperty("accountInfoData")
    private AddressData addressData;

    @JsonProperty("emailOptionsData")
    private EmailOptionsData emailOptionsData;
}
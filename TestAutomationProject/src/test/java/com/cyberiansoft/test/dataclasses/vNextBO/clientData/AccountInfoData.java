package com.cyberiansoft.test.dataclasses.vNextBO.clientData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccountInfoData {

    @JsonProperty("accountingId")
    private String accountingId;

    @JsonProperty("accountingId2")
    private String accountingId2;

    @JsonProperty("exportAs")
    private String exportAs;

    @JsonProperty("classOption")
    private String classOption;

    @JsonProperty("qbAccount")
    private String qbAccount;
}
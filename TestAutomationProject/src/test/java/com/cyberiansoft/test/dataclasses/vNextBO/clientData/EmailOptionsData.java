package com.cyberiansoft.test.dataclasses.vNextBO.clientData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EmailOptionsData {

    @JsonProperty("defaultRecipient")
    private String defaultRecipient;

    @JsonProperty("cc")
    private String cc;

    @JsonProperty("bcc")
    private String bcc;
}
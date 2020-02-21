package com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBODocumentData {

    @JsonProperty("type")
    private String type;

    @JsonProperty("number")
    private String number;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("amount")
    private String amount;
}

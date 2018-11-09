package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOQuickNotesData {

    @JsonProperty("quickNotesDescription")
    private String quickNotesDescription;

    @JsonProperty("quickNotesDescriptionErrorMessage")
    private String quickNotesDescriptionErrorMessage;

    public String getQuickNotesDescription() {
        return quickNotesDescription;
    }

    public String getQuickNotesDescriptionErrorMessage() {
        return quickNotesDescriptionErrorMessage;
    }
}

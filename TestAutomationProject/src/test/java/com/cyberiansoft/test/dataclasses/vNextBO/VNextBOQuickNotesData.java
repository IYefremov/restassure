package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOQuickNotesData {

    @JsonProperty("quickNotesDescription")
    private String quickNotesDescription;

    @JsonProperty("quickNotesDescriptionEdited")
    private String quickNotesDescriptionEdited;

    public String getQuickNotesDescription() {
        return quickNotesDescription;
    }

    public String getQuickNotesDescriptionEdited() {
        return quickNotesDescriptionEdited;
    }
}

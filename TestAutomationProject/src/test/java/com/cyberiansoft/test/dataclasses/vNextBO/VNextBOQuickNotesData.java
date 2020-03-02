package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOQuickNotesData {

    @JsonProperty("quickNotesDescription")
    private String quickNotesDescription;

    @JsonProperty("quickNotesDescriptionEdited")
    private String quickNotesDescriptionEdited;

    @JsonProperty("quickNotesDescriptionList")
    private String[] quickNotesDescriptionList;
}
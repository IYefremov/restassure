package com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

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

    @JsonProperty("attachment")
    private String attachment;

    public String getAttachment() {
        return System.getProperty("user.dir") + "/resources/sunrise.png";
    }

    public VNextBODocumentData() {
        setRandomNumber();
        setRandomNotes();
        setRandomAmount();
    }

    private void setRandomNumber() {
        this.number = String.valueOf(RandomUtils.nextInt(1, 99));
    }

    private void setRandomNotes() {
        this.notes = "auto-test-" + RandomStringUtils.randomAlphanumeric(4);
    }

    private void setRandomAmount() {
        this.amount = String.valueOf(RandomUtils.nextInt(1, 99));
    }
}

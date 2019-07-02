package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
public class VNextBOSmokeData {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("userMailPrefix")
    private String userMailPrefix;

    @JsonProperty("userMailPostbox")
    private String userMailPostbox;

    @JsonProperty("userPhone")
    private String userPhone;

    @JsonProperty("userEdited")
    private String userEdited;

    @JsonProperty("userNewPhone")
    private String userNewPhone;

    @JsonProperty("shortPassword")
    private String shortPassword;

    public String getFirstName() {
        return firstName + RandomStringUtils.randomAlphanumeric(5);
    }
}
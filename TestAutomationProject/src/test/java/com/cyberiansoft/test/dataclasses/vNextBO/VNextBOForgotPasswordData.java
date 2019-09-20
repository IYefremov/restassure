package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOForgotPasswordData {

    @JsonProperty("email")
    private String email;

    public String getEmail() {
        return email;
    }
}

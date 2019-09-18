package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VNextBOHomePageData {

    @JsonProperty("url")
    private String url;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;
}

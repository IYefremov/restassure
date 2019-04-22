package com.cyberiansoft.test.dataclasses.vNextBO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VNextBOHomePageData {

    @JsonProperty("url")
    private String url;

    public String getUrl() {
        return url;
    }
}

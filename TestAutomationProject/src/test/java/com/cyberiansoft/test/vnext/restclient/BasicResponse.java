package com.cyberiansoft.test.vnext.restclient;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Result",
        "ErrorCode",
        "ErrorDescription"
})
public class BasicResponse {

    @JsonProperty("Result")
    private Boolean Result;
    @JsonProperty("ErrorCode")
    private Integer ErrorCode;
    @JsonProperty("ErrorDescription")
    private String ErrorDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Result")
    public Boolean getResult() {
        return Result;
    }

    @JsonProperty("Result")
    public void setResult(Boolean result) {
        this.Result = result;
    }

    @JsonProperty("ErrorCode")
    public Integer getErrorCode() {
        return ErrorCode;
    }

    @JsonProperty("ErrorCode")
    public void setErrorCode(Integer errorCode) {
        this.ErrorCode = errorCode;
    }

    @JsonProperty("ErrorDescription")
    public String getErrorDescription() {
        return ErrorDescription;
    }

    @JsonProperty("ErrorDescription")
    public void setErrorDescription(String errorDescription) {
        this.ErrorDescription = errorDescription;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

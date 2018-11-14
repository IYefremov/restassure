package com.cyberiansoft.test.vnext.restclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Result",
        "ErrorCode",
        "ErrorDescription"
})

public class InvoicesListResponse {

    @JsonProperty("Result")
    private List<InvoiceResultDTO> Result = null;
    @JsonProperty("ErrorCode")
    private Integer ErrorCode;
    @JsonProperty("ErrorDescription")
    private Object ErrorDescription;


    @JsonProperty("Result")
    public List<InvoiceResultDTO> getResult() {
        return Result;
    }

    @JsonProperty("Result")
    public void setResult(List<InvoiceResultDTO> result) {
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
    public Object getErrorDescription() {
        return ErrorDescription;
    }

    @JsonProperty("ErrorDescription")
    public void setErrorDescription(Object errorDescription) {
        this.ErrorDescription = errorDescription;
    }

}

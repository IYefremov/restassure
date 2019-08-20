package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InvoiceCashCheckPaymentData {

    @JsonProperty("cashCheckNumber")
    String cashCheckNumber;

    @JsonProperty("cashCheckAmount")
    String cashCheckAmount;

    @JsonProperty("cashCheckDescription")
    String cashCheckDescription;
}

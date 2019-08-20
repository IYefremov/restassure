package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InvoiceData {

    @JsonProperty("invoiceType")
    String invoiceType;

    @JsonProperty("poNumber")
    String poNumber;

    @JsonProperty("invoiceTotal")
    String invoiceTotal;

    @JsonProperty("questionScreenData")
    QuestionScreenData questionScreenData;

    @JsonProperty("creditCardData")
    CreditCardData creditCardData;

    @JsonProperty("cashCheckPaymentData")
    InvoiceCashCheckPaymentData cashCheckPaymentData;

    @JsonProperty("newPoNumber")
    String newPoNumber;
}

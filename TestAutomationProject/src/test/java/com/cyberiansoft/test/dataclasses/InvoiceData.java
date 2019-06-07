package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

public class InvoiceData {

    @JsonProperty("invoiceType")
    String invoiceType;

    @JsonProperty("poNumber")
    String poNumber;

    @JsonProperty("invoiceTotal")
    String invoiceTotal;

    @Getter
    @JsonProperty("questionScreenData")
    QuestionScreenData questionScreenData;

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getInvoicePONumber() {
        return poNumber;
    }

    public String getInvoiceTotal() {
        return invoiceTotal;
    }
}

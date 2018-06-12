package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Invoice {

    @JsonProperty("workOrderData")
    WorkOrderData workOrderData;

    @JsonProperty("invoiceData")
    InvoiceData invoiceData;

    @JsonProperty("creditCardData")
    CreditCardData creditCardData;

    public WorkOrderData getWorkOrderData() {
        return workOrderData;
    }

    public InvoiceData getInvoiceData() {
        return invoiceData;
    }

    public CreditCardData getCreditCardData() {
        return creditCardData;
    }
}

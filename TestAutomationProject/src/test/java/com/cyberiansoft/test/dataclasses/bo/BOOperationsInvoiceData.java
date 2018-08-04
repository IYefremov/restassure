package com.cyberiansoft.test.dataclasses.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.RandomStringUtils;

public class BOOperationsInvoiceData {

    @JsonProperty("customer")
    private String customer;

    @JsonProperty("amountFrom")
    private String amountFrom;

    @JsonProperty("amountTo")
    private String amountTo;

    @JsonProperty("po")
    private String po;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("poNum")
    private String poNum;

    @JsonProperty("poNotes")
    private String poNotes;

    @JsonProperty("userMail")
    private String userMail;

    @JsonProperty("message")
    private String message;

    public String getCustomer() {
        return customer;
    }

    public String getAmountFrom() {
        return amountFrom;
    }

    public String getAmountTo() {
        return amountTo;
    }

    public String getPo() {
        return po;
    }

    public String getNotes() {
        return notes;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getPoNum() {
        return poNum;
    }

    public String getPoNotes() {
        return poNotes;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return "test" + RandomStringUtils.randomAlphanumeric(3) + "@domain.com";
    }
}

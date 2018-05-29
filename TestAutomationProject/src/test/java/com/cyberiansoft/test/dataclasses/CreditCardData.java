package com.cyberiansoft.test.dataclasses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardData {

    @JsonProperty("cardNumber")
    String cardNumber;

    @JsonProperty("expirationMonth")
    String expirationMonth;

    @JsonProperty("expirationYear")
    String expirationYear;

    @JsonProperty("cvc")
    String cvc;

    public String getCardNumber() { return cardNumber; };

    public String getExpirationMonth() { return expirationMonth; };

    public String getExpirationYear() { return expirationYear; };

    public String getCVC() { return cvc; };
}

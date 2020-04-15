package com.cyberiansoft.test.vnext.validations.customers;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.CustomerContact;
import com.cyberiansoft.test.vnext.screens.customers.VNextNewContactScreen;
import org.testng.Assert;

public class NewContactScreenValidations {

    private static void verifyFirstNameFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getFirstNameField()), expectedValue,
                "'First name' field has contained incorrect value");
    }

    private static void verifyLastNameFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getLastNameField()), expectedValue,
                "'Last name' field has contained incorrect value");
    }

    private static void verifyCompanyNameFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getCompanyNameField()), expectedValue,
                "'Company name' field has contained incorrect value");
    }

    private static void verifyEmailFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getEmailField()), expectedValue,
                "'Email' field has contained incorrect value");
    }

    private static void verifyPhoneFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getPhoneField()), expectedValue,
                "'Phone' field has contained incorrect value");
    }

    private static void verifyAddress1FieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getAddress1Field()), expectedValue,
                "'Address 1' field has contained incorrect value");
    }

    private static void verifyAddress2FieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getAddress2Field()), expectedValue,
                "'Address 2' field has contained incorrect value");
    }

    private static void verifyCityFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getCityField()), expectedValue,
                "'City' field has contained incorrect value");
    }

    private static void verifyCountryFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getCountryField()), expectedValue,
                "'Country' field has contained incorrect value");
    }

    private static void verifyStateFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getStateFieldValue()), expectedValue,
                "'State' field has contained incorrect value");
    }

    private static void verifyZipFieldContainsCorrectValue(String expectedValue) {

        Assert.assertEquals(Utils.getInputFieldValue(new VNextNewContactScreen().getZipField()), expectedValue,
                "'ZIP' field has contained incorrect value");
    }

    public static void verifyAllCustomerDataIsCorrect(CustomerContact contact) {

        verifyFirstNameFieldContainsCorrectValue(contact.getFirstName());
        verifyLastNameFieldContainsCorrectValue(contact.getLastName());
        verifyCompanyNameFieldContainsCorrectValue(contact.getCompanyName());
        verifyEmailFieldContainsCorrectValue(contact.getEmail());
        verifyPhoneFieldContainsCorrectValue(contact.getPhone());
        verifyAddress1FieldContainsCorrectValue(contact.getAddress1());
        verifyAddress2FieldContainsCorrectValue(contact.getAddress2());
        verifyCityFieldContainsCorrectValue(contact.getCity());
        verifyCountryFieldContainsCorrectValue(contact.getCountry());
        if (contact.getState() != null) verifyStateFieldContainsCorrectValue(contact.getState());
        verifyZipFieldContainsCorrectValue(contact.getZip());
    }
}

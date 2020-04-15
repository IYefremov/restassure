package com.cyberiansoft.test.vnext.steps.customers;

import com.cyberiansoft.test.baseutils.ConditionWaiter;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.CustomerContact;
import com.cyberiansoft.test.vnext.screens.customers.VNextNewContactScreen;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;

public class NewContactScreenSteps {

    private static void setFirstName(String firstName) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        Utils.clickElement(contactScreen.getFirstNameField());
        contactScreen.getFirstNameField().clear();
        contactScreen.getFirstNameField().sendKeys(firstName);
    }

    private static void setLastName(String lastName) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        Utils.clickElement(contactScreen.getLastNameField());
        contactScreen.getLastNameField().clear();
        contactScreen.getLastNameField().sendKeys(lastName);
    }

    private static void setCompanyName(String companyName) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        contactScreen.getCompanyNameField().clear();
        contactScreen.getCompanyNameField().sendKeys(companyName);
    }

    private static void setEmail(String email) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        contactScreen.getEmailField().clear();
        contactScreen.getEmailField().sendKeys(email);
    }

    private static void setPhone(String phone) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        contactScreen.getPhoneField().clear();
        contactScreen.getPhoneField().sendKeys(phone);
    }

    private static void setAddress1(String address1) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        contactScreen.getAddress1Field().clear();
        contactScreen.getAddress1Field().sendKeys(address1);
    }

    private static void setAddress2(String address2) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        contactScreen.getAddress2Field().clear();
        contactScreen.getAddress2Field().sendKeys(address2);
    }

    private static void setCity(String city) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        contactScreen.getCityField().clear();
        contactScreen.getCityField().sendKeys(city);
    }

    private static void setCountry(String country) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        Utils.clickElement(contactScreen.getCountryField());
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(country);
        Utils.clickElement(contactScreen.countryRecord(country));
        WaitUtilsWebDriver.waitABit(1000);
    }

    private static void setState(String state) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        Utils.clickElement(contactScreen.getStateField());
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(state);
        Utils.clickElement(contactScreen.stateRecord(state));
        WaitUtilsWebDriver.waitABit(1000);
    }

    private static void setZip(String zip) {

        VNextNewContactScreen contactScreen = new VNextNewContactScreen();
        ConditionWaiter.create(__ -> contactScreen.getZipField().isEnabled()).execute();
        Utils.clickElement(contactScreen.getZipField());
        contactScreen.getZipField().clear();
        contactScreen.getZipField().sendKeys(zip);
    }

    public static void setAllContactData(CustomerContact contact) {

        setFirstName(contact.getFirstName());
        setLastName(contact.getLastName());
        setCompanyName(contact.getCompanyName());
        setEmail(contact.getEmail());
        setPhone(contact.getPhone());
        setAddress1(contact.getAddress1());
        setAddress2(contact.getAddress2());
        setCity(contact.getCity());
        setCountry(contact.getCountry());
        if (contact.getState() != null) setState(contact.getState());
        setZip(contact.getZip());
    }
}

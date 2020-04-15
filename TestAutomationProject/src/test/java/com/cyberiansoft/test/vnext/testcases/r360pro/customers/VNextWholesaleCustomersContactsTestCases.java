package com.cyberiansoft.test.vnext.testcases.r360pro.customers;

import com.cyberiansoft.test.dataclasses.CustomerContact;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.dialogs.WarningDialogSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomerContactsScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomerOptionsScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.NewContactScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.commonobjects.VNextTopScreenPanelValidations;
import com.cyberiansoft.test.vnext.validations.customers.CustomerContactsScreenValidations;
import com.cyberiansoft.test.vnext.validations.customers.CustomerOptionsScreenValidation;
import com.cyberiansoft.test.vnext.validations.customers.CustomersScreenValidation;
import com.cyberiansoft.test.vnext.validations.customers.NewContactScreenValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWholesaleCustomersContactsTestCases extends BaseTestClass {

    final String TEST_CONTACT_NAME = "Test1 Test1";

    @BeforeClass(description = "Wholesale Customers Contacts search Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWholesaleCustomersContactsTestCasesDataPath();
        HomeScreenSteps.openCustomers();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToWholesaleMode();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchContacts(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.openSearchPanel();
        VNextTopScreenPanelValidations.verifySearchIsExpanded(true);
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenValidation.verifyCustomersAreFoundCorrectly(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenValidation.verifyViewButtonIsDisplayed();
        CustomerOptionsScreenValidation.verifySetAsDefaultButtonIsDisplayed();
        CustomerOptionsScreenValidation.verifyCustomerContactsButtonIsDisplayed();
        CustomerOptionsScreenSteps.openCustomerContacts();
        VNextTopScreenPanelValidations.verifyBackIconIsDisplayed();
        VNextTopScreenPanelValidations.verifySearchIconIsDisplayed();
        CustomerContactsScreenValidations.verifyPlusButtonIsDisplayed();
        CustomerContactsScreenValidations.verifyContactsListIsDisplayed();
        TopScreenPanelSteps.openSearchPanel();
        VNextTopScreenPanelValidations.verifySearchIsExpanded(true);
        TopScreenPanelSteps.fillSearchField(TEST_CONTACT_NAME);
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(TEST_CONTACT_NAME);
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseTheSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.cancelSearch();
        CustomerContactsScreenValidations.verifyContactsListIsDisplayed();
        VNextTopScreenPanelValidations.verifySearchIsExpanded(false);
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearTheSearchFieldXIcon(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(TEST_CONTACT_NAME);
        TopScreenPanelSteps.clearSearchField();
        VNextTopScreenPanelValidations.verifySearchFieldContainsCorrectValue("");
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseTheSearchWithFilledSearchField(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(TEST_CONTACT_NAME);
        TopScreenPanelSteps.cancelSearch();
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(TEST_CONTACT_NAME);
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMinimizeDropDownWindowOfNewContactsAdding(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenValidations.verifyXIconButtonIsDisplayed();
        CustomerContactsScreenValidations.verifyFromContactsIconAndButtonAreDisplayed();
        CustomerContactsScreenValidations.verifyNewContactIconAndButtonAreDisplayed();
        CustomerContactsScreenSteps.clickXIconButton();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelAddingCustomerContactsDoNotSave(String rowID, String description, JSONObject testData) {

        CustomerContact contactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        contactData.setLastName(contactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.setAllContactData(contactData);
        NewContactScreenValidations.verifyAllCustomerDataIsCorrect(contactData);
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickDontSaveButton();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(contactData.getLastName());
        CustomerContactsScreenValidations.verifyNotFoundMessageIsDisplayed();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelAddingCustomerContactsButSaveChanges(String rowID, String description, JSONObject testData) {

        CustomerContact contactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        contactData.setLastName(contactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.setAllContactData(contactData);
        NewContactScreenValidations.verifyAllCustomerDataIsCorrect(contactData);
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickSaveButton();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(contactData.getLastName());
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(contactData.getLastName());
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddCustomerContacts(String rowID, String description, JSONObject testData) {

        CustomerContact contactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        contactData.setLastName(contactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.setAllContactData(contactData);
        NewContactScreenValidations.verifyAllCustomerDataIsCorrect(contactData);
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(contactData.getLastName());
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(contactData.getLastName());
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseEditCustomerContactsWindow(String rowID, String description, JSONObject testData) {

        CustomerContact contactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        contactData.setLastName(contactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField(TEST_CONTACT_NAME);
        CustomerContactsScreenSteps.tapOnFirstContact();
        CustomerContactsScreenValidations.verifyEditButtonIsDisplayed();
        CustomerContactsScreenValidations.verifyCloseButtonIsDisplayed();
        CustomerContactsScreenSteps.clickCloseButton();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

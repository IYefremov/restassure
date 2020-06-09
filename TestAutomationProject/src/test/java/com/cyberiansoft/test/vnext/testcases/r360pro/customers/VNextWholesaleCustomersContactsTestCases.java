package com.cyberiansoft.test.vnext.testcases.r360pro.customers;

import com.cyberiansoft.test.dataclasses.CustomerContact;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.dialogs.WarningDialogSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomerContactsScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.NewContactScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.commonobjects.VNextTopScreenPanelValidations;
import com.cyberiansoft.test.vnext.validations.customers.CustomerContactsScreenValidations;
import com.cyberiansoft.test.vnext.validations.customers.CustomersScreenValidation;
import com.cyberiansoft.test.vnext.validations.customers.NewContactScreenValidations;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWholesaleCustomersContactsTestCases extends BaseTestClass {

    final String TEST_CONTACT_NAME = "Test1 Test1";
    private static final String PRECONDITIONS_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360pro/customers/wholesale-customers-contact-base-data.json";

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
        MenuValidations.menuItemShouldBeVisible(MenuItems.VIEW, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.SET_AS_DEFAULT, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CUSTOMER_CONTACTS, true);
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
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

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.cancelSearch();
        CustomerContactsScreenValidations.verifyContactsListIsDisplayed();
        VNextTopScreenPanelValidations.verifySearchIsExpanded(false);
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearTheSearchFieldXIcon(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        TopScreenPanelSteps.searchData(TEST_CONTACT_NAME);
        TopScreenPanelSteps.clearSearchField();
        VNextTopScreenPanelValidations.verifySearchFieldContainsCorrectValue("");
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseTheSearchWithFilledSearchField(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        TopScreenPanelSteps.searchData(TEST_CONTACT_NAME);
        TopScreenPanelSteps.cancelSearch();
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(TEST_CONTACT_NAME);
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanMinimizeDropDownWindowOfNewContactsAdding(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
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
        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.setAllContactData(contactData);
        NewContactScreenValidations.verifyAllContactDataIsCorrect(contactData);
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickDontSaveButton();
        TopScreenPanelSteps.searchData(contactData.getLastName());
        CustomerContactsScreenValidations.verifyNotFoundMessageIsDisplayed();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelAddingCustomerContactsButSaveChanges(String rowID, String description, JSONObject testData) {

        CustomerContact contactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        contactData.setLastName(contactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.setAllContactData(contactData);
        NewContactScreenValidations.verifyAllContactDataIsCorrect(contactData);
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickSaveButton();
        TopScreenPanelSteps.searchData(contactData.getLastName());
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(contactData.getLastName());
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddCustomerContacts(String rowID, String description, JSONObject testData) {

        CustomerContact contactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        contactData.setLastName(contactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.setAllContactData(contactData);
        NewContactScreenValidations.verifyAllContactDataIsCorrect(contactData);
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.searchData(contactData.getLastName());
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(contactData.getLastName());
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseEditCustomerContactsWindow(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        TopScreenPanelSteps.searchData(TEST_CONTACT_NAME);
        CustomerContactsScreenSteps.tapOnFirstContact();
        CustomerContactsScreenValidations.verifyEditButtonIsDisplayed();
        CustomerContactsScreenValidations.verifyCloseButtonIsDisplayed();
        CustomerContactsScreenSteps.clickCloseButton();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditCustomerContacts(String rowID, String description, JSONObject testData) throws Exception {

        CustomerContact baseContact = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITIONS_FILE), CustomerContact.class);
        CustomerContact editedContactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        baseContact.setLastName(baseContact.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        editedContactData.setLastName(editedContactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        CustomerContactsScreenSteps.addNewContact(baseContact);
        TopScreenPanelSteps.searchData(baseContact.getLastName());
        CustomerContactsScreenSteps.tapOnFirstContact();
        CustomerContactsScreenSteps.clickEditContactButton();
        NewContactScreenSteps.setAllContactData(editedContactData);
        NewContactScreenValidations.verifyAllContactDataIsCorrect(editedContactData);
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.searchData(editedContactData.getLastName());
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(editedContactData.getLastName());
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.searchData(baseContact.getLastName());
        CustomerContactsScreenValidations.verifyNotFoundMessageIsDisplayed();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelEditingCustomerContactDontSave(String rowID, String description, JSONObject testData) throws Exception {

        CustomerContact baseContact = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITIONS_FILE), CustomerContact.class);
        CustomerContact editedContactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        baseContact.setLastName(baseContact.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        editedContactData.setLastName(editedContactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        CustomerContactsScreenSteps.addNewContact(baseContact);
        TopScreenPanelSteps.searchData(baseContact.getLastName());
        CustomerContactsScreenSteps.tapOnFirstContact();
        CustomerContactsScreenSteps.clickEditContactButton();
        NewContactScreenSteps.setAllContactData(editedContactData);
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickDontSaveButton();
        TopScreenPanelSteps.searchData(editedContactData.getLastName());
        CustomerContactsScreenValidations.verifyNotFoundMessageIsDisplayed();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.searchData(baseContact.getLastName());
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(baseContact.getLastName());
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelEditingCustomerContactSave(String rowID, String description, JSONObject testData) throws Exception {

        CustomerContact baseContact = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITIONS_FILE), CustomerContact.class);
        CustomerContact editedContactData = JSonDataParser.getTestDataFromJson(testData, CustomerContact.class);
        baseContact.setLastName(baseContact.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        editedContactData.setLastName(editedContactData.getLastName() + RandomStringUtils.randomAlphanumeric(5));
        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        MenuSteps.selectMenuItem(MenuItems.CUSTOMER_CONTACTS);
        CustomerContactsScreenSteps.addNewContact(baseContact);
        TopScreenPanelSteps.searchData(baseContact.getLastName());
        CustomerContactsScreenSteps.tapOnFirstContact();
        CustomerContactsScreenSteps.clickEditContactButton();
        NewContactScreenSteps.setAllContactData(editedContactData);
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickSaveButton();
        TopScreenPanelSteps.searchData(editedContactData.getLastName());
        CustomerContactsScreenValidations.verifyContactsWereFoundCorrectlyByName(editedContactData.getLastName());
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.searchData(baseContact.getLastName());
        CustomerContactsScreenValidations.verifyNotFoundMessageIsDisplayed();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

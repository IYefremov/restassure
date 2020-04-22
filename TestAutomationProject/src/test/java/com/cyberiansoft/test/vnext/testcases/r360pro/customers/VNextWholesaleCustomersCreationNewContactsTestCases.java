package com.cyberiansoft.test.vnext.testcases.r360pro.customers;

import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomerContactsScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomerOptionsScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.customers.NewContactScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.commonobjects.VNextTopScreenPanelValidations;
import com.cyberiansoft.test.vnext.validations.customers.NewContactScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWholesaleCustomersCreationNewContactsTestCases extends BaseTestClass {

    @BeforeClass(description = "Wholesale Customers Contacts search Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWholesaleCustomersContactsTestCasesDataPath();
        HomeScreenSteps.openCustomers();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToWholesaleMode();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReturnBackFromCountriesPage(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openCountrySearchPage();
        NewContactScreenValidations.verifyCountriesScreenIsOpened();
        TopScreenPanelSteps.goToThePreviousScreen();
        NewContactScreenValidations.verifyNewContactScreenIsOpened();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchCountriesUsingSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openCountrySearchPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("Gree");
        NewContactScreenValidations.verifyCountriesAreFoundCorrectly("Gree");
        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseFilledCountrySearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openCountrySearchPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("Gree");
        TopScreenPanelSteps.cancelSearch();
        NewContactScreenValidations.verifyCountriesAreFoundCorrectly("Gree");
        VNextTopScreenPanelValidations.verifySearchIconIsDisplayed();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearCountriesSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openCountrySearchPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("Gree");
        TopScreenPanelSteps.clearSearchField();
        VNextTopScreenPanelValidations.verifySearchFieldContainsCorrectValue("");
        NewContactScreenValidations.verifyAllCountriesAreDisplayed();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReturnBackFromStateProvincePage(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openStateProvinceSearchPage();
        NewContactScreenValidations.verifyStateProvinceScreenIsOpened();
        TopScreenPanelSteps.goToThePreviousScreen();
        NewContactScreenValidations.verifyNewContactScreenIsOpened();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectStateProvinceUsingSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openStateProvinceSearchPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("Ala");
        NewContactScreenValidations.verifyStatesAreFoundCorrectly("Ala");
        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseFilledStateSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openStateProvinceSearchPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("Ala");
        TopScreenPanelSteps.cancelSearch();
        NewContactScreenValidations.verifyStatesAreFoundCorrectly("Ala");
        VNextTopScreenPanelValidations.verifySearchIconIsDisplayed();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearStateProvinceSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openStateProvinceSearchPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("Ala");
        TopScreenPanelSteps.clearSearchField();
        VNextTopScreenPanelValidations.verifySearchFieldContainsCorrectValue("");
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNewContactPage(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenValidations.verifyNewContactScreenIsOpened();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanReturnBackFromListOfContactsPage(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openContactsListPage();
        NewContactScreenValidations.verifyContactsListScreenIsOpened();
        TopScreenPanelSteps.goToThePreviousScreen();
        NewContactScreenValidations.verifyNewContactScreenIsOpened();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseFilledSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openContactsListPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("gmail");
        TopScreenPanelSteps.cancelSearch();
        NewContactScreenValidations.verifyContactsListScreenIsOpened();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.clearSearchField();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearTheEmailSearch(String rowID, String description, JSONObject testData) {

        TopScreenPanelSteps.searchData(testwholesailcustomer.getCompany());
        CustomersScreenSteps.tapOnCustomer(testwholesailcustomer.getCompany());
        CustomerOptionsScreenSteps.openCustomerContacts();
        CustomerContactsScreenSteps.clickPlusButton();
        CustomerContactsScreenSteps.clickNewContactButton();
        NewContactScreenSteps.openContactsListPage();
        TopScreenPanelSteps.openSearchPanel();
        TopScreenPanelSteps.fillSearchField("gmail");
        TopScreenPanelSteps.clearSearchField();
        VNextTopScreenPanelValidations.verifySearchFieldContainsCorrectValue("");
        NewContactScreenValidations.verifyContactsListScreenIsOpened();
        TopScreenPanelSteps.cancelSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

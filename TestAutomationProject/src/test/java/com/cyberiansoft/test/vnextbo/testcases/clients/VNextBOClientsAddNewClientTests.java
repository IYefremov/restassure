package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientInfoBlockInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.steps.VNextBOHeaderPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.clients.VNextBOClientDetailsValidations;
import com.cyberiansoft.test.vnextbo.verifications.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.verifications.commonobjects.VNextBOSearchPanelValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOClientsAddNewClientTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsAddNewClientData.json";
    private VNextBOLoginScreenWebPage loginPage;
    String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = DATA_FILE;
        browserType = BaseUtils.getBrowserType(VNextBOConfigInfo.getInstance().getDefaultBrowser());
        try {
            DriverBuilder.getInstance().setDriver(browserType);
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        webdriver = DriverBuilder.getInstance().getDriver();

        webdriverGotoWebPage(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());

        loginPage = new VNextBOLoginScreenWebPage();
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectClientsMenu();
    }

    @AfterClass
    public void backOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanFillClientInfoFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOClientsPageSteps.clickAddNewClientButton();
        Assert.assertTrue(VNextBOClientDetailsValidations.isClientInfoPanelExpanded(),
                "Client info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isClientInfoPanelExpanded(),
                "Client info panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        VNextBOClientDetailsValidations.verifyClientInfoFieldsContainCorrectData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanFillAccountInfoFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isAccountInfoPanelExpanded(),
                "Account info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(data.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isAccountInfoPanelExpanded(),
                "Account info panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        VNextBOClientDetailsValidations.verifyAccountInfoFieldsContainCorrectData(data.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanFillAddressFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isAddressPanelExpanded(),
                "Address info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setAddressData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isAddressPanelExpanded(),
                "Address info panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        VNextBOClientDetailsValidations.verifyAddressFieldsContainCorrectData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanFillEmailOptionsFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOClientsPageSteps.clickAddNewClientButton();
        new VNextBOClientInfoBlockInteractions().setWholesaleCompanyType();
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isEmailOptionsBlockExpanded(),
                "Email options panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(data.getEmailOptionsData(), true);
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isEmailOptionsBlockExpanded(),
                "Email options panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        VNextBOClientDetailsValidations.verifyEmailOptionsFieldsContainCorrectData(data.getEmailOptionsData(), true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanFillPreferencesFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isPreferencesBlockExpanded(),
                "Preferences panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isPreferencesBlockExpanded(),
                "Preferences panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        VNextBOClientDetailsValidations.verifyPreferencesFieldsContainCorrectData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanFillMiscellaneousFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOClientsPageSteps.clickAddNewClientButton();
        new VNextBOClientInfoBlockInteractions().setWholesaleCompanyType();
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isMiscellaneousBlockExpanded(),
                "Miscellaneous panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(data.getNotes());
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isMiscellaneousBlockExpanded(),
                "Miscellaneous panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        VNextBOClientDetailsValidations.verifyMiscellaneousFieldsContainCorrectData(data.getNotes(), true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanFillAllFieldsForRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(data.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.setAddressData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(data.getEmailOptionsData(), false);
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(data.getNotes());
        VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        VNextBOClientDetailsValidations.verifyClientInfoFieldsContainCorrectData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        VNextBOClientDetailsValidations.verifyAccountInfoFieldsContainCorrectData(data.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        VNextBOClientDetailsValidations.verifyAddressFieldsContainCorrectData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        VNextBOClientDetailsValidations.verifyEmailOptionsFieldsContainCorrectData(data.getEmailOptionsData(), false);
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        VNextBOClientDetailsValidations.verifyPreferencesFieldsContainCorrectData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        VNextBOClientDetailsValidations.verifyMiscellaneousFieldsContainCorrectData(data.getNotes(), false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanCancelAddingRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        Employee retailClient = data.getEmployee();
        retailClient.setEmployeeFirstName(data.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(data.getEmployee(), data.getAccountInfoData(), data.getAddressData(),
                data.getEmailOptionsData(), data.getDefaultArea(), data.getNotes(), false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.searchByText(retailClient.getEmployeeFirstName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + retailClient.getEmployeeFirstName());
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanCancelAddingWholesaleClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        Employee wholesaleClient = data.getEmployee();
        wholesaleClient.setEmployeeFirstName(data.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(data.getEmployee(), data.getAccountInfoData(), data.getAddressData(),
                data.getEmailOptionsData(), data.getDefaultArea(), data.getNotes(), true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.searchByText(wholesaleClient.getEmployeeFirstName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + wholesaleClient.getEmployeeFirstName());
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanAddNewWholesaleClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        Employee wholesaleClient = data.getEmployee();
        wholesaleClient.setEmployeeFirstName(data.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(wholesaleClient, data.getAccountInfoData(), data.getAddressData(),
                data.getEmailOptionsData(), data.getDefaultArea(), data.getNotes(), true);
        VNextBOClientDetailsViewAccordionSteps.clickOkButton();
        VNextBOSearchPanelSteps.searchByText(wholesaleClient.getEmployeeFirstName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + wholesaleClient.getEmployeeFirstName());
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", wholesaleClient.getEmployeeFirstName());
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Wholesale", true);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Single WO type", true);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("PO#req.", true);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanAddNewRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        Employee retailClient = data.getEmployee();
        retailClient.setEmployeeFirstName(data.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(retailClient, data.getAccountInfoData(), data.getAddressData(),
                data.getEmailOptionsData(), data.getDefaultArea(), data.getNotes(), false);
        VNextBOClientDetailsViewAccordionSteps.clickOkButton();
        VNextBOSearchPanelSteps.searchByText(retailClient.getEmployeeFirstName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + retailClient.getEmployeeFirstName());
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", retailClient.getEmployeeFirstName());
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Wholesale", false);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Single WO type", true);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("PO#req.", true);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}
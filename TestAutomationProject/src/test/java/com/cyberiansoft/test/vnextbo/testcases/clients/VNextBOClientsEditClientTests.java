package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
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

public class VNextBOClientsEditClientTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsEditClientData.json";
    private static final String PRECONDITION_RETAIL_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsPreconditionsRetailClient.json";
    private static final String PRECONDITION_WHOLESALE_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsPreconditionsWholesaleClient.json";
    private VNextBOLoginScreenWebPage loginPage;
    String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
    String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();
    VNextBOClientsData baseRetailClient;
    VNextBOClientsData baseWholesaleClient;

    @BeforeClass
    public void settingUp() throws Exception {

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

        baseRetailClient = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITION_RETAIL_FILE), VNextBOClientsData.class);
        baseRetailClient.getEmployee().setEmployeeFirstName(baseRetailClient.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.createNewClient(baseRetailClient, false);
        baseWholesaleClient = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITION_WHOLESALE_FILE), VNextBOClientsData.class);
        baseWholesaleClient.getEmployee().setCompanyName(baseWholesaleClient.getEmployee().getCompanyName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.createNewClient(baseWholesaleClient, true);
    }

    @AfterClass
    public void backOfficeLogout() {
        VNextBOHeaderPanelSteps.logout();

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanEditClientInfoFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        Assert.assertTrue(VNextBOClientDetailsValidations.isClientInfoPanelExpanded(),
                "Client info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isClientInfoPanelExpanded(),
                "Client info panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        VNextBOClientDetailsValidations.verifyClientInfoFieldsContainCorrectData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanEditAccountInfoFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isAccountInfoPanelExpanded(),
                "Account info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(data.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isAccountInfoPanelExpanded(),
                "Account info panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        VNextBOClientDetailsValidations.verifyAccountInfoFieldsContainCorrectData(data.getAccountInfoData(), false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanEditAddressFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
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
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanEditEmailOptionsFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        new VNextBOClientInfoBlockInteractions().setWholesaleCompanyType();
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isEmailOptionsBlockExpanded(),
                "Email options panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(data.getEmailOptionsData(), true);
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isEmailOptionsBlockExpanded(),
                "Email options panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        VNextBOClientDetailsValidations.verifyEmailOptionsFieldsContainCorrectData(data.getEmailOptionsData(), true, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanEditPreferencesFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.isPreferencesBlockExpanded(),
                "Preferences panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.isPreferencesBlockExpanded(),
                "Preferences panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        VNextBOClientDetailsValidations.verifyPreferencesFieldsContainCorrectData(data.getDefaultArea(), false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanEditMiscellaneousFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
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
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanCancelEditingRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData retailClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        retailClientData.getEmployee().setEmployeeFirstName(retailClientData.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(retailClientData, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.searchByText(retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanCancelEditingWholesaleClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData wholesaleClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        wholesaleClientData.getEmployee().setEmployeeFirstName(wholesaleClientData.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(wholesaleClientData, true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.searchByText(wholesaleClientData.getEmployee().getEmployeeFirstName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + wholesaleClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanEditDetailsOfRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData retailClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(retailClientData, false);
        VNextBOClientDetailsValidations.verifyAllClientDetailsBlocksData(retailClientData, false, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSaveEditedDetailsForWholesaleClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData wholesaleClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        wholesaleClientData.getEmployee().setCompanyName(wholesaleClientData.getEmployee().getCompanyName() +
                RandomStringUtils.randomAlphabetic(10));
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(wholesaleClientData, true);
        VNextBOClientDetailsViewAccordionSteps.clickOkButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(wholesaleClientData.getEmployee().getCompanyName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client",
                wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Wholesale", true);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Single WO type", false);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("PO#req.", false);
        VNextBOClientsPageSteps.openClientsDetailsPage(wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientDetailsValidations.verifyAllClientDetailsBlocksData(wholesaleClientData, true, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanSaveEditedDetailsForRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData retailClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        retailClientData.getEmployee().setEmployeeFirstName(retailClientData.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByText(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(retailClientData, false);
        VNextBOClientDetailsViewAccordionSteps.clickOkButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOSearchPanelValidations.isSearchFilterTextCorrect("Text: " + retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithText("Client", retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.isCorrectRecordsAmountDisplayed(1);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Wholesale", false);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("Single WO type", false);
        VNextBOClientsPageValidations.isSearchResultCorrectForColumnWithCheckboxes("PO#req.", false);
        VNextBOClientsPageSteps.openClientsDetailsPage(retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientDetailsValidations.verifyAllClientDetailsBlocksData(retailClientData, false, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.isClientsNotFoundMessageDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}
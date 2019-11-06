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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded(),
                "Client info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded(),
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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyAccountInfoPanelIsExpanded(),
                "Account info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(data.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyAccountInfoPanelIsExpanded(),
                "Account info panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        VNextBOClientDetailsValidations.verifyAccountInfoFieldsContainCorrectData(data.getAccountInfoData(), true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanFillAddressFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyAddressPanelIsExpanded(),
                "Address info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setAddressData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyAddressPanelIsExpanded(),
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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyEmailOptionsBlockIsExpanded(),
                "Email options panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(data.getEmailOptionsData(), true);
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyEmailOptionsBlockIsExpanded(),
                "Email options panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        VNextBOClientDetailsValidations.verifyEmailOptionsFieldsContainCorrectData(data.getEmailOptionsData(), true, true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanFillPreferencesFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyPreferencesBlockIsExpanded(),
                "Preferences panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyPreferencesBlockIsExpanded(),
                "Preferences panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        VNextBOClientDetailsValidations.verifyPreferencesFieldsContainCorrectData(data.getDefaultArea(), true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanFillMiscellaneousFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOClientsPageSteps.clickAddNewClientButton();
        new VNextBOClientInfoBlockInteractions().setWholesaleCompanyType();
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyMiscellaneousBlockIsExpanded(),
                "Miscellaneous panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(data.getNotes());
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyMiscellaneousBlockIsExpanded(),
                "Miscellaneous panel hasn't been collapsed");
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        VNextBOClientDetailsValidations.verifyMiscellaneousFieldsContainCorrectData(data.getNotes(), true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanFillAllFieldsForRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData retailClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(retailClientData, false);
        VNextBOClientDetailsValidations.verifyAllClientDetailsBlocksData(retailClientData, false, true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanCancelAddingRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData retailClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        retailClientData.getEmployee().setEmployeeFirstName(retailClientData.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(retailClientData, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.searchByText(retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanCancelAddingWholesaleClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData wholesaleClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        wholesaleClientData.getEmployee().setCompanyName(wholesaleClientData.getEmployee().getCompanyName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.clickAddNewClientButton();
        VNextBOClientDetailsViewAccordionSteps.setAllClientsData(wholesaleClientData, true);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.searchByText(wholesaleClientData.getEmployee().getCompanyName());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanAddNewWholesaleClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData wholesaleClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        wholesaleClientData.getEmployee().setCompanyName(wholesaleClientData.getEmployee().getCompanyName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.createNewClient(wholesaleClientData, true);
        VNextBOSearchPanelSteps.searchByText(wholesaleClientData.getEmployee().getCompanyName());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Wholesale", true);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Single WO type", true);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("PO#req.", true);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanAddNewRetailClient(String rowID, String description, JSONObject testData) {

        VNextBOClientsData retailClientData = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        Utils.refreshPage();
        retailClientData.getEmployee().setEmployeeFirstName(retailClientData.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.createNewClient(retailClientData, false);
        VNextBOSearchPanelSteps.searchByText(retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Wholesale", false);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Single WO type", true);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("PO#req.", true);
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}
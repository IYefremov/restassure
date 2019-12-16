package com.cyberiansoft.test.vnextbo.testcases.clients;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOClientsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.clients.VNextBOClientInfoBlockInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientDetailsViewAccordionSteps;
import com.cyberiansoft.test.vnextbo.steps.clients.VNextBOClientsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientDetailsValidations;
import com.cyberiansoft.test.vnextbo.validations.clients.VNextBOClientsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOClientsEditClientTests extends BaseTestCase {

    private static final String PRECONDITION_RETAIL_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsPreconditionsRetailClient.json";
    private static final String PRECONDITION_WHOLESALE_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/clients/VNextBOClientsPreconditionsWholesaleClient.json";
    VNextBOClientsData baseRetailClient;
    VNextBOClientsData baseWholesaleClient;

    @BeforeClass
    public void settingUp() throws Exception {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getClientsEditClientTD();
        VNextBOLeftMenuInteractions.selectClientsMenu();
        baseRetailClient = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITION_RETAIL_FILE), VNextBOClientsData.class);
        baseRetailClient.getEmployee().setEmployeeFirstName(baseRetailClient.getEmployee().getEmployeeFirstName() + RandomStringUtils.randomAlphabetic(10));
        VNextBOClientsPageSteps.createNewClient(baseRetailClient, false);
        baseWholesaleClient = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITION_WHOLESALE_FILE), VNextBOClientsData.class);
        baseWholesaleClient.getEmployee().setCompanyName(baseWholesaleClient.getEmployee().getCompanyName() + RandomStringUtils.randomAlphabetic(10));
        Utils.refreshPage();
        VNextBOClientsPageSteps.createNewClient(baseWholesaleClient, true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanEditClientInfoFields(String rowID, String description, JSONObject testData) {

        VNextBOClientsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOClientsData.class);
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageSteps.openClientsDetailsPage(baseWholesaleClient.getEmployee().getCompanyName());
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded(),
                "Client info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setClientInfoData(data.getEmployee());
        VNextBOClientDetailsViewAccordionSteps.clickClientsInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyClientInfoPanelIsExpanded(),
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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyAccountInfoPanelIsExpanded(),
                "Account info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setAccountInfoData(data.getAccountInfoData());
        VNextBOClientDetailsViewAccordionSteps.clickAccountInfoTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyAccountInfoPanelIsExpanded(),
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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyAddressPanelIsExpanded(),
                "Address info panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setAddressData(data.getAddressData());
        VNextBOClientDetailsViewAccordionSteps.clickAddressTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyAddressPanelIsExpanded(),
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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyEmailOptionsBlockIsExpanded(),
                "Email options panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setEmailOptionsData(data.getEmailOptionsData(), true);
        VNextBOClientDetailsViewAccordionSteps.clickEmailOptionsTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyEmailOptionsBlockIsExpanded(),
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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyPreferencesBlockIsExpanded(),
                "Preferences panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setPreferencesData(data.getDefaultArea());
        VNextBOClientDetailsViewAccordionSteps.clickPreferencesTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyPreferencesBlockIsExpanded(),
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
        Assert.assertTrue(VNextBOClientDetailsValidations.verifyMiscellaneousBlockIsExpanded(),
                "Miscellaneous panel hasn't been expanded");
        VNextBOClientDetailsViewAccordionSteps.setMiscellaneousData(data.getNotes());
        VNextBOClientDetailsViewAccordionSteps.clickMiscellaneousTab();
        Assert.assertFalse(VNextBOClientDetailsValidations.verifyMiscellaneousBlockIsExpanded(),
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
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
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
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + wholesaleClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
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
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client",
                wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Wholesale", true);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Single WO type", false);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("PO#req.", false);
        VNextBOClientsPageSteps.openClientsDetailsPage(wholesaleClientData.getEmployee().getCompanyName());
        VNextBOClientDetailsValidations.verifyAllClientDetailsBlocksData(wholesaleClientData, true, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(baseWholesaleClient.getEmployee().getCompanyName());
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
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
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: " + retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithText("Client", retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifyCorrectRecordsAmountIsDisplayed(1);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Wholesale", false);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("Single WO type", false);
        VNextBOClientsPageValidations.verifySearchResultIsCorrectForColumnWithCheckboxes("PO#req.", false);
        VNextBOClientsPageSteps.openClientsDetailsPage(retailClientData.getEmployee().getEmployeeFirstName());
        VNextBOClientDetailsValidations.verifyAllClientDetailsBlocksData(retailClientData, false, false);
        VNextBOClientDetailsViewAccordionSteps.clickCancelButton();
        VNextBOSearchPanelSteps.clearSearchFilter();
        VNextBOSearchPanelSteps.searchByText(baseRetailClient.getEmployee().getEmployeeFirstName());
        VNextBOClientsPageValidations.verifyClientsNotFoundMessageIsDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilter();
    }
}
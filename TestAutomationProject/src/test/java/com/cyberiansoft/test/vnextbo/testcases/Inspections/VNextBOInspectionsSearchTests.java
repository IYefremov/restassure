package com.cyberiansoft.test.vnextbo.testcases.Inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.Inspections.VNextBOInspectionsAdvancedSearchValidations;
import com.cyberiansoft.test.vnextbo.verifications.Inspections.VNextBOInspectionsPageValidations;
import com.cyberiansoft.test.vnextbo.verifications.dialogs.VNextBOModalDialogValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsSearchTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/Inspections/VNextBOInspectionsSearchData.json";
    private VNextBOLoginScreenWebPage loginPage;
    private VNextBOInspectionAdvancedSearchForm vNextBOInspectionAdvancedSearchForm;
    private List<String> expectedStatusDropDownOptions =
            Arrays.asList("All Active", "New", "Approved", "Archived", "Declined");
    private List<String> expectedTimeFrameDropDownOptions =
            Arrays.asList("Week to Date", "Last Week", "Month to Date", "Last Month", "Last 30 Days",
                    "Last 90 Days", "Year to Date", "Last Year", "Custom");
    Map<String, String> valuesForSearch = new HashMap<String, String>() {{
        put("Customer", "Albert Einstein");
        put("PO#", "123");
        put("RO#", "123");
        put("Stock#", "123");
        put("VIN", "123");
        put("Status", "New");
        put("Inspection#", "123");
        put("Timeframe", "Week to Date");
        put("Search Name", "AutomationSearchTest");
    }};

    Map<String, String> editedValuesForSearch = new HashMap<String, String>() {{
        put("Customer", "Albert Einstein");
        put("PO#", "456");
        put("RO#", "456");
        put("Stock#", "456");
        put("VIN", "456");
        put("Status", "New");
        put("Inspection#", "456");
        put("Timeframe", "Week to Date");
        put("Search Name", "AutomationSearchTest2");
    }};

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
        String userName = VNextBOConfigInfo.getInstance().getVNextBONadaMail();
        String userPassword = VNextBOConfigInfo.getInstance().getVNextBOPassword();

        loginPage = new VNextBOLoginScreenWebPage(webdriver);
        loginPage.userLogin(userName, userPassword);
        VNextBOLeftMenuInteractions leftMenuInteractions = new VNextBOLeftMenuInteractions();
        leftMenuInteractions.selectInspectionsMenu();
    }

    @AfterClass
    public void BackOfficeLogout() {
        try {
            VNextBOHeaderPanel headerPanel = new VNextBOHeaderPanel(webdriver);
            if (headerPanel.logOutLinkExists()) {
                headerPanel.userLogout();
            }
        } catch (RuntimeException ignored) {}

        if (DriverBuilder.getInstance().getDriver() != null) {
            DriverBuilder.getInstance().quitDriver();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanUseAdvancedSearchForInspectionsSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(VNextBOInspectionsPageValidations.isSavedAdvancedSearchFilterExists("Archived"),
        "\"Saved searches list hasn't contained saved Archived search\");");
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm(webdriver);
        VNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(vNextBOInspectionAdvancedSearchForm);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanSearchInspectionsUsingSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.searchInspectionByText("123");
        VNextBOInspectionsPageValidations.isClearFilterIconDisplayed();
        for (String inspectionName: VNextBOInspectionsPageSteps.getNamesOfAllInspectionsInTheList()
             ) {
            Assert.assertTrue(inspectionName.contains("123"), inspectionName + "hasn't contained searched text \"123\"");
        }
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Text: 123"),
                "Search option under Search field hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanDeleteSearchOptions(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickClearFilterIcon();
        VNextBOInspectionsPageValidations.isClearFilterIconNotDisplayed();
        Assert.assertFalse(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Text: 123"),
                "Search option under Search field hasn't been cleaned up");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getSearchFieldValue().isEmpty(),
                "Search field hasn't been empty");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanFillAllAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getAllOptionsFromDropdownByName("Status"),
                expectedStatusDropDownOptions,"Status dropdown options set hasn't been correct");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getAllOptionsFromDropdownByName("Timeframe"),
                expectedTimeFrameDropDownOptions, "Timeframe dropdown options set hasn't been correct");
        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(valuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanClearSearchOptions(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        Map<String, String> emptyValuesForSearch = new HashMap<String, String>() {{
            put("Customer", "");
            put("PO#", "");
            put("RO#", "");
            put("Stock#", "");
            put("VIN", "");
            put("Status", "All Active");
            put("Inspection#", "");
            put("Timeframe", "Last 90 Days");
        }};
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(emptyValuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSaveSearchOptions(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
        VNextBOInspectionsAdvancedSearchSteps.clickSaveButton();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOInspectionsPageValidations.isEditAdvancedSearchIconDisplayed();
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(VNextBOInspectionsPageValidations.isSavedAdvancedSearchFilterExists("AutomationSearchTest"),
                "Saved searches list hasn't contained saved AutomationSearchTest search");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanOpenAdvancedSearchWindowWithEditPencilIcon(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(editedValuesForSearch);
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(editedValuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSaveEditedAdvancedSearch(String rowID, String description, JSONObject testData) {

        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm(webdriver);
        VNextBOInspectionsAdvancedSearchSteps.clickSaveButton();
        VNextBOInspectionsPageValidations.isEditAdvancedSearchIconDisplayed();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(editedValuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanNotSaveEditedAdvancedSearch(String rowID, String description, JSONObject testData) {

        Map<String, String> notSavedValuesForSearch = new HashMap<String, String>() {{
            put("Customer", "Albert Einstein");
            put("PO#", "789");
            put("RO#", "789");
            put("Stock#", "789");
            put("VIN", "789");
            put("Status", "Declined");
            put("Inspection#", "789");
            put("Timeframe", "Last Month");
            put("Search Name", "AutomationSearchTest3");
        }};
        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(notSavedValuesForSearch);
        VNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormDisplayed();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(editedValuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanCancelSavedAdvancedSearchDeleting(String rowID, String description, JSONObject testData) {

        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm(webdriver);
        VNextBOInspectionsAdvancedSearchSteps.clickDeleteButton();
        VNextBOModalDialogValidations.isDialogDisplayed();
        VNextBOModalDialogValidations.isNoButtonDisplayed();
        VNextBOModalDialogValidations.isYesButtonDisplayed();
        VNextBOModalDialogValidations.isCloseButtonDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(VNextBOInspectionsPageValidations.isSavedAdvancedSearchFilterExists("AutomationSearchTest2"),
                "Saved searches list hasn't contained saved AutomationSearchTest search");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanDeleteSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm(webdriver);
        VNextBOInspectionsAdvancedSearchSteps.clickDeleteButton();
        VNextBOModalDialogSteps.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertFalse(VNextBOInspectionsPageValidations.isSavedAdvancedSearchFilterExists("AutomationSearchTest2"),
                "Saved search has been displayed in the list");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanSearchInspectionByCustomer(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByCustomer("Amazing Nissan");
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Customer: Amazing Nissan"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            VNextBOInspectionsPageValidations.isCustomerNameCorrect("Amazing Nissan");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
    public void verifyUserCanSearchInspectionByPONumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByPONumber("123");
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("PO#: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("PO#").contains("123"),
                    "Inspection has been found incorrectly");
        }

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
    public void verifyUserCanSearchInspectionByRONumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByRONumber("123");
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("RO#: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("RO#").contains("123"),
                    "Inspection has been found incorrectly");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
    public void verifyUserCanSearchInspectionByStockNumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStockNumber("123");
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Stock#: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("Stock#").contains("123"),
                    "Inspection has been found incorrectly");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
    public void verifyUserCanSearchInspectionByVinNumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByVIN("123");
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("VIN: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("VIN").contains("123"),
                    "Inspection has been found incorrectly");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
    public void verifyUserCanSearchInspectionByInspectionNumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByInspectionNumber("222");
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Inspection#: 222"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            for (String inspectionNumber: VNextBOInspectionsPageSteps.getNumbersOfAllInspectionsInTheList()
            ) {
                Assert.assertTrue(inspectionNumber.contains("222"),
                        "Record with incorrect number has been found");
            }
        }
        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsPageSteps.clickClearFilterIcon();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
    public void verifyUserCanSearchInspectionByStatus(String rowID, String description, JSONObject testData) {

        VNextBOInspectionSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionSearchData.class);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStatus(data.getValueForSearch());
        WaitUtilsWebDriver.waitForLoading();
        if (!data.getValueForSearch().equals("All Active"))
        {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Status: " + data.getValueForSearch()),
                    "Search option under Search field hasn't been correct");
        }
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            if (!data.getValueForSearch().equals("All Active")){
                for (String inspectionStatus: VNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList()
                ) {
                    Assert.assertEquals(inspectionStatus, data.getValueForSearch(),
                            "Record with incorrect status has been found");
                }
            }
            else {
                Assert.assertTrue(VNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                        "Active inspections have not been found");
            }
        }
        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", "All Active");
        VNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
    public void verifyUserCanSearchInspectionByTimeFrame(String rowID, String description, JSONObject testData) {

        VNextBOInspectionSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionSearchData.class);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.findInspectionByCustomTimeFrame(data.getValueForSearch());
        WaitUtilsWebDriver.waitForLoading();
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Timeframe: " + data.getValueForSearch()),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkDisplayed())
        {
            VNextBOInspectionsPageValidations.isHowToCreateInspectionLinkTextCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                    "Inspections have not been found");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 21)
    public void verifyUserCanSearchInspectionByCustomTimeFrame(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm(webdriver);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", "All Active");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", "Custom");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("From", "1/1/2018");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("To", "10/1/2019");
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        VNextBOInspectionsAdvancedSearchValidations.isAdvancedSearchFormNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().equals("From: 01/01/2018, To: 10/01/2019"),
                "Search option under Search field hasn't been correct");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                "Inspections have not been found");
    }
}
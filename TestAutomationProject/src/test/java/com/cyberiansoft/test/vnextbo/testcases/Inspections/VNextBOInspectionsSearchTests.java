package com.cyberiansoft.test.vnextbo.testcases.Inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOInspectionSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.interactions.leftMenuPanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.VNextBOHeaderPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOModalDialog;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.verifications.Inspections.VNextBOInspectionsAdvancedSearchVerifications;
import com.cyberiansoft.test.vnextbo.verifications.Inspections.VNextBOInspectionsPageVerifications;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOInspectionsSearchTests extends BaseTestCase {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnextbo/data/Inspections/VNextBOInspectionsSearchData.json";
    private VNextBOInspectionsPageSteps vNextBOInspectionsPageSteps;
    private VNextBOInspectionsAdvancedSearchSteps vNextBOInspectionsAdvancedSearchSteps;
    private VNextBOInspectionsPageVerifications vNextBOInspectionsPageVerifications;
    private VNextBOInspectionsAdvancedSearchVerifications vNextBOInspectionsAdvancedSearchVerifications;
    private VNextBOLoginScreenWebPage loginPage;
    private List<String> expectedStatusDropDownOptions =
            Arrays.asList("All Active", "New", "Approved", "Archived", "Declined");
    private List<String> expectedTimeFrameDropDownOptions =
            Arrays.asList("Week to Date", "Last Week", "Month to Date", "Last Month", "Last 30 Days",
                    "Last 90 Days", "Year to Date", "Last Year", "Custom");

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

        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageVerifications = new VNextBOInspectionsPageVerifications(webdriver);

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

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(vNextBOInspectionsPageVerifications.isSavedAdvancedSearchFilterExists("Archived"),
        "\"Saved searches list hasn't contained saved Archived search\");");
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webdriver);
        vNextBOInspectionsAdvancedSearchVerifications = new VNextBOInspectionsAdvancedSearchVerifications(webdriver);
        vNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSearchInspectionsUsingSearch(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.searchInspectionByText("123");
        vNextBOInspectionsPageVerifications.isClearFilterIconDisplayed();
        for (String inspectionName: vNextBOInspectionsPageSteps.getNamesOfAllInspectionsInTheList()
             ) {
            Assert.assertTrue(inspectionName.contains("123"), inspectionName + "hasn't contained searched text \"123\"");
        }
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Text: 123"),
                "Search option under Search field hasn't been correct");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanDeleteSearchOptions(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickClearFilterIcon();
        vNextBOInspectionsPageVerifications.isClearFilterIconNotDisplayed();
        Assert.assertFalse(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Text: 123"),
                "Search option under Search field hasn't been cleaned up");
        Assert.assertTrue(vNextBOInspectionsPageSteps.getSearchFieldValue().isEmpty(),
                "Search field hasn't been empty");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanFillAllAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.openAdvancedSearchForm();
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getAllOptionsFromDropdownByName("Status"),
                expectedStatusDropDownOptions,"Status dropdown options set hasn't been correct");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getAllOptionsFromDropdownByName("Timeframe"),
                expectedTimeFrameDropDownOptions, "Timeframe dropdown options set hasn't been correct");
        List<String> valuesForSearch =
                Arrays.asList("Albert Einstein", "123", "123", "123", "123", "New", "123", "Week to Date", "AutomationSearchTest");
        vNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
        vNextBOInspectionsAdvancedSearchVerifications.verifyAllAdvancedSearchFormFields(valuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanClearSearchOptions(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        List<String> valuesForSearch = Arrays.asList("", "", "", "", "", "All Active", "", "Last 90 Days");
        vNextBOInspectionsAdvancedSearchVerifications.verifyAllAdvancedSearchFormFields(valuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanSaveSearchOptions(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webdriver);
        List<String> valuesForSearch =
                Arrays.asList("Albert Einstein", "123", "123", "123", "123", "New", "123", "Week to Date", "AutomationSearchTest");
        vNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
        vNextBOInspectionsAdvancedSearchSteps.clickSaveButton();
        vNextBOInspectionsPageVerifications.isEditAdvancedSearchIconDisplayed();
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(vNextBOInspectionsPageVerifications.isSavedAdvancedSearchFilterExists("AutomationSearchTest"),
                "Saved searches list hasn't contained saved AutomationSearchTest search");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanOpenAdvancedSearchWindowWithEditPencilIcon(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        List<String> valuesForSearch =
                Arrays.asList("Albert Einstein", "456", "456", "456", "456", "New", "456", "Last Week", "AutomationSearchTest2");
        vNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
        vNextBOInspectionsAdvancedSearchVerifications.verifyAllAdvancedSearchFormFields(valuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanSaveEditedAdvancedSearch(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsAdvancedSearchSteps.clickSaveButton();
        vNextBOInspectionsPageVerifications.isEditAdvancedSearchIconDisplayed();
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        vNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormDisplayed();
        WaitUtilsWebDriver.waitForLoading();
        List<String> valuesForSearch =
                Arrays.asList("Albert Einstein", "456", "456", "456", "456", "New", "456", "Last Week");
        vNextBOInspectionsAdvancedSearchVerifications.verifyAllAdvancedSearchFormFields(valuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanNotSaveEditedAdvancedSearch(String rowID, String description, JSONObject testData) {

        List<String> valuesForSearch =
                Arrays.asList("Albert Einstein", "789", "789", "789", "789", "Declined", "789", "Last Month", "AutomationSearchTest3");
        vNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
        vNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
        vNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormDisplayed();
        WaitUtilsWebDriver.waitForLoading();
        valuesForSearch =
                Arrays.asList("Albert Einstein", "456", "456", "456", "456", "New", "456", "Last Week");
        vNextBOInspectionsAdvancedSearchVerifications.verifyAllAdvancedSearchFormFields(valuesForSearch);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanCancelSavedAdvancedSearchDeleting(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsAdvancedSearchSteps.clickDeleteButton();
        VNextBOModalDialog vNextBOConfirmationDialog = new VNextBOModalDialog(webdriver);
        Assert.assertTrue(vNextBOConfirmationDialog.isDialogDisplayed(), "Confirmation dialog hasn't been opened");
        Assert.assertTrue(vNextBOConfirmationDialog.isNoButtonDisplayed(), "Confirmation dialog hasn't had \"No\" button");
        Assert.assertTrue(vNextBOConfirmationDialog.isYesButtonDisplayed(), "Confirmation dialog hasn't had \"Yes\" button\"");
        Assert.assertTrue(vNextBOConfirmationDialog.isCloseButtonDisplayed(), "Confirmation dialog hasn't had \"Close\" x-icon");
        vNextBOConfirmationDialog.clickNoButton();
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(vNextBOInspectionsPageVerifications.isSavedAdvancedSearchFilterExists("AutomationSearchTest2"),
                "Saved searches list hasn't contained saved AutomationSearchTest search");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanDeleteSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        vNextBOInspectionsAdvancedSearchSteps.clickDeleteButton();
        VNextBOModalDialog vNextBOConfirmationDialog = new VNextBOModalDialog(webdriver);
        vNextBOConfirmationDialog.clickYesButton();
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertFalse(vNextBOInspectionsPageVerifications.isSavedAdvancedSearchFilterExists("AutomationSearchTest2"),
                "Saved search has been displayed in the list");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanSearchInspectionByCustomer(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.advancedSearchInspectionByCustomer("Amazing Nissan");
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Customer: Amazing Nissan"),
                "Search option under Search field hasn't been correct");
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            vNextBOInspectionsPageVerifications.isCustomerNameCorrect("Amazing Nissan");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanSearchInspectionByPONumber(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.advancedSearchInspectionByPONumber("123");
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("PO#: 123"),
                "Search option under Search field hasn't been correct");
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(vNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("PO#").contains("123"),
                    "Inspection has been found incorrectly");
        }

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
    public void verifyUserCanSearchInspectionByRONumber(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.advancedSearchInspectionByRONumber("123");
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("RO#: 123"),
                "Search option under Search field hasn't been correct");
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(vNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("RO#").contains("123"),
                    "Inspection has been found incorrectly");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
    public void verifyUserCanSearchInspectionByStockNumber(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.advancedSearchInspectionByStockNumber("123");
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Stock#: 123"),
                "Search option under Search field hasn't been correct");
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(vNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("Stock#").contains("123"),
                    "Inspection has been found incorrectly");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
    public void verifyUserCanSearchInspectionByVinNumber(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.advancedSearchInspectionByVIN("123");
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("VIN: 123"),
                "Search option under Search field hasn't been correct");
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(vNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("VIN").contains("123"),
                    "Inspection has been found incorrectly");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
    public void verifyUserCanSearchInspectionByInspectionNumber(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.advancedSearchInspectionByInspectionNumber("222");
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Inspection#: 222"),
                "Search option under Search field hasn't been correct");
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            for (String inspectionNumber: vNextBOInspectionsPageSteps.getNumbersOfAllInspectionsInTheList()
            ) {
                Assert.assertTrue(inspectionNumber.contains("222"),
                        "Record with incorrect number has been found");
            }
        }
        vNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionsPageSteps.clickClearFilterIcon();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
    public void verifyUserCanSearchInspectionByStatus(String rowID, String description, JSONObject testData) {

        VNextBOInspectionSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionSearchData.class);
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.advancedSearchInspectionByStatus(data.getValueForSearch());
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        if (!data.getValueForSearch().equals("All Active"))
        {
            Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Status: " + data.getValueForSearch()),
                    "Search option under Search field hasn't been correct");
        }
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            if (!data.getValueForSearch().equals("All Active")){
                for (String inspectionStatus: vNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList()
                ) {
                    Assert.assertEquals(inspectionStatus, data.getValueForSearch(),
                            "Record with incorrect status has been found");
                }
            }
            else {
                Assert.assertTrue(vNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                        "Active inspections have not been found");
            }
        }
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", "All Active");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
    public void verifyUserCanSearchInspectionByTimeFrame(String rowID, String description, JSONObject testData) {

        VNextBOInspectionSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionSearchData.class);
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.findInspectionByCustomTimeFrame(data.getValueForSearch());
        WaitUtilsWebDriver.waitForLoading();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Timeframe: " + data.getValueForSearch()),
                "Search option under Search field hasn't been correct");
        if (vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkDisplayed())
        {
            vNextBOInspectionsPageVerifications.isHowToCreateInspectionLinkTextCorrect(
                    vNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(vNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                    "Inspections have not been found");
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
    public void verifyUserCanSearchInspectionByCustomTimeFrame(String rowID, String description, JSONObject testData) {

        vNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", "All Active");
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", "Custom");
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("From", "1/1/2018");
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("To", "10/1/2019");
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        vNextBOInspectionsAdvancedSearchVerifications.isAdvancedSearchFormNotDisplayed();
        Assert.assertTrue(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().equals("From: 01/01/2018, To: 10/01/2019"),
                "Search option under Search field hasn't been correct");
        Assert.assertTrue(vNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                "Inspections have not been found");
    }
}
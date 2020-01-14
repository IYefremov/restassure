package com.cyberiansoft.test.vnextbo.testcases.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.inspections.VNextBOInspectionSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.dialogs.VNextBOModalDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.inspections.VNextBOInspectionsAdvancedSearchValidations;
import com.cyberiansoft.test.vnextbo.validations.inspections.VNextBOInspectionsPageValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNextBOInspectionsSearchTests extends BaseTestCase {

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
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getInspectionsSearchTD();
        VNextBOLeftMenuInteractions.selectInspectionsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanUseAdvancedSearchForInspectionsSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(VNextBOInspectionsPageValidations.verifySavedAdvancedSearchFilterExists("Archived"),
        "\"Saved searches list hasn't contained saved Archived search\");");
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsDisplayed();
        VNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseAdvancedSearchWindow(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsNotDisplayed(vNextBOInspectionAdvancedSearchForm);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionsUsingSearch(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("123");
        VNextBOInspectionsPageValidations.verifyClearFilterIconIsDisplayed();
        for (String inspectionName: VNextBOInspectionsPageSteps.getNamesOfAllInspectionsInTheList()
             ) {
            Assert.assertTrue(inspectionName.contains("123"), inspectionName + "hasn't contained searched text \"123\"");
        }
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Text: 123"),
                "Search option under Search field hasn't been correct");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteSearchOptions(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("123");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOInspectionsPageValidations.verifyClearFilterIconIsNotDisplayed();
        Assert.assertFalse(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Text: 123"),
                "Search option under Search field hasn't been cleaned up");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getSearchFieldValue().isEmpty(),
                "Search field hasn't been empty");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanFillAllAdvancedSearchFields(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getAllOptionsFromDropdownByName("Status"),
                expectedStatusDropDownOptions,"Status dropdown options set hasn't been correct");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getAllOptionsFromDropdownByName("Timeframe"),
                expectedTimeFrameDropDownOptions, "Timeframe dropdown options set hasn't been correct");
        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(valuesForSearch);
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        VNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanClearSearchOptions(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(valuesForSearch);
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
        VNextBOInspectionsAdvancedSearchSteps.clickCloseButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSaveSearchOptions(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.saveAdvancedSearch(valuesForSearch);
        VNextBOInspectionsPageValidations.verifyEditAdvancedSearchIconIsDisplayed();
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(VNextBOInspectionsPageValidations.verifySavedAdvancedSearchFilterExists("AutomationSearchTest"),
                "Saved searches list hasn't contained saved AutomationSearchTest search");
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanOpenAdvancedSearchWindowWithEditPencilIcon(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.saveAdvancedSearch(valuesForSearch);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsDisplayed();
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.saveAdvancedSearch(valuesForSearch);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(editedValuesForSearch);
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(editedValuesForSearch);
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSaveEditedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.saveAdvancedSearch(valuesForSearch);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOInspectionsAdvancedSearchSteps.setAllAdvancedSearchFields(editedValuesForSearch);
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickSaveButton();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOInspectionsPageValidations.verifyEditAdvancedSearchIconIsDisplayed();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsDisplayed();
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(editedValuesForSearch);
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotSaveEditedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.saveAdvancedSearch(valuesForSearch);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
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
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsDisplayed();
        VNextBOInspectionsAdvancedSearchValidations.verifyAllAdvancedSearchFormFields(valuesForSearch);
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelSavedAdvancedSearchDeleting(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.saveAdvancedSearch(valuesForSearch);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickDeleteButton();
        VNextBOModalDialogValidations.verifyDialogIsDisplayed();
        VNextBOModalDialogValidations.verifyNoButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyYesButtonIsDisplayed();
        VNextBOModalDialogValidations.verifyCloseButtonIsDisplayed();
        VNextBOModalDialogSteps.clickNoButton();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertTrue(VNextBOInspectionsPageValidations.verifySavedAdvancedSearchFilterExists("AutomationSearchTest"),
                "Saved searches list hasn't contained saved AutomationSearchTest search");
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        VNextBOInspectionsAdvancedSearchSteps.deleteSavedSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanDeleteSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.saveAdvancedSearch(valuesForSearch);
        VNextBOInspectionsPageSteps.clickEditAdvancedSearchIcon();
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.clickDeleteButton();
        VNextBOModalDialogSteps.clickYesButton();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        Utils.refreshPage();
        VNextBOLeftMenuInteractions.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertFalse(VNextBOInspectionsPageValidations.verifySavedAdvancedSearchFilterExists("AutomationSearchTest"),
                "Saved search has been displayed in the list");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByCustomer(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByCustomer("Best Customer");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Customer: Best Customer"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            VNextBOInspectionsPageValidations.verifyCustomerNameIsCorrect("Best Customer");
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByPONumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByPONumber("123");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("PO#: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("PO#").contains("123"),
                    "Inspection has been found incorrectly");
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByRONumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByRONumber("123");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("RO#: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("RO#").contains("123"),
                    "Inspection has been found incorrectly");
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByStockNumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStockNumber("123");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Stock#: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("Stock#").contains("123"),
                    "Inspection has been found incorrectly");
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByVinNumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByVIN("123");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("VIN: 123"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getSelectedInspectionParameterValueByName("VIN").contains("123"),
                    "Inspection has been found incorrectly");
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByInspectionNumber(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByInspectionNumber("222");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Inspection#: 222"),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            for (String inspectionNumber: VNextBOInspectionsPageSteps.getNumbersOfAllInspectionsInTheList()
            ) {
                Assert.assertTrue(inspectionNumber.contains("222"),
                        "Record with incorrect number has been found");
            }
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByStatus(String rowID, String description, JSONObject testData) {

        VNextBOInspectionSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionSearchData.class);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStatus(data.getValueForSearch());
        if (!data.getValueForSearch().equals("All Active"))
        {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Status: " + data.getValueForSearch()),
                    "Search option under Search field hasn't been correct");
        }
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
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
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByTimeFrame(String rowID, String description, JSONObject testData) {

        VNextBOInspectionSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOInspectionSearchData.class);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.searchInspectionByCustomTimeFrame(data.getValueForSearch());
        Assert.assertTrue(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue().contains("Timeframe: " + data.getValueForSearch()),
                "Search option under Search field hasn't been correct");
        if (VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkIsDisplayed())
        {
            VNextBOInspectionsPageValidations.verifyHowToCreateInspectionLinkTextIsCorrect(
                    VNextBOInspectionsPageSteps.getNotFoundInspectionMessage());
        }
        else {
            Assert.assertTrue(VNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                    "inspections have not been found");
        }
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchInspectionByCustomTimeFrame(String rowID, String description, JSONObject testData) {

        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        vNextBOInspectionAdvancedSearchForm = new VNextBOInspectionAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Status", "All Active");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchDropDownField("Timeframe", "Custom");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("From", "1/1/2018");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("To", "10/1/2019");
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        VNextBOInspectionsAdvancedSearchValidations.verifyAdvancedSearchFormIsNotDisplayed(vNextBOInspectionAdvancedSearchForm);
        Assert.assertEquals("From: 01/01/2018, To: 10/01/2019", VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue(),
                "Search option under Search field hasn't been correct");
        Assert.assertTrue(VNextBOInspectionsPageSteps.getStatusesOfAllInspectionsInTheList().size() > 0,
                "inspections have not been found");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }
}
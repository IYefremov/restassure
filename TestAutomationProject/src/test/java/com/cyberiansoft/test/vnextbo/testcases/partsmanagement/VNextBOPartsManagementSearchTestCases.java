package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsManagement.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsManagementWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsOrdersListPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOAdvancedSearchDialogValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsManagementWebPageValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsOrdersListPanelValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;

public class VNextBOPartsManagementSearchTestCases extends BaseTestCase {

    VNextBOPartsManagementSearchData searchDataForEdit;
    VNextBOPartsManagementSearchData editedSearchData;

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementSearchTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSearchROUsingSearch(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("123");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersAreDisplayed();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Text: 123");
        VNextBOSearchPanelValidations.verifyXIconIsDisplayed(true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanDeleteSearchOptions(String rowID, String description, JSONObject testData) {

        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelValidations.verifyXIconIsDisplayed(false);
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 2)
    public void verifyUserCanUseAdvancedSearchForROSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogValidations.verifyAdvancedSearchIsDisplayedWithAllElements();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 3)
    public void verifyUserCanCloseAdvancedSearchFormByClickingXIcon(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.closeAdvancedSearchForm();
        VNextBOAdvancedSearchDialogValidations.verifyAdvancedSearchFormIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 4)
    public void verifyUserCanCloseAdvancedSearchFormByClickingOutside(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(1);
        VNextBOAdvancedSearchDialogValidations.verifyAdvancedSearchFormIsDisplayed(false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 5)
    public void verifyUserCanSearchROByCustomerUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setCustomerField("Mike's Techs");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Customer: Mike's Techs");
        VNextBOPartsOrdersListPanelValidations.verifyCustomerNamesAreCorrect("Mike's Techs");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 6)
    public void verifyUserCanSearchROByPhaseUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setPhaseField("PDR Station");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Phase: PDR Station");
        VNextBOPartsOrdersListPanelValidations.verifyPhasesAreCorrect("PDR Station");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 7)
    public void verifyUserCanSearchROByWONumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setWONumberField("O-062-00068");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("WO#: O-062-00068");
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect("O-062-00068");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 8)
    public void verifyUserCanSearchROByWOTypeUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setWOTypeField("Best");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("WO Type: Best");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersAreDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 9)
    public void verifyUserCanSearchROByStockNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setStockNumberField("Autotests");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Stock#: Autotests");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersStockNumbersAreCorrect("Autotests");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 10)
    public void verifyUserCanSearchROByETAFromUsingAdvancedSearch(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setEtaFromField("10/30/2018");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("ETA From: 10/30/2018");
        VNextBOPartsDetailsPanelValidations.verifyEtaDateIsCorrectAfterSearch("10/30/2018", "ETA From");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 11)
    public void verifyUserCanSearchROByETAToUsingAdvancedSearch(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setEtaToField("10/30/2019");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("ETA To: 10/30/2019");
        VNextBOPartsDetailsPanelValidations.verifyEtaDateIsCorrectAfterSearch("10/30/2019", "ETA To");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 12)
    public void verifyUserCanSearchROByVINNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setVINField("11111");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("VIN: 11111");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersVinNumbersAreCorrect("11111");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 13)
    public void verifyUserCanSearchROByOEMNumUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setPartNumberField("My OEM");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Part#: My OEM");
        VNextBOPartsDetailsPanelValidations.verifyPartNumberIsCorrect(0, "My OEM");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 14)
    public void verifyUserCanSearchROByNotesUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setNotesField("Autotest");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Notes: Autotest");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersAreDisplayed();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 15)
    public void verifyUserCanSearchROByOrderedFromUsingAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setOrderedFromField("Test Team");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Ordered From: Test Team");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersVendorsAreCorrect("Test Team");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 16)
    public void verifyUserCanSearchROByFromOptionUsingAdvancedSearch(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setFromField("12/14/2016");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("From: 12/14/2016");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersDatesAreCorrectAfterSearch("12/14/2016", "From");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 17)
    public void verifyUserCanSearchROByToOptionUsingAdvancedSearch(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setToField("12/14/2018");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("To: 12/14/2018");
        VNextBOPartsOrdersListPanelValidations.verifyOrdersDatesAreCorrectAfterSearch("12/14/2018", "To");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 18)
    public void verifyUserCanSearchROByCorePrice(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setCorePriceCheckbox();
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Core Price: Parts with Core Price");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOnePartHasCorePriceMoreThanZero();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 19)
    public void verifyUserCanSearchROByLaborCredit(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setLaborCreditCheckbox();
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSearchPanelValidations.verifySearchFilterTextIsCorrect("Labor Credit: Parts with Labor Credit");
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOnePartHasLaborCreditMoreThanZero();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 20)
    public void verifyUserCanSearchROByCoreStatus(String rowID, String description, JSONObject testData) throws ParseException {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setLaborCreditCheckbox();
        VNextBOAdvancedSearchDialogSteps.setCoreStatusField("Return to Vendor (RTV)");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOnePartHasCorePriceMoreThanZero();
        VNextBOPartsDetailsPanelValidations.verifyAtLeastOnePartHasCorrectCoreStatus("RTV Complete");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 21)
    public void verifyUserCanFillAllFieldsOfAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);
        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setAllFields(data);
        VNextBOAdvancedSearchDialogValidations.verifyAdvancedSearchFieldsHaveCorrectData(data);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 22)
    public void verifyUserCanClearAllEnteredFieldsOfAdvancedSearchDialog(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);
        VNextBOAdvancedSearchDialogSteps.clearAllFields();
        VNextBOAdvancedSearchDialogValidations.verifyAdvancedSearchFieldsHaveCorrectData(data);
        VNextBOAdvancedSearchDialogSteps.closeAdvancedSearchForm();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 23)
    public void verifyUserCanSearchROUsingOptionsOfAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementSearchData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);
        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setAllFields(data);
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOPartsOrdersListPanelValidations.verifyOrdersListEmptyStateIsCorrect();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 24)
    public void verifyUserCanSaveAllOptionsOfAdvancedSearchDialog(String rowID, String description, JSONObject testData) {

        searchDataForEdit = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);
        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setAllFields(searchDataForEdit);
        VNextBOAdvancedSearchDialogSteps.saveSearch();
        VNextBOPartsOrdersListPanelValidations.verifyOrdersListEmptyStateIsCorrect();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOPartsManagementWebPageValidations.verifySavedSearchIsPresentedInTheList(searchDataForEdit.getSearchName(), true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 25)
    public void verifyUserCanChangeValuesOfTheSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        editedSearchData = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementSearchData.class);
        VNextBOPartsManagementWebPageSteps.openSavedAdvancedSearch(searchDataForEdit.getSearchName());
        VNextBOAdvancedSearchDialogSteps.setAllFields(editedSearchData);
        VNextBOAdvancedSearchDialogSteps.saveSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Utils.refreshPage();
        VNextBOPartsManagementWebPageSteps.waitUntilPartsManagementPageIsLoaded();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOPartsManagementWebPageValidations.verifySavedSearchIsPresentedInTheList(editedSearchData.getSearchName(), true);
        VNextBOPartsManagementWebPageSteps.openSavedAdvancedSearch(editedSearchData.getSearchName());
        VNextBOAdvancedSearchDialogValidations.verifyAdvancedSearchFieldsHaveCorrectData(editedSearchData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 26)
    public void verifyUserCanCancelSavedSearchDeletingWithXIcon(String rowID, String description, JSONObject testData) {

        VNextBOAdvancedSearchDialogSteps.clickDeleteButton();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOPartsManagementWebPageValidations.verifySavedSearchIsPresentedInTheList(editedSearchData.getSearchName(), true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 27)
    public void verifyUserCanCancelSavedSearchDeletingWithNoButton(String rowID, String description, JSONObject testData) {

        VNextBOAdvancedSearchDialogSteps.clickDeleteButton();
        VNextBOModalDialogSteps.clickCloseButton();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOPartsManagementWebPageValidations.verifySavedSearchIsPresentedInTheList(editedSearchData.getSearchName(), true);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 28)
    public void verifyUserCanDeleteSavedAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOAdvancedSearchDialogSteps.clickDeleteButton();
        VNextBOModalDialogSteps.clickYesButton();
        Utils.refreshPage();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOPartsManagementWebPageValidations.verifySavedSearchIsPresentedInTheList(editedSearchData.getSearchName(), false);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 29)
    public void verifyUserCanSearchROUsingSavedOptionsOfAdvancedSearch(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setWONumberField("O-000-149577");
        VNextBOAdvancedSearchDialogSteps.setSearchName("Test91129");
        VNextBOAdvancedSearchDialogSteps.saveSearch();
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOPartsManagementWebPageSteps.openSavedAdvancedSearch("Test91129");
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOPartsOrdersListPanelValidations.verifyWoNumbersAreCorrect("O-000-149577");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOSearchPanelSteps.openAdvancedSearchForm();
        VNextBOPartsManagementWebPageSteps.openSavedAdvancedSearch("Test91129");
        VNextBOAdvancedSearchDialogSteps.clickDeleteButton();
        VNextBOModalDialogSteps.clickYesButton();
    }
}
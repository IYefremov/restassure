package com.cyberiansoft.test.vnextbo.testcases.servicerequests;

import com.cyberiansoft.test.dataclasses.vNextBO.alerts.VNextBOAlertMessages;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRData;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOReactSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.VNextBOSRTableSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRHeaderSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs.VNextBOSRAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.commonobjects.VNextBOReactSearchPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.VNextBOSRTableValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.details.VNextBOSRContactsValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.details.VNextBOSRGeneralInfoValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.details.VNextBOSRVehicleInfoValidations;
import com.cyberiansoft.test.vnextbo.validations.servicerequests.dialogs.VNextBOSRAdvancedSearchDialogValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.cyberiansoft.test.vnextbo.utils.WebDriverUtils.webdriverGotoWebPage;

public class VNextBOSRAdvancedSearchTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getSRAdvancedSearchTD();
    }

    @BeforeMethod
    public void goToPage() {
        webdriverGotoWebPage(BaseTestCase.getBackOfficeURL());
        VNextBOLeftMenuInteractions.selectServiceRequestsMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByHasThisText(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String hasThisText = data.getSearchData().getHasThisText();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogValidations.verifyFieldsWithAutoCompleteContainPlaceholders();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField(hasThisText);
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        final List<String> srNumValues = VNextBOSRTableSteps.getSRNumValues();
        VNextBOReactSearchPanelSteps.clearSearchFilter();
        VNextBOReactSearchPanelValidations.verifySearchInputFieldIsEmpty();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField("     " + hasThisText);
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        Assert.assertEquals(srNumValues, VNextBOSRTableSteps.getSRNumValues(), "The SRs list is not the same ");
        VNextBOReactSearchPanelSteps.clearSearchFilter();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField(hasThisText + "     ");
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        Assert.assertEquals(srNumValues, VNextBOSRTableSteps.getSRNumValues(), "The SRs list is not the same ");
        VNextBOReactSearchPanelSteps.clearSearchFilter();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField("test123test432lol");
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOSRTableValidations.verifyNotFoundNotificationIsDisplayed();
        VNextBOReactSearchPanelSteps.clearSearchFilter();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheHasThisTextFieldDuplicatesTheValueOfTheFreeTextSearch(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField(data.getSearchData().getHasThisText());
        VNextBOSRAdvancedSearchDialogSteps.clearHasThisTextField();
        VNextBOReactSearchPanelSteps.updateSearchInputFieldValue("");
        VNextBOReactSearchPanelValidations.verifySearchInputFieldValue("");
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifySearchInputFieldIsEmpty();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomerUsingAutocomplete(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String customer = data.getSearchData().getCustomer();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomerUsingAutoComplete("vd", customer);
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueCustomerNameFields()
                .forEach(value -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(value, customer));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByOwnerUsingAutocomplete(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String owner = data.getSearchData().getOwner();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setOwnerUsingAutoComplete("br", owner);
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRContactsValidations.verifyOwnerNameIsDisplayed(owner);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByAdvisorUsingAutocomplete(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final VNextBOSRSearchData searchData = data.getSearchData();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setAdvisorUsingAutoComplete("Adv", searchData.getAdvisor());
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(searchData);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRContactsValidations.verifyAdvisorNameIsDisplayed(searchData.getAdvisor());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByEmployeeUsingAutocomplete(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String employee = data.getSearchData().getEmployee();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setEmployeeUsingAutoComplete("drake", employee);
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueAssignedNameFields()
                .forEach(value -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(value, employee));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatus(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String status = data.getSearchData().getStatus();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setStatus(status);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueStatusesFields().forEach(actual ->
                VNextBOSRTableValidations.verifySRStatusIsDisplayed(actual, data.getExpectedStatus()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTeam(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String team = data.getSearchData().getTeam();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setTeam("T", team);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueAssignedNameFields()
                .forEach(value -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(value, team));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByType(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String srType = data.getSearchData().getSrType();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setSrType("R", srType);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueSrTypesFields()
                .forEach(value -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(value, srType));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRoNum(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String roNum = data.getSearchData().getRoNum();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setRoNum(roNum);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRGeneralInfoValidations.verifyRoIsDisplayed(roNum);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStockNum(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String stockNum = data.getSearchData().getStockNum();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setStockNum(stockNum);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueStockNumbersFields()
                .forEach(stock -> VNextBOSRTableValidations.verifySRContainingValueIsDisplayed(stock, stockNum));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByVinNum(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final VNextBOSRSearchData searchData = data.getSearchData();
        final String[] vinSearchValues = searchData.getSearchValues();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[0]);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRVehicleInfoValidations.verifyVinIsFinishedWithValue(vinSearchValues[0]);
        VNextBOSRHeaderSteps.returnToSrPage();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(searchData);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[1]);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRVehicleInfoValidations.verifyVinIsFinishedWithValue(vinSearchValues[1]);
        VNextBOSRHeaderSteps.returnToSrPage();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[2]);
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR_MESSAGE);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[3]);
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR_MESSAGE);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[4]);
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR_MESSAGE);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[5]);
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR_MESSAGE);

        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[6]);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOSRTableSteps.openSRDetailsPage(0);
        VNextBOSRVehicleInfoValidations.verifyVinIsFinishedWithValue(vinSearchValues[6]);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchBySrNum(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final VNextBOSRSearchData searchData = data.getSearchData();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(searchData);
        VNextBOSRAdvancedSearchDialogSteps.setSrNum(searchData.getSrNum());
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getSRNumValues()
                .forEach(sr -> VNextBOSRTableValidations.verifySRIsDisplayed(sr, searchData.getSrNum()));
        VNextBOReactSearchPanelSteps.clearSearchFilter();
        Arrays.asList(searchData.getSearchValues()).forEach(value -> VNextBOSRTableSteps.searchBySrNum(value, searchData));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameToday(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_TODAY);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyTimeFrameToday();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByTimeFrameWeekToDate(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_WEEKTODATE);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyTimeFrameWeekToDate();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastWeekTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTWEEK);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyLastWeekTimeFrame();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByMonthToDateTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_MONTHTODATE);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyMonthToDateTimeFrame();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastMonthTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTMONTH);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyLastMonthTimeFrame();
    }
}

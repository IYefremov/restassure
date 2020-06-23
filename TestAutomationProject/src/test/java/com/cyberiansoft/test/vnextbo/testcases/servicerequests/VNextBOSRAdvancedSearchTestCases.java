package com.cyberiansoft.test.vnextbo.testcases.servicerequests;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.steps.menu.BOMenuSteps;
import com.cyberiansoft.test.bo.steps.monitor.repairorders.BOROPageSteps;
import com.cyberiansoft.test.bo.steps.monitor.repairorders.BOROSearchBlockSteps;
import com.cyberiansoft.test.bo.steps.operations.servicerequestslist.BOSRListBlockSteps;
import com.cyberiansoft.test.bo.steps.operations.servicerequestslist.BOSRSearchSteps;
import com.cyberiansoft.test.bo.steps.search.BOSearchSteps;
import com.cyberiansoft.test.bo.validations.monitor.BOROTableValidations;
import com.cyberiansoft.test.bo.validations.operations.servicerequestslist.BOSRDetailsHeaderPanelValidations;
import com.cyberiansoft.test.bo.validations.operations.servicerequestslist.BOSRListValidations;
import com.cyberiansoft.test.dataclasses.vNextBO.alerts.VNextBOAlertMessages;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRData;
import com.cyberiansoft.test.dataclasses.vNextBO.servicerequests.VNextBOSRSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.enums.servicerequests.SRPhase;
import com.cyberiansoft.test.enums.servicerequests.ServiceRequestStatus;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOReactSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.VNextBOSRTableSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRHeaderSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.details.VNextBOSRWOSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs.VNextBOSRAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.servicerequests.dialogs.VNextBOSRWorkOrdersDialogSteps;
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

    //todo bug https://cyb.tpondemand.com/restui/board.aspx?#page=bug/134873
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
        final List<String> srNumValues = VNextBOSRTableSteps.getSRValuesList();
        VNextBOReactSearchPanelSteps.clearSearchFilter();
        VNextBOReactSearchPanelValidations.verifySearchInputFieldIsEmpty();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField("     " + hasThisText);
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        Assert.assertEquals(srNumValues, VNextBOSRTableSteps.getSRValuesList(), "The SRs list is not the same ");
        VNextBOReactSearchPanelSteps.clearSearchFilter();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField(hasThisText + "     ");
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        Assert.assertEquals(srNumValues, VNextBOSRTableSteps.getSRValuesList(), "The SRs list is not the same ");
        VNextBOReactSearchPanelSteps.clearSearchFilter();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setHasThisTextFieldAndVerifySearchInputField("test123test432lol");
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOSRTableValidations.verifyNotFoundNotificationIsDisplayed();
        VNextBOReactSearchPanelSteps.clearSearchFilter();
    }

    //todo bug https://cyb.tpondemand.com/restui/board.aspx?#page=bug/134873
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
                VNextBOSRTableValidations.verifySRStatusIsDisplayed(actual, data.getExpectedValue()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusWaitingForAcceptance(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String status = data.getSearchData().getStatus();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(data.getSearchData());
        VNextBOSRAdvancedSearchDialogSteps.setStatus(status);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueStatusesFields().forEach(actual ->
                VNextBOSRTableValidations.verifySRStatusIsDisplayed(actual, data.getExpectedValue()));
        VNextBOSRTableValidations.verifyAcceptButtonsAreEnabled();
        VNextBOSRTableValidations.verifyRejectButtonsAreEnabled();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusReadyForDelivery(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String status = data.getSearchData().getStatus();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(data.getSearchData());
        VNextBOSRAdvancedSearchDialogSteps.setStatus(status);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueStatusBadgeLettersFields().forEach(badge ->
                VNextBOSRTableValidations.verifySRIsDisplayed(badge, data.getExpectedValue()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusAcceptedNotInspected(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String status = data.getSearchData().getStatus();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setStatus(status);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueStatusesFields().forEach(actual ->
                VNextBOSRTableValidations.verifySRStatusIsNotEqual(actual, ServiceRequestStatus.REJECTED));
        VNextBOSRTableValidations.verifyInspectionsDocumentsListIsEmpty();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusNoWorkOrder(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String status = data.getSearchData().getStatus();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setStatus(status);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyWoDocumentsListIsEmpty();
    }

    //todo bug https://cyb.tpondemand.com/restui/board.aspx?#page=bug/133535
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusWorkInProcessNotInvoiced(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String status = data.getSearchData().getStatus();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setStatus(status);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyWoListContainsDocuments();
        VNextBOSRTableSteps.getUniqueStatusBadgeLettersFields().forEach(badge ->
                VNextBOSRTableValidations.verifySRIsDisplayed(badge, data.getExpectedValue()));
        //        VNextBOSRTableValidations.verifyInvoicesDocumentsListIsEmpty(); todo uncomment after bug fix
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusCheckedInNotInspected(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final VNextBOSRSearchData searchData = data.getSearchData();
        final String from = CustomDateProvider.getDateMinusDays(searchData.getFromDate(), 1);
        final String to = CustomDateProvider.getDatePlusDays(searchData.getToDate(), 1);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(searchData);
        VNextBOSRAdvancedSearchDialogSteps.setStatus(searchData.getStatus());
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
//        VNextBOSRTableValidations.verifyInspectionsDocumentsListIsEmpty(); todo uncomment after bug fix
        final String srNumber = VNextBOSRTableSteps.getRandomSR();
        VNextBOHomeWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.OPERATIONS, SubMenu.SERVICE_REQUESTS_LIST);
        BOSearchSteps.expandSearchTab();
        BOSRSearchSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM);
        BOSRSearchSteps.setFromAndToTimeFrameFields(from, to);
        BOSRSearchSteps.setFreeText(srNumber);
        BOSRSearchSteps.search();
        BOSRListValidations.verifySRIsDisplayed(srNumber);
        BOSRListBlockSteps.openSRDetailsBlock(0);
        BOSRDetailsHeaderPanelValidations.verifyCheckInButtonIsNotDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusNotCheckedIn(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final String status = data.getSearchData().getStatus();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setStatus(status);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());

        final String srNumber = VNextBOSRTableSteps.getRandomSR();
        VNextBOHomeWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.OPERATIONS, SubMenu.SERVICE_REQUESTS_LIST);
        BOSearchSteps.expandSearchTab();
        BOSRSearchSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        BOSRSearchSteps.setFreeText(srNumber);
        BOSRSearchSteps.search();
        BOSRListValidations.verifySRIsDisplayed(srNumber);
        BOSRListBlockSteps.openSRDetailsBlock(0);
        BOSRDetailsHeaderPanelValidations.verifyUndoCheckInButtonIsNotDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByStatusAppointmentRequired(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final VNextBOSRSearchData searchData = data.getSearchData();
        final String from = CustomDateProvider.getDateMinusDays(searchData.getFromDate(), 1);
        final String to = CustomDateProvider.getDatePlusDays(searchData.getToDate(), 1);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(searchData);
        VNextBOSRAdvancedSearchDialogSteps.setStatus(searchData.getStatus());
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        final String srNumber = VNextBOSRTableSteps.getRandomSR();
        VNextBOHomeWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.OPERATIONS, SubMenu.SERVICE_REQUESTS_LIST);
        BOSearchSteps.expandSearchTab();
        BOSRSearchSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM);
        BOSRSearchSteps.setFromAndToTimeFrameFields(from, to);
        BOSRSearchSteps.setFreeText(srNumber);
        BOSRSearchSteps.search();
        BOSRListValidations.verifySRIsDisplayed(srNumber);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByRepairLocation(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);
        final VNextBOSRSearchData searchData = data.getSearchData();

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(searchData);
        VNextBOSRAdvancedSearchDialogSteps.setRepairLocation("R", searchData.getRepairLocation());
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        final int index = VNextBOSRTableSteps.getRandomSRIndex();
        VNextBOSRTableSteps.openSRDetailsPage(index);
        VNextBOSRWOSteps.openWOEditDialog();
        final String woNumber = VNextBOSRWorkOrdersDialogSteps.getRandomWONumber();
        VNextBOHomeWebPageSteps.clickLogo();
        VNextBOHomeWebPageSteps.clickAccessReconProBOLink();
        BOMenuSteps.open(Menu.MONITOR, SubMenu.REPAIR_ORDERS);
        BOROPageSteps.waitForRoPageToBeDisplayed();
        BOSearchSteps.expandSearchTab();
        BOROSearchBlockSteps.setLocation(searchData.getRepairLocation());
        BOROSearchBlockSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM);
        BOROSearchBlockSteps.setFromAndToTimeFrameFields(searchData);
        BOROSearchBlockSteps.setRepairStatus(searchData.getStatus());
        BOROSearchBlockSteps.setWoNum(woNumber);
        BOSearchSteps.search();
        BOROTableValidations.verifyWOIsDisplayed(woNumber);
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
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[3]);
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[4]);
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR);
        VNextBOSRAdvancedSearchDialogSteps.setVinNum(vinSearchValues[5]);
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyVinErrorMessageIsDisplayed(VNextBOAlertMessages.VIN_ERROR);

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
        VNextBOSRTableSteps.getSRValuesList()
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLast30DaysTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_30_DAYS);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyLast30DaysTimeFrame();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLast90DaysTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_90_DAYS);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyLast90DaysTimeFrame();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByYearToDateTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_YEARTODATE);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyYearToDateTimeFrame();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByLastYearTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableValidations.verifyLastYearTimeFrame();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByCustomTimeFrame(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOSRAdvancedSearchDialogSteps.clearFromDateField();
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyFromDateErrorMessageIsDisplayed(VNextBOAlertMessages.FROM_DATE_ERROR);
        VNextBOSRAdvancedSearchDialogSteps.clearToDateField();
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyFromDateErrorMessageIsDisplayed(VNextBOAlertMessages.FROM_DATE_ERROR);
        VNextBOSRAdvancedSearchDialogValidations.verifyToDateErrorMessageIsDisplayed(VNextBOAlertMessages.TO_DATE_ERROR);
        VNextBOSRAdvancedSearchDialogSteps.setFromDateField(CustomDateProvider.getCurrentDateInFullFormat(true));
        VNextBOSRAdvancedSearchDialogValidations.verifyFromDateErrorMessageIsNotDisplayed();
        VNextBOSRAdvancedSearchDialogValidations.verifyToDateErrorMessageIsDisplayed(VNextBOAlertMessages.TO_DATE_ERROR);
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogValidations.verifyFromDateFieldIsNotDisplayed();
        VNextBOSRAdvancedSearchDialogValidations.verifyToDateFieldIsNotDisplayed();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame("05/30/2020", data.getSearchData().getToDate());
        VNextBOSRAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOSRAdvancedSearchDialogValidations.verifyFromDateErrorMessageIsDisplayed(VNextBOAlertMessages.FROM_LESS_THAN_TO_ERROR);
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(data.getSearchData());
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOSRTableValidations.verifyCustomTimeFrame(data.getSearchData());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhaseAcceptance(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setCustomTimeFrame(data.getSearchData());
        VNextBOSRAdvancedSearchDialogSteps.setPhase(SRPhase.ACCEPTANCE.getValue());
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueStatusBadgeLettersFields().forEach(badge ->
                VNextBOSRTableValidations.verifySRIsDisplayed(badge, data.getExpectedValue()));
        VNextBOSRTableSteps.getUniqueStatusesFields().forEach(actual ->
                VNextBOSRTableValidations.verifySRIsDisplayed(actual, ServiceRequestStatus.PROPOSED.getValue()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSearchByPhase(String rowID, String description, JSONObject testData) {
        VNextBOSRData data = JSonDataParser.getTestDataFromJson(testData, VNextBOSRData.class);

        VNextBOReactSearchPanelSteps.openAdvancedSearchForm();
        VNextBOSRAdvancedSearchDialogSteps.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR);
        VNextBOSRAdvancedSearchDialogSteps.setPhase(data.getSearchData().getPhase());
        VNextBOSRAdvancedSearchDialogSteps.search();
        VNextBOReactSearchPanelValidations.verifyFilterInfoTextIsDisplayed(data.getInfoText());
        VNextBOSRTableSteps.getUniqueStatusBadgeLettersFields().forEach(badge ->
                VNextBOSRTableValidations.verifySRIsDisplayed(badge, data.getExpectedValue()));
    }
}

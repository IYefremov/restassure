package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOTimeReportingDialogSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOTimeReportingDialogValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VNextBOMonitorTimeReportingTestCases extends BaseTestCase {

    final String TEST_ORDER_NUMBER = "O-113-00063";
    final String TEST_LOCATION = "Rozstalnoy_location";
    final String CURRENT_DATETIME = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a"));
    final String CURRENT_DATE = LocalDateTime.now().format(DateTimeFormatter.ofPattern("M/d/yyyy"));
    final String YESTERDAY = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a"));
    final String DAY_BEFORE_YESTERDAY = LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a"));
    final String CURRENT_DAY_PLUS_YEAR = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("M/d/yyyy hh:mm a"));

	@BeforeClass
	public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTimeReportingTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsStepsNew.openTimeReporting();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeTimeReportTabAndSortRecords(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOTimeReportingDialogValidations.verifyTimeReportingDialogIsDisplayed(true);
        VNextBOTimeReportingDialogSteps.clickStartedOnlyCheckbox();
        VNextBOTimeReportingDialogValidations.verifyStartedOnlyCheckBox(true);
        VNextBOTimeReportingDialogValidations.verifyStopDatesAreCorrect("");
        VNextBOTimeReportingDialogSteps.selectTechnician(data.getTechnician());
        VNextBOTimeReportingDialogValidations.verifyTechniciansAreCorrect(data.getTechnician());
        VNextBOTimeReportingDialogSteps.selectPhase(data.getPhase());
        List<String> displayedServices = VNextBOTimeReportingDialogSteps.getDisplayedServicesList();
        VNextBOTimeReportingDialogSteps.closeDialog();
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        for (String service: displayedServices) {
            VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(service, true);
        }
        VNextBORODetailsStepsNew.openTimeReporting();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAndDeleteNewTimeRecord(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        int initialRecordsAmount = VNextBOTimeReportingDialogSteps.getSavedRecordsNumber();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogValidations.verifyNotSavedRecordIsDisplayed(true);
        VNextBOTimeReportingDialogSteps.cancelAddingNewRecord();
        VNextBOTimeReportingDialogValidations.verifyNotSavedRecordIsDisplayed(false);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setStopDateTimeForNewRecord(CURRENT_DAY_PLUS_YEAR);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumberAndCancelWithCancelButton(4);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumberAndCancelWithXIcon(4);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(4);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotAddNewRecordWithoutRequiredFields(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        int initialRecordsAmount = VNextBOTimeReportingDialogSteps.getSavedRecordsNumber();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
        VNextBOTimeReportingDialogValidations.verifyStartedDateFieldIsHighLighted();
        VNextBOTimeReportingDialogValidations.verifyTechnicianFieldIsHighLighted();
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
        VNextBOTimeReportingDialogValidations.verifyTechnicianFieldIsHighLighted();
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord("");
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
        VNextBOTimeReportingDialogValidations.verifyStartedDateFieldIsHighLighted();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanStartStopServices(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOTimeReportingDialogSteps.changeStopDateByTimeRecordNumber(3, CURRENT_DATETIME);
        VNextBOTimeReportingDialogValidations.verifyTimerIconIsDisplayedByRecordNumber(3, false);
        VNextBOTimeReportingDialogSteps.changeStopDateByTimeRecordNumber(3, "");
        VNextBOTimeReportingDialogValidations.verifyTimerIconIsDisplayedByRecordNumber(3, true);
        VNextBOTimeReportingDialogValidations.verifyTotalSumForServiceIsCorrect(data.getService());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanNotStartSimultaneouslyTwoOrMoreTimeRecordsForSameTechnician(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        int initialRecordsAmount = VNextBOTimeReportingDialogSteps.getSavedRecordsNumber();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.changeStopDateByTimeRecordNumber(4, CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(YESTERDAY);
        VNextBOTimeReportingDialogSteps.setStopDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(4);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanStartTwoOrMoreTimeReportsForDifferentTechnicians(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        int initialRecordsAmount = VNextBOTimeReportingDialogSteps.getSavedRecordsNumber();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician1());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 2);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(4);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(4);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanStartTimeReportsForOneTechnicianInTheSameTimeForDifferentServices(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        int initialRecordsAmount = VNextBOTimeReportingDialogSteps.getSavedRecordsNumber();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogSteps.addNewRecordForService("rozstalnoy_disable_labor");
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 2);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(4);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(6);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanEditValuesOfTimeReport(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTimeForNewRecord(CURRENT_DATETIME);
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogSteps.changeTechnicianByTimeRecordNumber(4, data.getTechnician1());
        VNextBOTimeReportingDialogValidations.verifyTechnicianIsCorrectByRecordNumber(4, data.getTechnician1());
        VNextBOTimeReportingDialogSteps.clearStartDateByTimeRecordNumber(4);
        VNextBOTimeReportingDialogSteps.closeErrorDialog();
        VNextBOTimeReportingDialogValidations.verifyStartDateIsCorrectByRecordNumber(4, CURRENT_DATE);
        VNextBOTimeReportingDialogSteps.changeStopDateByTimeRecordNumber(4, DAY_BEFORE_YESTERDAY);
        VNextBOTimeReportingDialogSteps.closeErrorDialog();
        VNextBOTimeReportingDialogValidations.verifyStopDateIsCorrectByRecordNumber(4, "");
        VNextBOTimeReportingDialogSteps.changeStopDateByTimeRecordNumber(4, YESTERDAY);
        VNextBOTimeReportingDialogSteps.closeErrorDialog();
        VNextBOTimeReportingDialogValidations.verifyStopDateIsCorrectByRecordNumber(4, "");
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(4);
    }
}
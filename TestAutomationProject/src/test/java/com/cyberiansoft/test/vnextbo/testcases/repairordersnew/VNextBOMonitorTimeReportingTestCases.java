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

import java.util.List;

public class VNextBOMonitorTimeReportingTestCases extends BaseTestCase {

    final String TEST_ORDER_NUMBER = "O-113-00063";
    final String TEST_LOCATION = "Rozstalnoy_location";

	@BeforeClass
	public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTimeReportingTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeTimeReportTabAndSortRecords(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.openTimeReporting();
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
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddAndDeleteNewTimeRecord(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBORODetailsStepsNew.openTimeReporting();
        int initialRecordsAmount = VNextBOTimeReportingDialogSteps.getSavedRecordsNumber();
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogValidations.verifyNotSavedRecordIsDisplayed(true);
        VNextBOTimeReportingDialogSteps.cancelAddingNewRecord();
        VNextBOTimeReportingDialogValidations.verifyNotSavedRecordIsDisplayed(false);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
        VNextBOTimeReportingDialogSteps.addNewRecordForService(data.getService());
        VNextBOTimeReportingDialogSteps.setStartDateTime();
        VNextBOTimeReportingDialogSteps.setStopDateTime();
        VNextBOTimeReportingDialogSteps.setTechnicianForNewRecord(data.getTechnician());
        VNextBOTimeReportingDialogSteps.saveNewRecord();
        VNextBOTimeReportingDialogValidations.verifyNotSavedRecordIsDisplayed(false);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumberAndCancelWithCancelButton(4);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumberAndCancelWithXIcon(4);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount + 1);
        VNextBOTimeReportingDialogSteps.deleteRecordByNumber(4);
        VNextBOTimeReportingDialogValidations.verifySavedRecordsNumberIsCorrect(initialRecordsAmount);
    }
}
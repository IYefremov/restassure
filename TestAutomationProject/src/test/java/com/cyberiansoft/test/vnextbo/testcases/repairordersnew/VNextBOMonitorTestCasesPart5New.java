package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBORODetailsStepsNew;
import com.cyberiansoft.test.vnextbo.steps.repairordersnew.VNextBOROPageStepsNew;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBORODetailsValidationsNew;
import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorTestCasesPart5New extends BaseTestCase {

    final String TEST_ORDER_NUMBER = "O-000-147163";
    final String TEST_LOCATION = "Best Location Automation";

	@BeforeClass
	public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddANewTaskWithAllFields(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        data.setNotesMessage(data.getNotesMessage() + RandomStringUtils.randomAlphabetic(10));
        VNextBORODetailsStepsNew.addNewTask(data, false, true);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getNotesMessage(), true);
        VNextBORODetailsValidationsNew.verifyServiceVendorIsCorrect(data.getNotesMessage(), data.getTeam());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getNotesMessage(), data.getTechnician());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddANewTaskWithRequiredFields(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        data.setNotesMessage(data.getNotesMessage() + RandomStringUtils.randomAlphabetic(10));
        VNextBORODetailsStepsNew.addNewTask(data, true, true);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getNotesMessage(), true);
        VNextBORODetailsValidationsNew.verifyServiceVendorIsCorrect(data.getNotesMessage(), data.getTeam());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getNotesMessage(), data.getTechnician());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCancelTaskAdding(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        data.setNotesMessage(data.getNotesMessage() + RandomStringUtils.randomAlphabetic(10));
        VNextBORODetailsStepsNew.addNewTask(data, false, false);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getNotesMessage(), false);
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanAddNewTaskWithPredefinedTechnician(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        data.setNotesMessage(data.getNotesMessage() + RandomStringUtils.randomAlphabetic(10));
        VNextBORODetailsStepsNew.addNewTaskWithPredefinedTechnician(data);
        VNextBORODetailsStepsNew.expandPhaseByName(data.getPhase());
        VNextBORODetailsValidationsNew.verifyServiceIsDisplayed(data.getNotesMessage(), true);
        VNextBORODetailsValidationsNew.verifyServiceVendorIsCorrect(data.getNotesMessage(), data.getTeam());
        VNextBORODetailsValidationsNew.verifyServiceTechnicianIsCorrect(data.getNotesMessage(), data.getTechnician());
        VNextBORODetailsStepsNew.collapsePhaseByName(data.getPhase());
    }
}
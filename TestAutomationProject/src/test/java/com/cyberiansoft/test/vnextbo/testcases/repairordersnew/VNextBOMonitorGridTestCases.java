package com.cyberiansoft.test.vnextbo.testcases.repairordersnew;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
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
import com.cyberiansoft.test.vnextbo.validations.repairordersnew.VNextBOROWebPageValidationsNew;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOMonitorGridTestCases extends BaseTestCase {

    final String TEST_ORDER_NUMBER = "O-000-152551";
    final String TEST_LOCATION = "Rozstalnoy_location";

	@BeforeClass
	public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorGridTD();
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation(TEST_LOCATION);
        VNextBOROPageStepsNew.searchOrdersByOrderNumber(TEST_ORDER_NUMBER);
        WaitUtilsWebDriver.waitForPageToBeLoaded();
	}

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCloseRO(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.closeOrder(TEST_ORDER_NUMBER, data.getProblemReason());
        VNextBOROPageStepsNew.openOrderDetailsByNumberInList(0);
        VNextBORODetailsValidationsNew.verifyOrderStatusIsCorrect("Closed");
        VNextBORODetailsValidationsNew.verifyOrderCloseReasonIsCorrect(data.getProblemReason());
        VNextBORODetailsValidationsNew.verifyPhaseTextStatusIsCorrect(data.getPhase(), "Skipped");
        VNextBORODetailsStepsNew.reopenOrderIfNeeded();
        Utils.goToPreviousPage();
        WaitUtilsWebDriver.waitForPageToBeLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeFlagOfRo(String rowID, String description, JSONObject testData) {

        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Test");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(128, 0, 128)", "purple");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Blue");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(72, 124, 184)", "blue");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Green");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(0, 166, 81)", "green");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Yellow");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(255, 242, 0)", "yellow");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Low priority");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(255, 140, 90)", "orange");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "Important");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "3px solid rgb(237, 28, 36)", "red");
        VNextBOROPageStepsNew.changeOrderFlag(TEST_ORDER_NUMBER, "White");
        VNextBOROWebPageValidationsNew.verifyOrderFlagIsCorrect(TEST_ORDER_NUMBER, "0px none rgb(70, 70, 70)", "white");
    }
}
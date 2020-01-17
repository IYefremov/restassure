package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.partsManagement.VNextBOPartsManagementOrderDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOPMOrderDetailsPartsDetailsStatusTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsPartsDetailsStatusTD();
        com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation("automationMonitoring");
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-444-00531");
    }

    @AfterClass
    public void setStatusToOrdered() {

        VNextBOBreadCrumbInteractions.setLocation("automationMonitoring");
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-444-00531");
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, "Ordered");
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeStatusOfThePart(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementOrderDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, data.getStatus());
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPriceAndQuantityAreChangedAfterTheStatusChanging(String rowID, String description, JSONObject testData) {

        VNextBOBreadCrumbInteractions.setLocation("Rozstalnoy_location");
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-385-00027");
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, "Ordered");
        VNextBOPartsDetailsPanelSteps.setPriceForPartByPartNumberInList(0, "10");
        VNextBOPartsDetailsPanelSteps.setQuantityForPartByPartNumberInList(0, "10");
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, "Refused");
        WaitUtilsWebDriver.waitABit(30000);
        Utils.refreshPage();
        VNextBOBreadCrumbInteractions.setLocation("Rozstalnoy_location");
        WaitUtilsWebDriver.waitForSpinnerToDisappear();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-385-00027");
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-385-00027");
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, "Refused");
        VNextBOPartsDetailsPanelValidations.verifyPartPriceIsCorrect(0, "$0.00");
        VNextBOPartsDetailsPanelValidations.verifyPartQuantityIsCorrect(0, "1.000");
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        VNextBOBreadCrumbInteractions.setLocation("automationMonitoring");
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-444-00531");
    }
}
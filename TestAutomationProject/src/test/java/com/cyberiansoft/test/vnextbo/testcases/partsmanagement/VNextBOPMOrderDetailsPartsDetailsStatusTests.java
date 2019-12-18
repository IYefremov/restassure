package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementOrderDetailsData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOPMOrderDetailsPartsDetailsStatusTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsPartsDetailsStatusTD();
        com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-000-152414");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanChangeStatusOfThePart(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementOrderDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, data.getStatus());
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, data.getStatus());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyPriceAndQuantityAreChangedAfterTheStatusChanging(String rowID, String description, JSONObject testData) {

        VNextBOBreadCrumbInteractions.setLocation("Rozstalnoy_location");
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-385-00027");
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, "Ordered");
        VNextBOPartsDetailsPanelSteps.setPriceForPartByPartNumberInList(0, "10");
        VNextBOPartsDetailsPanelSteps.setQuantityForPartByPartNumberInList(0, "10");
        VNextBOPartsDetailsPanelSteps.setStatusForPartByPartNumberInList(0, "Refused");
        Utils.refreshPage();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading("O-385-00027");
        VNextBOPartsDetailsPanelValidations.verifyPartStatusIsCorrect(0, "Refused");
        VNextBOPartsDetailsPanelValidations.verifyPartPriceIsCorrect(0, "$0.00");
        VNextBOPartsDetailsPanelValidations.verifyPartQuantityIsCorrect(0, "1.000");
    }
}
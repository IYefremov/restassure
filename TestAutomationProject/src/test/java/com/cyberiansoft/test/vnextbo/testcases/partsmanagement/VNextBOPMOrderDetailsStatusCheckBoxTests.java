package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.dataclasses.vNextBO.VNextBOPartsManagementData;
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

public class VNextBOPMOrderDetailsStatusCheckBoxTests extends BaseTestCase {

    @BeforeClass
    public void settingUp() {

        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsStatusCheckBoxDataTD();
        com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions.selectPartsManagementMenu();
        VNextBOBreadCrumbInteractions.setLocation("Best Location Automation");
        VNextBOSearchPanelSteps.searchByText("O-000-152414");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 0)
    public void verifyUserCanSeeOnlyStatusesBelongingToThePertsInRO(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);
        VNextBOPartsDetailsPanelSteps.clickStatusesCheckBox();
        VNextBOPartsDetailsPanelValidations.verifyStatusesListIsCorrect(data.getStatusesList());
        VNextBOPartsDetailsPanelSteps.clickStatusesCheckBox();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class, priority = 1)
    public void verifyUserCanSelectPartsByStatus(String rowID, String description, JSONObject testData) {

        VNextBOPartsManagementOrderDetailsData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementOrderDetailsData.class);
        VNextBOPartsDetailsPanelSteps.displayPartsByStatus(data.getStatus());
        VNextBOPartsDetailsPanelValidations.verifyPartsCheckBoxesAreActivatedByPartStatus(data.getStatus(), true);
        VNextBOPartsDetailsPanelValidations.verifyDeleteSelectedPartsButtonIsDisplayed(true);
        VNextBOPartsDetailsPanelSteps.clickStatusesCheckBox();
        VNextBOPartsDetailsPanelValidations.verifyDeleteSelectedPartsButtonIsDisplayed(false);
        VNextBOPartsDetailsPanelValidations.verifyPartsCheckBoxesAreActivatedByPartStatus(data.getStatus(), false);
    }
}
package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementSearchData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.config.VNextBOConfigInfo;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBORODetailsPartsBlockValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextBOPartsManagementOrderDetailsTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementOrderDetailsTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheSameStatusesAreDisplayedOnRODetailsAndPMPages(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        final VNextBOPartsData partData = data.getPartData();
        final VNextBOPartsManagementSearchData searchData = data.getSearchData();
//
//        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(searchData.getWoNum());
//        VNextBOPartsDetailsPanelSteps.deleteServicesByName(partData.getPartName());
//        VNextBOPartsDetailsPanelSteps.addPartIfOpenStatusIsNotPresent(partData, searchData.getWoNum());

//        final String pmWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();

        Utils.openNewTab(VNextBOConfigInfo.getInstance().getVNextBOCompanionappURL());
        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(searchData.getWoNum(), TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(searchData.getWoNum());
        final String roWindow = DriverBuilder.getInstance().getDriver().getWindowHandle();
        VNextBORODetailsPartsBlockValidations.verifyServicePartsFieldsAreNotClickable(partData.getPartName());

    }
}
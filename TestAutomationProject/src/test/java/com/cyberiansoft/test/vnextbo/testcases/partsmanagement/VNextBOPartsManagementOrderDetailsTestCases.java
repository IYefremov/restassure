package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.PartStatuses;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBORODetailsPartsBlockInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.leftmenupanel.VNextBOLeftMenuSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPartsBlockSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBOPartsDetailsPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.partsmanagement.VNextBORODetailsPartsBlockValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class VNextBOPartsManagementOrderDetailsTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPartsManagementOrderDetailsTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheSameStatusesAreDisplayedOnRODetailsAndPMPages(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String pmWindow = Utils.getParentTab();
        final String woNum = data.getSearchData().getWoNum();
        final String partName = data.getPartData().getPartNames()[0];
        final String openStatus = PartStatuses.OPEN.getStatus();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.deleteServicesByName(partName);
        VNextBOPartsDetailsPanelSteps.addPart(data.getPartData(), woNum);
        VNextBOLeftMenuSteps.openRepairOrdersMenuInNewTab();
        final String roWindow = Utils.getParentTab();
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(woNum, TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(woNum);
        VNextBORODetailsPartsBlockValidations.verifyServicePartsFieldsAreNotChangeable(partName);

        Arrays.stream(PartStatuses.values())
                .map(PartStatuses::getStatus)
                .filter(status -> !(status.equals("All") || status.equals("Open") || status.equals("Problem")))
                .forEach(status -> {
                    VNextBOPartsDetailsPanelSteps.openPMPageAndSetStatusForPart(pmWindow, partName, status);
                    VNextBORODetailsPartsBlockSteps.updatePartStatus(roWindow, partName, status);
                });
        Utils.openTab(pmWindow);
        VNextBOPartsDetailsPanelSteps.deleteServicesByStatus(PartStatuses.AUDITED.getStatus());
        Utils.closeNewWindow(pmWindow);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyThePONumberFieldIsDisplayedAndEditableOnThePMPage(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        final String poNumber = VNextBOPartsDetailsPanelInteractions.setPONumber(0);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        Assert.assertEquals(poNumber, VNextBOPartsDetailsPanelInteractions.getPONumber(0),
                "The PO# hasn't been set");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeProviderOfThePart(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        VNextBOPartsDetailsPanelInteractions.setProvider(data.getProvider());
        VNextBOPartsDetailsPanelValidations.verifyProviderIsSet(0, data.getProvider());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangePartForSpecifiedParts(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String woNum = data.getSearchData().getWoNum();
        final String partName = data.getPartData().getPartNames()[0];
        final String pmWindow = Utils.getParentTab();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        final int order = VNextBOPartsDetailsPanelInteractions.getPartNumberByPartName(partName);
        VNextBOPartsDetailsPanelInteractions.setPartNumber(order, data.getPartNumber());

        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(woNum, TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(woNum);
        final String partNumValue = VNextBORODetailsPartsBlockInteractions.getPartNumberValueByPartName(partName);
        Assert.assertEquals(partNumValue, data.getPartNumber(), "The part number is not displayed properly");
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();

        VNextBOLeftMenuSteps.openPartsManagementMenuInNewTab();
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelInteractions.clearPartNumber(order);

        VNextBOLeftMenuInteractions.selectRepairOrdersMenu();
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(woNum, TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(woNum);
        final String partNumUpdatedValue = VNextBORODetailsPartsBlockInteractions.getPartNumberValueByPartName(partName);
        Assert.assertEquals(partNumUpdatedValue, "", "The part number has not been cleared");
        Utils.closeNewWindow(pmWindow);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLaborCredit(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        final String laborCredit = VNextBOPartsDetailsPanelInteractions.setLaborCredit(0);
        Assert.assertEquals(VNextBOPartsDetailsPanelInteractions.getLaborCredit(0), laborCredit,
                "The labor credit hasn't been set");
    }
}
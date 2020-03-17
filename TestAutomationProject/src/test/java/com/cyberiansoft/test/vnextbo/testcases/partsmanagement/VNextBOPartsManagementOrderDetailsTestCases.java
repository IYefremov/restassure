package com.cyberiansoft.test.vnextbo.testcases.partsmanagement;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.dataclasses.vNextBO.partsmanagement.VNextBOPartsManagementData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.RepairStatus;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.enums.partsmanagement.CoreStatus;
import com.cyberiansoft.test.enums.partsmanagement.PartStatus;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBOPartsDetailsPanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.partsmanagement.VNextBORODetailsPartsBlockInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.leftmenupanel.VNextBOLeftMenuSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsDetailsPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsManagementWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.partsmanagement.VNextBOPartsOrdersListPanelSteps;
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
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getPMOrderDetailsTD();
        VNextBOLeftMenuInteractions.selectPartsManagementMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyTheSameStatusesAreDisplayedOnRODetailsAndPMPages(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        final String pmWindow = Utils.getParentTab();
        final String woNum = data.getSearchData().getWoNum();
        final String partName = data.getPartData().getPartNames()[0];

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(woNum);
        VNextBOPartsDetailsPanelSteps.deleteServicesByName(partName);
        VNextBOPartsDetailsPanelSteps.addPartWithPartsListUpdate(data.getPartData(), woNum);
        VNextBOLeftMenuSteps.openRepairOrdersMenuInNewTab();
        final String roWindow = Utils.getParentTab();
        VNextBOROAdvancedSearchDialogSteps.searchByWoAndTimeFrame(woNum, TimeFrameValues.TIMEFRAME_CUSTOM);
        VNextBOROPageSteps.openRODetailsPage(woNum);
        VNextBORODetailsPartsBlockValidations.verifyServicePartsFieldsAreNotChangeable(partName);

        Arrays.stream(PartStatus.values())
                .map(PartStatus::getStatus)
                .filter(status -> !(status.equals("All") || status.equals("Open") || status.equals("Problem")))
                .forEach(status -> {
                    VNextBOPartsDetailsPanelSteps.openPMPageAndSetStatusForPart(pmWindow, partName, status);
                    VNextBORODetailsPartsBlockSteps.updatePartStatus(roWindow, partName, status);
                });
        Utils.openTab(pmWindow);
        VNextBOPartsDetailsPanelSteps.deleteServicesByStatus(PartStatus.AUDITED.getStatus());
        Utils.closeAllNewWindowsExceptParentTab(pmWindow);    }

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
        Utils.closeAllNewWindowsExceptParentTab(pmWindow);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeLaborCredit(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        final String laborCredit = VNextBOPartsDetailsPanelInteractions.setLaborCredit(0);
        VNextBOPartsDetailsPanelValidations.verifyPartLaborCreditValue(0, laborCredit);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeCorePrice(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        final String corePrice = VNextBOPartsDetailsPanelInteractions.setCorePrice(0);
        VNextBOPartsDetailsPanelValidations.verifyPartCorePrice(0, corePrice);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeCoreStatus(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOPartsDetailsPanelInteractions.setCoreStatusForPartByPartNumber(0, CoreStatus.RETURN_TO_VENDOR.getStatus());
        VNextBOPartsDetailsPanelValidations.verifyPartCoreStatus(0, CoreStatus.RETURN_TO_VENDOR.getStatus());
        VNextBOPartsDetailsPanelInteractions.setCoreStatusForPartByPartNumber(0, CoreStatus.RTV_COMPLETE.getStatus());
        VNextBOPartsDetailsPanelValidations.verifyPartCoreStatus(0, CoreStatus.RTV_COMPLETE.getStatus());
        VNextBOPartsDetailsPanelSteps.checkNACoreStatusOptionIsNotDisplayed(0);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSelectPartConditions(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOPartsManagementWebPageSteps.openAdvancedSearchForm();
        VNextBOAdvancedSearchDialogSteps.setRepairStatusField(RepairStatus.IN_PROGRESS_ALL.getValue());
        VNextBOAdvancedSearchDialogSteps.clickSearchButton();
        VNextBOPartsOrdersListPanelSteps.openPartOrderDetailsByNumberInList(0);
        VNextBOPartsDetailsPanelSteps.setAllConditionsForPartByPartNumberInList(0);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeVendorPrice(String rowID, String description, JSONObject testData) {
        VNextBOPartsManagementData data = JSonDataParser.getTestDataFromJson(testData, VNextBOPartsManagementData.class);

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(data.getSearchData().getWoNum());
        final String vendorPrice = VNextBOPartsDetailsPanelInteractions.setVendorPrice(0);
        VNextBOPartsDetailsPanelValidations.verifyPartVendorPrice(0, vendorPrice);
    }
}
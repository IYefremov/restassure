package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorBasicData;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorGridData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.RepairStatus;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.enums.monitor.OrderMonitorFlags;
import com.cyberiansoft.test.enums.monitor.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.monitor.OrderMonitorStatuses;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOCurrentPhasePanelInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOPageSwitcherSteps;
import com.cyberiansoft.test.vnextbo.steps.homepage.VNextBOHomeWebPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOOtherPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCurrentPhasePanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOOtherPanelValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBORODetailsPageValidations;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOROPageValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

public class VNextBOMonitorGridDataTestCases extends BaseTestCase {

    private VNextBOMonitorBasicData startData;

    @BeforeClass
    public void settingUp() throws IOException {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
        startData = JSonDataParser.getTestDataFromJson(
                new File(VNextBOTestCasesDataPaths.getInstance().getMonitorBasicTD()), VNextBOMonitorBasicData.class);
        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(startData.getLocation());
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

//    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorGridData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorGridData.class);
        final VNextBOMonitorData monitorData = data.getMonitorData();
        System.out.println(monitorData);
        VNextBOHomeWebPageSteps.openRepairOrdersMenuWithLocation(monitorData.getLocation());
        final String orderNumber = monitorData.getOrderNumber();
        final String vendor = monitorData.getVendor();
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(orderNumber);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(orderNumber, true),
                "The work order is not displayed after search by clicking the 'Search' icon");

        final String priorTechniciansValue = VNextBOROPageInteractions.getTechnicianValueForWO(orderNumber);
        String selectedRandomTechnician = VNextBOROPageSteps.setTechnicianAndVendorByWoNumber(
                orderNumber, vendor);
        if (priorTechniciansValue.equals(selectedRandomTechnician)) {
            selectedRandomTechnician = VNextBOROPageSteps.setTechnicianAndVendorByWoNumber(orderNumber, vendor);
        }
        VNextBOROPageSteps.openRODetailsPage(orderNumber);

        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(monitorData.getServices()[0], monitorData.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(monitorData.getServices()[0], OrderMonitorStatuses.COMPLETED.getValue());
        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(monitorData.getServices()[1], monitorData.getServiceTabs()[1]);
        VNextBORODetailsPageValidations.verifyVendorTechnicianNameIsSet(selectedRandomTechnician);

        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROPageValidations.verifyAnotherTechnicianIsDisplayed(orderNumber, selectedRandomTechnician);
        VNextBOROPageSteps.openRODetailsPage(orderNumber);
        VNextBORODetailsPageValidations.verifyServiceIsDisplayedForCollapsedPhase(monitorData.getServices()[0], monitorData.getServiceTabs()[0]);
        VNextBORODetailsPageSteps.setServiceStatusForService(monitorData.getServices()[0], OrderMonitorStatuses.ACTIVE.getValue());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeMoreThanOneDifferentTechniciansLinkedToOnePhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOBreadCrumbInteractions.setLocation(startData.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        final String orderNumber = data.getOrderNumber();
        final String phaseOne = data.getServiceTabs()[0];
        final String phaseTwo = data.getServiceTabs()[1];

        VNextBOROAdvancedSearchDialogInteractions.setWoNum(orderNumber);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(orderNumber, true),
                "The work order is not displayed after search by clicking the 'Search' icon");

        String prevTechnician = VNextBOROPageInteractions.getTechnicianValueForWO(orderNumber);
        VNextBOROPageSteps.openRODetailsPage(orderNumber);
        VNextBORODetailsPageSteps.setCompleteCurrentPhase(phaseOne);

        final List<String> techniciansSet = VNextBORODetailsPageSteps.setTechniciansForAllPhaseServices(phaseTwo);

        Utils.goToPreviousPage();
        VNextBOROPageInteractions.waitForTechnicianToBeUpdated(orderNumber, prevTechnician);
        Assert.assertTrue(WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                        VNextBOROPageInteractions.getMultipleTechniciansValuesForWO(orderNumber).containsAll(techniciansSet)),
                "The technician's name is not displayed");
        prevTechnician = VNextBOROPageInteractions.getTechnicianValueForWO(orderNumber);

        String selectedRandomTechnician = VNextBOROPageSteps.setTechnicianAndVendorByWoNumber(
                orderNumber, data.getVendor());
        VNextBOROPageInteractions.waitForTechnicianToBeUpdated(orderNumber, prevTechnician);
        Assert.assertEquals(selectedRandomTechnician, VNextBOROPageInteractions.getTechnicianValueForWO(orderNumber),
                "The technician hasn't been changed");

        VNextBOROPageSteps.openRODetailsPage(orderNumber);
        Utils.refreshPage();
        VNextBORODetailsPageInteractions.expandPhasesTable(phaseTwo);
        final List<String> updatedTechniciansSet = VNextBORODetailsPageSteps.setTechniciansForAllPhaseServices(phaseTwo);

        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        VNextBOROAdvancedSearchDialogInteractions.setWoNum(orderNumber);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();

        VNextBOROPageInteractions.waitForTechnicianToBeUpdated(orderNumber, prevTechnician);

        Assert.assertTrue(WaitUtilsWebDriver.getShortWait().until((ExpectedCondition<Boolean>) driver ->
                        VNextBOROPageInteractions.getMultipleTechniciansValuesForWO(orderNumber).containsAll(updatedTechniciansSet)),
                "The technician's name is not displayed");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeFieldsOfStockROPONumsAndInvoiceColumns(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOBreadCrumbInteractions.setLocation(startData.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        final String orderNumber = data.getOrderNumber();

        VNextBOROAdvancedSearchDialogInteractions.setWoNum(orderNumber);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(orderNumber, true),
                "The work order is not displayed after search by clicking the 'Search' icon");

        final String stock = data.getRandomStockNum();
        final String ro = data.getRandomRONum();
        final String po = data.getRandomPONum();
        VNextBOROPageInteractions.setStockNum(orderNumber, stock);
        VNextBOROPageInteractions.setRONum(orderNumber, ro);
        VNextBOROPageInteractions.setPONum(orderNumber, po);
        Assert.assertEquals(stock, VNextBOROPageInteractions.getStockNum(orderNumber),
                "The stock number hasn't been updated");
        Assert.assertEquals(ro, VNextBOROPageInteractions.getRONum(orderNumber),
                "The RO number hasn't been updated");
        Assert.assertEquals(po, VNextBOROPageInteractions.getPONum(orderNumber),
                "The RO number hasn't been updated");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanStartCompleteServicesAndPhasesFromMainROsPage(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        VNextBOBreadCrumbInteractions.setLocation(startData.getLocation());
        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        final String orderNumber = data.getOrderNumber();

        VNextBOROAdvancedSearchDialogInteractions.setWoNum(orderNumber);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(RepairStatus.All.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(orderNumber, true),
                "The work order is not displayed after search by clicking the 'Search' icon");

        VNextBOROPageSteps.openRODetailsPage(orderNumber);
        final List<String> allServicesId = VNextBORODetailsPageSteps.getAllPhaseServicesId(data.getPhase());
        VNextBORODetailsPageSteps.setServiceStatusForMultipleServicesByServiceId(
                allServicesId, OrderMonitorServiceStatuses.ACTIVE.getValue());
        VNextBORODetailsPageSteps.resetServiceStartDate(allServicesId);
        VNextBOBreadCrumbInteractions.clickFirstBreadCrumbLink();
        WaitUtilsWebDriver.waitABit(2000);

        VNextBOROPageSteps.openCurrentPhasePanel(orderNumber);
        final List<String> servicesList = VNextBOCurrentPhasePanelInteractions.getServices();
        final int prev = VNextBOCurrentPhasePanelInteractions.getDisplayedStartedServicesIconsNumber(orderNumber);
        final int next = VNextBOCurrentPhasePanelInteractions.getDisplayedCompletedServicesIconsNumber(orderNumber);
        final String service = VNextBOCurrentPhasePanelInteractions.clickRandomPhaseStatusOption(orderNumber);
        Assert.assertTrue(VNextBOCurrentPhasePanelValidations.isCurrentPhasePanelOpened(),
                "The current phase panel has been closed after clicking the phase status");
        Assert.assertEquals(servicesList.size(), VNextBOCurrentPhasePanelInteractions.getServices().size(),
                "The services list number has changed");
        Assert.assertTrue(VNextBOCurrentPhasePanelInteractions.getStartedServicesValues(orderNumber).contains(service),
                "The service is not displayed as started");
        Assert.assertEquals(prev - 1, VNextBOCurrentPhasePanelInteractions.getDisplayedStartedServicesIconsNumber(orderNumber),
                "The started services icons number hasn't been changed");
        Assert.assertEquals(next + 1, VNextBOCurrentPhasePanelInteractions.getDisplayedCompletedServicesIconsNumber(orderNumber),
                "The completed services icons number hasn't been changed");
        VNextBOROPageSteps.closeCurrentPhaseOption(orderNumber);
        VNextBOROPageSteps.openCurrentPhasePanel(orderNumber);
//            Assert.assertEquals(servicesList.size() - 1, VNextBOCurrentPhasePanelInteractions.getServices().size(),
//                    "The services list number has not been changed after reopening the current phase panel");

        Assert.assertTrue(VNextBOCurrentPhasePanelValidations.areStartServicesIconsDisplayedForWO(orderNumber),
                "The start services icons are not displayed for order " + orderNumber);
        VNextBOCurrentPhasePanelInteractions.clickStartPhaseServices(orderNumber);
        Assert.assertTrue(VNextBOCurrentPhasePanelValidations.isCurrentPhasePanelClosed(),
                "The current phase panel hasn't been closed after starting the phase services");

        VNextBOROPageSteps.openCurrentPhasePanel(orderNumber);
        Assert.assertTrue(VNextBOCurrentPhasePanelValidations.areCompleteServicesIconsDisplayedForWO(orderNumber),
                "The complete services icons are not displayed for order " + orderNumber);
        VNextBOCurrentPhasePanelInteractions.clickCompleteCurrentPhaseOption(orderNumber);
        Assert.assertTrue(VNextBOCurrentPhasePanelValidations.isCurrentPhasePanelClosed(),
                "The current phase panel hasn't been closed after completing the phase services");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanPrioritizeRO(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        final String orderNumber = data.getOrderNumber();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[0]);
        VNextBOOtherPanelSteps.setHighPriority(orderNumber);
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[1]);
        Assert.assertTrue(VNextBOROPageInteractions.getHighPriorityOrdersListOnPage().contains(orderNumber),
                "The order " + orderNumber + " doesn't have the highest priority");
        Assert.assertTrue(VNextBOROPageValidations.isArrowUpDisplayed(orderNumber, true),
                "The red icon is not displayed for the order");

        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[0]);
        VNextBOOtherPanelSteps.setLowPriority(orderNumber);
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[1]);
        VNextBOPageSwitcherSteps.changeItemsPerPage(data.getPages().getHundred());
        Assert.assertTrue(VNextBOROPageInteractions.getLowPriorityOrdersListOnPage().contains(orderNumber),
                "The order " + orderNumber + " doesn't have the lowest priority");
        Assert.assertTrue(VNextBOROPageValidations.isArrowDownDisplayed(orderNumber, true),
                "The green icon is not displayed for the order");

        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[0]);
        VNextBOOtherPanelSteps.setMidPriority(orderNumber);
        VNextBOROPageInteractions.clickCancelSearchIcon();
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[1]);
        Assert.assertTrue(VNextBOROPageInteractions.getMidPriorityOrdersListOnPage().contains(orderNumber),
                "The order " + orderNumber + " doesn't have the mid priority");
        Assert.assertTrue(VNextBOROPageValidations.isArrowUpDisplayed(orderNumber, false),
                "The red icon is displayed for the order with the mid priority");
        Assert.assertTrue(VNextBOROPageValidations.isArrowDownDisplayed(orderNumber, false),
                "The green icon is displayed for the order with the mid priority");
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanChangeFlagOfRO(String rowID, String description, JSONObject testData) {
        VNextBOMonitorData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorData.class);

        final String orderNumber = data.getOrderNumber();

        VNextBOBreadCrumbInteractions.setLocation(data.getLocation());
        VNextBOROPageSteps.setSavedSearchOption(data.getSearchValues().getSearchNames()[0]);

        EnumSet.allOf(OrderMonitorFlags.class).forEach(flag -> {
            VNextBOOtherPanelSteps.setFlag(orderNumber, flag.getFlag());
            Assert.assertTrue(VNextBOOtherPanelValidations.isOtherPanelClosed(orderNumber),
                    "The 'Other' panel hasn't been closed");
            Assert.assertEquals(VNextBOROPageInteractions.getFirstOrderBackgroundColor(), flag.name().toLowerCase(),
                    "The background color of the order hasn't been changed");
        });
    }
}

package com.cyberiansoft.test.vnextbo.testcases.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorBasicData;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorData;
import com.cyberiansoft.test.dataclasses.vNextBO.repairorders.VNextBOMonitorGridData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.OrderMonitorRepairStatuses;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.enums.TimeFrameValues;
import com.cyberiansoft.test.vnextbo.config.VNextBOTestCasesDataPaths;
import com.cyberiansoft.test.vnextbo.interactions.breadcrumb.VNextBOBreadCrumbInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROAdvancedSearchDialogInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBORODetailsPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.repairorders.VNextBOROPageInteractions;
import com.cyberiansoft.test.vnextbo.steps.HomePageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROAdvancedSearchDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBORODetailsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.repairorders.VNextBOROPageSteps;
import com.cyberiansoft.test.vnextbo.testcases.BaseTestCase;
import com.cyberiansoft.test.vnextbo.validations.repairorders.VNextBOCurrentPhasePanelValidations;
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
import java.util.List;

public class VNextBOMonitorGridDataTestCases extends BaseTestCase {

    @BeforeClass
    public void settingUp() throws IOException {
        JSONDataProvider.dataFile = VNextBOTestCasesDataPaths.getInstance().getMonitorTD();
        VNextBOMonitorBasicData data = JSonDataParser.getTestDataFromJson(
                new File(VNextBOTestCasesDataPaths.getInstance().getMonitorBasicTD()), VNextBOMonitorBasicData.class);
        HomePageSteps.openRepairOrdersMenuWithLocation(data.getLocation());
    }

    @AfterMethod
    public void refreshPage() {
        Utils.refreshPage();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanSeeAndChangeTechniciansOfTheCurrentPhase(String rowID, String description, JSONObject testData) {
        VNextBOMonitorGridData data = JSonDataParser.getTestDataFromJson(testData, VNextBOMonitorGridData.class);
        final VNextBOMonitorData monitorData = data.getMonitorData();
        System.out.println(monitorData);
        HomePageSteps.openRepairOrdersMenuWithLocation(monitorData.getLocation());
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

        VNextBOROAdvancedSearchDialogSteps.openAdvancedSearchDialog();
        final String orderNumber = data.getOrderNumber();

        VNextBOROAdvancedSearchDialogInteractions.setWoNum(orderNumber);
        VNextBOROAdvancedSearchDialogInteractions.setTimeFrame(TimeFrameValues.TIMEFRAME_LASTYEAR.getName());
        VNextBOROAdvancedSearchDialogInteractions.setRepairStatus(OrderMonitorRepairStatuses.All.getValue());
        VNextBOROAdvancedSearchDialogSteps.search();
        Assert.assertTrue(VNextBOROPageValidations.isWorkOrderDisplayedByName(orderNumber, true),
                "The work order is not displayed after search by clicking the 'Search' icon");

        VNextBOCurrentPhasePanelValidations.verifyOrderIsNotCompleted(orderNumber, data.getPhase());
        VNextBOROPageInteractions.clickWorkOrderCurrentPhaseMenu(orderNumber);
        //todo uncomment after clarifying the preconditions for starting the current phase
//        VNextBOCurrentPhasePanelValidations.verifyOrderCanBeStarted(orderNumber);
//        Assert.assertTrue(VNextBOCurrentPhasePanelValidations.areStartServicesIconsDisplayedForWO(orderNumber),
//                "The start services icons are not displayed for order " + orderNumber);
//        VNextBOCurrentPhasePanelInteractions.startPhaseServices(orderNumber);
    }
}

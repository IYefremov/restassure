package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersList extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description="Team Work Orders List Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersListTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCreatedWOWithTeamSharingDisplaysInTeamWOList(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();

        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
        workordersscreen.switchToTeamWorkordersView();
        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
        workordersscreen.switchToMyWorkordersView();
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSearchWOByCustomerName(String rowID,
                                                                       String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();

        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
        workordersscreen.switchToTeamWorkordersView();
        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
        workordersscreen.searchWorkOrderByFreeText(testcustomer.getFullName());
        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
        workordersscreen.switchToMyWorkordersView();
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWO(String rowID,
                                                                       String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();

        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
        workordersscreen.switchToTeamWorkordersView();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleinfoscreen.selectMakeAndModel(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel());
        vehicleinfoscreen.saveWorkOrderViaMenu();
        workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.switchToMyWorkordersView();
        workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditWOIfWeHaveNoInternetConnection(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspNumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR2);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.switchToMyWorkordersView();
        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));

        AppiumUtils.setNetworkOff();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleinfoscreen.selectMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(),
                inspectionData.getVehicleInfo().getVehicleModel());
        vehicleinfoscreen.saveWorkOrderViaMenu();
        workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleinfoscreen.cancelWorkOrder();
        workordersscreen.clickBackButton();
        Assert.assertEquals(Integer.valueOf(homescreen.getQueueMessageValue()).intValue(), 1);
        AppiumUtils.setAndroidNetworkOn();
        homescreen.waitUntilQueueMessageInvisible();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyAllChangesIsSavedAfterReconnectInternet(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspNumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR2);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.switchToMyWorkordersView();
        Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));

        AppiumUtils.setNetworkOff();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleinfoscreen.selectMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(),
                inspectionData.getVehicleInfo().getVehicleModel());
        vehicleinfoscreen.saveWorkOrderViaMenu();
        workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleinfoscreen.cancelWorkOrder();
        workordersscreen.clickBackButton();
        Assert.assertEquals(Integer.valueOf(homescreen.getQueueMessageValue()).intValue(), 1);
        AppiumUtils.setAndroidNetworkOn();
        homescreen.waitUntilQueueMessageInvisible();

        VNextStatusScreen statusScreen = homescreen.clickStatusMenuItem();
        statusScreen.updateMainDB();
        //statusScreen.clickBackButton();
        homescreen.clickWorkOrdersMenuItem();
        workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleinfoscreen.cancelWorkOrder();
        workordersscreen.clickBackButton();
    }
}

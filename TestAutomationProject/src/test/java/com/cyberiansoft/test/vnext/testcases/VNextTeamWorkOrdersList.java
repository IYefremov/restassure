package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersList extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-workorders-list-testcases-data.json";

    @BeforeClass(description="Team Work Orders List Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCreatedWOWithTeamSharingDisplaysInTeamWOList(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(appiumdriver);
        inspectionTypesList.selectInspectionType(inspectionData.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.saveInspectionViaMenu();
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(inspectionData.getInspectionType());
        vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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
        AppiumUtils.setNetworkOn();
        homescreen.waitUntilQueueMessageInvisible();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyAllChangesIsSavedAfterReconnectInternet(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(appiumdriver);
        inspectionTypesList.selectInspectionType(inspectionData.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.saveInspectionViaMenu();
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();

        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(inspectionData.getInspectionType());
        vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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
        AppiumUtils.setNetworkOn();
        homescreen.waitUntilQueueMessageInvisible();

        VNextStatusScreen statusScreen = homescreen.clickStatusMenuItem();
        statusScreen.updateMainDB();
        statusScreen.clickBackButton();
        homescreen.clickWorkOrdersMenuItem();
        workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleinfoscreen.cancelWorkOrder();
        workordersscreen.clickBackButton();
    }
}

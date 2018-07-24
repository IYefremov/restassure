package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextWorkOrderTypesList;
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
}

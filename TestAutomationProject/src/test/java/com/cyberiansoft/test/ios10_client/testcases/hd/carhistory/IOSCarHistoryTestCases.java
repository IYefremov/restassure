package com.cyberiansoft.test.ios10_client.testcases.hd.carhistory;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.MyWorkOrdersSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCarHistoryTestCases extends IOSHDBaseTestCase {

    private WholesailCustomer ZAZ_Motors = new WholesailCustomer();

    @BeforeClass(description = "Car History Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCarHistoryTestCasesDataPath();
        ZAZ_Motors.setCompanyName("Zaz Motors");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testBugWithCrashOnCopyVehicle(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        VehicleInfoData vehicleInfoData = testCaseData.getWorkOrderData().getVehicleInfoData();
        final String vehicleinfo = vehicleInfoData.getVehicleColor() + ", " +
                vehicleInfoData.getVehicleMake() + ", " + vehicleInfoData.getVehicleModel();

        HomeScreen homeScreen = new HomeScreen();
        CarHistoryScreen carhistoryscreen = homeScreen.clickCarHistoryButton();
        carhistoryscreen.clickFirstCarHistoryInTable();
        carhistoryscreen.clickCarHistoryMyWorkOrders();
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        String workOrderNumber = myWorkOrdersScreen.getFirstWorkOrderNumberValue();
        MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber, ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
        vehicleScreen.cancelWizard();
        myWorkOrdersScreen.clickBackToCarHystoryScreen();
        carhistoryscreen.clickHomeButton();
    }
}

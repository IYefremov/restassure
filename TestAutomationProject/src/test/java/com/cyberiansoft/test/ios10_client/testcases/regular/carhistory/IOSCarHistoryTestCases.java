package com.cyberiansoft.test.ios10_client.testcases.regular.carhistory;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularCarHistoryWOsAndInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCarHistoryTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Car History Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCarHistoryTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testBugWithCrashOnCopyVehicle(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        VehicleInfoData vehicleInfoData = testCaseData.getWorkOrderData().getVehicleInfoData();
        final String vehicleinfo = vehicleInfoData.getVehicleColor() + ", " +
                vehicleInfoData.getVehicleMake() + ", " + vehicleInfoData.getVehicleModel();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);
        RegularHomeScreenSteps.navigateTocarHistoryScreen();
        RegularCarHistoryScreen carhistoryscreen = new RegularCarHistoryScreen();

        RegularCarHistoryWOsAndInvoicesScreen regularCarHistoryWOsAndInvoicesScreen = carhistoryscreen.clickCarHistoryRowByVehicleInfo(vehicleinfo);
        regularCarHistoryWOsAndInvoicesScreen.clickCarHistoryMyWorkOrders();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectFirstOrder();
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.COPY_VEHICLE);
        RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        myWorkOrdersScreen.clickBackButton();

        carhistoryscreen.clickBackButton();
        carhistoryscreen.clickHomeButton();
    }
}

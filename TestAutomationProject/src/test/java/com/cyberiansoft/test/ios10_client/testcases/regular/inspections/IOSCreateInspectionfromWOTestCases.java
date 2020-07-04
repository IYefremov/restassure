package com.cyberiansoft.test.ios10_client.testcases.regular.inspections;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularHomeScreenSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularMyWorkOrdersSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularNavigationSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularWorkOrdersSteps;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCreateInspectionfromWOTestCases extends IOSRegularBaseTestCase {

    private WholesailCustomer Specific_Client = new WholesailCustomer();

    @BeforeClass(description = "Create Inspection From WO Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCreateInspectionfromWOTestCasesDataPath();
        Specific_Client.setCompanyName("Specific_Client");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionFromWO(String rowID,
                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(Specific_Client, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());

        String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();

        //Test case
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber1), testCaseData.getWorkOrderData().getWorkOrderPrice());
        RegularMyWorkOrdersSteps.selectWorkOrderForNewInspection(workOrderNumber1);
        vehicleScreen.verifyMakeModelyearValues(testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleMake(),
                testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleModel(), testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleYear());
        vehicleScreen.cancelOrder();
        RegularNavigationSteps.navigateBackScreen();
    }
}

package com.cyberiansoft.test.ios10_client.testcases.hd.workorders;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.InvoiceTypesSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.MyWorkOrdersSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSWorkOrdersBillingTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Work Orders Billing Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getWorkOrdersBillingTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrderIDs = new ArrayList<>();
        final String billingFilterValue = "All";
        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
            workOrderIDs.add(vehicleScreen.getInspectionNumber());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            for (ServiceData serviceData : workOrderData.getServicesList())
                servicesScreen.selectService(serviceData.getServiceName());
            NavigationSteps.navigateToOrderSummaryScreen();
            OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
            orderSummaryScreen.clickSave();
        }

        for (String workOrderID : workOrderIDs)
            myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderID, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderIDs.get(0));
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        for (String workOrderID : workOrderIDs) {
            myWorkOrdersScreen.clickFilterButton();
            myWorkOrdersScreen.setFilterBilling(billingFilterValue);
            myWorkOrdersScreen.clickSaveFilter();
            myWorkOrdersScreen.clickActionButton();
            myWorkOrdersScreen.selectWorkOrderForAction(workOrderID);
            myWorkOrdersScreen.clickDoneButton();
        }
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testDontAlowToChangeBilledOrders(String rowID,
                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String[] menuItemsToVerify = {"Edit", "Notes", "Change\nstatus", "Delete", "Create\nInvoices"};
        final String billingFilterValue = "All";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();

        myWorkOrdersScreen.clickFilterButton();
        myWorkOrdersScreen.setFilterBilling(billingFilterValue);
        myWorkOrdersScreen.clickSaveFilter();

        myWorkOrdersScreen.selectWorkOrder(workOrderNumber1);
        for (String menuItem : menuItemsToVerify) {
            Assert.assertFalse(myWorkOrdersScreen.isMenuItemForSelectedWOExists(menuItem), "Find menu: " + menuItem);
        }
        myWorkOrdersScreen.clickDetailspopupMenu();
        vehicleScreen.clickCancelButton();
        NavigationSteps.navigateBackScreen();
    }
}

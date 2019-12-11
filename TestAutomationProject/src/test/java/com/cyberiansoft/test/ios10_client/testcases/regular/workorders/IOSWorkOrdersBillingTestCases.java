package com.cyberiansoft.test.ios10_client.testcases.regular.workorders;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSWorkOrdersBillingTestCases extends IOSRegularBaseTestCase {

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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
            workOrderIDs.add(vehicleScreen.getWorkOrderNumber());
            RegularNavigationSteps.navigateToServicesScreen();
            RegularServicesScreen servicesScreen = new RegularServicesScreen();
            for (ServiceData serviceData : workOrderData.getServicesList())
                servicesScreen.selectService(serviceData.getServiceName());
            RegularNavigationSteps.navigateToOrderSummaryScreen();
            RegularWorkOrdersSteps.saveWorkOrder();
        }

        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        for (String workOrderID : workOrderIDs)
            myWorkOrdersScreen.approveWorkOrder(workOrderID, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderIDs.get(0));
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveAsDraft();

        for (String workOrderID : workOrderIDs) {
            myWorkOrdersScreen.clickFilterButton();
            myWorkOrdersScreen.setFilterBilling(billingFilterValue);
            myWorkOrdersScreen.clickSaveFilter();
            myWorkOrdersScreen.clickActionButton();
            myWorkOrdersScreen.selectWorkOrderForAction(workOrderID);
            myWorkOrdersScreen.clickDoneButton();
        }
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testDontAlowToChangeBilledOrders(String rowID,
                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String[] menuItemsToVerify = {"Edit", "Notes", "Change\nstatus", "Delete", "Create\nInvoices"};
        final String billingFilterValue = "All";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveAsDraft();
        myWorkOrdersScreen.clickFilterButton();
        myWorkOrdersScreen.setFilterBilling(billingFilterValue);
        myWorkOrdersScreen.clickSaveFilter();

        myWorkOrdersScreen.selectWorkOrder(workOrderNumber1);
        for (String menuItem : menuItemsToVerify) {
            Assert.assertFalse(myWorkOrdersScreen.isMenuItemForSelectedWOExists(menuItem));
        }
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.DETAILS);
        vehicleScreen.waitVehicleScreenLoaded();
        vehicleScreen.clickCancelWizard();
        RegularNavigationSteps.navigateBackScreen();
    }
}

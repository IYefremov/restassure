package com.cyberiansoft.test.ios10_client.testcases.regular.invoices;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularVehicleInfoValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInvoiceChangeCustomerTestCases extends IOSRegularBaseTestCase {

    private WholesailCustomer Specific_Client = new WholesailCustomer();
    private WholesailCustomer ZAZ_Motors = new WholesailCustomer();

    @BeforeClass(description = "Invoice Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInvoiceChangeCustomerTestCasesDataPath();
        Specific_Client.setCompanyName("Specific_Client");
        ZAZ_Motors.setCompanyName("Zaz Motors");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerForInvoice(String rowID,
                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Specific_Client");

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
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
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        myWorkOrdersScreen.waitMyWorkOrdersScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.changeCustomerForInvoice(invoiceNumber, wholesailCustomer);
        Helpers.waitABit(50 * 1000);
        RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
        Assert.assertEquals(invoiceInfoScreen.getInvoiceCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
        invoiceInfoScreen.clickWO(workOrderNumber1);
        RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(Specific_Client);
        RegularMyWorkOrdersSteps.cancelCreatingWorkOrder();
        invoiceInfoScreen.waitInvoiceInfoScreenLoaded();
        invoiceInfoScreen.cancelInvoice();
        RegularNavigationSteps.navigateBackScreen();
    }
}

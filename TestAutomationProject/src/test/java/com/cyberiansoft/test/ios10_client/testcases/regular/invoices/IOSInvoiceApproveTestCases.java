package com.cyberiansoft.test.ios10_client.testcases.regular.invoices;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyInvoicesScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInvoiceApproveTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Invoice Approve Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInvoiceApproveTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        Assert.assertTrue(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatGreyAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        Assert.assertTrue(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists(String rowID,
                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
            RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
            RegularNavigationSteps.navigateToServicesScreen();
            for (ServiceData serviceData : workOrderData.getServicesList())
                RegularServicesScreenSteps.selectService(serviceData.getServiceName());
            RegularNavigationSteps.navigateToOrderSummaryScreen();
            RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
            orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
            orderSummaryScreen.checkApproveAndCreateInvoice();
            orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
            orderSummaryScreen.clickSave();

            RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.valueOf(workOrderData.getInvoiceData().getInvoiceType()));
            invoiceInfoScreen.setPO(workOrderData.getInvoiceData().getPoNumber());
            final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
            invoiceInfoScreen.clickSaveAsFinal();
            RegularNavigationSteps.navigateBackScreen();
            RegularHomeScreenSteps.navigateToMyInvoicesScreen();
            RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
            RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
            RegularMyInvoicesScreenSteps.selectInvoiceForApprove(invoiceNumber);
            myInvoicesScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
            RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
            approveInspectionsScreen.clickApproveButton();
            approveInspectionsScreen.drawApprovalSignature();
            myInvoicesScreen.waitInvoicesScreenLoaded();

            Assert.assertFalse(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumber));
            RegularNavigationSteps.navigateBackScreen();
        }
    }
}

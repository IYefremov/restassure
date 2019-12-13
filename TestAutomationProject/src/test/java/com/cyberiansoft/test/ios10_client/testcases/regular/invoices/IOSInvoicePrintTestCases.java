package com.cyberiansoft.test.ios10_client.testcases.regular.invoices;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyInvoicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularSelectedServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInvoicePrintTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Invoice Print Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInvoicePrintTestCasesDataPath();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedMyInvoices(String rowID,
                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String printServerName = "TA_Print_Server";

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(workOrderData.getMoneyServiceData().getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularSummaryApproveScreenSteps.approveWorkOrder();

        myWorkOrdersScreen.selectWorkOrderForAction(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoicenum = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.printInvoice(invoicenum, printServerName);
        RegularNavigationSteps.navigateBackScreen();
        Helpers.waitABit(20000);
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoiceHasPrintIcon(invoicenum, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedTeamInvoices(String rowID,
                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String printServerName = "TA_Print_Server";

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(workOrderData.getMoneyServiceData().getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularSummaryApproveScreenSteps.approveWorkOrder();

        myWorkOrdersScreen.selectWorkOrderForAction(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoicenum = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        Helpers.waitABit(10000);
        RegularHomeScreenSteps.navigateToTeamInvoicesScreen();
        RegularMyInvoicesScreenSteps.printInvoice(invoicenum, printServerName);
        RegularNavigationSteps.navigateBackScreen();
        Helpers.waitABit(20000);
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoiceHasPrintIcon(invoicenum, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatOnInvoiceSummaryMainServiceOfThePanelIsDisplayedAsFirstThenAdditionalServices(String rowID,
                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName(), true);
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularSummaryApproveScreenSteps.approveWorkOrder();

        myWorkOrdersScreen.selectWorkOrderForAction(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoicenum = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.openInvoiceSummary(invoicenum);

        RegularSummaryScreen summaryScreen = new RegularSummaryScreen();
        Assert.assertTrue(summaryScreen.isSummaryPDFExists());
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }
}

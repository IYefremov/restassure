package com.cyberiansoft.test.ios10_client.testcases.hd.invoices;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.ScreenNamesConstants;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInvoicePrintTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Invoice Print Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInvoicePrintTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedMyInvoices(String rowID,
                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String printServerName = "TA_Print_Server";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        ServicesScreenSteps.selectService(workOrderData.getMoneyServiceData().getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.selectWorkOrderForAction(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoicenum = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsFinal();
        NavigationSteps.navigateBackScreen();

        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.printInvoice(invoicenum, printServerName);
        NavigationSteps.navigateBackScreen();
        Helpers.waitABit(20000);
        homeScreen.clickMyInvoices();
        Assert.assertTrue(myInvoicesScreen.isInvoicePrintButtonExists(invoicenum));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedTeamInvoices(String rowID,
                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String printServerName = "TA_Print_Server";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        ServicesScreenSteps.selectService(workOrderData.getMoneyServiceData().getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.selectWorkOrderForAction(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoicenum = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsFinal();
        NavigationSteps.navigateBackScreen();

        Helpers.waitABit(10000);
        homeScreen.clickTeamInvoices();
        TeamInvoicesScreen teamInvoicesScreen = new TeamInvoicesScreen();
        teamInvoicesScreen.printInvoice(invoicenum, printServerName);
        NavigationSteps.navigateBackScreen();
        Helpers.waitABit(20000);
        homeScreen.clickTeamInvoices();
        Assert.assertTrue(teamInvoicesScreen.isInvoicePrintButtonExists(invoicenum));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatOnInvoiceSummaryMainServiceOfThePanelIsDisplayedAsFirstThenAdditionalServices(String rowID,
                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName()));
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.selectWorkOrderForAction(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoicenum = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsFinal();
        NavigationSteps.navigateBackScreen();

        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.selectInvoice(invoicenum);
        SummaryScreen summaryScreen = myInvoicesScreen.clickSummaryPopup();
        Assert.assertTrue(summaryScreen.isSummaryPDFExists());
        summaryScreen.clickBackButton();
        NavigationSteps.navigateBackScreen();
    }
}

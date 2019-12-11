package com.cyberiansoft.test.ios10_client.testcases.hd.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.InvoiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.MyWorkOrdersSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServicesScreenSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectEmployeePopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInvoicePaymentTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Invoice Payment Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInvoicePaymentTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatItIsPosibleToAddPaymentFromDeviceForDraftInvoice(String rowID,
                                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String cashcheckamount = "100";
        final String expectedPrice = "$0.00";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());

        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword("Zayats", "1111");
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myWorkOrdersScreen.clickHomeButton();

        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickEditPopup();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickInvoicePayButton();
        invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
        invoiceInfoScreen.clickInvoicePayDialogButon();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        NavigationSteps.navigateBackScreen();
        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPage.clickInvoicesLink();
        invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        Assert.assertEquals(invoicesWebPage.getInvoicePONumber(invoiceNumber), invoiceData.getPoNumber());
        Assert.assertEquals(invoicesWebPage.getInvoicePOPaidValue(invoiceNumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
        String mainWindowHandle = webdriver.getWindowHandle();
        InvoicePaymentsTabWebPage invoicePaymentsTabWebPage = new InvoicePaymentsTabWebPage(webdriver);
        invoicesWebPage.clickInvoicePayments(invoiceNumber);
        Assert.assertEquals(invoicePaymentsTabWebPage.getPaymentsTypeAmountValue("Cash/Check"), PricesCalculations.getPriceRepresentation(cashcheckamount));
        Assert.assertEquals(invoicePaymentsTabWebPage.getPaymentsTypeCreatedByValue("Cash/Check"), "Employee Simple 20%");

        Assert.assertEquals(invoicePaymentsTabWebPage.getPaymentsTypeAmountValue("PO/RO"), expectedPrice);
        Assert.assertEquals(invoicePaymentsTabWebPage.getPaymentsTypeCreatedByValue("PO/RO"), "Back Office");
        invoicePaymentsTabWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderMyInvoice(String rowID,
                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String cashcheckamount = "100";
        final String expectedPrice = "$0.00";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword("Zayats", "1111");

        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myWorkOrdersScreen.clickHomeButton();

        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickEditPopup();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickInvoicePayButton();
        invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
        invoiceInfoScreen.clickInvoicePayDialogButon();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();

        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickChangePOPopup();
        myInvoicesScreen.changePO(invoiceData.getNewPoNumber());
        NavigationSteps.navigateBackScreen();
        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPage.clickInvoicesLink();
        invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();

        Assert.assertEquals(invoicesWebPage.getInvoicePONumber(invoiceNumber), invoiceData.getNewPoNumber());
        Assert.assertEquals(invoicesWebPage.getInvoicePOPaidValue(invoiceNumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
        String mainWindowHandle = webdriver.getWindowHandle();
        InvoicePaymentsTabWebPage invoicePaymentsTabWebPage = new InvoicePaymentsTabWebPage(webdriver);
        invoicesWebPage.clickInvoicePayments(invoiceNumber);

        Assert.assertEquals(invoicePaymentsTabWebPage.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
        invoicePaymentsTabWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderTeamInvoice(String rowID,
                                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String expectedPrice = "$0.00";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword("Zayats", "1111");
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myWorkOrdersScreen.clickHomeButton();
        BaseUtils.waitABit(25 * 1000);
        TeamInvoicesScreen teaminvoicesscreen = homeScreen.clickTeamInvoices();
        teaminvoicesscreen.selectInvoice(invoiceNumber);

        teaminvoicesscreen.clickChangePOPopup();
        teaminvoicesscreen.changePO(invoiceData.getNewPoNumber());
        NavigationSteps.navigateBackScreen();

        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPage.clickInvoicesLink();
        invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();

        Assert.assertEquals(invoicesWebPage.getInvoicePONumber(invoiceNumber), invoiceData.getNewPoNumber());
        //Assert.assertEquals(invoicesWebPage.getInvoicePOPaidValue(invoiceNumber), PricesCalculations.getPriceRepresentation(cashcheckamount));
        String mainWindowHandle = webdriver.getWindowHandle();
        InvoicePaymentsTabWebPage invoicePaymentsTabWebPage = new InvoicePaymentsTabWebPage(webdriver);
        invoicesWebPage.clickInvoicePayments(invoiceNumber);

        Assert.assertEquals(invoicePaymentsTabWebPage.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
        invoicePaymentsTabWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamAndMyInvoices(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword("Zayats", "1111");
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        NavigationSteps.navigateBackScreen();

        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickChangePOPopup();
        myInvoicesScreen.changePO(invoiceData.getNewPoNumber());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY_HD);
        myInvoicesScreen.clickCancelButton();

        myInvoicesScreen.switchToTeamView();
        TeamInvoicesScreen teaminvoicesscreen = new TeamInvoicesScreen();
        teaminvoicesscreen.selectInvoice(invoiceNumber);
        teaminvoicesscreen.clickChangePOPopup();
        teaminvoicesscreen.changePO(invoiceData.getNewPoNumber());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY_HD);
        teaminvoicesscreen.clickCancelButton();
        NavigationSteps.navigateBackScreen();
    }
}

package com.cyberiansoft.test.ios10_client.testcases.regular.invoices;

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
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
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

public class IOSInvoicePaymentTestCases extends IOSRegularBaseTestCase {

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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        servicesScreen.waitServicesScreenLoaded();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();
        BaseUtils.waitABit(1000 * 60);
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickInvoicePayButton();
        invoiceInfoScreen.changePaynentMethodToCashNormal();
        invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
        invoiceInfoScreen.clickInvoicePayDialogButon();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();
        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
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
        InvoicePaymentsTabWebPage invoicePaymentsWebPage = new InvoicePaymentsTabWebPage(webdriver);
        invoicesWebPage.clickInvoicePayments(invoiceNumber);
        Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeAmountValue("Cash/Check"), PricesCalculations.getPriceRepresentation(cashcheckamount));
        Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeCreatedByValue("Cash/Check"), "Employee Simple 20%");

        Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeAmountValue("PO/RO"), expectedPrice);
        Assert.assertEquals(invoicePaymentsWebPage.getPaymentsTypeCreatedByValue("PO/RO"), "Back Office");
        invoicePaymentsWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderMyInvoice(String rowID,
                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String cashcheckamount = "100";
        final String expectedPrice = "$0.00";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        servicesScreen.waitServicesScreenLoaded();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();
        BaseUtils.waitABit(1000 * 60);
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickInvoicePayButton();
        invoiceInfoScreen.changePaynentMethodToCashNormal();
        invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
        invoiceInfoScreen.clickInvoicePayDialogButon();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickSaveAsDraft();
        RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
        myInvoicesScreen.changePO(invoiceData.getNewPoNumber());
        RegularNavigationSteps.navigateBackScreen();
        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
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
        InvoicePaymentsTabWebPage invoicePaymentsWebPage = new InvoicePaymentsTabWebPage(webdriver);
        invoicesWebPage.clickInvoicePayments(invoiceNumber);

        Assert.assertEquals(invoicePaymentsWebPage.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
        invoicePaymentsWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatPaymentIsSendToBOWhenPONumberIsChangedUnderTeamInvoice(String rowID,
                                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String expectedPrice = "$0.00";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        servicesScreen.waitServicesScreenLoaded();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();
        BaseUtils.waitABit(1000 * 60);
        RegularHomeScreenSteps.navigateToTeamInvoicesScreen();
        RegularTeamInvoicesScreen teamInvoicesScreen = new RegularTeamInvoicesScreen();
        teamInvoicesScreen.selectInvoice(invoiceNumber);
        teamInvoicesScreen.clickChangePOPopup();
        teamInvoicesScreen.changePO(invoiceData.getNewPoNumber());
        teamInvoicesScreen.clickHomeButton();

        Helpers.waitABit(5000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPage.clickInvoicesLink();
        invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_ALL);
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();

        Assert.assertEquals(invoicesWebPage.getInvoicePONumber(invoiceNumber), invoiceData.getNewPoNumber());
        String mainWindowHandle = webdriver.getWindowHandle();
        InvoicePaymentsTabWebPage invoicePaymentsWebPage = new InvoicePaymentsTabWebPage(webdriver);
        invoicesWebPage.clickInvoicePayments(invoiceNumber);

        Assert.assertEquals(invoicePaymentsWebPage.getPaymentDescriptionTypeAmountValue("PO #: " + invoiceData.getNewPoNumber()), expectedPrice);
        invoicePaymentsWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatMessageInvoicPONumberShouldntBeEmptyIsShownForTeamAndMyInvoices(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        servicesScreen.waitServicesScreenLoaded();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword("Zayats", "1111");
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();
        homeScreen.clickMyInvoicesButton();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_PO);
        myInvoicesScreen.changePO(invoiceData.getNewPoNumber());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
        myInvoicesScreen.clickCancel();

        myInvoicesScreen.switchToTeamView();

        RegularTeamInvoicesScreen teamInvoicesScreen = new RegularTeamInvoicesScreen();
        teamInvoicesScreen.selectInvoice(invoiceNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CHANGE_PO);
        teamInvoicesScreen.changePO(invoiceData.getNewPoNumber());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY);
        teamInvoicesScreen.clickCancel();
        teamInvoicesScreen.clickHomeButton();
    }
}

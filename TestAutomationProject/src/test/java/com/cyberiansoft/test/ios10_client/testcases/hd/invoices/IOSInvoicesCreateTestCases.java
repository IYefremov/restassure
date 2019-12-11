package com.cyberiansoft.test.ios10_client.testcases.hd.invoices;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.ServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.hdvalidations.VehicleInfoValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectEmployeePopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInvoicesCreateTestCases extends IOSHDBaseTestCase {

    private WholesailCustomer _002_Test_Customer = new WholesailCustomer();

    @BeforeClass(description = "Invoice Create Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInvoiceCreateTestCasesDataPath();
        _002_Test_Customer.setCompanyName("002 - Test Company");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWithTwoWOsAndCopyVehicle(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();
        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber1), workOrderData.getWorkOrderPrice());
        MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, _002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        Assert.assertEquals(vehicleScreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
        NavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectDefaultInvoiceType();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.addWorkOrder(workOrderNumber1);
        final String invoicenum = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        NavigationSteps.navigateBackScreen();
        Helpers.waitABit(10 * 1000);

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
        invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoicenum);
        invoicesWebPage.clickFindButton();
        Assert.assertTrue(invoicesWebPage.isInvoiceDisplayed(invoicenum));
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceFromWOInMyWOsList(String rowID,
                                                   String description, JSONObject testData) {
        final String employee = "Employee Simple 20%";

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        selectedServiceDetailsScreen.clickAdjustments();
        selectedServiceDetailsScreen.selectAdjustment(workOrderData.getPercentageServiceData().
                getServiceAdjustment().getAdjustmentData().getAdjustmentName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getPercentageServiceData().getServicePrice());

        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServiceQuantity() != null) {
                selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
                selectedServiceDetailsScreen.saveSelectedServiceDetails();
            } else
                selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        for (ServiceData serviceData : workOrderData.getPercentageServices())
            servicesScreen.selectService(serviceData.getServiceName());
        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        PriceMatrixScreen priceMatrixScreen = selectedServiceDetailsScreen.selectMatrics(matrixServiceData.getHailMatrixName());
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        priceMatrixScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
        Assert.assertTrue(priceMatrixScreen.isNotesExists());
        Assert.assertTrue(priceMatrixScreen.getTechniciansValue().contains(employee));
        Assert.assertEquals(priceMatrixScreen.getPrice(), vehiclePartData.getVehiclePartPrice());
        for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices())
            priceMatrixScreen.selectDiscaunt(serviceData.getServiceName());

        priceMatrixScreen.clickSaveButton();
        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_PO_IS_REQUIRED_REGULAR);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        NavigationSteps.navigateBackScreen();
        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.myInvoiceExists(invoiceNumber);
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatCreateInvoiceCheckMarkIsNotShownForWOThatIsSelectedForBilling(String rowID,
                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectDefaultInvoiceType();
        invoiceInfoScreen.clickFirstWO();
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertFalse(orderSummaryScreen.isApproveAndCreateInvoiceExists());
        orderSummaryScreen.clickCancelButton();
        Helpers.acceptAlert();
        invoiceInfoScreen.cancelInvoice();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesCreateInvoiceWithTwoWOsAndCopyVehicleForRetailCustomer(String rowID,
                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final RetailCustomer retailcustomer = new RetailCustomer("19319", "");

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(retailcustomer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        /*VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(VIN);

        vehicleScreen.setMakeAndModel(_make, _model);
        vehicleScreen.setColor(_color);
        vehicleScreen.setYear(_year);
        vehicleScreen.setMileage(mileage);
        vehicleScreen.setFuelTankLevel(fueltanklevel);
        vehicleScreen.setType(_type);
        vehicleScreen.setStock(stock);
        vehicleScreen.setRO(_ro);*/
        String workOrderNumber1 = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();

        ServicesScreenSteps.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenSteps.setServiceDetailsData(workOrderData.getMoneyServiceData());
        ServiceDetailsScreenValidations.verifyServiceDetailsScreenHasVehicleParts(workOrderData.getMoneyServiceData().getVehicleParts());
        ServiceDetailsScreenSteps.saveServiceDetails();
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertEquals(servicesScreen.getNumberOfServiceSelectedItems(workOrderData.getMoneyServiceData().getServiceName()),
                workOrderData.getMoneyServiceData().getVehicleParts().size());
        NavigationSteps.navigateToOrderSummaryScreen();
        WorkOrdersSteps.saveWorkOrder();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber1), workOrderData.getWorkOrderPrice());
        MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, retailcustomer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        //Assert.assertEquals(vehicleScreen.getMake(), _make);
        //Assert.assertEquals(vehicleScreen.getModel(), _model);
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectDefaultInvoiceType();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoicenum = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.addWorkOrder(workOrderNumber1);
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myWorkOrdersScreen.clickHomeButton();
        Helpers.waitABit(30 * 1000);

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
        invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_DRAFT);
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoicenum);
        invoicesWebPage.clickFindButton();
        Assert.assertTrue(invoicesWebPage.isInvoiceDisplayed(invoicenum));
        DriverBuilder.getInstance().getDriver().quit();
    }
}

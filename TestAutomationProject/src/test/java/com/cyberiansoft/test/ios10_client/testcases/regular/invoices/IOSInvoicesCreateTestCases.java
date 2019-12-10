package com.cyberiansoft.test.ios10_client.testcases.regular.invoices;

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
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularInvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyInvoicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularWizardScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInvoicesCreateTestCases extends IOSRegularBaseTestCase {

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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, _002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        Assert.assertEquals(vehicleScreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        orderSummaryScreen.selectDefaultInvoiceType();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.addWorkOrder(workOrderNumber1);
        final String invoicenum = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();

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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServiceDetailsDataAndSave(workOrderData.getMoneyServiceData());

        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickAdjustmentsCell();
        RegularServiceDetailsScreenSteps.selectServiceAdjustment(workOrderData.getPercentageServiceData().
                getServiceAdjustment());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getPercentageServiceData().getServicePrice());

        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        RegularServicesScreenSteps.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServiceQuantity() != null) {
                RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
                selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else {
                RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
                selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
            }
        }
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        for (ServiceData serviceData : workOrderData.getPercentageServices())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.openCustomServiceDetails(matrixServiceData.getMatrixServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        RegularPriceMatrixScreen priceMatrixScreen = selectedServiceDetailsScreen.selectMatrics(matrixServiceData.getHailMatrixName());
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(employee));
        Assert.assertEquals(vehiclePartScreen.getPrice(), vehiclePartData.getVehiclePartPrice());
        for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices())
            vehiclePartScreen.selectDiscaunt(serviceData.getServiceName());
        vehiclePartScreen.saveVehiclePart();

        priceMatrixScreen.clickSave();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.clickSave();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_PO_IS_REQUIRED);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }

}

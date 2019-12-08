package com.cyberiansoft.test.ios10_client.testcases.regular.workorders;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LicensesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyWorkOrdersScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
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

public class IOSCreateWorkOrderTestCases extends IOSRegularBaseTestCase {

    private WholesailCustomer _002_Test_Customer = new WholesailCustomer();
    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();
    private WholesailCustomer Specific_Client = new WholesailCustomer();

    @BeforeClass(description = "Create Work Orders Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCreateWorkOrdersTestCasesDataPath();
        _002_Test_Customer.setCompanyName("002 - Test Company");
        _003_Test_Customer.setCompanyName("003 - Test Company");
        Specific_Client.setCompanyName("Specific_Client");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testBlockForTheSameVINIsONVerifyDuplicateVINMessageWhenCreate2WOWithOneVIN(String rowID,
                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();
        orderSummaryScreen.waitForCustomWarningMessage(String.format(AlertsCaptions.ALERT_YOU_CANT_CREATE_WORK_ORDER_BECAUSE_VIN_EXISTS,
                WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON.getWorkOrderTypeName(), workOrderData.getVehicleInfoData().getVINNumber()), "Cancel");
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_PRICE_MATRIX);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }
        RegularVehiclePartsScreenSteps.savePriceMatrixData();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
            RegularServicesScreenSteps.waitServicesScreenLoad();
        }
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertEquals(selectedServicesScreen.getNumberOfSelectedServices(), 1);
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testRegularWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection(String rowID,
                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumberber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumberber);
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumberber);
        approveInspectionsScreen.approveInspectionApproveAllAndSignature();
        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumberber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.openCustomServiceDetails(inspectionData.getBundleService().getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : inspectionData.getBundleService().getServices()) {
            if (serviceData.isSelected())
                Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsSelected(serviceData.getServiceName()));
            if (!serviceData.isSelected())
                Assert.assertTrue(selectedServiceBundleScreen.checkBundleIsNotSelected(serviceData.getServiceName()));
        }

        selectedServiceBundleScreen.clickServicesIcon();
        for (ServiceData serviceData : inspectionData.getBundleService().getServices())
            Assert.assertTrue(selectedServiceBundleScreen.isBundleServiceExists(serviceData.getServiceName()));
        selectedServiceBundleScreen.clickCloseServicesPopup();
        selectedServiceBundleScreen.clickCancel();
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();


        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));

        Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());

        Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWONumberIsNotDuplicated(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        final String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();

        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.approveWorkOrder(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
        invoiceInfoScreen.clickSaveAsFinal();
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
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        invoicesWebPage.archiveInvoiceByNumber(invoiceNumber);
        Assert.assertFalse(invoicesWebPage.isInvoiceDisplayed(invoiceNumber));
        webdriver.quit();

        //Create second WO
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        final String workOrder2 = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        myWorkOrdersScreen.approveWorkOrder(workOrder2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrder2);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        homeScreen.clickStatusButton();
        homeScreen.updateDatabase();
        RegularMainScreen mainScreen = new RegularMainScreen();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        //Create third WO
        homeScreen.clickMyWorkOrdersButton();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        final String workOrder3 = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();

        WorkOrdersWebPage workorderspage = new WorkOrdersWebPage(webdriver);
        operationsWebPage.clickWorkOrdersLink();

        workorderspage.makeSearchPanelVisible();
        workorderspage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        workorderspage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
        workorderspage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        workorderspage.setSearchOrderNumber(workOrder3);
        workorderspage.clickFindButton();

        Assert.assertEquals(workorderspage.getWorkOrdersTableRowCount(), 1);
        webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne(String rowID,
                                                                                             String description, JSONObject testData) {

        final String[] VINs = {"2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"};
        final String makes[] = {"Chrysler", "Ford", ""};
        final String models[] = {"Town and Country", "Explorer", ""};


        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        for (int i = 0; i < VINs.length; i++) {
            vehicleScreen.setVIN(VINs[i]);
            Assert.assertEquals(vehicleScreen.getMake(), makes[i]);
            Assert.assertEquals(vehicleScreen.getModel(), models[i]);
            vehicleScreen.clearVINCode();
        }

        vehicleScreen.cancelOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone(String rowID,
                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.selectServiceVehicleParts(workOrderData.getServiceData().getVehicleParts());

        for (VehiclePartData vehiclePartData : workOrderData.getServiceData().getVehicleParts()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            Assert.assertTrue(selectedServiceDetailsScreen.getVehiclePartValue().contains(vehiclePartData.getVehiclePartName()));
        }
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServiceSelectedItems(workOrderData.getServiceData().getServiceName()), workOrderData.getServiceData().getVehicleParts().size());
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
                                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_VIN_ONLY);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
        vehicleScreen.clickVINField();
        Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
        vehicleScreen.cancelOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatValidationIsPresentForVehicleTrimField(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String trimvalue = "Sport Plus";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_VEHICLE_TRIM_VALIDATION);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TRIM_REQUIRED);
        vehicleScreen.setTrim(trimvalue);
        Assert.assertEquals(vehicleScreen.getTrim(), trimvalue);

        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF(String rowID,
                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String location = "All locations";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TOTAL_SALE_NOT_REQUIRED);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertFalse(orderSummaryScreen.isTotalSaleFieldPresent());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(location);
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertFalse(orderSummaryScreen.isTotalSaleFieldPresent());
        RegularWizardScreensSteps.clickSaveButton();
        RegularTeamWorkOrdersSteps.waitTeamWorkOrdersScreenLoaded();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatSearchBarIsPresentForServicePackScreen(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            if (serviceData.getVehiclePart() != null)
                RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            else
                RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO(String rowID,
                                                                       String description, JSONObject testData) {

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.clickChangeScreen();
        vehicleScreen.clickCancel();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_CREATION);
        vehicleScreen.clickCancel();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_CREATION);

        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        String workOrderNumber = myWorkOrdersScreen.getFirstWorkOrderNumberValue();
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        vehicleScreen.clickChangeScreen();
        vehicleScreen.clickCancel();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_EDIT);
        vehicleScreen.clickCancel();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_EDIT);

        RegularMyWorkOrdersSteps.openWorkOrderDetails(workOrderNumber);
        vehicleScreen.clickChangeScreen();
        vehicleScreen.clickCancel();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateWOFromServiceRequest(String rowID,
                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        RegularNavigationSteps.navigateBackScreen();

        //test case
        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        serviceRequestSscreen.selectServiceRequest(serviceRequestSscreen.getFirstServiceRequestNumber());
        serviceRequestSscreen.selectCreateWorkOrderRequestAction();
        RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice2()));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getBundleService().getBundleServiceName(), PricesCalculations.getPriceRepresentation(workOrderData.getBundleService().getBundleServiceAmount())));

        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getBundleService().getBundleServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();

        selectedServicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.THE_VIN_IS_INVALID_AND_SAVE_WORKORDER);
        serviceRequestSscreen.waitForServiceRequestScreenLoad();
        serviceRequestSscreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String license = "Iphone_Test_Spec_Client";
        final String percService = "Test Tax";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
        LicensesScreen licensesscreen = mainScreen.clickLicenses();
        licensesscreen.clickAddLicenseButtonAndAcceptAlert();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BaseUtils.waitABit(1000);
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        backOfficeHeaderPanel.clickCompanyLink();
        ActiveDevicesWebPage devicespage = new ActiveDevicesWebPage(webdriver);
        companyWebPage.clickManageDevicesLink();

        devicespage.setSearchCriteriaByName(license);
        String regCode = devicespage.getFirstRegCodeInTable();

        DriverBuilder.getInstance().getDriver().quit();
        RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen();
        LoginScreen loginscreen = selectenvscreen.selectEnvironment(envType.getEnvironmentTypeName());
        loginscreen.registeriOSDevice(regCode);
        mainScreen.userLogin(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN, iOSInternalProjectConstants.USER_PASSWORD);

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAvailableSelectedServicesOn();
        settingsScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(Specific_Client, WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(workOrderData.getMoneyServiceData().getServiceName());
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(percService));
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        orderSummaryScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_VIN_REQUIRED);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.clickSave();
        orderSummaryScreen.selectDefaultInvoiceType();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveEmptyPO();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();
    }
}

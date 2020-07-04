package com.cyberiansoft.test.ios10_client.testcases.hd.workorders;

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
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.InvoiceInfoScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSCreateWorkOrderTestCases extends IOSHDBaseTestCase {

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

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();


        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();
        orderSummaryScreen.waitForCustomWarningMessage(String.format(AlertsCaptions.ALERT_YOU_CANT_CREATE_WORK_ORDER_BECAUSE_VIN_EXISTS,
                WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON.getWorkOrderTypeName(), workOrderData.getVehicleInfoData().getVINNumber()), "Cancel");

        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_PRICE_MATRIX);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName()));
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        vehicleScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testNotMultipleServiceWithRequiredPanelsIsAddedOneTimeToWOAfterSelecting(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
            AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
            Assert.assertEquals(servicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), 1);
        }
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testRegularWOVerifyThatBundleItemsAreShownWhenCreateWOFromInspection(String rowID,
                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_003_Test_Customer, InspectionsTypes.INSP_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();

        servicesScreen.openCustomServiceDetails(inspectionData.getBundleService().getBundleServiceName());
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
        servicesScreen.saveWizard();

        myInspectionsScreen.approveInspectionAllServices(inspectionNumber,
                iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        MyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber,
                WorkOrdersTypes.WO_TYPE_FOR_CALC);
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.openServiceDetails(inspectionData.getBundleService().getBundleServiceName());
        SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
        for (ServiceData serviceData : inspectionData.getBundleService().getServices()) {
            if (serviceData.isSelected())
                Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(serviceData.getServiceName()));
            if (!serviceData.isSelected())
                Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(serviceData.getServiceName()));
        }
        selectedservicebundlescreen.clickServicesIcon();
        for (ServiceData serviceData : inspectionData.getBundleService().getServices())
            Assert.assertTrue(selectedservicebundlescreen.isBundleServiceExists(serviceData.getServiceName()));
        selectedservicebundlescreen.clickCloseServicesPopup();
        selectedservicebundlescreen.clickCancelBundlePopupButton();
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWhenUseCopyServicesActionForWOAllServiceInstancesShouldBeCopied(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();


        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);

        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));

        Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        MyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertEquals(servicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());
        Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), workOrderData.getWorkOrderPrice());
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWONumberIsNotDuplicated(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();

        //customer approval ON
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        final String workOrderNumber1 = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
        invoiceInfoScreen.clickSaveAsFinal();
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
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        invoicesWebPage.archiveInvoiceByNumber(invoiceNumber);
        Assert.assertFalse(invoicesWebPage.isInvoiceDisplayed(invoiceNumber));
        webdriver.quit();

        //Create second WO
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        final String workOrderNumber2 = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        NavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber2, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber2);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(testCaseData.getInvoiceData().getQuestionScreenData());
        invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen = new MyWorkOrdersScreen();
        homeScreen = myWorkOrdersScreen.clickHomeButton();
        homeScreen.clickStatusButton();
        homeScreen.updateDatabase();
        MainScreen mainScreeneen = new MainScreen();
        homeScreen = mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        //Create third WO
        myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        final String workOrderNumber3 = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToOrderSummaryScreen();

        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.clickHomeButton();
        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        backofficeHeader.clickOperationsLink();
        operationsWebPage = new OperationsWebPage(webdriver);
        operationsWebPage.clickWorkOrdersLink();
        WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
        workOrdersWebPage.makeSearchPanelVisible();
        workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        workOrdersWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        workOrdersWebPage.setSearchOrderNumber(workOrderNumber3);

        workOrdersWebPage.clickFindButton();

        Assert.assertEquals(workOrdersWebPage.getWorkOrdersTableRowCount(), 1);
        webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatInCaseValidVINIsDecodedReplaceExistingMakeAndModelWithNewOne(String rowID,
                                                                                             String description, JSONObject testData) {

        final String[] VINs = {"2A8GP54L87R279721", "1FMDU32X0PUB50142", "GFFGG"};
        final String[] makes = {"Chrysler", "Ford", null};
        final String[] models = {"Town and Country", "Explorer", null};

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INVOICE_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        for (int i = 0; i < VINs.length; i++) {
            vehicleScreen.setVIN(VINs[i]);
            Assert.assertEquals(vehicleScreen.getMake(), makes[i]);
            Assert.assertEquals(vehicleScreen.getModel(), models[i]);
            vehicleScreen.clearVINCode();
        }
        vehicleScreen.cancelOrder();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWhenServiceDoNotHaveQuestionsAndSelectSeveralPanelsDoNotUnderlineAnyone(String rowID,
                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.slectServiceVehicleParts(workOrderData.getServiceData().getVehicleParts());

        for (VehiclePartData vehiclePartData : workOrderData.getServiceData().getVehicleParts()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
            Assert.assertTrue(selectedServiceDetailsScreen.getVehiclePartValue().contains(vehiclePartData.getVehiclePartName()));
        }
        ServiceDetailsScreenSteps.saveServiceDetails();
        Assert.assertEquals(servicesScreen.getNumberOfServiceSelectedItems(workOrderData.getServiceData().getServiceName()), workOrderData.getServiceData().getVehicleParts().size());
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
                                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_VIN_ONLY);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
        vehicleScreen.clickVINField();
        Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
        vehicleScreen.hideKeyboard();
        vehicleScreen.cancelOrder();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatValidationIsPresentForVehicleTrimField(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String trimvalue = "Sport Plus";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_VEHICLE_TRIM_VALIDATION);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TRIM_REQUIRED);
        vehicleScreen.setTrim(trimvalue);
        Assert.assertEquals(vehicleScreen.getTrim(), trimvalue);
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        vehicleScreen.saveWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatTotalSaleIsNotShownWhenCheckmarkTotalSaleRequiredIsNotSetToOFF(String rowID,
                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TOTAL_SALE_NOT_REQUIRED);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertFalse(orderSummaryScreen.isTotalSaleFieldPresent());
        orderSummaryScreen.clickSave();
        myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertFalse(orderSummaryScreen.isTotalSaleFieldPresent());
        orderSummaryScreen.clickSave();
        teamWorkordersScreen.waitTeamWorkOrdersScreenLoaded();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatSearchBarIsPresentForServicePackScreen(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            if (serviceData.getVehiclePart() != null)
                ServicesScreenSteps.selectServiceWithServiceData(serviceData);
            else
                ServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatCancelMessageIsShownForNewOrExistingWO(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.clickCancelButton();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_CREATION);
        vehicleScreen.clickCancelButton();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_CREATION);

        String workOrderNumber = myWorkOrdersScreen.getFirstWorkOrderNumberValue();
        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.clickCancelButton();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_EDIT);
        vehicleScreen.clickCancelButton();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.STOP_WORKORDER_EDIT);
        myWorkOrdersScreen.openWorkOrderDetails(workOrderNumber);
        vehicleScreen.clickCancelButton();
        NavigationSteps.navigateBackScreen();
    }

    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateWorkOrderWithTypeIsAssignedToASpecificClient(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String license = "Iphone_Test_Spec_Client";

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreeneen = homeScreen.clickLogoutButton();
        LicensesScreen licensesscreen = mainScreeneen.clickLicenses();
        licensesscreen.clickAddLicenseButtonAndAcceptAlert();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        backofficeHeader.clickCompanyLink();

        ActiveDevicesWebPage devicespage = new ActiveDevicesWebPage(webdriver);
        companyWebPage.clickManageDevicesLink();

        devicespage.setSearchCriteriaByName(license);
        String regCode = devicespage.getFirstRegCodeInTable();

        DriverBuilder.getInstance().getDriver().quit();

        SelectEnvironmentPopup selectenvscreen = new SelectEnvironmentPopup();
        LoginScreen loginscreen = selectenvscreen.selectEnvironment(envType.getEnvironmentTypeName());
        loginscreen.registeriOSDevice(regCode);
        mainScreeneen.userLogin(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN, iOSInternalProjectConstants.USER_PASSWORD);

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(Specific_Client, WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
        VehicleScreen vehicleScreen = new VehicleScreen();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkDefaultServiceIsSelected());
        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertTrue(orderSummaryScreen.checkDefaultServiceIsSelected());
        Assert.assertTrue(orderSummaryScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        orderSummaryScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_VIN_REQUIRED);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectDefaultInvoiceType();
        invoiceInfoScreen.clickSaveEmptyPO();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myWorkOrdersScreen.clickHomeButton();
    }
}

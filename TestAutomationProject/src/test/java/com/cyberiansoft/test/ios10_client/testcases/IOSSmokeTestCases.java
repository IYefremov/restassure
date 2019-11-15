package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.verifications.ServiceRequestsListVerifications;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.OrderMonitorServiceDetailsPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.ordermonitorphases.OrderMonitorPhases;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IOSSmokeTestCases extends ReconProBaseTestCase {

    //public HomeScreen homeScreen;
    private WholesailCustomer Specific_Client = new WholesailCustomer();
    private WholesailCustomer ZAZ_Motors = new WholesailCustomer();
    private WholesailCustomer _002_Test_Customer = new WholesailCustomer();
    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();
    private WholesailCustomer _004_Test_Customer = new WholesailCustomer();
    private WholesailCustomer Test_Company_Customer = new WholesailCustomer();
    private RetailCustomer testRetailCustomer = new RetailCustomer("Retail", "Customer");
    private RetailCustomer johnRetailCustomer = new RetailCustomer("John", "Simple_PO#_Req");

    @BeforeClass
    public void setUpSuite() {
        Specific_Client.setCompanyName("Specific_Client");
        ZAZ_Motors.setCompanyName("Zaz Motors");
        _002_Test_Customer.setCompanyName("002 - Test Company");
        _003_Test_Customer.setCompanyName("003 - Test Company");
        _004_Test_Customer.setCompanyName("004 - Test Company");
        Test_Company_Customer.setCompanyName("Test Company");
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getGeneralSuiteTestCasesDataPath();
        mobilePlatform = MobilePlatform.IOS_HD;
        initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
                ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "Ios_automation",
                envType);
        MainScreen mainScreen = new MainScreen();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.setInsvoicesCustomLayoutOff();
        settingsScreen.clickHomeButton();
    }

    @AfterMethod
    public void closeBrowser() {
        if (webdriver != null)
            webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUpdateDatabase(String rowID,
                                   String description, JSONObject testData) {
        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        homeScreen.clickStatusButton();
        homeScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUpdateVIN(String rowID,
                              String description, JSONObject testData) {
        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateVIN();
        homeScreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        homeScreen.clickStatusButton();
        homeScreen.updateVIN();
        //homeScreen.clickLogoutButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateRetailCustomer(String rowID,
                                         String description, JSONObject testData) {

        RetailCustomer newCustomer = new RetailCustomer("supermy12", "super");
        newCustomer.setCompanyName("supercompany");
        newCustomer.setCustomerAddress1( "470 Copper Drive");
        newCustomer.setCustomerCity("New Port");
        newCustomer.setCustomerZip("19804");
        newCustomer.setCustomerPhone("723-1234567");
        newCustomer.setMailAddress("test@cyberiansoft.com");
        newCustomer.setCustomerState("Delaware");
        newCustomer.setCustomerCountry("United States");

        RetailCustomer editedCustomer = new RetailCustomer("supernewmy12", "superedited");
        editedCustomer.setCompanyName("supercompanyedited");
        editedCustomer.setCustomerAddress1( "600 Markley Street");
        editedCustomer.setCustomerCity("Port Reading");
        editedCustomer.setCustomerZip("07064");
        editedCustomer.setCustomerPhone("723-1234576");
        editedCustomer.setMailAddress("test@getnada.com");
        editedCustomer.setCustomerState("California");
        editedCustomer.setCustomerCountry("United States");

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BaseUtils.waitABit(1000);
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        backOfficeHeaderPanel.clickCompanyLink();
        ClientsWebPage clientsWebPage = new ClientsWebPage(webdriver);
        companyWebPage.clickClientsLink();
        clientsWebPage.searchClientByName(newCustomer.getFullName());
        if (clientsWebPage.isClientPresentInTable(newCustomer.getFullName()))
            clientsWebPage.archiveFirstClient();

        clientsWebPage.searchClientByName(editedCustomer.getFullName());
        if (clientsWebPage.isClientPresentInTable(editedCustomer.getFullName()))
            clientsWebPage.archiveFirstClient();

        DriverBuilder.getInstance().getDriver().quit();

        HomeScreen homeScreen = new HomeScreen();
        homeScreen.clickLogoutButton();
        MainScreen mainScreen = new MainScreen();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        AddCustomerScreen addCustomerScreen = customersScreen.clickAddCustomersButton();

        addCustomerScreen.addCustomer(newCustomer);
        addCustomerScreen.clickSaveBtn();
        Assert.assertTrue(customersScreen.isCustomerExists(newCustomer.getFirstName()));
        customersScreen.selectCustomerToEdit(newCustomer);

        addCustomerScreen.editCustomer(editedCustomer);
        addCustomerScreen.clickSaveBtn();
        customersScreen.selectCustomerWithoutEditing(editedCustomer.getFirstName());
        Assert.assertTrue(customersScreen.isCustomerExists(editedCustomer.getFirstName()));
        customersScreen.clickHomeButton();
        homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BaseUtils.waitABit(1000);
        backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        companyWebPage = new CompanyWebPage(webdriver);
        backOfficeHeaderPanel.clickCompanyLink();
        clientsWebPage = new ClientsWebPage(webdriver);
        companyWebPage.clickClientsLink();

        clientsWebPage.deleteUserViaSearch(editedCustomer.getFullName());

        DriverBuilder.getInstance().getDriver().quit();

        homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        Assert.assertFalse(customersScreen.isCustomerExists(editedCustomer.getFirstName()));
        customersScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 8685:Set Inspection to non Single page (HD) ", description = "Set Inspection To Non Single Page Inspection Type")
    public void testSetInspectionToNonSinglePage() {

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditRetailInspectionNotes(String rowID,
                                              String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String _notes1 = "Test\nTest 2";
        final String quickNote = "This is test Quick Notes";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        final String inspNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.saveWizard();

        myInspectionsScreen.selectInspectionForEdit(inspNumber);
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();
        WizardScreensSteps.clickNotesButton();
        NotesScreen notesScreen = new NotesScreen();
        notesScreen.setNotes(_notes1);
        notesScreen.addQuickNotes(quickNote);

        notesScreen.clickSaveButton();
        vehicleScreen.clickNotesButton();
        Assert.assertEquals(notesScreen.getNotesValue(), _notes1 + "\n" + quickNote);
        notesScreen.clickSaveButton();
        vehicleScreen.cancelOrder();
        myInspectionsScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnDevice(String rowID,
                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        final String inspNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.saveWizard();

        myInspectionsScreen.selectInspectionForApprove(inspNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspNumber);
        approveInspectionsScreen.clickApproveAfterSelection();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testArchiveAndUnArchiveTheInspection(String rowID,
                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String archiveReason = "Reason 2";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        String myinspetoarchive = vehicleScreen.getInspectionNumber();
        vehicleScreen.saveWizard();

        myInspectionsScreen.clickHomeButton();

        myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        myInspectionsScreen.archiveInspection(myinspetoarchive,
                archiveReason);
        Assert.assertFalse(myInspectionsScreen.isInspectionExists(myinspetoarchive));
        myInspectionsScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateWorkOrderWithTeamSharingOption(String rowID,
                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrdersData().get(0);

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();

        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();

        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), workOrderData.getMoneyServiceData().getServicePrice());
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), workOrderData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        selectedServiceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMoneyServiceData().getServiceName()));
        // =====================================
        servicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());

        servicesScreen.openServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), workOrderData.getPercentageServiceData().getServicePrice());
        selectedServiceDetailsScreen.clickAdjustments();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
                workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
        selectedServiceDetailsScreen.selectAdjustment(workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), workOrderData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        // =====================================
        servicesScreen.selectService(workOrderData.getBundleService().getBundleServiceName());
        SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
        for (ServiceData serviceData : workOrderData.getBundleService().getServices()) {
            if (serviceData.isSelected()) {
                Assert.assertTrue(selectedservicebundlescreen.checkBundleIsSelected(serviceData.getServiceName()));
                selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
                selectedServiceDetailsScreen.setServiceQuantityValue(serviceData.getServiceQuantity());
            } else {
                Assert.assertTrue(selectedservicebundlescreen.checkBundleIsNotSelected(serviceData.getServiceName()));
                selectedservicebundlescreen.selectBundle(serviceData.getServiceName());
            }
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getBundleService().getBundleServiceName()));
        // =====================================
        for (ServiceData serviceData : workOrderData.getPercentageServices()) {
            servicesScreen.selectService(serviceData.getServiceName());
            Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        }
        // =====================================
        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        PriceMatrixScreen priceMatrixScreen = servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
        priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
        priceMatrixScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        Assert.assertEquals(priceMatrixScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
        Assert.assertTrue(priceMatrixScreen.isNotesExists());
        for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
            priceMatrixScreen.selectDiscaunt(serviceData.getServiceName());
        }
        priceMatrixScreen.clickSaveButton();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        for (ServiceData serviceData : workOrderData.getSelectedServices()) {
            Assert.assertTrue(orderSummaryScreen.checkServiceIsSelected(serviceData.getServiceName()));
        }
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        orderSummaryScreen.saveWizard();

        WorkOrderData workOrderDataCopiedServices = testCaseData.getWorkOrdersData().get(1);
        MyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, Specific_Client,
                WorkOrdersTypes.SPECIFIC_CLIENT_TEST_WO1);
        vehicleScreen.setVIN(workOrderDataCopiedServices.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.verifyServicesAreSelected(workOrderDataCopiedServices.getSelectedServices());
        NavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderDataCopiedServices.getWorkOrderPrice());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionsOnDeviceViaAction(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionsID = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
            vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
            vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
            inspectionsID.add(vehicleScreen.getInspectionNumber());
            vehicleScreen.saveWizard();
        }

        myInspectionsScreen.clickActionButton();
        for (String inspectionNumber : inspectionsID) {
            myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        }

        myInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        for (String inspectionNumber : inspectionsID) {
            approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
            approveInspectionsScreen.clickApproveAfterSelection();
        }

        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        for (String inspectionNumber : inspectionsID) {
            Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspectionNumber));
        }
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testArchiveInspectionsOnDeviceViaAction(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionsID = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
            vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
            vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
            inspectionsID.add(vehicleScreen.getInspectionNumber());
            vehicleScreen.saveWizard();

        }
        myInspectionsScreen.archiveInspections(inspectionsID, testCaseData.getArchiveReason());

        for (String inspectionNumber : inspectionsID) {
            Assert.assertFalse(myInspectionsScreen.isInspectionExists(inspectionNumber));
        }
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnBackOfficeFullInspectionApproval(String rowID,
                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        vehicleScreen.saveWizard();

        myInspectionsScreen.clickHomeButton();
        MainScreen mainScreeneen = homeScreen.clickLogoutButton();
        mainScreeneen.updateDatabase();
        Helpers.waitABit(60 * 1000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();

        inspectionsWebPage.approveInspectionByNumber(inspectionNumber);

        DriverBuilder.getInstance().getDriver().quit();

        mainScreeneen.updateDatabase();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspectionNumber));

        myInspectionsScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnBackOfficeLinebylineApproval(String rowID,
                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        //MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.VITALY_TEST_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

        vehicleScreen.saveWizard();
        myInspectionsScreen.clickHomeButton();
        MainScreen mainScreeneen = homeScreen.clickLogoutButton();
        mainScreeneen.updateDatabase();
        Helpers.waitABit(10 * 1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();

        inspectionsWebPage.approveInspectionLinebylineApprovalByNumber(
                inspectionNumber, inspectionData.getServicesList());

        DriverBuilder.getInstance().getDriver().quit();

        mainScreeneen.updateDatabase();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspectionNumber));

        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        final String teamName = "Default team";
        final String serviceName = "Test Company (Universal Client)";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();

        ServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer,
                ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        final VehicleInfoData vehicleInfoData = serviceRequestData.getVihicleInfo();
        vehicleScreen.setVIN(vehicleInfoData.getVINNumber());
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.THE_VIN_IS_INCORRECT);

        vehicleScreen.setMakeAndModel(vehicleInfoData.getVehicleMake(), vehicleInfoData.getVehicleModel());
        vehicleScreen.setColor(vehicleInfoData.getVehicleColor());
        vehicleScreen.setYear(vehicleInfoData.getVehicleYear());
        vehicleScreen.setMileage(vehicleInfoData.getMileage());
        vehicleScreen.setFuelTankLevel(vehicleInfoData.getFuelTankLevel());
        vehicleScreen.setType(vehicleInfoData.getVehicleType());
        vehicleScreen.setStock(vehicleInfoData.getVehicleStock());
        vehicleScreen.setRO(vehicleInfoData.getRoNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
            if (serviceData.getServiceQuantity() != null) {
                servicesScreen.selectService(serviceData.getServiceName());
                SelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
                servicedetailsscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
                servicedetailsscreen.saveSelectedServiceDetails();
            } else
                servicesScreen.selectService(serviceData.getServiceName());
        }

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(serviceRequestData.getInsuranceCompany().getInsuranceCompanyName());
        servicesScreen.clickSave();
        Helpers.waitForAlert();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.drawSignature();
        questionsScreen.clickSave();

        alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
        questionsScreen = new QuestionsScreen();
        questionsScreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestClient(newserviceRequestNumber), iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
        Assert.assertTrue(serviceRequestsScreen.getServiceRequestVehicleInfo(newserviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        serviceRequestsScreen.clickHomeButton();


        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.makeSearchPanelVisible();

        final ServiceRequestsListVerifications serviceRequestsListVerifications = new ServiceRequestsListVerifications();
        serviceRequestsListVerifications.verifySearchFieldsAreVisible();

        serviceRequestsListInteractions.selectSearchTeam(teamName);
        serviceRequestsListInteractions.selectSearchTechnician("Employee Simple 20%");
        serviceRequestsListInteractions.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        serviceRequestsListInteractions.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
        serviceRequestsListInteractions.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        serviceRequestsListInteractions.setSearchFreeText(newserviceRequestNumber);
        serviceRequestsListInteractions.clickFindButton();
        serviceRequestsListVerifications.verifySearchResultsByServiceName(serviceName);
        serviceRequestsListInteractions.selectFirstServiceRequestFromList();
        Assert.assertEquals(serviceRequestsListInteractions.getVINValueForSelectedServiceRequest(), serviceRequestData.getVihicleInfo().getVINNumber());
        Assert.assertEquals(serviceRequestsListInteractions.getCustomerValueForSelectedServiceRequest(), serviceName);
        Assert.assertEquals(serviceRequestsListInteractions.getEmployeeValueForSelectedServiceRequest(), "Employee Simple 20% (Default team)");
        Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Bundle1_Disc_Ex $150.00 (1.00)"));
        Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Quest_Req_Serv $10.00 (1.00)"));
        Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Wheel $70.00 (3.00)"));
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionFromServiceRequest(String rowID,
                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String questionValue = "Test Answer 1";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToSinglePageInspection();
        settingsScreen.clickHomeButton();

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(newserviceRequestNumber, InspectionsTypes.INSP_FOR_SR_INSPTYPE);

        SinglePageInspectionScreen singlePageInspectionScreen = new SinglePageInspectionScreen();
        String inspectionNumber = singlePageInspectionScreen.getInspectionNumber();
        singlePageInspectionScreen.expandToFullScreeenSevicesSection();
        BaseUtils.waitABit(2000);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2()));
            if (serviceData.getQuestionData() != null) {
                SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
                selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
                selectedServiceDetailsScreen.saveSelectedServiceDetails();
            }
        }
        BundleServiceData bundleServiceData = inspectionData.getBundleService();
        Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(bundleServiceData.getBundleServiceName(), bundleServiceData.getBundleServiceAmount()));
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServiceQuantity() != null) {
                selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
                selectedServiceDetailsScreen.saveSelectedServiceDetails();
            } else
                selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();


        singlePageInspectionScreen = new SinglePageInspectionScreen();
        singlePageInspectionScreen.collapseFullScreen();

        singlePageInspectionScreen.expandToFullScreeenQuestionsSection();

        Assert.assertTrue(singlePageInspectionScreen.isSignaturePresent());
        singlePageInspectionScreen.swipeScreenRight();
        singlePageInspectionScreen.swipeScreenRight();
        singlePageInspectionScreen.swipeScreenUp();

        Assert.assertTrue(singlePageInspectionScreen.isAnswerPresent(questionValue));
        singlePageInspectionScreen.collapseFullScreen();
        singlePageInspectionScreen.clickSaveButton();
        serviceRequestsScreen = new ServiceRequestsScreen();
        homeScreen = serviceRequestsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspectionNumber));
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());

        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        approveInspectionsScreen.clickApproveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitInspectionsScreenLoaded();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateWOFromServiceRequest(String rowID,
                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        homeScreen = settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        homeScreen = customersScreen.clickHomeButton();
        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        serviceRequestsScreen.clickHomeButton();
        //test case
        boolean createWOExists = false;
        final int timaoutMinules = 15;
        int iterator = 0;
        while ((iterator < timaoutMinules) | (!createWOExists)) {

            serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
            serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
            createWOExists = serviceRequestsScreen.isCreateWorkOrderActionExists();
            if (!createWOExists) {
                serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
                serviceRequestsScreen.clickHomeButton();
                Helpers.waitABit(1000 * 30);
            } else {
                serviceRequestsScreen.selectCreateWorkOrderRequestAction();
                WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup();
                workOrderTypesPopup.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR.getWorkOrderTypeName());
                break;
            }

        }

        VehicleScreen vehicleScreen = new VehicleScreen();
        String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice2()));
        Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getBundleService().getBundleServiceName(), PricesCalculations.getPriceRepresentation(workOrderData.getBundleService().getBundleServiceAmount())));

        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(workOrderData.getBundleService().getBundleServiceName());
        selectedServiceDetailsScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen = new ServicesScreen();
        servicesScreen.clickSave();
        Helpers.waitForAlert();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.THE_VIN_IS_INVALID_AND_SAVE_WORKORDER);
        serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        homeScreen = myWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionFromInvoiceWithTwoWOs(String rowID, String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        Assert.assertEquals(vehicleScreen.getMake(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        NavigationSteps.navigateToOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();
        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectDefaultInvoiceType();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.addWorkOrder(workOrderNumber1);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), workOrderData.getWorkOrderPrice());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myWorkOrdersScreen.clickHomeButton();
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
        MyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        servicesScreen = new ServicesScreen();
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
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_PO_IS_REQUIRED_REGULAR);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myWorkOrdersScreen.clickHomeButton();
        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.myInvoiceExists(invoiceNumber);
        myInvoicesScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testDontAlowToSelectBilleAandNotBilledOrdersTogetherInMultiSelectionMode(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrderIDs = new ArrayList<>();
        final String billingFilterValue = "All";
        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
            workOrderIDs.add(vehicleScreen.getInspectionNumber());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            for (ServiceData serviceData : workOrderData.getServicesList())
                servicesScreen.selectService(serviceData.getServiceName());
            NavigationSteps.navigateToOrderSummaryScreen();
            OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
            orderSummaryScreen.clickSave();
        }

        for (String workOrderID : workOrderIDs)
            myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderID, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderIDs.get(0));
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        for (String workOrderID : workOrderIDs) {
            myWorkOrdersScreen.clickFilterButton();
            myWorkOrdersScreen.setFilterBilling(billingFilterValue);
            myWorkOrdersScreen.clickSaveFilter();
            myWorkOrdersScreen.clickActionButton();
            myWorkOrdersScreen.selectWorkOrderForAction(workOrderID);
            myWorkOrdersScreen.clickDoneButton();
        }

        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testDontAlowToChangeBilledOrders(String rowID,
                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String[] menuItemsToVerify = {"Edit", "Notes", "Change\nstatus", "Delete", "Create\nInvoices"};
        final String billingFilterValue = "All";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();

        myWorkOrdersScreen.clickFilterButton();
        myWorkOrdersScreen.setFilterBilling(billingFilterValue);
        myWorkOrdersScreen.clickSaveFilter();

        myWorkOrdersScreen.selectWorkOrder(workOrderNumber1);
        for (String menuItem : menuItemsToVerify) {
            Assert.assertFalse(myWorkOrdersScreen.isMenuItemForSelectedWOExists(menuItem), "Find menu: " + menuItem);
        }
        myWorkOrdersScreen.clickDetailspopupMenu();
        vehicleScreen = new VehicleScreen();
        vehicleScreen.clickCancelButton();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerForInvoice(String rowID,
                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber1);
        myWorkOrdersScreen.clickInvoiceIcon();
        InvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        InvoiceInfoScreen invoiceInfoScreen = new InvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveInvoiceAsDraft();

        myWorkOrdersScreen.clickHomeButton();
        BaseUtils.waitABit(60000);
        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();

        myInvoicesScreen.changeCustomerForInvoice(invoiceNumber, iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickEditPopup();
        Assert.assertEquals(invoiceInfoScreen.getInvoiceCustomer(), iOSInternalProjectConstants.SPECIFIC_CLIENT_CUSTOMER);
        invoiceInfoScreen.clickFirstWO();
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(Specific_Client);
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.cancelWizard();
        invoiceInfoScreen.cancelInvoice();
        myInvoicesScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testBugWithCrashOnCopyVehicle(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        VehicleInfoData vehicleInfoData = testCaseData.getWorkOrderData().getVehicleInfoData();
        final String vehicleinfo = vehicleInfoData.getVehicleColor() + ", " +
                vehicleInfoData.getVehicleMake() + ", " + vehicleInfoData.getVehicleModel();

        HomeScreen homeScreen = new HomeScreen();
        CarHistoryScreen carhistoryscreen = homeScreen.clickCarHistoryButton();
        carhistoryscreen.clickFirstCarHistoryInTable();
        carhistoryscreen.clickCarHistoryMyWorkOrders();
        MyWorkOrdersScreen myWorkOrdersScreen = new MyWorkOrdersScreen();
        String workOrderNumber = myWorkOrdersScreen.getFirstWorkOrderNumberValue();
        MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber, ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
        vehicleScreen.cancelWizard();
        myWorkOrdersScreen.clickBackToCarHystoryScreen();
        carhistoryscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCopyInspections(String rowID,
                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.ZAZ_MOTORS_CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.FOR_COPY_INSP_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        final String inspNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
            visualInteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
            visualInteriorScreen.tapInterior();
            if (visualScreenData.getScreenTotalPrice() != null)
                Assert.assertEquals(visualInteriorScreen.getTotalAmaunt(), visualScreenData.getScreenTotalPrice());
        }
        for (QuestionScreenData questionScreenData : inspectionData.getQuestionScreensData())
            QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getMoneyServiceData().getServiceName()));
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), inspectionData.getMoneyServiceData().getServicePrice());
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), inspectionData.getMoneyServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getMoneyServiceData().getServiceName()));
        // =====================================
        servicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getPercentageServiceData().getServiceName()));
        servicesScreen.openServiceDetails(inspectionData.getPercentageServiceData().getServiceName());
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), inspectionData.getPercentageServiceData().getServicePrice());
        selectedServiceDetailsScreen.clickAdjustments();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentValue(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName()),
                inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentQuantity());
        selectedServiceDetailsScreen.selectAdjustment(inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentData().getAdjustmentName());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), inspectionData.getPercentageServiceData().getServiceAdjustment().getAdjustmentTotalAmaunt());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getPercentageServiceData().getServiceName()));
        // =====================================
        ServicesScreenSteps.selectBundleService(inspectionData.getBundleService());
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(inspectionData.getBundleService().getBundleServiceName()));
        // =====================================
        for (ServiceData serviceData : inspectionData.getPercentageServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        // =====================================
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        PriceMatrixScreen priceMatrixScreen = servicesScreen.selectServicePriceMatrices(matrixServiceData.getHailMatrixName());
        priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
        priceMatrixScreen.setSizeAndSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSize(), matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        Assert.assertEquals(priceMatrixScreen.getPrice(), matrixServiceData.getVehiclePartData().getVehiclePartPrice());
        Assert.assertTrue(priceMatrixScreen.isNotesExists());
        for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
            priceMatrixScreen.selectDiscaunt(serviceData.getServiceName());
        }
        priceMatrixScreen.clickSaveButton();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(matrixServiceData.getMatrixServiceName()));
        servicesScreen.saveWizard();
        myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.selectInspectionForCopy(inspNumber);
        vehicleScreen = new VehicleScreen();
        final String copiedInspection = vehicleScreen.getInspectionNumber();
        Assert.assertEquals(vehicleScreen.getMake(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleScreen.getModel(), inspectionData.getVehicleInfo().getVehicleModel());

        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
            visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
        }

        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreenSteps.verifyServicesAreSelected(inspectionData.getSelectedServices());

        NavigationSteps.navigateToScreen(ScreenNamesConstants.FOLLOW_UP_REQUESTED);
        QuestionsScreen questionScreen = new QuestionsScreen();
        questionScreen.waitQuestionsScreenLoaded();
        SinglePageInspectionScreen singlePageInspectionScreen = new SinglePageInspectionScreen();
        Assert.assertTrue(singlePageInspectionScreen.isSignaturePresent());
        singlePageInspectionScreen.selectNextScreen("BATTERY PERFORMANCE");
        vehicleScreen.swipeScreenRight();
        Assert.assertTrue(singlePageInspectionScreen.isAnswerPresent("Test Answer 1"));
        servicesScreen.clickSave();

        myInspectionsScreen = new MyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(copiedInspection), inspectionData.getInspectionPrice());
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionFromWO(String rowID,
                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.saveWizard();

        //Test case
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber1), testCaseData.getWorkOrderData().getWorkOrderPrice());
        myWorkOrdersScreen.selectWorkOrderNewInspection(workOrderNumber1);
        vehicleScreen = new VehicleScreen();
        vehicleScreen.verifyMakeModelyearValues(testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleMake(),
                testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleModel(), testCaseData.getWorkOrderData().getVehicleInfoData().getVehicleYear());
        vehicleScreen.cancelOrder();
        myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.clickHomeButton();

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
        MyWorkOrdersSteps.startCreatingWorkOrder(ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        String workOrderNumber1 = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();
        myWorkOrdersScreen.approveWorkOrderWithoutSignature(workOrderNumber1, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber1), workOrderData.getWorkOrderPrice());
        MyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber1, ZAZ_Motors, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
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
        myWorkOrdersScreen.clickHomeButton();
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
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoicenum);
        invoicesWebPage.clickFindButton();
        Assert.assertTrue(invoicesWebPage.isInvoiceDisplayed(invoicenum));
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForInspection(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreeneen = homeScreen.clickLogoutButton();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSP_CHANGE_INSPTYPE);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        vehicleScreen.saveWizard();
        myInspectionsScreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
        BaseUtils.waitABit(90 * 1000);
        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
        vehicleScreen.saveWizard();

        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForInspectionAsChangeWholesaleToRetailAndViceVersa(String rowID,
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
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSP_CHANGE_INSPTYPE);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        vehicleScreen.saveWizard();

        myInspectionsScreen.selectInspectionInTable(inspectionnumber);
        myInspectionsScreen.clickChangeCustomerpopupMenu();
        myInspectionsScreen.customersPopupSwitchToRetailMode();
        myInspectionsScreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);

        myInspectionsScreen.clickHomeButton();
        customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();
        BaseUtils.waitABit(60 * 1000);
        myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(johnRetailCustomer);
        vehicleScreen.saveWizard();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies(String rowID,
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
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimScreen.saveWizard();
        myInspectionsScreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
        BaseUtils.waitABit(30 * 1000);
        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
        vehicleScreen.saveWizard();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForWorkOrder(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        String workOrderNumber = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.changeCustomerForWorkOrder(workOrderNumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
        myWorkOrdersScreen.openWorkOrderDetails(workOrderNumber);
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
        servicesScreen.clickCancelButton();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForWOBasedOnTypeWithPreselectedCompanies(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_WITH_PRESELECTED_CLIENTS);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        String workOrderNumber = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.saveWizard();

        BaseUtils.waitABit(45000);
        myWorkOrdersScreen.changeCustomerForWorkOrder(workOrderNumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
        myWorkOrdersScreen.openWorkOrderDetails(workOrderNumber);
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
        servicesScreen.clickCancelButton();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForWOAsChangeWholesaleToRetailAndViceVersa(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        String workOrderNumber = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.selectWorkOrder(workOrderNumber);
        myWorkOrdersScreen.clickChangeCustomerPopupMenu();
        myWorkOrdersScreen.customersPopupSwitchToRetailMode();
        myWorkOrdersScreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);
        myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.clickHomeButton();
        customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        myWorkOrdersScreen.openWorkOrderDetails(workOrderNumber);
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(johnRetailCustomer);
        servicesScreen.clickCancelButton();
        myWorkOrdersScreen.clickHomeButton();
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

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();
        orderSummaryScreen.waitForCustomWarningMessage(String.format(AlertsCaptions.ALERT_YOU_CANT_CREATE_WORK_ORDER_BECAUSE_VIN_EXISTS,
                WorkOrdersTypes.WOTYPE_BLOCK_VIN_ON.getWorkOrderTypeName(), workOrderData.getVehicleInfoData().getVINNumber()), "Cancel");

        myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testBlockForTheSameServicesIsONVerifyDuplicateServicesMessageWhenCreate2WOWithOneService(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();
        orderSummaryScreen.waitForCustomWarningMessage("Duplicate services", "Cancel");
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditOptionOfDuplicateServicesMessageForWO(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        servicesScreen.selectService(workOrderData.getServiceData().getServiceName());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();
        orderSummaryScreen.waitForCustomWarningMessage("Duplicate services", "Edit");
        orderSummaryScreen.waitOrderSummaryScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.removeSelectedServices(workOrderData.getServiceData().getServiceName());
        Assert.assertFalse(servicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testOverrideOptionOfDuplicateServicesMessageForWO(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        String workOrderNumber = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();
        orderSummaryScreen.waitForCustomWarningMessage("Duplicate services", "Override");
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen = new MyWorkOrdersScreen();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));

        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCancelOptionOfDuplicateServicesMessageForWO(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(_002_Test_Customer, WorkOrdersTypes.WOTYPE_BLOCK_FOR_THE_SAME_SERVICES_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        String workOrderNumber = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();
        orderSummaryScreen.waitForCustomWarningMessage("Duplicate services", "Cancel");
        myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
        Assert.assertFalse(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSavingInspectionsWithThreeMatrix(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.VITALY_TEST_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setType(inspectionData.getVehicleInfo().getVehicleType());
        vehicleScreen.setPO(inspectionData.getVehicleInfo().getPoNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimScreen.setClaim(inspectionData.getInsuranceCompanyData().getClaimNumber());
        claimScreen.setAccidentDate();

        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            NavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
            visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
            if (visualScreenData.getDamagesData() != null) {
                visualInteriorScreen.selectService(visualScreenData.getDamagesData().get(0).getDamageGroupName());
                visualInteriorScreen.tapInteriorWithCoords(1);
                visualInteriorScreen.tapInteriorWithCoords(2);
            } else {
                visualInteriorScreen.selectService(visualScreenData.getDamageData().getDamageGroupName());
                VisualInteriorScreen.tapExteriorWithCoords(100, 500);
            }
            Assert.assertEquals(visualInteriorScreen.getTotalAmaunt(), visualScreenData.getScreenTotalPrice());
            Assert.assertEquals(visualInteriorScreen.getSubTotalAmaunt(), visualScreenData.getScreenPrice());
        }
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);
        }

        NavigationSteps.navigateToScreen(ScreenNamesConstants.ALL_SERVICES);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        servicesScreen.selectService(inspectionData.getServiceData().getServiceName());

        NavigationSteps.navigateToScreen(inspectionData.getVisualScreenData().getScreenName());
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.selectService(inspectionData.getVisualScreenData().getDamageData().getDamageGroupName());
        visualInteriorScreen.tapCarImage();
        Assert.assertEquals(visualInteriorScreen.getTotalAmaunt(), inspectionData.getVisualScreenData().getScreenTotalPrice());
        Assert.assertEquals(visualInteriorScreen.getSubTotalAmaunt(), inspectionData.getVisualScreenData().getScreenPrice());
        visualInteriorScreen.saveWizard();
        myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
        vehicleScreen.waitVehicleScreenLoaded();

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
            for (VehiclePartData vehiclePartData : priceMatrixScreenData.getVehiclePartsData()) {
                PriceMatrixScreenSteps.verifyIfVehiclePartContainsPriceValue(vehiclePartData);
            }
        }

        servicesScreen.cancelWizard();
        myInspectionsScreen.selectInspectionForCopy(inspectionNumber);
        vehicleScreen = new VehicleScreen();
        String copiedinspectionNumber = vehicleScreen.getInspectionNumber();
        servicesScreen.saveWizard();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(copiedinspectionNumber));
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testThatSelectedServicesOnSRAreCopiedToInspectionBasedOnSR(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        HomeScreen homeScreen = new HomeScreen();

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(_003_Test_Customer,
                ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            if (serviceData.getServiceQuantity() != null) {
                SelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
                servicedetailsscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
                servicedetailsscreen.saveSelectedServiceDetails();
            }
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());

        vehicleScreen.clickSave();
        Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(newserviceRequestNumber, InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
        vehicleScreen.waitVehicleScreenLoaded();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            servicesScreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2());
        servicesScreen.saveWizard();
        serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        TeamInspectionsScreen teamInspectionsScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumber));
        teamInspectionsScreen.clickBackServiceRequest();
        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testThatAutoSavedWOIsCreatedCorrectly(String rowID,
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
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_CLIENT_CHANGING_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        Helpers.waitABit(40 * 1000);
        DriverBuilder.getInstance().getAppiumDriver().closeApp();
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
        MainScreen mainScreeneen = new MainScreen();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        Assert.assertTrue(myWorkOrdersScreen.isAutosavedWorkOrderExists());
        myWorkOrdersScreen.selectContinueWorkOrder();
        BaseUtils.waitABit(30 * 1000);
        Assert.assertEquals(vehicleScreen.getInspectionNumber(), workOrderNumber);

        DriverBuilder.getInstance().getAppiumDriver().closeApp();
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
        mainScreeneen = new MainScreen();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        Assert.assertTrue(myWorkOrdersScreen.isAutosavedWorkOrderExists());
        myWorkOrdersScreen.selectDiscardWorkOrder();
        Assert.assertFalse(myWorkOrdersScreen.isAutosavedWorkOrderExists());
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRAddAppointmentToServiceRequest(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        final String appointmentSubject = "SR-APP";
        final String appointmentAddress = "Maidan";
        final String appointmentCity = "Kiev";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();
        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer,
                ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVINFieldValue(serviceRequestData.getVihicleInfo().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.drawSignature();
        servicesScreen.clickSave();
        alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);

        QuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();
        final String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectAppointmentRequestAction();
        serviceRequestsScreen.clickAddAppointmentButton();
        serviceRequestsScreen.selectTodayFromAppointmet();
        serviceRequestsScreen.selectTodayToAppointmet();

        serviceRequestsScreen.setSubjectAppointmet(appointmentSubject);
        serviceRequestsScreen.setAddressAppointmet(appointmentAddress);
        serviceRequestsScreen.setCityAppointmet(appointmentCity);
        serviceRequestsScreen.saveAppointment();
        serviceRequestsScreen.selectCloseAction();
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(newserviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
        serviceRequestsScreen.selectRejectAction();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifySummaryActionForAppointmentOnSRsCalendar(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String appointmentSubject = "SR-APP";
        final String appointmentAddress = "Maidan";
        final String appointmentCity = "Kiev";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();

        ServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer,
                ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVINFieldValue(serviceRequestData.getVihicleInfo().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.drawSignature();
        servicesScreen.clickSave();
        alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);

        QuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        final String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);

        serviceRequestsScreen.selectAppointmentRequestAction();
        serviceRequestsScreen.clickAddAppointmentButton();
        serviceRequestsScreen.selectTodayFromAppointmet();
        serviceRequestsScreen.selectTodayToAppointmet();

        serviceRequestsScreen.setSubjectAppointmet(appointmentSubject);
        serviceRequestsScreen.setAddressAppointmet(appointmentAddress);
        serviceRequestsScreen.setCityAppointmet(appointmentCity);
        serviceRequestsScreen.saveAppointment();
        serviceRequestsScreen.selectCloseAction();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        Assert.assertTrue(serviceRequestsScreen.isSRSummaryAppointmentsInformation());

        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectRejectAction();
        serviceRequestsScreen.clickHomeButton();
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

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
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
        myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatOnlyAssignedServicesOnMatrixPanelIsAvailableAsAdditionalServices(String rowID,
                                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        VehicleInfoScreenSteps.verifyMakeModelyearValues(inspectionData.getVehicleInfo());
        String inspnum = vehicleScreen.getInspectionNumber();
        PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        vehicleScreen.saveWizard();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspnum));
        myInspectionsScreen.clickHomeButton();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatItIsNotPossibleToChangeServiceStatusBeforeStartService(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.verifyMakeModelyearValues(workOrderData.getVehicleInfoData());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        myWorkOrdersScreen.clickHomeButton();

        HomeScreenSteps.navigateToTeamWorkOrdersScreen();
        TeamWorkOrdersScreen teamWorkordersScreen = new TeamWorkOrdersScreen();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
        serviceDetailsPopup.clickServiceStatusCell();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_MUST_SERVICE_PHASE_BEFORE_CHANGING_STATUS);
        serviceDetailsPopup.clickStartService();
        serviceDetailsPopup = orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        serviceDetailsPopup.setCompletedServiceStatus();
        List<String> statuses = orderMonitorScreen.getPanelsStatuses(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        for (String status : statuses)
            Assert.assertEquals(status, orderMonitorData.getMonitorServiceData().getMonitorServiceStatus());
        teamWorkordersScreen = orderMonitorScreen.clickBackButton();
        teamWorkordersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatItIsNotPossibleToChangePhaseStatusBeforeStartPhase(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String dyeService = "Dye";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        myWorkOrdersScreen.clickHomeButton();

        HomeScreenSteps.navigateToTeamWorkOrdersScreen();
        TeamWorkOrdersScreen teamWorkordersScreen = new TeamWorkOrdersScreen();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        Assert.assertTrue(orderMonitorScreen.isRepairPhaseExists());
        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clicksRepairPhaseLine();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_MUST_START_PHASE_BEFORE_CHANGING_STATUS);
        orderMonitorScreen.clickStartPhase();


        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(dyeService);
        Assert.assertFalse(serviceDetailsPopup.isStartPhaseButtonPresent());
        Assert.assertTrue(serviceDetailsPopup.isServiceStartDateExists());

        serviceDetailsPopup.clickServiceStatusCell();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        orderMonitorScreen.clicksRepairPhaseLine();
        orderMonitorScreen.setCompletedPhaseStatus();
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData())
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(monitorServiceData.getMonitorService().getServiceName()), monitorServiceData.getMonitorServiceStatus());
        teamWorkordersScreen = orderMonitorScreen.clickBackButton();
        teamWorkordersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatStartDateIsSetWhenStartService(String rowID,
                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();

        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertFalse(serviceDetailsPopup.isServiceStartDateExists());
        serviceDetailsPopup.clickStartService();
        orderMonitorScreen.selectPanel(orderMonitorData.getMonitorServiceData().getMonitorService().getServiceName());
        Assert.assertTrue(serviceDetailsPopup.isServiceStartDateExists());
        serviceDetailsPopup.clickServiceDetailsDoneButton();
        teamWorkordersScreen = orderMonitorScreen.clickBackButton();
        teamWorkordersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOMonitorVerifyThatWhenChangeStatusForPhaseWithDoNotTrackIndividualServiceStatusesONPhaseStatusIsSetToAllServicesAssignedToPhase(String rowID,
                                                                                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getMatrixServiceData().getMatrixServiceName()));

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));

        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkordersScreen.clickSearchSaveButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorsData().get(0).getMonitorServicesData()) {
            OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService().getServiceName());
            Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
            serviceDetailsPopup.clickStartService();
            orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
            serviceDetailsPopup.setCompletedServiceStatus();
            OrderMonitorScreenValidations.verifyServicesStatus(monitorServiceData.getMonitorService(), OrderMonitorStatuses.COMPLETED);
        }

        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clickStartPhase();
        OrderMonitorData repairPhaseOrderMonitorData = workOrderData.getOrderMonitorsData().get(1);
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(repairPhaseOrderMonitorData.getMonitorServicesData().get(0).getMonitorService());
        serviceDetailsPopup.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        orderMonitorScreen.clicksRepairPhaseLine();
        orderMonitorScreen.setCompletedPhaseStatus();

        for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData())
            OrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorStatuses.COMPLETED);

        for (MonitorServiceData monitorServiceData : repairPhaseOrderMonitorData.getMonitorServicesData()) {
            orderMonitorScreen.selectPanel(monitorServiceData.getMonitorService());
            Assert.assertEquals(orderMonitorScreen.getPanelStatusInPopup(), OrderMonitorStatuses.COMPLETED.getValue());
            orderMonitorScreen.clickCancelServiceDetails();
        }
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyThatSRIsCreatedCorrectlyWhenSelectOwnerOnVehicleInfo(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String owner = "Avalon";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.selectOwnerT(owner);
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());

        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestClient(serviceRequestNumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
        Assert.assertTrue(serviceRequestsScreen.getServiceRequestVehicleInfo(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyThatCheckInActionIsPresentForSRWhenAppropriateSRTypeHasOptionCheckInON(String rowID,
                                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String owner = "Avalon";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.selectOwnerT(owner);
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());

        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestClient(serviceRequestNumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
        Assert.assertTrue(serviceRequestsScreen.getServiceRequestVehicleInfo(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectCheckInMenu();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyThatCheckInActionIsChangedToUndoCheckInAfterPressingOnItAndViceVersa(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String owner = "Avalon";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.selectOwnerT(owner);
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestClient(serviceRequestNumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
        Assert.assertTrue(serviceRequestsScreen.getServiceRequestVehicleInfo(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectCheckInMenu();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectUndoCheckMenu();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectCheckInMenu();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyThatFilterNotCheckedInIsWorkingCorrectly(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String owner = "Avalon";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.selectOwnerT(owner);
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());

        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestClient(serviceRequestNumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
        Assert.assertTrue(serviceRequestsScreen.getServiceRequestVehicleInfo(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        serviceRequestsScreen.applyNotCheckedInFilter();
        Assert.assertEquals(serviceRequestsScreen.getFirstServiceRequestNumber(), serviceRequestNumber);

        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectCheckInMenu();
        Assert.assertFalse(serviceRequestsScreen.isServiceRequestExists(serviceRequestNumber));
        serviceRequestsScreen.resetFilter();
        Assert.assertTrue(serviceRequestsScreen.isServiceRequestExists(serviceRequestNumber));
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCustomerApprovalRequiredIsSetToONAutoEmailIsNotSentWhenApprovalDoesNotExist(String rowID,
                                                                                                              String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
        myWorkOrdersScreen.clickHomeButton();
        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        Assert.assertTrue(myInvoicesScreen.myInvoiceExists(invoiceNumber));
        myInvoicesScreen.clickInvoiceApproveButton(invoiceNumber);

        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.drawApprovalSignature();
        approveInspectionsScreen.clickApproveButton();
        myInvoicesScreen.clickHomeButton();

        final String invpoicereportfilenname = invoiceNumber + ".pdf";

        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(ReconProIOSStageInfo.getInstance().getTestMail());

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invpoicereportfilenname);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfdoc = new File(invpoicereportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(workOrderData.getVehicleInfoData().getVINNumber()));
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCustomerApprovalRequiredIsSetToOffAutoEmailIsSentWhenInvoiceAsAutoApproved(String rowID,
                                                                                                             String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            servicesScreen.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
        myWorkOrdersScreen.clickHomeButton();
        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        Assert.assertTrue(myInvoicesScreen.myInvoiceExists(invoiceNumber));
        myInvoicesScreen.clickHomeButton();


        final String invpoicereportfilenname = invoiceNumber + ".pdf";
        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(ReconProIOSStageInfo.getInstance().getTestMail());
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invpoicereportfilenname);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfdoc = new File(invpoicereportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(workOrderData.getVehicleInfoData().getVINNumber()));
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedMyInvoices(String rowID,
                                                                                      String description, JSONObject testData) {


        final String printServerName = "TA_Print_Server";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        final String invoicenum = myInvoicesScreen.getFirstInvoiceValue();
        myInvoicesScreen.printInvoice(invoicenum, printServerName);
        myInvoicesScreen.clickHomeButton();
        Helpers.waitABit(20000);
        homeScreen.clickMyInvoices();
        Assert.assertTrue(myInvoicesScreen.isInvoicePrintButtonExists(invoicenum));
        myInvoicesScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPrintIconIsShownNextToInvoiceWhenItWasPrintedTeamInvoices(String rowID,
                                                                                        String description, JSONObject testData) {

        final String printServerName = "TA_Print_Server";
        HomeScreen homeScreen = new HomeScreen();
        TeamInvoicesScreen teaminvoicesscreen = homeScreen.clickTeamInvoices();
        final String invoicenum = teaminvoicesscreen.getFirstInvoiceValue();
        teaminvoicesscreen.printInvoice(invoicenum, printServerName);
        teaminvoicesscreen.clickHomeButton();
        Helpers.waitABit(20000);
        teaminvoicesscreen = homeScreen.clickTeamInvoices();
        Assert.assertTrue(teaminvoicesscreen.isInvoicePrintButtonExists(invoicenum));
        teaminvoicesscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRedAIconIsPresentForInvoiceWithCustomerApprovalONAndNoSignature(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALON_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
        myWorkOrdersScreen.clickHomeButton();
        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.myInvoiceExists(invoiceNumber);
        Assert.assertTrue(myInvoicesScreen.isInvoiceApproveRedButtonExists(invoiceNumber));
        myInvoicesScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatGreyAIconIsPresentForInvoiceWithCustomerApprovalOFFAndNoSignature(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.CUSTOMER_APPROVALOFF_INVOICETYPE);
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
        myWorkOrdersScreen.clickHomeButton();
        MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
        myInvoicesScreen.myInvoiceExists(invoiceNumber);
        Assert.assertTrue(myInvoicesScreen.isInvoiceApproveGreyButtonExists(invoiceNumber));
        myInvoicesScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAIconIsNotPresentForInvoiceWhenSignatureExists(String rowID,
                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();

            MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_INV_PRINT);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
            NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
            for (ServiceData serviceData : workOrderData.getServicesList())
                ServicesScreenSteps.selectService(serviceData.getServiceName());
            NavigationSteps.navigateToOrderSummaryScreen();
            OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
            orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
            orderSummaryScreen.checkApproveAndCreateInvoice();
            SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
            selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
            orderSummaryScreen.clickSave();

            InvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.valueOf(workOrderData.getInvoiceData().getInvoiceType()));
            invoiceInfoScreen.setPO(workOrderData.getInvoiceData().getPoNumber());
            final String invoiceNumberapproveon = invoiceInfoScreen.getInvoiceNumber();
            invoiceInfoScreen.clickSaveAsFinal();
            myWorkOrdersScreen.waitWorkOrdersScreenLoaded();
            myWorkOrdersScreen.clickHomeButton();
            MyInvoicesScreen myInvoicesScreen = homeScreen.clickMyInvoices();
            myInvoicesScreen.myInvoiceExists(invoiceNumberapproveon);
            Assert.assertTrue(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumberapproveon));
            SummaryScreen summaryScreen = myInvoicesScreen.clickSummaryPopup();
            summaryScreen.clickBackButton();
            myInvoicesScreen.selectInvoiceForApprove(invoiceNumberapproveon);

            selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
            ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
            approveInspectionsScreen.drawApprovalSignature();
            approveInspectionsScreen.clickApproveButton();
            myInvoicesScreen = new MyInvoicesScreen();

            Assert.assertFalse(myInvoicesScreen.isInvoiceApproveButtonExists(invoiceNumberapproveon));
            myInvoicesScreen.clickVoidInvoiceMenu();
            Helpers.getAlertTextAndAccept();
            myInvoicesScreen.clickHomeButton();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech(String rowID,
                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        //Create first SR
        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);

        serviceRequestsScreen = new ServiceRequestsScreen();
        String serviceRequestNumber1 = serviceRequestsScreen.getFirstServiceRequestNumber();
        serviceRequestsScreen.rejectServiceRequest(serviceRequestNumber1);
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE);
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        servicesScreen.saveWizard();
        String serviceRequestNumber2 = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber2), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber2);
        serviceRequestsScreen.selectRejectAction();
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsDisplayedForSRInStatusOnHoldInspOrWOAndAssignForTech(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());

        serviceRequestsScreen.rejectServiceRequest(serviceRequestNumber);
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_DRAFT_MODE);
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.clickSaveAsFinal();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
        TeamInspectionsScreen teamInspectionsScreen = new TeamInspectionsScreen();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumber));
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.selectInspectionForAction(inspectionNumber);

        teamInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        //Helpers.acceptAlert();
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.approveInspectionWithSelectionAndSignature(inspectionNumber);
        teamInspectionsScreen.clickBackServiceRequest();
        serviceRequestdetailsScreen.clickBackButton();

        serviceRequestsScreen.clickHomeButton();
        boolean onhold = false;
        for (int i = 0; i < 7; i++) {
            serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
            if (!serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber).equals(ServiceRequestStatus.ON_HOLD.getValue())) {
                serviceRequestsScreen.clickHomeButton();
                BaseUtils.waitABit(30 * 1000);
            } else {

                onhold = true;
                break;
            }
        }
        Assert.assertTrue(onhold);
        serviceRequestsScreen.rejectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusOnHoldInspOrWOAndNotAssignForTech(String rowID,
                                                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_INSP_ONLY.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.saveNewServiceRequest();
        String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        DriverBuilder.getInstance().getDriver().quit();

        DriverBuilder.getInstance().getAppiumDriver().closeApp();
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
        MainScreen mainScreen = new MainScreen();
        mainScreen.updateDatabase();
        HomeScreen homeScreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestsScreen.isRejectActionExists());
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusScheduledInspOrWOAndNotAssignForTech(String rowID,
                                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();
        serviceRequestsListInteractions.addServicesToServiceRequest(serviceRequestData.getPercentageServices());
        serviceRequestsListInteractions.saveNewServiceRequest();
        String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

        DriverBuilder.getInstance().getDriver().quit();

        MainScreen mainScreen = new HomeScreen().clickLogoutButton();
        mainScreen.updateDatabase();
        HomeScreen homeScreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();

        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestsScreen.isRejectActionExists());
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.clickHomeButton();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        backofficeHeader.clickOperationsLink();
        operationsWebPage = new OperationsWebPage(webdriver);
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();
        serviceRequestsListInteractions.addServicesToServiceRequest(serviceRequestData.getMoneyServices());
        serviceRequestsListInteractions.saveNewServiceRequest();
        serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

        DriverBuilder.getInstance().getDriver().quit();
        mainScreen = new HomeScreen().clickLogoutButton();
        mainScreen.updateDatabase();
        homeScreen = mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestsScreen.isRejectActionExists());
        serviceRequestsScreen.selectEditServiceRequestAction();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
        vehicleScreen.saveWizard();
        serviceRequestsScreen.clickHomeButton();
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
            Assert.assertEquals(Helpers.getAlertTextAndAccept(), String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
            Assert.assertEquals(servicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), 1);
        }
        servicesScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();
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
        servicesScreen = new ServicesScreen();
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
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
                                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
                                                                                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectRejectAction();
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);

        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown(String rowID,
                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        serviceRequestsScreen.clickCancelCloseReasonDialog();
        serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsShownInCaseItIsAssignedToReasonOnBO(String rowID,
                                                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        final String answerReason = "All work is done. Answer questions";
        final String answerQuestion = "A3";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isServiceRequestExists(serviceRequestNumber));
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        serviceRequestsScreen.selectUIAPickerValue(answerReason);
        serviceRequestsScreen.clickDoneButton();
        QuestionsPopup questionspopup = new QuestionsPopup();
        questionspopup.answerQuestion2(answerQuestion);
        serviceRequestsScreen.clickCloseSR();
        Assert.assertFalse(serviceRequestsScreen.isServiceRequestExists(serviceRequestNumber));
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenStatusReasonIsSelectedQuestionSectionIsNotShownInCaseItIsNotAssignedToReasonOnBO(String rowID,
                                                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String answerReason = "All work is done. No Questions";

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isServiceRequestExists(serviceRequestNumber));
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        serviceRequestsScreen.selectDoneReason(answerReason);
        Assert.assertFalse(serviceRequestsScreen.isServiceRequestExists(serviceRequestNumber));
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String totalSale = "3";

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_WO_ONLY);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen = new ServiceRequestsScreen();
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingWorkOrderFromServiceRequest(serviceRequestNumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        VehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(totalSale);
        String workOrderNumber = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();

        for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
            alertText = Helpers.getAlertTextAndAccept();
            String servicedetails = alertText.substring(alertText.indexOf("'") + 1, alertText.indexOf("' require"));
            for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
                if (serviceData.getServiceName().equals(servicedetails)) {
                    SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(servicedetails);
                    selectedServiceDetailsScreen.clickVehiclePartsCell();
                    selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
                    selectedServiceDetailsScreen.saveSelectedServiceDetails();
                    selectedServiceDetailsScreen.saveSelectedServiceDetails();
                }
            }
            orderSummaryScreen.clickSave();
        }
        serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        TeamWorkOrdersScreen teamWorkOrdersScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();
        teamWorkOrdersScreen.woExists(workOrderNumber);
        teamWorkOrdersScreen.clickServiceRequestButton();
        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
                                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_INSP_ONLY);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        servicesScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_FOR_CALC);
        VehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        servicesScreen.clickSave();

        for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
            alertText = Helpers.getAlertTextAndAccept();
            String servicedetails = alertText.substring(alertText.indexOf("'") + 1, alertText.indexOf("' require"));
            for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
                if (serviceData.getServiceName().equals(servicedetails)) {
                    SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(servicedetails);
                    selectedServiceDetailsScreen.clickVehiclePartsCell();
                    selectedServiceDetailsScreen.selectVehiclePart(serviceData.getVehiclePart().getVehiclePartName());
                    selectedServiceDetailsScreen.saveSelectedServiceDetails();
                    selectedServiceDetailsScreen.saveSelectedServiceDetails();
                }
            }
            servicesScreen.clickSave();
        }
        serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
        TeamInspectionsScreen teamInspectionsScreen = new TeamInspectionsScreen();
        teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumber));
        teamInspectionsScreen.clickBackServiceRequest();
        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown(String rowID,
                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();
        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData())
            PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        vehicleScreen.saveWizard();
        myInspectionsScreen.selectInspectionForAction(inspectionnumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionnumber);
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList()) {
            Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData.getServiceName()));
            Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(serviceData.getServiceName()), serviceData.getServicePrice());
        }

        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();
        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        vehicleScreen.waitVehicleScreenLoaded();
        PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreensData().get(0);
        NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
        Assert.assertEquals(priceMatrixScreen.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
        InspectionToolBar inspectionToolBar = new InspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), inspectionData.getInspectionPrice());
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionTotalPrice());
        vehicleScreen.saveWizard();
        myInspectionsScreen.selectInspectionForAction(inspectionnumber);
        selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionnumber);
        Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServicesToApprovesList().get(0).getServiceName()), inspectionData.getServicesToApprovesList().get(0).getServicePrice());
        Assert.assertFalse(approveInspectionsScreen.isInspectionServiceExistsForApprove(inspectionData.getServicesToApprovesList().get(1).getServiceName()));
        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();
        ;
        myInspectionsScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired(String rowID,
                                                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        ;

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.slectServiceVehicleParts(inspectionData.getServiceData().getVehicleParts());
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        final String selectedhehicleparts = selectedServiceDetailsScreen.getListOfSelectedVehicleParts();
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts())
            Assert.assertTrue(selectedhehicleparts.contains(vehiclePartData.getVehiclePartName()));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        int i = 0;
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
            servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            i++;
            Assert.assertFalse(selectedServiceDetailsScreen.isQuestionFormCellExists());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.cancelWizard();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatKeyboardIsNotShownOverTheVINWhenItIsEnteredInCaseOnlyVINIsPresentOnVehicleScreen(String rowID,
                                                                                                               String description, JSONObject testData) {
        final String newLineSymbol = "\n";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPECTION_VIN_ONLY);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.getVINField().click();
        Assert.assertTrue(vehicleScreen.getVINField().isDisplayed());
        vehicleScreen.getVINField().click();
        vehicleScreen.getVINField().sendKeys(newLineSymbol);
        vehicleScreen.cancelOrder();
        myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenEditInspectionSelectedVehiclePartsForServicesArePresent(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
        servicesScreen.saveWizard();

        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        int i = 0;
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
            servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            i++;
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
            Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(), vehiclePartData.getVehiclePartName());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.cancelWizard();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsPossibleToSaveAsFinalInspectionLinkedToSR(String rowID,
                                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        String alertText = Helpers.getAlertTextAndCancel();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_CREATE_APPOINTMENT);

        serviceRequestsScreen = new ServiceRequestsScreen();
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_DRAFT_MODE);
        InspectionData inspectionData = testCaseData.getInspectionData();

        String inspectionnumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
        servicesScreen.clickSaveAsDraft();
        serviceRequestsScreen = new ServiceRequestsScreen();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        TeamInspectionsScreen teamInspectionsScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
        teamInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        InspectionsSteps.saveInspectionAsFinal();
        Assert.assertTrue(teamInspectionsScreen.isInspectionApproveButtonExists(inspectionnumber));
        teamInspectionsScreen.clickBackServiceRequest();
        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenFinalInspectionIsCopiedServisesAreCopiedWithoutStatuses_Approved_Declined_Skipped(String rowID,
                                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
        SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
        servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
        servicedetailsscreen.saveSelectedServiceDetails();

        ServicesScreenSteps.selectMatrixServiceDataAndSave(inspectionData.getMatrixServiceData());

        servicesScreen.clickSaveAsFinal();
        myInspectionsScreen = new MyInspectionsScreen();
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);

        myInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
            if (serviceData.isSelected())
                Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData), "Can't find service:" + serviceData.getServiceName());
            else
                Assert.assertFalse(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData));
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitInspectionsScreenLoaded();
        myInspectionsScreen.selectInspectionForCopy(inspectionNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.TEST_PACK_FOR_CALC);
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
            if (serviceData.isSelected())
                Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        InspectionsSteps.saveInspectionAsFinal();
        myInspectionsScreen.waitInspectionsScreenLoaded();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction(String rowID,
                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String notesText = "Test for copy";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.DEFAULT);
        VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        WizardScreensSteps.clickNotesButton();
        NotesScreen notesScreen = new NotesScreen();
        notesScreen.setNotes(notesText);
        notesScreen.clickSaveButton();
        InspectionsSteps.saveInspectionAsFinal();
        myInspectionsScreen.selectInspectionForCopy(inspectionNumber);

        String copiedInspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        InspectionsSteps.saveInspectionAsFinal();

        Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(copiedInspectionNumber));
        notesScreen = myInspectionsScreen.openInspectionNotesScreen(copiedInspectionNumber);
        Assert.assertEquals(notesScreen.getAddedNotesText(), notesText);
        notesScreen.clickSaveButton();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inpectionsIDs = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            inpectionsIDs.add(vehicleScreen.getInspectionNumber());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
            SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
            servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
            servicedetailsscreen.saveSelectedServiceDetails();
            servicesScreen.waitServicesScreenLoaded();
            servicesScreen.clickSaveAsFinal();
        }
        myInspectionsScreen.clickHomeButton();
        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inpectionsIDs) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }

        teamInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        for (String inspectionID : inpectionsIDs) {
            approveInspectionsScreen.selectInspectionForApprove(inspectionID);
            approveInspectionsScreen.clickApproveAllServicesButton();
            approveInspectionsScreen.clickSaveButton();
        }
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inpectionsIDs) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        teamInspectionsScreen.clickActionButton();
        Assert.assertFalse(teamInspectionsScreen.isApproveInspectionMenuActionExists());
        Assert.assertTrue(teamInspectionsScreen.isSendEmailInspectionMenuActionExists());
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatServicesOnServicePackageAreGroupedByTypeSelectedOnInspTypeWizard(String rowID,
                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPECTION_GROUP_SERVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreensData().get(0));

        NavigationSteps.navigateToScreen(ScreenNamesConstants.ZAYATS_TEST_PACK);
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectService(inspectionData.getServiceData().getServiceName());
        for (ServicesScreenData servicesScreenData : inspectionData.getServicesScreens()) {
            NavigationSteps.navigateToScreen(servicesScreenData.getScreenName());
            servicesScreen.selectGroupServiceItem(servicesScreenData.getDamageData().getDamageGroupName());
            ServicesScreenSteps.selectServiceWithServiceData(servicesScreenData.getDamageData().getMoneyService());
        }
        servicesScreen.clickSave();
        servicesScreen.clickFinalPopup();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.drawSignature();
        questionsScreen.clickSave();
        questionsScreen.clickFinalPopup();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
        QuestionsScreenSteps.answerQuestion(inspectionData.getQuestionScreensData().get(1).getQuestionData());
        questionsScreen.clickSave();
        questionsScreen.clickFinalPopup();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspectionNumber));
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAllInstancesOfOneServiceAreCopiedFromInspectionToWO(String rowID,
                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        servicesScreen.saveWizard();

        myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.approveInspectionApproveAllAndSignature(inspectionNumber);

        MyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList())
            if (serviceData.isSelected())
                Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(serviceData));
            else
                Assert.assertEquals(servicesScreen.getNumberOfServiceSelectedItems(serviceData.getServiceName()), serviceData.getVehicleParts().size());
        servicesScreen.cancelWizard();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAssignButtonIsPresentWhenSelectSomeTechInCaseDirectAssignOptionIsSetForInspectionType(String rowID,
                                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPECTION_DIRECT_ASSIGN);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        InspectionsSteps.saveInspectionAsFinal();
        myInspectionsScreen.selectInspectionToAssign(inspectionNumber);
        DevicesPopupScreen devicesscreen = new DevicesPopupScreen();
        Assert.assertTrue(devicesscreen.isAssignButtonDisplayed());
        devicesscreen.clickCancelButton();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly(String rowID,
                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        servicesScreen.saveWizard();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        for (ServiceData serviceData : inspectionData.getServicesList())
            Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData));

        approveInspectionsScreen.clickApproveAllServicesButton();
        Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveApproveButtons(), 2);
        approveInspectionsScreen.clickDeclineAllServicesButton();
        Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveDeclineButtons(), 2);
        approveInspectionsScreen.clickSkipAllServicesButton();
        Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveSkipButtons(), 2);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ArrayList<String> inspections = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            final String inspectionNumber = vehicleScreen.getInspectionNumber();
            inspections.add(inspectionNumber);
            QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.saveWizard();
            myInspectionsScreen.selectInspectionForAction(inspectionNumber);
            SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
            selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
            ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
            approveInspectionsScreen.approveInspectionApproveAllAndSignature(inspectionNumber);
            myInspectionsScreen = new MyInspectionsScreen();
        }
        myInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            myInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        Assert.assertFalse(myInspectionsScreen.isApproveInspectionMenuActionExists());
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.clickDoneButton();
        myInspectionsScreen.clickHomeButton();

        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        teamInspectionsScreen.clickActionButton();
        Assert.assertFalse(teamInspectionsScreen.isApproveInspectionMenuActionExists());
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected(String rowID,
                                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ArrayList<String> inspections = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            inspections.add(vehicleScreen.getInspectionNumber());
            QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.saveWizard();
        }
        myInspectionsScreen.selectInspectionForAction(inspections.get(0));
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.approveInspectionApproveAllAndSignature(inspections.get(0));

        myInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            myInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        Assert.assertTrue(myInspectionsScreen.isApproveInspectionMenuActionExists());
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.clickDoneButton();
        myInspectionsScreen.clickHomeButton();

        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        teamInspectionsScreen.clickActionButton();
        Assert.assertTrue(teamInspectionsScreen.isApproveInspectionMenuActionExists());
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionDraftModeIsSetToONWhenSaveInspectionProvidePromptToAUserToSelectEitherDraftOrFinal(String rowID,
                                                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        servicesScreen.clickSaveAsDraft();
        Assert.assertTrue(myInspectionsScreen.isDraftIconPresentForInspection(inspectionNumber));
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.clickActionButton();
        Assert.assertFalse(myInspectionsScreen.isApproveInspectionMenuActionExists());
        Assert.assertFalse(myInspectionsScreen.isCreateWOInspectionMenuActionExists());
        Assert.assertFalse(myInspectionsScreen.isCreateServiceRequestInspectionMenuActionExists());
        Assert.assertFalse(myInspectionsScreen.isCopyInspectionMenuActionExists());
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.clickDoneButton();

        homeScreen = myInspectionsScreen.clickHomeButton();
        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        Assert.assertTrue(teamInspectionsScreen.isDraftIconPresentForInspection(inspectionNumber));
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListColumnApprovedAmount(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        InspectionsSteps.saveInspection();

        myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        approveInspectionsScreen.clickDeclineAllServicesButton();
        approveInspectionsScreen.selectApproveInspectionServiceStatus(inspectionData.getMoneyServicesList().get(inspectionData.getMoneyServicesList().size() - 1));
        //approveInspectionsScreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        NavigationSteps.navigateBackScreen();
        Helpers.waitABit(40 * 1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);

        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchStatus("All active");
        inspectionsWebPage.selectSearchTimeframe("Custom");
        inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());

        inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
        Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$2,000.00");
        Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), "Approved");
        Assert.assertEquals(inspectionsWebPage.getInspectionApprovedTotal(inspectionNumber), "$2000.00");
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatApproveWOIsWorkingCorrectUnderTeamWO(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String locationFilterValue = "All locations";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();

        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        homeScreen = myWorkOrdersScreen.clickHomeButton();
        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(locationFilterValue);
        teamWorkordersScreen.clickSearchSaveButton();
        Assert.assertTrue(teamWorkordersScreen.woExists(workOrderNumber));
        Assert.assertTrue(teamWorkordersScreen.isWorkOrderHasApproveIcon(workOrderNumber));
        teamWorkordersScreen.approveWorkOrder(workOrderNumber, iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        Assert.assertTrue(teamWorkordersScreen.isWorkOrderHasActionIcon(workOrderNumber));
        teamWorkordersScreen.clickHomeButton();
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
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());

        servicesScreen = new ServicesScreen();
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
        invoiceInfoScreen = new InvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickInvoicePayButton();
        invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
        invoiceInfoScreen.clickInvoicePayDialogButon();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();
        myInvoicesScreen.clickHomeButton();
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
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
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
        invoiceInfoScreen = new InvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickInvoicePayButton();
        invoiceInfoScreen.setCashCheckAmountValue(cashcheckamount);
        invoiceInfoScreen.clickInvoicePayDialogButon();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePOValue(), invoiceData.getPoNumber());
        invoiceInfoScreen.clickSaveInvoiceAsDraft();

        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickChangePOPopup();
        myInvoicesScreen.changePO(invoiceData.getNewPoNumber());
        myInvoicesScreen.clickHomeButton();
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
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
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
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());

        servicesScreen = new ServicesScreen();
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
        teaminvoicesscreen.clickHomeButton();

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
    public void testInvoicesVerifyFilterForTeamWOThatReturnsOnlyWorkAssignedToTechWhoIsLoggedIn(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String locationFilterValue = "All locations";
        final String serviceToChangeRepairOrderVendor = "Dye";

        Employee defEmployee = new Employee();
        defEmployee.setEmployeeFirstName("Employee");
        defEmployee.setEmployeeLastName("Simple 20%");
        defEmployee.setEmployeePassword("12345");

        Employee defZayats = new Employee();
        defZayats.setEmployeeFirstName("Oksana");
        defZayats.setEmployeeLastName("Zayats");
        defZayats.setEmployeePassword("1111");

        Employee defInspector = new Employee();
        defInspector.setEmployeeFirstName("Inspector");
        defInspector.setEmployeeLastName("1");
        defInspector.setEmployeePassword("12345");

        ArrayList<Employee> allEmployees = new ArrayList<>();
        allEmployees.add(defEmployee);
        allEmployees.add(defZayats);
        allEmployees.add(defInspector);

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.selectService(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnician() != null) {
                ServicesScreen servicesScreen = new ServicesScreen();
                servicesScreen.openServiceDetails(serviceData.getServiceName());
                ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                if (!serviceData.getServiceDefaultTechnician().isSelected())
                    TechniciansPopupSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
                if (serviceData.getServiceNewTechnician().isSelected())
                    TechniciansPopupSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
                TechniciansPopupSteps.saveTechViewDetails();
                ServiceDetailsScreenSteps.saveServiceDetails();
            }
        }
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        NavigationSteps.navigateBackScreen();
        BaseUtils.waitABit(1000 * 20);

        for (Employee employee : allEmployees) {
            MainScreen mainScreeneen = homeScreen.clickLogoutButton();
            homeScreen = mainScreeneen.userLogin(employee.getEmployeeFirstName(), employee.getEmployeePassword());
            TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
            teamWorkordersScreen.clickSearchButton();
            teamWorkordersScreen.selectSearchLocation(locationFilterValue);
            teamWorkordersScreen.clickSearchSaveButton();
            teamWorkordersScreen.clickOnWO(workOrderNumber);
            OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
            orderMonitorScreen.checkMyWorkCheckbox();
            for (ServiceData serviceData : workOrderData.getServicesList()) {
                if (serviceData.getServiceDefaultTechnician().getTechnicianFullName().equals(employee.getEmployeeName())) {
                    if (serviceData.getServiceDefaultTechnician().isSelected())
                        Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                    else
                        Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                } else if (serviceData.getServiceNewTechnician() == null) {
					Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                } else if (serviceData.getServiceNewTechnician().getTechnicianFullName().equals(employee.getEmployeeName())) {
                    if (serviceData.getServiceNewTechnician().isSelected())
                        Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                    else
                        Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                } else {
                    Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
                }
            }
            NavigationSteps.navigateBackScreen();
            NavigationSteps.navigateBackScreen();
        }

		MainScreen mainScreeneen = homeScreen.clickLogoutButton();

        homeScreen = mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        MonitorWebPage monitorpage = new MonitorWebPage(webdriver);
        backofficeHeader.clickMonitorLink();
		backofficeHeader.clickMonitorLink();
        RepairOrdersWebPage repairorderspage = new RepairOrdersWebPage(webdriver);
        monitorpage.clickRepairOrdersLink();
        repairorderspage.makeSearchPanelVisible();
        repairorderspage.selectSearchLocation("Default Location");
        repairorderspage.selectSearchTimeframe("Custom");
        repairorderspage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
        repairorderspage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        repairorderspage.setSearchWoNumber(workOrderNumber);
        repairorderspage.clickFindButton();

        VendorOrderServicesWebPage vendororderservicespage = new VendorOrderServicesWebPage(webdriver);
        repairorderspage.clickOnWorkOrderLinkInTable(workOrderNumber);
        vendororderservicespage.changeRepairOrderServiceVendor(serviceToChangeRepairOrderVendor, "Device Team");
        vendororderservicespage.waitABit(3000);
        Assert.assertEquals(vendororderservicespage.getRepairOrderServiceTechnician(serviceToChangeRepairOrderVendor), "Oksi User");
        DriverBuilder.getInstance().getDriver().quit();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();

        for (ServiceData serviceData : workOrderData.getServicesList()) {
			if (serviceData.getServiceDefaultTechnician().isSelected())
				Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
			else
				Assert.assertFalse(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
		}

        teamWorkordersScreen = orderMonitorScreen.clickBackButton();
        homeScreen = teamWorkordersScreen.clickHomeButton();
        mainScreeneen = homeScreen.clickLogoutButton();

        homeScreen = mainScreeneen.userLogin(allEmployees.get(1).getEmployeeFirstName(), allEmployees.get(1).getEmployeePassword());
        teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickOnWO(workOrderNumber);
        orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
		for (ServiceData serviceData : workOrderData.getServicesList()) {
			Assert.assertTrue(orderMonitorScreen.isServicePresent(serviceData.getServiceName()));
		}

        teamWorkordersScreen = orderMonitorScreen.clickBackButton();
        homeScreen = teamWorkordersScreen.clickHomeButton();
        mainScreeneen = homeScreen.clickLogoutButton();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionNumbers = new ArrayList<>();
        List<String> workOrderNumbers = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        Helpers.getAlertTextAndCancel();
        serviceRequestsScreen = new ServiceRequestsScreen();
        final String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            ServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.valueOf(inspectionData.getInspectionType()));
            inspectionNumbers.add(vehicleScreen.getInspectionNumber());
            if (inspectionData.isDraft()) {
                NavigationSteps.navigateToServicesScreen();
                ServicesScreen servicesScreen = new ServicesScreen();
                servicesScreen.clickSaveAsDraft();
            } else
                vehicleScreen.saveWizard();
            serviceRequestsScreen = new ServiceRequestsScreen();
        }

        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();

        TeamInspectionsScreen teamInspectionsScreen = new TeamInspectionsScreen();
        for (String inspectionNumber : inspectionNumbers)
            Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumber));

        teamInspectionsScreen.clickBackServiceRequest();
        serviceRequestdetailsScreen.clickBackButton();
        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            ServiceRequestSteps.startCreatingWorkOrderFromServiceRequest(serviceRequestNumber, WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
            workOrderNumbers.add(vehicleScreen.getInspectionNumber());
            NavigationSteps.navigateToOrderSummaryScreen();
            OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
            orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
            orderSummaryScreen.saveWizard();
        }

        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        TeamWorkOrdersScreen teamWorkOrdersScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();

        for (String workOrderNumber : workOrderNumbers)
            Assert.assertTrue(teamWorkOrdersScreen.woExists(workOrderNumber));
        teamWorkOrdersScreen.clickServiceRequestButton();
        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatSinglePageInspectionIsSavedWithoutCrush(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String firstQuestionTxt = "Warning!\n" +
                "Question 'Is all good?' in section 'Required trafficlight' should be answered.";

        final String secondQuestionTxt = "Warning!\n" +
                "Question 'Question 2' in section 'Zayats Section1' should be answered.";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_SINGLE_PAGE);
        SinglePageInspectionScreen singlePageInspectionScreen = new SinglePageInspectionScreen();

        final String inspectionNumber = singlePageInspectionScreen.getInspectionNumber();

        singlePageInspectionScreen.expandToFullScreeenSevicesSection();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(inspectionData.getMatrixServiceData());
        InspectionsSteps.saveInspectionAsFinal();

        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_VIN_REQUIRED);
        VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        WizardScreensSteps.clickSaveButton();
        InspectionsSteps.saveInspectionAsFinal();

        AlertsValidations.acceptAlertAndValidateAlertMessage(firstQuestionTxt);
        singlePageInspectionScreen.expandToFullScreeenQuestionsSection();

        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.answerAllIsGoodQuestion();
        InspectionsSteps.saveInspectionAsFinal();
        AlertsValidations.acceptAlertAndValidateAlertMessage(secondQuestionTxt);

        QuestionsScreenSteps.answerQuestion(inspectionData.getQuestionScreenData().getQuestionData());
        InspectionsSteps.saveInspectionAsFinal();
        myInspectionsScreen.selectInspectionInTable(inspectionNumber);
        myInspectionsScreen.isApproveInspectionMenuActionExists();
        myInspectionsScreen.clickArchiveInspectionButton();
        myInspectionsScreen.selectReasonToArchive(inspectionData.getDeclineReason());
        NavigationSteps.navigateBackScreen();
        settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval(String rowID,
                                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final int timetowaitwo = 4;
        final String inspectionNotes = "Inspection notes";
        final String serviceNotes = "Service Notes";
        final String locationFilterValue = "All locations";
        final String searchStatus = "New";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            if (serviceData.getServicePrice() != null)
                ServicesScreenSteps.selectServiceWithServiceData(serviceData);
            else
                ServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        servicesScreen.saveWizard();

        myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
        visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
        NavigationSteps.navigateToVehicleInfoScreen();
        WizardScreensSteps.clickNotesButton();
        NotesScreen notesScreen = new NotesScreen();
        notesScreen.setNotes(inspectionNotes);
        notesScreen.clickSaveButton();

        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            SelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
            notesScreen = servicedetailsscreen.clickNotesCell();
            notesScreen.setNotes(serviceNotes);
            notesScreen.clickSaveButton();
            servicedetailsscreen.saveSelectedServiceDetails();
        }
        for (ServiceData serviceData : inspectionData.getServicesList())
            Assert.assertTrue(servicesScreen.isNotesIconPresentForSelectedService(serviceData.getServiceName()));

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);
        }
        vehicleScreen.saveWizard();

        Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(inspectionNumber));
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();

        homeScreen = myInspectionsScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkOrdersScreen = homeScreen.clickTeamWorkordersButton();
        homeScreen = teamWorkOrdersScreen.clickHomeButton();

        for (int i = 0; i < timetowaitwo; i++) {
            Helpers.waitABit(60 * 1000);
            teamWorkOrdersScreen = homeScreen.clickTeamWorkordersButton();
            homeScreen = teamWorkOrdersScreen.clickHomeButton();
        }
        teamWorkOrdersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchStatus(searchStatus);
        teamWorkOrdersScreen.setSearchType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR.getWorkOrderTypeName());
        teamWorkOrdersScreen.selectSearchLocation(locationFilterValue);
        teamWorkOrdersScreen.clickSearchSaveButton();

        final String workOrderNumber = teamWorkOrdersScreen.getFirstWorkOrderNumberValue();
        teamWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        WizardScreensSteps.clickNotesButton();
        Assert.assertEquals(notesScreen.getNotesValue(), inspectionNotes);
        notesScreen.clickSaveButton();
        Assert.assertEquals(vehicleScreen.getEst(), inspectionNumber);
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            SelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
            notesScreen = servicedetailsscreen.clickNotesCell();
            Assert.assertEquals(notesScreen.getNotesValue(), serviceNotes);
            notesScreen.clickSaveButton();
            servicedetailsscreen.saveSelectedServiceDetails();
            Assert.assertTrue(servicesScreen.isNotesIconPresentForSelectedWorkOrderService(serviceData.getServiceName()));
        }
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired(String rowID,
                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());

        servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), 0);
        SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
        Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
        Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), inspectionData.getServiceData().getQuestionData().getQuestionAnswer());
        servicedetailsscreen.saveSelectedServiceDetails();

        for (int i = 1; i < inspectionData.getServiceData().getVehicleParts().size(); i++) {
            servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            Assert.assertFalse(servicedetailsscreen.isQuestionFormCellExists());
            servicedetailsscreen.saveSelectedServiceDetails();
        }
        servicesScreen.cancelWizard();
        myInspectionsScreen.clickHomeButton();
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
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoicenum);
        invoicesWebPage.clickFindButton();
        Assert.assertTrue(invoicesWebPage.isInvoiceDisplayed(invoicenum));
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
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY_HD);
        myInvoicesScreen.clickCancelButton();

        myInvoicesScreen.switchToTeamView();
        TeamInvoicesScreen teaminvoicesscreen = new TeamInvoicesScreen();
        teaminvoicesscreen.selectInvoice(invoiceNumber);
        teaminvoicesscreen.clickChangePOPopup();
        teaminvoicesscreen.changePO(invoiceData.getNewPoNumber());
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.INVOICE_PO_SHOULDNT_BE_EMPTY_HD);
        teaminvoicesscreen.clickCancelButton();

        myInvoicesScreen.clickHomeButton();
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

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
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
    public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices(String rowID,
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
        ;
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        PriceMatrixScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
        PriceMatrixScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
        PriceMatrixScreenSteps.selectVehiclePartTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());

        List<ServiceTechnician> serviceTechnicians = new ArrayList<>();
        serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
        serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
        PriceMatrixScreenSteps.verifyVehiclePartTechniciansValue(serviceTechnicians);
        PriceMatrixScreenSteps.savePriceMatrixData();

        servicesScreen.waitServicesScreenLoaded();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        TechRevenueScreen techrevenuescreen = myWorkOrdersScreen.selectWorkOrderTechRevenueMenuItem(workOrderNumber);
        Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName()));
        techrevenuescreen.clickBackButton();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit(String rowID,
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
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        techniciansPopup.selecTechnician(workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        techniciansPopup.saveTechViewDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickTechnicianToolbarIcon();
        servicesScreen.changeTechnician("Dent", workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());

        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        techniciansPopup.cancelSearchTechnician();
        selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
        servicesScreen = new ServicesScreen();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));

        myWorkOrdersScreen.clickHomeButton();
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

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
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
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
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
        workOrdersWebPage.setSearchFromDate(CustomDateProvider.getCurrentDateFormatted());
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
        final String makes[] = {"Chrysler", "Ford", null};
        final String models[] = {"Town and Country", "Explorer", null};

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
        myWorkOrdersScreen.clickHomeButton();
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
        myWorkOrdersScreen = new MyWorkOrdersScreen();
        homeScreen = myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatMessageIsShownForMoneyAndLaborServiceWhenPriceIsChangedTo0UnderWO(String rowID,
                                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String zeroPrice = "0";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_MONITOR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
            if (serviceData.getServicePrice().equals(zeroPrice)) {
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                if (serviceData.getServicePrice().equals(zeroPrice))
                    Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
                selectedServiceDetailsScreen.clickTechniciansIcon();
                Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
            } else {
                selectedServiceDetailsScreen.clickTechniciansIcon();
                Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
                selectedServiceDetailsScreen.cancelSelectedServiceDetails();
                selectedServiceDetailsScreen.setServiceRateValue(serviceData.getServicePrice());
                selectedServiceDetailsScreen.clickTechniciansIcon();
            }
            TechniciansPopup techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
            techniciansPopup.searchTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFirstName());
            techniciansPopup.selecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            techniciansPopup.searchTechnician(serviceData.getServiceNewTechnician().getTechnicianFirstName());
            techniciansPopup.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
            techniciansPopup.clearSerchTechnician();
            DriverBuilder.getInstance().getAppiumDriver().hideKeyboard();
            if (serviceData.getServicePrice().equals(zeroPrice)) {
                Assert.assertFalse(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
                Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
            } else {
                Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
                Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceNewTechnician().getTechnicianFullName()));
            }
            techniciansPopup.saveTechViewDetails();
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        vehicleScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();
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
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TRIM_REQUIRED);
        vehicleScreen.setTrim(trimvalue);
        Assert.assertEquals(vehicleScreen.getTrim(), trimvalue);
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        vehicleScreen.saveWizard();
        homeScreen = myWorkOrdersScreen.clickHomeButton();
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
        teamWorkordersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians(String rowID,
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
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());

        ServicesScreenSteps.selectBundleService(workOrderData.getBundleService());
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);

        ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        PriceMatrixScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
        PriceMatrixScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
        PriceMatrixScreenSteps.selectVehiclepartAdditionalService(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService());
        PriceMatrixScreenSteps.savePriceMatrixData();

        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        TechniciansPopup techniciansPopup = myWorkOrdersScreen.selectWorkOrderTechniciansMenuItem(workOrderNumber);
        techniciansPopup.selecTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.selecTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName());
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();
        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();
        Assert.assertEquals(vehicleScreen.getTechnician(), workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName() +
                ", " + workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName());
        vehicleScreen.cancelOrder();
        myWorkOrdersScreen = new MyWorkOrdersScreen();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown(String rowID,
                                                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        NavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : inspectionData.getDamagesData()) {
            servicesScreen.selectGroupServiceItem(damageData.getDamageGroupName());
            for (ServiceData serviceData : damageData.getMoneyServices())
                AvailableServicesScreenValidations.verifyServiceExixts(serviceData.getServiceName(), true);
            servicesScreen.clickServiceTypesButton();
        }
        servicesScreen.cancelWizard();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit(String rowID,
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
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices()) {
            servicesScreen.selectService(workOrderData.getDamageData().getDamageGroupName());
            servicesScreen.selectServiceSubSrvice(workOrderData.getDamageData().getDamageGroupName(), serviceData.getServiceName());
        }
        for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
            Assert.assertTrue(servicesScreen.isServiceWithSubSrviceSelected(workOrderData.getDamageData().getDamageGroupName(), serviceData.getServiceName()));
        servicesScreen.saveWizard();
        homeScreen = myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatAnswerServicesAreCorrectlyAddedForWOWhenPanelGroupIsSet(String rowID,
                                                                                        String description, JSONObject testData) {

        final String questionName = "Q1";
        final String questionAswer = "No - rate 0";
        final String questionAswerSecond = "A1";
        final String questionVehiclePart = "Deck Lid";

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_PANEL_GROUP);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        NavigationSteps.navigateToScreen("Zayats Section2");
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.selectAnswerForQuestionWithAdditionalConditions(questionName, questionAswer, questionAswerSecond, questionVehiclePart);
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getServiceData().getServiceName()));

        servicesScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();
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
        homeScreen = myWorkOrdersScreen.clickHomeButton();
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
        Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.STOP_WORKORDER_CREATION);
        vehicleScreen.clickCancelButton();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.STOP_WORKORDER_CREATION);

        String workOrderNumber = myWorkOrdersScreen.getFirstWorkOrderNumberValue();
        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen = new VehicleScreen();
        vehicleScreen.clickCancelButton();
        Assert.assertEquals(Helpers.getAlertTextAndCancel(), AlertsCaptions.STOP_WORKORDER_EDIT);
        vehicleScreen.clickCancelButton();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.STOP_WORKORDER_EDIT);
        myWorkOrdersScreen.openWorkOrderDetails(workOrderNumber);
        vehicleScreen = new VehicleScreen();
        vehicleScreen.clickCancelButton();

        homeScreen = myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAcceptDeclineActionsArePresentForTechWhenTechnicianAcceptanceRequiredOptionIsONAndStatusIsProposed(String rowID,
                                                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.saveNewServiceRequest();
        final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        DriverBuilder.getInstance().getDriver().quit();


        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        Assert.assertTrue(serviceRequestsScreen.isServiceRequestProposed(serviceRequestNumber));
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isAcceptActionExists());
        Assert.assertTrue(serviceRequestsScreen.isDeclineActionExists());
        serviceRequestsScreen.selectAcceptAction();

        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_ACCEPT_SERVICEREQUEST);
        serviceRequestsScreen = new ServiceRequestsScreen();
        Assert.assertTrue(serviceRequestsScreen.isServiceRequestOnHold(serviceRequestNumber));
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestsScreen.isAcceptActionExists());
        Assert.assertFalse(serviceRequestsScreen.isDeclineActionExists());
        serviceRequestsScreen.selectRejectAction();
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();
        serviceRequestsScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenSRIsDeclinedStatusReasonShouldBeSelected(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.saveNewServiceRequest();
        final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        DriverBuilder.getInstance().getDriver().quit();


        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        Assert.assertTrue(serviceRequestsScreen.isServiceRequestProposed(serviceRequestNumber));
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isAcceptActionExists());
        Assert.assertTrue(serviceRequestsScreen.isDeclineActionExists());
        serviceRequestsScreen.selectDeclineAction();

        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(alertText, AlertsCaptions.ALERT_DECLINE_SERVICEREQUEST);
        serviceRequestsScreen.clickDoneCloseReasonDialog();
        Assert.assertFalse(serviceRequestsScreen.isServiceRequestExists(serviceRequestNumber));
        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatSRIsNotAcceptedWhenEmployeeReviewOrUpdateIt(String rowID,
                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();
        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.saveNewServiceRequest();
        final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        DriverBuilder.getInstance().getDriver().quit();


        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        Assert.assertTrue(serviceRequestsScreen.isServiceRequestProposed(serviceRequestNumber));
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectEditServiceRequestAction();
        VehicleScreen vehicleScreen = new VehicleScreen();

        vehicleScreen.setTech(serviceRequestData.getVihicleInfo().getDefaultTechnician().getTechnicianFullName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.saveWizard();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isAcceptActionExists());
        Assert.assertTrue(serviceRequestsScreen.isDeclineActionExists());
        serviceRequestsScreen.selectAcceptAction();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_ACCEPT_SERVICEREQUEST);
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();

        serviceRequestsScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyThatItIsPossibleToAcceptDeclineAppointmentWhenOptionAppointmentAcceptanceRequiredEqualsON(String rowID,
                                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String technicianValue = "Employee Simple 20%";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String startDate = LocalDate.now().plusDays(1).format(formatter);
        String endDate = LocalDate.now().plusDays(2).format(formatter);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickGeneralInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestGeneralInfoAssignedTo(technicianValue);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.saveNewServiceRequest();
        final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
        serviceRequestsListInteractions.addAppointmentFromSRList(startDate, endDate, technicianValue);
        Assert.assertTrue(new ServiceRequestsListVerifications().isAddAppointmentFromSRListClosed(),
                "The Add Appointment dialog hasn't been closed");
        DriverBuilder.getInstance().getDriver().quit();

        HomeScreen homeScreen = new HomeScreen();
        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();

        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectAppointmentRequestAction();

        Assert.assertTrue(serviceRequestsScreen.isAcceptAppointmentRequestActionExists());
        Assert.assertTrue(serviceRequestsScreen.isDeclineAppointmentRequestActionExists());
        serviceRequestsScreen.clickCloseButton();

        serviceRequestsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatDefaultTechIsNotChangedWhenResetOrderSplit(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();

        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getServiceDefaultTechnician() != null) {
                ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                TechniciansPopup techniciansPopup = new TechniciansPopup();
                Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(serviceData.getServiceDefaultTechnician().getTechnicianFullName()));
                techniciansPopup.saveTechViewDetails();
            }
            ServiceDetailsScreenSteps.saveServiceDetails();
        }

        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoScreenSteps.clickTech();
        TechniciansPopupSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice2() != null)
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice2());
            if (serviceData.getServiceDefaultTechnician() != null) {
                ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
                TechniciansPopupSteps.saveTechViewDetails();
            } else {
                ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                TechniciansPopupValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
                TechniciansPopupSteps.saveTechViewDetails();
            }
            ServiceDetailsScreenSteps.saveServiceDetails();
        }
        servicesScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyAssignTechToServiceTypeInsteadOfIndividualServices(String rowID,
                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_GROUP_SERVICE_TYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.clickTechnicianToolbarIcon();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.NO_SELECTED_SERVICES);
        for (DamageData damageData : workOrderData.getDamagesData()) {
            ServicesScreenSteps.selectPanelServiceData(damageData);
            servicesScreen.clickServiceTypesButton();
        }

        servicesScreen = new ServicesScreen();
        servicesScreen.clickTechnicianToolbarIcon();
        ServiceTypesScreen serviceTypesScreen = new ServiceTypesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(serviceData.getServiceName()));

        DamageData moneyServicePanel = workOrderData.getDamagesData().get(0);

        TechniciansPopup techniciansPopup = serviceTypesScreen.clickOnPanel(moneyServicePanel.getDamageGroupName());
        techniciansPopup.selecTechnician(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
        serviceTypesScreen.clickSaveButton();

        servicesScreen.selectGroupServiceItem(moneyServicePanel.getDamageGroupName());
        for (ServiceData serviceData : moneyServicePanel.getMoneyServices()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(serviceData.getServiceName());
            techniciansPopup = selectedServiceDetailsScreen.clickTechniciansIcon();
            Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
            techniciansPopup.cancelSearchTechnician();
            selectedServiceDetailsScreen.cancelSelectedServiceDetails();
        }
        servicesScreen.clickServiceTypesButton();

        DamageData bundleServicePanel = workOrderData.getDamagesData().get(1);
        servicesScreen.clickTechnicianToolbarIcon();
        techniciansPopup = serviceTypesScreen.clickOnPanel(bundleServicePanel.getDamageGroupName());
        techniciansPopup.selecTechnician(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
        serviceTypesScreen.clickSaveButton();

        servicesScreen.openServiceDetails(bundleServicePanel.getBundleService().getBundleServiceName());
        for (ServiceData serviceData : bundleServicePanel.getBundleService().getServices()) {
            SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
            selectedservicebundlescreen.openBundleInfo(serviceData.getServiceName());
            ServiceDetailsScreenSteps.clickServiceTechniciansCell();
            techniciansPopup = new TechniciansPopup();
            Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
            techniciansPopup.cancelSearchTechnician();
            ServiceDetailsScreenSteps.cancelServiceDetails();
            selectedservicebundlescreen.clickCancelBundlePopupButton();
        }

        servicesScreen.clickTechnicianToolbarIcon();
        techniciansPopup = serviceTypesScreen.clickOnPanel(workOrderData.getDamageData().getDamageGroupName());
        techniciansPopup.selecTechnician(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.selecTechnician(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
        serviceTypesScreen.clickSaveButton();

        servicesScreen.openServiceDetails(workOrderData.getDamageData().getBundleService().getBundleServiceName());
        SelectedServiceBundleScreen selectedservicebundlescreen = new SelectedServiceBundleScreen();
        selectedservicebundlescreen.openBundleInfo(workOrderData.getDamageData().getBundleService().getService().getServiceName());
        ServiceDetailsScreenSteps.clickServiceTechniciansCell();

        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName()));
        techniciansPopup.cancelSearchTechnician();
        ServiceDetailsScreenSteps.saveServiceDetails();
        selectedservicebundlescreen.clickCancelBundlePopupButton();

        servicesScreen.selectGroupServiceItem(moneyServicePanel.getDamageGroupName());
        ServicesScreenSteps.openCustomServiceDetails(moneyServicePanel.getMoneyService().getServiceName());
        ServiceDetailsScreenSteps.slectServiceVehiclePart(moneyServicePanel.getMoneyService().getVehiclePart());
        ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
        techniciansPopup.cancelSearchTechnician();
        ServiceDetailsScreenSteps.cancelServiceDetails();

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsPossibleToAssignTechWhenWOIsNotStarted(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String defaultLocationValue = "Test Location ZZZ";

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(defaultLocationValue);
        final String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(defaultLocationValue);
        teamWorkordersScreen.clickSearchSaveButton();

        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        OrderMonitorData orderMonitorData = workOrderData.getOrderMonitorData();
        MonitorServiceData firstMonitorServiceData = orderMonitorData.getMonitorServicesData().get(0);
        Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
        TechniciansPopup techniciansPopup = serviceDetailsPopup.clickTech();
        techniciansPopup.selecTechnician(firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
        orderMonitorScreen = new OrderMonitorScreen();
        orderMonitorScreen.selectPanel(firstMonitorServiceData.getMonitorService());
        Assert.assertEquals(serviceDetailsPopup.getTechnicianValue(), firstMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        orderMonitorScreen = new OrderMonitorScreen();
        orderMonitorScreen.clickStartOrderButton();
        Assert.assertTrue(Helpers.getAlertTextAndAccept().contains(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER));
        orderMonitorScreen = new OrderMonitorScreen();

        MonitorServiceData secondMonitorServiceData = orderMonitorData.getMonitorServicesData().get(1);
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.clickTech();
        techniciansPopup.selecTechnician(secondMonitorServiceData.getMonitorService().getServiceDefaultTechnician().getTechnicianFullName());
        techniciansPopup.saveTechViewDetails();
        orderMonitorScreen = new OrderMonitorScreen();
        serviceDetailsPopup = orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.clickStartService();
        serviceDetailsPopup = orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.setCompletedServiceStatus();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(secondMonitorServiceData.getMonitorService()), secondMonitorServiceData.getMonitorServiceStatus());
        orderMonitorScreen.selectPanel(secondMonitorServiceData.getMonitorService());
        serviceDetailsPopup.clickTech();
        serviceDetailsPopup.clickServiceDetailsDoneButton();

        TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsNotPossibleToAssignTechWhenWOIsOnHold(String rowID,
                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String defaultLocationValue = "Test Location ZZZ";
        final String workOrderMonitorStatus = "On Hold";
        final String statusReason = "On Hold new reason";

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_MONITOR_REQUIRED_START);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(defaultLocationValue);
        final String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        myWorkOrdersScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkordersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkordersScreen.clickSearchButton();
        teamWorkordersScreen.selectSearchLocation(defaultLocationValue);
        teamWorkordersScreen.clickSearchSaveButton();

        teamWorkordersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkordersScreen.selectWOMonitor();
        Assert.assertTrue(orderMonitorScreen.isStartOrderButtonExists());
        orderMonitorScreen.changeStatusForWorkOrder(workOrderMonitorStatus, statusReason);

        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
        serviceDetailsPopup.clickTech();
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        TeamWorkOrdersScreen teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatTechSplitAssignedFormVehicleScreenIsSetToServicesUnderList(String rowID,
                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        VehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupSteps.selectServiceTechnician(technician);
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();

        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            servicesScreen.openServiceDetails(serviceData.getServiceName());
            ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
                TechniciansPopupValidations.verifyServiceTechnicianIsSelected(technician);
            TechniciansPopupSteps.saveTechViewDetails();
            ServiceDetailsScreenSteps.saveServiceDetails();
        }

        servicesScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyPriceMatrixItemDoesntHaveAdditionalServicesItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
                                                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_SMOKE_TEST);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        WorkOrdersSteps.saveWorkOrder();

        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        VehicleInfoScreenSteps.clickTech();
        TechniciansPopupSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        TechniciansPopupValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());
        TechniciansPopupSteps.cancelTechViewDetails();
        NavigationSteps.navigateToServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice()));

        WizardScreensSteps.cancelWizard();
        NavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyPriceMatrixItemHasMoneyAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
                                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_SMOKE_TEST);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();


        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
        PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
        PriceMatrixScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        TechniciansPopup techniciansPopup = priceMatrixScreen.openTechniciansPopup();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
        Assert.assertFalse(techniciansPopup.isCustomTabSelected());
        techniciansPopup.cancelTechViewDetails();

        priceMatrixScreen.selectDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
        techniciansPopup = priceMatrixScreen.openTechniciansPopup();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
        Assert.assertFalse(techniciansPopup.isCustomTabSelected());
        techniciansPopup.cancelTechViewDetails();

        priceMatrixScreen.clickSave();
        servicesScreen = new ServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

        servicesScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyPriceMatrixItemHasPercentageAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmountPlusAdditionalPercentage(String rowID,
                                                                                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_SMOKE_TEST);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();


        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
        PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
        PriceMatrixScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        TechniciansPopup techniciansPopup = priceMatrixScreen.openTechniciansPopup();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
        Assert.assertFalse(techniciansPopup.isCustomTabSelected());
        techniciansPopup.cancelTechViewDetails();

        priceMatrixScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
        techniciansPopup = priceMatrixScreen.openTechniciansPopup();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(techniciansPopup.isEvenlyTabSelected());
        Assert.assertFalse(techniciansPopup.isCustomTabSelected());
        techniciansPopup.cancelTechViewDetails();

        priceMatrixScreen.clickSave();
        servicesScreen = new ServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

        servicesScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyIfServiceHasDefaultTechnicianAndItsAmountIs0ThenDefaultTechnicianShouldBeAssignedToTheService_NotTechnicianSplitAtWorkOrderLevel(String rowID,
                                                                                                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
        ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        ServiceDetailsScreenSteps.saveServiceDetails();

        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoScreenSteps.clickTech();
        TechniciansPopupSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();
        NavigationSteps.navigateToServicesScreen();

        servicesScreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        TechniciansPopupValidations.verifyServiceTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician());
        TechniciansPopupSteps.cancelTechViewDetails();
        ServiceDetailsScreenSteps.cancelServiceDetails();
        WizardScreensSteps.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatCorrectTechSplitAmountIsShownForMatrixServiceWhenChangePriceTo0(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String serviceZeroPrice = "0";

        HomeScreen homeScreen = new HomeScreen();
        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();


        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
        PriceMatrixScreenSteps.selectVehiclePart(vehiclePartData);
        PriceMatrixScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        priceMatrixScreen.setPrice(serviceZeroPrice);

        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);
        priceMatrixScreen.clickOnTechnicians();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
        techniciansPopup.saveTechViewDetails();
        priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.clickSaveButton();

        servicesScreen.waitServicesScreenLoaded();
        servicesScreen.cancelWizard();
        myWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatTechSplitAmountIsShownCorrectUnderMonitorForServiceWithAdjustments(String rowID,
                                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String locationsFiler = "All locations";

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        ServiceDetailsScreenSteps.selectServiceAdjustment(workOrderData.getServiceData().getServiceAdjustment());
        ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        ServiceDetailsScreenSteps.saveServiceDetails();

        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        NavigationSteps.navigateBackScreen();
        TeamWorkOrdersScreen teamWorkOrdersScreen = homeScreen.clickTeamWorkordersButton();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(locationsFiler);
        teamWorkOrdersScreen.setSearchWorkOrderNumber(workOrderNumber);
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        Assert.assertEquals(orderMonitorScreen.getMonitorServiceAmauntValue(workOrderData.getServiceData()), workOrderData.getServiceData().getServicePrice2());
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getServiceData());
        Assert.assertEquals(serviceDetailsPopup.getServiceDetailsPriceValue(),
                BackOfficeUtils.getFormattedServicePriceValue(BackOfficeUtils.getServicePriceValue(workOrderData.getServiceData().getServicePrice())));
        TechniciansPopup techniciansPopup = serviceDetailsPopup.clickTech();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
                workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertEquals(techniciansPopup.getCustomTechnicianPercentage(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
                workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPercentageValue());

        techniciansPopup.cancelTechViewDetails();
        serviceDetailsPopup.clickServiceDetailsCancelButton();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRequiredServicesHasCorrectTech(String rowID,
                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_MONITOR_REQUIRED_SERVICES_ALL);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        SelectedServiceBundleScreen serviceBundleScreen = servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }
        serviceBundleScreen.clickCancelBundlePopupButton();
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();
        NavigationSteps.navigateToServicesScreen();

        servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
                ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            ServiceDetailsScreenSteps.cancelServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }
        serviceBundleScreen.clickCancelBundlePopupButton();
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        ServiceDetailsScreenSteps.saveServiceDetails();
        servicesScreen.clickOnSelectService(workOrderData.getMatrixServiceData().getMatrixServiceName());
        servicesScreen.selectServicePriceMatrices(workOrderData.getMatrixServiceData().getHailMatrixName());
        PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        PriceMatrixScreenSteps.savePriceMatrixData();
        servicesScreen.saveWizard();

        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

        for (ServiceData serviceData : bundleServiceData.getServices()) {
            selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
                ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            ServiceDetailsScreenSteps.cancelServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }
        serviceBundleScreen.clickCancelBundlePopupButton();
        WorkOrdersSteps.saveWorkOrder();

        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRequiredBundleItemsHasCorrectDefTech(String rowID,
                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        SelectedServiceBundleScreen serviceBundleScreen = servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.cancelSelectedServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }
        serviceBundleScreen.clickCancelBundlePopupButton();

        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }

            if (serviceData.getVehiclePart() != null)
                ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
            ServiceDetailsScreenSteps.saveServiceDetails();
        }
        serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
        serviceBundleScreen.saveSelectedServiceDetails();
        WorkOrdersSteps.saveWorkOrder();

        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }
            ServiceDetailsScreenSteps.cancelServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }
        serviceBundleScreen.clickCancelBundlePopupButton();
        WorkOrdersSteps.saveWorkOrder();

        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRequiredBundleItemsAndServiceWithExpensesAnd0PriceHasCorrectDefTechAfterEditWO(String rowID,
                                                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        SelectedServiceBundleScreen serviceBundleScreen = servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.cancelSelectedServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }

        serviceBundleScreen.clickCancelBundlePopupButton();
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();
        NavigationSteps.navigateToServicesScreen();

        servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            SelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }

            if (serviceData.getVehiclePart() != null)
                ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

            ServiceDetailsScreenSteps.saveServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }

        serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
        serviceBundleScreen.saveSelectedServiceDetails();

        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
        selectedServiceDetailsScreen.clickTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        TechniciansPopupSteps.saveTechViewDetails();
        ServiceDetailsScreenSteps.saveServiceDetails();
        ServicesScreenSteps.waitServicesScreenLoad();
        WorkOrdersSteps.saveWorkOrder();

        myWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();
        NavigationSteps.navigateToServicesScreen();
        servicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }
            selectedServiceDetailsScreen.cancelSelectedServiceDetails();
            serviceBundleScreen.waitUntilBundlePopupOpened();
        }

        serviceBundleScreen.clickCancelBundlePopupButton();
        servicesScreen.openServiceDetails(workOrderData.getServiceData().getServiceName());
        selectedServiceDetailsScreen.clickTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        TechniciansPopupSteps.saveTechViewDetails();
        ServiceDetailsScreenSteps.saveServiceDetails();
        WorkOrdersSteps.saveWorkOrder();

        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatSelectedServicesHaveCorrectTechSplitWhenChangeIsDuringCreatingWO(String rowID,
                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_ALL_SERVICES);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();
        VehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupSteps.selectServiceTechnician(serviceTechnician);
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();

        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.openServiceDetails(serviceData.getServiceName());
            ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
                TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
            TechniciansPopupSteps.cancelTechViewDetails();
            ServiceDetailsScreenSteps.cancelServiceDetails();
        }

        ServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        PriceMatrixScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        PriceMatrixScreenSteps.verifyVehiclePartTechniciansValue(workOrderData.getVehicleInfoData().getNewTechnicians());
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.clickDiscaunt(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        TechniciansPopupSteps.cancelTechViewDetails();
        ServiceDetailsScreenSteps.cancelServiceDetails();
        priceMatrixScreen.setTime(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTime());
        priceMatrixScreen.clickSaveButton();
        ServicesScreen servicesScreen = new ServicesScreen();

        servicesScreen.openCustomServiceDetails(workOrderData.getBundleService().getBundleServiceName());
        SelectedServiceBundleScreen serviceBundleScreen = new SelectedServiceBundleScreen();
        serviceBundleScreen.clickTechniciansIcon();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            TechniciansPopupValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        TechniciansPopupSteps.cancelTechViewDetails();
        serviceBundleScreen.openBundleInfo(workOrderData.getBundleService().getService().getServiceName());
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            ServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
        ServiceDetailsScreenSteps.cancelServiceDetails();
        serviceBundleScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
        serviceBundleScreen.saveSelectedServiceDetails();
        Assert.assertTrue(servicesScreen.checkServiceIsSelected(workOrderData.getBundleService().getBundleServiceName()));
        servicesScreen.clickSave();
        servicesScreen.clickFinalPopup();
        servicesScreen.clickSave();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));

        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfServicePriceIs0AndHasDefTechAssignToServiceDefTech(String rowID,
                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(_003_Test_Customer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.clickTech();
        TechniciansPopupSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        TechniciansPopupSteps.saveTechViewDetails();
        AssignTechniciansSteps.assignTechniciansToWorkOrder();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        ServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
        ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        TechniciansPopupValidations.verifyServiceTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician());
        TechniciansPopupValidations.verifyServiceTechnicianPriceValue(workOrderData.getServiceData().getServiceDefaultTechnician(),
                workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
        TechniciansPopupSteps.cancelTechViewDetails();
        ServiceDetailsScreenSteps.cancelServiceDetails();
        ServicesScreenSteps.waitServicesScreenLoad();
        WizardScreensSteps.cancelWizard();

        NavigationSteps.navigateBackScreen();
        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsNotPossibleToChangeStatusForServiceOrPhaseWhenCheckOutRequired(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.waitServicesScreenLoaded();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();
        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        serviceDetailsPopup.clickServiceStatusCell();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(),
                AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickStartPhase();
        serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        serviceDetailsPopup.clickServiceStatusCell();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(),
                AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickStartPhaseCheckOutButton();
        serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getMatrixServiceData().getMatrixServiceName());
        serviceDetailsPopup.clickServiceStatusCell();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(),
                AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.changeStatusForStartPhase(OrderMonitorPhases.COMPLETED);
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(workOrderData.getMatrixServiceData().getMatrixServiceName()),
                OrderMonitorPhases.COMPLETED.getrderMonitorPhaseName());
        orderMonitorScreen.clickBackButton();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionPhaseEnforcementIsOFFAndStartServiceRequiredItIsPossibleToStartServiceFromInactivePhase(String rowID,
                                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_DEVICE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        ServicesScreenSteps.selectMatrixServiceDataAndSave(workOrderData.getMatrixServiceData());
        servicesScreen.waitServicesScreenLoaded();
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndSaveWorkOrder();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.saveWizard();

        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getOrderMonitorData().getMonitorServiceData().getMonitorService());
        Assert.assertTrue(serviceDetailsPopup.isStartServiceButtonPresent());
        serviceDetailsPopup.clickServiceDetailsDoneButton();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        Assert.assertTrue(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clickStartPhase();
        orderMonitorScreen.waitOrderMonitorScreenLoaded();
        Assert.assertFalse(orderMonitorScreen.isStartPhaseButtonPresent());
        orderMonitorScreen.clickBackButton();
        homeScreen = teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatTechWhoIsNotAssignedToOrderServiceCannotStartOrder(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
        VehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        VehicleInfoScreenSteps.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = VehicleInfoScreenSteps.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getVehiclePart() != null)
                ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
            ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            TechniciansPopupSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
            TechniciansPopupSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
            TechniciansPopupSteps.saveTechViewDetails();
            ServiceDetailsScreenSteps.saveServiceDetails();
        }
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.setSearchWorkOrderNumber(workOrderNumber);
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        orderMonitorScreen.clickStartOrderButton();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(),
                AlertsCaptions.YOU_CANT_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
        orderMonitorScreen = new OrderMonitorScreen();

        orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatIsItImpossibleToChangeStatusForServiceOrPhaseWhenOrderIsNotStarted(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.userLogin("Zayats", "1111");

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);


        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        MyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DELAY_START);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.selectLocation(workOrderData.getVehicleInfoData().getLocation());
        String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            ServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                ServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getVehiclePart() != null)
                ServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());
            ServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            TechniciansPopup techniciansPopup = new TechniciansPopup();
            techniciansPopup.selecTechnician(serviceData.getServiceNewTechnician().getTechnicianFullName());
            techniciansPopup.unselecTechnician(serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            ServiceDetailsScreenSteps.saveServiceDetails();
            ServiceDetailsScreenSteps.saveServiceDetails();
        }
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();

        TeamWorkOrdersScreen teamWorkOrdersScreen = myWorkOrdersScreen.switchToTeamWorkOrdersView();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchLocation(workOrderData.getVehicleInfoData().getLocation());
        teamWorkOrdersScreen.setSearchWorkOrderNumber(workOrderNumber);
        teamWorkOrdersScreen.clickSearchSaveButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);

        OrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(serviceData), OrderMonitorPhases.QUEUED.getrderMonitorPhaseName());

        OrderMonitorServiceDetailsPopup serviceDetailsPopup = orderMonitorScreen.selectPanel(workOrderData.getServicesList().get(workOrderData.getServicesList().size() - 1));
        serviceDetailsPopup.clickServiceStatusCell();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(),
                AlertsCaptions.YOU_MUST_START_REPAIR_ORDER_BECAUSE_YOU_ARE_NOT_ASSIGNED_TO_SERVICES);
        serviceDetailsPopup.clickServiceDetailsCancelButton();

        orderMonitorScreen.clickOrderStartDateButton();
        LocalDate date = LocalDate.now();
        orderMonitorScreen.setOrderStartYearValue(date.getYear() + 1);
        Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear()));
        orderMonitorScreen.setOrderStartYearValue(date.getYear() - 1);
        Assert.assertEquals(orderMonitorScreen.getOrderStartYearValue(), Integer.toString(date.getYear() - 1));
        orderMonitorScreen.closeSelectorderDatePicker();

        orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();

        homeScreen.clickLogoutButton();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanChangeApprovedStatusOfPercentageServiceToDeclinedOnlyUsingDeclineAllOption(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        final String declineReason = "Decline 2";

        HomeScreen homeScreen = new HomeScreen();
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSPECTION_ALL_SERVICES);
        VehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectService(inspectionData.getPercentageServiceData().getServiceName());
        InspectionsSteps.saveInspectionAsFinal();

        myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);

        final String serviceName = inspectionData.getPercentageServiceData().getServiceName();
        Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.selectInspectionServiceToSkip(serviceName);
        Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.selectInspectionServiceToDecline(serviceName);
        Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.clickSkipAllServicesButton();
        Assert.assertFalse(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertTrue(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.clickDeclineAllServicesButton();
        Assert.assertFalse(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertTrue(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.selectStatusReason(declineReason);
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneStatusReasonButton();
        myInspectionsScreen.clickHomeButton();
    }
}
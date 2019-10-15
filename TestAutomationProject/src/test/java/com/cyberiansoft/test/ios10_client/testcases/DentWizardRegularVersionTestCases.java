package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCarHistoryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularSettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.DentWizardInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.DentWizardInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.DentWizardWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import io.appium.java_client.ios.IOSElement;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DentWizardRegularVersionTestCases extends ReconProDentWizardBaseTestCase {

    public RegularHomeScreen homescreen;

    @BeforeClass
    public void setUpSuite() throws Exception {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getDentWizardSuiteTestCasesDataPath();
        mobilePlatform = MobilePlatform.IOS_REGULAR;
        initTestUser(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);

        DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
                ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(),
                "DW_Automation3", envType);

        RegularMainScreen mainscr = new RegularMainScreen();
        mainscr.userLogin(UtilConstants.USER_LOGIN, UtilConstants.USER_PASSWORD);
        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAvailableSelectedServicesOn();
        settingsScreen.setInsvoicesCustomLayoutOff();
        homescreen = settingsScreen.clickHomeButton();
        ExcelUtils.setDentWizardExcelFile();
    }

    @BeforeMethod
    public void setUpCustomer() {
        if (!homescreen.getActiveCustomerValue().equals(UtilConstants.TEST_CUSTOMER_FOR_TRAINING)) {
            RegularCustomersScreen customersScreen = homescreen.clickCustomersButton();
            customersScreen.swtchToWholesaleMode();
            homescreen = customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testValidVINCheck(String rowID,
                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testTopCustomersSetting(String rowID,
                                        String description, JSONObject testData) {

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowTopCustomersOn();
        settingsScreen.clickHomeButton();

        RegularCustomersScreen customersScreen = homescreen.clickCustomersButton();
        Assert.assertTrue(customersScreen.checkTopCustomersExists());
        Assert.assertTrue(customersScreen.checkCustomerExists(UtilConstants.TEST_CUSTOMER_FOR_TRAINING));
        customersScreen.clickHomeButton();

        homescreen.clickSettingsButton();
        settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowTopCustomersOff();
        settingsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVINDuplicateCheck(String rowID,
                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setCheckDuplicatesOn();
        settingsScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        String searchresult = vehicleScreen.setVINAndAndSearch(workOrderData.getVehicleInfoData().getVINNumber().substring(
                0, 11));
        Assert.assertEquals(searchresult, "No vehicle invoice history found");
        vehicleScreen.setVINValue(workOrderData.getVehicleInfoData().getVINNumber().substring(10, 17));
        String msg = vehicleScreen.getExistingWorkOrdersDialogMessage();
        Assert.assertTrue(msg.contains("Existing work orders were found"), msg);
        if (DriverBuilder.getInstance().getAppiumDriver().findElementsByAccessibilityId("Close").size() > 0)
            DriverBuilder.getInstance().getAppiumDriver().findElementByAccessibilityId("Close").click();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
        homescreen.clickSettingsButton();
        settingsScreen = new RegularSettingsScreen();
        settingsScreen.setCheckDuplicatesOff();
        settingsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVehiclePartRequirement(String rowID,
                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        List<String> sericesToValidate = new ArrayList<>();
        sericesToValidate.add("PDR Panel (Non-Customary)");
        sericesToValidate.add("PDR Vehicle");
        sericesToValidate.add("PDR - Panel (Hail)");

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();

        settingsScreen.setInsvoicesCustomLayoutOff();
        settingsScreen.setShowAvailableSelectedServicesOn();
        settingsScreen.setCheckDuplicatesOn();
        settingsScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServicePanel(workOrderData.getDamageData());
        for (String serviceName : sericesToValidate)
            RegularAvailableServicesScreenValidations.verifyServiceExixts(serviceName, true);
        RegularServicesScreenSteps.clickServiceTypesButton();
        RegularServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();

        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickSettingsButton();
        settingsScreen = new RegularSettingsScreen();
        settingsScreen.setCheckDuplicatesOff();
        settingsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testTurningMultipleWorkOrdersIntoASingleInvoice(String rowID,
                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();

        WorkOrderData workOrderData1 = workOrdersData.get(0);

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setInsvoicesCustomLayoutOff();
        settingsScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData1.getVehicleInfoData().getVINNumber());

        String workOrderNumber1 = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData1.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData1.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData1.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();

        // ==================Create second WO=============
        WorkOrderData workOrderData2 = workOrdersData.get(1);

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData2.getVehicleInfoData().getVINNumber());

        String workOrderNumber2 = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData2.getVehicleInfoData());
        RegularNavigationSteps.navigateToServicesScreen();

        int i = 1;
        for (DamageData damageData : workOrderData2.getDamagesData()) {
            RegularServicesScreenSteps.selectServicePanel(damageData);
            RegularServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(damageData.getMoneyService());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            if (i == 1) {

                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(damageData.getMoneyService().getServiceDefaultTechnician());
                for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
                    RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(damageData.getMoneyService().getServiceDefaultTechnician(),
                        damageData.getMoneyService().getServiceDefaultTechnician().getTechnicianPriceValue());
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(damageData.getMoneyService().getServiceDefaultTechnician());
                for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
                    RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
                            serviceTechnician.getTechnicianPriceValue());
                i++;
            } else {
                RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
                for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
                    RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

                RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(damageData.getMoneyService().getServiceDefaultTechnician());
                for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
                    RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(serviceTechnician);


                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(damageData.getMoneyService().getServiceDefaultTechnician(),
                        damageData.getMoneyService().getServiceDefaultTechnician().getTechnicianPriceValue());
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(damageData.getMoneyService().getServiceDefaultTechnician());
                for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
                    RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
                            serviceTechnician.getTechnicianPriceValue());
            }
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        final String[] wos = {workOrderNumber1, workOrderNumber2};
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWOs(wos);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(workOrderNumber1));
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(workOrderNumber2));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber1, false);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber2, false);
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        final String wosvalue = workOrderNumber1 + ", " + workOrderNumber2;
        Assert.assertEquals(wosvalue, myInvoicesScreen.getWOsForInvoice(invoiceNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice(String rowID,
                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();
        List<String> workOrders = new ArrayList<>();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        for (WorkOrderData workOrderData : workOrdersData) {
            RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
            RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

            workOrders.add(RegularVehicleInfoScreenSteps.getWorkOrderNumber());
            RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

            RegularNavigationSteps.navigateToServicesScreen();
            for (DamageData damageData : workOrderData.getDamagesData()) {
                RegularServicesScreenSteps.selectPanelServiceData(damageData);
                RegularServicesScreenSteps.clickServiceTypesButton();
            }
            RegularNavigationSteps.navigateToOrderSummaryScreen();
            RegularWorkOrdersSteps.saveWorkOrder();
        }

        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWOs(workOrders);

        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(
                alertText,
                "Invoice type " + DentWizardInvoiceTypes.NO_ORDER_TYPE.getInvoiceTypeName() + " doesn't support multiple Work Order types.");
        myWorkOrdersScreen.clickCancel();
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        settingsScreen.clickHomeButton();

        homescreen.clickMyInspectionsButton();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteinspectiontype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());

        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForApprove(inspNumber);
        RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();

        approveinspscreen.clickApproveButton();
        approveinspscreen.clickSignButton();
        approveinspscreen.drawApprovalSignature();
        myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsConvertToWorkOrder(String rowID,
                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        settingsScreen.clickHomeButton();

        homescreen.clickMyInspectionsButton();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteinspectiontype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(inspectionData.getInspectionPrice());

        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForApprove(inspNumber);
        RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
        approveinspscreen.clickApproveButton();
        approveinspscreen.clickSignButton();
        approveinspscreen.drawApprovalSignature();
        myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspNumber));
        myInspectionsScreen.clickOnInspection(inspNumber);
        myInspectionsScreen.clickCreateWOButton();

        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();

        Assert.assertEquals(inspNumber.substring(0, 1), "E");
        Assert.assertEquals(workOrderNumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(testCaseData.getWorkOrderData().getWorkOrderPrice()));
        RegularWizardScreensSteps.clickSaveButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setCheckDuplicatesOff();
        settingsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels(String rowID,
                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCarmaxVehicleInformationRequirements(String rowID,
                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        vehicleScreen.clickChangeScreen();
        Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Stock# is required"));
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        vehicleScreen.clickChangeScreen();
        Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("RO# is required"));
        RegularVehicleInfoScreenSteps.setRoNumber(workOrderData.getVehicleInfoData().getRoNumber());
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testServiceDriveRequiresAdvisor(String rowID,
                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        homescreen.clickCustomersButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.clickChangeScreen();
        Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Advisor is required"));
        RegularVehicleInfoScreenSteps.selectAdvisor(workOrderData.getVehicleInfoData().getVehicleAdvisor());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionRequirementsInforced(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizardprotrackerrouteinspectiondertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.clickSave();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains("VIN# is required"));
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.clickSave();
        alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains("Advisor is required"));
        RegularVehicleInfoScreenSteps.selectAdvisor(inspectionData.getVehicleInfo().getVehicleAdvisor());
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsCanConvertToMultipleWorkOrders(String rowID,
                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickMyInspectionsButton();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routecanadainspectiontype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspNumber);
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(workOrderNumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspNumber);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        Assert.assertEquals(vehicleScreen.getWorkOrderNumber(), workOrderNumber);
        vehicleScreen.clickCancel();

        RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspNumber);
        String workOrderNumber2 = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertEquals(workOrderNumber2.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspNumber);

        Assert.assertEquals(myInspectionsScreen.getNumberOfWorkOrdersForIspection(), 2);
        Assert.assertTrue(myInspectionsScreen.isWorkOrderForInspectionExists(workOrderNumber2));
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber2, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testArchiveFeatureForInspections(String rowID,
                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickOnInspection(inspNumber);
        myInspectionsScreen.clickArchiveInspectionButton();
        myInspectionsScreen.clickFilterButton();
        myInspectionsScreen.clickStatusFilter();
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected(InspectionStatus.NEW.getStatus()));
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected(InspectionStatus.APPROVED.getStatus()));
        myInspectionsScreen.clickFilterStatus(InspectionStatus.NEW.getStatus());
        myInspectionsScreen.clickFilterStatus(InspectionStatus.APPROVED.getStatus());
        myInspectionsScreen.clickFilterStatus(InspectionStatus.ARCHIVED.getStatus());
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected(InspectionStatus.ARCHIVED.getStatus()));
        myInspectionsScreen.clickBackButton();
        myInspectionsScreen.clickSaveFilterDialogButton();

        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        Assert.assertEquals(myInspectionsScreen.checkFilterIsApplied(), true);
        myInspectionsScreen.clearFilter();
        myInspectionsScreen.clickSaveFilterDialogButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEvenWOLevelTechSplitForWholesaleHail(String rowID,
                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        selectedServiceDetailsScreen.selectTechniciansEvenlyView();
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());

        RegularServicesScreenSteps.switchToSelectedServices();
        for (VehiclePartData vehiclePartData : workOrderData.getMoneyServiceData().getVehicleParts()) {
            RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
            selectedServicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName(),
                    vehiclePartData);

            RegularServiceDetailsScreenSteps.setServicePriceValue(vehiclePartData.getVehiclePartPrice());

            if (vehiclePartData.getServiceDefaultTechnicians() != null) {
                RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
                    RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
                selectedServiceDetailsScreen.unselecTechnician(vehiclePartData.getServiceDefaultTechnicians().get(1).getTechnicianFullName());
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnicians().get(0),
                        vehiclePartData.getVehiclePartPrice());

                selectedServiceDetailsScreen.selecTechnician(vehiclePartData.getServiceNewTechnician().getTechnicianFullName());
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnicians().get(0),
                        vehiclePartData.getServiceDefaultTechnicians().get(0).getTechnicianPriceValue());
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceNewTechnician(),
                        vehiclePartData.getServiceNewTechnician().getTechnicianPriceValue());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            }
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        for (VehiclePartData vehiclePartData : workOrderData.getMoneyServiceData().getVehicleParts()) {
            RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(),
                    vehiclePartData.getVehiclePartTotalPrice()));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(),
                    vehiclePartData.getVehiclePartTotalPrice()));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(),
                    vehiclePartData.getVehiclePartTotalPrice()));
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(),
                    vehiclePartData.getVehiclePartTotalPrice()));
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEvenServiceLevelTechSplitForWholesaleHail(String rowID,
                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(serviceData);
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
            for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

            RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceData.getServiceDefaultTechnician(),
                    serviceData.getServiceDefaultTechnician().getTechnicianPriceValue());
            for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
                        serviceTechnician.getTechnicianPriceValue());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomWOLevelTechSplitForWholesaleHail(String rowID,
                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String fullPercentageValue = "100.00";
        final String defTechPrice = "93.50";
        final String newTechPrice = "16.50";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularVehicleInfoScreenSteps.clickTech();

        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());

        selectedServiceDetailsScreen.selectTechniciansCustomView();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                fullPercentageValue);

        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());

        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertTrue(alertText.contains("Total amount is not equal 100%"));
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician(),
                workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianPercentageValue());

        alertText = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());

        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();

        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                defTechPrice);
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(workOrderData.getVehicleInfoData().getNewTechnician(),
                newTechPrice);

        selectedServiceDetailsScreen.selectTechniciansCustomView();
        RegularServiceDetailsScreenSteps.unselectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getServiceData().getServiceNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(workOrderData.getServiceData().getServiceNewTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianCustomPercentageValue(workOrderData.getServiceData().getServiceNewTechnician(),
                workOrderData.getServiceData().getServiceNewTechnician().getTechnicianPercentageValue());

        RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(workOrderData.getServiceData().getServiceDefaultTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianCustomPercentageValue(workOrderData.getServiceData().getServiceDefaultTechnician(),
                workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPercentageValue());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomServiceLevelTechSplitForWholesaleHail(String rowID,
                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
        RegularServicesScreenSteps.switchToSelectedServices();
        for (VehiclePartData vehiclePartData : workOrderData.getMoneyServiceData().getVehicleParts()) {
            RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
            selectedServicesScreen.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName(),
                    vehiclePartData);
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            selectedServiceDetailsScreen.selectTechniciansCustomView();
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnician(),
                    workOrderData.getMoneyServiceData().getServicePrice());
            RegularServiceDetailsScreenSteps.selectServiceTechnician(vehiclePartData.getServiceNewTechnician());
            RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceNewTechnician());

            RegularServiceDetailsScreenValidations.verifyServiceTechnicianCustomPercentageValue(vehiclePartData.getServiceNewTechnician(),
                    vehiclePartData.getServiceNewTechnician().getTechnicianPercentageValue());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            Assert.assertTrue(Helpers.getAlertTextAndAccept().contains("Split amount should be equal to total amount."));
            RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceDefaultTechnician());

            RegularServiceDetailsScreenValidations.verifyServiceTechnicianCustomPercentageValue(vehiclePartData.getServiceDefaultTechnician(),
                    vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomerDiscountOnWholesaleHail(String rowID,
                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testQuickQuoteOptionForRetailHail(String rowID,
                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());
        RegularNavigationSteps.navigateToServicesScreen();
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            Assert.assertTrue(vehiclePartScreen.isNotesExists());
            Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }

        RegularPriceMatrixScreenSteps.savePriceMatrix();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomerSelfPayOptionForRetailHail(String rowID,
                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEvenWOLevelTechSplitForRetailHail(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selectTechniciansEvenlyView();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(serviceTechnician,
                    serviceTechnician.getTechnicianPercentageValue());

        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            Assert.assertEquals(vehiclePartScreen.getPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
            if (vehiclePartData.getVehiclePartPrice() != null)
                RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());

            for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
                Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                RegularVehiclePartsScreenSteps.openVehiclePartAdditionalServiceDetails(serviceData);
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                for (ServiceTechnician serviceTechnician : serviceData.getServiceDefaultTechnicians()) {
                    if (!serviceTechnician.isSelected())
                        RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceTechnician);
                }
                for (ServiceTechnician serviceTechnician : serviceData.getServiceDefaultTechnicians()) {
                    if (serviceTechnician.isSelected())
                        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
                                serviceData.getServicePrice());
                }

                if (serviceData.getServiceNewTechnicians() != null) {
                    for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians()) {
                        if (serviceTechnician.isSelected()) {
                            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
                            RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
                                    serviceData.getServicePrice());
                        }
                    }
                }
                RegularServiceDetailsScreenSteps.saveServiceDetails();
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            }
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }
        RegularPriceMatrixScreenSteps.savePriceMatrix();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEvenServiceLevelTechSplitForRetailHail(String rowID,
                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            Assert.assertEquals(vehiclePartScreen.getPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
            Assert.assertTrue(vehiclePartScreen.isNotesExists());
            for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
                Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                RegularVehiclePartsScreenSteps.openVehiclePartAdditionalServiceDetails(serviceData);

                if (serviceData.getServiceDefaultTechnicians() != null) {
                    RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                    for (ServiceTechnician serviceTechnician : serviceData.getServiceDefaultTechnicians()) {
                        RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceTechnician);
                    }
                    for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians()) {
                        RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
                        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
                                serviceTechnician.getTechnicianPriceValue());
                    }
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                }
                if (serviceData.getServicePrice() != null)
                    RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            }
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }
        RegularPriceMatrixScreenSteps.savePriceMatrix();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testDeductibleFeatureForRetailHail(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            Assert.assertEquals(vehiclePartScreen.getPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
            Assert.assertTrue(vehiclePartScreen.isNotesExists());
            for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
                Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }
        RegularPriceMatrixScreenSteps.savePriceMatrix();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL);
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testZipCodeValidatorForRetailHail(String rowID,
                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }

        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText
                .contains("Your answer doesn't match the validator 'US Zip Codes'."));
        RegularQuestionsScreenSteps.clearTextQuestion();
        RegularQuestionsScreenSteps.answerQuestion(workOrderData.getQuestionScreenData().getQuestionData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomWOLevelTechSplitForRetailHail(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String fullPercentage = "100.00";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                fullPercentage);
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertTrue(alertText.contains("Total amount is not equal 100%"));
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());

        alertText = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();


        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            Assert.assertEquals(vehiclePartScreen.getPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
            Assert.assertTrue(vehiclePartScreen.isNotesExists());
            for (ServiceTechnician serviceTechnician : vehiclePartData.getServiceDefaultTechnicians())
                Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(serviceTechnician.getTechnicianFullName()));
            if (vehiclePartData.getVehiclePartPrice() != null)
                vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                RegularVehiclePartsScreenSteps.openVehiclePartAdditionalServiceDetails(serviceData);
                BaseUtils.waitABit(1000);
                if (serviceData.getServicePrice2() != null) {
                    Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), serviceData.getServicePrice2());
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                    Helpers.acceptAlert();
                    RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(serviceData.getServiceDefaultTechnician());
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                }
                if (serviceData.getServicePrice() != null) {
                    RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                    RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                    selectedServiceDetailsScreen.selectTechniciansEvenlyView();
                    for (ServiceTechnician serviceTechnician : serviceData.getServiceDefaultTechnicians()) {
                        RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceTechnician);
                    }
                    RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceData.getServiceNewTechnician());
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                }
            }
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }
        RegularPriceMatrixScreenSteps.savePriceMatrix();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomServiceLevelTechSplitForRetailHail(String rowID,
                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());
        RegularNavigationSteps.navigateToServicesScreen();

        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(vehiclePartData.getVehiclePartSubTotalPrice()));
            Assert.assertTrue(vehiclePartScreen.isNotesExists());
            vehiclePartScreen.clickOnTechnicians();
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician());
            RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
            RegularServiceDetailsScreenSteps.selectServiceTechnician(vehiclePartData.getServiceNewTechnician());
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnician(),
                    vehiclePartData.getVehiclePartSubTotalPrice());
            RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceDefaultTechnician());
            RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(vehiclePartData.getServiceNewTechnician());
            RegularServiceDetailsScreenSteps.saveServiceDetails();

            Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                    vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
            Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                    vehiclePartData.getServiceNewTechnician().getTechnicianFullName()));
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                RegularVehiclePartsScreenSteps.openVehiclePartAdditionalServiceDetails(serviceData);
                RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
                BaseUtils.waitABit(1000);
                if (serviceData.getServicePrice2() != null) {
                    Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), serviceData.getServicePrice2());
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                    Helpers.acceptAlert();
                    RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(serviceData.getServiceDefaultTechnician());
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                }
                if (serviceData.getServicePrice() != null) {
                    RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                    RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                    RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
                    RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceData.getServiceDefaultTechnician());
                    for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians()) {
                        if (!serviceTechnician.isSelected())
                            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
                        RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(serviceTechnician);
                    }

                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                    RegularServiceDetailsScreenSteps.saveServiceDetails();
                }
            }
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomerDiscountOnRetailHail(String rowID,
                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        selectedServiceDetailsScreen.setServicePriceValue(workOrderData.getPercentageServiceData().getServicePrice());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularSelectedServicesSteps.switchToAvailableServices();
        RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getMoneyServiceData());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice(String rowID,
                                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();

        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testPhaseEnforcementForWizardProTracker(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();

        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectWOMonitor();
        Helpers.waitABit(3000);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            orderMonitorScreen.selectPanelToChangeStatus(damageData.getDamageGroupName());
            orderMonitorScreen.setCompletedPhaseStatus();
            if (damageData.getMoneyService().getVehicleParts() != null) {
                for (VehiclePartData vehiclePartData : damageData.getMoneyService().getVehicleParts())
                    RegularOrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(), vehiclePartData, OrderMonitorStatuses.COMPLETED);
            } else {
                RegularOrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(), OrderMonitorStatuses.COMPLETED);
            }
        }
        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();

        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.verifyCreateInvoiceIsActivated(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddingServicesToOnOrderBeingMonitored(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectWOMonitor();
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        final DamageData pdrDamage = workOrderData.getDamagesData().get(workOrderData.getDamagesData().size() - 1);
        orderMonitorScreen.selectPanelToChangeStatus(pdrDamage.getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();
        for (VehiclePartData vehiclePartData : pdrDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(pdrDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.COMPLETED);
        final DamageData paintDamage = workOrderData.getDamagesData().get(0);
        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.ACTIVE);

        orderMonitorScreen.clickServicesButton();
        RegularServicesScreenSteps.switchToAvailableServices();
        RegularServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
        RegularServicesScreenSteps.clickServiceTypesButton();
        RegularWizardScreensSteps.clickSaveButton();

        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.QUEUED);
        final DamageData pdrDamageNew = workOrderData.getDamageData();
        orderMonitorScreen.selectPanelToChangeStatus(pdrDamageNew.getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();
        for (VehiclePartData vehiclePartData : pdrDamageNew.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(pdrDamageNew.getMoneyService(), vehiclePartData, OrderMonitorStatuses.COMPLETED);

        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.ACTIVE);

        orderMonitorScreen.selectPanelToChangeStatus(paintDamage.getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();
        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.COMPLETED);

        final DamageData wheelsDamage = workOrderData.getDamagesData().get(workOrderData.getDamagesData().size() - 2);
        for (VehiclePartData vehiclePartData : wheelsDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(wheelsDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.ACTIVE);

        orderMonitorScreen.selectPanelToChangeStatus(wheelsDamage.getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();
        for (VehiclePartData vehiclePartData : wheelsDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(wheelsDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.COMPLETED);

        for (VehiclePartData vehiclePartData : wheelsDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(wheelsDamage.getMoneyService(), vehiclePartData, OrderMonitorStatuses.COMPLETED);

        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testStartServiceFeatureIsAccuratelyCapturingTimes(String rowID,
                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
        RegularServicesScreenSteps.clickServiceTypesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectWOMonitor();
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        RegularOrderMonitorScreenValidations.verifyServiceStatus(workOrderData.getDamageData().getMoneyService(), OrderMonitorStatuses.ACTIVE);

        orderMonitorScreen.selectPanel(workOrderData.getDamageData().getMoneyService().getServiceName());
        orderMonitorScreen.clickStartService();
        orderMonitorScreen.selectPanel(workOrderData.getDamageData().getMoneyService().getServiceName());
        Assert.assertTrue(orderMonitorScreen.isStartServiceDissapeared());
        String srvstartdate = orderMonitorScreen.getServiceStartDate().substring(0, 10);
        orderMonitorScreen.clickServiceDetailsDoneButton();
        Assert.assertEquals(orderMonitorScreen.getServiceFinishDate(workOrderData.getDamageData().getMoneyService().getServiceName()).substring(0, 10), srvstartdate);
        orderMonitorScreen.clickBackButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testQuantityDoesNotMulitplyPriceInRoutePackage(String rowID,
                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularNavigationSteps.navigateToServicesScreen();


        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testDeleteWorkOrderFunction(String rowID,
                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.deleteWorkOrder(workOrderNumber);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, false);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangingCustomerOnInspection(String rowID,
                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Abc Rental Center");

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.changeCustomerForInspection(inspNumber, wholesailCustomer);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomerWithoutEditing(wholesailCustomer.getCompany());
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreenValidations.verifyInspectionPresent(inspNumber, true);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreenValidations.verifyInspectionPresent(inspNumber, false);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testRetailHailPackageQuantityMultiplier(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        for (QuestionScreenData questionScreenData : workOrderData.getQuestionScreensData()) {
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(questionScreenData);
        }
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(workOrderData.getInsuranceCompanyData());
        RegularNavigationSteps.navigateToServicesScreen();

        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL_NO_DISCOUNT_INVOICE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCustomWOLevelSplitForRoutePackage(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
        RegularServicesScreenSteps.clickServiceTypesButton();

        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectServicePanel(damageData);
            RegularServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(damageData.getMoneyService());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceDefaultTechnicians())
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(serviceTechnician,
                        serviceTechnician.getTechnicianPriceValue());
            RegularServiceDetailsScreenSteps.selectTechniciansCustomView();

            for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceDefaultTechnicians())
                if (!serviceTechnician.isSelected())
                    RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceTechnician);

            for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians())
                if (!serviceTechnician.isSelected())
                    RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

            for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceNewTechnicians()) {
                RegularServiceDetailsScreenSteps.setTechnicianCustomPriceValue(serviceTechnician);
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianCustomPercentageValue(serviceTechnician,
                        serviceTechnician.getTechnicianPercentageValue());
            }
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEvenWOLevelSplitForRoutePackage(String rowID,
                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
        RegularServicesScreenSteps.clickServiceTypesButton();

        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectServicePanel(damageData);
            RegularServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(damageData.getMoneyService());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();

            for (ServiceTechnician serviceTechnician : damageData.getMoneyService().getServiceDefaultTechnicians())
                if (!serviceTechnician.isSelected())
                    RegularServiceDetailsScreenSteps.unselectServiceTechnician(serviceTechnician);

            RegularServiceDetailsScreenSteps.selectServiceTechnician(damageData.getMoneyService().getServiceNewTechnician());
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianCustomPercentageValue(damageData.getMoneyService().getServiceNewTechnician(),
                    damageData.getMoneyService().getServiceNewTechnician().getTechnicianPercentageValue());
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(damageData.getMoneyService().getServiceNewTechnician(),
                    damageData.getMoneyService().getServiceNewTechnician().getTechnicianPriceValue());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddingNotesToWorkOrder(String rowID,
                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String vehicleNotes = "Blue fender";
        final String invoiceseNotes = "Declined wheel work";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularWizardScreensSteps.clickNotesButton();
        RegularNotesScreenSteps.setTextNotes(vehicleNotes);
        RegularNotesScreenSteps.saveNotes();

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        RegularNotesScreenSteps.setTextNotes(invoiceseNotes);
        RegularNotesScreenSteps.saveNotes();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangingThePOOnAnInvoice(String rowID,
                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String newPO = "832710";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.selectAdvisor(workOrderData.getVehicleInfoData().getVehicleAdvisor());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
        myInvoicesScreen.changePO(newPO);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditingAnInspection(String rowID,
                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspNumber);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.selectPanelServiceData(inspectionData.getDamageData());
        RegularServicesScreenSteps.clickServiceTypesButton();
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditingWorkOrder(String rowID,
                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoScreenSteps.setRoNumber(workOrderData.getVehicleInfoData().getRoNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularServicesScreenSteps.switchToSelectedServices();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(damageData.getMoneyService().getServiceName(),
                    damageData.getMoneyService().getServicePrice2()));
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);

        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
        RegularServicesScreenSteps.clickServiceTypesButton();

        RegularServicesScreenSteps.switchToSelectedServices();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(damageData.getMoneyService().getServiceName(),
                    damageData.getMoneyService().getServicePrice2()));
        }
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getDamageData().getMoneyService().getServiceName(),
                workOrderData.getDamageData().getMoneyService().getServicePrice2()));

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditingAnInvoiceInDraft(String rowID,
                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
        invoiceInfoScreen.clickOnWO(workOrderNumber);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();

        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.
                openCustomServiceDetails(workOrderData.getDamagesData().get(workOrderData.getDamagesData().size() - 1).getMoneyService().getServiceName());
        selectedServiceDetailsScreen.removeService();
        RegularSelectedServicesSteps.switchToAvailableServices();

        for (DamageData damageData : testCaseData.getInvoiceData().getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularWorkOrdersSteps.saveWorkOrder();
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19548:Test adding a PO# to an invoice", description = "Test adding a PO# to an invoice")
    public void testAddingAPOToAnInvoice() {
        String tcname = "testAddingAPOToAnInvoice";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setInsvoicesCustomLayoutOff();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_FABRIC_SERVICE);
        servicesScreen.selectService("Tear/Burn >2\" (Fabric)");
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDR6PANEL_SUBSERVICE);
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(UtilConstants.PDR6PANEL_SUBSERVICE));
        Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
        Assert.assertEquals(orderMonitorScreen.getPanelStatus("Tear/Burn >2\" (Fabric)"), "Active");
        orderMonitorScreen.selectPanelToChangeStatus("Interior Repair");
        orderMonitorScreen.setCompletedPhaseStatus();
        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.PDR6PANEL_SUBSERVICE, "Completed");
        Assert.assertEquals(orderMonitorScreen.getPanelStatus("Tear/Burn >2\" (Fabric)"), "Completed");
        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.verifyCreateInvoiceIsActivated(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        RegularNavigationSteps.navigateToScreen("AVIS Questions");
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();

        questionsScreen.chooseAVISCode("Rental-921");
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
        myInvoicesScreen.changePO("170116");
        myInvoicesScreen.clickBackButton();
        homescreen.clickMyInvoicesButton();
        Assert.assertEquals(myInvoicesScreen.getInvoicePrice(invoiceNumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        Assert.assertTrue(myInvoicesScreen.isInvoiceHasInvoiceSharedIcon(invoiceNumber));
        Assert.assertTrue(myInvoicesScreen.isInvoiceHasInvoiceNumberIcon(invoiceNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19670:Test adding a PO# to an invoice containing multiple Work Orders", description = "Test adding a PO# to an invoice containing multiple Work Orders")
    public void testAddingPOToAnInvoiceContainingMultipleWorkOrders() {
        String tcname1 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders1";
        String tcname2 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders2";
        String tcname3 = "testAddingPOToAnInvoiceContainingMultipleWorkOrders3";

        int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
        int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);
        int testcaserow3 = ExcelUtils.getTestCaseRow(tcname3);

        final String[] vehicleparts = {"Left Rear Door", "Right Rear Door"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow1));
        String workOrderNumber1 = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow1)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularWorkOrdersSteps.saveWorkOrder();

        //Create second WO
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow2));
        String workOrderNumber2 = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));

        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.LEATHERREPAIR_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow2));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow2)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularWorkOrdersSteps.saveWorkOrder();

        //Create third WO
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow3));
        String workOrderNumber3 = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow3), ExcelUtils.getModel(testcaserow3), ExcelUtils.getYear(testcaserow3));

        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow3));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow3)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber1);
        RegularOrderMonitorScreen orderMonitorScreen = teamWorkOrdersScreen.selectWOMonitor();
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (int i = 0; i < vehicleparts.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.PDRVEHICLE_SUBSERVICE + " (" + vehicleparts[i] + ")"), "Completed");

        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber2);
        teamWorkOrdersScreen.selectWOMonitor();
        orderMonitorScreen.selectPanelToChangeStatus("Interior Repair");
        orderMonitorScreen.setCompletedPhaseStatus();
        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.LEATHERREPAIR_SUBSERVICE, "Completed");
        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber3);
        teamWorkOrdersScreen.selectWOMonitor();
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PAINT_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.BLACKOUT_SUBSERVICE, "Completed");
        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();

        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber1);
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber2);
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber3);

        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
        myInvoicesScreen.changePO("957884");
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19671:Test Copy Vehicle feature", description = "Test Copy Vehicle feature")
    public void testCopyVehicleFeature() {
        String tcname = "testCopyVehicleFeature";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.OTHER_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber, DentWizardWorkOrdersTypes.carmaxworkordertype);
        vehicleScreen.waitVehicleScreenLoaded();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19672:Test Copy Services feature", description = "Test Copy Services feature")
    public void testCopyServicesFeature() {
        String tcname1 = "testCopyServicesFeature1";
        String tcname2 = "testCopyServicesFeature2";

        int testcaserow1 = ExcelUtils.getTestCaseRow(tcname1);
        int testcaserow2 = ExcelUtils.getTestCaseRow(tcname2);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow1));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow1), ExcelUtils.getModel(testcaserow1), ExcelUtils.getYear(testcaserow1));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.BLACKOUT_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow1));

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, DentWizardWorkOrdersTypes.routeusworkordertype);
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow2));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow2), ExcelUtils.getModel(testcaserow2), ExcelUtils.getYear(testcaserow2));
        RegularNavigationSteps.navigateToServicesScreen();
        //servicesScreen.selectService(UtilConstants.PAINT_SERVICE);
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(UtilConstants.BLACKOUT_SUBSERVICE));
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(UtilConstants.BLACKOUT_SUBSERVICE), new String(PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow1)) + " x 1.00").replaceAll(" ", ""));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 24164:Test Pre-Existing Damage option", description = "Test Pre-Existing Damage option")
    public void testPreExistingDamageOption() {
        String tcname = "testPreExistingDamageOption";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left A Pillar", "Left Front Door", "Metal Sunroof", "Right Roof Rail"};

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.servicedriveinspectiondertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails("Scratch (Exterior)");
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.checkPreexistingDamage();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickOnInspection(inspectionNumber);
        myInspectionsScreen.clickCreateWOButton();
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale("1");
        RegularWorkOrdersSteps.saveWorkOrder();
        //Assert.assertEquals(myInspectionsScreen.getFirstInspectionNumberValue(), inspectionNumber);
        RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspectionNumber);
        Assert.assertEquals(vehicleScreen.getWorkOrderNumber(), workOrderNumber);
        servicesScreen.clickCancel();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWOViaSearch(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19676:Test Total Sale requirement", description = "Test Total Sale requirement")
    public void testTotalSaleRequirement() {
        String tcname = "testTotalSaleRequirement";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Hood", "Left Fender"};
        final String totalsale = "675";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackerservicedriveworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.ODORREMOVAL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.clickSave();
        String alertText = Helpers.getAlertTextAndAccept();
        orderSummaryScreen.setTotalSale(totalsale);
        Assert.assertTrue(alertText.contains("Total Sale is required."));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19678:Test package pricing for read only items", description = "Test package pricing for read only items")
    public void testPackagePricingForReadOnlyItems() {
        String tcname = "testPackagePricingForReadOnlyItems";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Left Roof Rail", "Right Fender"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
        vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_LEATHER_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CRT_2_RPR_LTHR_SUBSERVICE);
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), "-$14.48");
        selectedServiceDetailsScreen.setServiceQuantityValue("3.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CUST2PNL_SUBSERVICE);
        Assert.assertEquals(selectedServiceDetailsScreen.getAdjustmentsValue(), "-$6.55");
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        vehicleScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 19681:Test price policy for service items from Inspection through Invoice creation", description = "Test price policy for service items from Inspection through Invoice creation")
    public void testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation() {
        String tcname = "testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Front Door", "Left Rear Door", "Right Fender"};
        final String[] vehiclepartspaint = {"Hood", "Left Fender"};

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartspaint.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspectionNumber);
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.PDRVEHICLE_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)) + " x 1.00"));

        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.PAINTPANEL_SUBSERVICE, PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)) + " x 1.00"));
        RegularInspectionsSteps.saveInspection();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspectionNumber);
        Assert.assertEquals(vehicleScreen.getWorkOrderNumber(), workOrderNumber);
        servicesScreen.clickCancel();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10263:Send Multiple Emails", description = "Send Multiple Emails")
    public void testSendMultipleEmails() {

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.clickActionButton();
        myInvoicesScreen.selectInvoices(3);

        myInvoicesScreen.clickActionButton();
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
        RegularEmailScreenSteps.sendSingleEmailToAddress(UtilConstants.TEST_EMAIL);
        myInvoicesScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19683:Test Work Order Discount Override feature", description = "Test Work Order Discount Override feature")
    public void testWorkOrderDiscountOverrideFeature() {
        String tcname = "testWorkOrderDiscountOverrideFeature";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Right Fender"};


        RegularCustomersScreen customersScreen = homescreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        homescreen = customersScreen.selectCustomerWithoutEditing("Bel Air Auto Auction Inc");

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceAdjustmentsValue(), "-$28.50");
        selectedServiceDetailsScreen.clickVehiclePartsCell();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WSANDBPANEL_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        selectedServiceDetailsScreen.selectVehiclePart("Hood");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19685:Test completed RO only requirement for invoicing", description = "Test Completed RO only requirement for invoicing")
    public void testCompletedROOnlyRequirementForInvoicing() {
        String tcname = "testCompletedROOnlyRequirementForInvoicing";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Quarter Panel", "Right Roof Rail", "Trunk Lid"};
        final String[] vehiclepartswheel = {"Right Front Wheel", "Right Rear Wheel"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTTOWHOOKCOVER_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        for (int i = 0; i < vehiclepartswheel.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectEditWO();
        vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.selectNextScreen(WizardScreenTypes.ORDER_SUMMARY);
        Assert.assertFalse(orderSummaryScreen.checkApproveAndCreateInvoiceExists());
        RegularWorkOrdersSteps.saveWorkOrder();
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO));
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 10659:Test Enterprise Work Order question forms inforced", description = "Test Enterprize Work Order question forms inforced")
    public void testEnterprizeWorkOrderQuestionFormsInforced() {
        String tcname = "testEnterprizeWorkOrderQuestionFormsInforced";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.enterpriseworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        RegularNavigationSteps.navigateToScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE.getDefaultScreenTypeName());
        RegularEnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new RegularEnterpriseBeforeDamageScreen();
        enterprisebeforedamagescreen.clickSave();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains("Question 'VIN' in section 'Enterprise Before Damage' should be answered."));
        enterprisebeforedamagescreen.setVINCapture();
        enterprisebeforedamagescreen.clickSave();
        alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains("Question 'License Plate' in section 'Enterprise Before Damage' should be answered."));
        enterprisebeforedamagescreen.setLicensePlateCapture();

        RegularNavigationSteps.navigateToScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12633:Test successful email of pictures using Notes feature", description = "Test successful email of pictures using Notes feature")
    public void testSuccessfulEmailOfPicturesUsingNotesFeature() {
        String tcname = "testSuccessfulEmailOfPicturesUsingNotesFeature";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.avisworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.clickNotesButton();
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
        notesScreen.clickSaveButton();

        servicesScreen.selectService(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDR2PANEL_SUBSERVICE);
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        selectedServiceDetailsScreen.clickNotesCell();
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE.getDefaultScreenTypeName());
        RegularEnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new RegularEnterpriseBeforeDamageScreen();
        enterprisebeforedamagescreen.setVINCapture();
        enterprisebeforedamagescreen.setLicensePlateCapture();

        RegularNavigationSteps.navigateToScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderForAddingNotes(workOrderNumber);
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 3);
        notesScreen.clickSaveButton();

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        RegularNavigationSteps.navigateToScreen("AVIS Questions");
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.chooseAVISCode("Other-920");
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();
        myInvoicesScreen.clickActionButton();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickActionButton();
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
        RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
        myInvoicesScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12634:Test emailing photos in Economical Inspection", description = "Test emailing photos in Economical Inspection")
    public void testEmailingPhotosInEconomicalInspection() {
        String tcname = "testEmailingPhotosInEconomicalInspection";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.economicalinspectiondertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        final String inspNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_PHOTOS_SCREEN_CAPTION);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.makeCaptureForQuestionRegular("VIN");
        questionsScreen.swipeScreenUp();
        questionsScreen.makeCaptureForQuestionRegular("Odometer");
        questionsScreen.swipeScreenUp();
        questionsScreen.makeCaptureForQuestionRegular("License Plate Number");
        questionsScreen.swipeScreenUp();
        questionsScreen.makeCaptureForQuestionRegular("Left Front of Vehicle");
        questionsScreen.swipeScreenUp();
        questionsScreen.makeCaptureForQuestionRegular("Right Front of Vehicle");
        questionsScreen.swipeScreenUp();
        questionsScreen.makeCaptureForQuestionRegular("Right Rear of Vehicle");
        questionsScreen.swipeScreenUp();
        questionsScreen.makeCaptureForQuestionRegular("Left Rear of Vehicle");
        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectProperQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.CANADA, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails("E-Coat");
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.clickNotesCell();
        notesScreen.addNotesCapture();
        notesScreen.clickSaveButton();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        RegularNavigationSteps.navigateToScreen(UtilConstants.PRICE_MATRIX_SCREEN_CAPTION);
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickNotesButton();
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
        notesScreen.clickSaveButton();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickOnInspection(inspNumber);
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
        RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12635:Test emailing photos in Auction package", description = "Test emailing photos in Auction package")
    public void testEmailingPhotosInAuctionPackage() {
        String tcname = "testEmailingPhotosInAuctionPackage";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Hood", "Left Fender", "Right Fender", "Roof"};
        final String firstnote = "Refused paint";
        final String secondnote = "Just 4 panels";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.auctionworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        String wo = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes(firstnote);
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickNotesCell();
        notesScreen.setNotes(secondnote);
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        RegularNavigationSteps.navigateToScreen(WizardScreenTypes.QUESTIONS.getDefaultScreenTypeName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.chooseConsignor("Unknown Consignor/One Off-718");

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderForAddingNotes(wo);
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 2);
        notesScreen.clickSaveButton();

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(wo);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.AUCTION_NO_DISCOUNT_INVOICE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        myInvoicesScreen.clickActionButton();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickActionButton();
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
        RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
        myInvoicesScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12636:Test emailing photos in Service Drive package", description = "Test emailing photos in Service Drive package")
    public void testEmailingPhotosInServiceDrivePackage() {
        String tcname = "testEmailingPhotosInServiceDrivePackage";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Decklid", "Left A Pillar"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDENTREMOVEAL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();


        servicesScreen.selectService(UtilConstants.INTERIOR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.STAIN_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.setTotalSale("1");
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes("Refused paint");
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();

        myInvoicesScreen.clickActionButton();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickActionButton();
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
        RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
        myInvoicesScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 24131:Test PO# saves with active keyboard on WO summary screen", description = "Test PO# saves with active keyboard on WO summary screen")
    public void testPONumberSavesWithActiveKeyboardOnWOSummaryScreen() {
        String tcname = "testPONumberSavesWithActiveKeyboardOnWOSummaryScreen";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String poNumber = "998601";
        final String[] vehicleparts = {"Hood", "Left Quarter Panel", "Right Roof Rail"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), (PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow))));

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        invoiceInfoScreen.setPOWithoutHidingkeyboard(poNumber);
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 24132:Test Total Sale saves with active keyboard on WO summary screen", description = "Test Total Sale saves with active keyboard on WO summary screen")
    public void testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen() {
        String tcname = "testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);
        final String totalsale = "675";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.selectAdvisor(UtilConstants.TRAINING_ADVISOR);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTHOOD_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSaleWithoutHidingkeyboard(totalsale);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19673:Test Car History feature", description = "Test Car History feature")
    public void testCarHistoryFeature()
            throws Exception {
        String tcname = "testCarHistoryFeature";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
        vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIORPLASTIC_SERVICE);
        servicesScreen.selectService(UtilConstants.SCRTCH_1_SECTPLSTC_SERVICE);
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        String invoiceNumberber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularCarHistoryScreen carHistoryScreen = new RegularCarHistoryScreen();
        carHistoryScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateTocarHistoryScreen();
        carHistoryScreen.searchCar("887340");
        String strtocompare = ExcelUtils.getYear(testcaserow) + ", " + ExcelUtils.getMake(testcaserow) + ", " + ExcelUtils.getModel(testcaserow);
        Assert.assertEquals(carHistoryScreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
        Assert.assertEquals(carHistoryScreen.getFirstCarHistoryDetailsInTable(), strtocompare);
        RegularCarHistoryWOsAndInvoicesScreen carHistoryWOsAndInvoicesScreen = carHistoryScreen.clickFirstCarHistoryInTable();
        RegularMyInvoicesScreen myInvoicesScreen = carHistoryWOsAndInvoicesScreen.clickCarHistoryInvoices();
        Assert.assertTrue(myInvoicesScreen.myInvoicesIsDisplayed());
        myInvoicesScreen.clickBackButton();
        carHistoryWOsAndInvoicesScreen = new RegularCarHistoryWOsAndInvoicesScreen();
        carHistoryWOsAndInvoicesScreen.clickBackButton();

        carHistoryScreen.clickSwitchToWeb();
        Assert.assertEquals(carHistoryScreen.getFirstCarHistoryValueInTable(), ExcelUtils.getVIN(testcaserow));
        Assert.assertEquals(carHistoryScreen.getFirstCarHistoryDetailsInTable(), strtocompare);
        carHistoryWOsAndInvoicesScreen = carHistoryScreen.clickFirstCarHistoryInTable();
        carHistoryWOsAndInvoicesScreen.clickCarHistoryInvoices();

        Assert.assertTrue(myInvoicesScreen.teamInvoicesIsDisplayed());
        Assert.assertTrue(myInvoicesScreen.myInvoiceExists(invoiceNumberber));
        myInvoicesScreen.clickBackButton();
        carHistoryWOsAndInvoicesScreen = new RegularCarHistoryWOsAndInvoicesScreen();
        carHistoryWOsAndInvoicesScreen.clickBackButton();
        carHistoryScreen.clickHomeButton();
    }
}

package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
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
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios10_client.utils.UtilConstants;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
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
    public void setUpSuite() {
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
        settingsScreen.clickHomeButton();
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
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected(InspectionStatus.NEW));
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected(InspectionStatus.APPROVED));
        myInspectionsScreen.clickFilterStatus(InspectionStatus.NEW);
        myInspectionsScreen.clickFilterStatus(InspectionStatus.APPROVED);
        myInspectionsScreen.clickFilterStatus(InspectionStatus.ARCHIVED);
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected(InspectionStatus.ARCHIVED));
        myInspectionsScreen.clickBackButton();
        myInspectionsScreen.clickSaveFilterDialogButton();

        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        Assert.assertTrue(myInspectionsScreen.checkFilterIsApplied(),"FilterIsApplied is not true");
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
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.selectTechniciansEvenlyView();
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

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
                RegularServiceDetailsScreenSteps.unselectServiceTechnician(vehiclePartData.getServiceDefaultTechnicians().get(1));
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(vehiclePartData.getServiceDefaultTechnicians().get(0),
                        vehiclePartData.getVehiclePartPrice());

                RegularServiceDetailsScreenSteps.selectServiceTechnician(vehiclePartData.getServiceNewTechnician());
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

        final String defTechPrice = "93.50";
        final String newTechPrice = "16.50";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());

        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());

        RegularServiceDetailsScreenSteps.saveServiceDetails();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TOTAL_AMAUNT_NOT_EQUAL100);
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician(),
                workOrderData.getVehicleInfoData().getNewTechnician().getTechnicianPercentageValue());

        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();
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

        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
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
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularAssignTechniciansSteps.clickTechniciansCell();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selectTechniciansEvenlyView();
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(serviceTechnician,
                    serviceTechnician.getTechnicianPercentageValue());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(serviceTechnician);

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
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        Assert.assertEquals(claimScreen.getDeductibleValue(), workOrderData.getInsuranceCompanyData().getDeductible());

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

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreenSteps.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToVehicleInfoScreen();

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();

        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TOTAL_AMAUNT_NOT_EQUAL100);
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

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
                    RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
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
                    RegularServiceDetailsScreenSteps.selectTechniciansEvenlyView();
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
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getPercentageServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
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
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO, workOrderNumber));
        RegularNavigationSteps.navigateBackScreen();
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
                    RegularOrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.COMPLETED);
            } else {
                RegularOrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(), OrderMonitorServiceStatuses.COMPLETED);
            }
        }
        RegularNavigationSteps.navigateBackScreen();

        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.verifyCreateInvoiceIsActivated(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
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
            RegularOrderMonitorScreenValidations.verifyServiceStatus(pdrDamage.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.COMPLETED);
        final DamageData paintDamage = workOrderData.getDamagesData().get(0);
        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.ACTIVE);

        orderMonitorScreen.clickServicesButton();
        RegularServicesScreenSteps.switchToAvailableServices();
        RegularServicesScreenSteps.selectPanelServiceData(workOrderData.getDamageData());
        RegularServicesScreenSteps.clickServiceTypesButton();
        RegularWizardScreensSteps.clickSaveButton();

        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.QUEUED);
        final DamageData pdrDamageNew = workOrderData.getDamageData();
        orderMonitorScreen.selectPanelToChangeStatus(pdrDamageNew.getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();
        for (VehiclePartData vehiclePartData : pdrDamageNew.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(pdrDamageNew.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.COMPLETED);

        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.ACTIVE);

        orderMonitorScreen.selectPanelToChangeStatus(paintDamage.getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();
        for (VehiclePartData vehiclePartData : paintDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(paintDamage.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.COMPLETED);

        final DamageData wheelsDamage = workOrderData.getDamagesData().get(workOrderData.getDamagesData().size() - 2);
        for (VehiclePartData vehiclePartData : wheelsDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(wheelsDamage.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.ACTIVE);

        orderMonitorScreen.selectPanelToChangeStatus(wheelsDamage.getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();
        for (VehiclePartData vehiclePartData : wheelsDamage.getMoneyService().getVehicleParts())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(wheelsDamage.getMoneyService(), vehiclePartData, OrderMonitorServiceStatuses.COMPLETED);

        RegularNavigationSteps.navigateBackScreen();
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
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
        RegularOrderMonitorScreenValidations.verifyServiceStatus(workOrderData.getDamageData().getMoneyService(), OrderMonitorServiceStatuses.ACTIVE);

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
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();
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
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectTechniciansCustomView();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.setTechnicianCustomPercentageValue(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

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
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getDamagesData().get(workOrderData.getDamagesData().size() - 1).getMoneyService().getServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddingAPOToAnInvoice(String rowID,
                                            String description, JSONObject testData) {

        final String secondOrderMonitorPanel = "Interior Repair";

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setInsvoicesCustomLayoutOff();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackeravisworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularServicesScreenSteps.switchToSelectedServices();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelected(damageData.getMoneyService().getServiceName(), true);
        }
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanelToChangeStatus(workOrderData.getDamagesData().get(0).getDamageGroupName());
        orderMonitorScreen.setCompletedPhaseStatus();

        RegularOrderMonitorScreenValidations.verifyServiceStatus(workOrderData.getDamagesData().get(0).getMoneyService(),
                OrderMonitorServiceStatuses.COMPLETED);
        RegularOrderMonitorScreenValidations.verifyServiceStatus(workOrderData.getDamagesData().get(1).getMoneyService(),
                OrderMonitorServiceStatuses.ACTIVE);
        orderMonitorScreen.selectPanelToChangeStatus(secondOrderMonitorPanel);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularOrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(),
                    OrderMonitorServiceStatuses.COMPLETED);
        }

        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.verifyCreateInvoiceIsActivated(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        final InvoiceData invoiceData = testCaseData.getInvoiceData();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(invoiceData.getQuestionScreenData());
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
        myInvoicesScreen.changePO(invoiceData.getPoNumber());
        myInvoicesScreen.clickBackButton();
        homescreen.clickMyInvoicesButton();
        Assert.assertEquals(myInvoicesScreen.getInvoicePrice(invoiceNumber), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        Assert.assertTrue(myInvoicesScreen.isInvoiceHasInvoiceSharedIcon(invoiceNumber));
        Assert.assertTrue(myInvoicesScreen.isInvoiceHasInvoiceNumberIcon(invoiceNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddingPOToAnInvoiceContainingMultipleWorkOrders(String rowID,
                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<WorkOrderData> workOrdersData = testCaseData.getWorkOrdersData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        for (WorkOrderData workOrderData : workOrdersData ) {
            RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
            RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
            RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
            workOrderData.setWorkOrderID(RegularVehicleInfoScreenSteps.getWorkOrderNumber());
            RegularNavigationSteps.navigateToServicesScreen();
            for (DamageData damageData : workOrderData.getDamagesData()) {
                RegularServicesScreenSteps.selectPanelServiceData(damageData);
                RegularServicesScreenSteps.clickServiceTypesButton();
            }
            RegularWorkOrdersSteps.saveWorkOrder();
        }
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        for (WorkOrderData workOrderData : workOrdersData ) {
            RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderData.getWorkOrderID());
            RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
            for (DamageData damageData : workOrderData.getDamagesData()) {
                orderMonitorScreen.selectPanelToChangeStatus(damageData.getOrderPanelName());
                orderMonitorScreen.setCompletedPhaseStatus();
                if (damageData.getMoneyService().getVehicleParts() !=null) {
                    for (VehiclePartData vehiclePartData : damageData.getMoneyService().getVehicleParts()) {
                        RegularOrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(), vehiclePartData,
                                OrderMonitorServiceStatuses.COMPLETED);
                    }
                } else {
                    RegularOrderMonitorScreenValidations.verifyServiceStatus(damageData.getMoneyService(),
                            OrderMonitorServiceStatuses.COMPLETED);
                }
            }
            RegularNavigationSteps.navigateBackScreen();
        }

        for (WorkOrderData workOrderData : workOrdersData ) {
            teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderData.getWorkOrderID());
        }

        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
        myInvoicesScreen.changePO(testCaseData.getInvoiceData().getPoNumber());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCopyVehicleFeature(String rowID,
                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrderNumber, DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCopyServicesFeature(String rowID,
                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String newVIN = "1G8AZ52F23Z186658";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrderNumber, DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(newVIN);
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(damageData.getMoneyService().getServiceName()));
            Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(damageData.getMoneyService().getServiceName()),
                    (PricesCalculations.getPriceRepresentation(damageData.getMoneyService().getServicePrice()) + " x 1.00"));
        }
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testPreExistingDamageOption(String rowID,
                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.servicedriveinspectiondertype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        RegularVehicleInfoScreenSteps.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickOnInspection(inspectionNumber);
        myInspectionsScreen.clickCreateWOButton();
        final WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspectionNumber);
        Assert.assertEquals(RegularVehicleInfoScreenSteps.getWorkOrderNumber(), workOrderNumber);
        RegularWizardScreensSteps.cancelWizard();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWOViaSearch(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testTotalSaleRequirement(String rowID,
                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizardprotrackerservicedriveworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        RegularVehicleInfoScreenSteps.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.clickSave();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains("Total Sale is required."));
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testPackagePricingForReadOnlyItems(String rowID,
                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoScreenSteps.setRoNumber(workOrderData.getVehicleInfoData().getRoNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectServicePanel(damageData);
            RegularServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(damageData.getMoneyService());
            RegularServiceDetailsScreenValidations.verifyServiceDetailsAdjustmentValue(damageData.getMoneyService().getServiceAdjustment().getAdjustmentTotalAmaunt());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServicesScreenSteps.clickServiceTypesButton();
            RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(damageData.getMoneyService().getServicePrice2()));
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testPricePolicyForServiceItemsFromInspectionThroughInvoiceCreation(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : inspectionData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspectionNumber);
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (DamageData damageData : inspectionData.getDamagesData())
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(damageData.getMoneyService().getServiceName(),
                    PricesCalculations.getPriceRepresentation(damageData.getMoneyService().getServicePrice()) + " x 1.00"));

        RegularInspectionsSteps.saveInspection();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspectionNumber);
        Assert.assertEquals(RegularVehicleInfoScreenSteps.getWorkOrderNumber(), workOrderNumber);
        RegularWizardScreensSteps.cancelWizard();

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSendMultipleEmails(String rowID,
                                       String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWorkOrderDiscountOverrideFeature(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Bel Air Auto Auction Inc");

        RegularCustomersScreen customersScreen = homescreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        homescreen = customersScreen.selectCustomerWithoutEditing(wholesailCustomer.getCompany());

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectServicePanel(damageData);
            RegularServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(damageData.getMoneyService());
            if (damageData.getMoneyService().getServiceAdjustment() != null)
                RegularServiceDetailsScreenValidations.verifyServiceDetailsAdjustmentValue(damageData.getMoneyService().getServiceAdjustment().getAdjustmentTotalAmaunt());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServicesScreenSteps.clickServiceTypesButton();
            RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(damageData.getMoneyService().getServicePrice2()));
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(testCaseData.getInvoiceData().getInvoiceTotal()));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCompletedROOnlyRequirementForInvoicing(String rowID,
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
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectEditWO();
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertFalse(orderSummaryScreen.checkApproveAndCreateInvoiceExists());
        RegularWorkOrdersSteps.saveWorkOrder();
        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_CANNOT_CREATE_INVOICE_NOT_COMPLETED_RO, workOrderNumber));
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEnterprizeWorkOrderQuestionFormsInforced(String rowID,
                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.enterpriseworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSuccessfulEmailOfPicturesUsingNotesFeature(String rowID,
                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.avisworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularWizardScreensSteps.clickNotesButton();
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
        RegularNotesScreenSteps.saveNotes();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWizardScreensSteps.clickNotesButton();
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(2);
        RegularNotesScreenSteps.saveNotes();

        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectServicePanel(damageData);
            RegularServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
            RegularServiceDetailsScreenValidations.verifyServicePriceValue(damageData.getMoneyService().getServicePrice());
            RegularServiceDetailsScreenSteps.clickNotes();
            RegularNotesScreenSteps.addImageNote();
            RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
            RegularNotesScreenSteps.saveNotes();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToScreen(WizardScreenTypes.ENTERPRISE_BEFORE_DAMAGE.getDefaultScreenTypeName());
        RegularEnterpriseBeforeDamageScreen enterprisebeforedamagescreen = new RegularEnterpriseBeforeDamageScreen();
        enterprisebeforedamagescreen.setVINCapture();
        enterprisebeforedamagescreen.setLicensePlateCapture();

        RegularNavigationSteps.navigateToScreen(UtilConstants.ENTERPRISE_AFTER_REPAIR_SCREEN_CAPTION);
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderForAddingNotes(workOrderNumber);
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(3);
        RegularNotesScreenSteps.saveNotes();

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        final InvoiceData invoiceData = testCaseData.getInvoiceData();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(invoiceData.getQuestionScreenData());

        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
        RegularNotesScreenSteps.saveNotes();
        myInvoicesScreen.clickActionButton();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickActionButton();
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
        RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
        myInvoicesScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEmailingPhotosInEconomicalInspection(String rowID,
                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.economicalinspectiondertype);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularWizardScreensSteps.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.addNotesCapture();
        Assert.assertEquals(notesScreen.getNumberOfAddedPhotos(), 1);
        notesScreen.clickSaveButton();

        RegularNavigationSteps.navigateToClaimScreen();
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
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails("E-Coat");
        RegularServiceDetailsScreenSteps.setServicePriceValue("20");
        RegularWizardScreensSteps.clickNotesButton();
        notesScreen.addNotesCapture();
        notesScreen.clickSaveButton();
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularNavigationSteps.navigateToScreen(UtilConstants.PRICE_MATRIX_SCREEN_CAPTION);
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix("Roof");
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEmailingPhotosInAuctionPackage(String rowID,
                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String firstnote = "Refused paint";
        final String secondnote = "Just 4 panels";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.auctionworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularWizardScreensSteps.clickNotesButton();
        RegularNotesScreenSteps.setTextNotes(firstnote);
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
        RegularNotesScreenSteps.saveNotes();
        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectServicePanel(damageData);
            RegularServicesScreenSteps.openCustomServiceDetails(damageData.getMoneyService().getServiceName());
            RegularServiceDetailsScreenSteps.setServiceDetailsData(damageData.getMoneyService());
            RegularServiceDetailsScreenSteps.clickNotes();
            RegularNotesScreenSteps.setTextNotes(secondnote);
            RegularNotesScreenSteps.addImageNote();
            RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
            RegularNotesScreenSteps.saveNotes();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServicesScreenSteps.clickServiceTypesButton();
        }

        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderForAddingNotes(workOrderNumber);
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(2);
        RegularNotesScreenSteps.saveNotes();

        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEmailingPhotosInServiceDrivePackage(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String notesTxt = "Refused paint";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
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
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);

        RegularNotesScreenSteps.setTextNotes(notesTxt);
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
        RegularNotesScreenSteps.saveNotes();

        myInvoicesScreen.clickActionButton();
        myInvoicesScreen.selectInvoice(invoiceNumber);
        myInvoicesScreen.clickActionButton();
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.SEND_EMAIL);
        RegularEmailScreenSteps.sendEmailToAddress(UtilConstants.TEST_EMAIL);
        myInvoicesScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testPONumberSavesWithActiveKeyboardOnWOSummaryScreen(String rowID,
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

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        invoiceInfoScreen.setPOWithoutHidingkeyboard(testCaseData.getInvoiceData().getPoNumber());
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testTotalSaleSavesWithActiveKeyboardOnWOSummaryScreen(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.selectAdvisor(UtilConstants.TRAINING_ADVISOR);
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            RegularServicesScreenSteps.clickServiceTypesButton();
        }
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSaleWithoutHidingkeyboard(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCarHistoryFeature(String rowID,
                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.setStockNumber(workOrderData.getVehicleInfoData().getStockNumber());
        RegularVehicleInfoScreenSteps.setRoNumber(workOrderData.getVehicleInfoData().getRoNumber());
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
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        String invoiceNumberber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularCarHistoryScreen carHistoryScreen = new RegularCarHistoryScreen();
        carHistoryScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateTocarHistoryScreen();
        carHistoryScreen.searchCar("887340");
        String strtocompare = workOrderData.getVehicleInfoData().getVehicleYear() + ", " +
                workOrderData.getVehicleInfoData().getVehicleMake() + ", " + workOrderData.getVehicleInfoData().getVehicleModel();
        Assert.assertEquals(carHistoryScreen.getFirstCarHistoryValueInTable(), workOrderData.getVehicleInfoData().getVINNumber());
        Assert.assertEquals(carHistoryScreen.getFirstCarHistoryDetailsInTable(), strtocompare);
        RegularCarHistoryWOsAndInvoicesScreen carHistoryWOsAndInvoicesScreen = carHistoryScreen.clickFirstCarHistoryInTable();
        RegularMyInvoicesScreen myInvoicesScreen = carHistoryWOsAndInvoicesScreen.clickCarHistoryInvoices();
        Assert.assertTrue(myInvoicesScreen.myInvoicesIsDisplayed());
        myInvoicesScreen.clickBackButton();
        carHistoryWOsAndInvoicesScreen = new RegularCarHistoryWOsAndInvoicesScreen();
        carHistoryWOsAndInvoicesScreen.clickBackButton();

        carHistoryScreen.clickSwitchToWeb();
        Assert.assertEquals(carHistoryScreen.getFirstCarHistoryValueInTable(), workOrderData.getVehicleInfoData().getVINNumber());
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

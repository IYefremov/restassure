package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
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
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularAvailableServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyInvoicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyWorkOrdersScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularVehicleInfoValidations;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.DentWizardInspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.DentWizardInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.DentWizardWorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
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


        //servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickSettingsButton();
        settingsScreen = new RegularSettingsScreen();
        settingsScreen.setCheckDuplicatesOff();
        settingsScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 10455:Test turning multiple Work Orders into a single Invoice", description = "Test turning multiple Work Orders into a single Invoice")
    public void testTurningMultipleWorkOrdersIntoASingleInvoice() {
        String tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice1";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Left Front Door",
                "Roof"};
        final String[] vehiclepartswheels = {"Left Front Wheel",
                "Right Front Wheel"};

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setInsvoicesCustomLayoutOff();
        settingsScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));

        String wo1 = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
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
        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartswheels.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheels[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();

        // ==================Create second WO=============
        tcname = "testTurningMultipleWorkOrdersIntoASingleInvoice2";
        testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts2 = {"Hood", "Roof", "Trunk Lid"};
        final String[] vehiclepartspaints = {"Front Bumper", "Rear Bumper"};

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        String wo2 = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts2.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$41.66");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$41.67");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$41.67");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartspaints.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaints[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen
                .setTechnicianCustomPriceValue(UtilConstants.technicianA, "165");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB,
                "50");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
                "$165.00");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB),
                "$50.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        final String[] wos = {wo1, wo2};
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWOs(wos);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(wo1));
        Assert.assertTrue(invoiceInfoScreen.isWOSelected(wo2));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(wo1, false);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(wo2, false);
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        final String wosvalue = wo1 + ", " + wo2;
        Assert.assertEquals(wosvalue, myInvoicesScreen.getWOsForInvoice(invoiceNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10651:Test same Order Type required for turning multiple Work Orders into a single Invoice", description = "Test same Order Type required for turning multiple Work Orders into a single Invoice")
    public void testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice() {
        String tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice1";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Roof"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        String wo1 = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        for (int i = 0; i <= vehicleparts.length; i++) {
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();

        // ==================Create second WO=============
        tcname = "testSameOrderTypeRequiredForTurningMultipleWorkOrdersIntoASingleInvoice2";
        testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts2 = {"Hood"};

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        String wo2 = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts2.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
        }
        for (int i = 0; i <= vehicleparts2.length; i++) {
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        final String[] wos = {wo1, wo2};
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickCreateInvoiceIconForWOs(wos);

        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertEquals(
                alertText,
                "Invoice type " + DentWizardInvoiceTypes.NO_ORDER_TYPE.getInvoiceTypeName() + " doesn't support multiple Work Order types.");
        myWorkOrdersScreen.clickCancel();
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(testName = "Test Case 10652:Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker", description = "Test Approval needed before Inspections can be converted to Work Order for WizardPro Tracker")
    public void testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker() {
        String tcname = "testApprovalNeededBeforeInspectionsCanBeConvertedToWorkOrderForWizardProTracker";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Left Front Door",
                "Left Quarter Panel"};

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        settingsScreen.clickHomeButton();

        homescreen.clickMyInspectionsButton();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteinspectiontype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        final String inspNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
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

    @Test(testName = "Test Case 10653:Test Inspections convert to Work Order", description = "Test Inspections convert to Work Order")
    public void testInspectionsConvertToWorkOrder() {
        String tcname = "testInspectionsConvertToWorkOrder";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Right Fender", "Right Roof Rail",
                "Right Rear Door"};

        final String[] vehicleparts2 = {"Left Mirror", "Right Mirror"};

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        settingsScreen.clickHomeButton();

        homescreen.clickMyInspectionsButton();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizprotrackerrouteinspectiontype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        String inpnumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
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
        Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts2.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts2[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getSubTotalAmaunt(), "$930.00");

        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForApprove(inpnumber);
        RegularApproveInspectionsScreen approveinspscreen = new RegularApproveInspectionsScreen();
        approveinspscreen.clickApproveButton();
        approveinspscreen.clickSignButton();
        approveinspscreen.drawApprovalSignature();
        myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inpnumber));
        myInspectionsScreen.clickOnInspection(inpnumber);
        myInspectionsScreen.clickCreateWOButton();
        vehicleScreen = new RegularVehicleScreen();
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();

        Assert.assertEquals(inpnumber.substring(0, 1), "E");
        Assert.assertEquals(workOrderNumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
        RegularWizardScreensSteps.clickSaveButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10654:Test 'Vehicle' Service does not multiply price entered when selecting multiple panels", description = "Test 'Vehicle' Service does not multiply price entered when selecting multiple panels")
    public void testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels() {
        String tcname = "testVehicleServiceDoesNotMultiplyPriceEnteredWhenSelectingMultiplePanels";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Hood", "Roof", "Trunk Lid"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickSettingsButton();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setCheckDuplicatesOff();
        settingsScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 10655:Test 'Panel' Service multiplies price entered when selecting multiple panels", description = "Test 'Panel' Service multiplies price entered when selecting multiple panels")
    public void testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels() {
        String tcname = "testPanelServiceMultipliesPriceEnteredWhenSelectingMultiplePanels";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Left Front Door",
                "Left Rear Door"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10656:Test Carmax vehicle information requirements", description = "Test Carmax vehicle information requirements")
    public void testCarmaxVehicleInformationRequirements() {
        String tcname = "testCarmaxVehicleInformationRequirements";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.clickChangeScreen();
        Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Stock# is required"));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
        vehicleScreen.clickChangeScreen();
        Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("RO# is required"));
        vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10657:Test Service Drive requires Advisor", description = "Test Service Drive requires Advisor")
    public void testServiceDriveRequiresAdvisor() {
        String tcname = "testServiceDriveRequiresAdvisor";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        homescreen.clickCustomersButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.clickChangeScreen();
        Assert.assertTrue(vehicleScreen.clickSaveWithAlert().contains("Advisor is required"));
        vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10658:Test Inspection requirments inforced", description = "Test Inspection requirements inforced")
    public void testInspectionRequirementsInforced() {
        String tcname = "testInspectionRequirementsInforced";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.wizardprotrackerrouteinspectiondertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.clickSave();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains("VIN# is required"));
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.clickSave();
        alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText.contains("Advisor is required"));
        vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10663:Test Inspections can convert to multiple Work Orders", description = "Test Inspections can convert to multiple Work Orders")
    public void testInspectionsCanConvertToMultipleWorkOrders() {
        String tcname = "testInspectionsCanConvertToMultipleWorkOrders";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickMyInspectionsButton();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routecanadainspectiontype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        final String inspNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.INTERIORBURNS_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspNumber);
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        ;
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(workOrderNumber.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.selectShowWOsMenuForInspection(inspNumber);
        vehicleScreen = new RegularVehicleScreen();
        Assert.assertEquals(vehicleScreen.getWorkOrderNumber(), workOrderNumber);
        servicesScreen.clickCancel();

        RegularMyInspectionsSteps.selectInspectionForCreatingWO(inspNumber);
        String workOrderNumber2 = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        Assert.assertEquals(workOrderNumber2.substring(0, 1), "O");
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
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

    @Test(testName = "Test Case 10665:Test Archive feature for Inspections", description = "Test Archive feature for Inspections")
    public void testArchiveFeatureForInspections() {
        String tcname = "testArchiveFeatureForInspections";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        final String inspNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.OTHER_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickOnInspection(inspNumber);
        myInspectionsScreen.clickArchiveInspectionButton();
        myInspectionsScreen.clickFilterButton();
        myInspectionsScreen.clickStatusFilter();
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected("New"));
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected("Approved"));
        myInspectionsScreen.clickFilterStatus("New");
        myInspectionsScreen.clickFilterStatus("Approved");
        myInspectionsScreen.clickFilterStatus("Archived");
        Assert.assertTrue(myInspectionsScreen.checkFilterStatusIsSelected("Archived"));
        myInspectionsScreen.clickBackButton();
        myInspectionsScreen.clickSaveFilterDialogButton();

        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        Assert.assertEquals(myInspectionsScreen.checkFilterIsApplied(), true);
        myInspectionsScreen.clearFilter();
        myInspectionsScreen.clickSaveFilterDialogButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 11731:Test even WO level tech split for Wholesale Hail", description = "Test even WO level tech split for Wholesale Hail")
    public void testEvenWOLevelTechSplitForWholesaleHail() {
        String tcname = "testEvenWOLevelTechSplitForWholesaleHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left A Pillar", "Left Fender",
                "Left Rear Door", "Roof"};


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
        vehicleScreen.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.selectTechniciansEvenlyView();
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);

        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(UtilConstants.FIXPRICE_SERVICE));
        selectedServicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
        Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
                vehicleparts[0]);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$0.00 x 1.00"));

        selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
        Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
                vehicleparts[1]);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianB));
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianNotSelected(UtilConstants.technicianB));
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
                PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$70.00");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$70.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$0.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00"));

        selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
        Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
                vehicleparts[2]);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianB));
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianNotSelected(UtilConstants.technicianB));
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianD);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$30.00");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianD), "$30.00");
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianNotSelected(UtilConstants.technicianA));
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$60.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$0.00 x 1.00"));

        selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
        Assert.assertEquals(selectedServiceDetailsScreen.getVehiclePartValue(),
                vehicleparts[3]);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$275.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$105.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$140.00 x 1.00"));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.FIXPRICE_SERVICE, "$60.00 x 1.00"));

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 11732:Test even service level tech split for Wholesale Hail", description = "Test even service level tech split for Wholesale Hail")
    public void testEvenServiceLevelTechSplitForWholesaleHail() {
        String tcname = "testEvenServiceLevelTechSplitForWholesaleHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Hood", "Right Quarter Panel",
                "Sunroof"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$47.50");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$47.50");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_TOTAL_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
                "$175.00");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB),
                "$175.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen = new RegularServicesScreen();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 11733:Test Custom WO level tech split for wholesale Hail", description = "Test Custom WO level tech split for wholesale Hail")
    public void testCustomWOLevelTechSplitForWholesaleHail() {
        String tcname = "testCustomWOLevelTechSplitForWholesaleHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Cowl, Other", "Left Fender",
                "Trunk Lid"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
        vehicleScreen.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
                "%100.00");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
                "%85.00");

        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertTrue(alertText.contains("Total amount is not equal 100%"));
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "15");

        alertText = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_ND_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), "$93.50");
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$16.50");
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "50");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%45.45");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "60");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%54.55");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen = new RegularServicesScreen();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 11734:Test Custom service level tech split for wholesale Hail", description = "Test Custom service level tech split for wholesale Hail")
    public void testCustomServiceLevelTechSplitForWholesaleHail() {
        String tcname = "testCustomServiceLevelTechSplitForWholesaleHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Left Quarter Panel", "Right Rear Door",
                "Trunk Lid"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));

        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 0);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "45");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%36.00");
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertTrue(alertText.contains("Split amount should be equal to total amount."));
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "80");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%64.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 1);
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "25");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%20.00");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "100");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%80.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 2);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "50");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%40.00");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "75");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%60.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        selectedServicesScreen.openServiceDetailsByIndex(UtilConstants.FIXPRICE_SERVICE, 3);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "30");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%24.00");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "95");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%76.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen = new RegularServicesScreen();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 11735:Test Customer Discount on Wholesale Hail", description = "Test Customer Discount on Wholesale Hail")
    public void testCustomerDiscountOnWholesaleHail() {
        String tcname = "testCustomerDiscountOnWholesaleHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Hood", "Roof", "Trunk Lid"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wholesailhailworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails("Customer Discount");
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen = new RegularServicesScreen();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10668:Test Quick Quote option for Retail Hail", description = "Test Quick Quote option for Retail Hail")
    public void testQuickQuoteOptionForRetailHail() {
        String tcname = "testQuickQuoteOptionForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.clickSave();
        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(
                alertText.contains("Question 'Estimate Conditions' in section 'Hail Info' should be answered."));
        questionsScreen = new RegularQuestionsScreen();
        questionsScreen.selectOutsideQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
        Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
        RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Quick Quote");
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
        vehiclePartScreen.setPrice(ExcelUtils.getServicePrice(testcaserow));

        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        //vehiclePartScreen.saveVehiclePart();
        vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QUICK_QUOTE_SIZE, PriceMatrixScreen.QUICK_QUOTE_SEVERITY);
        vehiclePartScreen.setPrice(ExcelUtils.getServicePrice3(testcaserow));
        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        priceMatrixScreen.clickSave();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10733:Test Customer self-pay option for Retail Hail", description = "Test Customer self-pay option for Retail Hail")
    public void testCustomerSelfPayOptionForRetailHail() {
        String tcname = "testCustomerSelfPayOptionForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOutsideQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompany(ExcelUtils.getInsuranceCompany(retailhaildatarow));
        claimScreen.clickChangeScreen();
        String alertText = claimScreen.clickSaveWithAlert();
        Assert.assertTrue(alertText.contains("Claim# is required."));
        claimScreen.setClaim(ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.HAIL_PDR_NON_CUSTOMARY_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.answerQuestion("DEALER");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10734:Test Even WO level tech split for Retail Hail", description = "Test Even WO level tech split for Retail Hail")
    public void testEvenWOLevelTechSplitForRetailHail() {
        String tcname = "testEvenWOLevelTechSplitForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        selectedServiceDetailsScreen.selectTechniciansEvenlyView();
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
                "%33.34");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB),
                "%33.33");
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianC),
                "%33.33");
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOtherQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
        Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
        RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.HEAVY_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianB));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianC));

        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianC);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen.saveVehiclePart();
        vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.NKL_SIZE, PriceMatrixScreen.SEVERE_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), "$0.00");
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianB));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianC));

        vehiclePartScreen.setPrice(ExcelUtils.getServicePrice2(testcaserow));
        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianC);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianD);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianD), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm2(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10735:Test even service level tech split for Retail Hail", description = "Test even service level tech split for Retail Hail")
    public void testEvenServiceLevelTechSplitForRetailHail() {
        String tcname = "testEvenServiceLevelTechSplitForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOtherQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
        Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
        RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));

        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);

        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);

        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), "$60.00");

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen.saveVehiclePart();

        vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.HLF_SIZE, PriceMatrixScreen.LIGHT_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));

        vehiclePartScreen.clickOnTechnicians();
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount3(retailhaildatarow));
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 10736:Test deductible feature for Retail Hail", description = "Test deductible feature for Retail Hail")
    public void testDeductibleFeatureForRetailHail() {
        String tcname = "testDeductibleFeatureForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOtherQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));
        claimScreen.setDeductible("50");
        Assert.assertEquals(
                claimScreen
                        .getDeductibleValue(), "50.00");

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
        Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
        RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL);
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(testName = "Test Case 10737:Test Zip Code validator for Retail Hail", description = "Test Zip Code validator for Retail Hail")
    public void testZipCodeValidatorForRetailHail() {
        String tcname = "testZipCodeValidatorForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);
        final String validzip = "83707";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOutsideQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        String alertText = Helpers.getAlertTextAndAccept();
        Assert.assertTrue(alertText
                .contains("Your answer doesn't match the validator 'US Zip Codes'."));
        questionsScreen.clearZip();
        questionsScreen.setRegularSetFieldValue((IOSElement) DriverBuilder.getInstance().getAppiumDriver().findElementByAccessibilityId("Owner Zip_TextView"), validzip);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 11727:Test Custom WO level tech split for Retail Hail", description = "Test Custom WO level tech split for Retail Hail")
    public void testCustomWOLevelTechSplitForRetailHail() {
        String tcname = "testCustomWOLevelTechSplitForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA),
                "%100.00");

        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA,
                "70");
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertTrue(alertText.contains("Total amount is not equal 100%"));
        selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB,
                "30");
        alertText = selectedServiceDetailsScreen.saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
        vehicleScreen = new RegularVehicleScreen();
        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectProperQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
        Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
        RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.VERYLIGHT_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianB));

        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "%25.000");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Helpers.acceptAlert();
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "35.75");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianB));
        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selectTechniciansEvenlyView();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianB), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 11728:Test Custom service level tech split for Retail Hail", description = "Test Custom service level tech split for Retail Hail")
    public void testCustomServiceLevelTechSplitForRetailHail() {
        String tcname = "testCustomServiceLevelTechSplitForRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);


        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOtherQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(UtilConstants.PDRHAILUS_SERVICE);
        Assert.assertTrue(servicesScreen.priceMatricesPopupIsDisplayed());
        RegularPriceMatrixScreen priceMatrixScreen = servicesScreen.selectPriceMatrices("Hail Estimating");
        RegularVehiclePartScreen vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.QTR_SIZE, PriceMatrixScreen.MODERATE_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.isTechniciansExists());
        vehiclePartScreen.clickOnTechnicians();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        Assert.assertEquals(
                selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianA),
                PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "285");
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertTrue(alertText.contains("Split amount should be equal to total amount."));
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "40");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianB));

        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount(retailhaildatarow));
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), "%25.000");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Helpers.acceptAlert();
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "121.25");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        //vehiclePartScreen.saveVehiclePart();
        vehiclePartScreen = priceMatrixScreen.selectPriceMatrix(ExcelUtils.getPriceMatrix2(retailhaildatarow));
        vehiclePartScreen.setSizeAndSeverity(PriceMatrixScreen.DIME_SIZE, PriceMatrixScreen.MEDIUM_SEVERITY);
        Assert.assertEquals(vehiclePartScreen.getPrice(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        Assert.assertTrue(vehiclePartScreen.isNotesExists());
        Assert.assertTrue(vehiclePartScreen.isTechniciansExists());

        vehiclePartScreen.clickOnTechnicians();
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "125");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "75");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianA));
        Assert.assertTrue(vehiclePartScreen.getTechniciansValue().contains(
                UtilConstants.technicianB));

        vehiclePartScreen.clickDiscaunt(ExcelUtils.getDiscount2(retailhaildatarow));
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));

        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "100");
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "45");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12626:Test Customer Discount on Retail Hail", description = "Test Customer Discount on Retail Hail")
    public void testCustomerDiscountOnRetailHail() {
        String tcname = "testCustomerDiscountOnRetailHail";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOutsideQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails("Customer Discount");
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.switchToAvailableServicesTab();
        servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
        selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12627:Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice", description = "Test WizardPro Tracker package requirement for RO to be completed before turning into an invoice")
    public void testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice() {
        String tcname = "testWizardProTrackerPackageRequirementForROToBeCompletedBeforeTurningIntoOnInvoice";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Roof Rail", "Right Roof Rail",
                "Roof"};
        final String[] vehiclepartswheel = {"Left Front Wheel", "Left Rear Wheel"};


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
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTROCKERPANELS_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartswheel.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
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

    @Test(testName = "Test Case 12628:Test Phase Enforcement for WizardPro Tracker", description = "Test Phase Enforcement for WizardPro Tracker")
    public void testPhaseEnforcementForWizardProTracker() {
        String tcname = "testPhaseEnforcementForWizardProTracker";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Left Front Door",
                "Left Quarter Panel"};
        final String[] vehiclepartswheel = {"Left Front Wheel", "Left Rear Wheel"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

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
        servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartswheel.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectWOMonitor();
        Helpers.waitABit(3000);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (int i = 0; i < vehicleparts.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.PDRPANEL_SUBSERVICE + " (" + vehicleparts[i] + ")"), "Completed");

        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE, "Active");

        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PAINT_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.PAINTDOORHANDLE_SUBSERVICE, "Completed");

        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.WHEELS_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (int i = 0; i < vehiclepartswheel.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.WHEEL_SUBSERVICE + " (" + vehiclepartswheel[i] + ")"), "Completed");

        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();

        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.verifyCreateInvoiceIsActivated(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 12630:Test adding services to an order being monitored", description = "Test adding services to an order being monitored")
    public void testAddingServicesToOnOrderBeingMonitored() {
        String tcname = "testAddingServicesToOnOrderBeingMonitored";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Rear Door",
                "Right Front Door"};
        final String[] vehiclepartswheel = {"Left Front Wheel", "Right Front Wheel"};
        final String[] vehiclepartspaint = {"Front Bumper"};
        final String[] vehiclepartstoadd = {"Hood"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTFULLBAMPER_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartspaint.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
        }
        for (int i = 0; i <= vehiclepartspaint.length; i++) {
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickBackServicesButton();


        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartswheel.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice3(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectWOMonitor();
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (int i = 0; i < vehicleparts.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.PDRPANEL_NONCUSTOMARY_SUBSERVICE + " (" + vehicleparts[i] + ")"), "Completed");
        for (int i = 0; i < vehiclepartspaint.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.PAINTFULLBAMPER_SUBSERVICE + " (" + vehiclepartspaint[i] + ")"), "Active");


        orderMonitorScreen.clickServicesButton();
        servicesScreen.waitServicesScreenLoaded();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice4(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartstoadd.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartstoadd[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        servicesScreen.clickSave();
        orderMonitorScreen = new RegularOrderMonitorScreen();
        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.PAINTFULLBAMPER_SUBSERVICE, "Queued");
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PDR_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (int i = 0; i < vehiclepartstoadd.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.PDRPANEL_SUBSERVICE + " (" + vehiclepartstoadd[i] + ")"), "Completed");

        for (int i = 0; i < vehiclepartspaint.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.PAINTFULLBAMPER_SUBSERVICE + " (" + vehiclepartspaint[i] + ")"), "Active");
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.PAINT_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (int i = 0; i < vehiclepartspaint.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.PAINTFULLBAMPER_SUBSERVICE + " (" + vehiclepartspaint[i] + ")"), "Completed");
        orderMonitorScreen.verifyPanelsStatuses(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE, "Active");
        orderMonitorScreen.selectPanelToChangeStatus(UtilConstants.WHEELS_SERVICE);
        orderMonitorScreen.setCompletedPhaseStatus();
        for (int i = 0; i < vehiclepartswheel.length; i++)
            Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.WHEELSTRAIGHTENING_SUBSERVICE + " (" + vehiclepartswheel[i] + ")"), "Completed");

        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();

        teamWorkOrdersScreen.clickCreateInvoiceForWO(workOrderNumber);
        teamWorkOrdersScreen.clickiCreateInvoiceButton();
        RegularInvoiceTypesSteps.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        invoiceInfoScreen.clickSaveAsFinal();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 12629:Test Start Service feature is accurately capturing times", description = "Test Start Service feature is accurately capturing times")
    public void testStartServiceFeatureIsAccuratelyCapturingTimes() {
        String tcname = "testStartServiceFeatureIsAccuratelyCapturingTimes";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.wizprotrackerrouteworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.DETAIL_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickOnWO(workOrderNumber);
        teamWorkOrdersScreen.selectWOMonitor();
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getPanelStatus(UtilConstants.FRONTLINEREADY_SUBSERVICE), "Active");
        orderMonitorScreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
        orderMonitorScreen.clickStartService();
        orderMonitorScreen.selectPanel(UtilConstants.FRONTLINEREADY_SUBSERVICE);
        Assert.assertTrue(orderMonitorScreen.isStartServiceDissapeared());
        String srvstartdate = orderMonitorScreen.getServiceStartDate().substring(0, 10);
        orderMonitorScreen.clickServiceDetailsDoneButton();
        Assert.assertEquals(orderMonitorScreen.getServiceFinishDate(UtilConstants.FRONTLINEREADY_SUBSERVICE).substring(0, 10), srvstartdate);

        teamWorkOrdersScreen = orderMonitorScreen.clickBackButton();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(testName = "Test Case 12631:Test Quantity does not mulitply price in Route package", description = "Test Quantity does not mulitply price in Route package")
    public void testQuantityDoesNotMulitplyPriceInRoutePackage() {
        String tcname = "testQuantityDoesNotMulitplyPriceInRoutePackage";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String vehiclepart = "Left Roof Rail";
        final String[] vehiclepartspaint = {"Hood", "Left Roof Rail", "Right Fender"};
        final String[] vehiclepartswheel = {"Left Front Wheel", "Left Rear Wheel"};

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
        selectedServiceDetailsScreen.setServiceQuantityValue("4");
        //selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        selectedServiceDetailsScreen.selectVehiclePart(vehiclepart);

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTPANEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.setServiceQuantityValue("5");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartspaint.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartswheel.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12632:Test Delete Work Order function", description = "Test Delete Work Order function")
    public void testDeleteWorkOrderFunction() {
        String tcname = "testDeleteWorkOrderFunction";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Roof"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

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
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.deleteWorkOrder(workOrderNumber);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, false);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12637:Test changing customer on Inspection", description = "Test changing customer on Inspection")
    public void testChangingCustomerOnInspection() {
        String tcname = "testChangingCustomerOnInspection";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Front Wheel", "Left Rear Wheel"};
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Abc Rental Center");

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickMyInspectionsButton();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        final String inspNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.DETAIL_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
        //servicesScreen.openServiceDetails(UtilConstants.WASH_N_VAC_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.clickVehiclePartsCell();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.changeCustomerForInspection(inspNumber, wholesailCustomer);
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickCustomersButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomerWithoutEditing("Abc Rental Center");
        homescreen.clickMyInspectionsButton();
        myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();

        homescreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(UtilConstants.TEST_CUSTOMER_FOR_TRAINING);
        homescreen.clickMyInspectionsButton();
        myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionDoesntExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12638:Test Retail Hail package quantity multiplier", description = "Test Retail Hail package quantity multiplier")
    public void testRetailHailPackageQuantityMultiplier() {
        String tcname = "testRetailHailPackageQuantityMultiplier";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String servicequantity = "3";
        final String servicequantity2 = "4.5";
        final String totalsumm = "$3,738.00";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(DentWizardWorkOrdersTypes.retailhailworkordertype, UtilConstants.WO_TYPE_JOB);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.acceptForReminderNoDrilling();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));

        RegularNavigationSteps.navigateToScreen(UtilConstants.HAIL_INFO_SCREEN_CAPTION);
        questionsScreen.selectOutsideQuestions();

        int retailhaildatarow = ExcelUtils.getRetailHailDataRow(tcname);
        questionsScreen.setOwnerInfo(ExcelUtils.getOwnerName(retailhaildatarow), ExcelUtils.getOwnerAddress(retailhaildatarow), ExcelUtils.getownerCity(retailhaildatarow),
                ExcelUtils.getownerState(retailhaildatarow), UtilConstants.UNATED_STATES, ExcelUtils.getOwnerZip(retailhaildatarow));

        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompanyAndSetClaim(ExcelUtils.getInsuranceCompany(retailhaildatarow), ExcelUtils.getClaim(retailhaildatarow));

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FIXPRICE_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.setServiceQuantityValue(servicequantity);
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.RANDI_HAIL_SERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.setServiceQuantityValue(servicequantity2);
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        Assert.assertEquals(orderSummaryScreen.getOrderSumm(), totalsumm);
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.HAIL_NO_DISCOUNT_INVOICE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), totalsumm);
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12641:Test custom WO level split for Route package", description = "Test custom WO level split for Route package")
    public void testCustomWOLevelSplitForRoutePackage() {
        String tcname = "testCustomWOLevelSplitForRoutePackage";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Mirror", "Right Mirror"};
        final String[] vehiclepartswheel = {"Left Front Wheel", "Right Front Wheel"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianA, "70");
        selectedServiceDetailsScreen.setTechnicianCustomPercentageValue(UtilConstants.technicianB, "30");
        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.DUELEATHER_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA), "$29.40");
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB), "$12.60");
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianA);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "31.50");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%75.00");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "10.50");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%25.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WHEEL_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehiclepartswheel.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA), "$70.00");
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB), "$30.00");
        selectedServiceDetailsScreen.selectTechniciansCustomView();
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianC, "28");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%28.00");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianA, "67");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianA), "%67.00");
        selectedServiceDetailsScreen.setTechnicianCustomPriceValue(UtilConstants.technicianB, "5");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianB), "%5.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12642:Test even WO level split for Route package", description = "Test even WO level split for Route package")
    public void testEvenWOLevelSplitForRoutePackage() {
        String tcname = "testEvenWOLevelSplitForRoutePackage";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Fender", "Left Front Door", "Left Quarter Panel", "Left Rear Door", "Left Roof Rail"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.clickTech();
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(UtilConstants.technicianA));
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianB);
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianA), "%50.00");
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPercentage(UtilConstants.technicianB), "%50.00");

        String alertText = selectedServiceDetailsScreen
                .saveSelectedServiceDetailsWithAlert();
        Assert.assertEquals(
                alertText,
                AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.DETAIL_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.FRONTLINEREADY_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.OTHER_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.WINDOWTINT_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.unselecTechnician(UtilConstants.technicianB);
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$108.00");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%50.00");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRVEHICLE_SUBSERVICE);

        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.clickVehiclePartsCell();
        for (int i = 0; i < vehicleparts.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehicleparts[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        selectedServiceDetailsScreen.selecTechnician(UtilConstants.technicianC);
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(UtilConstants.technicianC), "$80.00");
        Assert.assertEquals(selectedServiceDetailsScreen.getCustomTechnicianPercentage(UtilConstants.technicianC), "%33.33");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12644:Test adding notes to a Work Order", description = "Test adding notes to a Work Order")
    public void testAddingNotesToWorkOrder() {
        String tcname = "testAddingNotesToWorkOrder";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Hood", "Left Rear Door", "Right Fender"};
        final String[] vehiclepartspaint = {"Left Rear Door", "Right Fender"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routecanadaworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes("Blue fender");
        //notesScreen.clickDoneButton();
        notesScreen.clickSaveButton();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice(testcaserow)));
        selectedServiceDetailsScreen.clickNotesCell();
        notesScreen.setNotes("Declined right door");
        notesScreen.clickSaveButton();

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
        selectedServiceDetailsScreen.clickNotesCell();
        notesScreen.setNotes("Declined hood");
        notesScreen.clickSaveButton();

        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartspaint.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        notesScreen.setNotes("Declined wheel work");
        notesScreen.clickSaveButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12645:Test changing the PO# on an invoice", description = "Test changing the PO# on an invoice")
    public void testChangingThePOOnAnInvoice() {
        String tcname = "testChangingThePOOnAnInvoice";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.servicedriveworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

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
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.setTotalSale("1");
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO("832145");
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForChangePO(invoiceNumber);
        myInvoicesScreen.changePO("832710");
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12646:Test editing an Inspection", description = "Test editing an Inspection")
    public void testEditingAnInspection() {
        String tcname = "testEditingAnInspection";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehicleparts = {"Left Front Door", "Left Rear Door", "Right Front Door", "Right Rear Door"};

        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreen settingsScreen = new RegularSettingsScreen();
        settingsScreen.setShowAllServicesOn();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(DentWizardInspectionsTypes.routeinspectiontype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        final String inspNumber = vehicleScreen.getInspectionNumber();
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
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspNumber);
        vehicleScreen = new RegularVehicleScreen();
        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTDOORHANDLE_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        Assert.assertEquals(selectedServiceDetailsScreen.getServicePriceValue(), PricesCalculations.getPriceRepresentation(ExcelUtils.getServicePrice2(testcaserow)));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 12647:Test editing a Work Order", description = "Test editing a Work Order")
    public void testEditingWorkOrder() {
        String tcname = "testEditingWorkOrder";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.carmaxworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        String wo = vehicleScreen.getWorkOrderNumber();
        vehicleScreen.setStock(ExcelUtils.getStock(testcaserow));
        vehicleScreen.setRO(ExcelUtils.getRO(testcaserow));
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_FABRIC_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE);
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));
        selectedServicesScreen.switchToAvailableServicesTab();
        servicesScreen.clickBackServicesButton();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_VINIL_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE);
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularServicesScreenSteps.switchToSelectedServices();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(wo);

        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.WHEELCOVER2_SUBSERVICE);
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        RegularServicesScreenSteps.switchToSelectedServices();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.HB_13_BRNS_1_SECT_FBRC_SUBSERVICE, "$36.97 x 1.00"));

        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.CRT_3_RPR_VNYL_SUBSERVICE, "$89.67 x 1.00"));

        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(UtilConstants.WHEELCOVER2_SUBSERVICE, "$45.00 x 1.00"));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(testName = "Test Case 19514:Test editing an Invoice in Draft", description = "Test Editing an Invoice in Draft")
    public void testEditingAnInvoiceInDraft() {
        String tcname = "testEditingAnInvoiceInDraft";
        int testcaserow = ExcelUtils.getTestCaseRow(tcname);

        final String[] vehiclepartspdr = {"Left Fender", "Left Roof Rail", "Right Quarter Panel", "Trunk Lid"};
        final String[] vehiclepartspaint = {"Left Mirror"};
        final String[] vehiclepartswheel = {"Left Front Wheel", "Right Front Wheel"};

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(DentWizardWorkOrdersTypes.routeusworkordertype);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(ExcelUtils.getVIN(testcaserow));
        vehicleScreen.verifyMakeModelyearValues(ExcelUtils.getMake(testcaserow), ExcelUtils.getModel(testcaserow), ExcelUtils.getYear(testcaserow));
        String wonum = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectServicePanel(UtilConstants.INTERIOR_SERVICE);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CARPETREPAIR_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PDR_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PDRPANEL_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice2(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartspdr.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspdr[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartspaint.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartspaint[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.clickSave();
        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(DentWizardInvoiceTypes.NO_ORDER_TYPE);
        Assert.assertEquals(invoiceInfoScreen.getOrderSumm(), PricesCalculations.getPriceRepresentation(ExcelUtils.getTotalSumm(testcaserow)));
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
        invoiceInfoScreen.clickOnWO(wonum);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        //servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        //servicesScreen.searchServiceByName(UtilConstants.PAINTMIRROR_SUBSERVICE);
        selectedServiceDetailsScreen = selectedServicesScreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
        selectedServiceDetailsScreen.removeService();
        RegularSelectedServicesSteps.switchToAvailableServices();
        servicesScreen.selectServicePanel(UtilConstants.PAINT_SERVICE);
        servicesScreen.openCustomServiceDetails(UtilConstants.PAINTMIRROR_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice3(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        selectedServiceDetailsScreen.selectVehiclePart("Right Mirror");
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.selectServicePanel(UtilConstants.WHEELS_SERVICE);
        selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(UtilConstants.CHROMEWHEELREPAIR_SUBSERVICE);
        selectedServiceDetailsScreen.setServicePriceValue(ExcelUtils.getServicePrice4(testcaserow));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertTrue(selectedServiceDetailsScreen.vehiclePartsIsDisplayed());
        for (int i = 0; i < vehiclepartswheel.length; i++) {
            selectedServiceDetailsScreen.selectVehiclePart(vehiclepartswheel[i]);
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickBackServicesButton();

        servicesScreen.clickSave();
        invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.clickSaveAsFinal();
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
        vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
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
        vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);
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
        vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

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
        vehicleScreen.seletAdvisor(UtilConstants.TRAINING_ADVISOR);

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

package com.cyberiansoft.test.ios10_client.testcases.regular.calculations;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServicePartSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.InvoicesTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IOSCalculationsTestCases extends IOSRegularBaseTestCase {

    private RetailCustomer testRetailCustomer = new RetailCustomer("Retail", "Customer");

    @BeforeClass(description = "Calculations Test Cases")
    public void setUpSuite() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getCalculationsTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionOnTheDeviceWithApprovalRequired(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        RegularNavigationSteps.navigateToVisualScreen(WizardScreenTypes.VISUAL_INTERIOR);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_VIN_REQUIRED);

        vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_MAKE_REQUIRED);

        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        String inspnumber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspection();

        RegularMyInspectionsSteps.selectInspectionForApprove(inspnumber);
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspnumber);
        approveInspectionsScreen.clickApproveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspnumber));

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddServicesToVisualInspection(String rowID,
                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.DEFAULT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
        RegularInspectionsSteps.saveInspectionAsFinal();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_VIN_REQUIRED);
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspectionAsFinal();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_MAKE_REQUIRED);

        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        RegularInspectionsSteps.saveInspectionAsDraft();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);

        int tapXCoordInicial = 100;
        int tapYCoordInicial = 100;
        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
            visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
            for (DamageData damageData : visualScreenData.getDamagesData()) {
                visualInteriorScreen.clickServicesToolbarButton();
                visualInteriorScreen.switchToCustomTab();
                visualInteriorScreen.selectService(damageData.getDamageGroupName());
                visualInteriorScreen.selectSubService(damageData.getMoneyService().getServiceName());
                RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial);
                RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial + 50);
                tapYCoordInicial = tapYCoordInicial + 100;
                visualInteriorScreen.selectService(damageData.getDamageGroupName());
            }
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice());
            Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice());
        }

        RegularInspectionsSteps.saveInspectionAsDraft();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);

        tapXCoordInicial = 100;
        tapYCoordInicial = 100;
        for (VisualScreenData visualScreenData : inspectionData.getVisualScreensData()) {
            RegularNavigationSteps.navigateToScreen(visualScreenData.getScreenName());
            RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
            for (DamageData damageData : visualScreenData.getDamagesData()) {
                visualInteriorScreen.waitVisualScreenLoaded(visualScreenData.getScreenName());
                RegularVisualInteriorScreen.tapInteriorWithCoords(tapXCoordInicial, tapYCoordInicial);
                visualInteriorScreen.setCarServiceQuantityValue(damageData.getMoneyService().getServiceQuantity());
                visualInteriorScreen.saveCarServiceDetails();
                tapYCoordInicial = tapYCoordInicial + 100;
            }
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), visualScreenData.getScreenTotalPrice2());
            Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), visualScreenData.getScreenPrice2());
        }

        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfFeeBundleItemPricePolicyPanelThenItWillBeAddedOnceForManyAssociatedServiceInstancesWithSameVehiclePart(String rowID,
                                                                                                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
        }

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfOneFeeBundleItemIsRelatedTo2OrMoreFeeBundlePackagesAndAssignedServiceIsSelectedInWOThenAmountOfTheFeeWillBeMultipleToPackageQuantity(String rowID,
                                                                                                                                                                 String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FOR_FEE_ITEM_IN_2_PACKS);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServiceData().getServicePrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();


        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
        operationsWebPageWebPage.clickWorkOrdersLink();
        workOrdersWebPage.makeSearchPanelVisible();
        workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
        workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
        workOrdersWebPage.unselectInvoiceFromDeviceCheckbox();
        workOrdersWebPage.clickFindButton();
        String mainWindowHandle = webdriver.getWindowHandle();

        WorkOrderInfoTabWebPage workOrderInfoTabWebPage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
        BaseUtils.waitABit(5000);
        Assert.assertTrue(workOrderInfoTabWebPage.isServicePriceCorrectForWorkOrder("$36.00"));
        Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Service_in_2_fee_packs"));
        Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Oksi_Test1"));
        Assert.assertTrue(workOrderInfoTabWebPage.isServiceSelectedForWorkOrder("Oksi_Test2"));
        workOrderInfoTabWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPackagePriceOfFeeBundleItemIsOverrideThePriceOfWholesaleAndRetailPrices(String rowID,
                                                                                                      String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FEE_PRICE_OVERRIDE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getWorkOrderPrice());

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
                                                                                                                        String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();

        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
        }

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfFeeBundleItemPricePolicyServiceOrFlatFeeThenItWillBeAddedToWOEveryTimeWhenAssociatedServiceInstanceWillAddToWO(String rowID,
                                                                                                                                           String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
        }

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatForWholesaleAndRetailCustomersFeeIsAddedDependsOnThePriceAccordinglyToPriceOfTheFeeBundleItem(String rowID,
                                                                                                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(workOrderData.getVehicleInfoData().getVehicleMake(),
                workOrderData.getVehicleInfoData().getVehicleModel(), workOrderData.getVehicleInfoData().getVehicleYear());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            Assert.assertEquals(servicesScreen.getTotalAmaunt(), serviceData.getServicePrice2());
        }

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatFeeBundleServicesIsCalculatedForAdditionalMatrixServices(String rowID,
                                                                                       String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(workOrderData.getMatrixServiceData().getMatrixServiceName());
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartName());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.switchOffOption(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartOption());
        vehiclePartScreen.setPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
        for (ServiceData serviceData : workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalServices())
            vehiclePartScreen.selectDiscaunt(serviceData.getServiceName());
        Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartSubTotalPrice(), workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTotalPrice());
        vehiclePartScreen.saveVehiclePart();
        Assert.assertEquals(priceMatrixScreen.getInspectionSubTotalPrice(), workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTotalPrice());
        priceMatrixScreen.clickSave();

        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();


        BaseUtils.waitABit(45*1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
        operationsWebPageWebPage.clickWorkOrdersLink();
        workOrdersWebPage.makeSearchPanelVisible();
        workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
        workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
        workOrdersWebPage.clickFindButton();
        String mainWindowHandle = webdriver.getWindowHandle();
        WorkOrderInfoTabWebPage woinfowebpage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
        Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$2,127.50"));
        Assert.assertTrue(woinfowebpage.isServiceSelectedForWorkOrder("SR_S6_FeeBundle"));

        woinfowebpage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_095(String rowID,
                                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

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
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

            if (serviceData.getServicePrice2() != null) {
                RegularServicesScreenSteps.switchToSelectedServices();
                RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
                RegularServiceDetailsScreenValidations.verifyServiceDetailsPriceValue(serviceData.getServicePrice2());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
                RegularSelectedServicesSteps.switchToAvailableServices();
            }
        }

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_003(String rowID,
                                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

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
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            if (serviceData.getServicePrice2() != null) {
                RegularServicesScreenSteps.switchToSelectedServices();
                RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
                RegularServiceDetailsScreenValidations.verifyServiceDetailsPriceValue(serviceData.getServicePrice2());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
                RegularSelectedServicesSteps.switchToAvailableServices();
            }
        }
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatMoneyValueOfSomePercentageServiceIsRoundsUpAfter0_005(String rowID,
                                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

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
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

            if (serviceData.getServicePrice2() != null) {
                RegularServicesScreenSteps.switchToSelectedServices();
                RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
                RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
                RegularServiceDetailsScreenValidations.verifyServiceDetailsPriceValue(serviceData.getServicePrice2());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
                selectedServicesScreen.switchToAvailableServicesTab();
            }
        }
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAmountIsCalculatedAndRoundedCorrectly(String rowID,
                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        WorkOrdersWebPage workOrdersWebPage = new WorkOrdersWebPage(webdriver);
        operationsWebPageWebPage.clickWorkOrdersLink();
        workOrdersWebPage.makeSearchPanelVisible();
        workOrdersWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        workOrdersWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
        workOrdersWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        workOrdersWebPage.setSearchOrderNumber(workOrderNumber);
        workOrdersWebPage.clickFindButton();
        String mainWindowHandle = webdriver.getWindowHandle();
        WorkOrderInfoTabWebPage woinfowebpage = workOrdersWebPage.clickWorkOrderInTable(workOrderNumber);
        Assert.assertTrue(woinfowebpage.isServicePriceCorrectForWorkOrder("$3,153.94"));

        woinfowebpage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatInspectionIsSavedAsDeclinedWhenAllServicesAreSkippedOrDeclined(String rowID,
                                                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_SIMPLE);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.DEFAULT);
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        for (VehiclePartData vehiclePartData : inspectionData.getVehiclePartsData()) {
            priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
            vehiclePartScreen.saveVehiclePart();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.clickSkipAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickCancelStatusReasonButton();

        approveInspectionsScreen.clickDeclineAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();

        BaseUtils.waitABit(45*1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPageWebPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        inspectionsWebPage.selectSearchStatus("Declined");
        inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
        Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$0.00");
        Assert.assertEquals(inspectionsWebPage.getInspectionReason(inspectionNumber), "Decline 1");
        Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), "Declined");
        DriverBuilder.getInstance().getDriver().quit();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListInColumnApprovedAmount(String rowID,
                                                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        servicesScreen.waitServicesScreenLoaded();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);
        }
        approveInspectionsScreen.clickSaveButton();

        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPageWebPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        inspectionsWebPage.selectSearchStatus("All active");
        inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
        Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$2,000.00");
        Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), "Approved");
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAmountOfSkippedDeclinedServicesAreNotCalc(String rowID,
                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        servicesScreen.waitServicesScreenLoaded();
        RegularInspectionsSteps.saveInspection();

        RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumber);
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.clickSkipAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickDoneStatusReasonButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPageWebPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        inspectionsWebPage.selectSearchStatus("Declined");
        inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
        Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$0.00");
        Assert.assertEquals(inspectionsWebPage.getInspectionApprovedTotal(inspectionNumber), "$0.00");
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionVerifyServicesAreMarkedAsStrikethroughWhenExcludeFromTotal(String rowID,
                                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        Assert.assertEquals(approveInspectionsScreen.getInspectionTotalAmount(), inspectionData.getInspectionPrice());
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatValueSelectedOnPriceMatrixStepIsSavedAndShownDuringEditMode(String rowID,
                                                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
            RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
            priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            vehiclePartScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(),
                    priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
            if (priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService() != null)
                vehiclePartScreen.selectDiscaunt(priceMatrixScreenData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
            if (priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice() != null)
                vehiclePartScreen.setPrice(priceMatrixScreenData.getVehiclePartData().getVehiclePartPrice());
            vehiclePartScreen.saveVehiclePart();
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), priceMatrixScreenData.getMatrixScreenTotalPrice());
        }
        vehicleScreen.swipeScreenLeft();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
        servicesScreen.clickSave();
        Helpers.acceptAlert();
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
            RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
            priceMatrixScreen.waitPriceMatrixScreenLoad();
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), priceMatrixScreenData.getMatrixScreenPrice());
        }
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
                                                                    String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        //customer approval ON
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getServiceData());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();

        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_WO_IS_HUGE, workOrderData.getWorkOrderPrice()));
        orderSummaryScreen.swipeScreenLeft();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServiceQuantityValue(workOrderData.getServiceData().getServiceQuantity2());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularSelectedServicesSteps.waitSelectedServicesScreenLoaded();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final int workOrdersToCreate = 2;
        List<String> workOrders = new ArrayList<>();
        final String locationValue = "All locations";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        for (int i = 0; i < workOrdersToCreate; i++) {
            RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(testCaseData.getWorkOrderData().getVehicleInfoData().getVINNumber());
            workOrders.add(vehicleScreen.getWorkOrderNumber());

            RegularNavigationSteps.navigateToServicesScreen();
            RegularServicesScreenSteps.selectServiceWithServiceData(testCaseData.getWorkOrderData().getServiceData());

            RegularNavigationSteps.navigateToOrderSummaryScreen();
            RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
            orderSummaryScreen.setTotalSale(testCaseData.getWorkOrderData().getWorkOrderTotalSale());
            RegularWorkOrdersSteps.saveWorkOrder();
        }

        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.approveWorkOrder(workOrders.get(0), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.setFilterCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        teamWorkOrdersScreen.setFilterLocation(locationValue);
        teamWorkOrdersScreen.setBilling("All");
        teamWorkOrdersScreen.clickSaveFilter();

        teamWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrders.get(0));
        myWorkOrdersScreen.clickInvoiceIcon();

        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.INVOICE_CUSTOM1);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        RegularNavigationSteps.navigateToInvoiceInfoScreen();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsDraft();
        teamWorkOrdersScreen.clickHomeButton();
        homeScreen.clickMyWorkOrdersButton();
        myWorkOrdersScreen.approveWorkOrder(workOrders.get(1), iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        BaseUtils.waitABit(1000);
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrders.get(1));
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setPO(testCaseData.getInvoiceData().getPoNumber());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForEdit(invoiceNumber);
        questionsScreen.waitQuestionsScreenLoaded();
        RegularNavigationSteps.navigateToInvoiceInfoScreen();
        invoiceInfoScreen.addTeamWorkOrder(workOrders.get(1));
        invoiceInfoScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INVOICE_IS_HUGE, testCaseData.getInvoiceData().getInvoiceTotal()));
        invoiceInfoScreen.swipeScreenLeft();
        questionsScreen.waitQuestionsScreenLoaded();
        RegularInvoicesSteps.cancelCreatingInvoice();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionVerifyThatMessageIsShownTotalIsOverLimitation(String rowID,
                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_TOTAL_AMAUNT_OF_INSPECTION_IS_HUGE, inspectionData.getInspectionPrice()));

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        RegularSelectedServicesSteps.openSelectedServiceDetails(inspectionData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServiceQuantityValue(inspectionData.getServiceData().getServiceQuantity2());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularInspectionsSteps.saveInspectionAsDraft();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyApprovedAmountForInspectionCreatedFromSR(String rowID,
                                                                   String description, JSONObject testData) {

        ServiceRequestData serviceRequestData = JSonDataParser.getTestDataFromJson(testData, ServiceRequestData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());

        RegularNavigationSteps.navigateToScreen(serviceRequestData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        questionsScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);

        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
        visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
        vehicleScreen.waitVehicleScreenLoaded();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(serviceRequestData.getInspectionData().getMoneyServiceData());
        servicesScreen.waitServicesScreenLoaded();
        RegularServiceRequestSteps.saveServiceRequest();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectDetailsRequestAction();
        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();

        teamInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        teamInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getMoneyServiceData());
        approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceRequestData.getInspectionData().getPercentageServiceData());
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
        BaseUtils.waitABit(2000);
        Assert.assertTrue(teamInspectionsScreen.checkInspectionIsApproved(inspectionNumber));
        Assert.assertEquals(teamInspectionsScreen.getFirstInspectionAprovedPriceValue(), serviceRequestData.getInspectionData().getInspectionPrice());
        Assert.assertEquals(teamInspectionsScreen.getFirstInspectionPriceValue(), serviceRequestData.getInspectionData().getInspectionTotalPrice());
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatOnPriceMatrixStepSubTotalValueIsShownCorrectly(String rowID,
                                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());


        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
            RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
            VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
            priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            if (vehiclePartData.getVehiclePartSize() != null)
                vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
            if (vehiclePartData.getVehiclePartOption() != null)
                vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
            if (vehiclePartData.getVehiclePartPrice() != null)
                vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
            if (vehiclePartData.getVehiclePartTime() != null)
                vehiclePartScreen.setTime(vehiclePartData.getVehiclePartTime());
            if (vehiclePartData.getVehiclePartTotalPrice() != null)
                vehiclePartScreen.setTime(vehiclePartData.getVehiclePartTime());
            for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
                vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
                RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
                selectedServiceDetailsScreen.saveSelectedServiceDetails();
                Assert.assertEquals(vehiclePartScreen.getPriceMatrixVehiclePartSubTotalPrice(), serviceData.getServicePrice());
            }
            vehiclePartScreen.saveVehiclePart();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        RegularMyInspectionsSteps.selectInspectionForApprove(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.approveInspectionApproveAllAndSignature();
        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber, WorkOrdersTypes.WO_SMOKE_MONITOR);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), inspectionData.getInspectionPrice());
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(),
                    serviceData.getServicePrice()));
        }
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForInspection(String rowID,
                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_WITH_PART_SERVICES);
        RegularVehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());
        RegularInspectionsSteps.saveInspectionAsDraft();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();

        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularServicesScreenSteps.openCustomServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServicePartValue(inspectionData.getMoneyServiceData().getServicePartData());
        RegularServiceDetailsScreenSteps.setServicePriceValue(inspectionData.getMoneyServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularNavigationSteps.navigateToScreen(ScreenNamesConstants.FUTURE_AUDI_CAR);
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_AUDI_CAR);
        visualInteriorScreen.clickServicesToolbarButton();
        visualInteriorScreen.switchToCustomTab();
        visualInteriorScreen.selectService(inspectionData.getDamageData().getDamageGroupName());
        visualInteriorScreen.selectSubService(inspectionData.getDamageData().getMoneyService().getServiceName());
        Helpers.tapRegularCarImage();
        Helpers.tapRegularCarImage();
        RegularServiceDetailsScreenSteps.clickServicePartCell();
        RegularServicePartSteps.selectServicePartData(inspectionData.getDamageData().getMoneyService().getServicePartData());
        RegularServiceDetailsScreenSteps.setServicePriceValue(inspectionData.getDamageData().getMoneyService().getServicePrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        final PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreenData();
        RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        final VehiclePartData vehiclePartData = priceMatrixScreenData.getVehiclePartData();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
        vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
        for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
            vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.clickServicePartCell();
            RegularServicePartSteps.selectServicePartData(serviceData.getServicePartData());
            RegularServiceDetailsScreenValidations.verifyServicePartValue(serviceData.getServicePartData());
            RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }
        vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        RegularServiceDetailsScreenSteps.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        vehiclePartScreen.saveVehiclePart();
        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), vehiclePartData.getVehiclePartTotalPrice());
        Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), vehiclePartData.getVehiclePartTotalPrice());
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber, WorkOrdersTypes.WO_WITH_PART_SERVICE);
        String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWizardScreensSteps.clickSaveButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatPartServicesWithDifferentConfigurationsAreCorrectlyShownForWO(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_WITH_PART_SERVICE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServicePartValue(workOrderData.getMoneyServiceData().getServicePartData());
        RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getMoneyServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
        vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
        for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
            vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.clickServicePartCell();
            ServicePartSteps.selectServicePartData(serviceData.getServicePartData());
            RegularServiceDetailsScreenValidations.verifyServicePartValue(serviceData.getServicePartData());
            RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }


        Assert.assertEquals(priceMatrixScreen.getPriceMatrixVehiclePartSubTotalPrice(), vehiclePartData.getVehiclePartSubTotalPrice());
        vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        RegularServiceDetailsScreenSteps.setServiceQuantityValue(vehiclePartData.getVehiclePartAdditionalService().getServiceQuantity());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();
        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyRoundingInCalculationScriptWithPriceMatrix(String rowID,
                                                                       String description, JSONObject testData) {

        final String filterBillingValue = "All";

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        InvoiceData invoiceData = testCaseData.getInvoiceData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
        vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
        for (ServiceData serviceData : vehiclePartData.getVehiclePartAdditionalServices()) {
            vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();
        RegularServicesScreenSteps.selectServiceWithServiceData(workOrderData.getServiceData());
        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();

        RegularInvoiceInfoScreen invoiceInfoScreen = orderSummaryScreen.selectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSave();
        Helpers.acceptAlert();
        questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());

        invoiceInfoScreen.clickSaveAsFinal();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickFilterButton();
        myWorkOrdersScreen.setFilterBilling(filterBillingValue);
        myWorkOrdersScreen.clickSaveFilter();

        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreen myInvoicesScreen = new RegularMyInvoicesScreen();
        Assert.assertEquals(myInvoicesScreen.getPriceForInvoice(invoiceNumber), invoiceData.getInvoiceTotal());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyCalculationWithPriceMatrixLaborType(String rowID,
                                                                String description, JSONObject testData) {
        final String filterBillingValue = "All";
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        InvoiceData invoiceData = testCaseData.getInvoiceData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            if (serviceData.getServicePrice() != null) {
                RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            } else {
                RegularServicesScreenSteps.selectService(serviceData.getServiceName());
            }
        }
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServicePrice() != null) {
                RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
                selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
                RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
                selectedServiceDetailsScreen.setServicePriceValue(serviceData.getServicePrice());
                selectedServiceDetailsScreen.saveSelectedServiceDetails();
            } else {
                RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
                selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
            }
        }
        RegularSelectedBundleServiceScreenSteps.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        RegularPriceMatricesScreen priceMatricesScreen = new RegularPriceMatricesScreen();
        RegularPriceMatrixScreen priceMatrixScreen = priceMatricesScreen.selectPriceMatrice(matrixServiceData.getHailMatrixName());
        priceMatrixScreen.selectPriceMatrix(matrixServiceData.getVehiclePartData().getVehiclePartName());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.switchOffOption(matrixServiceData.getVehiclePartData().getVehiclePartOption());
        vehiclePartScreen.setTime(matrixServiceData.getVehiclePartData().getVehiclePartTime());
        for (ServiceData serviceData : matrixServiceData.getVehiclePartData().getVehiclePartAdditionalServices()) {
            vehiclePartScreen.clickDiscaunt(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.checkApproveAndCreateInvoice();
        orderSummaryScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        orderSummaryScreen.clickSave();
        orderSummaryScreen.clickInvoiceType("Invoice_AutoWorkListNet");
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(invoiceData.getQuestionScreenData().getQuestionData());
        RegularNavigationSteps.navigateToInvoiceInfoScreen();
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.clickFilterButton();
        myWorkOrdersScreen.setFilterBilling(filterBillingValue);
        myWorkOrdersScreen.clickSaveFilter();

        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPageWebPage.clickInvoicesLink();

        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        String mainWindowHandle = webdriver.getWindowHandle();
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.clickInvoicePrintPreview(invoiceNumber);
        invoicesWebPage.waitABit(4000);
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue("Matrix Service"), "$100.00");
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceNetValue("Matrix Service"), "$112.50");
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue("Test service zayats"), "$100.00");
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceNetValue("Test service zayats"), "$112.50");
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue("SR_Disc_20_Percent (25.000%)"), "$25.00");
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceNetValue("SR_Disc_20_Percent (25.000%)"), "$28.13");
        invoicesWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyRoundingMoneyAmountValues(String rowID,
                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        InvoiceData invoiceData = testCaseData.getInvoiceData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        servicesScreen.selectService(matrixServiceData.getMatrixServiceName());
        servicesScreen.selectPriceMatrices(matrixServiceData.getHailMatrixName());
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.switchOffOption(vehiclePartData.getVehiclePartOption());
        vehiclePartScreen.setPrice(vehiclePartData.getVehiclePartPrice());
        vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        RegularServiceDetailsScreenSteps.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        vehiclePartScreen.saveVehiclePart();
        priceMatrixScreen.clickSave();

        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            RegularServiceDetailsScreenSteps.selectServiceVehiclePart(serviceData.getVehiclePart());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        RegularSelectedBundleServiceScreenSteps.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        servicesScreen.waitServicesScreenLoaded();
        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderForApprove(workOrderNumber);
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.clickApproveButton();
        myWorkOrdersScreen.clickCreateInvoiceIconForWO(workOrderNumber);
        myWorkOrdersScreen.clickInvoiceIcon();
        RegularInvoiceTypesSteps.selectInvoiceType(InvoicesTypes.DEFAULT_INVOICETYPE);
        RegularInvoiceInfoScreen invoiceInfoScreen = new RegularInvoiceInfoScreen();
        invoiceInfoScreen.setPO(invoiceData.getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.clickSaveAsFinal();
        RegularNavigationSteps.navigateBackScreen();


        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPageWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPageWebPage.clickInvoicesLink();
        invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
        invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        String mainWindowHandle = webdriver.getWindowHandle();
        invoicesWebPage.clickInvoiceInternalTechInfo(invoiceNumber);
        Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix"), "4.67000");
        Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Test service price matrix (Sev: None; Size: None)"), "3.79600");
        Assert.assertEquals(invoicesWebPage.getTechInfoServicesTableServiceValue("<Tax>", "Oksi_Service_PP_Flat_Fee"), "0.87400");
        invoicesWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatUpdatedValueForRequiredServiceWith0PriceIsSavedWhenPackageArouvedByPanels(String rowID,
                                                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_WITH_0_PRICE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspnumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspnumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspnumber);
        Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), inspectionData.getServiceData().getServicePrice());
        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();

        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspnumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesSteps.openSelectedServiceDetails(inspectionData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServicePriceValue(inspectionData.getServiceData().getServicePrice2());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularInspectionsSteps.saveInspection();

        myInspectionsScreen.selectInspectionForAction(inspnumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        approveInspectionsScreen.selectInspection(inspnumber);
        Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServiceData().getServiceName()), PricesCalculations.getPriceRepresentation(inspectionData.getServiceData().getServicePrice2()));
        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenAppoveInspection(String rowID,
                                                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspnumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        BundleServiceData bundleServiceData = inspectionData.getBundleService();
        servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServicePrice() != null) {
                selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else
                selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
        }
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionPrice());
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspnumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspnumber);
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
            approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);

        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();

        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspnumber), inspectionData.getInspectionPrice());
        Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspnumber), inspectionData.getInspectionApprovedPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAppovedAmountIsShownOnInspectionListInDarkGreenAndTotalInDarkGrayWhenDeclineInspection(String rowID,
                                                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        BundleServiceData bundleServiceData = inspectionData.getBundleService();
        servicesScreen.openCustomServiceDetails(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServicePrice() != null) {
                selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else
                selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
        }
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.clickDeclineAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.selectStatusReason(inspectionData.getDeclineReason());
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        myInspectionsScreen.clickFilterButton();
        myInspectionsScreen.clickStatusFilter();
        myInspectionsScreen.clickFilterStatus(InspectionStatuses.DECLINED.getInspectionStatusValue());
        myInspectionsScreen.clickBackButton();
        myInspectionsScreen.clickSaveFilterDialogButton();
        Assert.assertEquals(myInspectionsScreen.getInspectionApprovedPriceValue(inspectionNumber), inspectionData.getInspectionApprovedPrice());
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatForBundleItemsPricePolicyIsApplied(String rowID,
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
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        servicesScreen.selectService(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
            selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getServiceQuantity() != null)
                RegularServiceDetailsScreenSteps.setServiceQuantityValue(serviceData.getServiceQuantity());
            if (serviceData.getVehicleParts() != null) {
                RegularServiceDetailsScreenSteps.selectServiceVehicleParts(serviceData.getVehicleParts());
            }
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            if (serviceData.isNotMultiple()) {
                AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.ALERT_YOU_CAN_ADD_ONLY_ONE_SERVICE, serviceData.getServiceName()));
            }
            Assert.assertEquals(selectedServiceBundleScreen.getServiceDetailsPriceValue(), serviceData.getServicePrice2());
        }

        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        selectedServiceBundleScreen.openBundleInfo(bundleServiceData.getLaborService().getServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServiceTimeValue(bundleServiceData.getLaborService().getLaborServiceTime());
        selectedServiceDetailsScreen.setServiceRateValue(bundleServiceData.getLaborService().getLaborServiceRate());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        Assert.assertEquals(selectedServiceBundleScreen.getServiceDetailsPriceValue(), bundleServiceData.getBundleServiceAmount());

        RegularSelectedBundleServiceScreenSteps.changeAmountOfBundleService(workOrderData.getWorkOrderPrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatForSalesTaxServiceDataIsSetFromDBWhenCreateWOForCustomerWithAppropriateData(String rowID,
                                                                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        RetailCustomer retailCustomer = new RetailCustomer("Avalon", "");

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(retailCustomer, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (TaxServiceData taxServiceData : workOrderData.getTaxServicesData()) {
            RegularServicesScreenSteps.openCustomServiceDetails(taxServiceData.getTaxServiceName());
            for (ServiceRateData serviceRateData : taxServiceData.getServiceRatesData()) {
                RegularServiceDetailsScreenValidations.verifyServiceRateValue(serviceRateData);
            }
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);

        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getTaxServicesData().get(0).getTaxServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertFalse(selectedServiceDetailsScreen.isServiceRateFieldEditable(workOrderData.getTaxServicesData().get(0).getServiceRatesData().get(0)));
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getTaxServiceData().getTaxServiceName());
        selectedServiceDetailsScreen.setServiceRateFieldValue(workOrderData.getTaxServiceData().getServiceRateData());
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceRateValue(workOrderData.getTaxServiceData().getServiceRateData()), "%" + workOrderData.getTaxServiceData().getServiceRateData().getServiceRateValue());
        Assert.assertEquals(selectedServiceDetailsScreen.getServiceDetailsPercentageTotalValue(), workOrderData.getTaxServiceData().getTaxServiceTotal());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatTaxRoundingIsCorrectlyCalculated(String rowID,
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
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.waitWorkOrderSummaryScreenLoad();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getPercentageServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServiceDetailsPriceValue(workOrderData.getPercentageServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatTaxIsCalcCorrectlyFromServicesWithTaxExemptYESNo(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToScreen(inspectionData.getQuestionScreenData().getScreenName());
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
        questionsScreen.swipeScreenUp();
        questionsScreen.selectAnswerForQuestion(inspectionData.getQuestionScreenData().getQuestionData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularInspectionsSteps.saveInspection();

        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
        RegularNavigationSteps.navigateToServicesScreen();

        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice2());
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatPackagePriceOverridesClient_RretailOrWholesale(String rowID,
                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrders = new ArrayList<>();
        RetailCustomer retailCustomer = new RetailCustomer("Oksana", "Avalon");

        final int workOrderIndexToEdit = 1;
        final String workOrderNewPrice = "$35.00";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
            customersScreen.swtchToWholesaleMode();
            customersScreen.selectCustomerWithoutEditing(workOrderData.getWholesailCustomer().getCompany());

            RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
            RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
            String workOrderNumber = vehicleScreen.getWorkOrderNumber();
            workOrders.add(workOrderNumber);
            if (workOrderData.getQuestionScreenData() != null) {
                RegularNavigationSteps.navigateToScreen(workOrderData.getQuestionScreenData().getScreenName());
                RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();
                questionsScreen.swipeScreenUp();
                questionsScreen.selectAnswerForQuestion(workOrderData.getQuestionScreenData().getQuestionData());
            }

            RegularNavigationSteps.navigateToOrderSummaryScreen();
            RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
            orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
            RegularWorkOrdersSteps.saveWorkOrder();

            RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
            RegularNavigationSteps.navigateToServicesScreen();
            RegularServicesScreen servicesScreen = new RegularServicesScreen();
            servicesScreen.selectService(workOrderData.getServiceData().getServiceName());
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderData.getWorkOrderPrice());
            RegularWorkOrdersSteps.saveWorkOrder();
            RegularNavigationSteps.navigateBackScreen();
        }
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.changeCustomerForWorkOrder(workOrders.get(workOrderIndexToEdit), retailCustomer);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrders.get(workOrderIndexToEdit), false);
        RegularNavigationSteps.navigateBackScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        homeScreen.clickMyWorkOrdersButton();
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrders.get(workOrderIndexToEdit));
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getWorkOrderPrice());
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.selectService(testCaseData.getWorkOrdersData().get(workOrderIndexToEdit).getServiceData().getServiceName());
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), workOrderNewPrice);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatClientAndJobOverridesAreWorkingFineForWO(String rowID,
                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(WorkOrdersTypes.WO_TYPE_WITH_JOB, workOrderData.getWorkOrderJob());
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            servicesScreen.selectService(serviceData.getServiceName());
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice());
        }
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        Assert.assertEquals(myWorkOrdersScreen.getPriceValueForWO(workOrderNumber), workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatCalculationIsCorrectForWOWithAllTypeOfServices(String rowID,
                                                                               String description, JSONObject testData) throws Exception {

        final DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrderWithJob(WorkOrdersTypes.WO_TYPE_WITH_JOB, workOrderData.getWorkOrderJob());
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            servicesScreen.selectService(serviceData.getServiceName());
            RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
            Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), serviceData.getServicePrice());
        }
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        RegularServicesScreenSteps.selectBundleService(workOrderData.getServicesScreen().getBundleService());
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getServicesScreen().getMatrixService());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.clickOpenMonitorForWO(workOrderNumber);
        RegularOrderMonitorScreenSteps.startWorkOrder();
        final LocalDate repairOrderDate = LocalDate.now();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.WOULD_YOU_LIKE_TO_START_REPAIR_ORDER, repairOrderDate.format(df)));

        RegularOrderMonitorScreenSteps.selectWorkOrderPhaseStatus(OrderMonitorStatuses.COMPLETED);
        RegularOrderMonitorScreenValidations.verifyOrderPhaseStatus(OrderMonitorStatuses.COMPLETED);
        RegularNavigationSteps.navigateBackScreen();

        RegularTeamWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(InvoicesTypes.INVOICE_DEFAULT_TEMPLATE);
        final InvoiceData invoiceData = testCaseData.getInvoiceData();
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(invoiceData.getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();

        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(invoiceData.getQuestionScreenData());
        RegularInvoicesSteps.saveInvoiceAsFinal();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePrice(invoiceNumber, invoiceData.getInvoiceTotal());

        NadaEMailService nada = new NadaEMailService();
        RegularMyInvoicesScreenSteps.selectSendEmailMenuForInvoice(invoiceNumber);
        RegularEmailScreenSteps.sendEmailToAddress(nada.getEmailId());
        RegularNavigationSteps.navigateBackScreen();

        final String invoiceFileName = invoiceNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invoiceFileName);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfDoc = new File(invoiceFileName);
        String pdfText = PDFReader.getPDFText(pdfDoc);
        Assert.assertTrue(pdfText.contains(invoiceData.getInvoiceTotal()));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatForDeclinedSkippedaServicesAppropriateIconIsShownAfterApproval(String rowID,
                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspnumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        for (ServiceData serviceData : inspectionData.getPercentageServicesList()) {
            servicesScreen.selectService(serviceData.getServiceName());
        }
        BundleServiceData bundleServiceData = inspectionData.getBundleService();
        servicesScreen.selectService(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServiceQuantity() != null) {
                selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.setServiceQuantityValue(serviceData.getServiceQuantity());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else
                selectedServiceBundleScreen.selectBundle(serviceData.getServiceName());
        }
        selectedServiceBundleScreen.clickSaveButton();

        RegularNavigationSteps.navigateToScreen("Future Sport Car");
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        visualInteriorScreen.clickServicesToolbarButton();
        visualInteriorScreen.selectService(inspectionData.getServiceData().getServiceName());
        RegularVisualInteriorScreen.tapInteriorWithCoords(150, 200);
        RegularVisualInteriorScreen.tapInteriorWithCoords(200, 250);
        RegularVisualInteriorScreen.tapInteriorWithCoords(200, 300);
        RegularVisualInteriorScreen.tapInteriorWithCoords(200, 350);

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
            RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
            priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
            if (priceMatrixScreenData.getVehiclePartData().getVehiclePartSize() != null)
                vehiclePartScreen.setSizeAndSeverity(priceMatrixScreenData.getVehiclePartData().getVehiclePartSize(), priceMatrixScreenData.getVehiclePartData().getVehiclePartSeverity());
            if (priceMatrixScreenData.getVehiclePartData().getVehiclePartOption() != null) {
                vehiclePartScreen.switchOffOption(priceMatrixScreenData.getVehiclePartData().getVehiclePartOption());
                vehiclePartScreen.setTime(priceMatrixScreenData.getVehiclePartData().getVehiclePartTime());
            }
            vehiclePartScreen.saveVehiclePart();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspnumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspnumber);
        approveInspectionsScreen.clickDeclineAllServicesButton();
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList())
            approveInspectionsScreen.selectApproveInspectionServiceStatus(serviceData);

        approveInspectionsScreen.clickSaveButton();
        //approveInspectionsScreen.selectStatusReason("Decline 1");
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        BaseUtils.waitABit(10 * 1000);
        RegularMyInspectionsSteps.selectInspectionForEdit(inspnumber);
        visualInteriorScreen.waitVisualScreenLoaded("Future Sport Car");
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceApproved(serviceData.getServiceName()));
        for (ServiceData serviceData : inspectionData.getPercentageServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(serviceData.getServiceName()));
        Assert.assertTrue(selectedServicesScreen.isServiceDeclinedSkipped(inspectionData.getBundleService().getBundleServiceName()));

        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOIfFeeBundleItemPricePolicyEqualsVehicleThenItWillBeAddedOnceForManyAssociatedServiceInstances(String rowID,
                                                                                                                    String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_TEST_FEE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatDiscountsAreCalculatedCorrectlyOnAllLevels(String rowID,
                                                                           String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String subTotal = "$159.00";
        final String subTotal2 = "$259.00";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.CALC_ORDER);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectBundleService(workOrderData.getServicesScreen().getBundleService());
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getServicesScreen().getMatrixService());
        RegularPriceMatrixScreenValidations.verifyPriceMatrixScreenSubTotalValue(workOrderData.getServicesScreen().getMatrixService().getVehiclePartData().getVehiclePartTotalPrice());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(subTotal);
        RegularServicesScreenSteps.selectLaborServiceAndSetData(workOrderData.getServicesScreen().getLaborService());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(subTotal2);

        for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatUpchargesAreCalculatedCorrectlyOnAllLevels(String rowID,
                                                                           String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String subTotal = "$241.00";
        final String subTotal2 = "$341.00";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.CALC_ORDER);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectBundleService(workOrderData.getServicesScreen().getBundleService());
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getServicesScreen().getMatrixService());
        RegularPriceMatrixScreenValidations.verifyPriceMatrixScreenSubTotalValue(workOrderData.getServicesScreen().getMatrixService().getVehiclePartData().getVehiclePartTotalPrice());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(subTotal);
        RegularServicesScreenSteps.selectLaborServiceAndSetData(workOrderData.getServicesScreen().getLaborService());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(subTotal2);

        for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatOnPrintOutOfAllProTemplateAllCalculationDataIsCorrectProductionData(String rowID,
                                                                                                          String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String totalColumnText = "TOTAL:";
        final String taxColumnText = "TAX DUE:";
        final String taxServicesTotal = "$6.63";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularSummaryApproveScreenSteps.approveWorkOrder();

        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(InvoicesTypes.INVOICE_ALLPRO);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenValidations.verifyInvoiceTotalValue(testCaseData.getInvoiceData().getInvoiceTotal());
        RegularInvoicesSteps.saveInvoiceAsFinal();

        RegularNavigationSteps.navigateBackScreen();

        BaseUtils.waitABit(30*1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeader.clickOperationsLink();

        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPage.clickInvoicesLink();
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();

        String mainWindowHandle = webdriver.getWindowHandle();
        invoicesWebPage.clickInvoicePrintPreview(invoiceNumber);
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTestMartrixLaborServiceListValue(totalColumnText), testCaseData.getInvoiceData().getInvoiceTotal());
        Assert.assertEquals(invoicesWebPage.getPrintPreviewListValue(taxColumnText), taxServicesTotal);
        invoicesWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatOnPrintOutOfAutoWorkListNetTemplateAllCalculationDataIsCorrect_ProdData(String rowID,
                                                                                                              String description, JSONObject testData) throws Exception {

        final String listTotal = "2368.00";
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
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        final MatrixServiceData matrixServiceData = workOrderData.getServicesScreen().getMatrixService();
        RegularServicesScreenSteps.selectMatrixService(matrixServiceData);
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            RegularVehiclePartsScreenSteps.selectVehiclePartAndSetData(vehiclePartData);
            RegularVehiclePartsScreenSteps.saveVehiclePart();
            RegularPriceMatrixScreenValidations.verifyVehiclePartPriceValue(vehiclePartData.getVehiclePartName(),
                    vehiclePartData.getVehiclePartTotalPrice());
        }
        RegularVehiclePartsScreenSteps.savePriceMatrixData();

        for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularSummaryApproveScreenSteps.approveWorkOrder();

        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(InvoicesTypes.INVOICE_AUTOWORKLISTNET);
        RegularQuestionsScreenSteps.answerQuestion(workOrderData.getQuestionScreenData().getQuestionData());
        RegularNavigationSteps.navigateToInvoiceInfoScreen();

        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenValidations.verifyInvoiceTotalValue(testCaseData.getInvoiceData().getInvoiceTotal());
        RegularInvoicesSteps.saveInvoiceAsFinal();

        RegularNavigationSteps.navigateBackScreen();

        BaseUtils.waitABit(30*1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeader = new BackOfficeHeaderPanel(webdriver);

        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeader.clickOperationsLink();

        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        operationsWebPage.clickInvoicesLink();
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();

        String mainWindowHandle = webdriver.getWindowHandle();
        invoicesWebPage.clickInvoicePrintPreview(invoiceNumber);
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTotalListValue(), BackOfficeUtils.getFormattedServicePriceValue(listTotal));
        Assert.assertEquals(invoicesWebPage.getPrintPreviewTotalNetValue(), BackOfficeUtils.getFormattedServicePriceValue(testCaseData.getInvoiceData().getInvoiceTotal()));

        invoicesWebPage.closeNewTab(mainWindowHandle);
        DriverBuilder.getInstance().getDriver().quit();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInvoicesVerifyThatOnPrintOutOfDentWizardHailTemplateAllCalculationDataIsCorrect_ProductionData(String rowID,
                                                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_DW_INVOICE_HAIL);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();

        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.clickServiceTypesButton();
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());

        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickInvoiceIcon();

        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        RegularInvoiceInfoScreenValidations.verifyInvoiceTotalValue(testCaseData.getInvoiceData().getInvoiceTotal());
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatUpChargesAndDiscountsAreCalculatedCorrectlyOnAllLevels(String rowID,
                                                                                       String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String subTotal2 = "$303.90";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.CALC_ORDER);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServicesScreen().getBundleService().getBundleServiceName());

        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();

        for (ServiceData serviceData : workOrderData.getServicesScreen().getBundleService().getMoneyServices()) {
            selectedServiceBundleScreen.clickServicesIcon();
            selectedServiceBundleScreen.openBundleMoneyServiceDetailsFromServicesScrollElement(serviceData);
            RegularServiceDetailsScreenSteps.setServiceDetailsDataAndSave(serviceData);

        }

        for (ServiceData serviceData : workOrderData.getServicesScreen().getBundleService().getPercentageServices()) {
            selectedServiceBundleScreen.clickServicesIcon();
            selectedServiceBundleScreen.selectBundlePercentageServiceFromServicesScrollElement(serviceData);

        }
        selectedServiceBundleScreen.changeAmountOfBundleService(workOrderData.getServicesScreen().getBundleService().getBundleServiceAmount());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getServicesScreen().getMatrixService());
        RegularPriceMatrixScreenValidations.verifyPriceMatrixScreenSubTotalValue(workOrderData.getServicesScreen().getMatrixService().getVehiclePartData().getVehiclePartTotalPrice());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularServicesScreenSteps.selectLaborServiceAndSetData(workOrderData.getServicesScreen().getLaborService());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(subTotal2);

        for (ServiceData serviceData : workOrderData.getServicesScreen().getPercentageServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());

        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, workOrderData.getWorkOrderPrice());
        RegularNavigationSteps.navigateBackScreen();

    }
}

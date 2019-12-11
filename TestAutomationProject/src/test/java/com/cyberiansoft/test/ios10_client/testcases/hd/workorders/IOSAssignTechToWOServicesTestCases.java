package com.cyberiansoft.test.ios10_client.testcases.hd.workorders;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.ServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.hdvalidations.TechniciansPopupValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSAssignTechToWOServicesTestCases extends IOSHDBaseTestCase {

    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();

    @BeforeClass(description = "Assign Tech To WO Services Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getAssignTechToWOServicesTestCasesDataPath();
        _003_Test_Customer.setCompanyName("003 - Test Company");
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
        NavigationSteps.navigateBackScreen();
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
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
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
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
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
        NavigationSteps.navigateBackScreen();
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

        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);
        priceMatrixScreen.clickOnTechnicians();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
        TechniciansPopup techniciansPopup = new TechniciansPopup();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(techniciansPopup.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
        techniciansPopup.saveTechViewDetails();
        priceMatrixScreen.clickSaveButton();

        servicesScreen.waitServicesScreenLoaded();
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
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
        AssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
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
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
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
        AssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
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
        AssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
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


}


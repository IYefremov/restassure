package com.cyberiansoft.test.ios10_client.testcases.regular.workorders;

import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyWorkOrdersScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularSelectedServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularVehicleInfoValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
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

public class IOSAssignTechToWOServicesTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Assign Tech To WO Services Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getAssignTechToWOServicesTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatTechSplitsIsSavedInPriceMatrices(String rowID,
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
        RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        RegularVehiclePartsScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());

        RegularVehiclePartsScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
        RegularVehiclePartsScreenSteps.selectVehiclePartTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
        List<ServiceTechnician> serviceTechnicians = new ArrayList<>();
        serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
        serviceTechnicians.add(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
        RegularVehiclePartsScreenSteps.verifyVehiclePartTechniciansValue(serviceTechnicians);
        RegularVehiclePartsScreenSteps.saveVehiclePart();
        vehicleScreen.clickSave();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.clickSave();
        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        RegularTechRevenueScreen techrevenuescreen = myWorkOrdersScreen.selectWorkOrderTechRevenueMenuItem(workOrderNumber);
        Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertTrue(techrevenuescreen.isTechIsPresentInReport(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName()));
        techrevenuescreen.clickBackButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyPriceMatrixItemDoesntHaveAdditionalServicesItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
                                                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.selectMatrixServiceData(workOrderData.getMatrixServiceData());
        RegularPriceMatrixScreenSteps.savePriceMatrix();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularVehicleInfoValidations.verifyVehicleInfoScreenTechValue(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPercentageValue(workOrderData.getVehicleInfoData().getDefaultTechnician(),
                workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianPercentageValue());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(),
                PricesCalculations.getPriceRepresentation(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice()));

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyPriceMatrixItemHasMoneyAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmount(String rowID,
                                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
        RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
        RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        RegularSelectedServiceDetailsScreen regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
        Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
        regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

        vehiclePartScreen.selectDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        Assert.assertEquals(vehiclePartScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
        regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
        Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
        regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

        vehiclePartScreen.clickSave();
        vehiclePartScreen.clickSave();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyPriceMatrixItemHasPercentageAdditionalServiceItsMainServicesTechSplitAmountIsEqualToMainServicesAmountPlusAdditionalPercentage(String rowID,
                                                                                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_SMOKE_TEST);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
        RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
        RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        RegularSelectedServiceDetailsScreen regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), "" +
                vehiclePartData.getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
        Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
        regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

        vehiclePartScreen.clickDiscaunt(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.setServicePriceValue(vehiclePartData.getVehiclePartAdditionalService().getServicePrice());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        Assert.assertEquals(vehiclePartScreen.getPriceMatrixTotalPriceValue(), vehiclePartData.getVehiclePartTotalPrice());
        regularSelectedServiceDetailsScreen = vehiclePartScreen.openTechniciansPopup();
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getCustomTechnicianPercentage(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPercentageValue());
        Assert.assertEquals(regularSelectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianFullName()),
                vehiclePartData.getVehiclePartAdditionalService().getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertTrue(regularSelectedServiceDetailsScreen.isEvenlyTabSelected());
        Assert.assertFalse(regularSelectedServiceDetailsScreen.isCustomTabSelected());
        regularSelectedServiceDetailsScreen.cancelSelectedServiceDetails();

        vehiclePartScreen.clickSave();
        vehiclePartScreen.clickSave();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        Assert.assertEquals(servicesScreen.getTotalAmaunt(), workOrderData.getWorkOrderPrice());

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyIfServiceHasDefaultTechnicianAndItsAmountIs0ThenDefaultTechnicianShouldBeAssignedToTheService_NotTechnicianSplitAtWorkOrderLevel(String rowID,
                                                                                                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
        RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician());
        RegularServiceDetailsScreenSteps.cancelServiceDetails();
        RegularServiceDetailsScreenSteps.cancelServiceDetails();

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatCorrectTechSplitAmountIsShownForMatrixServiceWhenChangePriceTo0(String rowID,
                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String serviceZeroPrice = "0";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();


        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        VehiclePartData vehiclePartData = workOrderData.getMatrixServiceData().getVehiclePartData();
        RegularVehiclePartsScreenSteps.selectVehiclePart(vehiclePartData);
        RegularVehiclePartsScreenSteps.setVehiclePartPrice(vehiclePartData.getVehiclePartPrice());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.clickSave();
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());

        vehiclePartScreen.setPrice(serviceZeroPrice);
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_DEFAULT_TECH_SPLIT_WILL_BE_ASSIGNED_IF_SET_ZERO_AMAUNT);

        vehiclePartScreen.clickOnTechnicians();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_VEHICLE_PART);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(selectedServiceDetailsScreen.getTechnicianPrice(vehiclePartData.getServiceDefaultTechnician().getTechnicianFullName()), PricesCalculations.getPriceRepresentation(serviceZeroPrice));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.clickSave();
        vehiclePartScreen.clickSave();

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatTechSplitAmountIsShownCorrectUnderMonitorForServiceWithAdjustments(String rowID,
                                                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_MONITOR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.selectServiceAdjustment(workOrderData.getServiceData().getServiceAdjustment());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        RegularTeamWorkOrdersSteps.selectSearchLocation("All locations");
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        RegularOrderMonitorScreen orderMonitorScreen = new RegularOrderMonitorScreen();
        Assert.assertEquals(orderMonitorScreen.getMonitorServiceAmauntValue(workOrderData.getServiceData()), workOrderData.getServiceData().getServicePrice2());
        orderMonitorScreen.selectPanel(workOrderData.getServiceData());
        Assert.assertEquals(orderMonitorScreen.getServiceDetailsPriceValue(), BackOfficeUtils.getFormattedServicePriceValue(BackOfficeUtils.getServicePriceValue(workOrderData.getServiceData().getServicePrice())));
        orderMonitorScreen.clickTech();
        RegularSelectedServiceDetailsScreen serviceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(serviceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertEquals(serviceDetailsScreen.getTechnicianPrice(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
                workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
        Assert.assertEquals(serviceDetailsScreen.getCustomTechnicianPercentage(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()),
                workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPercentageValue());

        serviceDetailsScreen.cancelSelectedServiceDetails();
        orderMonitorScreen.clickServiceDetailsDoneButton();
        orderMonitorScreen.clickBackButton();

        teamWorkOrdersScreen.clickHomeButton();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRequiredServicesHasCorrectTech(String rowID,
                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_MONITOR_REQUIRED_SERVICES_ALL);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen serviceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.clickCancel();
        }
        serviceBundleScreen.clickSaveButton();
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleInfoScreenSteps.clickTech();

        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

        for (ServiceData serviceData : bundleServiceData.getServices()) {
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
                RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            RegularServiceDetailsScreenSteps.cancelServiceDetails();
        }
        serviceBundleScreen.clickSaveButton();
        selectedServicesScreen.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getMatrixServiceData().getMatrixServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selectPriceMatrices(workOrderData.getMatrixServiceData().getHailMatrixName());
        RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        RegularVehiclePartsScreenSteps.saveVehiclePart();
        selectedServicesScreen.clickSave();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());

        for (ServiceData serviceData : bundleServiceData.getServices()) {
            selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            String techString = "";
            for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
                techString = techString + ", " + serviceTechnician.getTechnicianFullName();
            techString = techString.replaceFirst(",", "").trim();
            Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), techString);
            selectedServiceDetailsScreen.clickCancel();
        }
        serviceBundleScreen.clickSaveButton();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRequiredBundleItemsHasCorrectDefTech(String rowID,
                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen serviceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.cancelSelectedServiceDetails();
        }
        serviceBundleScreen.clickCancel();

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
        //selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }

            if (serviceData.getVehiclePart() != null)
                RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
        serviceBundleScreen.clickSaveButton();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }
            RegularServiceDetailsScreenSteps.cancelServiceDetails();
        }

        serviceBundleScreen.clickSaveButton();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRequiredBundleItemsAndServiceWithExpensesAnd0PriceHasCorrectDefTechAfterEditWO(String rowID,
                                                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_BUNDLE_REQ_DEF_TECH);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        BundleServiceData bundleServiceData = workOrderData.getBundleService();
        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        RegularSelectedServiceBundleScreen serviceBundleScreen = new RegularSelectedServiceBundleScreen();
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            Assert.assertEquals(selectedServiceDetailsScreen.getTechniciansValue(), serviceData.getServiceDefaultTechnician().getTechnicianFullName());
            selectedServiceDetailsScreen.cancelSelectedServiceDetails();
        }
        serviceBundleScreen.clickCancel();
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);

        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }

            if (serviceData.getVehiclePart() != null)
                RegularServiceDetailsScreenSteps.slectServiceVehiclePart(serviceData.getVehiclePart());

            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        serviceBundleScreen.changeAmountOfBundleService(bundleServiceData.getBundleServiceAmount());
        serviceBundleScreen.clickSaveButton();

        selectedServicesScreen.switchToAvailableServicesTab();
        servicesScreen.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        servicesScreen.switchToSelectedServicesTab();
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        selectedServicesScreen.openSelectBundleServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            serviceBundleScreen.openBundleInfo(serviceData.getServiceName());
            if (serviceData.getServiceNewTechnicians() != null) {
                for (ServiceTechnician serviceTechnician : serviceData.getServiceNewTechnicians())
                    RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
            } else {
                RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceData.getServiceDefaultTechnician(), true);
            }
            RegularServiceDetailsScreenSteps.cancelServiceDetails();
        }
        serviceBundleScreen.clickSaveButton();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatSelectedServicesHaveCorrectTechSplitWhenChangeIsDuringCreatingWO(String rowID,
                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_ALL_SERVICES);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularVehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenSteps.selectServiceTechnician(serviceTechnician);
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());

        RegularServicesScreenSteps.switchToSelectedServices();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
            RegularServiceDetailsScreenSteps.cancelServiceDetails();
            RegularServiceDetailsScreenSteps.cancelServiceDetails();
        }
        RegularSelectedServicesSteps.switchToAvailableServices();
        RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        RegularVehiclePartsScreenSteps.verifyVehiclePartTechniciansValue(workOrderData.getVehicleInfoData().getNewTechnicians());
        RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
        vehiclePartScreen.clickDiscaunt(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        RegularServiceDetailsScreenSteps.cancelServiceDetails();
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        vehiclePartScreen.setTime(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartTime());
        vehiclePartScreen.clickSave();
        vehiclePartScreen.clickSave();

        servicesScreen.selectService(workOrderData.getBundleService().getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceTechnician);
        RegularServiceDetailsScreenSteps.cancelServiceDetails();
        selectedServiceBundleScreen.openBundleInfo(workOrderData.getBundleService().getService().getServiceName());
        for (ServiceTechnician serviceTechnician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenValidations.verifyTechnicianCellHasValue(serviceTechnician, true);
        RegularServiceDetailsScreenSteps.cancelServiceDetails();
        selectedServiceBundleScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
        selectedServiceBundleScreen.clickSaveButton();
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(workOrderData.getBundleService().getBundleServiceName(), true);
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfServicePriceIs0AndHasDefTechAssignToServiceDefTech(String rowID,
                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.slectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        RegularServiceDetailsScreenSteps.setServicePriceValue(workOrderData.getServiceData().getServicePrice());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_WITH_ZERO_AMAUNT);
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(workOrderData.getServiceData().getServiceDefaultTechnician(),
                workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianPriceValue());
        RegularServiceDetailsScreenSteps.cancelServiceDetails();
        RegularServiceDetailsScreenSteps.cancelServiceDetails();

        servicesScreen.waitServicesScreenLoaded();
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfServicePriceIs0AssignWOSplitToServiceInCaseNoDefTech(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin("Zayats", "1111");

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_TYPE_FOR_CALC);

        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.assignTechniciansToWorkOrder();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyServicePriceValue(workOrderData.getServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_TECH_SPLIT_SET_NON_ZERO_AMAUNT_FOR_SERVICE);
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularServiceDetailsScreenValidations.verifyServiceTechnicianPriceValue(workOrderData.getVehicleInfoData().getNewTechnician(),
                workOrderData.getServiceData().getServicePrice());
        RegularServiceDetailsScreenSteps.cancelServiceDetails();
        RegularServiceDetailsScreenSteps.cancelServiceDetails();

        servicesScreen.waitServicesScreenLoaded();
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
    }
}

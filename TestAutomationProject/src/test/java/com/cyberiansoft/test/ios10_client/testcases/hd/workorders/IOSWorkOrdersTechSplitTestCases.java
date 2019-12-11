package com.cyberiansoft.test.ios10_client.testcases.hd.workorders;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.hdvalidations.AvailableServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.hdvalidations.TechniciansPopupValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSWorkOrdersTechSplitTestCases extends IOSHDBaseTestCase {

    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();

    @BeforeClass(description = "Work Orders Tech Split Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getWorkOrdersTechSplitTestCasesDataPath();
        _003_Test_Customer.setCompanyName("003 - Test Company");
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
        NavigationSteps.navigateBackScreen();
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
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(techniciansPopup.isTechnicianIsSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        techniciansPopup.cancelSearchTechnician();
        selectedServiceDetailsScreen.clickCancelSelectedServiceDetails();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        orderSummaryScreen.saveWizard();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        NavigationSteps.navigateBackScreen();
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
        for (DamageData damageData : inspectionData.getDamagesData()) {
            servicesScreen.selectGroupServiceItem(damageData.getDamageGroupName());
            for (ServiceData serviceData : damageData.getMoneyServices())
                AvailableServicesScreenValidations.verifyServiceExixts(serviceData.getServiceName(), true);
            servicesScreen.clickServiceTypesButton();
        }
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
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
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.NO_SELECTED_SERVICES);
        for (DamageData damageData : workOrderData.getDamagesData()) {
            ServicesScreenSteps.selectPanelServiceData(damageData);
            servicesScreen.clickServiceTypesButton();
        }
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
        NavigationSteps.navigateBackScreen();
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
        AssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            servicesScreen.openServiceDetails(serviceData.getServiceName());
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
        NavigationSteps.navigateBackScreen();
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
        AssignTechniciansSteps.assignTechniciansToWorkOrderWithServices();

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
        NavigationSteps.navigateBackScreen();
    }
}

package com.cyberiansoft.test.ios10_client.testcases.regular.workorders;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceBundleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularServiceTypesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularAvailableServicesScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyWorkOrdersScreenValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularServiceDetailsScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSWorkOrdersTechSplitTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Work Orders Tech Split Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getWorkOrdersTechSplitTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatWOIsSavedCorrectWithSelectedSubService_NoMessageWithIncorrectTechSplit(String rowID,
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
        final String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
            RegularServicesScreenSteps.selectServiceWithSubServices(workOrderData.getDamageData());


        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : workOrderData.getDamageData().getMoneyServices())
            Assert.assertTrue(selectedServicesScreen.isServiceWithSubServiceSelected(workOrderData.getDamageData().getDamageGroupName(),
                    serviceData.getServiceName()));
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsNotPossibleToChangeDefaultTechViaServiceTypeSplit(String rowID,
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
        RegularServicesScreenSteps.selectService(workOrderData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.selectServiceVehiclePart(workOrderData.getServiceData().getVehiclePart());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        selectedServiceDetailsScreen.selecTechnician(workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.waitServicesScreenLoaded();
        servicesScreen.clickTechnicianToolbarIcon();
        servicesScreen.changeTechnician("Dent", workOrderData.getServiceData().getServiceNewTechnician().getTechnicianFullName());
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getServiceData().getServiceName());
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getServiceData().getServiceDefaultTechnician().getTechnicianFullName()));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatItIsPossibleToAssignTechToOrderByActionTechnicians(String rowID,
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
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());

        RegularServicesScreenSteps.selectBundleService(workOrderData.getBundleService());
        for (ServiceData serviceData : workOrderData.getMoneyServices())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);

        RegularServicesScreenSteps.selectMatrixService(workOrderData.getMatrixServiceData());
        RegularVehiclePartsScreenSteps.selectVehiclePart(workOrderData.getMatrixServiceData().getVehiclePartData());
        RegularVehiclePartsScreenSteps.setVehiclePartPrice(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartPrice());
        RegularVehiclePartsScreenSteps.verifyVehiclePartTechnicianValue(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
        RegularVehiclePartsScreenSteps.selectVehiclePartAdditionalServiceAndSave(workOrderData.getMatrixServiceData().getVehiclePartData().getVehiclePartAdditionalService());
        RegularVehiclePartsScreenSteps.saveVehiclePart();
        vehicleScreen.clickSave();

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreen myWorkOrdersScreen = new RegularMyWorkOrdersScreen();
        myWorkOrdersScreen.selectWorkOrderTechniciansMenuItem(workOrderNumber);
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician());
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.clickDoneButton();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        Assert.assertEquals(vehicleScreen.getTechnician(), workOrderData.getMatrixServiceData().getVehiclePartData().getServiceNewTechnician().getTechnicianFullName() +
                ", " + workOrderData.getMatrixServiceData().getVehiclePartData().getServiceDefaultTechnician().getTechnicianFullName());
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatWhenPanelGroupingIsUsedForPackageForSelectedPanelOnlyLinkedServicesAreShown(String rowID,
                                                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O04TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_SERVICE_TYPE_WITH_OUT_REQUIRED);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (DamageData damageData : inspectionData.getDamagesData()) {
            servicesScreen.selectServicePanel(damageData.getDamageGroupName());
            for (ServiceData serviceData : damageData.getMoneyServices())
                RegularAvailableServicesScreenValidations.verifyServiceExixts(serviceData.getServiceName(), true);
            servicesScreen.clickBackServicesButton();
        }
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyAssignTechToServiceTypeInsteadOfIndividualServices(String rowID,
                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_GROUP_SERVICE_TYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(workOrderData.getVehicleInfoData().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.clickTechnicianToolbarIcon();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.NO_SELECTED_SERVICES);
        for (DamageData damageData : workOrderData.getDamagesData()) {
            RegularServicesScreenSteps.selectPanelServiceData(damageData);
            servicesScreen.clickBackServicesButton();
        }

        servicesScreen.clickTechnicianToolbarIcon();
        RegularServiceTypesScreen serviceTypesScreen = new RegularServiceTypesScreen();
        serviceTypesScreen.waitServiceTypesScreenLoaded();
        for (ServiceData serviceData : workOrderData.getServicesList())
            Assert.assertTrue(serviceTypesScreen.isPanelOrServiceExists(serviceData.getServiceName()));

        DamageData moneyServicePanel = workOrderData.getDamagesData().get(0);
        serviceTypesScreen.clickOnPanel(moneyServicePanel.getDamageGroupName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.selecTechnician(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
        selectedServiceDetailsScreen.clickSaveButton();
        serviceTypesScreen.clickSaveButton();

        servicesScreen.selectServicePanel(moneyServicePanel.getDamageGroupName());
        for (ServiceData serviceData : moneyServicePanel.getMoneyServices()) {
            RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            selectedServiceDetailsScreen.clickTechniciansIcon();
            Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
            selectedServiceDetailsScreen.clickCancel();
            selectedServiceDetailsScreen.clickCancel();
        }
        servicesScreen.clickBackServicesButton();

        DamageData bundleServicePanel = workOrderData.getDamagesData().get(1);
        servicesScreen.clickTechnicianToolbarIcon();
        serviceTypesScreen.clickOnPanel(bundleServicePanel.getDamageGroupName());
        selectedServiceDetailsScreen.selecTechnician(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName());
        selectedServiceDetailsScreen.clickSaveButton();
        serviceTypesScreen.clickSaveButton();

        servicesScreen.switchToSelectedServicesTab();
        servicesScreen.selectServiceSubSrvice(bundleServicePanel.getBundleService().getBundleServiceName());
        for (ServiceData serviceData : bundleServicePanel.getBundleService().getServices()) {
            RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
            selectedServiceBundleScreen.openBundleInfo(serviceData.getServiceName());
            selectedServiceDetailsScreen.clickTechniciansCell();
            Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(bundleServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
            selectedServiceDetailsScreen.clickCancel();
            selectedServiceDetailsScreen.clickCancel();
        }
        selectedServiceDetailsScreen.clickCancel();
        servicesScreen.switchToAvailableServicesTab();

        servicesScreen.clickTechnicianToolbarIcon();
        serviceTypesScreen.clickOnPanel(workOrderData.getDamageData().getDamageGroupName());
        selectedServiceDetailsScreen.selecTechnician(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName());
        selectedServiceDetailsScreen.selecTechnician(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName());
        selectedServiceDetailsScreen.clickSaveButton();
        serviceTypesScreen.clickSaveButton();

        servicesScreen.switchToSelectedServicesTab();
        servicesScreen.selectServiceSubSrvice(workOrderData.getDamageData().getBundleService().getBundleServiceName());
        RegularSelectedServiceBundleScreen selectedServiceBundleScreen = new RegularSelectedServiceBundleScreen();
        selectedServiceBundleScreen.openBundleInfo(workOrderData.getDamageData().getBundleService().getService().getServiceName());
        selectedServiceDetailsScreen.clickTechniciansCell();

        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getDamageData().getServiceDefaultTechnician().getTechnicianFullName()));
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(workOrderData.getDamageData().getServiceNewTechnician().getTechnicianFullName()));
        selectedServiceDetailsScreen.clickCancel();
        selectedServiceDetailsScreen.clickCancel();
        selectedServiceBundleScreen.clickCancel();
        servicesScreen.switchToAvailableServicesTab();

        servicesScreen.selectServicePanel(moneyServicePanel.getDamageGroupName());
        RegularServicesScreenSteps.openCustomServiceDetails(moneyServicePanel.getMoneyService().getServiceName());
        RegularServiceDetailsScreenSteps.selectServiceVehiclePart(moneyServicePanel.getMoneyService().getVehiclePart());
        selectedServiceDetailsScreen.clickTechniciansIcon();
        Assert.assertTrue(selectedServiceDetailsScreen.isTechnicianSelected(moneyServicePanel.getServiceDefaultTechnician().getTechnicianFullName()));
        selectedServiceDetailsScreen.clickCancel();
        selectedServiceDetailsScreen.clickCancel();
        servicesScreen.clickBackServicesButton();

        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatDefaultTechIsNotChangedWhenResetOrderSplit(String rowID,
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
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(workOrderData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            RegularServicesScreenSteps.openCustomServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice() != null)
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice());
            if (serviceData.getServiceDefaultTechnician() != null) {
                RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            }
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleInfoScreenSteps.clickTech();
        RegularServiceDetailsScreenSteps.selectServiceTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        //selectedServiceDetailsScreen.unselecTechnician(workOrderData.getVehicleInfoData().getDefaultTechnician().getTechnicianFullName());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.clickDoneButton();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        for (ServiceData serviceData : workOrderData.getMoneyServices()) {
            RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
            if (serviceData.getServicePrice2() != null)
                RegularServiceDetailsScreenSteps.setServicePriceValue(serviceData.getServicePrice2());
            if (serviceData.getServiceDefaultTechnician() != null) {
                RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(serviceData.getServiceDefaultTechnician());
            } else {
                RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
            }
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testWOVerifyThatTechSplitAssignedFormVehicleScreenIsSetToServicesUnderList(String rowID,
                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(WorkOrdersTypes.WO_FORR_MONITOR_WOTYPE);
        RegularVehicleInfoScreenSteps.setVIN(workOrderData.getVehicleInfoData().getVINNumber());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        }

        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularVehicleInfoScreenSteps.clickTech();
        for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
            RegularServiceDetailsScreenSteps.selectServiceTechnician(technician);
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularAssignTechniciansSteps.clickDoneButton();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesList()) {
            selectedServicesScreen.openSelectedServiceDetails(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            for (ServiceTechnician technician : workOrderData.getVehicleInfoData().getNewTechnicians())
                RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(technician);
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }
}

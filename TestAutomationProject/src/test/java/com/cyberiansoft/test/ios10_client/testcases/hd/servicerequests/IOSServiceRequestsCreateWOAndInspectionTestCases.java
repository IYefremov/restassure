package com.cyberiansoft.test.ios10_client.testcases.hd.servicerequests;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServiceRequestdetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSServiceRequestsCreateWOAndInspectionTestCases extends IOSHDBaseTestCase {

    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();

    @BeforeClass(description = "Service Requests Create Work Order And Inspection Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getServiceRequestsCreateWOAndInspectionTestCasesDataPath();
        _003_Test_Customer.setCompanyName("003 - Test Company");
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
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(newserviceRequestNumber, InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
        vehicleScreen.waitVehicleScreenLoaded();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            servicesScreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2());
        servicesScreen.saveWizard();
        serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        TeamInspectionsScreen teamInspectionsScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumber));
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
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
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
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
            String alertText = Helpers.getAlertTextAndAccept();
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
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        serviceRequestdetailsScreen.clickServiceRequestSummaryInspectionsButton();
        TeamInspectionsScreen teamInspectionsScreen = new TeamInspectionsScreen();
        teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumber));
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
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
        serviceRequestsScreen.selectDetailsRequestAction();
        TeamWorkOrdersScreen teamWorkOrdersScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();

        for (String workOrderNumber : workOrderNumbers)
            Assert.assertTrue(teamWorkOrdersScreen.woExists(workOrderNumber));
        teamWorkOrdersScreen.clickServiceRequestButton();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }
}

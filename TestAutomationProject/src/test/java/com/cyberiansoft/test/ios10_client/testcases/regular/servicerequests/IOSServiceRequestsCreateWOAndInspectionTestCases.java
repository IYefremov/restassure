package com.cyberiansoft.test.ios10_client.testcases.regular.servicerequests;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularOrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSServiceRequestsCreateWOAndInspectionTestCases extends IOSRegularBaseTestCase {

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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(_003_Test_Customer, ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel(), serviceRequestData.getVihicleInfo().getVehicleYear());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            if (serviceData.getServiceQuantity() != null) {
                RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.setServiceQuantityValue(serviceData.getServiceQuantity());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            }
        servicesScreen.waitServicesScreenLoaded();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestSscreen.waitForServiceRequestScreenLoad();
        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        String inspectnumber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2());

        RegularServiceRequestSteps.saveServiceRequest();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectDetailsRequestAction();
        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectnumber));
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCreateInspectionFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
                                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_INSP_ONLY);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_FOR_CALC);
        RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
        String inspectionNumberber = vehicleScreen.getInspectionNumber();

        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        selectedServicesScreen.clickSave();

        for (int i = 0; i < serviceRequestData.getMoneyServices().size(); i++) {
            String alerText = Helpers.getAlertTextAndAccept();
            String servicedetails = alerText.substring(alerText.indexOf("'") + 1, alerText.lastIndexOf("'"));
            RegularServicesScreenSteps.switchToSelectedServices();
            for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
                if (serviceData.getServiceName().equals(servicedetails)) {
                    RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
                    RegularServiceDetailsScreenSteps.setServiceDetailsDataAndSave(serviceData);
                    RegularServicesScreenSteps.waitServicesScreenLoad();
                }
            }
            servicesScreen.clickSave();
        }
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectDetailsRequestAction();
        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectionNumberber));
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyMultipleInspectionsAndMultipleWorkOrdersToBeTiedToAServiceRequest(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionNumberbers = new ArrayList<>();
        List<String> workOrderNumbers = new ArrayList<>();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        Helpers.getAlertTextAndCancel();
        final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.valueOf(inspectionData.getInspectionType()));
            vehicleScreen.waitVehicleScreenLoaded();
            inspectionNumberbers.add(vehicleScreen.getInspectionNumber());
            if (inspectionData.isDraft()) {
                RegularInspectionsSteps.saveInspectionAsDraft();
            } else
                RegularServiceRequestSteps.saveServiceRequest();
        }

        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectDetailsRequestAction();
        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        for (String inspectnumber : inspectionNumberbers)
            Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectnumber));

        teamInspectionsScreen.clickBackButton();
        serviceRequestSscreen.clickBackButton();
        serviceRequestSscreen = new RegularServiceRequestsScreen();
        for (WorkOrderData workOrderData : testCaseData.getWorkOrdersData()) {
            serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
            serviceRequestSscreen.selectCreateWorkOrderRequestAction();
            RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.valueOf(workOrderData.getWorkOrderType()));
            workOrderNumbers.add(vehicleScreen.getWorkOrderNumber());
            RegularNavigationSteps.navigateToOrderSummaryScreen();
            RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
            orderSummaryScreen.setTotalSale(workOrderData.getWorkOrderTotalSale());
            Assert.assertEquals(orderSummaryScreen.getTotalSaleValue(), PricesCalculations.getPriceRepresentation(workOrderData.getWorkOrderTotalSale()));
            RegularWizardScreensSteps.clickSaveButton();
        }
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectDetailsRequestAction();
        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryOrdersButton();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        for (String workOrderNumber : workOrderNumbers)
            Assert.assertTrue(teamWorkOrdersScreen.isWorkOrderExists(workOrderNumber));
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }
}

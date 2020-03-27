package com.cyberiansoft.test.ios10_client.testcases.hd.servicerequests;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListInteractions;
import com.cyberiansoft.test.bo.validations.ServiceRequestsListVerifications;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServiceRequestdetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.OrderSummaryScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IOSServiceRequestsCreateAppointmentTestCases extends IOSHDBaseTestCase {

    private WholesailCustomer Test_Company_Customer = new WholesailCustomer();

    @BeforeClass(description = "Service Requests Create Appointment Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getServiceRequestsCreateAppointmentTestCasesDataPath();
        Test_Company_Customer.setCompanyName("Test Company");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifySummaryActionForAppointmentOnSRsCalendar(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String appointmentSubject = "SR-APP";
        final String appointmentAddress = "Maidan";
        final String appointmentCity = "Kiev";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();

        ServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer,
                ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVINFieldValue(serviceRequestData.getVihicleInfo().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.drawSignature();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);

        QuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        final String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);

        serviceRequestsScreen.selectAppointmentRequestAction();
        serviceRequestsScreen.clickAddAppointmentButton();
        serviceRequestsScreen.selectTodayFromAppointmet();
        serviceRequestsScreen.selectTodayToAppointmet();

        serviceRequestsScreen.setSubjectAppointmet(appointmentSubject);
        serviceRequestsScreen.setAddressAppointmet(appointmentAddress);
        serviceRequestsScreen.setCityAppointmet(appointmentCity);
        serviceRequestsScreen.saveAppointment();
        serviceRequestsScreen.selectCloseAction();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        Assert.assertTrue(serviceRequestsScreen.isSRSummaryAppointmentsInformation());

        serviceRequestdetailsScreen.clickBackButton();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectRejectAction();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String totalSale = "3";

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_WO_ONLY);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            ServicesScreenSteps.selectService(serviceData.getServiceName());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        ServiceRequestSteps.startCreatingWorkOrderFromServiceRequest(serviceRequestNumber, WorkOrdersTypes.WO_TYPE_FOR_CALC);
        VehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            Assert.assertTrue(servicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        NavigationSteps.navigateToOrderSummaryScreen();
        OrderSummaryScreen orderSummaryScreen = new OrderSummaryScreen();
        orderSummaryScreen.setTotalSale(totalSale);
        String workOrderNumber = orderSummaryScreen.getWorkOrderNumber();
        orderSummaryScreen.clickSave();

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
            orderSummaryScreen.clickSave();
        }
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        ServiceRequestdetailsScreen serviceRequestdetailsScreen = serviceRequestsScreen.selectDetailsRequestAction();
        TeamWorkOrdersScreen teamWorkOrdersScreen = serviceRequestdetailsScreen.clickServiceRequestSummaryOrdersButton();
        teamWorkOrdersScreen.woExists(workOrderNumber);
        teamWorkOrdersScreen.clickServiceRequestButton();
        NavigationSteps.navigateBackScreen();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyThatItIsPossibleToAcceptDeclineAppointmentWhenOptionAppointmentAcceptanceRequiredEqualsON(String rowID,
                                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String technicianValue = "Employee Simple 20%";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String startDate = LocalDate.now().plusDays(1).format(formatter);
        String endDate = LocalDate.now().plusDays(2).format(formatter);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestDropDown(ServiceRequestTypes.SR_ACCEPT_ON_MOBILE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickGeneralInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestGeneralInfoAssignedTo(technicianValue);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(),
                serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.saveNewServiceRequest();
        final String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        serviceRequestsListInteractions.acceptFirstServiceRequestFromList();
        serviceRequestsListInteractions.addAppointmentFromSRList(startDate, endDate, technicianValue);
        Assert.assertTrue(new ServiceRequestsListVerifications().isAddAppointmentFromSRListClosed(),
                "The Add Appointment dialog hasn't been closed");
        DriverBuilder.getInstance().getDriver().quit();

        HomeScreen homeScreen = new HomeScreen();
        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();

        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectAppointmentRequestAction();

        Assert.assertTrue(serviceRequestsScreen.isAcceptAppointmentRequestActionExists());
        Assert.assertTrue(serviceRequestsScreen.isDeclineAppointmentRequestActionExists());
        serviceRequestsScreen.clickCloseButton();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRAddAppointmentToServiceRequest(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        final String appointmentSubject = "SR-APP";
        final String appointmentAddress = "Maidan";
        final String appointmentCity = "Kiev";

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();
        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer,
                ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVINFieldValue(serviceRequestData.getVihicleInfo().getVINNumber());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.drawSignature();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);

        QuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();
        final String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestsScreen.selectAppointmentRequestAction();
        serviceRequestsScreen.clickAddAppointmentButton();
        serviceRequestsScreen.selectTodayFromAppointmet();
        serviceRequestsScreen.selectTodayToAppointmet();

        serviceRequestsScreen.setSubjectAppointmet(appointmentSubject);
        serviceRequestsScreen.setAddressAppointmet(appointmentAddress);
        serviceRequestsScreen.setCityAppointmet(appointmentCity);
        serviceRequestsScreen.saveAppointment();
        serviceRequestsScreen.selectCloseAction();
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(newserviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
        serviceRequestsScreen.selectRejectAction();
        NavigationSteps.navigateBackScreen();
    }
}

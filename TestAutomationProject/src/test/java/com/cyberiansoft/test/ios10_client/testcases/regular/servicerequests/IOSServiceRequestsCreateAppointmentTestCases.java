package com.cyberiansoft.test.ios10_client.testcases.regular.servicerequests;

import com.cyberiansoft.test.baseutils.BaseUtils;
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
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularServiceRequestDetalsScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
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

public class IOSServiceRequestsCreateAppointmentTestCases extends IOSRegularBaseTestCase {

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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer, ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.waitServicesScreenLoad();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();

        questionsScreen.drawSignature();
        servicesScreen.clickSave();
        Helpers.waitForAlert();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
        RegularQuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());

        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestSscreen.waitForServiceRequestScreenLoad();
        final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);

        serviceRequestSscreen.selectAppointmentRequestAction();
        serviceRequestSscreen.clickAddButton();
        serviceRequestSscreen.selectTodayFromAppointmet();
        serviceRequestSscreen.selectTodayToAppointmet();

        serviceRequestSscreen.setSubjectAppointmet(appointmentSubject);
        serviceRequestSscreen.setAddressAppointmet(appointmentAddress);
        serviceRequestSscreen.setCityAppointmet(appointmentCity);
        serviceRequestSscreen.saveAppointment();
        serviceRequestSscreen.clickBackButton();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectDetailsRequestAction();

        RegularServiceRequestDetalsScreenValidations.verifySRSummaryAppointmentsInformationExists(true);

        serviceRequestSscreen.clickBackButton();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectRejectAction();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenCreateWOFromSRMessageThatVehiclePartsAreRequiredIsShownForAppropriateServices(String rowID,
                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String totalSale = "3";

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_WO_ONLY);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectCreateWorkOrderRequestAction();
        RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.WO_TYPE_FOR_CALC);
        RegularVehicleInfoScreenSteps.verifyMakeModelyearValues(serviceRequestData.getVihicleInfo());
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices())
            Assert.assertTrue(selectedServicesScreen.checkServiceIsSelected(serviceData.getServiceName()));
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreen orderSummaryScreen = new RegularOrderSummaryScreen();
        orderSummaryScreen.setTotalSale(totalSale);
        orderSummaryScreen.clickSave();

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
        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryOrdersButton();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.isWorkOrderExists(workOrderNumber);
        teamWorkOrdersScreen.clickBackButton();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
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
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();

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

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();

        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectAppointmentRequestAction();
        BaseUtils.waitABit(2000);
        Assert.assertTrue(serviceRequestSscreen.isAcceptAppointmentRequestActionExists());
        Assert.assertTrue(serviceRequestSscreen.isDeclineAppointmentRequestActionExists());
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRAddAppointmentToServiceRequest(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        final String appointmentSubject = "SR-APP";
        final String appointmentAddress = "Maidan";
        final String appointmentCity = "Kiev";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer, ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();

        questionsScreen.drawSignature();
        servicesScreen.clickSave();
        Helpers.waitForAlert();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
        RegularQuestionsScreenSteps.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectAppointmentRequestAction();
        serviceRequestSscreen.clickAddButton();
        serviceRequestSscreen.selectTodayFromAppointmet();
        serviceRequestSscreen.selectTodayToAppointmet();

        serviceRequestSscreen.setSubjectAppointmet(appointmentSubject);
        serviceRequestSscreen.setAddressAppointmet(appointmentAddress);
        serviceRequestSscreen.setCityAppointmet(appointmentCity);
        serviceRequestSscreen.saveAppointment();
        serviceRequestSscreen.clickBackButton();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectRejectAction();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_REJECT_SERVICEREQUEST);
        serviceRequestSscreen.clickHomeButton();
    }
}

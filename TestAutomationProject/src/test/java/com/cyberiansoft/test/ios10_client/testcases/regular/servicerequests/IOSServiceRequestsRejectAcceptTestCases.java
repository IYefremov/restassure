package com.cyberiansoft.test.ios10_client.testcases.regular.servicerequests;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListInteractions;
import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSServiceRequestsRejectAcceptTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Service Requests Reject/Accept Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getServiceRequestsRejectAcceptTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsDisplayedForSRInStatusScheduledInspOrWOAndAssignForTech(String rowID,
                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        //Create first SR
        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);

        String serviceRequestNumber1 = serviceRequestSscreen.getFirstServiceRequestNumber();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber1);
        serviceRequestSscreen.selectRejectAction();
        Helpers.acceptAlert();
        //Create second SR
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE);
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularServiceRequestSteps.saveServiceRequest();

        String serviceRequestNumber2 = serviceRequestSscreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber2), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber2);
        serviceRequestSscreen.selectRejectAction();
        Helpers.acceptAlert();

        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsDisplayedForSRInStatusOnHoldInspOrWOAndAssignForTech(String rowID,
                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_ALL_PHASES);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());

        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestSscreen.isRejectActionExists());
        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.CREATE_INPECTION);
        RegularInspectionTypesSteps.selectInspectionType(InspectionsTypes.INSP_DRAFT_MODE);
        vehicleScreen.waitVehicleScreenLoaded();
        String inspectnumber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspectionAsFinal();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectDetailsRequestAction();
        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        Assert.assertTrue(teamInspectionsScreen.isInspectionExists(inspectnumber));
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.selectInspectionForAction(inspectnumber);

        teamInspectionsScreen.clickApproveInspections();
        teamInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectnumber);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
        boolean onhold = false;
        for (int i = 0; i < 7; i++) {
            RegularHomeScreenSteps.navigateToServiceRequestScreen();
            if (!serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber).equals(ServiceRequestStatus.ON_HOLD.getValue())) {
                serviceRequestSscreen.clickHomeButton();
                Helpers.waitABit(30 * 1000);
            } else {

                onhold = true;
                break;
            }
        }

        Assert.assertTrue(onhold);
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectRejectAction();
        Helpers.acceptAlert();
        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusOnHoldInspOrWOAndNotAssignForTech(String rowID,
                                                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

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
        serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_ALL_PHASES.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.saveNewServiceRequest();
        serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestSscreen.isRejectActionExists());
        serviceRequestSscreen.selectCancelAction();
        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatRejectActionIsNotDisplayedForSRInStatusScheduledInspOrWOAndNotAssignForTech(String rowID,
                                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

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
        serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_ONLY_ACC_ESTIMATE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();
        serviceRequestsListInteractions.addServicesToServiceRequest(serviceRequestData.getPercentageServices());
        serviceRequestsListInteractions.saveNewServiceRequest();
        String serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

        DriverBuilder.getInstance().getDriver().quit();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularMainScreen mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestSscreen.isRejectActionExists());
        serviceRequestSscreen.selectCancelAction();
        serviceRequestSscreen.clickHomeButton();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        operationsWebPage.clickNewServiceRequestList();
        serviceRequestsListInteractions.selectAddServiceRequestsComboboxValue(ServiceRequestTypes.SR_TYPE_WO_AUTO_CREATE.getServiceRequestTypeName());
        serviceRequestsListInteractions.clickAddServiceRequestButtonAndSave();

        serviceRequestsListInteractions.clickCustomerEditButton();
        serviceRequestsListInteractions.selectServiceRequestCustomer(iOSInternalProjectConstants.O02TEST__CUSTOMER);
        serviceRequestsListInteractions.clickDoneButton();

        serviceRequestsListInteractions.clickVehicleInfoEditButton();
        serviceRequestsListInteractions.setServiceRequestVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        serviceRequestsListInteractions.decodeAndVerifyServiceRequestVIN(serviceRequestData.getVihicleInfo().getVehicleMake(), serviceRequestData.getVihicleInfo().getVehicleModel());
        serviceRequestsListInteractions.clickDoneButton();
        serviceRequestsListInteractions.addServicesToServiceRequest(serviceRequestData.getMoneyServices());
        serviceRequestsListInteractions.saveNewServiceRequest();
        serviceRequestNumber = serviceRequestsListInteractions.getFirstInTheListServiceRequestNumber();
        serviceRequestsListInteractions.acceptFirstServiceRequestFromList();

        DriverBuilder.getInstance().getDriver().quit();

        mainScreen = homeScreen.clickLogoutButton();
        mainScreen.updateDatabase();
        mainScreen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestSscreen.isRejectActionExists());
        serviceRequestSscreen.selectEditServiceRequestAction();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        Assert.assertTrue(vehicleScreen.getTechnician().isEmpty());
        vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularServiceRequestSteps.saveServiceRequest();
        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAcceptDeclineActionsArePresentForTechWhenTechnicianAcceptanceRequiredOptionIsONAndStatusIsProposed(String rowID,
                                                                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

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
        DriverBuilder.getInstance().getDriver().quit();


        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        Assert.assertTrue(serviceRequestSscreen.isServiceRequestProposed(serviceRequestNumber));
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestSscreen.isAcceptActionExists());
        Assert.assertTrue(serviceRequestSscreen.isDeclineActionExists());
        serviceRequestSscreen.selectAcceptAction();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_ACCEPT_SERVICEREQUEST);

        Assert.assertTrue(serviceRequestSscreen.isServiceRequestOnHold(serviceRequestNumber));
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestSscreen.isAcceptActionExists());
        Assert.assertFalse(serviceRequestSscreen.isDeclineActionExists());
        serviceRequestSscreen.clickCancel();
        serviceRequestSscreen.clickHomeButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatSRIsNotAcceptedWhenEmployeeReviewOrUpdateIt(String rowID,
                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

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
        DriverBuilder.getInstance().getDriver().quit();


        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        Assert.assertTrue(serviceRequestSscreen.isServiceRequestProposed(serviceRequestNumber));
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        serviceRequestSscreen.selectEditServiceRequestAction();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setTech(serviceRequestData.getVihicleInfo().getDefaultTechnician().getTechnicianFullName());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        RegularServiceRequestSteps.saveServiceRequest();
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestSscreen.isAcceptActionExists());
        Assert.assertTrue(serviceRequestSscreen.isDeclineActionExists());
        serviceRequestSscreen.clickCancel();

        serviceRequestSscreen.clickHomeButton();

    }

}

package com.cyberiansoft.test.ios10_client.testcases.regular.servicerequests;

import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMenuValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSServiceRequestsCloseTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Service Requests Close Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getServiceRequestsCloseTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
                                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        vehicleScreen.selectNextScreen(WizardScreenTypes.SERVICES);
        RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
        RegularServiceRequestSteps.saveServiceRequest();

        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());

        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestSscreen.isCloseActionExists());
        serviceRequestSscreen.clickCancel();
        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
                                                                                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
        RegularServiceRequestSteps.saveServiceRequest();
        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestSscreen.isCloseActionExists());
        serviceRequestSscreen.clickCancel();
        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
        RegularServiceRequestSteps.saveServiceRequest();

        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.CLOSE, false);
        RegularMenuItemsScreenSteps.closeMenuScreen();
        serviceRequestSscreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown(String rowID,
                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(serviceRequestData.getMoneyService().getServiceName());
        RegularServiceRequestSteps.saveServiceRequest();

        String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestSscreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestSscreen.isCloseActionExists());
        serviceRequestSscreen.selectCloseAction();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        serviceRequestSscreen.clickCancelCloseReasonDialog();
        serviceRequestSscreen.clickHomeButton();
    }
}

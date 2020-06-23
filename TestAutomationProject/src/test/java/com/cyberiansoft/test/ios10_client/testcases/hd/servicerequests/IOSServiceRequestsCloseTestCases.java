package com.cyberiansoft.test.ios10_client.testcases.hd.servicerequests;

import com.cyberiansoft.test.dataclasses.ServiceRequestData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.servicerequests.ServiceRequestStatus;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.QuestionsScreenSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServiceRequestSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSServiceRequestsCloseTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Service Requests Close Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getServiceRequestsCloseTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionAllowToCloseSRIsSetToONActionCloseIsShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
                                                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatWhenOptionAllowToCloseSRIsSetToOFFActionCloseIsNotShownForSelectedSROnStatusScheduledOrOnHold(String rowID,
                                                                                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_DONOT_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertFalse(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectRejectAction();
        serviceRequestsScreen.waitServiceRequestsScreenLoaded();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressNoAlertMessageIsClose(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAlertMessageIsShownWhenSelectCloseActionForSRPressYesListOfStatusReasonsIsShown(String rowID,
                                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_ALLOW_CLOSE_SR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(serviceRequestData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());
        servicesScreen.saveWizard();

        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.SCHEDULED.getValue());
        serviceRequestsScreen.selectServiceRequest(serviceRequestNumber);
        Assert.assertTrue(serviceRequestsScreen.isCloseActionExists());
        serviceRequestsScreen.selectCloseAction();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CLOSE_SERVICEREQUEST);
        serviceRequestsScreen.clickCancelCloseReasonDialog();
        NavigationSteps.navigateBackScreen();
    }
}

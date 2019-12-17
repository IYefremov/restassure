package com.cyberiansoft.test.ios10_client.testcases.regular.servicerequests;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListInteractions;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.verifications.ServiceRequestsListVerifications;
import com.cyberiansoft.test.dataclasses.*;
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
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularClaimScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularQuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularHomeScreenSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularNavigationSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularServiceRequestSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.RegularServicesScreenSteps;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSServiceRequestsCreateTestCases extends IOSRegularBaseTestCase {

    private WholesailCustomer Test_Company_Customer = new WholesailCustomer();

    @BeforeClass(description = "Service Requests Create Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getServiceRequestsCreateTestCasesDataPath();
        Test_Company_Customer.setCompanyName("Test Company");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreatingServiceRequestWithInspectionWOAndAppointmentRequiredOnDevice(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        final String teamName = "Default team";
        final String serviceName = "Test Company (Universal Client)";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestsScreen serviceRequestSscreen = new RegularServiceRequestsScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(Test_Company_Customer, ServiceRequestTypes.SR_EST_WO_REQ_SRTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        final VehicleInfoData vehicleInfoData = serviceRequestData.getVihicleInfo();
        vehicleScreen.setVIN(vehicleInfoData.getVINNumber());
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.THE_VIN_IS_INCORRECT);

        vehicleScreen.setMakeAndModel(vehicleInfoData.getVehicleMake(), vehicleInfoData.getVehicleModel());
        vehicleScreen.setColor(vehicleInfoData.getVehicleColor());
        vehicleScreen.setYear(vehicleInfoData.getVehicleYear());

        vehicleScreen.setMileage(vehicleInfoData.getMileage());
        vehicleScreen.setFuelTankLevel(vehicleInfoData.getFuelTankLevel());
        vehicleScreen.setType(vehicleInfoData.getVehicleType());
        vehicleScreen.setStock(vehicleInfoData.getVehicleStock());
        vehicleScreen.setRO(vehicleInfoData.getRoNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();

        for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
            if (serviceData.getServiceQuantity() != null) {
                RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            } else
                RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        servicesScreen.waitServicesScreenLoaded();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimScreen = new RegularClaimScreen();
        claimScreen.selectInsuranceCompany(serviceRequestData.getInsuranceCompany().getInsuranceCompanyName());
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        RegularQuestionsScreen questionsScreen = new RegularQuestionsScreen();

        questionsScreen.drawSignature();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
        questionsScreen.answerQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        serviceRequestSscreen.waitForServiceRequestScreenLoad();
        final String serviceRequestNumber = serviceRequestSscreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestSscreen.getServiceRequestStatus(serviceRequestNumber), ServiceRequestStatus.ON_HOLD.getValue());
        Assert.assertTrue(serviceRequestSscreen.getServiceRequestClient(serviceRequestNumber).contains(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER));
        Assert.assertTrue(serviceRequestSscreen.getServiceRequestDetails(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        serviceRequestSscreen.clickHomeButton();
        Helpers.waitABit(10 * 1000);

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
        serviceRequestsListInteractions.makeSearchPanelVisible();
        final ServiceRequestsListVerifications serviceRequestsListVerifications = new ServiceRequestsListVerifications();
        serviceRequestsListVerifications.verifySearchFieldsAreVisible();

        serviceRequestsListInteractions.selectSearchTeam(teamName);
        serviceRequestsListInteractions.selectSearchTechnician("Employee Simple 20%");
        serviceRequestsListInteractions.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        serviceRequestsListInteractions.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        serviceRequestsListInteractions.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());

        serviceRequestsListInteractions.setSearchFreeText(serviceRequestNumber);
        serviceRequestsListInteractions.clickFindButton();
        serviceRequestsListVerifications.verifySearchResultsByServiceName(serviceName);
        serviceRequestsListInteractions.selectFirstServiceRequestFromList();
        Assert.assertEquals(serviceRequestsListInteractions.getVINValueForSelectedServiceRequest(), serviceRequestData.getVihicleInfo().getVINNumber());
        Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Bundle1_Disc_Ex $150.00 (1.00)"));
        Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Quest_Req_Serv $10.00 (1.00)"));
        Assert.assertTrue(serviceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Wheel $70.00 (3.00)"));
        Assert.assertEquals(serviceRequestsListInteractions.getCustomerValueForSelectedServiceRequest(), serviceName);
        Assert.assertEquals(serviceRequestsListInteractions.getEmployeeValueForSelectedServiceRequest(), "Employee Simple 20% (Default team)");
        DriverBuilder.getInstance().getDriver().quit();
    }
}

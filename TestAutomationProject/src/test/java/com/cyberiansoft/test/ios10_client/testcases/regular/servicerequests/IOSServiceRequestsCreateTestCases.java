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
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMyWorkOrdersScreenValidations;
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


        //Create inspection

        InspectionData inspectionData = testCaseData.getInspectionData();
        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        serviceRequestSscreen.waitForServiceRequestScreenLoad();

        RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, InspectionsTypes.INSP_FOR_SR_INSPTYPE);
        vehicleScreen.waitVehicleScreenLoaded();
        String inspectionNumberber = vehicleScreen.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2()));
            if (serviceData.getQuestionData() != null) {
                RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
                RegularServiceDetailsScreenSteps.answerServiceQuestion(serviceData.getQuestionData());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            }
        }

        BundleServiceData bundleServiceData = inspectionData.getBundleService();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(bundleServiceData.getBundleServiceName(), bundleServiceData.getBundleServiceAmount()));
        RegularSelectedServicesSteps.openSelectedServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServiceQuantity() != null) {
                RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
                selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
                RegularServiceDetailsScreenSteps.saveServiceDetails();
            } else {
                RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
                selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
            }
        }
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularServiceRequestSteps.saveServiceRequest();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspectionNumberber));
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumberber), inspectionData.getInspectionPrice());
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.selectInspectionForAction(inspectionNumberber);

        myInspectionsScreen.clickApproveInspections();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumberber);
        approveInspectionsScreen.clickApproveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();


        //Create WO
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        serviceRequestSscreen.selectServiceRequest(serviceRequestSscreen.getFirstServiceRequestNumber());
        serviceRequestSscreen.selectCreateWorkOrderRequestAction();
        RegularWorkOrderTypesSteps.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR);
        String workOrderNumber = vehicleScreen.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.switchToSelectedServices();
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice2()));
        Assert.assertTrue(selectedServicesScreen.isServiceIsSelectedWithServiceValues(workOrderData.getBundleService().getBundleServiceName(), PricesCalculations.getPriceRepresentation(workOrderData.getBundleService().getBundleServiceAmount())));

        RegularSelectedServicesSteps.openSelectedServiceDetails(workOrderData.getBundleService().getBundleServiceName());
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        selectedServiceDetailsScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        selectedServicesScreen.waitSelectedServicesScreenLoaded();

        selectedServicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.THE_VIN_IS_INVALID_AND_SAVE_WORKORDER);
        serviceRequestSscreen.waitForServiceRequestScreenLoad();
        serviceRequestSscreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }
}

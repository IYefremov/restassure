package com.cyberiansoft.test.ios10_client.testcases.hd.servicerequests;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServiceRequestsListInteractions;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.bo.validations.ServiceRequestsListVerifications;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.ServiceRequestSteps;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.ServiceRequestsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ClaimScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.QuestionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.ServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSServiceRequestsCreateTestCases extends IOSHDBaseTestCase {

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

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : serviceRequestData.getMoneyServices()) {
            if (serviceData.getServiceQuantity() != null) {
                servicesScreen.selectService(serviceData.getServiceName());
                SelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
                servicedetailsscreen.setServiceQuantityValue(serviceData.getServiceQuantity());
                servicedetailsscreen.saveSelectedServiceDetails();
            } else
                servicesScreen.selectService(serviceData.getServiceName());
        }

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(serviceRequestData.getInsuranceCompany().getInsuranceCompanyName());
        servicesScreen.clickSave();
        Helpers.waitForAlert();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_SIGNATURE_SHOULD_BE_ANSWERED);
        QuestionsScreen questionsScreen = new QuestionsScreen();
        questionsScreen.drawSignature();
        questionsScreen.clickSave();

        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_QUESTION_TAX_POINT_1_SHOULD_BE_ANSWERED);
        questionsScreen.selectAnswerForQuestion(serviceRequestData.getQuestionScreenData().getQuestionData());
        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String newserviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestClient(newserviceRequestNumber), iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
        Assert.assertTrue(serviceRequestsScreen.getServiceRequestVehicleInfo(newserviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        serviceRequestsScreen.clickHomeButton();


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
        serviceRequestsListInteractions.makeSearchPanelVisible();

        final ServiceRequestsListVerifications serviceRequestsListVerifications = new ServiceRequestsListVerifications();
        ServiceRequestsListVerifications.verifySearchFieldsAreVisible();

        serviceRequestsListInteractions.selectSearchTeam(teamName);
        serviceRequestsListInteractions.selectSearchTechnician("Employee Simple 20%");
        serviceRequestsListInteractions.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        serviceRequestsListInteractions.setSearchFromDate(CustomDateProvider.getCurrentDateInShortFormat());
        serviceRequestsListInteractions.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        serviceRequestsListInteractions.setSearchFreeText(newserviceRequestNumber);
        serviceRequestsListInteractions.clickFindButton();
        ServiceRequestsListVerifications.isServiceNamePresentInFirstSR(serviceName);
        serviceRequestsListInteractions.selectFirstServiceRequestFromList();
        Assert.assertEquals(serviceRequestsListInteractions.getVINValueForSelectedServiceRequest(), serviceRequestData.getVihicleInfo().getVINNumber());
        Assert.assertEquals(serviceRequestsListInteractions.getCustomerValueForSelectedServiceRequest(), serviceName);
        Assert.assertEquals(serviceRequestsListInteractions.getEmployeeValueForSelectedServiceRequest(), "Employee Simple 20% (Default team)");
        Assert.assertTrue(ServiceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Bundle1_Disc_Ex $150.00 (1.00)"));
        Assert.assertTrue(ServiceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Quest_Req_Serv $10.00 (1.00)"));
        Assert.assertTrue(ServiceRequestsListVerifications.isServiceIsPresentForForSelectedServiceRequest("Wheel $70.00 (3.00)"));
        DriverBuilder.getInstance().getDriver().quit();

        //Create inspection
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String questionValue = "Test Answer 1";

        homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToSinglePageInspection();
        settingsScreen.clickHomeButton();

        homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingInspectionFromServiceRequest(newserviceRequestNumber, InspectionsTypes.INSP_FOR_SR_INSPTYPE);

        SinglePageInspectionScreen singlePageInspectionScreen = new SinglePageInspectionScreen();
        String inspectionNumber = singlePageInspectionScreen.getInspectionNumber();
        singlePageInspectionScreen.expandToFullScreeenSevicesSection();
        BaseUtils.waitABit(2000);
        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(serviceData.getServiceName(), serviceData.getServicePrice2()));
            if (serviceData.getQuestionData() != null) {
                SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
                selectedServiceDetailsScreen.answerQuestion(serviceData.getQuestionData());
                selectedServiceDetailsScreen.saveSelectedServiceDetails();
            }
        }
        BundleServiceData bundleServiceData = inspectionData.getBundleService();
        Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(bundleServiceData.getBundleServiceName(), bundleServiceData.getBundleServiceAmount()));
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openServiceDetails(bundleServiceData.getBundleServiceName());
        for (ServiceData serviceData : bundleServiceData.getServices()) {
            if (serviceData.getServiceQuantity() != null) {
                selectedServiceDetailsScreen.changeBundleQuantity(serviceData.getServiceName(), serviceData.getServiceQuantity());
                selectedServiceDetailsScreen.saveSelectedServiceDetails();
            } else
                selectedServiceDetailsScreen.selectBundle(serviceData.getServiceName());
        }
        selectedServiceDetailsScreen.saveSelectedServiceDetails();


        singlePageInspectionScreen.collapseFullScreen();
        singlePageInspectionScreen.expandToFullScreeenQuestionsSection();

        Assert.assertTrue(singlePageInspectionScreen.isSignaturePresent());
        singlePageInspectionScreen.swipeScreenRight();
        singlePageInspectionScreen.swipeScreenRight();
        singlePageInspectionScreen.swipeScreenUp();

        Assert.assertTrue(singlePageInspectionScreen.isAnswerPresent(questionValue));
        singlePageInspectionScreen.collapseFullScreen();
        singlePageInspectionScreen.clickSaveButton();
        serviceRequestsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionExists(inspectionNumber));
        Assert.assertEquals(myInspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());

        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        approveInspectionsScreen.clickApproveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.waitInspectionsScreenLoaded();
        NavigationSteps.navigateBackScreen();

        homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();


        //Create WO
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        homeScreen.clickServiceRequestsButton();
        serviceRequestsScreen.clickHomeButton();
        //test case
        boolean createWOExists = false;
        final int timaoutMinules = 15;
        int iterator = 0;
        while ((iterator < timaoutMinules) | (!createWOExists)) {

            homeScreen.clickServiceRequestsButton();
            serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
            createWOExists = serviceRequestsScreen.isCreateWorkOrderActionExists();
            if (!createWOExists) {
                serviceRequestsScreen.selectServiceRequest(newserviceRequestNumber);
                serviceRequestsScreen.clickHomeButton();
                Helpers.waitABit(1000 * 30);
            } else {
                serviceRequestsScreen.selectCreateWorkOrderRequestAction();
                WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup();
                workOrderTypesPopup.selectWorkOrderType(WorkOrdersTypes.WO_FOR_SR.getWorkOrderTypeName());
                break;
            }

        }
        String workOrderNumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToServicesScreen();
        Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice2()));
        Assert.assertTrue(servicesScreen.checkServiceIsSelectedWithServiceValues(workOrderData.getBundleService().getBundleServiceName(), PricesCalculations.getPriceRepresentation(workOrderData.getBundleService().getBundleServiceAmount())));

        servicesScreen.openServiceDetails(workOrderData.getBundleService().getBundleServiceName());
        selectedServiceDetailsScreen.changeAmountOfBundleService(workOrderData.getBundleService().getBundleServiceAmount());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        servicesScreen.clickSave();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.THE_VIN_IS_INVALID_AND_SAVE_WORKORDER);
        serviceRequestsScreen.clickHomeButton();

        MyWorkOrdersScreen myWorkOrdersScreen = homeScreen.clickMyWorkOrdersButton();
        Assert.assertTrue(myWorkOrdersScreen.isWorkOrderPresent(workOrderNumber));
        NavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyThatSRIsCreatedCorrectlyWhenSelectOwnerOnVehicleInfo(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String owner = "Avalon";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        ServiceRequestsScreen serviceRequestsScreen = homeScreen.clickServiceRequestsButton();
        ServiceRequestSteps.startCreatingServicerequest(ServiceRequestTypes.SR_TYPE_CHECKIN_ON);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(serviceRequestData.getVihicleInfo().getVINNumber());
        vehicleScreen.selectOwnerT(owner);
        NavigationSteps.navigateToScreen(ScreenNamesConstants.PACKAGE_FOR_MONITOR);
        ServicesScreen servicesScreen = new ServicesScreen();
        servicesScreen.selectService(serviceRequestData.getMoneyService().getServiceName());

        servicesScreen.clickSave();
        AlertsValidations.cancelAlertAndValidateAlertMessage(AlertsCaptions.ALERT_CREATE_APPOINTMENT);
        String serviceRequestNumber = serviceRequestsScreen.getFirstServiceRequestNumber();
        Assert.assertEquals(serviceRequestsScreen.getServiceRequestClient(serviceRequestNumber), iOSInternalProjectConstants.O02TEST__CUSTOMER);
        Assert.assertTrue(serviceRequestsScreen.getServiceRequestVehicleInfo(serviceRequestNumber).contains(serviceRequestData.getVihicleInfo().getVINNumber()));
        NavigationSteps.navigateBackScreen();
    }
}

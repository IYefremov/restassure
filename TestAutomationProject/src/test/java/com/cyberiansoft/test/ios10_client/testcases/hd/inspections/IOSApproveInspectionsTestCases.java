package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.PriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class IOSApproveInspectionsTestCases extends IOSHDBaseTestCase {

    private RetailCustomer johnRetailCustomer = new RetailCustomer("John", "Simple_PO#_Req");
    private WholesailCustomer Test_Company_Customer = new WholesailCustomer();
    private WholesailCustomer _002_Test_Customer = new WholesailCustomer();

    @BeforeClass(description = "Approve Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getApproveInspectionsTestCasesDataPath();
        Test_Company_Customer.setCompanyName("Test Company");
        _002_Test_Customer.setCompanyName("002 - Test Company");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnBackOfficeFullInspectionApproval(String rowID,
                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        vehicleScreen.saveWizard();

        myInspectionsScreen.clickHomeButton();
        MainScreen mainScreeneen = homeScreen.clickLogoutButton();
        mainScreeneen.updateDatabase();
        Helpers.waitABit(60 * 1000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();

        inspectionsWebPage.approveInspectionByNumber(inspectionNumber);

        DriverBuilder.getInstance().getDriver().quit();

        mainScreeneen.updateDatabase();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspectionNumber));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnBackOfficeLinebylineApproval(String rowID,
                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        //MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.VITALY_TEST_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

        vehicleScreen.saveWizard();
        myInspectionsScreen.clickHomeButton();
        MainScreen mainScreeneen = homeScreen.clickLogoutButton();
        mainScreeneen.updateDatabase();
        Helpers.waitABit(10 * 1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();

        inspectionsWebPage.approveInspectionLinebylineApprovalByNumber(
                inspectionNumber, inspectionData.getServicesList());

        DriverBuilder.getInstance().getDriver().quit();

        mainScreeneen.updateDatabase();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        homeScreen.clickMyInspectionsButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspectionNumber));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown(String rowID,
                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.TEST_COMPANY_CUSTOMER);
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();
        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData())
            PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);

        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        vehicleScreen.saveWizard();
        myInspectionsScreen.selectInspectionForAction(inspectionnumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionnumber);
        for (ServiceData serviceData : inspectionData.getServicesToApprovesList()) {
            Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData.getServiceName()));
            Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(serviceData.getServiceName()), serviceData.getServicePrice());
        }

        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();
        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        vehicleScreen.waitVehicleScreenLoaded();
        PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreensData().get(0);
        NavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
        PriceMatrixScreen priceMatrixScreen = new PriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
        Assert.assertEquals(priceMatrixScreen.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
        InspectionToolBar inspectionToolBar = new InspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), inspectionData.getInspectionPrice());
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionTotalPrice());
        vehicleScreen.saveWizard();
        myInspectionsScreen.selectInspectionForAction(inspectionnumber);
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionnumber);
        Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServicesToApprovesList().get(0).getServiceName()), inspectionData.getServicesToApprovesList().get(0).getServicePrice());
        Assert.assertFalse(approveInspectionsScreen.isInspectionServiceExistsForApprove(inspectionData.getServicesToApprovesList().get(1).getServiceName()));
        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inpectionsIDs = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_DRAFT_MODE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            inpectionsIDs.add(vehicleScreen.getInspectionNumber());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
            SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
            servicedetailsscreen.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
            servicedetailsscreen.saveSelectedServiceDetails();
            servicesScreen.waitServicesScreenLoaded();
            servicesScreen.clickSaveAsFinal();
        }
        myInspectionsScreen.clickHomeButton();
        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inpectionsIDs) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }

        teamInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        for (String inspectionID : inpectionsIDs) {
            approveInspectionsScreen.selectInspectionForApprove(inspectionID);
            approveInspectionsScreen.clickApproveAllServicesButton();
            approveInspectionsScreen.clickSaveButton();
        }
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inpectionsIDs) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        teamInspectionsScreen.clickActionButton();
        Assert.assertFalse(teamInspectionsScreen.isApproveInspectionMenuActionExists());
        Assert.assertTrue(teamInspectionsScreen.isSendEmailInspectionMenuActionExists());
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly(String rowID,
                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList())
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        servicesScreen.saveWizard();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        for (ServiceData serviceData : inspectionData.getServicesList())
            Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData));

        approveInspectionsScreen.clickApproveAllServicesButton();
        Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveApproveButtons(), 2);
        approveInspectionsScreen.clickDeclineAllServicesButton();
        Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveDeclineButtons(), 2);
        approveInspectionsScreen.clickSkipAllServicesButton();
        Assert.assertEquals(approveInspectionsScreen.getNumberOfActiveSkipButtons(), 2);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ArrayList<String> inspections = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            final String inspectionNumber = vehicleScreen.getInspectionNumber();
            inspections.add(inspectionNumber);
            QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.saveWizard();
            myInspectionsScreen.selectInspectionForAction(inspectionNumber);
            SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
            selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
            ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
            approveInspectionsScreen.approveInspectionApproveAllAndSignature(inspectionNumber);
            myInspectionsScreen = new MyInspectionsScreen();
        }
        myInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            myInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        Assert.assertFalse(myInspectionsScreen.isApproveInspectionMenuActionExists());
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.clickDoneButton();
        myInspectionsScreen.clickHomeButton();

        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        teamInspectionsScreen.clickActionButton();
        Assert.assertFalse(teamInspectionsScreen.isApproveInspectionMenuActionExists());
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected(String rowID,
                                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ArrayList<String> inspections = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            inspections.add(vehicleScreen.getInspectionNumber());
            QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
            NavigationSteps.navigateToServicesScreen();
            ServicesScreen servicesScreen = new ServicesScreen();
            servicesScreen.saveWizard();
        }
        myInspectionsScreen.selectInspectionForAction(inspections.get(0));
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.approveInspectionApproveAllAndSignature(inspections.get(0));

        myInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            myInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        Assert.assertTrue(myInspectionsScreen.isApproveInspectionMenuActionExists());
        myInspectionsScreen.clickActionButton();
        myInspectionsScreen.clickDoneButton();
        myInspectionsScreen.clickHomeButton();

        TeamInspectionsScreen teamInspectionsScreen = homeScreen.clickTeamInspectionsButton();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        teamInspectionsScreen.clickActionButton();
        Assert.assertTrue(teamInspectionsScreen.isApproveInspectionMenuActionExists());
        teamInspectionsScreen.clickActionButton();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanChangeApprovedStatusOfPercentageServiceToDeclinedOnlyUsingDeclineAllOption(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        final String declineReason = "Decline 2";

        HomeScreen homeScreen = new HomeScreen();
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSPECTION_ALL_SERVICES);
        VehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        NavigationSteps.navigateToServicesScreen();
        ServicesScreenSteps.selectService(inspectionData.getPercentageServiceData().getServiceName());
        InspectionsSteps.saveInspectionAsFinal();

        myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);

        final String serviceName = inspectionData.getPercentageServiceData().getServiceName();
        Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.selectInspectionServiceToSkip(serviceName);
        Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.selectInspectionServiceToDecline(serviceName);
        Assert.assertTrue(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.clickSkipAllServicesButton();
        Assert.assertFalse(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertTrue(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.clickDeclineAllServicesButton();
        Assert.assertFalse(approveInspectionsScreen.isServiceApproved(serviceName));
        Assert.assertFalse(approveInspectionsScreen.isServiceSkipped(serviceName));
        Assert.assertTrue(approveInspectionsScreen.isServiceDeclibed(serviceName));

        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.selectStatusReason(declineReason);
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneStatusReasonButton();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionsOnDeviceViaAction(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionsID = new ArrayList<>();

        HomeScreen homeScreen = new HomeScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
            VehicleScreen vehicleScreen = new VehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
            vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
            vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
            inspectionsID.add(vehicleScreen.getInspectionNumber());
            vehicleScreen.saveWizard();
        }

        myInspectionsScreen.clickActionButton();
        for (String inspectionNumber : inspectionsID) {
            myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        }

        myInspectionsScreen.clickApproveInspections();
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        for (String inspectionNumber : inspectionsID) {
            approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
            approveInspectionsScreen.clickApproveAfterSelection();
        }

        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        for (String inspectionNumber : inspectionsID) {
            Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspectionNumber));
        }
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnDevice(String rowID,
                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(johnRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        final String inspNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.saveWizard();

        myInspectionsScreen.selectInspectionForApprove(inspNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspNumber);
        approveInspectionsScreen.clickApproveAfterSelection();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionApproved(inspNumber));
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatAmountOfApprovedServicesAreShownOnBOInspectionsListColumnApprovedAmount(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            ServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        InspectionsSteps.saveInspection();

        myInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        approveInspectionsScreen.clickDeclineAllServicesButton();
        approveInspectionsScreen.selectApproveInspectionServiceStatus(inspectionData.getMoneyServicesList().get(inspectionData.getMoneyServicesList().size() - 1));
        //approveInspectionsScreen.selectInspectionServiceToApprove(iOSInternalProjectConstants.SR_S1_MONEY + " (Grill)");
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();
        NavigationSteps.navigateBackScreen();
        Helpers.waitABit(40 * 1000);
        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebpage = new BackOfficeLoginWebPage(webdriver);
        loginWebpage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);

        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchStatus("All active");
        inspectionsWebPage.selectSearchTimeframe("Custom");
        inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());

        inspectionsWebPage.searchInspectionByNumber(inspectionNumber);
        Assert.assertEquals(inspectionsWebPage.getInspectionAmountApproved(inspectionNumber), "$2,000.00");
        Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), "Approved");
        Assert.assertEquals(inspectionsWebPage.getInspectionApprovedTotal(inspectionNumber), "$2000.00");
        DriverBuilder.getInstance().getDriver().quit();
    }
}

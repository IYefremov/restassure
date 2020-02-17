package com.cyberiansoft.test.ios10_client.testcases.regular.inspections;

import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularInspectionToolBar;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularMenuValidations;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularWizardScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
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

public class IOSApproveInspectionsTestCases extends IOSRegularBaseTestCase {

    private RetailCustomer testRetailCustomer = new RetailCustomer("Automation", "Retail Customer");
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

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.waitVehicleScreenLoaded();
        String inpectionnumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.updateMainDataBase();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();

        inspectionsWebPage.approveInspectionByNumber(inpectionnumber);

        DriverBuilder.getInstance().getDriver().quit();

        RegularMainScreenSteps.updateMainDataBase();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inpectionnumber));

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnBackOfficeLinebylineApproval(String rowID,
                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_LA_TS_INSPTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        String inspectionNumber = vehicleScreen.getInspectionNumber();
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);

        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.logoutUser();
        RegularMainScreenSteps.updateMainDataBase();
        Helpers.waitABit(10000);

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        InspectionsWebPage inspectionsWebPage = new InspectionsWebPage(webdriver);
        operationsWebPage.clickInspectionsLink();
        inspectionsWebPage.makeSearchPanelVisible();
        inspectionsWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
        inspectionsWebPage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
        inspectionsWebPage.approveInspectionLinebylineApprovalByNumber(
                inspectionNumber, inspectionData.getServicesList());

        DriverBuilder.getInstance().getDriver().quit();

        RegularMainScreenSteps.updateMainDataBase();
        RegularMainScreenSteps.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspectionNumber));

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatOnInspectionApprovalScreenSelectedPriceMatrixValueIsShown(String rowID,
                                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(Test_Company_Customer, InspectionsTypes.INSP_TYPE_FOR_PRICE_MATRIX_APP_REQ);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionNumber = vehicleScreen.getInspectionNumber();

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToPriceMatrixScreen(priceMatrixScreenData.getMatrixScreenName());
            RegularPriceMatrixScreenSteps.selectPriceMatrixData(priceMatrixScreenData);
            RegularVehiclePartsScreenSteps.saveVehiclePart();
            RegularWizardScreenValidations.verifyScreenSubTotalPrice(priceMatrixScreenData.getMatrixScreenPrice());
            RegularWizardScreenValidations.verifyScreenTotalPrice(priceMatrixScreenData.getMatrixScreenTotalPrice());
        }

        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);

        for (ServiceData serviceData : inspectionData.getServicesToApprovesList()) {
            Assert.assertTrue(approveInspectionsScreen.isInspectionServiceExistsForApprove(serviceData.getServiceName()));
            Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(serviceData.getServiceName()), serviceData.getServicePrice());
        }
        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
        RegularVehicleInfoScreenSteps.waitVehicleScreenLoaded();
        PriceMatrixScreenData priceMatrixScreenData = inspectionData.getPriceMatrixScreensData().get(0);
        RegularNavigationSteps.navigateToScreen(priceMatrixScreenData.getMatrixScreenName());
        RegularPriceMatrixScreen priceMatrixScreen = new RegularPriceMatrixScreen();
        priceMatrixScreen.selectPriceMatrix(priceMatrixScreenData.getVehiclePartData().getVehiclePartName());
        Assert.assertEquals(priceMatrixScreen.clearVehicleData(), AlertsCaptions.ALERT_ALL_VEHICLE_PART_DATA_WILL_BE_ERASED);
        RegularInspectionToolBar inspectionToolBar = new RegularInspectionToolBar();
        Assert.assertEquals(inspectionToolBar.getInspectionSubTotalPrice(), inspectionData.getInspectionPrice());
        Assert.assertEquals(inspectionToolBar.getInspectionTotalPrice(), inspectionData.getInspectionTotalPrice());
        RegularInspectionsSteps.saveInspection();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        Assert.assertEquals(approveInspectionsScreen.getInspectionServicePrice(inspectionData.getServicesToApprovesList().get(0).getServiceName()), inspectionData.getServicesToApprovesList().get(0).getServicePrice());
        Assert.assertFalse(approveInspectionsScreen.isInspectionServiceExistsForApprove(inspectionData.getServicesToApprovesList().get(1).getServiceName()));
        approveInspectionsScreen.clickCancelButton();
        approveInspectionsScreen.clickCancelButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatItIsPossibleToApproveTeamInspectionsUseMultiSelect(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inpectionsIDs = new ArrayList<>();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.DEFAULT);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            inpectionsIDs.add(vehicleScreen.getInspectionNumber());
            RegularNavigationSteps.navigateToServicesScreen();
            RegularServicesScreen servicesScreen = new RegularServicesScreen();
            servicesScreen.selectService(inspectionData.getBundleService().getBundleServiceName());
            RegularSelectedBundleServiceScreenSteps.changeAmountOfBundleService(inspectionData.getBundleService().getBundleServiceAmount());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularInspectionsSteps.saveInspectionAsFinal();
            RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();

        }
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToTeamInspectionsScreen();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inpectionsIDs) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }

        teamInspectionsScreen.clickApproveInspections();
        teamInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        for (String inspectionID : inpectionsIDs) {
            approveInspectionsScreen.selectInspection(inspectionID);
            approveInspectionsScreen.clickApproveAllServicesButton();
            approveInspectionsScreen.clickSaveButton();
        }
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.waitTeamInspectionsScreenLoaded();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inpectionsIDs) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        teamInspectionsScreen.clickActionButton();
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, false);
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.SEND_EMAIL, true);
        teamInspectionsScreen.clickCancel();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatDuringLineApprovalSelectAllButtonsAreWorkingCorrectly(String rowID,
                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumberber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList())
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        servicesScreen.waitServicesScreenLoaded();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionNumberber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumberber);

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
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatApproveOptionIsNotPresentForApprovedInspectionInMultiselectMode(String rowID,
                                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ArrayList<String> inspections = new ArrayList<>();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            final String inspectionNumber = vehicleScreen.getInspectionNumber();
            inspections.add(inspectionNumber);
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
            RegularInspectionsSteps.saveInspection();
            RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
            myInspectionsScreen.selectInspectionForAction(inspectionNumber);
            myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

            RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
            approveInspectionsScreen.selectInspection(inspectionNumber);
            approveInspectionsScreen.clickApproveAllServicesButton();
            approveInspectionsScreen.clickSaveButton();
            approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
            approveInspectionsScreen.clickDoneButton();
            RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        }
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            myInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, false);
        myInspectionsScreen.clickCancel();
        myInspectionsScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToTeamInspectionsScreen();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, false);
        teamInspectionsScreen.clickCancel();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatApproveOptionIsPresentInMultiselectModeOnlyOneOrMoreNotApprovedInspectionsAreSelected(String rowID,
                                                                                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ArrayList<String> inspections = new ArrayList<>();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O02TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSPTYPE_FOR_SR_INSPTYPE);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            inspections.add(vehicleScreen.getInspectionNumber());
            RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
            RegularInspectionsSteps.saveInspection();
            RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        }
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspections.get(0));
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspections.get(0));
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        myInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            myInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, true);
        myInspectionsScreen.clickCancel();
        myInspectionsScreen.clickDoneButton();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToTeamInspectionsScreen();
        RegularTeamInspectionsScreen teamInspectionsScreen = new RegularTeamInspectionsScreen();
        teamInspectionsScreen.clickActionButton();
        for (String inspectionID : inspections) {
            teamInspectionsScreen.selectInspectionForAction(inspectionID);
        }
        myInspectionsScreen.clickActionButton();
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.APPROVE, true);
        teamInspectionsScreen.clickCancel();
        teamInspectionsScreen.clickDoneButton();
        teamInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanChangeApprovedStatusOfPercentageServiceToDeclinedOnlyUsingDeclineAllOption(String rowID,
                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        final String declineReason = "Decline 2";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSPECTION_ALL_SERVICES);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectService(inspectionData.getPercentageServiceData().getServiceName());
        RegularInspectionsSteps.saveInspectionAsFinal();

        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);

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
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneStatusReasonButton();
        myInspectionsScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionsOnDeviceViaAction(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> inspectionsID = new ArrayList<>();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        for (InspectionData inspectionData : testCaseData.getInspectionsData()) {
            RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
            RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
            vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
            vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
            vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
            vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
            inspectionsID.add(vehicleScreen.getInspectionNumber());
            RegularInspectionsSteps.saveInspection();
        }
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.clickActionButton();
        for (String inspectionNumber : inspectionsID) {
            myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        }

        myInspectionsScreen.clickApproveInspections();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);

        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        for (String inspectionNumber : inspectionsID) {
            approveInspectionsScreen.selectInspection(inspectionNumber);
            approveInspectionsScreen.clickApproveButton();
        }
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        for (String inspectionNumber : inspectionsID) {
            Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspectionNumber));
        }
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testApproveInspectionOnDevice(String rowID,
                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.INSP_NOTLA_TS_INSPTYPE);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        vehicleScreen.setTech(iOSInternalProjectConstants.EMPLOYEE_TECHNICIAN);
        final String inspNumber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForApprove(inspNumber);
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspNumber);
        approveInspectionsScreen.clickApproveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        Assert.assertTrue(myInspectionsScreen.isInspectionIsApproved(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }
}

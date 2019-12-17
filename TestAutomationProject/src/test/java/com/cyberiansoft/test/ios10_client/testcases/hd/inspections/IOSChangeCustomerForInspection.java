package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.MyInspectionsSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.hdclientsteps.QuestionsScreenSteps;
import com.cyberiansoft.test.ios10_client.hdvalidations.VehicleInfoValidations;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.MainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ClaimScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VisualInteriorScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSChangeCustomerForInspection extends IOSHDBaseTestCase {

    private WholesailCustomer _002_Test_Customer = new WholesailCustomer();
    private WholesailCustomer _003_Test_Customer = new WholesailCustomer();
    private RetailCustomer johnRetailCustomer = new RetailCustomer("John", "Simple_PO#_Req");

    @BeforeClass(description = "Change Customer for Inspections Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getChangeCustomerForInspectionsTestCasesDataPath();
        _002_Test_Customer.setCompanyName("002 - Test Company");
        _003_Test_Customer.setCompanyName("003 - Test Company");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForInspection(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        MainScreen mainScreeneen = homeScreen.clickLogoutButton();
        mainScreeneen.userLogin(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);

        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSP_CHANGE_INSPTYPE);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());

        vehicleScreen.saveWizard();
        myInspectionsScreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
        BaseUtils.waitABit(90 * 1000);
        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
        vehicleScreen.saveWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForInspectionAsChangeWholesaleToRetailAndViceVersa(String rowID,
                                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.INSP_CHANGE_INSPTYPE);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        vehicleScreen.saveWizard();

        myInspectionsScreen.selectInspectionInTable(inspectionnumber);
        myInspectionsScreen.clickChangeCustomerpopupMenu();
        myInspectionsScreen.customersPopupSwitchToRetailMode();
        myInspectionsScreen.selectCustomer(iOSInternalProjectConstants.JOHN_RETAIL_CUSTOMER);

        NavigationSteps.navigateBackScreen();
        customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();
        BaseUtils.waitABit(60 * 1000);
        homeScreen.clickMyInspectionsButton();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(johnRetailCustomer);
        vehicleScreen.saveWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testChangeCustomerOptionForInspectionsBasedOnTypeWithPreselectedCompanies(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        NavigationSteps.navigateBackScreen();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        NavigationSteps.navigateBackScreen();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(_002_Test_Customer, InspectionsTypes.TYPEWITHPRESELECTEDCOMPANIES_INSPTYPE);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        String inspectionnumber = vehicleScreen.getInspectionNumber();

        NavigationSteps.navigateToClaimScreen();
        ClaimScreen claimScreen = new ClaimScreen();
        claimScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimScreen.saveWizard();
        myInspectionsScreen.changeCustomerForInspection(inspectionnumber, iOSInternalProjectConstants.O03TEST__CUSTOMER);
        BaseUtils.waitABit(30 * 1000);
        myInspectionsScreen.selectInspectionForEdit(inspectionnumber);
        visualInteriorScreen.waitVisualScreenLoaded(VisualInteriorScreen.getVisualExteriorCaption());
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(_003_Test_Customer);
        vehicleScreen.saveWizard();
        NavigationSteps.navigateBackScreen();
    }
}

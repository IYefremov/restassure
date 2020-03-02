package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.SelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInspectionsQuestionFormsTestCases extends IOSHDBaseTestCase {

    @BeforeClass(description = "Inspections Question Forms Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInspectionsQFTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired(String rowID,
                                                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.slectServiceVehicleParts(inspectionData.getServiceData().getVehicleParts());
        SelectedServiceDetailsScreen selectedServiceDetailsScreen = new SelectedServiceDetailsScreen();
        final String selectedhehicleparts = selectedServiceDetailsScreen.getListOfSelectedVehicleParts();
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts())
            Assert.assertTrue(selectedhehicleparts.contains(vehiclePartData.getVehiclePartName()));
        selectedServiceDetailsScreen.saveSelectedServiceDetails();
        int i = 0;
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
            servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            i++;
            Assert.assertFalse(selectedServiceDetailsScreen.isQuestionFormCellExists());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired(String rowID,
                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        ServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());

        servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), 0);
        SelectedServiceDetailsScreen servicedetailsscreen = new SelectedServiceDetailsScreen();
        Assert.assertTrue(servicedetailsscreen.isQuestionFormCellExists());
        Assert.assertEquals(servicedetailsscreen.getQuestion2Value(), inspectionData.getServiceData().getQuestionData().getQuestionAnswer());
        servicedetailsscreen.saveSelectedServiceDetails();

        for (int i = 1; i < inspectionData.getServiceData().getVehicleParts().size(); i++) {
            servicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            Assert.assertFalse(servicedetailsscreen.isQuestionFormCellExists());
            servicedetailsscreen.saveSelectedServiceDetails();
        }
        servicesScreen.cancelWizard();
        myInspectionsScreen.clickHomeButton();
    }
}

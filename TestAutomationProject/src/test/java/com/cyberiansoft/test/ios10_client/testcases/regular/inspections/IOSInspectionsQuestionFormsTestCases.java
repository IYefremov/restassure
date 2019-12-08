package com.cyberiansoft.test.ios10_client.testcases.regular.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectedServiceDetailsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInspectionsQuestionFormsTestCases extends IOSRegularBaseTestCase {

    @BeforeClass(description = "Inspections Question Forms Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInspectionsQFTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatQuestionSectionIsShownPerServiceForFirstSelectedPanelWhenQFIsNotRequired(String rowID,
                                                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();

        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.openCustomServiceDetails(inspectionData.getServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.selectServiceVehicleParts(inspectionData.getServiceData().getVehicleParts());
        final String selectedhehicleparts = servicesScreen.getListOfSelectedVehicleParts();

        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts())
            Assert.assertTrue(selectedhehicleparts.contains(vehiclePartData.getVehiclePartName()));
        servicesScreen.clickSave();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        int i = 0;
        for (VehiclePartData vehiclePartData : inspectionData.getServiceData().getVehicleParts()) {
            selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            i++;
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            Assert.assertFalse(selectedServiceDetailsScreen.isQuestionFormCellExists());
            selectedServiceDetailsScreen.clickSaveButton();
        }
        selectedServicesScreen.clickSave();
        Helpers.acceptAlert();
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatQuestionSectionIsShownPerServiceWithMustPanelsWhenQuestionsAreRequired(String rowID,
                                                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_CALC);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getServiceData());

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), 0);
        RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
        Assert.assertTrue(selectedServiceDetailsScreen.isQuestionFormCellExists());
        Assert.assertEquals(selectedServiceDetailsScreen.getQuestion2Value(), inspectionData.getServiceData().getQuestionData().getQuestionAnswer());
        selectedServiceDetailsScreen.saveSelectedServiceDetails();

        for (int i = 1; i < inspectionData.getServiceData().getVehicleParts().size(); i++) {
            selectedServicesScreen.openServiceDetailsByIndex(inspectionData.getServiceData().getServiceName(), i);
            selectedServiceDetailsScreen = new RegularSelectedServiceDetailsScreen();
            Assert.assertFalse(selectedServiceDetailsScreen.isQuestionFormCellExists());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        RegularInspectionsSteps.saveInspection();
        RegularNavigationSteps.navigateBackScreen();
    }
}

package com.cyberiansoft.test.ios10_client.testcases.regular.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularApproveInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularNotesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularTeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularSelectedServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVisualInteriorScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.RegularNotesScreenValidations;
import com.cyberiansoft.test.ios10_client.testcases.regular.IOSRegularBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.wizardscreens.WizardScreenTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInspectionsNotesTestCases extends IOSRegularBaseTestCase {

    private RetailCustomer testRetailCustomer = new RetailCustomer("Automation", "Retail Customer");

    @BeforeClass(description = "Inspections Notes Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getInspectionsNotesTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatTextNotesAreCopiedToNewInspectionsWhenUseCopyAction(String rowID,
                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String notesText = "Test for copy";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.DEFAULT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumberber = vehicleScreen.getInspectionNumber();
        vehicleScreen.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes(notesText);
        notesScreen.clickSaveButton();
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsSteps.selectInspectionForCopy(inspectionNumberber);
        vehicleScreen.waitVehicleScreenLoaded();
        String copiedinspectionNumberber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(copiedinspectionNumberber));
        RegularMyInspectionsSteps.selectInspectionNotesMenu(copiedinspectionNumberber);
        RegularNotesScreenValidations.verifyTextNotesPresent(notesText);
        RegularNotesScreenSteps.saveNotes();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval(String rowID,
                                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final int timetowaitwo = 4;
        final String inspectionnotes = "Inspection notes";
        final String servicenotes = "Service Notes";
        final String locationFilterValue = "All locations";

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularQuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            if (serviceData.getServicePrice() != null)
                RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            else
                RegularServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);

        RegularNavigationSteps.navigateToVehicleInfoScreen();

        vehicleScreen.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes(inspectionnotes);
        notesScreen.clickSaveButton();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesScreen selectedServicesScreen = new RegularSelectedServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.clickNotes();
            RegularNotesScreenSteps.setTextNotes(servicenotes);
            RegularNotesScreenSteps.saveNotes();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }
        for (ServiceData serviceData : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedService(serviceData.getServiceName()));

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            RegularNavigationSteps.navigateToPriceMatrixScreen(priceMatrixScreenData.getMatrixScreenName());
            RegularPriceMatrixScreenSteps.selectPriceMatrixData(priceMatrixScreenData);
            RegularVehiclePartsScreenSteps.saveVehiclePart();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(inspectionNumber));
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        myInspectionsScreen.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        RegularApproveInspectionsScreen approveInspectionsScreen = new RegularApproveInspectionsScreen();
        approveInspectionsScreen.selectInspection(inspectionNumber);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.clickSingnAndDrawApprovalSignature();
        approveInspectionsScreen.clickDoneButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        RegularTeamWorkOrdersScreen teamWorkOrdersScreen = new RegularTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickHomeButton();

        for (int i = 0; i < timetowaitwo; i++) {
            Helpers.waitABit(60 * 1000);
            RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
            teamWorkOrdersScreen.clickHomeButton();
        }
        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.setSearchType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR.getWorkOrderTypeName());
        teamWorkOrdersScreen.selectSearchLocation(locationFilterValue);
        teamWorkOrdersScreen.clickSearchSaveButton();

        final String workOrderNumber = teamWorkOrdersScreen.getFirstWorkOrderNumberValue();
        BaseUtils.waitABit(45 * 1000);
        teamWorkOrdersScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToTeamWorkOrdersScreen();
        teamWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);

        Assert.assertEquals(vehicleScreen.getEst(), inspectionNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        vehicleScreen.clickNotesButton();
        Assert.assertTrue(notesScreen.getNotesValue().length() > 0);
        notesScreen.clickSaveButton();

        servicesScreen.waitServicesScreenLoaded();
        RegularServicesScreenSteps.switchToSelectedServices();
        for (ServiceData serviceData : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isNotesIconPresentForSelectedWorkOrderService(serviceData.getServiceName()));
        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        teamWorkOrdersScreen.waitTeamWorkOrdersScreenLoaded();
        teamWorkOrdersScreen.clickHomeButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditRetailInspectionNotes(String rowID,
                                              String description, JSONObject testData) {
        final String _notes1 = "Test\nTest 2";
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularHomeScreen homeScreen = new RegularHomeScreen();
        RegularCustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToRetailMode();
        customersScreen.clickHomeButton();
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(testRetailCustomer, InspectionsTypes.DEFAULT);
        RegularVehicleScreen vehicleScreen = new RegularVehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        vehicleScreen.setMakeAndModel(inspectionData.getVehicleInfo().getVehicleMake(), inspectionData.getVehicleInfo().getVehicleModel());
        vehicleScreen.setColor(inspectionData.getVehicleInfo().getVehicleColor());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        RegularInspectionsSteps.saveInspectionAsDraft();

        RegularMyInspectionsSteps.selectInspectionForEdit(inspectionNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        RegularNavigationSteps.navigateToVisualScreen(WizardScreenTypes.VISUAL_INTERIOR);
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        visualInteriorScreen.clickNotesButton();
        RegularNotesScreen notesScreen = new RegularNotesScreen();
        notesScreen.setNotes(_notes1);
        notesScreen.addQuickNotes();
        notesScreen.clickSaveButton();
        visualInteriorScreen.waitVisualScreenLoaded(WizardScreenTypes.VISUAL_INTERIOR.getDefaultScreenTypeName());
        visualInteriorScreen.clickNotesButton();
        Assert.assertEquals(notesScreen.getNotesAndQuickNotes(), _notes1 + "\n" + notesScreen.quicknotesvalue);
        notesScreen.clickSaveButton();
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }
}

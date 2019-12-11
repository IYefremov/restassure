package com.cyberiansoft.test.ios10_client.testcases.hd.inspections;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.hdclientsteps.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.CustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.basescreens.SettingsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.MyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens.TeamWorkOrdersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.ServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VehicleScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens.VisualInteriorScreen;
import com.cyberiansoft.test.ios10_client.testcases.hd.IOSHDBaseTestCase;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.InspectionsTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.WorkOrdersTypes;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.ScreenNamesConstants;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IOSInspectionsNotesTestCases extends IOSHDBaseTestCase {

    private RetailCustomer johnRetailCustomer = new RetailCustomer("John", "Simple_PO#_Req");

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

        HomeScreen homeScreen = new HomeScreen();
        SettingsScreen settingscreen = homeScreen.clickSettingsButton();
        settingscreen.setInspectionToNonSinglePageInspection();
        settingscreen.clickHomeButton();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.DEFAULT);
        VehicleInfoScreenSteps.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        WizardScreensSteps.clickNotesButton();
        NotesScreen notesScreen = new NotesScreen();
        notesScreen.setNotes(notesText);
        notesScreen.clickSaveButton();
        InspectionsSteps.saveInspectionAsFinal();
        myInspectionsScreen.selectInspectionForCopy(inspectionNumber);

        String copiedInspectionNumber = VehicleInfoScreenSteps.getInspectionNumber();
        InspectionsSteps.saveInspectionAsFinal();

        Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(copiedInspectionNumber));
        notesScreen = myInspectionsScreen.openInspectionNotesScreen(copiedInspectionNumber);
        Assert.assertEquals(notesScreen.getAddedNotesText(), notesText);
        notesScreen.clickSaveButton();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsVerifyThatServiceLevelNotesAreCopiedFromInspectionToWOWhenItIsAutoCreatedAfterApproval(String rowID,
                                                                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final int timetowaitwo = 4;
        final String inspectionNotes = "Inspection notes";
        final String serviceNotes = "Service Notes";
        final String locationFilterValue = "All locations";
        final String searchStatus = "New";

        HomeScreen homeScreen = new HomeScreen();
        CustomersScreen customersScreen = homeScreen.clickCustomersButton();
        customersScreen.swtchToWholesaleMode();
        customersScreen.selectCustomerWithoutEditing(iOSInternalProjectConstants.O03TEST__CUSTOMER);
        SettingsScreen settingsScreen = homeScreen.clickSettingsButton();
        settingsScreen.setInspectionToNonSinglePageInspection();
        settingsScreen.clickHomeButton();

        MyInspectionsScreen myInspectionsScreen = homeScreen.clickMyInspectionsButton();
        MyInspectionsSteps.startCreatingInspection(InspectionsTypes.INSP_FOR_AUTO_WO_LINE_APPR_MULTISELECT);
        VisualInteriorScreen visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
        NavigationSteps.navigateToVehicleInfoScreen();
        VehicleScreen vehicleScreen = new VehicleScreen();
        vehicleScreen.setVIN(inspectionData.getVehicleInfo().getVINNumber());
        final String inspectionNumber = vehicleScreen.getInspectionNumber();
        QuestionsScreenSteps.goToQuestionsScreenAndAnswerQuestions(inspectionData.getQuestionScreenData());
        NavigationSteps.navigateToServicesScreen();
        ServicesScreen servicesScreen = new ServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            if (serviceData.getServicePrice() != null)
                ServicesScreenSteps.selectServiceWithServiceData(serviceData);
            else
                ServicesScreenSteps.selectService(serviceData.getServiceName());
        }
        servicesScreen.saveWizard();

        myInspectionsScreen.selectInspectionForEdit(inspectionNumber);
        visualInteriorScreen = new VisualInteriorScreen();
        visualInteriorScreen.waitVisualScreenLoaded(ScreenNamesConstants.FUTURE_SPORT_CAR);
        NavigationSteps.navigateToVehicleInfoScreen();
        WizardScreensSteps.clickNotesButton();
        NotesScreen notesScreen = new NotesScreen();
        notesScreen.setNotes(inspectionNotes);
        notesScreen.clickSaveButton();

        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            SelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
            notesScreen = servicedetailsscreen.clickNotesCell();
            notesScreen.setNotes(serviceNotes);
            notesScreen.clickSaveButton();
            servicedetailsscreen.saveSelectedServiceDetails();
        }
        for (ServiceData serviceData : inspectionData.getServicesList())
            Assert.assertTrue(servicesScreen.isNotesIconPresentForSelectedService(serviceData.getServiceName()));

        for (PriceMatrixScreenData priceMatrixScreenData : inspectionData.getPriceMatrixScreensData()) {
            PriceMatrixScreenSteps.goTopriceMatrixScreenAndSelectPriceMatrixData(priceMatrixScreenData);
        }
        vehicleScreen.saveWizard();

        Assert.assertTrue(myInspectionsScreen.isNotesIconPresentForInspection(inspectionNumber));
        myInspectionsScreen.selectInspectionForAction(inspectionNumber);
        SelectEmployeePopup selectEmployeePopup = new SelectEmployeePopup();
        selectEmployeePopup.selectEmployeeAndTypePassword(iOSInternalProjectConstants.MAN_INSP_EMPLOYEE, iOSInternalProjectConstants.USER_PASSWORD);
        ApproveInspectionsScreen approveInspectionsScreen = new ApproveInspectionsScreen();
        approveInspectionsScreen.selectInspectionForApprove(inspectionNumber);
        approveInspectionsScreen.clickApproveAllServicesButton();
        approveInspectionsScreen.clickSaveButton();
        approveInspectionsScreen.drawSignatureAfterSelection();
        approveInspectionsScreen.clickDoneButton();

        homeScreen = myInspectionsScreen.clickHomeButton();

        TeamWorkOrdersScreen teamWorkOrdersScreen = homeScreen.clickTeamWorkordersButton();
        homeScreen = teamWorkOrdersScreen.clickHomeButton();

        for (int i = 0; i < timetowaitwo; i++) {
            Helpers.waitABit(60 * 1000);
            teamWorkOrdersScreen = homeScreen.clickTeamWorkordersButton();
            homeScreen = teamWorkOrdersScreen.clickHomeButton();
        }
        homeScreen.clickTeamWorkordersButton();
        teamWorkOrdersScreen.clickSearchButton();
        teamWorkOrdersScreen.selectSearchStatus(searchStatus);
        teamWorkOrdersScreen.setSearchType(WorkOrdersTypes.WO_TYPE_FOR_MONITOR.getWorkOrderTypeName());
        teamWorkOrdersScreen.selectSearchLocation(locationFilterValue);
        teamWorkOrdersScreen.clickSearchSaveButton();

        final String workOrderNumber = teamWorkOrdersScreen.getFirstWorkOrderNumberValue();
        teamWorkOrdersScreen.selectWorkOrderForEidt(workOrderNumber);
        vehicleScreen.waitVehicleScreenLoaded();
        WizardScreensSteps.clickNotesButton();
        Assert.assertEquals(notesScreen.getNotesValue(), inspectionNotes);
        notesScreen.clickSaveButton();
        Assert.assertEquals(vehicleScreen.getEst(), inspectionNumber);
        NavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            SelectedServiceDetailsScreen servicedetailsscreen = servicesScreen.openServiceDetails(serviceData.getServiceName());
            notesScreen = servicedetailsscreen.clickNotesCell();
            Assert.assertEquals(notesScreen.getNotesValue(), serviceNotes);
            notesScreen.clickSaveButton();
            servicedetailsscreen.saveSelectedServiceDetails();
            Assert.assertTrue(servicesScreen.isNotesIconPresentForSelectedWorkOrderService(serviceData.getServiceName()));
        }
        servicesScreen.cancelWizard();
        NavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testEditRetailInspectionNotes(String rowID,
                                              String description, JSONObject testData) {
        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String _notes1 = "Test\nTest 2";
        final String quickNote = "This is test Quick Notes";

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

        myInspectionsScreen.selectInspectionForEdit(inspNumber);
        VehicleInfoScreenSteps.waitVehicleScreenLoaded();
        WizardScreensSteps.clickNotesButton();
        NotesScreen notesScreen = new NotesScreen();
        notesScreen.setNotes(_notes1);
        notesScreen.addQuickNotes(quickNote);

        notesScreen.clickSaveButton();
        vehicleScreen.clickNotesButton();
        Assert.assertEquals(notesScreen.getNotesValue(), _notes1 + "\n" + quickNote);
        notesScreen.clickSaveButton();
        vehicleScreen.cancelOrder();
        NavigationSteps.navigateBackScreen();

    }
}

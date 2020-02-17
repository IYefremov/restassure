package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.NotesSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

public class VNextInspectionsNotesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    @BeforeClass(description = "R360 Inspection Notes Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInspectionsNotessTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyQuickNotesAreAddedAsNewLinesOfText(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Alum Hood";
        final String quickNoteText2 = "Left Fender";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceData().getServiceName());
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceData().getServiceName());
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);

        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        inspectionsScreen = selectedServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testValidateTextContentIsValidatedOnTappingBackButton(String rowID,
                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String notetext = "abcd%:?*()текст";
        final String noteTextValid = "abcd%:?*()";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(0));
        availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(1));
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
        notesScreen.setNoteText(notetext);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();

        selectedServicesScreen = new VNextSelectedServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
        NotesSteps.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        selectedServicesScreen = new VNextSelectedServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = selectedServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTextContentIsValidatedOnTappingHardwareBackButton(String rowID,
                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String notetext = "abcd%:?*()текст";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(0));
        availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(1));
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
        notesScreen.setNoteText(notetext);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();

        selectedServicesScreen = new VNextSelectedServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
        NotesSteps.verifyNoteIsPresent(notetext);
        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        inspectionsScreen = selectedServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyNotesOptionIsAvailableAtDifferentWizardSteps(String rowID,
                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final int notesToAdd = 9;

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        for (int i = 0; i < notesToAdd; i++) {
            availableServicesScreen.clickInspectionNotesOption();
            ScreenNavigationSteps.pressBackButton();

            availableServicesScreen.swipeScreenLeft();
        }

        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddTextNotesForServiceInTheList(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceData().getServiceName());
        NotesSteps.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(serviceDetailsScreen.getServiceNotesValue(), noteTextValid);
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddQuickNoteForServiceInTheList(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Alum Hood";
        final String quickNoteText2 = "Left Fender";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceData().getServiceName());
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceNotesOption();
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        inspectionsScreen = selectedServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddSeveralQuickNotesForServiceInTheList(String rowID,
                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Alum Hood";
        final String quickNoteText2 = "Left Fender";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

        selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceData().getServiceName());
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesSteps.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveTextNoteOnTappingBackButtonEstimationLevel(String rowID,
                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.clickInspectionNotesOption();
        NotesSteps.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveTextNoteOnTappingBackButtonEstimationLevel_CheckAfterInspectionSave(String rowID,
                                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteTextValid = "abcd%:?*()";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.saveInspection();

        inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.clickInspectionNotesOption();
        NotesSteps.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveTextNoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.clickInspectionNotesOption();
        NotesSteps.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSavePhotoAsANoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteTextValid = "abcd%:?*()";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        NotesSteps.addPhotoFromCamera();
        BaseUtils.waitABit(2000);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.clickInspectionNotesOption();
        NotesSteps.verifyNoteIsPresent(noteTextValid);
        NotesSteps.verifyPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSavePictureNoteOnTappingBackButtonEstimationLevel(String rowID,
                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        NotesSteps.addPhotoFromCamera();
        BaseUtils.waitABit(2000);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();

        inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.clickInspectionNotesOption();
        NotesSteps.verifyNoteIsPresent(noteTextValid);
        NotesSteps.verifyPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testClearTextNotesForServiceInTheList(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Alum Hood";
        final String quickNoteText2 = "Left Fender";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceData().getServiceName());
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        new VNextSelectedServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();

        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceData().getServiceName());
        notesScreen.getClearNoteButton().click();
        NotesSteps.verifyNoteIsPresent("");
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceNotesOption();
        NotesSteps.verifyNoteIsPresent("");
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        selectedServicesScreen = new VNextSelectedServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionsScreen = selectedServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithMoneyServiceImageNotes(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final int addedpictures = 1;

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (ServiceData serviceAdd : inspectionData.getServicesList())
            availableServicesScreen.selectService(serviceAdd.getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData serviceAdd : inspectionData.getServicesList()) {
            VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(serviceAdd.getServiceName());
            NotesSteps.addPhotoFromCamera();
            NotesSteps.verifyPicturesPresent();
            ScreenNavigationSteps.pressBackButton();
            VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
            serviceDetailsScreen.clickServiceDetailsDoneButton();
        }

        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();

        BaseUtils.waitABit(30000);
        WebDriver webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftmenu.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
        for (ServiceData serviceAdd : inspectionData.getServicesList()) {
            Assert.assertTrue(inspectionsWebPage.isServicePresentForSelectedInspection(serviceAdd.getServiceName()));
            Assert.assertTrue(inspectionsWebPage.isServiceNotesIconDisplayed(serviceAdd.getServiceName()));
            Assert.assertTrue(inspectionsWebPage.isImageExistsForServiceNote(serviceAdd.getServiceName()));
        }
        webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithMatrixServicesImageNotes(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final int addedPictures = 1;

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
            VNextVehiclePartInfoPage vNextVehiclePartInfoPage = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vNextVehiclePartInfoPage.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vNextVehiclePartInfoPage.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            VNextNotesScreen notesScreen = vNextVehiclePartInfoPage.clickMatrixServiceNotesOption();
            NotesSteps.addPhotoFromCamera();
            NotesSteps.verifyPicturesPresent();
            ScreenNavigationSteps.pressBackButton();
            ScreenNavigationSteps.pressBackButton();
            vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        }
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getMatrixServiceData().getMatrixServiceName()));
        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        homeScreen = inspectionsScreen.clickBackButton();
        homeScreen.waitUntilQueueMessageInvisible();

        BaseUtils.waitABit(45000);

        WebDriver webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
        Assert.assertTrue(inspectionsWebPage.isMatrixServiceExists(inspectionData.getMatrixServiceData().getMatrixServiceName()));
        List<WebElement> matrixSepviseRows = inspectionsWebPage.getAllMatrixServicesRows();
        Assert.assertEquals(matrixSepviseRows.size(), inspectionData.getMatrixServiceData().getVehiclePartsData().size());
        for (WebElement matrixServiceeRow : matrixSepviseRows) {
            Assert.assertTrue(inspectionsWebPage.isImageExistsForMatrixServiceNotes(matrixServiceeRow));
        }
        webdriver.quit();
    }
}

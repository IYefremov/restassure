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
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnext.validations.NotesValidations;
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

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);

        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testValidateTextContentIsValidatedOnTappingBackButton(String rowID,
                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String notetext = "abcd%:?*()текст";
        final String noteTextValid = "abcd%:?*()";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(0));
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(1));
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceNameByIndex(1));
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesSteps.setNoteText(notetext);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();

        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceNameByIndex(1));
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesValidations.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTextContentIsValidatedOnTappingHardwareBackButton(String rowID,
                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String notetext = "abcd%:?*()текст";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(0));
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceNameByIndex(1));
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceNameByIndex(1));
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesSteps.setNoteText(notetext);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();

        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceNameByIndex(1));
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesValidations.verifyNoteIsPresent(notetext);
        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyNotesOptionIsAvailableAtDifferentWizardSteps(String rowID,
                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final int notesToAdd = 9;

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();

        for (int i = 0; i < notesToAdd; i++) {
            availableServicesScreen.clickInspectionNotesOption();
            ScreenNavigationSteps.pressBackButton();

            availableServicesScreen.swipeScreenLeft();
        }

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddTextNotesForServiceInTheList(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesSteps.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        Assert.assertEquals(serviceDetailsScreen.getServiceNotesValue(), noteTextValid);
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddQuickNoteForServiceInTheList(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Alum Hood";
        final String quickNoteText2 = "Left Fender";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceNotesOption();
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testAddSeveralQuickNotesForServiceInTheList(String rowID,
                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Alum Hood";
        final String quickNoteText2 = "Left Fender";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        SelectedServicesScreenSteps.switchToSelectedService();

        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        NotesValidations.verifyNoteIsPresent(noteText + '\n' + quickNoteText1 + '\n' + quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveTextNoteOnTappingBackButtonEstimationLevel(String rowID,
                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen.clickInspectionNotesOption();
        NotesValidations.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveTextNoteOnTappingBackButtonEstimationLevel_CheckAfterInspectionSave(String rowID,
                                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteTextValid = "abcd%:?*()";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        availableServicesScreen.clickInspectionNotesOption();
        NotesValidations.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSaveTextNoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen.clickInspectionNotesOption();
        NotesValidations.verifyNoteIsPresent(noteTextValid);
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSavePhotoAsANoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String noteTextValid = "abcd%:?*()";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        NotesSteps.addPhotoFromCamera();
        BaseUtils.waitABit(2000);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen.clickInspectionNotesOption();
        NotesValidations.verifyNoteIsPresent(noteTextValid);
        NotesValidations.verifyPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSavePictureNoteOnTappingBackButtonEstimationLevel(String rowID,
                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteTextValid = "abcd%:?*()";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(noteTextValid);
        NotesSteps.addPhotoFromCamera();
        BaseUtils.waitABit(2000);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen.saveInspectionViaMenu();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        availableServicesScreen.clickInspectionNotesOption();
        NotesValidations.verifyNoteIsPresent(noteTextValid);
        NotesValidations.verifyPicturesPresent();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testClearTextNotesForServiceInTheList(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String noteText = UUID.randomUUID().toString();
        final String quickNoteText1 = "Alum Hood";
        final String quickNoteText2 = "Left Fender";

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openServiceNotes();
        NotesSteps.setNoteText(noteText);
        NotesSteps.addQuickNote(quickNoteText1);
        NotesSteps.addQuickNote(quickNoteText2);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        new VNextSelectedServicesScreen();
        availableServicesScreen.saveInspectionViaMenu();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        availableServicesScreen.switchToSelectedServicesView();
        SelectedServicesScreenSteps.openServiceDetails(inspectionData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openServiceNotes();
        //notesScreen.getClearNoteButton().click();
        NotesValidations.verifyNoteIsPresent("");
        ScreenNavigationSteps.pressBackButton();
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickServiceNotesOption();
        NotesValidations.verifyNoteIsPresent("");
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithMoneyServiceImageNotes(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final int addedpictures = 1;

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        for (ServiceData serviceAdd : inspectionData.getServicesList())
            AvailableServicesScreenSteps.selectService(serviceAdd);
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData serviceAdd : inspectionData.getServicesList()) {
            SelectedServicesScreenSteps.openServiceDetails(serviceAdd.getServiceName());
            ServiceDetailsScreenSteps.openServiceNotes();
            NotesSteps.addPhotoFromCamera();
            NotesValidations.verifyPicturesPresent();
            ScreenNavigationSteps.pressBackButton();
            VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen();
            serviceDetailsScreen.clickServiceDetailsDoneButton();
        }

        availableServicesScreen.saveInspectionViaMenu();
        ScreenNavigationSteps.pressBackButton();

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

        HomeScreenSteps.openInspections();
        final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
            VNextVehiclePartInfoPage vNextVehiclePartInfoPage = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vNextVehiclePartInfoPage.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vNextVehiclePartInfoPage.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            VNextNotesScreen notesScreen = vNextVehiclePartInfoPage.clickMatrixServiceNotesOption();
            NotesSteps.addPhotoFromCamera();
            NotesValidations.verifyPicturesPresent();
            ScreenNavigationSteps.pressBackButton();
            ScreenNavigationSteps.pressBackButton();
            vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        }
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();

        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        ListServicesValidations.verifyServiceSelected(inspectionData.getMatrixServiceData().getMatrixServiceName(), true);
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
        //homeScreen.waitUntilQueueMessageInvisible();

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

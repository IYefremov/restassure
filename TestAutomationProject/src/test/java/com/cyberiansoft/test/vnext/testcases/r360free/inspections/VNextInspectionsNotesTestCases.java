package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
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
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextInspectionsNotesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Inspection Notes Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInspectionsNotessTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testValidateMakeFieldOnVehicleScreenReflectsVisibleONOFF(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quickNotesList = notesScreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quickNotesList)
			notes.add(note.getText());
		for (int i =0; i< notesToAdd; i++) {
			notesScreen.addQuickNote(notes.get(i));
		}
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());


		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectedNotes = notesScreen.getSelectedNotes();
		for (String note : notes)
			Assert.assertTrue(selectedNotes.contains(note));
		notesScreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyQuickNotesAreAddedAsNewLinesOfText(String rowID,
															 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quickNotesList = notesScreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quickNotesList)
			notes.add(note.getText());
		for (int i =0; i< notesToAdd; i++) {
			notesScreen.addQuickNote(notes.get(i));
		}
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectedNotes = notesScreen.getSelectedNotes();
		String notestocompare = "";
		for (int i =0; i< notesToAdd; i++) {
			notestocompare = notestocompare + notes.get(i) + "\n";
		}

		Assert.assertEquals(selectedNotes, notestocompare.trim());
		notesScreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testValidateTextContentIsValidatedOnTappingBackButton(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String notetext = "abcd%:?*()текст";
		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(0));
		availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(1));
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		notesScreen.setNoteText(notetext);
		notesScreen.clickNotesBackButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		notesScreen.setNoteText(noteTextValid);
		notesScreen.clickNotesBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteTextValid);
		notesScreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTextContentIsValidatedOnTappingHardwareBackButton(String rowID,
																			String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String notetext = "abcd%:?*()текст";
		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(0));
		availableServicesScreen.selectService(inspectionData.getServiceNameByIndex(1));
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		notesScreen.setNoteText(notetext);
		notesScreen.clickNotesBackButton();
		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		msg = informationDialog.getInformationDialogMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		notesScreen.setNoteText(noteTextValid);
		notesScreen.clickNotesBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteTextValid);
		notesScreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyNotesOptionIsAvailableAtDifferentWizardSteps(String rowID,
																	   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 9;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		for (int i=0; i<notesToAdd; i++) {
			availableServicesScreen.clickInspectionNotesOption();
			AppiumUtils.clickHardwareBackButton();

			availableServicesScreen.swipeScreenLeft();
		}

		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddTextNotesForServiceInTheList(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.addNotesToSelectedService(inspectionData.getServiceName(), noteTextValid);
		
		Assert.assertEquals(selectedServicesScreen.getSelectedServiceNotesValue(inspectionData.getServiceName()), noteTextValid);

		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddQuickNoteForServiceInTheList(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quickNotesList = notesScreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quickNotesList)
			notes.add(note.getText());
		notesScreen.addQuickNote(notes.get(0));
		notesScreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectedNotes = notesScreen.getSelectedNotes();
		Assert.assertTrue(selectedNotes.contains(notes.get(0)));
		notesScreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddSeveralQuickNotesForServiceInTheList(String rowID,
															String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		//VNextServiceDetailsScreen servicedetailsscreen = availableServicesScreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quickNotesList = notesScreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quickNotesList) {
			notes.add(note.getText());
		}
		for (int i = 0; i < notesToAdd; i++)
			notesScreen.addQuickNote(notes.get(i));
		notesScreen.clickNotesBackButton();
		new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < notesToAdd; i++)
			Assert.assertTrue(selectedServicesScreen.getSelectedServiceNotesValue(inspectionData.getServiceName()).trim().contains(notes.get(i).trim()));

		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectedNotes = notesScreen.getSelectedNotes();
		for (int i = 0; i < notesToAdd; i++)
			Assert.assertTrue(selectedNotes.contains(notes.get(i)));
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveTextNoteOnTappingBackButtonEstimationLevel(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
		notesScreen.setNoteText(noteTextValid);
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = availableServicesScreen.clickInspectionNotesOption();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteTextValid);
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveTextNoteOnTappingBackButtonEstimationLevel_CheckAfterInspectionSave(String rowID,
																							String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
		notesScreen.setNoteText(noteTextValid);
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.clickSaveInspectionMenuButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());

		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = availableServicesScreen.clickInspectionNotesOption();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteTextValid);
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveTextNoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
																		   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
		notesScreen.setNoteText(noteTextValid);
		AppiumUtils.clickHardwareBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = availableServicesScreen.clickInspectionNotesOption();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteTextValid);
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavePhotoAsANoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
																			   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
		notesScreen.setNoteText(noteTextValid);
		notesScreen.addCameraPictureToNote();
		BaseUtils.waitABit(2000);
		notesScreen.selectNotesTextTab();
		AppiumUtils.clickHardwareBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = availableServicesScreen.clickInspectionNotesOption();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteTextValid);
		notesScreen.selectNotesPicturesTab();
		Assert.assertTrue(notesScreen.isPictureaddedToNote());
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavePictureNoteOnTappingBackButtonEstimationLevel(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteTextValid = "abcd%:?*()";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
		notesScreen.setNoteText(noteTextValid);
		notesScreen.addCameraPictureToNote();
		BaseUtils.waitABit(2000);
		notesScreen.selectNotesTextTab();
		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();

		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = availableServicesScreen.clickInspectionNotesOption();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteTextValid);
		notesScreen.selectNotesPicturesTab();
		Assert.assertTrue(notesScreen.isPictureaddedToNote());

		notesScreen.clickNotesBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testClearTextNotesForServiceInTheList(String rowID,
													  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;
		final String emptyNotes = "";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		//VNextServiceDetailsScreen servicedetailsscreen = availableServicesScreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quickNotesList = notesScreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quickNotesList)
			notes.add(note.getText());
		for (int i = 0; i < notesToAdd; i++)
			notesScreen.addQuickNote(notes.get(i));
		AppiumUtils.clickHardwareBackButton();

		new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		notesScreen.clickClearNotesButton();
		Assert.assertEquals(notesScreen.getSelectedNotes(), emptyNotes);
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		Assert.assertEquals(notesScreen.getSelectedNotes(), emptyNotes);
		notesScreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen = selectedServicesScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavePictureNoteFromGalleryOnTappingBackButtonEstimationLevel(String rowID,
																				 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int addedpictures = 1;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		VNextNotesScreen notesScreen = vehicleInfoScreen.clickInspectionNotesOption();
		notesScreen.selectNotesPicturesTab();
		notesScreen.addFakeImageNote();
		notesScreen.clickAllowIfAppears();
		notesScreen.clickNotesBackButton();
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesScreen = vehicleInfoScreen.clickInspectionNotesOption();
		//notesScreen.selectNotesPicturesTab();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), addedpictures);
		AppiumUtils.clickHardwareBackButton();
		//notesScreen = new VNextNotesScreen(DriverBuilder.getInstance().getAppiumDriver());
		//notesScreen.clickNotesBackButton();
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionWithMoneyServiceImageNotes(String rowID,
															   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int addedpictures = 1;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (ServiceData serviceAdd : inspectionData.getServicesList())
			availableServicesScreen.selectService(serviceAdd.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		for (ServiceData serviceAdd : inspectionData.getServicesList()) {
			VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(serviceAdd.getServiceName());
			notesScreen.addFakeImageNote();
			Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), addedpictures);
			notesScreen.clickNotesBackButton();
			selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		}

		inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickBackButton();

		BaseUtils.waitABit(30000);
		WebDriver webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionWithMatrixServicesImageNotes(String rowID,
																 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int addedPictures = 1;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextVehicleInfoScreen vehicleInfoScreen =  inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextPriceMatrixesScreen pricematrixesscreen = availableServicesScreen.openMatrixServiceDetails(inspectionData.getMatrixServiceData().getMatrixServiceName());
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(inspectionData.getMatrixServiceData().getHailMatrixName());
		for (VehiclePartData vehiclePartData : inspectionData.getMatrixServiceData().getVehiclePartsData()) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
			vehiclepartinfoscreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
			vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
			VNextNotesScreen notesScreen = vehiclepartinfoscreen.clickMatrixServiceNotesOption();
			//notesScreen.selectNotesPicturesTab();
			notesScreen.addFakeImageNote();
			Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), addedPictures);
			notesScreen.clickNotesBackButton();
			vehiclepartinfoscreen = new VNextVehiclePartInfoPage(DriverBuilder.getInstance().getAppiumDriver());
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
			vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		}
		availableServicesScreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getMatrixServiceData().getMatrixServiceName()));
		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		homeScreen = inspectionsScreen.clickBackButton();
		homeScreen.waitUntilQueueMessageInvisible();

		BaseUtils.waitABit(30000);

		WebDriver webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginScreenWebPage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginScreenWebPage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
		Assert.assertTrue(inspectionsWebPage.isMatrixServiceExists(inspectionData.getMatrixServiceData().getMatrixServiceName()));
		List<WebElement> matrixSepviseRows = inspectionsWebPage.getAllMatrixServicesRows(inspectionData.getMatrixServiceData().getMatrixServiceName());
		Assert.assertEquals(matrixSepviseRows.size(), inspectionData.getMatrixServiceData().getVehiclePartsData().size());
		for (WebElement matrixServiceeRow : matrixSepviseRows) {
			Assert.assertTrue(inspectionsWebPage.isImageExistsForMatrixServiceNotes(matrixServiceeRow));
		}
		webdriver.quit();
	}
}
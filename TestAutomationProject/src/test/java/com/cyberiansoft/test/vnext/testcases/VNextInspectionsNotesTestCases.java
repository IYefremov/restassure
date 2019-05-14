package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.HailMatrixService;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextInspectionsNotesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360-inspections-notes-testcases-data.json";


	@BeforeClass(description="R360 Inspection Notes Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testValidateMakeFieldOnVehicleScreenReflectsVisibleONOFF(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quicknoteslist)
			notes.add(note.getText());
		for (int i =0; i< notesToAdd; i++) {
			notesscreen.addQuickNote(notes.get(i));
		}
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());


		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectednotes = notesscreen.getSelectedNotes();
		for (String note : notes)
			Assert.assertTrue(selectednotes.contains(note));
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyQuickNotesAreAddedAsNewLinesOfText(String rowID,
															 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quicknoteslist)
			notes.add(note.getText());
		for (int i =0; i< notesToAdd; i++) {
			notesscreen.addQuickNote(notes.get(i));
		}
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectednotes = notesscreen.getSelectedNotes();
		String notestocompare = "";
		for (int i =0; i< notesToAdd; i++) {
			notestocompare = notestocompare + notes.get(i) + "\n";
		}

		Assert.assertEquals(selectednotes, notestocompare.trim());
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testValidateTextContentIsValidatedOnTappingBackButton(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String notetext = "abcd%:?*()текст";
		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(0));
		inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(1));
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
		VNextInformationDialog infodialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		infodialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTextContentIsValidatedOnTappingHardwareBackButton(String rowID,
																			String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String notetext = "abcd%:?*()текст";
		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(0));
		inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(1));
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
		VNextInformationDialog infodialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		infodialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		msg = infodialog.getInformationDialogMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceNameByIndex(1));
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyNotesOptionIsAvailableAtDifferentWizardSteps(String rowID,
																	   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 9;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();

		for (int i=0; i<notesToAdd; i++) {
			inspservicesscreen.clickInspectionNotesOption();
			AppiumUtils.clickHardwareBackButton();

			inspservicesscreen.swipeScreenLeft();
		}

		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddTextNotesForServiceInTheList(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.addNotesToSelectedService(inspectionData.getServiceName(), notetextvalid);
		/*VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickHardwareBackButton();
		//notesscreen.clickHardwareBackButton();

		servicedetailsscreen = new VNextServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());
		servicedetailsscreen.clickServiceDetailsDoneButton();*/
		Assert.assertEquals(selectedServicesScreen.getSelectedServiceNotesValue(inspectionData.getServiceName()), notetextvalid);

		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddQuickNoteForServiceInTheList(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quicknoteslist)
			notes.add(note.getText());
		notesscreen.addQuickNote(notes.get(0));
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectednotes = notesscreen.getSelectedNotes();
		Assert.assertTrue(selectednotes.contains(notes.get(0)));
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testAddSeveralQuickNotesForServiceInTheList(String rowID,
															String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quicknoteslist) {
			notes.add(note.getText());
		}
		for (int i = 0; i < notesToAdd; i++)
			notesscreen.addQuickNote(notes.get(i));
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < notesToAdd; i++)
			Assert.assertTrue(selectedServicesScreen.getSelectedServiceNotesValue(inspectionData.getServiceName()).trim().contains(notes.get(i).trim()));

		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		final String selectednotes = notesscreen.getSelectedNotes();
		for (int i = 0; i < notesToAdd; i++)
			Assert.assertTrue(selectednotes.contains(notes.get(i)));
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveTextNoteOnTappingBackButtonEstimationLevel(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveTextNoteOnTappingBackButtonEstimationLevel_CheckAfterInspectionSave(String rowID,
																							String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspservicesscreen.clickSaveInspectionMenuButton();
		inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());

		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveTextNoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
																		   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		AppiumUtils.clickHardwareBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavePhotoAsANoteOnTappingHardwareBackButtonEstimationLevel(String rowID,
																			   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.addCameraPictureToNote();
		BaseUtils.waitABit(2000);
		notesscreen.selectNotesTextTab();
		AppiumUtils.clickHardwareBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.selectNotesPicturesTab();
		Assert.assertTrue(notesscreen.isPictureaddedToNote());
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavePictureNoteOnTappingBackButtonEstimationLevel(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String notetextvalid = "abcd%:?*()";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.addCameraPictureToNote();
		BaseUtils.waitABit(2000);
		notesscreen.selectNotesTextTab();
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();

		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.selectNotesPicturesTab();
		Assert.assertTrue(notesscreen.isPictureaddedToNote());

		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testClearTextNotesForServiceInTheList(String rowID,
													  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int notesToAdd = 3;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quicknoteslist)
			notes.add(note.getText());
		for (int i = 0; i < notesToAdd; i++)
			notesscreen.addQuickNote(notes.get(i));
		AppiumUtils.clickHardwareBackButton();

		new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		notesscreen.clickClearNotesButton();
		Assert.assertEquals(notesscreen.getSelectedNotes(), "");
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesscreen = selectedServicesScreen.clickServiceNotesOption(inspectionData.getServiceName());
		Assert.assertEquals(notesscreen.getSelectedNotes(), "");
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSavePictureNoteFromGalleryOnTappingBackButtonEstimationLevel(String rowID,
																				 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int addedpictures = 1;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextNotesScreen notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		notesscreen.selectNotesPicturesTab();
		notesscreen.addFakeImageNote();
		notesscreen.clickAllowIfAppears();
		notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		//notesscreen.selectNotesPicturesTab();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
		AppiumUtils.clickHardwareBackButton();
		//notesscreen = new VNextNotesScreen(DriverBuilder.getInstance().getAppiumDriver());
		//notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionWithMoneyServiceImageNotes(String rowID,
															   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int addedpictures = 1;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (ServiceData serviceadd : inspectionData.getServicesList())
			inspservicesscreen.selectService(serviceadd.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (ServiceData serviceadd : inspectionData.getServicesList()) {
			VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(serviceadd.getServiceName());
			notesscreen.addFakeImageNote();
			Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
			notesscreen.clickNotesBackButton();
			selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		}

		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspectionsscreen.clickBackButton();

		BaseUtils.waitABit(30000);
		WebDriver webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspNumber);
		for (ServiceData serviceadd : inspectionData.getServicesList()) {
			Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection(serviceadd.getServiceName()));
			Assert.assertTrue(inspectionspage.isServiceNotesIconDisplayed(serviceadd.getServiceName()));
			Assert.assertTrue(inspectionspage.isImageExistsForServiceNote(serviceadd.getServiceName()));
		}
		webdriver.quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionWithMatrixServicesImageNotes(String rowID,
																 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		/*final String matrixservice = "Hail Repair";
		final String pricematrix = "State Farm";
		final String[] vehiclepartnames = { "Hood", "Roof" };
		final String[] vehiclepartsizes = { "Dime", "Dime" };
		final String[] vehiclepartseverities = { "Light 6 to 15", "Light 6 to 15" };	*/
		final int addedpictures = 1;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspNumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(inspectionData.getMatrixServiceData().getMatrixServiceName());
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(inspectionData.getMatrixServiceData().getHailMatrixName());
		for (HailMatrixService hailMatrixService : inspectionData.getMatrixServiceData().getHailMatrixServices()) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
			vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
			vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
			VNextNotesScreen notesscreen = vehiclepartinfoscreen.clickMatrixServiceNotesOption();
			//notesscreen.selectNotesPicturesTab();
			notesscreen.addFakeImageNote();
			Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
			notesscreen.clickNotesBackButton();
			vehiclepartinfoscreen = new VNextVehiclePartInfoPage(DriverBuilder.getInstance().getAppiumDriver());
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
			vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		}
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getMatrixServiceData().getMatrixServiceName()));
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
		homescreen.waitUntilQueueMessageInvisible();

		BaseUtils.waitABit(30000);

		WebDriver webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspNumber);
		Assert.assertTrue(inspectionspage.isMatrixServiceExists(inspectionData.getMatrixServiceData().getMatrixServiceName()));
		List<WebElement> matrixsepviserows = inspectionspage.getAllMatrixServicesRows(inspectionData.getMatrixServiceData().getMatrixServiceName());
		Assert.assertEquals(matrixsepviserows.size(), inspectionData.getMatrixServiceData().getHailMatrixServices().size());
		for (WebElement matrixsepviserow : matrixsepviserows) {
			Assert.assertTrue(inspectionspage.isImageExistsForMatrixServiceNotes(matrixsepviserow));
		}
		webdriver.quit();
	}
}
package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextQuestionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class VNextTeamInspectionNotestTestCases extends BaseTestCaseTeamEditionRegistration {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-inspection-notes-testcases-data.json";
	final private String quicknote = "Test Quick Note 1";

	@BeforeClass(description="Team Inspection Notest Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserAddNotesForTeamInspection(String rowID,
																		String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteText = "new notes";
		final String quickNoteNew = "Warranty expired";
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
		inspectionScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextNotesScreen notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		notesScreen.addQuickNote(quicknote);
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesScreen.getSelectedNotes(), quicknote);
		notesScreen.setNoteText(noteText);
		notesScreen.addQuickNote(quickNoteNew);
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteText + "\n" + quickNoteNew);
		
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		homeScreen = inspectionScreen.clickBackButton();
		VNextStatusScreen statusscreen = homeScreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homeScreen = statusscreen.clickBackButton();
		inspectionScreen = homeScreen.clickInspectionsMenuItem();
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteText + "\n" + quickNoteNew);
		
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyNotesDisplaysOnTeamInspectionListAfterAddingOnMyInspectionList(String rowID,
														String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteText = "new notes";
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
		inspectionScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
		Assert.assertTrue(inspectionScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextNotesScreen notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		notesScreen.setNoteText(noteText);
		notesScreen.addQuickNote(quicknote);

		notesScreen.addFakeImageNote();
		notesScreen.addFakeImageNote();
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isNotesIconPresentForInspection(inspectionNumber));
		inspectionScreen.switchToTeamInspectionsView();
			
		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteText + "\n" + quicknote);
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), 2);
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.switchToMyInspectionsView();
		inspectionScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyNotesDisplaysOnMyInspectionListAfterAddingOnTeamInspectionList(String rowID,
																						 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteText = "new notes";
		final int expectedNotesNumber = 2;
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();		
		inspectionScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextNotesScreen notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		notesScreen.setNoteText(noteText);
		notesScreen.addQuickNote(quicknote);

		notesScreen.addFakeImageNote();
		notesScreen.addFakeImageNote();
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
 		Assert.assertTrue(inspectionScreen.isNotesIconPresentForInspection(inspectionNumber));
		inspectionScreen.switchToMyInspectionsView();
		BaseUtils.waitABit(1000);

		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteText + "\n" + quicknote);
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), expectedNotesNumber);
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyQuickNotesAreAddedAsNewLinesOfText(String rowID,
																						 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		final String[] quickNotes = { quicknote, "Warranty expired" };
		final String noteText = "new notes";
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();		
		inspectionScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextNotesScreen notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		notesScreen.setNoteText(noteText);
		for (String quickNote: quickNotes)
			notesScreen.addQuickNote(quickNote);
		
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		String notesfinal = noteText + "\n";
		for (String quickNote: quickNotes)
			notesfinal = notesfinal + quickNote + "\n";
		Assert.assertEquals(notesScreen.getSelectedNotes(), notesfinal.trim());
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSeveralQuickNotes(String rowID,
															 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final int numberOfQuickNotesToAdd = 4;
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();		
		inspectionScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextNotesScreen notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		ArrayList<String> addednotes = notesScreen.addNumberOfQuickNotes(numberOfQuickNotesToAdd);
		AppiumUtils.clickHardwareBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		for (String quicknote: addednotes) {
			Assert.assertTrue(notesScreen.getSelectedNotes().contains(quicknote));
		}
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifySavingTextNoteOnTappingHardwareBackButton(String rowID,
													  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String noteText = "new notes";
		final int expectedNotesNumber = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();		
		inspectionScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextNotesScreen notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		notesScreen.setNoteText(noteText);
		notesScreen.addQuickNote(quicknote);
		notesScreen.addFakeImageNote();
		notesScreen.addFakeImageNote();
		AppiumUtils.clickHardwareBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isNotesIconPresentForInspection(inspectionNumber));
			
		inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesScreen.getSelectedNotes(), noteText + "\n" + quicknote);
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), expectedNotesNumber);
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanRemovePicturesFromNotes(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int numberOfImages = 3;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();		
		inspectionScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
		inspectionScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextNotesScreen notesScreen = inspectionsMenuScreen.clickNotesInspectionMenuItem();
		notesScreen.addQuickNote(quicknote);
		for (int i = 0; i < numberOfImages; i++)
			notesScreen.addFakeImageNote();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImages);
		notesScreen.deletePictureFromNotes();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImages-1);
		notesScreen.clickNotesBackButton();
		inspectionScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionScreen.switchToMyInspectionsView();
		inspectionScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateInspectionWithBreakageServiceImageNotes(String rowID,
														 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final int addedPictures = 1;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
		inspectionScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.swipeScreenLeft();
		VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
		visualScreen.clickAddServiceButton();
		//VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
		VNextSelectDamagesScreen selectdamagesscreen = new VNextSelectDamagesScreen(DriverBuilder.getInstance().getAppiumDriver());
		selectdamagesscreen.selectAllDamagesTab();
		VNextVisualServicesScreen visualServicesScreen = selectdamagesscreen.clickCustomDamageType(inspectionData.getDamageData().getDamageGroupName());
		visualScreen = visualServicesScreen.selectCustomService(inspectionData.getDamageData().getMoneyServiceData().getServiceName());
		visualScreen.clickCarImage();
		BaseUtils.waitABit(1000);

		VNextServiceDetailsScreen serviceDetailsScreen = visualScreen.clickCarImageMarker();
		VNextNotesScreen notesScreen = serviceDetailsScreen.clickServiceNotesOption();
		//notesScreen.selectNotesPicturesTab();
		notesScreen.addFakeImageNote();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), addedPictures);
		notesScreen.clickNotesBackButton();
		serviceDetailsScreen = new VNextServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextQuestionsScreen questionsScreen = serviceDetailsScreen.clickServiceQuestionSection("Test Section");
		questionsScreen.setAllRequiredQuestions("test 1");
		questionsScreen.clickDoneButton();
		serviceDetailsScreen = new VNextServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
		visualScreen.clickDamageCancelEditingButton();
		inspectionScreen = visualScreen.saveInspectionViaMenu();
		inspectionScreen.clickBackButton();

		BaseUtils.waitABit(30000);
		WebDriver
		webDriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
		BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webDriver,
				BackOfficeLoginWebPage.class);
		loginWebPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webDriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
		InspectionsWebPage inspectionsWebPage = operationsWebPage.clickInspectionsLink();

		inspectionsWebPage.makeSearchPanelVisible();
		inspectionsWebPage.searchInspectionByNumber(inspectionNumber);


		InspectionMediaWebPage inspectionMediaWebPage = inspectionsWebPage.clickInspectionMediaAction(inspectionNumber);
		Assert.assertTrue(inspectionMediaWebPage.isImageNoteExistsForInspection());


		DriverBuilder.getInstance().getAppiumDriver().quit();
	}
}

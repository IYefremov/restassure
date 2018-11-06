package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextInspectionsNotesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testservice = "Dent Repair";
	final String testservice2 = "Bumper Repair";
	
	@BeforeMethod
	public void createInspection() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.createSimpleInspection();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40322:vNext - Add several quick notes for Service in the list", 
			description = "Add several quick notes for Service in the list")
	public void testValidateMakeFieldOnVehicleScreenReflectsVisibleONOFF() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		for (int i =0; i< 3; i++) {
			notesscreen.addQuickNote(notes.get(i));
		}
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);


		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		final String selectednotes = notesscreen.getSelectedNotes();
		for (String note : notes)
			Assert.assertTrue(selectednotes.contains(note));
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37673:vNext - Verify quick notes are added as new lines of text", 
			description = "Verify quick notes are added as new lines of text")
	public void testVerifyQuickNotesAreAddedAsNewLinesOfText() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		for (int i =0; i< 3; i++) {
			notesscreen.addQuickNote(notes.get(i));
		}
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);

		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		final String selectednotes = notesscreen.getSelectedNotes();
		String notestocompare = "";
		for (int i =0; i< 3; i++) {
			notestocompare = notestocompare + notes.get(i) + "\n";
		}
			
		Assert.assertEquals(selectednotes, notestocompare.trim());
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40335:vNext - Verify text content is validated on tapping 'Back' button", 
			description = "Verify text content is validated on tapping 'Back' button")
	public void testValidateTextContentIsValidatedOnTappingBackButton() {
		
		final String notetext = "abcd%:?*()текст";
		final String notetextvalid = "abcd%:?*()";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		inspservicesscreen.selectService(testservice2);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice2);
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
		VNextInformationDialog infodialog = new VNextInformationDialog(appiumdriver);
		String msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		infodialog = new VNextInformationDialog(appiumdriver);
		msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice2);
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40336:vNext - Verify text content is validated on tapping hardware 'Back' button", 
			description = "Verify text content is validated on tapping hardware 'Back' button")
	public void testVerifyTextContentIsValidatedOnTappingHardwareBackButton() {
		
		final String notetext = "abcd%:?*()текст";
		final String notetextvalid = "abcd%:?*()";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		inspservicesscreen.selectService(testservice2);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice2);
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
		VNextInformationDialog infodialog = new VNextInformationDialog(appiumdriver);
		String msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		infodialog = new VNextInformationDialog(appiumdriver);
		msg = infodialog.getInformationDialogMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		AppiumUtils.clickHardwareBackButton();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice2);
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 37670:vNext - Validate 'Notes' option is available at different wizard steps", 
			description = "Validate 'Notes' option is available at different wizard steps")
	public void testVerifyNotesOptionIsAvailableAtDifferentWizardSteps() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		
		for (int i=0; i<9; i++) {
			inspservicesscreen.clickInspectionNotesOption();
			AppiumUtils.clickHardwareBackButton();
			
			inspservicesscreen.swipeScreenLeft();
		}
		
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();	
	}
	
	@Test(testName= "Test Case 37671:vNext - Add text notes for Service in the list", 
			description = "Add text notes for Service in the list")
	public void testAddTextNotesForServiceInTheList() {
		
		final String notetextvalid = "abcd%:?*()";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.addNotesToSelectedService(testservice, notetextvalid);
		/*VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickHardwareBackButton();
		//notesscreen.clickHardwareBackButton();
		
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();*/
		Assert.assertEquals(selectedServicesScreen.getSelectedServiceNotesValue(testservice), notetextvalid);

		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 37672:vNext - Add quick note for Service in the list", 
			description = "Add quick note for Service in the list")
	public void testAddQuickNoteForServiceInTheList() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		notesscreen.addQuickNote(notes.get(0));
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);

		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		final String selectednotes = notesscreen.getSelectedNotes();
		Assert.assertTrue(selectednotes.contains(notes.get(0)));
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();	
	}
	
	@Test(testName= "Test Case 40322:vNext - Add several quick notes for Service in the list", 
			description = "Add several quick notes for Service in the list")
	public void testAddSeveralQuickNotesForServiceInTheList() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<>();
		for (WebElement note : quicknoteslist) {
    		notes.add(note.getText());
		}
		for (int i = 0; i < 3; i++) 
			notesscreen.addQuickNote(notes.get(i));
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		for (int i = 0; i < 3; i++) 
			Assert.assertTrue(selectedServicesScreen.getSelectedServiceNotesValue(testservice).trim().contains(notes.get(i).trim()));
		
		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		final String selectednotes = notesscreen.getSelectedNotes();
		for (int i = 0; i < 3; i++) 
			Assert.assertTrue(selectednotes.contains(notes.get(i)));		
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();	
	}
	
	@Test(testName= "Test Case 40287:vNext - Save text note on tapping hardware 'Back' button (Estimation level)", 
			description = "Save text note on tapping 'Back' button (Estimation level)")
	public void testSaveTextNoteOnTappingBackButtonEstimationLevel() {
		
		final String notetextvalid = "abcd%:?*()";
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40286:vNext - Save text note on tapping 'Back' button (Estimation level) - check after inspection save", 
			description = "Save text note on tapping 'Back' button (Estimation level) - check after inspection save")
	public void testSaveTextNoteOnTappingBackButtonEstimationLevel_CheckAfterInspectionSave() {
		
		final String notetextvalid = "abcd%:?*()";
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspservicesscreen.clickSaveInspectionMenuButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
				
		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40287:vNext - Save text note on tapping hardware 'Back' button (Estimation level)", 
			description = "Save text note on tapping hardware 'Back' button (Estimation level)")
	public void testSaveTextNoteOnTappingHardwareBackButtonEstimationLevel() {
		
		final String notetextvalid = "abcd%:?*()";
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		AppiumUtils.clickHardwareBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40294:vNext - Save photo as a note on tapping hardware 'Back' button (Estimation level)", 
			description = "Save photo as a note on tapping hardware 'Back' button (Estimation level)")
	public void testSavePhotoAsANoteOnTappingHardwareBackButtonEstimationLevel() {
		
		final String notetextvalid = "abcd%:?*()";
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.addCameraPictureToNote();
		BaseUtils.waitABit(2000);
		notesscreen.selectNotesTextTab();
		AppiumUtils.clickHardwareBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.selectNotesPicturesTab();
		Assert.assertTrue(notesscreen.isPictureaddedToNote());
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40332:vNext - Save picture note on tapping 'Back' button (Estimation level) - check after inspection save", 
			description = "Save picture note on tapping 'Back' button (Estimation level) - check after inspection save")
	public void testSavePictureNoteOnTappingBackButtonEstimationLevel() {
		
		final String notetextvalid = "abcd%:?*()";
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();		
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.addCameraPictureToNote();
		BaseUtils.waitABit(2000);
		notesscreen.selectNotesTextTab();
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.selectNotesPicturesTab();
		Assert.assertTrue(notesscreen.isPictureaddedToNote());
		
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}

	@Test(testName= "Test Case 43333:vNext - Clear text notes for Service in the list", 
			description = "Clear text notes for Service in the list")
	public void testClearTextNotesForServiceInTheList() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(testservice);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		for (int i = 0; i < 3; i++) 
			notesscreen.addQuickNote(notes.get(i));
		AppiumUtils.clickHardwareBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		notesscreen.clickClearNotesButton();
		Assert.assertEquals(notesscreen.getSelectedNotes(), "");
		AppiumUtils.clickHardwareBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		notesscreen = selectedServicesScreen.clickServiceNotesOption(testservice);
		Assert.assertEquals(notesscreen.getSelectedNotes(), "");
		notesscreen.clickNotesBackButton();
		selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		inspectionsscreen = selectedServicesScreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40285:vNext - Save picture note from gallery on tapping 'Back' button (Estimation level)", 
			description = "Save picture note from gallery on tapping 'Back' button (Estimation level)")
	public void testSavePictureNoteFromGalleryOnTappingBackButtonEstimationLevel() {
		
		final int addedpictures = 1;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextNotesScreen notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		notesscreen.selectNotesPicturesTab();
		notesscreen.addFakeImageNote();
		notesscreen.clickAllowIfAppears();
		notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		//notesscreen.selectNotesPicturesTab();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
		AppiumUtils.clickHardwareBackButton();
		notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 55441:vNext mobile: Create Inspection with breakage service image notes, "
			+ "Test Case 55444:vNext: verify displaying image notes for the Inspection Visual Breakage service", 
			description = "Create Inspection with breakage service image notes, "
					+ "verify displaying image notes for the Inspection Visual Breakage service")
	public void testCreateInspectionWithBreakageServiceImageNotes() {
		
		final String selectdamage = "Price Adjustment";
		final String servicepercentage = "Corrosion Protection";
		final int addedpictures = 1;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspnumber = inspectionsscreen.getFirstInspectionNumber();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		vehicleinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		//VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
		VNextSelectDamagesScreen selectdamagesscreen = new VNextSelectDamagesScreen(appiumdriver);
		selectdamagesscreen.selectAllDamagesTab();
		VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
		visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
		visualscreen.clickCarImage();
		BaseUtils.waitABit(1000);
		
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		//notesscreen.selectNotesPicturesTab();
		notesscreen.addFakeImageNote();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickDamageCancelEditingButton();
		inspectionsscreen = visualscreen.saveInspectionViaMenu();		
		homescreen = inspectionsscreen.clickBackButton();
		
		BaseUtils.waitABit(30000);
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnumber);
		Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection(servicepercentage));
		Assert.assertTrue(inspectionspage.isServiceNotesIconDisplayed(servicepercentage));
		Assert.assertTrue(inspectionspage.isImageExistsForServiceNote(servicepercentage));
		webdriver.quit();
	}
	
	@Test(testName= "Test Case 55648:vNext mobile: Create Inspection with money service image notes,"
			+ "Test Case 55649:vNext mobile: Create Inspection with percentage service image notes,"
			+ "Test Case 55662:vNext: verify displaying image notes for the Inspection Money service,"
			+ "Test Case 55663:vNext: verify displaying image notes for the Percentage service", 
			description = "Create Inspection with money service image notes")
	public void testCreateInspectionWithMoneyServiceImageNotes() {
		
		final String[] servicestoadd = { "Dent Repair", "Aluminum Upcharge" };
		final int addedpictures = 1;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspnumber = inspectionsscreen.getFirstInspectionNumber();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		for (String serviceadd : servicestoadd)
			inspservicesscreen.selectService(serviceadd);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		for (String serviceadd : servicestoadd) {
			//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(serviceadd);
			VNextNotesScreen notesscreen = selectedServicesScreen.clickServiceNotesOption(serviceadd);
			//notesscreen.selectNotesPicturesTab();
			notesscreen.addFakeImageNote();
			Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
			notesscreen.clickNotesBackButton();
			selectedServicesScreen = new VNextSelectedServicesScreen(appiumdriver);
		}
		
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
		
		BaseUtils.waitABit(30000);
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnumber);
		for (String serviceadd : servicestoadd) {
			Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection(serviceadd));
			Assert.assertTrue(inspectionspage.isServiceNotesIconDisplayed(serviceadd));
			Assert.assertTrue(inspectionspage.isImageExistsForServiceNote(serviceadd));
		}
		webdriver.quit();
	}
	
	@Test(testName= "Test Case 55650:vNext mobile: Create Inspection with matrix services image notes", 
			description = "Create Inspection with matrix services image notes")
	public void testCreateInspectionWithMatrixServicesImageNotes() {
		
		final String matrixservice = "Test Matrix Service";
		final String pricematrix = "State Farm";
		final String[] vehiclepartnames = { "Hood", "Roof" };
		final String[] vehiclepartsizes = { "Dime", "Dime" };	
		final String[] vehiclepartseverities = { "Light 6 to 15", "Light 6 to 15" };	
		final int addedpictures = 1;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspnumber = inspectionsscreen.getFirstInspectionNumber();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		for (int i=0; i < vehiclepartnames.length; i++) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartnames[i]);
			vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsizes[i]);
			vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverities[i]);
			VNextNotesScreen notesscreen = vehiclepartinfoscreen.clickMatrixServiceNotesOption();
			//notesscreen.selectNotesPicturesTab();
			notesscreen.addFakeImageNote();
			Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
			notesscreen.clickNotesBackButton();
			vehiclepartinfoscreen = new VNextVehiclePartInfoPage(appiumdriver);
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
			vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		}
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();		
		homescreen.waitUntilQueueMessageInvisible();
		
		BaseUtils.waitABit(30000);
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
		inspectionspage.selectInspectionInTheList(inspnumber);
		Assert.assertTrue(inspectionspage.isMatrixServiceExists(matrixservice));
		List<WebElement> matrixsepviserows = inspectionspage.getAllMatrixServicesRows(matrixservice);
		Assert.assertEquals(matrixsepviserows.size(), vehiclepartnames.length);
		for (WebElement matrixsepviserow : matrixsepviserows) {
			Assert.assertTrue(inspectionspage.isImageExistsForMatrixServiceNotes(matrixsepviserow));
		}
		webdriver.quit();
	}
}

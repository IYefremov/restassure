package com.cyberiansoft.test.vnext.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;

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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		for (int i =0; i< 3; i++) {
			notesscreen.addQuickNote(notes.get(i));
		}
		notesscreen.clickHardwareBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		final String selectednotes = notesscreen.getSelectedNotes();
		for (String note : notes)
			Assert.assertTrue(selectednotes.contains(note));
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 37673:vNext - Verify quick notes are added as new lines of text", 
			description = "Verify quick notes are added as new lines of text")
	public void testVerifyQuickNotesAreAddedAsNewLinesOfText() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		for (int i =0; i< 3; i++) {
			notesscreen.addQuickNote(notes.get(i));
		}
		notesscreen.clickHardwareBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		final String selectednotes = notesscreen.getSelectedNotes();
		String notestocompare = "";
		for (int i =0; i< 3; i++) {
			notestocompare = notestocompare + notes.get(i) + "\n";
		}
			
		Assert.assertEquals(selectednotes, notestocompare.trim());
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.selectService(testservice2);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice2);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
		VNextInformationDialog infodialog = new VNextInformationDialog(appiumdriver);
		String msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		notesscreen.clickHardwareBackButton();
		infodialog = new VNextInformationDialog(appiumdriver);
		msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice2);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.selectService(testservice2);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice2);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		notesscreen.setNoteText(notetext);
		notesscreen.clickNotesBackButton();
		VNextInformationDialog infodialog = new VNextInformationDialog(appiumdriver);
		String msg = infodialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		notesscreen.clickHardwareBackButton();
		infodialog = new VNextInformationDialog(appiumdriver);
		msg = infodialog.getInformationDialogMessage();
		Assert.assertEquals(msg, VNextAlertMessages.SOME_NOTES_CHARACTERS_NOT_ALLOWED);
		notesscreen.clickHardwareBackButton();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice2);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 37670:vNext - Validate 'Notes' option is available at different wizard steps", 
			description = "Validate 'Notes' option is available at different wizard steps")
	public void testVerifyNotesOptionIsAvailableAtDifferentWizardSteps() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		
		for (int i=0; i<9; i++) {
			VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
			notesscreen.clickHardwareBackButton();
			
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.addNotesToSelectedService(testservice, notetextvalid);
		/*VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickHardwareBackButton();
		//notesscreen.clickHardwareBackButton();
		
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();*/
		Assert.assertEquals(inspservicesscreen.getSelectedServiceNotesValue(testservice), notetextvalid);

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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		notesscreen.addQuickNote(notes.get(0));
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		final String selectednotes = notesscreen.getSelectedNotes();
		Assert.assertTrue(selectednotes.contains(notes.get(0)));
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();	
	}
	
	@Test(testName= "Test Case 40322:vNext - Add several quick notes for Service in the list", 
			description = "Add several quick notes for Service in the list")
	public void testAddSeveralQuickNotesForServiceInTheList() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = inspservicesscreen.clickServiceNotesOption(testservice);
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist) {
    		notes.add(note.getText());
		}
		for (int i = 0; i < 3; i++) 
			notesscreen.addQuickNote(notes.get(i));
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (int i = 0; i < 3; i++) 
			Assert.assertTrue(inspservicesscreen.getSelectedServiceNotesValue(testservice).trim().contains(notes.get(i).trim()));
		
		notesscreen = inspservicesscreen.clickServiceNotesOption(testservice);
		final String selectednotes = notesscreen.getSelectedNotes();
		for (int i = 0; i < 3; i++) 
			Assert.assertTrue(selectednotes.contains(notes.get(i)));		
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.clickSaveInspectionMenuButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
				
		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickHardwareBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.addCameraPictureToNote();
		waitABit(2000);
		notesscreen.selectNotesTextTab();
		notesscreen.clickHardwareBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.selectNotesPicturesTab();
		Assert.assertTrue(notesscreen.isPictureaddedToNote());
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextNotesScreen notesscreen = inspservicesscreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.addCameraPictureToNote();
		waitABit(2000);
		notesscreen.selectNotesTextTab();
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		
		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		notesscreen = inspservicesscreen.clickInspectionNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.selectNotesPicturesTab();
		Assert.assertTrue(notesscreen.isPictureaddedToNote());
		
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService(testservice);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = inspservicesscreen.clickServiceNotesOption(testservice);
		List<WebElement> quicknoteslist = notesscreen.getListOfQuickNotes();
		List<String> notes = new ArrayList<String>();
		for (WebElement note : quicknoteslist)
    		notes.add(note.getText());
		for (int i = 0; i < 3; i++) 
			notesscreen.addQuickNote(notes.get(i));
		notesscreen.clickHardwareBackButton();
		
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		notesscreen = inspservicesscreen.clickServiceNotesOption(testservice);
		notesscreen.clickClearNotesButton();
		Assert.assertEquals(notesscreen.getSelectedNotes(), "");
		notesscreen.clickHardwareBackButton();
		
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		notesscreen = inspservicesscreen.clickServiceNotesOption(testservice);
		Assert.assertEquals(notesscreen.getSelectedNotes(), "");
		notesscreen.clickNotesBackButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspectionsscreen = inspservicesscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 40285:vNext - Save picture note from gallery on tapping 'Back' button (Estimation level)", 
			description = "Save picture note from gallery on tapping 'Back' button (Estimation level)")
	public void testSavePictureNoteFromGalleryOnTappingBackButtonEstimationLevel() throws IOException {
		
		final int addedpictures = 1;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextNotesScreen notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		notesscreen.selectNotesPicturesTab();
		notesscreen.addImageToNotesFromGallery();
		notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
		//notesscreen.selectNotesPicturesTab();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
		notesscreen.clickNotesBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 55441:vNext mobile: Create Inspection with breakage service image notes, "
			+ "Test Case 55444:vNext: verify displaying image notes for the Inspection Visual Breakage service", 
			description = "Create Inspection with breakage service image notes, "
					+ "verify displaying image notes for the Inspection Visual Breakage service")
	public void testCreateInspectionWithBreakageServiceImageNotes() throws IOException {
		
		final String selectdamage = "Price Adjustment";
		final String servicepercentage = "Corrosion Protection";
		final int addedpictures = 1;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspnumber = inspectionsscreen.getFirstInspectionNumber();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickAddServiceButton();
		VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
		selectdamagesscreen.selectAllDamagesTab();
		VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
		visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
		visualscreen.clickCarImage();
		visualscreen.waitABit(1000);
		
		VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		notesscreen.selectNotesPicturesTab();
		notesscreen.addImageToNotesFromGallery();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		servicedetailsscreen.clickServiceDetailsDoneButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickDamageCancelEditingButton();
		inspectionsscreen = visualscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
		
		homescreen.waitABit(30000);
		initiateWebDriver();
		webdriverGotoWebPage(deviceofficeurl);
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
	public void testCreateInspectionWithMoneyServiceImageNotes() throws IOException {
		
		final String[] servicestoadd = { "Dent Repair", "Aluminum Upcharge" };
		final int addedpictures = 1;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspnumber = inspectionsscreen.getFirstInspectionNumber();
		VNextInspectionsMenuScreen inspmenu = inspectionsscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextVehicleInfoScreen vehicleinfoscreen =  inspmenu.clickEditInspectionMenuItem();
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		for (String serviceadd : servicestoadd)
			selectservicesscreen.selectService(serviceadd);
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		for (String serviceadd : servicestoadd) {
			//VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(serviceadd);
			VNextNotesScreen notesscreen = inspservicesscreen.clickServiceNotesOption(serviceadd);
			notesscreen.selectNotesPicturesTab();
			notesscreen.addImageToNotesFromGallery();
			Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
			notesscreen.clickNotesBackButton();
			inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		}
		
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();
		
		homescreen.waitABit(30000);
		initiateWebDriver();
		webdriverGotoWebPage(deviceofficeurl);
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
	public void testCreateInspectionWithMatrixServicesImageNotes() throws IOException {
		
		final String matrixservice = "Hail Dent Repair";
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
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		VNextPriceMatrixesScreen pricematrixesscreen = selectservicesscreen.openMatrixServiceDetails(matrixservice);
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(pricematrix);
		for (int i=0; i < vehiclepartnames.length; i++) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartnames[i]);
			vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsizes[i]);
			vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverities[i]);
			VNextNotesScreen notesscreen = vehiclepartinfoscreen.clickMatrixServiceNotesOption();
			notesscreen.selectNotesPicturesTab();
			notesscreen.addImageToNotesFromGallery();
			Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), addedpictures);
			notesscreen.clickNotesBackButton();
			vehiclepartinfoscreen = new VNextVehiclePartInfoPage(appiumdriver);
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		}
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		selectservicesscreen = vehiclepartsscreen.clickVehiclePartsBackButton();

		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		Assert.assertTrue(inspservicesscreen.isServiceSelected(matrixservice));
		inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
		homescreen = inspectionsscreen.clickBackButton();		
		homescreen.waitUntilQueueMessageInvisible();
		
		homescreen.waitABit(30000);
		
		initiateWebDriver();
		webdriverGotoWebPage(deviceofficeurl);
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

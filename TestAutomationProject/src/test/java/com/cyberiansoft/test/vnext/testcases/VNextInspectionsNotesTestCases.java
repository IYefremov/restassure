package com.cyberiansoft.test.vnext.testcases;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextNotesScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;

public class VNextInspectionsNotesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testservice = "Detail";
	final String testservice2 = "Bent Wheel";
	
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
		inspservicesscreen = servicedetailsscreen.clickServiceDetailsDoneButton();
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice2);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		inspservicesscreen = servicedetailsscreen.clickServiceDetailsDoneButton();
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
		inspservicesscreen = servicedetailsscreen.clickServiceDetailsDoneButton();
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice2);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		inspservicesscreen = servicedetailsscreen.clickServiceDetailsDoneButton();
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
		VNextServiceDetailsScreen servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		VNextNotesScreen notesscreen = servicedetailsscreen.clickServiceNotesOption();
		notesscreen.setNoteText(notetextvalid);
		notesscreen.clickHardwareBackButton();
		
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		inspservicesscreen = servicedetailsscreen.clickServiceDetailsDoneButton();
		servicedetailsscreen = inspservicesscreen.openServiceDetailsScreen(testservice);
		notesscreen = servicedetailsscreen.clickServiceNotesOption();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetextvalid);
		notesscreen.clickNotesBackButton();
		servicedetailsscreen = new VNextServiceDetailsScreen(appiumdriver);
		inspservicesscreen = servicedetailsscreen.clickServiceDetailsDoneButton();
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
	
	@Test(testName= "Test Case 40295:vNext - Save text note on tapping 'Back' button (Estimation level)", 
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

}

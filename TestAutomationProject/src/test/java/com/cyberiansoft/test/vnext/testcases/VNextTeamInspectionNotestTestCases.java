package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class VNextTeamInspectionNotestTestCases extends BaseTestCaseTeamEditionRegistration {

	final String quicknote = "Test Quick Note 1";

	@BeforeClass(description="Team Inspection Notest Test Cases")
	public void beforeClass() {
	}
	
	@Test(testName= "Test Case 67743:Verify user can add Notes for Team Inspection, "
			+ "Test Case 67744:Verify User can edit Notes from Team Inspection list, "
			+ "Test Case 67746:Verify Notes changes is saved after DB update", 
			description = "Verify user can add Notes for Team Inspection, "
					+ "Verify User can edit Notes from Team Inspection list, "
					+ "Verify Notes changes is saved after DB update")
	public void testVerifyUserAddNotesForTeamInspection() {

		final String vinnumber = "123";
		final String notetext = "new notes";
		final String quicknotenew = "Z note";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);

		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextNotesScreen notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		notesscreen.addQuickNote(quicknote);
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), quicknote);
		notesscreen.setNoteText(notetext);
		notesscreen.addQuickNote(quicknotenew);
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknotenew);
		
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
		inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknotenew);
		
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67747:Verify Notes displays on Team Inspection list after adding on My Inspection list, "
			+ "Test Case 67748:Verify Notes icon displays if user add Notes for Inspection My/Team", 
			description = "Verify Notes displays on Team Inspection list after adding on My Inspection list, "
					+ "Verify Notes icon displays if user add Notes for Inspection My/Team")
	public void testVerifyNotesDisplaysOnTeamInspectionListAfterAddingOnMyInspectionList() {
		
		final String vinnumber = "123";

		final String notetext = "new notes";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextNotesScreen notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.addQuickNote(quicknote);
		//notesscreen.addImageToNotesFromGallery();
		//notesscreen.addImageToNotesFromGallery();
		notesscreen.addFakeImageNote();
		notesscreen.addFakeImageNote();
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertTrue(inspectionscreen.isNotesIconPresentForInspection(inspnumber));
		inspectionscreen.switchToTeamInspectionsView();
			
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknote);
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), 2);
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67747:Verify Notes displays on Team Inspection list after adding on My Inspection list, "
			+ "Test Case 67748:Verify Notes icon displays if user add Notes for Inspection My/Team, "
			+ "Test Case 67750:Verify saving text note on tapping 'Back' button", 
			description = "Verify Notes displays on Team Inspection list after adding on My Inspection list, "
					+ "Verify Notes icon displays if user add Notes for Inspection My/Team, "
					+ "Verify saving text note on tapping 'Back' button")
	public void testVerifyNotesDisplaysOnMyInspectionListAfterAddingOnTeamInspectionList() {
		
		final String vinnumber = "123";

		final String notetext = "new notes";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextNotesScreen notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.addQuickNote(quicknote);
		//notesscreen.addImageToNotesFromGallery();
		//notesscreen.addImageToNotesFromGallery();
		notesscreen.addFakeImageNote();
		notesscreen.addFakeImageNote();
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
 		Assert.assertTrue(inspectionscreen.isNotesIconPresentForInspection(inspnumber));
		inspectionscreen.switchToMyInspectionsView();
			
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknote);
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), 2);
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67751:Verify quick notes are added as new lines of text",
			description = "Verify quick notes are added as new lines of text")
	public void testVerifyQuickNotesAreAddedAsNewLinesOfText() {
		
		final String vinnumber = "123";
		
		final String[] quicknotes = { quicknote, "Z note" };
		final String notetext = "new notes";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextNotesScreen notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		notesscreen.setNoteText(notetext);
		for (String quicknote: quicknotes)
			notesscreen.addQuickNote(quicknote);
		
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
			
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		String notesfinal = notetext + "\n";
		for (String quicknote: quicknotes)
			notesfinal = notesfinal + quicknote + "\n";
		Assert.assertEquals(notesscreen.getSelectedNotes(), notesfinal.trim());
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67754:Verify user can add several Quick notes",
			description = "Verify user can add several Quick notes")
	public void testVerifyUserCanAddSeveralQuickNotes() {
		
		final String vinnumber = "123";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);

		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextNotesScreen notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		ArrayList<String> addednotes = notesscreen.addNumberOfQuickNotes(10);
		
		
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
			
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		for (String quicknote: addednotes)
			Assert.assertTrue(notesscreen.getSelectedNotes().contains(quicknote));
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67749:Verify saving text note on tapping hardware 'Back' button",
			description = "Verify saving text note on tapping hardware 'Back' button")
	public void testVerifySavingTextNoteOnTappingHardwareBackButton() {
		
		final String vinnumber = "123";
		

		final String notetext = "new notes";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);

		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextNotesScreen notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.addQuickNote(quicknote);
		notesscreen.addFakeImageNote();
		notesscreen.addFakeImageNote();
		AppiumUtils.clickHardwareBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertTrue(inspectionscreen.isNotesIconPresentForInspection(inspnumber));
			
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknote);
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), 2);
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67752:Verify user can remove pictures from Notes",
			description = "Verify user can remove pictures from Notes")
	public void testVerifyUserCanRemovePicturesFromNotes() {
		
		final String vinnumber = "123";
		
		final int numberOfImages = 3;

		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);

		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextNotesScreen notesscreen = inspmenuscreen.clickNotesInspectionMenuItem();
		notesscreen.addQuickNote(quicknote);
		for (int i = 0; i < numberOfImages; i++)
			notesscreen.addFakeImageNote();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImages);
		notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImages-1);
		notesscreen.clickNotesBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.switchToMyInspectionsView();
		homescreen = inspectionscreen.clickBackButton();
	}
	
}

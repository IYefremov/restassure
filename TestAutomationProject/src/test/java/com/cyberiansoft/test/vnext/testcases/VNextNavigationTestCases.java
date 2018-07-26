package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;

public class VNextNavigationTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
	final String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 63497:Navigating by action screen for Android", 
			description = "Navigating by action screen for Android")
	public void testNavigatingByActionScreenForAndroid() { 
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.clickOnInspectionByInspNumber(inspectionsscreen.getFirstInspectionNumber());
		AppiumUtils.clickHardwareBackButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
	}
	
	@Test(testName= "Test Case 63502:Verify user can navigate by action screen in Inspections for Android", 
			description = "Verify user can navigate by action screen in Inspections for Android")
	public void testVerifyUserCanNavigateByActionScreenInInspectionsForAndroid() { 
		
		final String insuranceCompany = "Test Insurance Company";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
		
		vehicleinfoscreen.changeScreen("Visual");
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		vehicleinfoscreen.changeScreen("Services");
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.selectService("Facility Fee");
		inspservicesscreen.selectService("Other");
		inspservicesscreen.clickScreenBackButton();
		visualscreen = new VNextVisualScreen(appiumdriver);
		visualscreen.clickScreenForwardButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected("Facility Fee"));
		Assert.assertTrue(selectedServicesScreen.isServiceSelected("Other"));
		
		inspservicesscreen.changeScreen("Vehicle Info");
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), testVIN);
		vehicleinfoscreen.clickMenuButton();
		AppiumUtils.clickHardwareBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.clickScreenTitleCaption();
		AppiumUtils.clickHardwareBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), testVIN);
		vehicleinfoscreen.changeScreen("Claim");
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.changeScreen("Services");
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertTrue(selectedServicesScreen.isServiceSelected("Facility Fee"));
		Assert.assertTrue(selectedServicesScreen.isServiceSelected("Other"));
		
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertTrue(inspectionsscreen.isInspectionExists(inspNumber));
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 63509:Verify action screen works correctly", 
			description = "Verify action screen works correctly")
	public void testVerifyActionScreenWorksCorrectly() { 
		
		final String carColor = "Red";
		final String carYear = "2005";
		final String[] damageTypes = { "Miscellaneous", "Dent Repair" };
		final String[] selectedServices = { "Prior Damage", "Dent Repair" };
		final String notetext = "Test Notes";
		final String usermail = "anastasiia.naumenko@cyberiansoft.com";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen = inspectionsscreen.createSimpleInspection();
		
		final String inspnumber = inspectionsscreen.getFirstInspectionNumber();
		VNextInspectionsMenuScreen inspmenuscreen = inspectionsscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextViewScreen viewscreen = inspmenuscreen.clickViewInspectionMenuItem();
		viewscreen.clickScreenBackButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		
		VNextVehicleInfoScreen vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		final String VIN = vehicleinfoscreen.getVINFieldValue();
		vehicleinfoscreen.selectModelColor(carColor);
		vehicleinfoscreen.setYear(carYear);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), VIN);
		vehicleinfoscreen.clickCancelMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.CANCEL_CREATING_INSPECTION_ALERT);
		vehicleinfoscreen.clickScreenForwardButton();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		claiminfoscreen.clickScreenForwardButton();
		VNextVisualScreen visualscreen = new VNextVisualScreen(appiumdriver);
		for (int i = 0; i < damageTypes.length; i++) {
			visualscreen.selectDefaultDamage(damageTypes[i]);
			if (i == 0)
				visualscreen.clickCarImage();
			else
				visualscreen.clickCarImageSecondTime();
			BaseUtils.waitABit(1000);
		}
		Assert.assertEquals(visualscreen.getImageMarkers().size(), damageTypes.length);
		
		vehicleinfoscreen.clickScreenForwardButton();
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		/*for (String selectedService : selectedServices)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(selectedService));
		*/
		VNextNotesScreen notesscreen = selectedServicesScreen.clickInspectionNotesOption();
		notesscreen.setNoteText(notetext);
		notesscreen.clickScreenBackButton();

		selectedServicesScreen = new VNextSelectedServicesScreen (appiumdriver);
		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		
		notesscreen = inspectionsscreen.openInspectionNotes(inspnumber);
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext);
		notesscreen.clickScreenBackButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		
		VNextEmailScreen emailscreen = inspectionsscreen.clickOnInspectionToEmail(inspnumber);
		if (!emailscreen.getToEmailFieldValue().equals(usermail))
			emailscreen.sentToEmailAddress(usermail);
		
		emailscreen.clickSendEmailsButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGE_HAS_BEEEN_ADDDED_TO_THE_QUEUE);
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertTrue(inspectionsscreen.isEmailSentIconPresentForInspection(inspnumber));
		homescreen = inspectionsscreen.clickBackButton();
	}

}

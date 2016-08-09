package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;

public class VNextInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testcustomer = "Oksana Osmak";
	final String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 39549:vNext - Inspections - Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)", 
			description = "Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testNavigateToScreenWithRequiredFieldsOnSaveInspectionCalledFromHumburgerMenu_FirstStep() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.clickCancelInspectionMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleinfoscreen.swipeScreenLeft();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 43330:vNext - Navigate by swipe through wizard steps for Inspeciton", 
			description = "Navigate by swipe through wizard steps for Inspeciton")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testNavigateBySwipeThroughWizardStepsForInspeciton() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.clickSaveButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);	
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnumber);		
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 43331:vNext - Navigate through wizard steps for Inspection by hardware 'Back' button", 
			description = "Navigate through wizard steps for Inspection by hardware 'Back' button")
	@Parameters({ "backoffice.url", "user.name", "user.psw", "device.license" })
	public void testNavigateThroughWizardStepsForInspectionByHardwareBackButton() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		vehicleinfoscreen.clickHardwareBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectService("Detail");
		selectservicesscreen.clickSaveSelectedServicesButton();
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		vehicleinfoscreen = inspservicesscreen.goBackToInspectionVehicleInfoScreen();
		vehicleinfoscreen.clickHardwareBackButton();
		vehicleinfoscreen.waitABit(3000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.ARE_YOU_SURE_STOP_CREATING_INSPECTION);
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);	
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnumber);		
		homescreen = inspectionsscreen.clickBackButton();
	}

}

package com.cyberiansoft.test.vnext.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;

public class VNextInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testcustomer = "Retail Automation";
	final String testVIN = "1FMCU0DG4BK830800";
	
	@Test(testName= "Test Case 43325:vNext - Verify VIN is decoded correctly for Inspection", 
			description = "Verify VIN is decoded correctly for Inspection")
	public void testVerifyVINIsDecodedCorrectlyForInspection() { 
	
		final String vehiclemake = "Ford";
		final String vehiclemodel= "Escape";
		final String vehicleyear = "2011";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		vehicleinfoscreen.waitABit(2000);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), testVIN);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), vehiclemake);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), vehiclemodel);
		Assert.assertEquals(vehicleinfoscreen.getYear(), vehicleyear);
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 43511:vNext - Save inspection using option from hamburger menu (general case)", 
			description = "Save inspection using option from hamburger menu (general case)")
	public void testSaveInspectionUsingOptionFromHamburgerMenu_GeneralCase() { 
		
		final String[] secondservices = { "Bumper Repair", "Other" }; 
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		VNextInspectionServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		VNextSelectServicesScreen selectservicesscreen = inspservicesscreen.clickAddServicesButton();
		selectservicesscreen.selectServices(secondservices);
		selectservicesscreen.clickSaveSelectedServicesButton();	
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inspservicesscreen.clickSaveInspectionMenuButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 39549:vNext - Inspections - Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)", 
			description = "Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)")
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
	public void testNavigateThroughWizardStepsForInspectionByHardwareBackButton() { 
	
		final String servicename = "Dent Repair";
		
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
		selectservicesscreen.selectService(servicename);
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
	
	@Test(testName= "Test Case 43512:vNext - Edit inspection Vehicle Info", 
			description = "Edit inspection Vehicle Info")
	public void testEditInspectionVehicleInfo() { 
	
		final String newVIN = "1GCJTCFE7B8448756";
		final String _make = "Chevrolet";
		final String _model = "Colorado";
		final String _year = "2011";
		final String milage = "34561";
		final String freetext = "Lorem ipsum dolor sit amet";
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(testVIN);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionsscreen = vehicleinfoscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		vehicleinfoscreen.setVIN(newVIN);
		vehicleinfoscreen.setMilage(milage);
		vehicleinfoscreen.setStockNo(freetext);
		vehicleinfoscreen.setRoNo(freetext);
		vehicleinfoscreen.setLicPlate(milage);
		inspectionsscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), _year);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), newVIN);
		Assert.assertEquals(vehicleinfoscreen.getMilage(), milage);
		Assert.assertEquals(vehicleinfoscreen.getStockNo(), freetext.toUpperCase());
		Assert.assertEquals(vehicleinfoscreen.getRoNo(), freetext.toUpperCase());
		Assert.assertEquals(vehicleinfoscreen.getLicPlate(), milage);
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		homescreen = inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 43525:vNext - Create customer along with Inspection", 
			description = "Create customer along with Inspection")
	public void testCreateCustomerAlongWithInspection() { 
		
		final String firstname = "Eric";
		final String lastname = "Burn";
		final String customeraddress = "Stryis'ka, 223";
		final String customercity = "L'viv";
		final String customerzip = "79051";
		final String customeremail = "osmak.oksana+408222@gmail.com";
		final String customer = firstname + " " + lastname;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.setCustomerFirstName(firstname);
		newcustomerscreen.setCustomerLastName(lastname);
		newcustomerscreen.setCustomerEmail(customeremail);
		newcustomerscreen.setCustomerAddress(customeraddress);
		newcustomerscreen.setCustomerCity(customercity);
		newcustomerscreen.setCustomerZIP(customerzip);
		newcustomerscreen.clickSaveCustomerButton();
		newcustomerscreen.waitABit(7000);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertEquals(vehicleinfoscreen.getCustomercontextValue(), customer);
		final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
		inspectionsscreen = vehicleinfoscreen.saveInspectionfromFirstScreen();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnum);
		Assert.assertEquals(inspectionsscreen.getInspectionCustomerValue(inspnum), customer);
		homescreen = inspectionsscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.selectCustomer(customer);
		newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), firstname);
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), lastname);
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), customeraddress);
		Assert.assertEquals(newcustomerscreen.getCustomerCity(), customercity);
		Assert.assertEquals(newcustomerscreen.getCustomerZIP(), customerzip);
		customersscreen = newcustomerscreen.clickBackButton();
		homescreen = customersscreen.clickBackButton();
	}

}

package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VNextInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private final RetailCustomer testcustomer = new RetailCustomer("Retail", "Automation");
	private final String testVIN = "1FMCU0DG4BK830800";
	private final String testInsurenceCompany = "Test Insurance Company";
	
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
		BaseUtils.waitABit(2000);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), testVIN);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), vehiclemake);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), vehiclemodel);
		Assert.assertEquals(vehicleinfoscreen.getYear(), vehicleyear);
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
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
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(secondservices);
		inspservicesscreen.clickSaveInspectionMenuButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 39549:vNext - Inspections - Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)", 
			description = "Navigate to screen with required fields on 'Save Inspection' called from humburger menu (first step)")
	public void testNavigateToScreenWithRequiredFieldsOnSaveInspectionCalledFromHumburgerMenu_FirstStep() { 
	
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.clickCancelMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleinfoscreen.swipeScreenLeft();
		informationdlg = new VNextInformationDialog(appiumdriver);
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
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
		vehicleinfoscreen.setVIN(testVIN);
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.clickSaveButton();
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);	
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnumber);		
		inspectionsscreen.clickBackButton();
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
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(servicename);
		vehicleinfoscreen = inspservicesscreen.goBackToInspectionVehicleInfoScreen();
		AppiumUtils.clickHardwareBackButton();
		BaseUtils.waitABit(3000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.ARE_YOU_SURE_STOP_CREATING_INSPECTION);
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		
		inspectionsscreen = new VNextInspectionsScreen(appiumdriver);	
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnumber);		
		inspectionsscreen.clickBackButton();
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
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(testInsurenceCompany);
		inspectionsscreen = claiminfoscreen.saveInspectionViaMenu();
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
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 43525:vNext - Create customer along with Inspection", 
			description = "Create customer along with Inspection")
	public void testCreateCustomerAlongWithInspection() { 
		
		final RetailCustomer customer = new RetailCustomer("Eric", "Burn");
		final String customeraddress = "Stryis'ka, 223";
		final String customercity = "L'viv";
		final String customerzip = "79051";
		final String customeremail = "osmak.oksana+408222@gmail.com";
		
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.setCustomerFirstName(customer.getFirstName());
		newcustomerscreen.setCustomerLastName(customer.getLastName());
		newcustomerscreen.setCustomerEmail(customeremail);
		newcustomerscreen.setCustomerAddress(customeraddress);
		newcustomerscreen.setCustomerCity(customercity);
		newcustomerscreen.setCustomerZIP(customerzip);
		newcustomerscreen.clickSaveCustomerButton();
		BaseUtils.waitABit(7000);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertEquals(vehicleinfoscreen.getCustomercontextValue(), customer.getFullName());
		final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(testInsurenceCompany);
		claiminfoscreen.swipeScreenRight();
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspectionsscreen = vehicleinfoscreen.saveInspectionfromFirstScreen();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnum);
		Assert.assertEquals(inspectionsscreen.getInspectionCustomerValue(inspnum), customer.getFullName());
		homescreen = inspectionsscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		newcustomerscreen = customersscreen.openCustomerForEdit(customer);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), customer.getFirstName());
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), customer.getLastName());
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), customeraddress);
		Assert.assertEquals(newcustomerscreen.getCustomerCity(), customercity);
		Assert.assertEquals(newcustomerscreen.getCustomerZIP(), customerzip);
		customersscreen = newcustomerscreen.clickBackButton();
		customersscreen.clickBackButton();
	}
	
	@Test(testName= "Automate Test Case 75366:Verify user can archive created Inspection",
			description = "Verify user can archive created Inspection")
	public void testVerifyUserCanArchiveCreatedInspection() {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(testVIN);
		final String inspnumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(testInsurenceCompany);
		inspectionsscreen = claiminfoscreen.saveInspectionViaMenu();
		
		inspectionsscreen = inspectionsscreen.archiveInspection(inspnumber);
		Assert.assertFalse(inspectionsscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
			" still exists, but shouldn't");
		inspectionsscreen.clickBackButton();
		
	}
	
	@Test(testName= "Automate Test Case 75369:Verify user can archive several Inspections",
			description = "Verify user can archive several Inspections")
	public void testVerifyUserCanArchiveSeveralInspections() {
		
		final int INSP_TO_ARCHIVE = 3;
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		int inspnumbers = inspectionsscreen.getInspectionsList().size();
		
		for (int i = 0; i < INSP_TO_ARCHIVE; i++) {
			String inspnumber = inspectionsscreen.getFirstInspectionNumber();
			inspectionsscreen = inspectionsscreen.archiveInspection(inspnumber);
			Assert.assertFalse(inspectionsscreen.isInspectionExists(inspnumber));
		}
		Assert.assertEquals(inspectionsscreen.getInspectionsList().size(),inspnumbers - INSP_TO_ARCHIVE);
		
		inspectionsscreen.clickBackButton();
		
	}

}

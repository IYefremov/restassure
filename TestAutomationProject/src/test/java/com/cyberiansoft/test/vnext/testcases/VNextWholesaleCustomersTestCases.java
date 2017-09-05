package com.cyberiansoft.test.vnext.testcases;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewClientDialogWebPage;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.HomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;

public class VNextWholesaleCustomersTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 63620:Verify created on BO Wholesale customer displays in the list", 
			description = "Verify created on BO Wholesale customer displays in the list")
	public void testVerifyCreatedOnBOWholesaleCustomerDisplaysInTheList() {
		
		final String wholesalecustomer = "Test_Wholesale_BO";
		
		initiateWebDriver();
		webdriverGotoWebPage("https://reconpro.cyberianconcepts.com/Admin/Clients.aspx");

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin("olexandr.kramar@cyberiansoft.com", "test12345");
		ClientsWebPage clientspage = PageFactory.initElements(webdriver,
				ClientsWebPage.class);
		clientspage.searchClientByName(wholesalecustomer);
		if (clientspage.isClientExistsInTable(wholesalecustomer))
			clientspage.deleteClient(wholesalecustomer);
		NewClientDialogWebPage newclientpage = clientspage.clickAddClientButton();
		newclientpage.createWholesaleClient(wholesalecustomer);
		getWebDriver().quit();
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
			
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToWholesaleMode();
		customersscreen.searchCustomerByName(wholesalecustomer);
		customersscreen.selectCustomer(wholesalecustomer);
		VNextNewCustomerScreen newcustomerscreen = new VNextNewCustomerScreen(appiumdriver);
		//newcustomerscreen.g
	}
	
	@Test(testName= "Test Case 63617:Verify user can't create wholesale customers on the device", 
			description = "Verify user can't create wholesale customers on the device")
	public void testVerifyUserCantCreateWholesaleCustomersOnTheDevice() {
		
		final String wholesalecustomerNonExists = "XXXXXXXXXXXX";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
			
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToWholesaleMode();
		Assert.assertFalse(customersscreen.isAddCustomerButtonDisplayed());
		customersscreen.searchCustomerByName(wholesalecustomerNonExists);
		Assert.assertTrue(customersscreen.isNothingFoundCaptionDisplayed());
		customersscreen.clickCancelSearchButton();
		homescreen = customersscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 63629:Verify user can select Wholesale customer when create Inspection", 
			description = "Verify user can select Wholesale customer when create Inspection")
	public void testVerifyUserCanSelectWholesaleCustomerWhenCreateInspection() {
		
		final String wholesalecustomer = "001 - Test Company";
		final String inspType = "AppendToRO";
		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(wholesalecustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		homescreen = inspectionscreen.clickBackButton();
	}

}

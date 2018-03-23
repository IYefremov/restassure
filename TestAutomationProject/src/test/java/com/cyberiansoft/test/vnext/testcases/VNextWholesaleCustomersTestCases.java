package com.cyberiansoft.test.vnext.testcases;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ClientsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.NewClientDialogWebPage;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.VNextStatusScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextWholesailCustomer;

public class VNextWholesaleCustomersTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@Test(testName= "Test Case 63620:Verify created on BO Wholesale customer displays in the list", 
			description = "Verify created on BO Wholesale customer displays in the list")
	public void testVerifyCreatedOnBOWholesaleCustomerDisplaysInTheList() {
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("Test_Wholesale_BO");
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(BOConfigInfo.getInstance().getUserUserName(), BOConfigInfo.getInstance().getUserUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.searchClientByName(wholesalecustomer.getCompany());
		if (clientspage.isClientExistsInTable(wholesalecustomer.getFullName()))
			clientspage.deleteClient(wholesalecustomer.getFullName());
		NewClientDialogWebPage newclientpage = clientspage.clickAddClientButton();
		newclientpage.createWholesaleClient(wholesalecustomer.getCompany());
		DriverBuilder.getInstance().getDriver().quit();
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
			
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToWholesaleMode();
		customersscreen.searchCustomerByName(wholesalecustomer.getCompany());
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
		customersscreen.clickBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
	}
	
	@Test(testName= "Test Case 63629:Verify user can select Wholesale customer when create Inspection", 
			description = "Verify user can select Wholesale customer when create Inspection")
	public void testVerifyUserCanSelectWholesaleCustomerWhenCreateInspection() {
		
		final VNextWholesailCustomer wholesalecustomer = new VNextWholesailCustomer("001 - Test Company");
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

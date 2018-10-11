package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.config.BOConfigInfo;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextWholesaleCustomersTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@BeforeClass(description="Wholesale Customers Test Cases")
	public void beforeClass() {
	}
	
	//@Test(testName= "Test Case 63620:Verify created on BO Wholesale customer displays in the list",
	//		description = "Verify created on BO Wholesale customer displays in the list")
	public void testVerifyCreatedOnBOWholesaleCustomerDisplaysInTheList() {
		
		final WholesailCustomer wholesalecustomer = new WholesailCustomer();
		wholesalecustomer.setCompanyName("Test_Wholesale_BO");
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(BOConfigInfo.getInstance().getBackOfficeURL());
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(BOConfigInfo.getInstance().getUserName(), BOConfigInfo.getInstance().getUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		
		ClientsWebPage clientspage = companypage.clickClientsLink();
		clientspage.searchClientByName(wholesalecustomer.getCompany());
		if (clientspage.isClientPresentInTable(wholesalecustomer.getFullName()))
			clientspage.deleteClient(wholesalecustomer.getFullName());
		NewClientDialogWebPage newclientpage = clientspage.clickAddClientButton();
		newclientpage.createWholesaleClient(wholesalecustomer.getCompany());
		DriverBuilder.getInstance().getDriver().quit();
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
			
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

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		homescreen = inspectionscreen.clickBackButton();
	}

}

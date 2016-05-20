package com.cyberiansoft.test.vnext.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionTypesVehicleInfoSettingsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionTypesWebPage;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionServicesScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;

public class VNextVehicleScreenFieldsReflectingOnBOSettings extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final String testcustomer = "111 111";
	final String inspectiontype = "Lily_with_service Package";
	
	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34343:vNext - Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateVINFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		initiateWebDriver();
		webdriver.get(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectVINVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		Assert.assertFalse(vehicleinfoscreen.isVINFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectVINVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		Assert.assertTrue(vehicleinfoscreen.isVINFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34344:vNext - Validate Make field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate Make field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateMakeFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		initiateWebDriver();
		webdriver.get(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectMakeVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		Assert.assertFalse(vehicleinfoscreen.isMakeFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectMakeVisible();
		vehicleinfosettingspage.selectMakeRequired();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		Assert.assertTrue(vehicleinfoscreen.isMakeFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34345:vNext - Validate Model field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate Model field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateModelFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		initiateWebDriver();
		webdriver.get(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectModelVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionServicesScreen inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		VNextVehicleInfoScreen vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		Assert.assertFalse(vehicleinfoscreen.isModelFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectModelVisible();
		vehicleinfosettingspage.selectModelRequired();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		inspservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		vehicleinfoscreen = inspservicesscreen.goToVehicleInfoScreen();
		Assert.assertTrue(vehicleinfoscreen.isModelFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
}

package com.cyberiansoft.test.vnext.testcases;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionTypesVehicleInfoSettingsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionTypesWebPage;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehicleInfoScreen;

public class VNextVehicleScreenFieldsReflectingOnBOSettings extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
	
	final RetailCustomer testcustomer = new RetailCustomer("111", "111");
	final String inspectiontype = "Lily_with_service Package";
	
	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34343:vNext - Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateVINFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
			
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertTrue(vehicleinfoscreen.isModelFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34346:vNext - Validate Color field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate Color field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateColorFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		vehicleinfosettingspage.unselectColorVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertFalse(vehicleinfoscreen.isColorFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectColorVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertTrue(vehicleinfoscreen.isColorFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34347:vNext - Validate Year field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate Year field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateYearFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		vehicleinfosettingspage.unselectYearVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertFalse(vehicleinfoscreen.isYearFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectYearVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertTrue(vehicleinfoscreen.isYearFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34719:vNext - Validate Stock # field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate Stock # field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateStockNumberFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		vehicleinfosettingspage.unselectStockVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertFalse(vehicleinfoscreen.isStockNumberFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectStockVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertTrue(vehicleinfoscreen.isStockNumberFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34720:vNext - Validate RO # field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate RO # field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateRONumberFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		vehicleinfosettingspage.unselectROVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertFalse(vehicleinfoscreen.isRONumberFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectROVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertTrue(vehicleinfoscreen.isRONumberFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Parameters({ "user.name", "user.psw"})
	@Test(testName= "Test Case 34349:vNext - Validate Mileage field on Vehicle screen reflects 'Visible' ON/OFF", 
			description = "Validate Mileage field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateMileageFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		vehicleinfosettingspage.unselectMileageVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertFalse(vehicleinfoscreen.isMilageFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();
		
		insptypepage = companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectMileageVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		Assert.assertTrue(vehicleinfoscreen.isMilageFieldVisible());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	//@Parameters({ "user.name", "user.psw"})
	//@Test(testName= "Test Case 34342:vNext - Validate field order is shown correctly on Vehicle Info screen", 
			//description = "Validate field order is shown correctly on Vehicle Info screen")
	public void testValidateFieldOrderIsShownCorrectlyOnVehicleInfoScreen(String deviceuser, String devicepsw) {
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
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
		List<WebElement> elementfields = vehicleinfosettingspage.getDisplayedColumnsListItems();
		/*for (WebElement elm : elements) {
			if (!elm.getText().equals("Owner") & !elm.getText().equals("Fuel Tank Level") & !elm.getText().equals("Advisor"))
				System.out.println("++++++" + elm.getText());
		}*/
		
		List<String> fields = new ArrayList<String>();
    	for (WebElement element : elementfields)
    		if (!element.getText().equals("Owner") & !element.getText().equals("Fuel Tank Level") & !element.getText().equals("Advisor"))
    			fields.add(element.getText());
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		
		homescreen = loginscreen.userLogin(testEmployee, testEmployeePsw);
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		List<String> vehicleelements = vehicleinfoscreen.getDisplayedVehicleFieldsListItems();
		Iterator<String> it1 = fields.iterator();
		Iterator<String> it2 = vehicleelements.iterator();
		/*for (WebElement vhclfld : vehicleelements) {
			System.out.println("++++++" + vhclfld.getText());
		}*/
		while (it1.hasNext() && it2.hasNext()) {
			String fldbovalue = it1.next();
			String flddevvalue = it2.next();
			
			System.out.println("++++++" + fldbovalue);
			System.out.println("++++++" + flddevvalue);
			System.out.println("++++++" + fldbovalue.contains(flddevvalue.substring(0, flddevvalue.length()-2)));
			System.out.println("++++++" + flddevvalue.substring(0, flddevvalue.length()-2));
			
			/*if (fldvalue.equals("Vehicle Type"))
				Assert.assertTrue(it2.next().getText().equals("Type"));
			else if (fldvalue.equals("Tag # (Lic. Plate)"))
				Assert.assertTrue(it2.next().getText().equals("Lic. Plate"));
			else if (fldvalue.equals("PO #"))
				Assert.assertTrue(it2.next().getText().equals("PO#"));
			else if (fldvalue.equals("Stock #"))
				Assert.assertTrue(it2.next().getText().equals("Stock#"));
			else if (fldvalue.equals("RO #"))
				Assert.assertTrue(it2.next().getText().equals("RO#"));*/
		}
		
	}
}

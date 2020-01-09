package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class VNextVehicleScreenFieldsReflectingOnBOSettings extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	final RetailCustomer testcustomer = new RetailCustomer("111", "111");
	final String inspectiontype = "Lily_with_service Package";

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34343:vNext - Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateVINFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectVINVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.VIN, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectVINVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.VIN, true);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34344:vNext - Validate Make field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate Make field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateMakeFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectMakeVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.MAKE, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectMakeVisible();
		vehicleinfosettingspage.selectMakeRequired();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.MAKE, true);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34345:vNext - Validate Model field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate Model field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateModelFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectModelVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();

		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.MODEL, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectModelVisible();
		vehicleinfosettingspage.selectModelRequired();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.MODEL, true);

		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34346:vNext - Validate Color field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate Color field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateColorFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectColorVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();

		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.COLOR, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectColorVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.COLOR, true);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34347:vNext - Validate Year field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate Year field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateYearFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectYearVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.YEAR, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectYearVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.YEAR, true);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34719:vNext - Validate Stock # field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate Stock # field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateStockNumberFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectStockVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.STOCK_NO, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectStockVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.STOCK_NO, true);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34720:vNext - Validate RO # field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate RO # field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateRONumberFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectROVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.RO_NO, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectROVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.RO_NO, true);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Parameters({"user.name", "user.psw"})
	@Test(testName = "Test Case 34349:vNext - Validate Mileage field on Vehicle screen reflects 'Visible' ON/OFF",
			description = "Validate Mileage field on Vehicle screen reflects 'Visible' ON/OFF")
	public void testValidateMileageFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(deviceuser, devicepsw);
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		CompanyWebPage companypage = new CompanyWebPage(webdriver);
		backofficeheader.clickCompanyLink();
		InspectionTypesWebPage insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		final String mainWindowHandle = webdriver.getWindowHandle();
		InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.unselectMileageVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);

		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.MILAGE, false);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
		backofficeheader.clickCompanyLink();

		insptypepage = new InspectionTypesWebPage(webdriver);
		companypage.clickInspectionTypesLink();
		vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
		vehicleinfosettingspage.selectMileageVisible();
		vehicleinfosettingspage.clickUpdateButton();
		vehicleinfosettingspage.closeNewTab(mainWindowHandle);
		webdriver.quit();
		loginscreen = homescreen.clickLogoutButton();
		loginscreen.updateMainDB();

		homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenValidations.dataFiledShouldBeVisible(VehicleDataField.MILAGE, true);
		inspectionsscreen = vehicleInfoScreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

}

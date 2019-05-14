package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextLoginScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VNextVehicleScreenFieldsReflectingOnBOSettings extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    final RetailCustomer testcustomer = new RetailCustomer("111", "111");
    final String inspectiontype = "Lily_with_service Package";

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34343:vNext - Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate VIN field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateVINFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isVINFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34344:vNext - Validate Make field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate Make field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateMakeFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isMakeFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34345:vNext - Validate Model field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate Model field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateModelFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isModelFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34346:vNext - Validate Color field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate Color field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateColorFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isColorFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34347:vNext - Validate Year field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate Year field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateYearFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isYearFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34719:vNext - Validate Stock # field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate Stock # field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateStockNumberFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isStockNumberFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34720:vNext - Validate RO # field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate RO # field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateRONumberFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isRONumberFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Parameters({"user.name", "user.psw"})
    @Test(testName = "Test Case 34349:vNext - Validate Mileage field on Vehicle screen reflects 'Visible' ON/OFF",
            description = "Validate Mileage field on Vehicle screen reflects 'Visible' ON/OFF")
    public void testValidateMileageFieldOnVehicleScreenReflectsVisibleONOFF(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(vehicleinfoscreen.isMilageFieldVisible());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    //@Parameters({ "user.name", "user.psw"})
    //@Test(testName= "Test Case 34342:vNext - Validate field order is shown correctly on Vehicle Info screen",
    //description = "Validate field order is shown correctly on Vehicle Info screen")
    public void testValidateFieldOrderIsShownCorrectlyOnVehicleInfoScreen(String deviceuser, String devicepsw) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLoginScreen loginscreen = homescreen.clickLogoutButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
        InspectionTypesWebPage insptypepage = companypage.clickInspectionTypesLink();
        final String mainWindowHandle = webdriver.getWindowHandle();
        InspectionTypesVehicleInfoSettingsWebPage vehicleinfosettingspage = insptypepage.clickInspectionVehicleInfoSettingLink(inspectiontype);
        List<WebElement> elementfields = vehicleinfosettingspage.getDisplayedColumnsListItems();

        List<String> fields = new ArrayList<>();
        for (WebElement element : elementfields)
            if (!element.getText().equals("Owner") & !element.getText().equals("Fuel Tank Level") & !element.getText().equals("Advisor"))
                fields.add(element.getText());
        vehicleinfosettingspage.clickUpdateButton();
        vehicleinfosettingspage.closeNewTab(mainWindowHandle);

        homescreen = loginscreen.userLogin(employee.getEmployeeName(), employee.getEmployeeLastName());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        List<String> vehicleelements = vehicleinfoscreen.getDisplayedVehicleFieldsListItems();
        Iterator<String> it1 = fields.iterator();
        Iterator<String> it2 = vehicleelements.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            String fldbovalue = it1.next();
            String flddevvalue = it2.next();
        }

    }
}

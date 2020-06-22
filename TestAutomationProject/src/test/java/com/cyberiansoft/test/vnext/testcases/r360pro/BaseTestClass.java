package com.cyberiansoft.test.vnext.testcases.r360pro;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.targetprocessintegration.dto.TestPlanRunDTO;
import com.cyberiansoft.test.targetprocessintegration.model.TPIntegrationService;
import com.cyberiansoft.test.vnext.config.VNextEnvironmentInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.environments.EnvironmentType;
import com.cyberiansoft.test.vnext.listeners.TestServiceListener;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.utils.VNextClientEnvironmentUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.util.Optional;

public class BaseTestClass {

    protected static RetailCustomer testcustomer;
    protected static WholesailCustomer testwholesailcustomer;
    protected static String deviceOfficeUrl;
    protected static EnvironmentType environmentType = EnvironmentType.QC;
    protected static String deviceUrl = "http://208.87.18.5:8082/";
    @Getter
    protected static BrowserType browserType = BrowserType.CHROME;
    @Getter
    protected static Employee employee;
    protected static Employee inspector;
    protected static Employee manager;

    public BaseTestClass() {
        testcustomer = new RetailCustomer();
        testcustomer.setFirstName("000Automation");
        testcustomer.setLastName("Retail");
        //testcustomer.setFirstName("Olegg");
        //testcustomer.setLastName("Rom");

        testwholesailcustomer = new WholesailCustomer();
        testwholesailcustomer.setFirstName("Automation");
        testwholesailcustomer.setLastName("Wholesale");
        testwholesailcustomer.setCompanyName("007 - Test Company1");

        employee = new Employee();
        inspector = new Employee();
        manager = new Employee();
        //employee.setEmployeeFirstName("Ivan");
        //employee.setEmployeeLastName("Yefremov1");
        //employee.setEmployeePassword("111111");
        employee.setEmployeeFirstName("Employee");
        employee.setEmployeeLastName("Employee");
        employee.setEmployeePassword("12345");
        inspector.setEmployeeFirstName("AutoEmpl_Inspector");
        inspector.setEmployeeLastName("ADD_EDIT_REMOVE");
        inspector.setEmployeePassword("111111");
        manager.setEmployeeFirstName("AutoEmpl_Manager");
        manager.setEmployeeLastName("ADD_EDIT_REMOVE");
        manager.setEmployeePassword("111111");


    }


    @BeforeSuite
    @Parameters({"lic.name"})
    public void beforeSuite(String licenseName) {
        Optional<String> browserParam = Optional.ofNullable(System.getProperty("browser"));
        if (browserParam.isPresent())
            browserType = BaseUtils.getBrowserType(browserParam.get());

        Optional<String> testEnv = Optional.ofNullable(System.getProperty("testEnv"));
        if (testEnv.isPresent())
            environmentType = EnvironmentType.getEnvironmentType(testEnv.get());

        Optional<String> deviceURLParam = Optional.ofNullable(System.getProperty("clientWebURL"));
        if (deviceURLParam.isPresent())
            deviceUrl = deviceURLParam.get();

        Optional<String> boURLParam = Optional.ofNullable(System.getProperty("testBOURL"));
        if (boURLParam.isPresent())
            deviceOfficeUrl = boURLParam.get();
        else {
            deviceOfficeUrl = VNextClientEnvironmentUtils.getBackOfficeURL(environmentType);
        }

        Optional<String> testPlanIdFromMaven = Optional.ofNullable(System.getProperty("testPlanId"));
        //Optional<String> testCaseIdFromMaven = Optional.ofNullable("97261");
        if (testPlanIdFromMaven.isPresent()) {
            TPIntegrationService tpIntegrationService = new TPIntegrationService();
            String testPlanId = testPlanIdFromMaven.get();
            try {
                TestPlanRunDTO testPlanRunDTO = tpIntegrationService.createTestPlanRun(testPlanId);
                TestServiceListener.setTestPlanRunId(testPlanRunDTO.getId().toString());
                TestServiceListener.setTestToTestRunMap(
                        tpIntegrationService.testCaseToTestRunMapRecursively(
                                testPlanRunDTO));
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
        }

        WebDriver chromeWebDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        //chromeWebDriver.get("http://R360user:Geev9ied@77.120.104.171:8000");
        chromeWebDriver.get(deviceUrl);

        if (VNextEnvironmentInfo.getInstance().installNewBuild()) {
            String regCode = getRegistrationCode(licenseName);

            VNextEditionsScreen editionsScreen = new VNextEditionsScreen(chromeWebDriver);
            VNextEnvironmentSelectionScreen environmentSelectionScreen = new VNextEnvironmentSelectionScreen(chromeWebDriver);
            VNextTeamEditionVerificationScreen verificationScreen = new VNextTeamEditionVerificationScreen(chromeWebDriver);
            VNextDownloadDataScreen downloadDataScreen = new VNextDownloadDataScreen(chromeWebDriver);
            VNextInformationDialog informationDialog = new VNextInformationDialog(chromeWebDriver);

            //GeneralSteps.skipGuide();
            editionsScreen.selectEdition("ReconPro");
            environmentSelectionScreen.selectEnvironment(environmentType);
            verificationScreen.setDeviceRegistrationCode(regCode);
            verificationScreen.clickVerifyButton();
            downloadDataScreen.waitUntilDatabasesDownloaded();
            informationDialog.clickInformationDialogOKButton();
        }

        VNextLoginScreen loginScreen = new VNextLoginScreen(chromeWebDriver);
        loginScreen.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
    }

    @AfterSuite
    public void parentAfterClass() {
        //ChromeDriverProvider.INSTANCE.getMobileChromeDriver().quit();
    }


    private String getRegistrationCode(String licenseName) {

        DriverBuilder.getInstance().setBrowserType(browserType).
                setRemoteWebDriverURL("http://aqc-linux2.westus.cloudapp.azure.com:4444/wd/hub")
        .setDriver();
        /*if (browserType.equals(BrowserType.CHROME)) {
            DriverBuilder.getInstance().setDriver(browserType);
        } else
            DriverBuilder.getInstance().setAzureDriver("http://aqc-linux2.westus.cloudapp.azure.com:4444/wd/hub");*/
        WebDriver chromeDriver = DriverBuilder.getInstance().getDriver();
        BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(chromeDriver);
        ActiveDevicesWebPage activeDevicesWebPage = new ActiveDevicesWebPage(chromeDriver);

        chromeDriver.get(deviceOfficeUrl + "/Admin/Devices.aspx");
        loginPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());

        WaitUtils.getGeneralFluentWait().until(driver -> {
            activeDevicesWebPage.setSearchCriteriaByName(licenseName);
            return true;
        });

        String regCode = activeDevicesWebPage.getFirstRegCodeInTable();
        DriverBuilder.getInstance().getDriver().quit();
        // chromeDriver.quit();
        return regCode;
    }
}

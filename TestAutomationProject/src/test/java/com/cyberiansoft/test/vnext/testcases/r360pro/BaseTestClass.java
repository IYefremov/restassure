package com.cyberiansoft.test.vnext.testcases.r360pro;

import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
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
    protected static EnvironmentType environmentType;
    @Getter
    protected static Employee employee;

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
        //employee.setEmployeeFirstName("Ivan");
        //employee.setEmployeeLastName("Yefremov1");
        //employee.setEmployeePassword("111111");
        employee.setEmployeeFirstName("Employee");
        employee.setEmployeeLastName("Employee");
        employee.setEmployeePassword("12345");

        Optional<String> boURLParam = Optional.ofNullable(System.getProperty("testNewBOURL"));
        if (boURLParam.isPresent())
            deviceOfficeUrl = boURLParam.get();
        else {
            Optional<String> testEnv = Optional.ofNullable(System.getProperty("testEnv"));
            if (testEnv.isPresent())
                environmentType = EnvironmentType.getEnvironmentType(testEnv.get());
            else
                environmentType = EnvironmentType.QC;
            deviceOfficeUrl = VNextClientEnvironmentUtils.getBackOfficeURL(environmentType);
        }
    }

    @BeforeSuite
    @Parameters({"lic.name"})
    public void beforeSuite(String licenseName) {

        Optional<String> testCaseIdFromMaven = Optional.ofNullable(System.getProperty("testPlanId"));
        //Optional<String> testCaseIdFromMaven = Optional.ofNullable("97261");
        if (testCaseIdFromMaven.isPresent()) {
            TPIntegrationService tpIntegrationService = new TPIntegrationService();
            String testPlanId = testCaseIdFromMaven.get();
            try {
                TestServiceListener.setTestToTestRunMap(
                        tpIntegrationService.testCaseToTestRunMapRecursively(
                                tpIntegrationService.createTestPlanRun(testPlanId)));
            } catch (UnirestException | IOException e) {
                e.printStackTrace();
            }
        }


        WebDriver chromeWebDriver = ChromeDriverProvider.INSTANCE.getMobileChromeDriver();
        //chromeWebDriver.get("http://R360user:Geev9ied@77.120.104.171:8000");
        chromeWebDriver.get("http://208.87.18.5:8000/");

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
        DriverBuilder.getInstance().setDriver(BrowserType.CHROME);

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

package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.EmailUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionInfoWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class VNextCreateInspectionOnTheClientTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
    final String VIN = "19UUA66278A050105";

    String inspnumbertc47229 = "";
    String inspnumbertc46975 = "";
    String inspnumbertc47233 = "";


    @Test(testName = "Test Case 46975:vNext: Check Approved ammount is calculated correctly for Approved inspection",
            description = "Check Approved ammount is calculated correctly for Approved inspection")
    public void testCheckApprovedAmountIsCalculatedCorrectlyForApprovedInspection() throws Exception {

        final String _make = "Acura";
        final String _model = "TL";
        final String _year = "2008";
        final String percservices = "Aluminum Upcharge";
        final String moneyservices = "Dent Repair";
        final String matrixservice = "Hail Dent Repair";
        final String matrixsubservice = "State Farm";
        final String moneyserviceprice = "58";
        final String moneyservicequant = "1";
        final String vehiclepartname = "Hood";
        final String vehiclepartsize = "Dime";
        final String vehiclepartseverity = "Light 6 to 15";
        final String additionalservice = "Aluminum Upcharge";
        final String insppriceexp = "267.81";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(VIN);
        BaseUtils.waitABit(1000);
        Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), _make);
        Assert.assertEquals(vehicleInfoScreen.getModelInfo(), _model);
        Assert.assertEquals(vehicleInfoScreen.getYear(), _year);
        inspnumbertc46975 = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(percservices);
        availableServicesScreen.selectService(moneyservices);
        VNextPriceMatrixesScreen priceMatrixessScreen = availableServicesScreen.openMatrixServiceDetails(matrixservice);
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixessScreen.selectHailMatrix(matrixsubservice);
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclepartname);
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclepartsize);
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclepartseverity);
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalservice);
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), matrixsubservice);

        selectedServicesScreen.setServiceAmountValue(moneyservices, moneyserviceprice);
        selectedServicesScreen.setServiceQuantityValue(moneyservices, moneyservicequant);

        inspectionsScreen = availableServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspnumbertc46975), PricesCalculations.getPriceRepresentation(insppriceexp));
        VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspnumbertc46975);
        if (!emailScreen.getToEmailFieldValue().equals(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail()))
            emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail());

        emailScreen.sendEmail();

        final String inspectionreportfilenname = inspnumbertc46975 + ".pdf";
        EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextFreeRegistrationInfo.getInstance().getR360OutlookMail(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword(), EmailFolder.JUNK);
        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspnumbertc46975, inspectionreportfilenname).unreadOnlyMessages(true).maxMessagesToSearch(5);
        Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find inspection: " + inspnumbertc46975);

        File pdfDoc = new File(inspectionreportfilenname);
        String pdfText = PDFReader.getPDFText(pdfDoc);
        Assert.assertTrue(pdfText.contains(VIN));
        Assert.assertTrue(pdfText.contains(percservices));
        Assert.assertTrue(pdfText.contains(moneyservices));
        Assert.assertTrue(pdfText.contains(insppriceexp));
    }

    @Test(testName = "Test Case 47229:vNext mobile: Create Inspection which contains breakage service with big quantity",
            description = "Create Inspection which contains breakage service with big quantity")
    public void testCreateInspectionWhichContainsBreakageServiceWithBigQuantity() {


        final String selectdamage = "Dent Repair";
        final String amountvalue = "1577.20";
        final String finalprice = "$1733.45";

        final String matrixservice = "Hail Dent Repair";
        final String pricematrix = "State Farm";
        final String vehiclepartname = "Hood";
        final String vehiclepartsize = "Dime";
        final String vehiclepartseverity = "Light 6 to 15";
        final String additionalservice = "Aluminum Upcharge";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(VIN);
        inspnumbertc47229 = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(selectdamage);
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen servicedetailsscreen = visualScreen.clickCarImageMarker();
        servicedetailsscreen.setServiceAmountValue(amountvalue);
        Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
        servicedetailsscreen.setServiceQuantityValue("1");
        servicedetailsscreen.clickServiceDetailsDoneButton();
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextPriceMatrixesScreen priceMatrixessScreen = availableServicesScreen.openMatrixServiceDetails(matrixservice);
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixessScreen.selectHailMatrix(pricematrix);
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclepartname);
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclepartsize);
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclepartseverity);
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalservice);
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), pricematrix);

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspnumbertc47229), finalprice);
        homeScreen = inspectionsScreen.clickBackButton();
    }

    @Test(testName = "Test Case 47231:vNext mobile: Create Inspection with full populated customer info",
            description = "Create Inspection with full populated customer info")
    public void testCreateInspectionWhithFullPopulatedCustomerInfo() {

        RetailCustomer testcustomer = new RetailCustomer("CustomerFirstName", "CustomerLastName");
        testcustomer.setMailAddress("osmak.oksana+408222@gmail.com");
        testcustomer.setCustomerPhone("978385064");

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
        newCustomerScreen.createNewCustomer(testcustomer);

        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(VIN);
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();

        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), "$0.00");
        homeScreen = inspectionsScreen.clickBackButton();
    }

    @Test(testName = "Test Case 47233:vNext mobile: Create Inspection with customer with First name only",
            description = "Create Inspection with customer with First name only")
    public void testCreateInspectionWhithCustomerWithFirstNameOnly() {

        RetailCustomer testcustomer = new RetailCustomer();
        testcustomer.setFirstName("CustomerFirstName");
        testcustomer.setMailAddress("osmak.oksana+408222@gmail.com");
        testcustomer.setCustomerPhone("978385064");

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
        newCustomerScreen.createNewCustomer(testcustomer);

        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(VIN);
        inspnumbertc47233 = vehicleInfoScreen.getNewInspectionNumber();

        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspnumbertc47233), "$0.00");
        homeScreen = inspectionsScreen.clickBackButton();
    }

    @Test(testName = "Test Case 52818:vNext mobile: create and email Inspection with two matrix panel",
            description = "Create and email Inspection with two matrix panel")
    @Parameters({"usercapi.mail"})
    public void testCreateAndEmailInspectionWithTtwoMatrixPanel(String usermail) {

        final String VIN = "JT3AC11R4N1023558";
        final String _make = "Toyota";
        final String _model = "Previa";
        final String _year = "1992";
        final String matrixservice = "Hail Dent Repair";
        final String matrixsubservice = "State Farm";
        final String[] vehiclepartsname = {"Hood", "Roof"};
        final String vehiclepartsize = "Dime";
        final String vehiclepartseverity = "Light 6 to 15";
        final String finalprice = "$660.00";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(VIN);
        BaseUtils.waitABit(1000);
        Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), _make);
        Assert.assertEquals(vehicleInfoScreen.getModelInfo(), _model);
        Assert.assertEquals(vehicleInfoScreen.getYear(), _year);
        String inspnumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextPriceMatrixesScreen priceMatrixessScreen = availableServicesScreen.openMatrixServiceDetails(matrixservice);

        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixessScreen.selectHailMatrix(matrixsubservice);
        for (String vehiclePartName : vehiclepartsname) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartName);
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclepartsize);
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclepartseverity);
            List<String> additionalServices = vehiclePartInfoScreen.getListOfAdditionalServices();
            for (String serviceName : additionalServices)
                vehiclePartInfoScreen.selectVehiclePartAdditionalService(serviceName);
            vehiclePartInfoScreen.clickSaveVehiclePartInfo();
            vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());

        }
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspnumber), finalprice);
        VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspnumber);
        if (!emailScreen.getToEmailFieldValue().equals(usermail))
            emailScreen.sentToEmailAddress(usermail);

        emailScreen.sendEmail();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        homeScreen = inspectionsScreen.clickBackButton();
    }

    @Test(testName = "Test Case 40794:vNext: verify displaying inspection which contains breakage service with big quantity",
            description = "Verify displaying inspection which contains breakage service with big quantity",
            dependsOnMethods = {"testCreateInspectionWhichContainsBreakageServiceWithBigQuantity"})
    @Parameters({"backofficecapi.url", "usercapi.name", "usercapi.psw"})
    public void testVerifyDisplayingInspectionWhichContainsBreakageServiceWithBigQuantity(String bourl, String username, String userpsw) {
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(bourl);
        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(username, userpsw);
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspnumbertc47229);
        Assert.assertEquals(inspectionsWebPage.getSelectedInspectionCustomerName(), testcustomer);
        Assert.assertTrue(inspectionsWebPage.isServicePresentForSelectedInspection("Dent Repair"));
        Assert.assertTrue(inspectionsWebPage.isServicePresentForSelectedInspection("Hail Dent Repair"));
        Assert.assertTrue(inspectionsWebPage.isImageLegendContainsBreakageIcon("Hail Damage"));
        Assert.assertTrue(inspectionsWebPage.isImageLegendContainsBreakageIcon("Dent"));
    }

    @Test(testName = "Test Case 47236:vNext: verify displaying approved amount for Inspection",
            description = "Verify displaying approved amount for Inspection",
            dependsOnMethods = {"testCheckApprovedAmountIsCalculatedCorrectlyForApprovedInspection"})
    @Parameters({"backofficecapi.url", "usercapi.name", "usercapi.psw"})
    public void testVerifyDisplayingApprovedAmountForInspection(String bourl, String username, String userpsw) {

        final String insppriceexp = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(bourl);
        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(username, userpsw);
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspnumbertc46975);
        Assert.assertEquals(inspectionsWebPage.getSelectedInspectionTotalAmauntValue(), insppriceexp);
        String mainWindowHandle = webdriver.getWindowHandle();

        VNextBOInspectionInfoWebPage inspectionInfoWebPage = inspectionsWebPage.clickSelectedInspectionPrintIcon();
        inspectionInfoWebPage.closeNewTab(mainWindowHandle);
    }

    @Test(testName = "Test Case 47237:vNext: verify displaying Inspection with customer with First name only",
            description = "Verify displaying inspection with customer with First name only",
            dependsOnMethods = {"testCreateInspectionWhithCustomerWithFirstNameOnly"})
    @Parameters({"backofficecapi.url", "usercapi.name", "usercapi.psw"})
    public void testVerifyDisplayingInspectionWithCustomerWithFirstNameOnly(String bourl, String username, String userpsw) {
        final String firstname = "CustomerFirstName";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(bourl);
        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(username, userpsw);
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspnumbertc47233);
        Assert.assertEquals(inspectionsWebPage.getSelectedInspectionCustomerName(), firstname);
    }

}

package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.ServicePackagesWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class vNextInspectionServicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360-inspection-services-testcases-data.json";

    @BeforeClass(description="R360 Inspection Services Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testShowSelectedServicesAfterInspectionIsSaved(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        final String inspnum = inspservicesscreen.getNewInspectionNumber();
        inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
        vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspectionsscreen = inspservicesscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testShowSelectedServicesForInspectionWhenNavigatingFromServicesScreen(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspservicesscreen.swipeScreenLeft();
        inspservicesscreen.swipeScreenRight();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspservicesscreen.swipeScreenLeft();
        inspservicesscreen.swipeScreenRight();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddOneServiceToAlreadySelectedServicesWhenInspectionIsEdited(String rowID,
                                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(0));
        inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(1));
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(0)));
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(1)));
        final String inspnum = selectedServicesScreen.getNewInspectionNumber();
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();

        vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(0)));
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(1)));

        inspservicesscreen = inspservicesscreen.switchToAvalableServicesView();
        inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(2));
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(2)));
        inspectionsscreen = inspservicesscreen.saveInspectionViaMenu();
        vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddSeveralServicesToAlreadySelectedServicesWhenInspectionIsEdited(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final int firstPartNumber = 2;

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        for (int i =0; i< firstPartNumber; i++)
            inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(i));
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (int i =0; i< firstPartNumber; i++)
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(i)));
        final String inspnum = selectedServicesScreen.getNewInspectionNumber();
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (int i =0; i< firstPartNumber; i++)
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getServiceNameByIndex(i)));
        inspservicesscreen = inspservicesscreen.switchToAvalableServicesView();
        for (int i =firstPartNumber; i< inspectionData.getServicesList().size(); i++)
            inspservicesscreen.selectService(inspectionData.getServiceNameByIndex(i));
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifySelectedServicesAreSavedToBO(String rowID,
                                                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        final String inspnum = inspservicesscreen.getNewInspectionNumber();
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsscreen.clickBackButton();
        BaseUtils.waitABit(30000);
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionspage = leftmenu.selectInspectionsMenu();
        inspectionspage.selectInspectionInTheList(inspnum);
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(inspectionspage.isServicePresentForSelectedInspection(service.getServiceName()));
        webdriver.quit();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyLettersIOQAreTrimmedWhileManualEntry(String rowID,
                                                       String description, JSONObject testData) {

        final String vinnumber = "AI0YQ56ONJ";
        final String vinnumberverify = "A0Y56NJ";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(vinnumber);
        Assert.assertEquals(inspinfoscreen.getVINFieldValue(), vinnumberverify);
        inspectionsscreen = inspinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyNotAllowedCharactersAreTrimmedWhileManualEntry(String rowID,
                                                               String description, JSONObject testData) {

        final String vinnumber = "*90%$2~!$!`\":;\'<>?,./+=_-)(*&^#@\\|";
        final String vinnumberverify = "902";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumberverify);
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyLettersAreCapitalizedWhileManualEntry(String rowID,
                                                                         String description, JSONObject testData) {

        final String vinnumber = "abc458yhgd8bn";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(vinnumber);
        Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vinnumber.toUpperCase());
        inspectionsscreen = vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyServicesAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu(String rowID,
                                                                String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        selectedServicesScreen.saveInspectionViaMenu();

        vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyVehicleClaimAreSavedWhenSaveInspectionOptionWasUsedFromHumburgerMenu(String rowID,
                                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());

        final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
        final VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
        vehicleinfoscreen.setMilage(vehicleInfoData.getMileage());
        vehicleinfoscreen.setLicPlate(vehicleInfoData.getVehicleLicensePlate());
        vehicleinfoscreen.setStockNo(vehicleInfoData.getStockNumber());
        vehicleinfoscreen.setRoNo(vehicleInfoData.getRoNumber());
        vehicleinfoscreen.swipeScreenLeft();

        inspectionsscreen = vehicleinfoscreen.saveInspectionViaMenu();
        vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), vehicleInfoData.getVINNumber());
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), vehicleInfoData.getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), vehicleInfoData.getVehicleModel());
        Assert.assertEquals(vehicleinfoscreen.getYear(), vehicleInfoData.getVehicleYear());
        Assert.assertEquals(vehicleinfoscreen.getType(), vehicleInfoData.getVehicleType());
        Assert.assertEquals(vehicleinfoscreen.getMilage(), vehicleInfoData.getMileage());
        Assert.assertEquals(vehicleinfoscreen.getLicPlate(), vehicleInfoData.getVehicleLicensePlate());
        Assert.assertEquals(vehicleinfoscreen.getStockNo(), vehicleInfoData.getStockNumber());
        Assert.assertEquals(vehicleinfoscreen.getRoNo(), vehicleInfoData.getRoNumber());

        vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testExitCancelInspectionStateCalledFromHumburgerMenu_FirstStep(String rowID,
                                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.clickCancelMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        vehicleinfoscreen.swipeScreenLeft();
        vehicleinfoscreen.clickCancelMenuItem();
        new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        AppiumUtils.clickHardwareBackButton();
        AppiumUtils.clickHardwareBackButton();
        AppiumUtils.clickHardwareBackButton();
        informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
        Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
        vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testShowAllAssignedToServicePackageServicesAsAvailableOnes(String rowID,
                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        CompanyWebPage companypage = backofficeheader.clickCompanyLink();
        ServicePackagesWebPage servicepckgspage = companypage.clickServicePackagesLink();
        String mainWindowHandle = webdriver.getWindowHandle();
        servicepckgspage.clickServicesLinkForServicePackage("All Services");
        List<WebElement> allservices = servicepckgspage.getAllServicePackageItems();
        List<String> allservicestxt = new ArrayList<>();
        for (WebElement lst : allservices)
            allservicestxt.add(lst.getText());
        servicepckgspage.closeNewTab(mainWindowHandle);
        webdriver.quit();

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        List<WebElement> services = inspservicesscreen.getServicesListItems();
        List<String> servicestxt = new ArrayList<>();
        for (WebElement lst : services)
            servicestxt.add(inspservicesscreen.getServiceListItemName(lst));

        for (String srv : allservicestxt) {
            Assert.assertTrue(servicestxt.contains(srv));
        }
        AppiumUtils.clickHardwareBackButton();
        vehicleinfoscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyDefaultPriceForServiceIsShownCorrectly(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();

        inspservicesscreen.selectServices(inspectionData.getMoneyServicesList());
        inspservicesscreen.selectServices(inspectionData.getPercentageServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        for (ServiceData moneyService : inspectionData.getMoneyServicesList())
            Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(moneyService.getServiceName()), moneyService.getServicePrice());
        for (ServiceData percService : inspectionData.getPercentageServicesList())
            Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(percService.getServiceName()), percService.getServicePrice());

        selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddTheSameServiceMultipleTimes(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final int servicesNumberSelected = 2;

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(service.getServiceName()));
        inspservicesscreen = selectedServicesScreen.switchToAvalableServicesView();
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(service.getServiceName()), servicesNumberSelected);

        final String inspnum = selectedServicesScreen.getNewInspectionNumber();
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();

        vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnum);
        inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData service : inspectionData.getServicesList())
            Assert.assertEquals(selectedServicesScreen.getQuantityOfSelectedService(service.getServiceName()), servicesNumberSelected);

        inspectionsscreen = inspservicesscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditInspectionServices(String rowID,
                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String inspPrice = "$242.00";
        final String firstMoneyServicePrice = "5";
        final String secondMoneyServicePrice = "84.55";
        final String secondMoneyServiceQty = "9.15";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspnumber = inspinfoscreen.getNewInspectionNumber();
        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            selectedServicesScreen.setServiceAmountValue(serviceData.getServiceName(), serviceData.getServicePrice());
        }
        Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), inspPrice);
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();

        inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
        inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        selectedServicesScreen.uselectService(inspectionData.getServicesList().get(0).getServiceName());

        inspservicesscreen = selectedServicesScreen.switchToAvalableServicesView();
        inspservicesscreen.selectServices(inspectionData.getMoneyServicesList());
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        for (ServiceData serviceData : inspectionData.getMoneyServicesList()) {
            selectedServicesScreen.setServiceAmountValue(serviceData.getServiceName(), serviceData.getServicePrice());
        }

        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServicesList().get(0).getServiceName(),
                firstMoneyServicePrice);
        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServicesList().get(1).getServiceName(),
                secondMoneyServicePrice);
        selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServicesList().get(1).getServiceName(),
                secondMoneyServiceQty);

        Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(),
                BackOfficeUtils.getFormattedServicePriceValue(BackOfficeUtils.getServicePriceValue("$1000.63")));
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPriceMatrixAddedToServicePackageIsAvailableToChooseWhenAddEditInspection(String rowID,
                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String matrixservice = "Hail Dent Repair";
        final String[] availablepricematrixes = {"Nationwide Insurance", "Progressive", "State Farm"};
        final String vehiclepartname = "Hood";
        final String vehiclepartsize = "Dime";
        final String vehiclepartseverity = "Light 6 to 15";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(availablepricematrixes[2]);
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
        vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
        vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyListOfAvailablePriceMatricesIsLoadedWhenChoosingMatrixService(String rowID,
                                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String matrixservice = "Hail Dent Repair";
        final String[] availablepricematrixes = {"Nationwide Insurance", "Progressive", "State Farm"};
        final String vehiclepartname = "Hood";
        final String vehiclepartsize = "Dime";
        final String vehiclepartseverity = "Light 6 to 15";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
        for (String pricematrix : availablepricematrixes)
            Assert.assertTrue(pricematrixesscreen.isPriceMatrixExistsInTheList(pricematrix));
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(availablepricematrixes[2]);
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
        vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
        vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPriceMatrixNameIsShownOnSelectServicesScreenAfterSelection(String rowID,
                                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String matrixservice = "Hail Dent Repair";
        final String[] availablepricematrixes = {"Nationwide Insurance", "Progressive", "State Farm"};
        final String vehiclepartname = "Hood";
        final String vehiclepartsize = "Dime";
        final String vehiclepartseverity = "Light 6 to 15";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(availablepricematrixes[2]);
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
        vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
        vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), availablepricematrixes[2]);
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectPriceIsShownInServicesListAfterEditingServicePricePercentage(String rowID,
                                                                                     String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String servicetoselect = "555";
        final String serviceprice = "0.015%";
        final String servicelastsymbol = "8";
        final String newserviceprice = "0.018%";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectService(servicetoselect);
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(servicetoselect));
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicetoselect), serviceprice);
        selectedServicesScreen.setServiceAmountValue(servicetoselect, servicelastsymbol);
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(servicetoselect), newserviceprice);
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditServicePriceOnVisualsScreenePercentage(String rowID,
                                                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String selectdamage = "Price Adjustment";
        final String servicepercentage = "Dent on Body Line";
        final String amount = "55";
        final String amountvalue = "55.000";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        inspinfoscreen.swipeScreenLeft();
        VNextVisualScreen visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.clickAddServiceButton();
        VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
        selectdamagesscreen.selectAllDamagesTab();
        VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
        visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
        visualscreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
        servicedetailsscreen.setServiceAmountValue(amount);
        Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
        servicedetailsscreen.clickServiceDetailsDoneButton();
        visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsscreen = visualscreen.saveInspectionViaMenu();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectTotalIsShownAfterEditingPercentageService(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String moneyservice = "Bug";
        final String selectdamage = "Price Adjustment";
        final String servicepercentage = "Dent on Body Line";
        final String serviceprice = "$222.00";
        final String amount = "50";
        final String amountvalue = "50.000";
        final String inspprice = "$333.00";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectService(moneyservice);
        Assert.assertEquals(inspservicesscreen.getInspectionTotalPriceValue(), serviceprice);
        inspservicesscreen.swipeScreensRight(3);
        VNextVisualScreen visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.clickAddServiceButton();
        VNextSelectDamagesScreen selectdamagesscreen = visualscreen.clickOtherServiceOption();
        selectdamagesscreen.selectAllDamagesTab();
        VNextVisualServicesScreen visualservicesscreen = selectdamagesscreen.clickCustomDamageType(selectdamage);
        visualscreen = visualservicesscreen.selectCustomService(servicepercentage);
        visualscreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
        servicedetailsscreen.setServiceAmountValue(amount);
        Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amountvalue);
        servicedetailsscreen.clickServiceDetailsDoneButton();
        visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.clickDamageCancelEditingButton();
        Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
        inspectionsscreen = visualscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCorrectPriceIsShownInTotalOnVisualsScreenAfterEditingServicePrice_Money(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String selectdamage = "Miscellaneous";
        final String amount = "999999.99";
        final String inspprice = "$999999.99";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        inspinfoscreen.swipeScreenLeft();
        VNextVisualScreen visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.clickAddServiceButton();
        visualscreen.clickDefaultDamageType(selectdamage);
        visualscreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
        servicedetailsscreen.setServiceAmountValue(amount);
        Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), amount);
        servicedetailsscreen.clickServiceDetailsDoneButton();
        visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.clickDamageCancelEditingButton();
        Assert.assertEquals(visualscreen.getInspectionTotalPriceValue(), inspprice);
        inspectionsscreen = visualscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsscreen.getFirstInspectionPrice(), inspprice);
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditServicePricePercentage(String rowID,
                                                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String moneyservice = "Dent Repair";
        final String percentageservice = "Facility Fee";
        final String quantitylastdigit = "8";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectService(moneyservice);
        inspservicesscreen.selectService(percentageservice);
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(percentageservice, quantitylastdigit);
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(percentageservice), "28.000%");
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testEditServicePriceMoney(String rowID,
                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String moneyservice = "Dent Repair";
        final String pricevalue = "3.20";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectService(moneyservice);
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(moneyservice, pricevalue);
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(moneyservice), BackOfficeUtils.getServicePriceValue(pricevalue));
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testDeleteServiceFromServicesScreen(String rowID,
                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        String inspnumber = inspinfoscreen.getNewInspectionNumber();
        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getMoneyServicesList());
        inspservicesscreen.selectServices(inspectionData.getPercentageServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();

        selectedServicesScreen.uselectService(inspectionData.getMoneyServicesList().get(0).getServiceName());
        selectedServicesScreen.uselectService(inspectionData.getPercentageServicesList().get(0).getServiceName());
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();

        inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
        inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(inspectionData.getMoneyServicesList().get(1).getServiceName()));
        Assert.assertFalse(selectedServicesScreen.isServiceSelected(inspectionData.getMoneyServicesList().get(0).getServiceName()));
        Assert.assertFalse(selectedServicesScreen.isServiceSelected(inspectionData.getPercentageServicesList().get(0).getServiceName()));
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testInspectionTotalPriceShouldChangeWhenUselectSomeOfTheSelectedServiceOnServicesScreen(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String editedPrice = "$10.00";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getMoneyServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList())
            selectedServicesScreen.setServiceAmountValue(serviceData.getServiceName(), serviceData.getServicePrice());
        Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());

        for (ServiceData serviceData : inspectionData.getMoneyServicesList())
            selectedServicesScreen.uselectService(serviceData.getServiceName());
        Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), editedPrice);
        selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testServicesArentBecameSelectedIfUserUnselectThemBeforeClickingBackButtonOnServicesScreen(String rowID,
                                                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectServices(inspectionData.getMoneyServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData serviceData : inspectionData.getMoneyServicesList())
            selectedServicesScreen.uselectService(serviceData.getServiceName());

        for (ServiceData serviceData : inspectionData.getMoneyServicesList())
            Assert.assertFalse(selectedServicesScreen.isServiceSelected(serviceData.getServiceName()));
        selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testTotalIsNotSetTo0IfUserAddsMatrixAdditionalServiceWithNegativePercentageService(String rowID,
                                                                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String matrixservice = "Hail Repair";
        final String pricematrix = "State Farm";
        final String vehiclepartname = "Hood";
        final String vehiclepartsize = "Dime";
        final String vehiclepartseverity = "Light 6 to 15";
        final String additionalservicename = "Aluminum Upcharge";
        final String additionalservicenprice = "-25";


        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());

        VNextAvailableServicesScreen inspservicesscreen = inspinfoscreen.goToInspectionServicesScreen();
        VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixservice);
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(pricematrix);
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(vehiclepartname);
        vehiclepartinfoscreen.selectVehiclePartSize(vehiclepartsize);
        vehiclepartinfoscreen.selectVehiclePartSeverity(vehiclepartseverity);
        //vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalservicename);
        VNextServiceDetailsScreen serviceDetailsScreen = vehiclepartinfoscreen.openServiceDetailsScreen(additionalservicename);
        serviceDetailsScreen.setServiceAmountValue(additionalservicenprice);
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        vehiclepartinfoscreen = new VNextVehiclePartInfoPage(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehiclepartinfoscreen.getMatrixServiceTotalPriceValue(), inspectionData.getInspectionPrice());
        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(matrixservice));
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixservice), pricematrix);

        Assert.assertEquals(selectedServicesScreen.getInspectionTotalPriceValue(), inspectionData.getInspectionPrice());
        inspectionsscreen = selectedServicesScreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyServicesAreSavedCorrectlyWhenSavingInspectionFromVisualScreen(String rowID,
                                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String[] selectdamages = {"Miscellaneous", "Dent Repair"};
        final String[] selectedservices = {"Prior Damage", "Dent Repair"};

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        inspinfoscreen.swipeScreenLeft();
        VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
        claiminfoscreen.swipeScreenLeft();
        VNextVisualScreen visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        for (int i = 0; i < selectdamages.length; i++) {
            visualscreen.clickAddServiceButton();
            visualscreen.clickDefaultDamageType(selectdamages[i]);
            if (i == 0)
                visualscreen.clickCarImage();
            else
                visualscreen.clickCarImageSecondTime();
            BaseUtils.waitABit(1000);
        }
        visualscreen.swipeScreenLeft();
        VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        for (String serviceName : selectedservices) {
            Assert.assertTrue(selectedServicesScreen.isServiceSelected(serviceName));
        }

        inspectionsscreen = inspservicesscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }
}

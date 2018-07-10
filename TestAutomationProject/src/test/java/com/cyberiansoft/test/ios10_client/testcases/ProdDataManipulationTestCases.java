package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.MatrixPartData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ManipulationDataProdInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.*;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.baseappscreens.RegularCustomersScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.*;
import com.cyberiansoft.test.ios10_client.utils.UtilConstants;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class ProdDataManipulationTestCases extends BaseTestCase {

    private String regCode;
    private RegularHomeScreen homescreen;
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/ios10_client/data/data-manipulation-testcases-data.json";
    Employee employee = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/ios10_client/data/data-manipulation-device-employee-data.json", Employee.class);

    public ProdDataManipulationTestCases() throws IOException {
    }

    @BeforeClass
    public void setUpSuite() throws Exception {
        JSONDataProvider.dataFile = DATA_FILE;
        mobilePlatform = MobilePlatform.IOS_REGULAR;
        initTestUser(employee.getEmployeeName(), employee.getEmployeePassword());
        testGetDeviceRegistrationCode(ManipulationDataProdInfo.getInstance().getBackOfficeURL(),
                ManipulationDataProdInfo.getInstance().getUserName(), ManipulationDataProdInfo.getInstance().getUserPassword());
        testRegisterationiOSDdevice();
    }

    @AfterClass()
    public void settingDown() {
    }

    public void testGetDeviceRegistrationCode(String backofficeurl,
                                              String userName, String userPassword) {

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(backofficeurl);

        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.UserLogin(userName, userPassword);

        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        CompanyWebPage companyWebPage = backofficeheader.clickCompanyLink();

        ActiveDevicesWebPage devicespage = companyWebPage.clickManageDevicesLink();

        devicespage.setSearchCriteriaByName(ManipulationDataProdInfo.getInstance().getLicenseName());
        regCode = devicespage.getFirstRegCodeInTable();

        DriverBuilder.getInstance().getDriver().quit();
    }

    public void testRegisterationiOSDdevice() throws Exception {
        AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
        DriverBuilder.getInstance().getAppiumDriver().removeApp(IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
        DriverBuilder.getInstance().getAppiumDriver().quit();
        AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
        RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen();
        LoginScreen loginscreen = selectenvscreen.selectEnvironment("Prod Environment");
        loginscreen.registeriOSDevice(regCode);
        RegularMainScreen mainscr = new RegularMainScreen();
        homescreen = mainscr.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testMyInspectionInspectionTypeMatrixInspection(String rowID,
                                                               String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
        myinspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("R & Q Autobody");
        myinspectionsscreen.selectInspectionType(inspdata.getInspectionType());
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
        vehiclescreeen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreeen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreeen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreeen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreeen.getInspectionNumber();
        RegularClaimScreen claimscreen = vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
        claimscreen.selectInsuranceCompany(inspdata.getInsuranceCompanyData().getInsuranceCompanyName());
        claimscreen.setClaim(inspdata.getInsuranceCompanyData().getClaimNumber());
        claimscreen.setPolicy(inspdata.getInsuranceCompanyData().getPolicyNumber());
        claimscreen.setDeductible(inspdata.getInsuranceCompanyData().getDeductible());
        claimscreen.setAccidentDate();

        RegularPriceMatrixScreen pricematrix = claimscreen.selectNextScreen("State Farm", RegularPriceMatrixScreen.class);
        for (MatrixPartData matrixPartData : inspdata.getMatrixServiceData().getMatrixPartsData()) {
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(matrixPartData.getMatrixPartName());
            vehiclePartScreen.setSizeAndSeverity(matrixPartData.getPartSize(), matrixPartData.getPartSeverity());
            for (ServiceData service : matrixPartData.getMatrixAdditionalServices())
                vehiclePartScreen.selectDiscaunt(service.getServiceName());
            vehiclePartScreen.clickSave();
            pricematrix = new RegularPriceMatrixScreen();
        }
        pricematrix.saveWizard();
        Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
        myinspectionsscreen.clickHomeButton();
    }

    /*@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testTeemInspectionInspectionTypeMatrixInspection(String rowID,
                                                               String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
        teaminspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("R & Q Autobody");
        myinspectionsscreen.selectInspectionType(inspdata.getInspectionType());
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
        vehiclescreeen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreeen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreeen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreeen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreeen.getInspectionNumber();
        RegularClaimScreen claimscreen = vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
        claimscreen.selectInsuranceCompany(inspdata.getInsuranceCompanyData().getInsuranceCompanyName());
        claimscreen.setClaim(inspdata.getInsuranceCompanyData().getClaimNumber());
        claimscreen.setPolicy(inspdata.getInsuranceCompanyData().getPolicyNumber());
        claimscreen.setDeductible(inspdata.getInsuranceCompanyData().getDeductible());
        claimscreen.setAccidentDate();

        RegularPriceMatrixScreen pricematrix = claimscreen.selectNextScreen("State Farm", RegularPriceMatrixScreen.class);
        for (MatrixPartData matrixPartData : inspdata.getMatrixServiceData().getMatrixPartsData()) {
            RegularVehiclePartScreen vehiclePartScreen = pricematrix.selectPriceMatrix(matrixPartData.getMatrixPartName());
            vehiclePartScreen.setSizeAndSeverity(matrixPartData.getPartSize(), matrixPartData.getPartSeverity());
            for (ServiceData service : matrixPartData.getMatrixAdditionalServices())
                vehiclePartScreen.selectDiscaunt(service.getServiceName());
            vehiclePartScreen.clickSave();
            pricematrix = new RegularPriceMatrixScreen();
        }
        pricematrix.saveWizard();
        Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
        myinspectionsscreen.clickHomeButton();
    }*/

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testMyInspectionInspectionTypePaintInspection(String rowID,
                                                               String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
        myinspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("Pitt Paint & Body");
        myinspectionsscreen.selectInspectionType(inspdata.getInspectionType());
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
        vehiclescreeen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreeen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreeen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreeen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreeen.getInspectionNumber();
        RegularClaimScreen claimscreen = vehiclescreeen.selectNextScreen(UtilConstants.CLAIM_SCREEN_CAPTION, RegularClaimScreen.class);
        claimscreen.selectInsuranceCompany(inspdata.getInsuranceCompanyData().getInsuranceCompanyName());
        claimscreen.setClaim(inspdata.getInsuranceCompanyData().getClaimNumber());
        claimscreen.setPolicy(inspdata.getInsuranceCompanyData().getPolicyNumber());
        claimscreen.setDeductible(inspdata.getInsuranceCompanyData().getDeductible());
        claimscreen.setAccidentDate();

        RegularServicesScreen servicesScreen = claimscreen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
        servicesScreen.clickToolButton();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(moneyService.getServiceName());
            selectedServiceDetailsScreen.setServicePriceValue(moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                selectedServiceDetailsScreen.setServiceQuantityValue(moneyService.getServiceQuantity());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickAddServicesButton();
        servicesScreen.saveWizard();
        Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
        myinspectionsscreen.clickHomeButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testMyInspectionInspectionTypeInteriorInspection(String rowID,
                                                              String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
        myinspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("Plaza Kia");
        myinspectionsscreen.selectInspectionType(inspdata.getInspectionType());
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
        vehiclescreeen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreeen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreeen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreeen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreeen.getInspectionNumber();

        RegularServicesScreen servicesScreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
        servicesScreen.clickToolButton();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(moneyService.getServiceName());
            selectedServiceDetailsScreen.setServicePriceValue(moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                selectedServiceDetailsScreen.setServiceQuantityValue(moneyService.getServiceQuantity());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickAddServicesButton();
        servicesScreen.saveWizard();
        Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
        myinspectionsscreen.clickHomeButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testMyInspectionInspectionTypeWheelInspection(String rowID,
                                                                 String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
        myinspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("R & Q Autobody");
        myinspectionsscreen.selectInspectionType(inspdata.getInspectionType());
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
        vehiclescreeen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreeen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreeen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreeen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreeen.getInspectionNumber();

        RegularServicesScreen servicesScreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
        servicesScreen.clickToolButton();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(moneyService.getServiceName());
            selectedServiceDetailsScreen.setServicePriceValue(moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                selectedServiceDetailsScreen.setServiceQuantityValue(moneyService.getServiceQuantity());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickAddServicesButton();
        servicesScreen.saveWizard();
        Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
        myinspectionsscreen.clickHomeButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testMyInspectionInspectionTypeInteriorDetail(String rowID,
                                                              String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
        myinspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("R & Q Autobody");
        myinspectionsscreen.selectInspectionType(inspdata.getInspectionType());
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
        vehiclescreeen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreeen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreeen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreeen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreeen.getInspectionNumber();

        RegularServicesScreen servicesScreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
        servicesScreen.clickToolButton();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(moneyService.getServiceName());
            selectedServiceDetailsScreen.setServicePriceValue(moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                selectedServiceDetailsScreen.setServiceQuantityValue(moneyService.getServiceQuantity());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickAddServicesButton();
        servicesScreen.saveWizard();
        Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
        myinspectionsscreen.clickHomeButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testMyInspectionInspectionTypeExteriorDetail(String rowID,
                                                             String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularMyInspectionsScreen myinspectionsscreen = homescreen.clickMyInspectionsButton();
        myinspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("Torrington Detail");
        myinspectionsscreen.selectInspectionType(inspdata.getInspectionType());
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreeen = visualInteriorScreen.selectNextScreen(RegularVehicleScreen.getVehicleScreenCaption(), RegularVehicleScreen.class);
        vehiclescreeen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreeen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreeen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreeen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreeen.getInspectionNumber();

        RegularServicesScreen servicesScreen = vehiclescreeen.selectNextScreen(RegularServicesScreen.getServicesScreenCaption(), RegularServicesScreen.class);
        servicesScreen.clickToolButton();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularSelectedServiceDetailsScreen selectedServiceDetailsScreen = servicesScreen.openCustomServiceDetails(moneyService.getServiceName());
            selectedServiceDetailsScreen.setServicePriceValue(moneyService.getServicePrice());
            if (moneyService.getServiceQuantity() != null)
                selectedServiceDetailsScreen.setServiceQuantityValue(moneyService.getServiceQuantity());
            selectedServiceDetailsScreen.saveSelectedServiceDetails();
        }
        servicesScreen.clickAddServicesButton();
        servicesScreen.saveWizard();
        Assert.assertTrue(myinspectionsscreen.checkInspectionExists(inspNumber));
        myinspectionsscreen.clickHomeButton();
    }
}

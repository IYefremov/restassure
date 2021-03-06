package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.ActiveDevicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.CompanyWebPage;
import com.cyberiansoft.test.core.IOSRegularDeviceInfo;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.AppiumInicializator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.config.ManipulationDataProdInfo;
import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.LoginScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularHomeScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularMainScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularSelectEnvironmentScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.RegularVehiclePartScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInspectionsScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularClaimScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularPriceMatrixScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularServicesScreen;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularVehicleScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.ProdInspectionsTypes;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ProdDataManipulationTestCases extends BaseTestCase {

    private String regCode;
    private RegularHomeScreen homescreen;
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/ios10_client/data/data-manipulation-testcases-data.json";
    Employee employee = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/ios10_client/data/data-manipulation-device-employee-data.json"), Employee.class);

    public ProdDataManipulationTestCases() throws IOException {
    }

    @BeforeClass
    public void setUpSuite() {
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
        loginpage.userLogin(userName, userPassword);

        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        backofficeheader.clickCompanyLink();
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        companyWebPage.clickManageDevicesLink();
        ActiveDevicesWebPage devicespage = new ActiveDevicesWebPage(webdriver);

        devicespage.setSearchCriteriaByName(ManipulationDataProdInfo.getInstance().getLicenseName());
        regCode = devicespage.getFirstRegCodeInTable();

        DriverBuilder.getInstance().getDriver().quit();
    }

    public void testRegisterationiOSDdevice() {
        AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
        DriverBuilder.getInstance().getAppiumDriver().removeApp(IOSRegularDeviceInfo.getInstance().getDeviceBundleId());
        DriverBuilder.getInstance().getAppiumDriver().quit();
        AppiumInicializator.getInstance().initAppium(MobilePlatform.IOS_REGULAR);
        RegularSelectEnvironmentScreen selectenvscreen = new RegularSelectEnvironmentScreen();
        LoginScreen loginscreen = selectenvscreen.selectEnvironment("Prod Environment");
        loginscreen.registeriOSDevice(regCode);
        RegularMainScreen mainscr = new RegularMainScreen();
        mainscr.userLogin(employee.getEmployeeName(), employee.getEmployeePassword());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMyInspectionInspectionTypeMatrixInspection(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("R & Q Autobody");
        
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(wholesailCustomer, ProdInspectionsTypes.MATRIX_INSPECTION);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
        vehiclescreen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreen.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimscreen = new RegularClaimScreen();
        claimscreen.selectInsuranceCompany(inspdata.getInsuranceCompanyData().getInsuranceCompanyName());
        claimscreen.setClaim(inspdata.getInsuranceCompanyData().getClaimNumber());
        claimscreen.setPolicy(inspdata.getInsuranceCompanyData().getPolicyNumber());
        claimscreen.setDeductible(inspdata.getInsuranceCompanyData().getDeductible());
        claimscreen.setAccidentDate();

        RegularNavigationSteps.navigateToScreen("State Farm");
        RegularPriceMatrixScreen pricematrix = new RegularPriceMatrixScreen();
        for (VehiclePartData vehiclePartData : inspdata.getMatrixServiceData().getVehiclePartsData()) {
            pricematrix.selectPriceMatrix(vehiclePartData.getVehiclePartName());
            RegularVehiclePartScreen vehiclePartScreen = new RegularVehiclePartScreen();
                    vehiclePartScreen.setSizeAndSeverity(vehiclePartData.getVehiclePartSize(), vehiclePartData.getVehiclePartSeverity());
            for (ServiceData service : vehiclePartData.getVehiclePartAdditionalServices())
                vehiclePartScreen.selectDiscaunt(service.getServiceName());
            vehiclePartScreen.clickSave();
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    /*@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testTeemInspectionInspectionTypeMatrixInspection(String rowID,
                                                               String description, JSONObject testData) throws Exception {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        RegularTeamInspectionsScreen teaminspectionsscreen = homescreen.clickTeamInspectionsButton();
        teaminspectionsscreen.clickAddInspectionButton();
        RegularCustomersScreen customersScreen = new RegularCustomersScreen();
        customersScreen.selectCustomer("R & Q Autobody");
        myInspectionsScreen.selectInspectionType(ProdInspectionsTypes.MATRIX_INSPECTION);
        RegularVisualInteriorScreen visualInteriorScreen = new RegularVisualInteriorScreen();
        RegularVehicleScreen vehiclescreen = visualInteriorScreen.selectNextScreen(WizardScreenTypes.VEHICLE_INFO);
        vehiclescreen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreen.getInspectionNumber();
        RegularClaimScreen claimscreen = vehiclescreen.selectNextScreen(WizardScreenTypes.CLAIM);
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
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }*/

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMyInspectionInspectionTypePaintInspection(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Pitt Paint & Body");

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(wholesailCustomer, ProdInspectionsTypes.PAINT_INSPECTION);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
        vehiclescreen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreen.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreen claimscreen = new RegularClaimScreen();
        claimscreen.selectInsuranceCompany(inspdata.getInsuranceCompanyData().getInsuranceCompanyName());
        claimscreen.setClaim(inspdata.getInsuranceCompanyData().getClaimNumber());
        claimscreen.setPolicy(inspdata.getInsuranceCompanyData().getPolicyNumber());
        claimscreen.setDeductible(inspdata.getInsuranceCompanyData().getDeductible());
        claimscreen.setAccidentDate();

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(moneyService);
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMyInspectionInspectionTypeInteriorInspection(String rowID,
                                                                 String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Plaza Kia");

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(wholesailCustomer, ProdInspectionsTypes.INTERIOR_INSPECTION);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
        vehiclescreen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreen.getInspectionNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(moneyService);
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMyInspectionInspectionTypeWheelInspection(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("R & Q Autobody");

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(wholesailCustomer, ProdInspectionsTypes.WHEEL_INSPECTION);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
        vehiclescreen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreen.getInspectionNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(moneyService);
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMyInspectionInspectionTypeInteriorDetail(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("R & Q Autobody");

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(wholesailCustomer, ProdInspectionsTypes.INTERIOR_DETAIL);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
        vehiclescreen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreen.getInspectionNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(moneyService);
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testMyInspectionInspectionTypeExteriorDetail(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Torrington Detail");

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsSteps.startCreatingInspection(wholesailCustomer, ProdInspectionsTypes.EXTERIOR_DETAIL);
        RegularNavigationSteps.navigateToVehicleInfoScreen();
        RegularVehicleScreen vehiclescreen = new RegularVehicleScreen();
        vehiclescreen.setVIN(inspdata.getVehicleInfo().getVINNumber());
        vehiclescreen.setStock(inspdata.getVehicleInfo().getStockNumber());
        vehiclescreen.setRO(inspdata.getVehicleInfo().getRoNumber());
        vehiclescreen.setMileage(inspdata.getVehicleInfo().getMileage());
        final String inspNumber = vehiclescreen.getInspectionNumber();

        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreen servicesScreen = new RegularServicesScreen();
        for (ServiceData moneyService : inspdata.getMoneyServicesList()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(moneyService);
        }
        RegularInspectionsSteps.saveInspection();
        RegularMyInspectionsScreen myInspectionsScreen = new RegularMyInspectionsScreen();
        Assert.assertTrue(myInspectionsScreen.checkInspectionExists(inspNumber));
        RegularNavigationSteps.navigateBackScreen();
    }
}

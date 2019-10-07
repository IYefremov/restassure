package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.verifications.Inspections.VNextBOInspectionsPageVerifications;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VNextInspectionSearchTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    private String inspnumber = "";
    private String archivedinspnumber = "";
    private final String VIN = "3N1AB7AP3HY327077";
    private final String stock = "123";
    private final String po = "987";
    private final String defaultTimeFrameValue = "Timeframe: Last 90 Days";
    VNextBOInspectionsPageSteps vNextBOInspectionsPageSteps;
    private VNextBOInspectionsAdvancedSearchSteps vNextBOInspectionsAdvancedSearchSteps;

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithPopulatedVehicleInfoForCurrentDay(String rowID,
                                                                                String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

        inspnumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
        claimInfoScreen.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
        claimInfoScreen.setDeductibleValue(inspectionData.getInsuranceCompanyData().getDeductible());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(matrixServiceData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspnumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        inspectionsScreen.clickBackButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageSteps.advancedSearchInspectionByCustomer(testcustomer.getFullName());

        insppage.selectInspectionInTheList(inspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspectionData.getInspectionPrice());
        webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay(String rowID,
                                                                          String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

        archivedinspnumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
        claimInfoScreen.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
        claimInfoScreen.setDeductibleValue(inspectionData.getInsuranceCompanyData().getDeductible());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(matrixServiceData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

        inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(archivedinspnumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        InspectionSteps.archiveInspection(archivedinspnumber);

        inspectionsScreen.clickBackButton();
    }

    @Test(testName = "Test Case 64864:R360: verify searching inspection by All Status on BO",
            description = "Verify searching inspection by All Status on BO",
            dependsOnMethods = {"testCreateInspectionWithPopulatedVehicleInfoForCurrentDay", "testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay"})
    public void testVerifySearchingInspectionByAllStatusOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageSteps.advancedSearchInspectionByStatusAndInspectionNumber(archivedinspnumber, "All");

        insppage.selectInspectionInTheList(archivedinspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64871:R360: verify searching inspection by Archived Status on BO",
            description = "Verify searching inspection by Archived Status on BO",
            dependsOnMethods = {"testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay"})
    public void testVerifySearchingInspectionByArchivedStatusOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageSteps.advancedSearchInspectionByStatus("Archived");

        insppage.selectInspectionInTheList(archivedinspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64872:R360: verify searching inspection by Approved Status on BO",
            description = "Verify searching inspection by Approved Status on BO",
            dependsOnMethods = {"testCreateInspectionWithPopulatedVehicleInfoForCurrentDay"})
    public void testVerifySearchingInspectionByApprovedStatusOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageSteps.advancedSearchInspectionByStatus("Approved");

        insppage.selectInspectionInTheList(inspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64944:R360: verify searching inspection by Stock# on BO",
            description = "Verify searching inspection by Stock# on BO",
            dependsOnMethods = {"testCreateInspectionWithPopulatedVehicleInfoForCurrentDay"})
    public void testVerifySearchingInspectionByStockNumberOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageSteps.advancedSearchInspectionByStockNumber(stock);

        insppage.selectInspectionInTheList(inspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64945:R360: verify searching inspection by PO# on BO",
            description = "Verify searching inspection by PO# on BO",
            dependsOnMethods = {"testCreateInspectionWithPopulatedVehicleInfoForCurrentDay"})
    public void testVerifySearchingInspectionByPONumberOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageSteps.advancedSearchInspectionByPONumber(po);

        insppage.selectInspectionInTheList(inspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64946:R360: verify searching inspection by VIN on BO",
            description = "Verify searching inspection by VIN on BO",
            dependsOnMethods = {"testCreateInspectionWithPopulatedVehicleInfoForCurrentDay"})
    public void testVerifySearchingInspectionByVINOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsPageSteps.advancedSearchInspectionByVIN(VIN);

        insppage.selectInspectionInTheList(inspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64947:R360: verify posibility to Save Inspection Search filter on BO",
            description = "Verify posibility to Save Inspection Search filter on BO",
            dependsOnMethods = {"testCreateInspectionWithPopulatedVehicleInfoForCurrentDay"})
    public void testVerifyPosibilityToSaveInspectionSearchFilterOnBO() {

        final String inspTotalPrice = "$ 267.81";
        final String filterName = "test12345";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webdriver);
        if (new VNextBOInspectionsPageVerifications(webdriver).isSavedAdvancedSearchFilterExists(filterName))
            vNextBOInspectionsPageSteps.deleteSavedAdvancedSearchFilter(filterName);


        insppage.openAdvancedSearchForm();
        VNextBOInspectionAdvancedSearchForm advancedserchdialog = new VNextBOInspectionAdvancedSearchForm(webdriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#", inspnumber);
        vNextBOInspectionsAdvancedSearchSteps.setAdvancedSearchFilterNameAndSave(filterName);
        insppage.selectInspectionInTheList(inspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64949:R360: verify posibility to edit saved Inspection Search filter on BO",
            description = "Verify posibility to edit saved Inspection Search filter on BO",
            dependsOnMethods = {"testVerifyPosibilityToSaveInspectionSearchFilterOnBO"})
    public void testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO() {

        final String inspTotalPrice = "$ 267.81";
        final String filterName = "test12345";
        final String filterNameEdited = "test12345edited";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        if (new VNextBOInspectionsPageVerifications(webdriver).isSavedAdvancedSearchFilterExists(filterNameEdited))
            vNextBOInspectionsPageSteps.deleteSavedAdvancedSearchFilter(filterNameEdited);

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionAdvancedSearchForm advancedserchdialog = insppage.openSavedAdvancedSearchFilter(filterName);
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webdriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#","");
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Search Name", filterNameEdited);
        vNextBOInspectionsAdvancedSearchSteps.saveAdvancedSearchFilter();

        insppage.selectInspectionInTheList(inspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64956:R360: verify posibility to Clear saved Inspection Search filter on BO",
            description = "Verify posibility to Clear saved Inspection Search filter on BO",
            dependsOnMethods = {"testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO"})
    public void testVerifyPosibilityToClearSavedInspectionSearchFilterOnBO() {

        final String filterNameEdited = "test12345edited";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionAdvancedSearchForm advancedserchdialog = insppage.openSavedAdvancedSearchFilter(filterNameEdited);
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webdriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#","2");
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Search Name", filterNameEdited + "Clear");
        vNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Inspection#"), "");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("VIN"), "");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Search Name"), filterNameEdited);

        webdriver.quit();
    }

    @Test(testName = "Test Case 64957:R360: verify posibility to Delete saved Inspection Search filter on BO",
            description = "Verify posibility to Delete saved Inspection Search filter on BO",
            dependsOnMethods = {"testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO"})
    public void testVerifyPosibilityToDeleteSavedInspectionSearchFilterOnBO() {

        final String filterNameEdited = "test12345edited";

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webdriver);

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        vNextBOInspectionsPageSteps.deleteSavedAdvancedSearchFilter(filterNameEdited);
        Assert.assertEquals(vNextBOInspectionsPageSteps.getSearchFieldValue(), "");
        Assert.assertEquals(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue(), defaultTimeFrameValue);
        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertFalse(new VNextBOInspectionsPageVerifications(webdriver).isSavedAdvancedSearchFilterExists(filterNameEdited));
        webdriver.quit();
    }

    @Test(testName = "Test Case 64958:R360: verify posibility to reset Inspection Search filter to default on BO",
            description = "Verify posibility to reset Inspection Search filter to default on BO",
            dependsOnMethods = {"testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO"})
    public void testVerifyPosibilityToResetInspectionSearchFilterOnBO() {
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        vNextBOInspectionsPageSteps = new VNextBOInspectionsPageSteps(webdriver);

        vNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        insppage.openAdvancedSearchForm();
        vNextBOInspectionsAdvancedSearchSteps = new VNextBOInspectionsAdvancedSearchSteps(webdriver);
        vNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        vNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        insppage.selectInspectionInTheList(inspnumber);
        vNextBOInspectionsPageSteps.clickClearFilterIcon();
        Assert.assertEquals(vNextBOInspectionsPageSteps.getSearchFieldValue(), "");
        Assert.assertEquals(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue(), defaultTimeFrameValue);
        vNextBOInspectionsPageSteps.searchInspectionByText(inspnumber);
        insppage.selectInspectionInTheList(inspnumber);
        vNextBOInspectionsPageSteps.clickClearFilterIcon();
        Assert.assertEquals(vNextBOInspectionsPageSteps.getSearchFieldValue(), "");
        Assert.assertEquals(vNextBOInspectionsPageSteps.getCustomSearchInfoTextValue(), defaultTimeFrameValue);


        webdriver.quit();
    }
}

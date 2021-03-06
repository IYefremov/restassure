package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.commonobjects.VNextBOSearchPanelSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.validations.inspections.VNextBOInspectionsPageValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VNextInspectionSearchTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    private String inspectionNumber = "";
    private String archivedinspnumber = "";
    private final String VIN = "3N1AB7AP3HY327077";
    private final String stock = "123";
    private final String po = "987";
    private final String defaultTimeFrameValue = "Timeframe: Last 90 Days";

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWithPopulatedVehicleInfoForCurrentDay(String rowID,
                                                                                String description, JSONObject testData){

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        ClaimInfoSteps.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        ClaimInfoSteps.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
        ClaimInfoSteps.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
        ClaimInfoSteps.setDeductibleValue(inspectionData.getInsuranceCompanyData().getDeductible());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
        VNextVehiclePartInfoScreen vehiclePartInfoScreen = new VNextVehiclePartInfoScreen();
        vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(matrixServiceData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

        inspectionNumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        ScreenNavigationSteps.pressBackButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByCustomer(testcustomer.getFullName());

        insppage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspectionData.getInspectionPrice());
        webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateArchivedInspectionWithFullPopulatedVehicleInfoForCurrentDay(String rowID,
                                                                          String description, JSONObject testData)  {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        ClaimInfoSteps.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        ClaimInfoSteps.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
        ClaimInfoSteps.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
        ClaimInfoSteps.setDeductibleValue(inspectionData.getInsuranceCompanyData().getDeductible());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
        VNextVehiclePartInfoScreen vehiclePartInfoScreen = new VNextVehiclePartInfoScreen();
        vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(matrixServiceData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

        archivedinspnumber = InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionTotalPrice(archivedinspnumber, PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        InspectionSteps.archiveInspection(archivedinspnumber);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(testName = "Test Case 64864:R360: verify searching inspection by All Status on BO",
            description = "Verify searching inspection by All Status on BO")
    public void testVerifySearchingInspectionByAllStatusOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStatusAndInspectionNumber(archivedinspnumber, "All");

        insppage.selectInspectionInTheList(archivedinspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64871:R360: verify searching inspection by Archived Status on BO",
            description = "Verify searching inspection by Archived Status on BO")
    public void testVerifySearchingInspectionByArchivedStatusOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStatus("Archived");

        insppage.selectInspectionInTheList(archivedinspnumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64872:R360: verify searching inspection by Approved Status on BO",
            description = "Verify searching inspection by Approved Status on BO")
    public void testVerifySearchingInspectionByApprovedStatusOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStatus("Approved");

        insppage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64944:R360: verify searching inspection by Stock# on BO",
            description = "Verify searching inspection by Stock# on BO")
    public void testVerifySearchingInspectionByStockNumberOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByStockNumber(stock);

        insppage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64945:R360: verify searching inspection by PO# on BO",
            description = "Verify searching inspection by PO# on BO")
    public void testVerifySearchingInspectionByPONumberOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByPONumber(po);

        insppage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64946:R360: verify searching inspection by VIN on BO",
            description = "Verify searching inspection by VIN on BO")
    public void testVerifySearchingInspectionByVINOnBO() {

        final String inspTotalPrice = "$ 267.81";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
        VNextBOInspectionsPageSteps.advancedSearchInspectionByVIN(VIN);

        insppage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64947:R360: verify posibility to Save Inspection Search filter on BO",
            description = "Verify posibility to Save Inspection Search filter on BO")
    public void testVerifyPosibilityToSaveInspectionSearchFilterOnBO() {

        final String inspTotalPrice = "$ 267.81";
        final String filterName = "test12345";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        if (VNextBOInspectionsPageValidations.verifySavedAdvancedSearchFilterExists(filterName))
            VNextBOInspectionsPageSteps.deleteSavedAdvancedSearchFilter(filterName);


        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionAdvancedSearchForm advancedserchdialog = new VNextBOInspectionAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#", inspectionNumber);
        VNextBOInspectionsAdvancedSearchSteps.setAdvancedSearchFilterNameAndSave(filterName);
        insppage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64949:R360: verify posibility to edit saved Inspection Search filter on BO",
            description = "Verify posibility to edit saved Inspection Search filter on BO")
    public void testVerifyPosibilityToEditSavedInspectionSearchFilterOnBO() {

        final String inspTotalPrice = "$ 267.81";
        final String filterName = "test12345";
        final String filterNameEdited = "test12345edited";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        if (VNextBOInspectionsPageValidations.verifySavedAdvancedSearchFilterExists(filterNameEdited))
            VNextBOInspectionsPageSteps.deleteSavedAdvancedSearchFilter(filterNameEdited);

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#","");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Search Name", filterNameEdited);
        VNextBOInspectionsAdvancedSearchSteps.saveAdvancedSearchFilter();

        insppage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(insppage.getSelectedInspectionTotalAmountValue(), inspTotalPrice);
        webdriver.quit();
    }

    @Test(testName = "Test Case 64956:R360: verify posibility to Clear saved Inspection Search filter on BO",
            description = "Verify posibility to Clear saved Inspection Search filter on BO")
    public void testVerifyPosibilityToClearSavedInspectionSearchFilterOnBO() {

        final String filterNameEdited = "test12345edited";
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Inspection#","2");
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("Search Name", filterNameEdited + "Clear");
        VNextBOInspectionsAdvancedSearchSteps.clickClearButton();
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Inspection#"), "");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("VIN"), "");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Search Name"), filterNameEdited);

        webdriver.quit();
    }

    @Test(testName = "Test Case 64957:R360: verify posibility to Delete saved Inspection Search filter on BO",
            description = "Verify posibility to Delete saved Inspection Search filter on BO")
    public void testVerifyPosibilityToDeleteSavedInspectionSearchFilterOnBO() {

        final String filterNameEdited = "test12345edited";

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        leftmenu.selectInspectionsMenu();

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.deleteSavedAdvancedSearchFilter(filterNameEdited);
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSearchFieldValue(), "");
        Assert.assertEquals(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue(), defaultTimeFrameValue);
        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        Assert.assertFalse(VNextBOInspectionsPageValidations.verifySavedAdvancedSearchFilterExists(filterNameEdited));
        webdriver.quit();
    }

    @Test(testName = "Test Case 64958:R360: verify posibility to reset Inspection Search filter to default on BO",
            description = "Verify posibility to reset Inspection Search filter to default on BO")
    public void testVerifyPosibilityToResetInspectionSearchFilterOnBO() {
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        webdriver.get(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();

        VNextBOInspectionsPageSteps.clickExpandAdvancedSearchPanel();
        VNextBOInspectionsPageSteps.openAdvancedSearchForm();
        VNextBOInspectionsAdvancedSearchSteps.setAdvSearchTextField("VIN", VIN);
        VNextBOInspectionsAdvancedSearchSteps.clickSearchButton();
        insppage.selectInspectionInTheList(inspectionNumber);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSearchFieldValue(), "");
        Assert.assertEquals(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue(), defaultTimeFrameValue);
        VNextBOSearchPanelSteps.searchByTextWithSpinnerLoading(inspectionNumber);
        insppage.selectInspectionInTheList(inspectionNumber);
        VNextBOSearchPanelSteps.clearSearchFilterWithSpinnerLoading();
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSearchFieldValue(), "");
        Assert.assertEquals(VNextBOInspectionsPageSteps.getCustomSearchInfoTextValue(), defaultTimeFrameValue);

        webdriver.quit();
    }
}


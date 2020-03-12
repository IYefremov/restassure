package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionInfoWebPage;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class VNextCreateInspectionOnTheClientTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {
    
    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCheckApprovedAmountIsCalculatedCorrectlyForApprovedInspection(String rowID,
                                                                                      String description, JSONObject testData) throws Exception {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.selectService(inspectionData.getPercentageServiceData());
        AvailableServicesScreenSteps.selectService(inspectionData.getMoneyServiceData());
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(matrixServiceData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen.clickVehiclePartsSaveButton();

        SelectedServicesScreenSteps.changeSelectedServicePrice(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        NadaEMailService nadaEMailService = new NadaEMailService();
        emailScreen.sentToEmailAddress(nadaEMailService.getEmailId());

        emailScreen.sendEmail();

        final String inspectionReportFileName = inspectionNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionNumber, inspectionReportFileName);
        Assert.assertTrue(nadaEMailService.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionNumber +
                " in mail box " + nadaEMailService.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nadaEMailService.deleteMessageWithSubject(inspectionNumber);


        File pdfDoc = new File(inspectionReportFileName);
        String pdfText = PDFReader.getPDFText(pdfDoc);
        Assert.assertTrue(pdfText.contains(inspectionData.getVehicleInfo().getVINNumber()));
        Assert.assertTrue(pdfText.contains(inspectionData.getMoneyServiceData().getServiceName()));
        Assert.assertTrue(pdfText.contains(inspectionData.getPercentageServiceData().getServiceName()));
        Assert.assertTrue(pdfText.contains(inspectionData.getInspectionPrice()));

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(), VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(inspectionsWebPage.getSelectedInspectionTotalAmountValue(), inspectionData.getInspectionPrice());
        String mainWindowHandle = webdriver.getWindowHandle();

        VNextBOInspectionInfoWebPage inspectionInfoWebPage = inspectionsWebPage.clickSelectedInspectionPrintIcon();
        Utils.closeNewTab(mainWindowHandle);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWhichContainsBreakageServiceWithBigQuantity(String rowID,
                                                                                  String description, JSONObject testData) {
        
        final String damageName = "Hail Damage";
        
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        visualScreen.clickAddServiceButton();
        visualScreen.clickDefaultDamageType(inspectionData.getMoneyServiceData().getServiceName());
        visualScreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen servicedetailsscreen = visualScreen.clickCarImageMarker();
        servicedetailsscreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServicePrice());
        Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), inspectionData.getMoneyServiceData().getServicePrice());
        servicedetailsscreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceQuantity());
        servicedetailsscreen.clickServiceDetailsDoneButton();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixServiceData.getVehiclePartData().getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(matrixServiceData.getVehiclePartData().getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(matrixServiceData.getVehiclePartData().getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(matrixServiceData.getVehiclePartData().getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        SelectedServicesScreenSteps.switchToSelectedService();
        ListServicesValidations.verifyServiceSelected(matrixServiceData.getMatrixServiceName(), true);

        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(), VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionCustomerName(), testcustomer); //todo  iconvertible types
        Assert.assertTrue(inspectionsWebPage.isServicePresentForSelectedInspection(inspectionData.getMoneyServiceData().getServiceName()));
        Assert.assertTrue(inspectionsWebPage.isServicePresentForSelectedInspection(matrixServiceData.getMatrixServiceName()));
        Assert.assertTrue(inspectionsWebPage.isImageLegendContainsBreakageIcon(damageName));
        
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWhithFullPopulatedCustomerInfo(String rowID,
                                                                                String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWhithCustomerWithFirstNameOnly(String rowID,
                                                                   String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        CustomersScreenSteps.clickAddCustomerButton();
        VNextNewCustomerScreen newCustomerScreen = new VNextNewCustomerScreen();
        newCustomerScreen.createNewCustomer(inspectionData.getInspectionRetailCustomer());

        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(), VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInspectionsWebPage inspectionsWebPage = leftMenu.selectInspectionsMenu();
        inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionCustomerName(), inspectionData.getInspectionRetailCustomer().getFirstName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateAndEmailInspectionWithTtwoMatrixPanel(String rowID,
                                                                   String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, inspectionData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            List<String> additionalServices = vehiclePartInfoScreen.getListOfAdditionalServices();
            for (String serviceName : additionalServices)
                vehiclePartInfoScreen.selectVehiclePartAdditionalService(serviceName);
            vehiclePartInfoScreen.clickSaveVehiclePartInfo();

        }
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        ListServicesValidations.verifyServiceSelected(matrixServiceData.getMatrixServiceName(), true);

        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        NadaEMailService nadaEMailService = new NadaEMailService();
        emailScreen.sentToEmailAddress(nadaEMailService.getEmailId());

        ScreenNavigationSteps.pressBackButton();
    }
}

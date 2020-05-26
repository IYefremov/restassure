package com.cyberiansoft.test.vnext.testcases.r360free.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceInfoSteps;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import com.cyberiansoft.test.vnextbo.interactions.invoices.VNextBOInvoicesPageInteractions;
import com.cyberiansoft.test.vnextbo.interactions.leftmenupanel.VNextBOLeftMenuInteractions;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
import com.cyberiansoft.test.vnextbo.validations.invoices.VNextBOInvoicesPageValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class VNextInvoicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    @BeforeClass(description = "R360 Invoices Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInvoicesTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWithMatrixService(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        HomeScreenSteps.openCreateMyWorkOrder();
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        final WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        // VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
       // vehicleVINHistoryScreen.clickBackButton();
        VehicleInfoScreenInteractions.selectColor(workOrderData.getVehicleInfoData().getVehicleColor());

        VehicleInfoScreenValidations.validateVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(workOrderData.getMoneyServiceData().getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(workOrderData.getMoneyServiceData().getServiceQuantity());
        ServiceDetailsScreenSteps.saveServiceDetails();
        AvailableServicesScreenSteps.selectService(workOrderData.getPercentageServiceData());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        VNextVehiclePartInfoScreen vehiclePartInfoScreen = new VNextVehiclePartInfoScreen();
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickScreenBackButton();
        vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        SelectedServicesScreenSteps.openServiceDetails(matrixServiceData.getMatrixServiceName());
        Assert.assertEquals(vehiclePartsScreen.getVehiclePartsScrenPriceValue(),matrixServiceData.getVehiclePartData().getVehiclePartPrice());
        vehiclePartsScreen.clickScreenBackButton();
        SelectedServicesScreenSteps.changeSelectedServicePrice(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServiceQuantity());


        VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workOrdersScreen.getWorkOrderPriceValue(woNumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        WorkOrderSteps.createInvoiceFromWorkOrder(woNumber);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoice();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWithTextNote(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String woNote = "Only text Note";
        final String noteText = "Invoice text note";

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
        //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        //vehicleVINHistoryScreen.clickBackButton();
        VNextNotesScreen notesScreen = vehicleInfoScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(woNote);
        ScreenNavigationSteps.pressBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(workOrderNumber), testCaseData.getWorkOrderData().getWorkOrderPrice());
        WorkOrderSteps.createInvoiceFromWorkOrder(workOrderNumber);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        InvoiceSteps.addTextNoteToInvoice(noteText);
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), testCaseData.getWorkOrderData().getWorkOrderPrice());
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(60 * 1000);

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(deviceuser, devicepsw);
        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOInvoicesPageInteractions.selectInvoiceInTheList(invoiceNumber);
        VNextBOInvoicesPageValidations.verifySelectedInvoiceNotes(noteText);
        webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWhichContainsPriceServicesWithDecimalQuantity(String rowID,
                                                                               String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        HomeScreenSteps.openWorkOrders();
        WorkOrderSteps.clickAddWorkOrderButton();
        CustomersScreenSteps.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        //vehicleVINHistoryScreen.clickBackButton();

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.changeSelectedServicePrice(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServiceQuantity());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

        InvoiceData invoiceData = testCaseData.getInvoiceData();
        InvoiceInfoSteps.setInvoicePONumber(invoiceData.getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INVOICE);
        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(nada.getEmailId());
        EmailSteps.sendEmail(nada.getEmailId());
        ScreenNavigationSteps.pressBackButton();

        final String inspectionReportFilenName = invoiceNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, inspectionReportFilenName);
        nada.downloadMessageAttachment(searchParametersBuilder);
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfdoc = new File(inspectionReportFilenName);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains(workOrderData.getMoneyServiceData().getServiceName() +
                " " + workOrderData.getWorkOrderPrice()));
        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginSteps.userLogin(deviceuser, devicepsw);
        VNextBOLeftMenuInteractions.selectInvoicesMenu();
        VNextBOInvoicesPageInteractions.selectInvoiceInTheList(invoiceNumber);
        VNextBOInvoicesPageValidations.verifySelectedInvoiceCustomerName(testcustomer.getFullName());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWithSetPO(String rowID,
                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String invoiceNote = "Only text Note";

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        //vehicleVINHistoryScreen.clickBackButton();
        VehicleInfoScreenValidations.validateVehicleInfo(workOrderData.getVehicleInfoData());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        AvailableServicesScreenSteps.openServiceDetails(workOrderData.getMoneyServiceData().getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(workOrderData.getMoneyServiceData().getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(workOrderData.getMoneyServiceData().getServiceQuantity());
        ServiceDetailsScreenSteps.saveServiceDetails();
        AvailableServicesScreenSteps.selectService(workOrderData.getPercentageServiceData());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        VNextVehiclePartInfoScreen vehiclePartInfoScreen = new VNextVehiclePartInfoScreen();
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickScreenBackButton();
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        SelectedServicesScreenSteps.openServiceDetails(matrixServiceData.getMatrixServiceName());
        Assert.assertEquals(vehiclePartsScreen.getVehiclePartsScrenPriceValue(), vehiclePartData.getVehiclePartPrice());
        vehiclePartsScreen.clickScreenBackButton();
        SelectedServicesScreenSteps.changeSelectedServicePrice(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
        SelectedServicesScreenSteps.changeSelectedServiceQuantity(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServiceQuantity());

        VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workOrdersScreen.getWorkOrderPriceValue(workOrderNumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        WorkOrderSteps.createInvoiceFromWorkOrder(workOrderNumber);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        InvoiceSteps.addTextNoteToInvoice(invoiceNote);
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), woPrice);
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateAndEmailInvoiceWithTwoMatrixPanel(String rowID,
                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        //vehicleVINHistoryScreen.clickBackButton();

        VehicleInfoScreenValidations.validateVehicleInfo(workOrderData.getVehicleInfoData());

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen();
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen();
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            VNextVehiclePartInfoScreen vehiclePartInfoScreen = new VNextVehiclePartInfoScreen();
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            vehiclePartInfoScreen.getServicesList().forEach(serviceListItem -> vehiclePartInfoScreen.selectVehiclePartAdditionalService(serviceListItem.getServiceName()));
            vehiclePartInfoScreen.clickScreenBackButton();
            vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        }
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        SelectedServicesScreenSteps.switchToSelectedService();
        SelectedServicesScreenSteps.openServiceDetails(matrixServiceData.getMatrixServiceName());
        Assert.assertEquals(vehiclePartsScreen.getVehiclePartsScrenPriceValue(), workOrderData.getWorkOrderPrice());
        vehiclePartsScreen.clickScreenBackButton();
        //Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EMAIL_INVOICE);
        VNextEmailScreen emailScreen = new VNextEmailScreen();
        if (!emailScreen.getToEmailFieldValue().equals(VNextFreeRegistrationInfo.getInstance().getR360UserMail()))
            emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());

        emailScreen.sendEmail();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOWizard(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        //vehicleVINHistoryScreen.clickBackButton();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        AvailableServicesScreenSteps.selectService(workOrderData.getPercentageServiceData());

        WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber));
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOMenu(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        //VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        //vehicleVINHistoryScreen.clickBackButton();
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        AvailableServicesScreenSteps.selectService(workOrderData.getPercentageServiceData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
        WizardScreenSteps.saveAction();
        WorkOrderSteps.createInvoiceFromWorkOrder(workOrderNumber);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber));
        ScreenNavigationSteps.pressBackButton();
    }

}

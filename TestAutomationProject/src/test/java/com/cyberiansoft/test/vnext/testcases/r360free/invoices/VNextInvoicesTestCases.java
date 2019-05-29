package com.cyberiansoft.test.vnext.testcases.r360free.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.EmailUtils;
import com.cyberiansoft.test.email.emaildata.EmailFolder;
import com.cyberiansoft.test.email.emaildata.EmailHost;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInvoicesWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

public class VNextInvoicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    @BeforeClass(description="R360 Invoices Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInvoicesTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceWithMatrixService(String rowID,
                                                                     String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String quickNote = "Alum Deck";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        final WorkOrderData workOrderData = invoiceData.getWorkOrderData();
        vehicleInfoScreen.populateVehicleInfoDataOnCreateWOWizard(workOrderData.getVinNumber(), workOrderData.getVehicleInfoData().getVehicleColor());

        Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleInfoScreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleInfoScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceName());
        availableServicesScreen.selectService(workOrderData.getPercentageServiceName());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServiceQuantity());

        final String woNumber = selectedServicesScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workOrdersScreen.getWorkOrderPriceValue(woNumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(woNumber);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceInfoScreen.addQuickNoteToInvoice(quickNote);
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoice();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        homeScreen = invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceWithTextNote(String rowID,
                                                   String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String woNote = "Only text Note";
        final String noteText = "Invoice text note";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.populateVehicleInfoDataOnCreateWOWizard(invoiceData.getWorkOrderData().getVinNumber(),
                invoiceData.getWorkOrderData().getVehicleInfoData().getVehicleColor());

        VNextNotesScreen notesscreen = vehicleInfoScreen.clickInspectionNotesOption();
        notesscreen.setNoteText(woNote);
        notesscreen.clickNotesBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(workOrderNumber), invoiceData.getWorkOrderData().getWorkOrderPrice());
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceInfoScreen.addTextNoteToInvoice(noteText);
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoice();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), invoiceData.getWorkOrderData().getWorkOrderPrice());
        homeScreen = invoicesScreen.clickBackButton();
        BaseUtils.waitABit(60 * 1000);

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInvoicesWebPage invoicesWebPage = leftMenu.selectInvoicesMenu();
        invoicesWebPage.selectInvoiceInTheList(invoiceNumber);
        Assert.assertEquals(invoicesWebPage.getSelectedInvoiceNote(), noteText);
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceWhichContainsPriceServicesWithDecimalQuantity(String rowID,
                                              String description, JSONObject testData) throws Exception {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
        VNextVisualScreen visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.clickAddServiceButton();
        visualscreen.clickDefaultDamageType(workOrderData.getServiceName());
        visualscreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen serviceDetailsScreen = visualscreen.clickCarImageMarker();
        serviceDetailsScreen.setServiceAmountValue(workOrderData.getServicePrice());
        Assert.assertEquals(serviceDetailsScreen.getServiceAmountValue(), workOrderData.getServicePrice());
        serviceDetailsScreen.setServiceQuantityValue(workOrderData.getServicePrice());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen inspavailableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspavailableServicesScreen.selectService(workOrderData.getMoneyServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = inspavailableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServiceQuantity());
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspectionNumber), PricesCalculations.getPriceRepresentation(workOrderData.getMoneyServicePrice()));

        VNextInspectionsMenuScreen inspmenuscreen = inspectionsscreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspmenuscreen.clickCreateWorkOrderInspectionMenuItem();
        vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
                workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoice();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), PricesCalculations.getPriceRepresentation(workOrderData.getMoneyServicePrice()));
        VNextEmailScreen emailScreen = invoicesScreen.clickOnInvoiceToEmail(invoiceNumber);
        if (!emailScreen.getToEmailFieldValue().equals(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail()))
            emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail());

        emailScreen.sendEmail();
        final String inspectionReportFilenName = invoiceNumber + ".pdf";
        EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextFreeRegistrationInfo.getInstance().getR360OutlookMail(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword(), EmailFolder.JUNK);

        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, inspectionReportFilenName)
                .unreadOnlyMessages(true).maxMessagesToSearch(5);
        Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find invoice: " + invoiceNumber);

        File pdfdoc = new File(inspectionReportFilenName);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains("Dent Repair \n$0.98"));
        Assert.assertTrue(pdftext.contains("Bumper Repair $0.98 \n$1.96"));


        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInvoicesWebPage invoicesWebPage = leftMenu.selectInvoicesMenu();
        invoicesWebPage.selectInvoiceInTheList(invoiceNumber);
        Assert.assertEquals(invoicesWebPage.getSelectedInvoiceCustomerName(), testcustomer);

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceWithSetPO(String rowID,
                                                                               String description, JSONObject testData) throws Exception {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();
        final String invoiceNote = "Only text Note";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());

        Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleInfoScreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleInfoScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceName());
        availableServicesScreen.selectService(workOrderData.getPercentageServiceName());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
        vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServiceQuantity());

        final String workOrderNumber = selectedServicesScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workOrdersScreen.getWorkOrderPriceValue(workOrderNumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceInfoScreen.addTextNoteToInvoice(invoiceNote);
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoice();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), woPrice);
        homeScreen = invoicesScreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateAndEmailInvoiceWithTwoMatrixPanel(String rowID,
                                           String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();


        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());

        Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleInfoScreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleInfoScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        for (VehiclePartData vehiclePartData : matrixServiceData.getVehiclePartsData()) {
            VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
            vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
            vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
            vehiclePartInfoScreen.selectAllAvailableAdditionalServices();
            vehiclePartInfoScreen.clickSaveVehiclePartInfo();
            vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());

        }
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());
        selectedServicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoice();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        VNextEmailScreen emailScreen = invoicesScreen.clickOnInvoiceToEmail(invoiceNumber);
        if (!emailScreen.getToEmailFieldValue().equals(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail()))
            emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail());

        emailScreen.sendEmail();
        invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOWizard(String rowID,
                                                            String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        vehicleInfoScreen.clickScreenForwardButton();
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceName());
        availableServicesScreen.selectService(workOrderData.getPercentageServiceName());

        availableServicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber));
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOMenu(String rowID,
                                                          String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        vehicleInfoScreen.clickScreenForwardButton();
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceName());
        availableServicesScreen.selectService(workOrderData.getPercentageServiceName());
        availableServicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrderSummaryScreen.clickWorkOrderSaveButton();
        VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber));
        invoicesScreen.clickBackButton();
    }

}

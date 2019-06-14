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
import com.cyberiansoft.test.email.getnada.NadaEMailService;
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
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
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
import java.util.List;

public class VNextInvoicesTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

    @BeforeClass(description = "R360 Invoices Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInvoicesTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWithMatrixService(String rowID,
                                                   String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        final WorkOrderData workOrderData = invoiceData.getWorkOrderData();
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        vehicleInfoScreen.selectModelColor(workOrderData.getVehicleInfoData().getVehicleColor());
        Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleInfoScreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleInfoScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextServiceDetailsScreen serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(workOrderData.getMoneyServiceData().getServiceName());
        serviceDetailsScreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        availableServicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());
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

        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServiceQuantity());

        final String woNumber = selectedServicesScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workOrdersScreen.getWorkOrderPriceValue(woNumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(woNumber);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoice();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWithTextNote(String rowID,
                                              String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String woNote = "Only text Note";
        final String noteText = "Invoice text note";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(invoiceData.getWorkOrderData().getVinNumber());
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        VNextNotesScreen notesScreen = vehicleInfoScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(woNote);
        GeneralSteps.pressBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(workOrderNumber), invoiceData.getWorkOrderData().getWorkOrderPrice());
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceInfoScreen.addTextNoteToInvoice(noteText);
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), invoiceData.getWorkOrderData().getWorkOrderPrice());
        invoicesScreen.clickBackButton();
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
        webdriver.quit();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWhichContainsPriceServicesWithDecimalQuantity(String rowID,
                                                                               String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServiceQuantity());
        vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        InvoiceData invoiceData = testCaseData.getInvoiceData();
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoicePONumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        VNextEmailScreen emailScreen = invoicesScreen.clickOnInvoiceToEmail(invoiceNumber);
        NadaEMailService nada = new NadaEMailService();
        nada.setEmailId(nada.getEmailId());
        emailScreen.sentToEmailAddress(nada.getEmailId());

        emailScreen.sendEmail();
        invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver())
        invoicesScreen.clickBackButton();

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
        VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginPage.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftMenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInvoicesWebPage invoicesWebPage = leftMenu.selectInvoicesMenu();
        invoicesWebPage.selectInvoiceInTheList(invoiceNumber);
        Assert.assertEquals(invoicesWebPage.getSelectedInvoiceCustomerName(), testcustomer.getFullName());

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInvoiceWithSetPO(String rowID,
                                           String description, JSONObject testData) throws Exception {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();
        final String invoiceNote = "Only text Note";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleInfoScreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleInfoScreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextServiceDetailsScreen serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(workOrderData.getMoneyServiceData().getServiceName());
        serviceDetailsScreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServicePrice());
        serviceDetailsScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceQuantity());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        availableServicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());
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

        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceData().getServiceName(), workOrderData.getMoneyServiceData().getServiceQuantity());

        final String workOrderNumber = selectedServicesScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workOrdersScreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workOrdersScreen.getWorkOrderPriceValue(workOrderNumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceInfoScreen.addTextNoteToInvoice(invoiceNote);
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), woPrice);
        invoicesScreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateAndEmailInvoiceWithTwoMatrixPanel(String rowID,
                                                            String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();


        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
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
            List<String> additionalServices = vehiclePartInfoScreen.getListOfAdditionalServices();
            for (String serviceName : additionalServices)
                vehiclePartInfoScreen.selectVehiclePartAdditionalService(serviceName);
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
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getInvoicePriceValue(invoiceNumber), workOrderData.getWorkOrderPrice());
        VNextEmailScreen emailScreen = invoicesScreen.clickOnInvoiceToEmail(invoiceNumber);
        if (!emailScreen.getToEmailFieldValue().equals(VNextFreeRegistrationInfo.getInstance().getR360UserMail()))
            emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());

        emailScreen.sendEmail();
        invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOWizard(String rowID,
                                                          String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        availableServicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());

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

    @Test(dataProvider="fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOMenu(String rowID,
                                                        String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleInfoScreen = homeScreen.openCreateWOWizard(testcustomer);
        vehicleInfoScreen.setVIN(workOrderData.getVinNumber());
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        availableServicesScreen.selectService(workOrderData.getPercentageServiceData().getServiceName());
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

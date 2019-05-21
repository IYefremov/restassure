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

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
        final WorkOrderData workOrderData = invoiceData.getWorkOrderData();
        vehicleinfoscreen.populateVehicleInfoDataOnCreateWOWizard(workOrderData.getVinNumber(), workOrderData.getVehicleInfoData().getVehicleColor());

        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleinfoscreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleinfoscreen.swipeScreenLeft();
        VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesscreen.selectService(workOrderData.getMoneyServiceName());
        servicesscreen.selectService(workOrderData.getPercentageServiceName());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        final HailMatrixService hailMatrixService = matrixServiceData.getHailMatrixServices().get(0);
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
        vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
        vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
        vehiclepartinfoscreen.selectVehiclePartAdditionalService(hailMatrixService.getMatrixAdditionalServices().get(0).getServiceName());
        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServiceQuantity());

        final String woNumber = selectedServicesScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workordersscreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workordersscreen.getWorkOrderPriceValue(woNumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        workordersscreen.clickCreateInvoiceFromWorkOrder(woNumber);
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceinfoscreen.addQuickNoteToInvoice(quickNote);
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
        Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), workOrderData.getWorkOrderPrice());
        homescreen = invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceWithTextNote(String rowID,
                                                   String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String woNote = "Only text Note";
        final String noteText = "Invoice text note";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
        vehicleinfoscreen.populateVehicleInfoDataOnCreateWOWizard(invoiceData.getWorkOrderData().getVinNumber(),
                invoiceData.getWorkOrderData().getVehicleInfoData().getVehicleColor());

        VNextNotesScreen notesscreen = vehicleinfoscreen.clickInspectionNotesOption();
        notesscreen.setNoteText(woNote);
        notesscreen.clickNotesBackButton();
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workordersscreen.getWorkOrderPriceValue(wonumber), invoiceData.getWorkOrderData().getWorkOrderPrice());
        workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceinfoscreen.addTextNoteToInvoice(noteText);
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
        Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoiceNumber), invoiceData.getWorkOrderData().getWorkOrderPrice());
        homescreen = invoicesscreen.clickBackButton();
        BaseUtils.waitABit(60 * 1000);

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInvoicesWebPage invoiceswebpage = leftmenu.selectInvoicesMenu();
        invoiceswebpage.selectInvoiceInTheList(invoiceNumber);
        Assert.assertEquals(invoiceswebpage.getSelectedInvoiceNote(), noteText);
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceWhichContainsPriceServicesWithDecimalQuantity(String rowID,
                                              String description, JSONObject testData) throws Exception {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        String inspnumbertc = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreenLeft();
        VNextVisualScreen visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualscreen.clickAddServiceButton();
        visualscreen.clickDefaultDamageType(workOrderData.getServiceName());
        visualscreen.clickCarImage();
        BaseUtils.waitABit(1000);
        VNextServiceDetailsScreen servicedetailsscreen = visualscreen.clickCarImageMarker();
        servicedetailsscreen.setServiceAmountValue(workOrderData.getServicePrice());
        Assert.assertEquals(servicedetailsscreen.getServiceAmountValue(), workOrderData.getServicePrice());
        servicedetailsscreen.setServiceQuantityValue(workOrderData.getServicePrice());
        servicedetailsscreen.clickServiceDetailsDoneButton();
        visualscreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
        inspservicesscreen.selectService(workOrderData.getMoneyServiceName());
        VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServiceQuantity());
        inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspnumbertc), PricesCalculations.getPriceRepresentation(workOrderData.getMoneyServicePrice()));

        VNextInspectionsMenuScreen inspmenuscreen = inspectionsscreen.clickOnInspectionByInspNumber(inspnumbertc);
        inspmenuscreen.clickCreateWorkOrderInspectionMenuItem();
        vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrderSummaryScreen wosummaryscreen = vehicleinfoscreen.goToWorkOrderSummaryScreen();
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
        Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoiceNumber), PricesCalculations.getPriceRepresentation(workOrderData.getMoneyServicePrice()));
        VNextEmailScreen emailscreen = invoicesscreen.clickOnInvoiceToEmail(invoiceNumber);
        if (!emailscreen.getToEmailFieldValue().equals(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail()))
            emailscreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail());

        emailscreen.sendEmail();
        final String inspectionreportfilenname = invoiceNumber + ".pdf";
        EmailUtils emailUtils = new EmailUtils(EmailHost.OUTLOOK, VNextFreeRegistrationInfo.getInstance().getR360OutlookMail(),
                VNextFreeRegistrationInfo.getInstance().getR360UserPassword(), EmailFolder.JUNK);

        EmailUtils.MailSearchParametersBuilder mailSearchParameters = new EmailUtils.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, inspectionreportfilenname)
                .unreadOnlyMessages(true).maxMessagesToSearch(5);
        Assert.assertTrue(emailUtils.waitForMessageWithSubjectAndDownloadAttachment(mailSearchParameters), "Can't find invoice: " + invoiceNumber);

        File pdfdoc = new File(inspectionreportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        Assert.assertTrue(pdftext.contains("Dent Repair \n$0.98"));
        Assert.assertTrue(pdftext.contains("Bumper Repair $0.98 \n$1.96"));


        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
                VNextBOLoginScreenWebPage.class);
        loginpage.userLogin(deviceuser, devicepsw);
        VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
                VNexBOLeftMenuPanel.class);
        VNextBOInvoicesWebPage invoiceswebpage = leftmenu.selectInvoicesMenu();
        invoiceswebpage.selectInvoiceInTheList(invoiceNumber);
        Assert.assertEquals(invoiceswebpage.getSelectedInvoiceCustomerName(), testcustomer);

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceWithSetPO(String rowID,
                                                                               String description, JSONObject testData) throws Exception {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();
        final String invoiceNote = "Only text Note";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());

        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleinfoscreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleinfoscreen.swipeScreenLeft();
        VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesscreen.selectService(workOrderData.getMoneyServiceName());
        servicesscreen.selectService(workOrderData.getPercentageServiceName());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        final HailMatrixService hailMatrixService = matrixServiceData.getHailMatrixServices().get(0);
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
        vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
        vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
        vehiclepartinfoscreen.selectVehiclePartAdditionalService(hailMatrixService.getMatrixAdditionalServices().get(0).getServiceName());
        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

        selectedServicesScreen.setServiceAmountValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServicePrice());
        selectedServicesScreen.setServiceQuantityValue(workOrderData.getMoneyServiceName(), workOrderData.getMoneyServiceQuantity());

        final String wonumber = selectedServicesScreen.getNewInspectionNumber();
        VNextWorkOrdersScreen workordersscreen = selectedServicesScreen.saveWorkOrderViaMenu();
        final String woPrice = workordersscreen.getWorkOrderPriceValue(wonumber);
        Assert.assertEquals(woPrice, workOrderData.getWorkOrderPrice());
        workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        invoiceinfoscreen.addTextNoteToInvoice(invoiceNote);
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
        Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), woPrice);
        homescreen = invoicesscreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateAndEmailInvoiceWithTwoMatrixPanel(String rowID,
                                           String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();


        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());

        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), workOrderData.getVehicleInfoData().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), workOrderData.getVehicleInfoData().getVehicleModel());
        Assert.assertEquals(vehicleinfoscreen.getYear(), workOrderData.getVehicleInfoData().getVehicleYear());
        vehicleinfoscreen.swipeScreenLeft();
        VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        final MatrixServiceData matrixServiceData = workOrderData.getMatrixServiceData();
        VNextPriceMatrixesScreen pricematrixesscreen = servicesscreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
        VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
        for (HailMatrixService hailMatrixService : matrixServiceData.getHailMatrixServices()) {
            VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
            vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
            vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
            vehiclepartinfoscreen.selectAllAvailableAdditionalServices();
            vehiclepartinfoscreen.clickSaveVehiclePartInfo();
            vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());

        }
        servicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
        VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());
        selectedServicesScreen.swipeScreenLeft();
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
        Assert.assertEquals(invoicesscreen.getInvoicePriceValue(invoicenumber), workOrderData.getWorkOrderPrice());
        VNextEmailScreen emailscreen = invoicesscreen.clickOnInvoiceToEmail(invoicenumber);
        if (!emailscreen.getToEmailFieldValue().equals(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail()))
            emailscreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360OutlookMail());

        emailscreen.sendEmail();
        invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOWizard(String rowID,
                                                            String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        vehicleinfoscreen.clickScreenForwardButton();
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        vehicleinfoscreen.swipeScreenLeft();
        vehicleinfoscreen.swipeScreenLeft();
        VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesscreen.selectService(workOrderData.getMoneyServiceName());
        servicesscreen.selectService(workOrderData.getPercentageServiceName());

        servicesscreen.swipeScreenLeft();
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber));
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyInvoiceCanBeCreatedFromWOMenu(String rowID,
                                                          String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        WorkOrderData workOrderData = invoiceData.getWorkOrderData();

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextVehicleInfoScreen vehicleinfoscreen = homescreen.openCreateWOWizard(testcustomer);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        vehicleinfoscreen.clickScreenForwardButton();
        VNextVehicleVINHistoryScreen vehicleVINHistoryScreen = new VNextVehicleVINHistoryScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleVINHistoryScreen.clickBackButton();
        final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreenLeft();
        vehicleinfoscreen.swipeScreenLeft();
        VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        servicesscreen.selectService(workOrderData.getMoneyServiceName());
        servicesscreen.selectService(workOrderData.getPercentageServiceName());
        servicesscreen.swipeScreenLeft();
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        wosummaryscreen.clickWorkOrderSaveButton();
        VNextWorkOrdersScreen workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        Assert.assertTrue(invoicesscreen.isInvoiceExists(invoiceNumber));
        invoicesscreen.clickBackButton();
    }

}

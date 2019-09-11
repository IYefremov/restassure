package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.*;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.core.MobilePlatform;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.WorkOrderStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.enums.ServiceRequestAppointmentStatuses;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens.RegularMyInvoicesScreen;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.UATInspectionTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.UATInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.UATServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.UATWorkOrderTypes;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IOSRegularProdRegressionTestCases extends ReconProBaseTestCase {

    private List<String> workOrdersForInvoice = new ArrayList<>();

    @BeforeClass
    public void setUpSuite() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getProdRegressionSuiteTestCasesDataPath();
        mobilePlatform = MobilePlatform.IOS_REGULAR;
        initTestUser("Test User", "1111");
        DeviceRegistrator.getInstance().installAndRegisterDevice(browsertype, mobilePlatform, deviceofficeurl,
                ReconProIOSStageInfo.getInstance().getUserStageUserName(), ReconProIOSStageInfo.getInstance().getUserStageUserPassword(), "oks",
                envType);

        RegularMainScreenSteps.userLogin("Test User", "1111");
        RegularHomeScreenSteps.navigateToSettingsScreen();
        RegularSettingsScreenSteps.setShowAvailableSelectedServicesOn();
        RegularNavigationSteps.navigateBackScreen();
    }

    public String createInspectionForWorkOrders(InspectionData inspectionData) {

        RegularHomeScreenSteps.navigateToCustomersScreen();
        AppCustomer appCustomer;
        if (inspectionData.getWholesailCustomer() != null) {
            appCustomer = inspectionData.getWholesailCustomer();
            RegularCustomersScreenSteps.switchToWholesaleMode();
        }
        else {
            appCustomer = inspectionData.getInspectionRetailCustomer();
            RegularCustomersScreenSteps.switchToRetailMode();
        }
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(appCustomer, UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionID = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.selectBundleService(inspectionData.getServicesScreen().getBundleService());
        RegularServicesScreenSteps.selectLaborServiceAndSetData(inspectionData.getServicesScreen().getLaborService());
        RegularServiceDetailsScreenSteps.saveServiceDetails();
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularNavigationSteps.swipeScreenLeft();
        RegularPriceMatrixScreenSteps.selectPriceMatrixData(inspectionData.getPriceMatrixScreenData());
        RegularVehiclePartsScreenValidations.verifyVehiclePartScreenSubTotalValue(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartTotalPrice());
        RegularVehiclePartsScreenSteps.saveVehiclePart();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(inspectionData.getPriceMatrixScreenData().getMatrixScreenPrice());
        //RegularWizardScreenValidations.verifyScreenTotalPrice(inspectionData.getPriceMatrixScreenData().getMatrixScreenTotalPrice());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(inspectionData.getServicesScreen().getScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(inspectionData.getServicesScreen().getScreenTotalPrice());
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsScreenValidations.verifyInspectionTotalPrice(inspectionID, inspectionData.getInspectionTotalPrice());

        RegularMyInspectionsSteps.selectInspectionForApprovaViaAction(inspectionID);
        RegularApproveInspectionScreenActions.clickApproveAllServicesButton();
        RegularApproveInspectionScreenActions.saveApprovedServices();
        RegularApproveInspectionScreenActions.clickSingnAndDrawSignature();

        RegularMyInspectionsScreenValidations.verifyInspectionTotalPrice(inspectionID, inspectionData.getInspectionTotalPrice());
        RegularMyInspectionsScreenValidations.verifyInspectionApprovedPrice(inspectionID, inspectionData.getInspectionTotalPrice());
        return inspectionID;
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInspectionVerifyOnBO(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        String inspectionID = createInspectionForWorkOrders(inspectionData);
        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionID, UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        workOrdersForInvoice.add(workOrderNumber);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelected(serviceData.getServiceName(), true);
        }
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getBundleService().getBundleServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getLaborService().getServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName(), false);

        RegularWizardScreenValidations.verifyScreenSubTotalPrice(testCaseData.getWorkOrderData().getServicesScreen().getScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(testCaseData.getWorkOrderData().getServicesScreen().getScreenTotalPrice());

        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderTotalPrice(workOrderNumber, testCaseData.getWorkOrderData().getWorkOrderPrice());

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateNewWOUsingCopyVehicleInfoAction_VerifyNewWOOnBO(String rowID,
                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.selectWorkOrderForCopyVehicle(workOrdersForInvoice.get(0));
        RegularCustomersScreenSteps.selectCustomer(testCaseData.getWorkOrderData().getWholesailCustomer());
        RegularWorkOrderTypesSteps.selectWorkOrderType(UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        workOrdersForInvoice.add(RegularVehicleInfoScreenSteps.getWorkOrderNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelected(serviceData.getServiceName(), false);
        }
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getBundleService().getBundleServiceName(), false);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getLaborService().getServiceName(), false);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName(), false);
        RegularWorkOrdersSteps.saveWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateNewWOUsingCopyServicesInfoAction_VerifyNewWOOnBO(String rowID,
                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.selectWorkOrderForCopyServices(workOrdersForInvoice.get(0));
        RegularCustomersScreenSteps.selectCustomer(testCaseData.getWorkOrderData().getWholesailCustomer());
        RegularWorkOrderTypesSteps.selectWorkOrderType(UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoValidations.validateVehicleInfoData(testCaseData.getWorkOrderData().getVehicleInfoData());
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        workOrdersForInvoice.add(RegularVehicleInfoScreenSteps.getWorkOrderNumber());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelected(serviceData.getServiceName(), true);
        }
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getBundleService().getBundleServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getLaborService().getServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName(), false);

        RegularWizardScreenValidations.verifyScreenSubTotalPrice(testCaseData.getWorkOrderData().getServicesScreen().getScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(testCaseData.getWorkOrderData().getServicesScreen().getScreenTotalPrice());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInvoiceBasedOn3CreatedWOs_VerifyPresenceOfInvoiceOnBO(String rowID,
                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InvoiceData invoiceData = testCaseData.getInvoiceData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        for (String workOrderId : workOrdersForInvoice) {
            RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderId);
            RegularSummaryApproveScreenSteps.approveWorkOrder();
        }

        for (String workOrderId : workOrdersForInvoice)
            RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderId);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);

        for (String workOrderId : workOrdersForInvoice)
            RegularInvoiceInfoScreenValidations.verifyWorkOrderIsPresentForInvoice(workOrderId, true);
        RegularInvoiceInfoScreenValidations.verifyInvoiceTotalValue(invoiceData.getInvoiceTotal());
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(invoiceData.getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceForPayment(invoiceNumber);
        RegularPaymentOptionsScreenSteps.closePaymentOptions();
        RegularPaymentScreenSteps.sitchToCashCheckPayOption();

        RegularPaymentScreenValidations.verifyCashCheckAmountValue(invoiceData.getInvoiceTotal());
        RegularPaymentScreenSteps.setInvoiceCashCheckPaymentValues(invoiceData.getCashCheckPaymentData());
        RegularPaymentScreenSteps.payForInvoice();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
         operationsWebPage.clickInvoicesLink();
        InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        String mainWindowHandle = webdriver.getWindowHandle();
        invoicesWebPage.clickInvoicePayments(invoiceNumber);
        InvoicePaymentsTabWebPage invoicePaymentsTabWebPage = new InvoicePaymentsTabWebPage(webdriver);
        Assert.assertEquals(invoicePaymentsTabWebPage.getInvoicePaidValue(invoiceNumber),
                BackOfficeUtils.getFormattedServicePriceValue(invoiceData.getCashCheckPaymentData().getCashCheckAmount()));
        Assert.assertEquals(invoicePaymentsTabWebPage.getInvoiceAmountValue(invoiceNumber),
                BackOfficeUtils.getFormattedServicePriceValue(invoiceData.getInvoiceTotal()));
        invoicePaymentsTabWebPage.closeNewTab(mainWindowHandle);

        RegularMyInvoicesScreenSteps.selectInvoiceForPayment(invoiceNumber);
        RegularPaymentOptionsScreenSteps.closePaymentOptions();
        RegularPaymentScreenSteps.sitchToCashCheckPayOption();
        Double expectedAmount = Double.valueOf(invoiceData.getInvoiceTotal()) - Double.valueOf(invoiceData.getCashCheckPaymentData().getCashCheckAmount());
        RegularPaymentScreenValidations.verifyCashCheckAmountValue(String.valueOf(expectedAmount));
        RegularPaymentScreenSteps.setCashCheckNumberValue(invoiceData.getCashCheckPaymentData().getCashCheckNumber());
        RegularPaymentScreenSteps.payForInvoice();
        RegularMyInvoicesScreenSteps.selectInvoice(invoiceNumber);
        RegularMenuValidations.menuShouldBePresent(ReconProMenuItems.PAY, false);
        RegularMenuItemsScreenSteps.closeMenuScreen();
        RegularNavigationSteps.navigateBackScreen();

        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        mainWindowHandle = webdriver.getWindowHandle();
        invoicesWebPage.clickInvoicePayments(invoiceNumber);
        Assert.assertEquals(invoicePaymentsTabWebPage.getInvoicePaidValue(invoiceNumber),
                BackOfficeUtils.getFormattedServicePriceValue(invoiceData.getInvoiceTotal()));
        Assert.assertEquals(invoicePaymentsTabWebPage.getInvoiceAmountValue(invoiceNumber),
                BackOfficeUtils.getFormattedServicePriceValue(invoiceData.getInvoiceTotal()));
        invoicePaymentsTabWebPage.closeNewTab(mainWindowHandle);
        webdriver.quit();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateSRAddAppointmentCreateInspectionAndWOAndInvoiceAndVerifyDataOnBOISCorrectlyDisplayed(String rowID,
                                                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreenSteps.navigateToServiceRequestScreenScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(serviceRequestData.getWholesailCustomer(), UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(serviceRequestData.getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        final String serviceRequestNumber = RegularServiceRequestSteps.getFirstServiceRequestNumber();

        RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber , UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoValidations.validateVehicleInfoData(serviceRequestData.getVihicleInfo());
        RegularVehicleInfoScreenSteps.setVehicleInfoData(testCaseData.getInspectionData().getVehicleInfo());
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(testCaseData.getInspectionData().getInsuranceCompanyData());
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularServiceRequestsScreenValidations.verifyInspectionIconPresentForServiceRequest(serviceRequestNumber, true);
        RegularServiceRequestSteps.startCreatingWorkOrderFromServiceRequest(serviceRequestNumber, UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoValidations.validateVehicleInfoData(serviceRequestData.getVihicleInfo());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularWizardScreensSteps.clickSaveButton();
        RegularServiceRequestsScreenValidations.verifyWorkOrderIconPresentForServiceRequest(serviceRequestNumber, true);

        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularSummaryApproveScreenSteps.approveWorkOrder();
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);

        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenSteps.saveInvoiceAsDraft();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToServiceRequestScreenScreen();
        RegularServiceRequestSteps.openServiceRequestDetails(serviceRequestNumber);
        RegularServiceRequestDetalsScreenValidations.verifySRSummaryAppointmentsInformationExists(true);
        RegularServiceRequestDetalsScreenValidations.verifySRSheduledAppointmentExists(true);

        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInspectionsButton();
        RegularTeamInspectionsScreenValidations.verifyTeamInspectionExists(inspectionNumber, true);
        RegularNavigationSteps.navigateBackScreen();

        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryOrdersButton();
        RegularTeamWorkOrdersScreenValidations.verifyTeamInspectionExists(workOrderNumber, true);
        RegularNavigationSteps.navigateBackScreen();

        RegularServiceRequestDetalsScreenSteps.clickServiceRequestSummaryInvoicesButton();
        RegularTeamInvoicesScreenValidations.verifyTeamInspectionExists(invoiceNumber, true);
        RegularNavigationSteps.navigateBackScreen();
        RegularServiceRequestDetalsScreenSteps.navigateBackToServiceRequestsScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        operationsWebPage.clickNewServiceRequestList();
        ServiceRequestsListWebPage serviceRequestsListWebPage = new ServiceRequestsListWebPage(webdriver);
        serviceRequestsListWebPage.makeSearchPanelVisible();

        serviceRequestsListWebPage.setSearchFreeText(serviceRequestNumber);
        serviceRequestsListWebPage.clickFindButton();
        serviceRequestsListWebPage.selectFirstServiceRequestFromList();
        Assert.assertEquals(serviceRequestsListWebPage.getServiceRequestInspectionNumber(), inspectionNumber);
        Assert.assertEquals(serviceRequestsListWebPage.getServiceRequestWorkOrderNumber(), workOrderNumber);
        Assert.assertEquals(serviceRequestsListWebPage.getServiceRequestInvoiceNumber(), invoiceNumber);
        Assert.assertEquals(serviceRequestsListWebPage.getServiceRequestAppointmentStatus(), ServiceRequestAppointmentStatuses.SCHEDULED.getAppointmentStatus());
        webdriver.quit();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateRetailCustomer(String rowID,
                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreenSteps.switchToRetailMode();
        RegularCustomersScreenSteps.addNewRetailCustomer(inspectionData.getInspectionRetailCustomer());
        RegularCustomersScreenValidations.validateCustomerExists(inspectionData.getInspectionRetailCustomer());
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        backOfficeHeaderPanel.clickCompanyLink();
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        companyWebPage.clickClientsLink();
        ClientsWebPage clientsWebPage = new ClientsWebPage(webdriver);
        clientsWebPage.isClientPresentInTable(inspectionData.getInspectionRetailCustomer().getFullName());

        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInspectionWOInvoiceForCreatedRetailCustomer(String rowID,
                                               String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        AppCustomer appCustomer;
        if (inspectionData.getWholesailCustomer() != null) {
            appCustomer = inspectionData.getWholesailCustomer();
            RegularCustomersScreenSteps.switchToWholesaleMode();
        }
        else {
            appCustomer = inspectionData.getInspectionRetailCustomer();
            RegularCustomersScreenSteps.switchToRetailMode();
        }
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(appCustomer, UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularInspectionsSteps.saveInspectionAsFinal();

        RegularMyInspectionsSteps.selectInspectionForApprovaViaAction(inspectionNumber);
        RegularApproveInspectionScreenActions.clickApproveAllServicesButton();
        RegularApproveInspectionScreenActions.saveApprovedServices();
        RegularApproveInspectionScreenActions.clickSingnAndDrawSignature();


        RegularMyInspectionsSteps.selectSendEmailMenuForInspection(inspectionNumber);

        NadaEMailService nada = new NadaEMailService();
        RegularEmailScreenSteps.sendEmailToAddress(nada.getEmailId());

        final String inspectionFileName = inspectionNumber + ".pdf";
        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionNumber, inspectionFileName);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find inspection: " + inspectionNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(inspectionNumber);

        File pdfDoc = new File(inspectionFileName);
        String pdfText = PDFReader.getPDFText(pdfDoc);
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            Assert.assertTrue(pdfText.contains(serviceData.getServiceName()));
        }

        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionNumber, UATWorkOrderTypes.WO_FINAL_INVOICE);
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularSummaryApproveScreenSteps.approveWorkOrder();
        RegularMyWorkOrdersSteps.selectSendEmailMenuForWorkOrder(workOrderNumber);
        RegularEmailScreenSteps.sendEmailToAddress(nada.getEmailId());

        final String workOrderFileName = workOrderNumber + ".pdf";
        searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(workOrderNumber, workOrderFileName);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find work order: " + workOrderNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(workOrderNumber);

        pdfDoc = new File(workOrderFileName);
        pdfText = PDFReader.getPDFText(pdfDoc);
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            Assert.assertTrue(pdfText.contains(serviceData.getServiceName()));
        }

        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenSteps.saveInvoiceAsFinal();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, false);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularMyInvoicesScreenSteps.selectSendEmailMenuForInvoice(invoiceNumber);
        RegularEmailScreenSteps.sendEmailToAddress(nada.getEmailId());

        final String invoiceFileName = invoiceNumber + ".pdf";
        searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invoiceFileName);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        pdfDoc = new File(invoiceFileName);
        pdfText = PDFReader.getPDFText(pdfDoc);
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            Assert.assertTrue(pdfText.contains(serviceData.getServiceName()));
        }
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCheckInUndoCheckInForSR(String rowID,
                                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreenSteps.navigateToServiceRequestScreenScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(serviceRequestData.getWholesailCustomer(), UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(serviceRequestData.getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        final String serviceRequestNumber = RegularServiceRequestSteps.getFirstServiceRequestNumber();
        RegularServiceRequestSteps.clickServiceRequestCheckInAction(serviceRequestNumber);
        RegularServiceRequestSteps.clickServiceRequestUndoCheckInAction(serviceRequestNumber);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCreatingSRFromInspectionTeamInspection(String rowID,
                                                                      String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularInspectionsSteps.saveInspectionAsFinal();

        RegularMyInspectionsSteps.createServiceRequestFromInspection(inspectionNumber, UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoValidations.validateVehicleInfoData(testCaseData.getServiceRequestData().getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        RegularMyInspectionsSteps.switchToTeamView();

        RegularMyInspectionsSteps.createServiceRequestFromInspection(inspectionNumber, UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoValidations.validateVehicleInfoData(testCaseData.getServiceRequestData().getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyAddNotesToInspection(String rowID,
                                                                 String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String textNotes = "Inspection text notes";

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularWizardScreensSteps.clickNotesButton();
        RegularNotesScreenSteps.setTextNotes(textNotes);
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenSteps.saveNotes();
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsScreenValidations.verifyNotesIconPresentForInspection(inspectionNumber, true);

        RegularMyInspectionsSteps.selectInspectionNotesMenu(inspectionNumber);
        RegularNotesScreenValidations.verifyTextNotesPresent(textNotes);
        RegularNotesScreenSteps.switchToPhotosView();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
        RegularNotesScreenSteps.saveNotes();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyAddNotesForWorkOrder(String rowID,
                                               String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String textNotes = "Inspection text notes";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularWizardScreensSteps.clickNotesButton();
        RegularNotesScreenSteps.setTextNotes(textNotes);
        RegularNotesScreenSteps.addImageNote();
        RegularNotesScreenSteps.saveNotes();
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersScreenValidations.verifyNotesIconPresentForInspection(workOrderNumber, true);

        RegularMyWorkOrdersSteps.selectWorkOrderNotesMenu(workOrderNumber);
        RegularNotesScreenValidations.verifyTextNotesPresent(textNotes);
        RegularNotesScreenSteps.switchToPhotosView();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(1);
        RegularNotesScreenSteps.saveNotes();
        RegularMyWorkOrdersSteps.waitMyWorkOrdersLoaded();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyDeleteWOFunctionality(String rowID,
                                               String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.deleteWorkOrder(workOrderNumber);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, false);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyArchiveFunctionalityForInspection(String rowID,
                                               String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsSteps.archiveInspection(inspectionNumber);
        RegularMyInspectionsScreenValidations.verifyInspectionPresent(inspectionNumber, false);

        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyApprovingInspectionUnderMyInspectionTeamInspection(String rowID,
                                                            String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        List<String> inspectionsIDs = new ArrayList<>();
        final int inspectionsToCreate = 2;

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        for (int i =0; i < inspectionsToCreate; i++) {
            RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
            RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
            inspectionsIDs.add(RegularVehicleInfoScreenSteps.getInspectionNumber());
            RegularNavigationSteps.navigateToClaimScreen();
            RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

            RegularNavigationSteps.navigateToServicesScreen();
            for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
                RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
            }

            RegularServicesScreenSteps.waitServicesScreenLoad();
            RegularInspectionsSteps.saveInspectionAsFinal();
        }
        RegularMyInspectionsScreenValidations.verifyApproveIconPresentForInspection(inspectionsIDs.get(0), true);
        RegularMyInspectionsSteps.selectInspectionForApprovaViaAction(inspectionsIDs.get(0));
        RegularApproveInspectionScreenActions.clickApproveAllServicesButton();
        RegularApproveInspectionScreenActions.saveApprovedServices();
        RegularApproveInspectionScreenActions.clickSingnAndDrawSignature();
        RegularMyInspectionsScreenValidations.verifyApproveIconPresentForInspection(inspectionsIDs.get(0), false);

        RegularMyInspectionsSteps.switchToTeamView();
        RegularMyInspectionsScreenValidations.verifyApproveIconPresentForInspection(inspectionsIDs.get(1), true);
        RegularMyInspectionsSteps.selectInspectionForApprovaViaAction(inspectionsIDs.get(1));
        RegularApproveInspectionScreenActions.clickApproveAllServicesButton();
        RegularApproveInspectionScreenActions.saveApprovedServices();
        RegularApproveInspectionScreenActions.clickSingnAndDrawSignature();
        RegularMyInspectionsScreenValidations.verifyApproveIconPresentForInspection(inspectionsIDs.get(1), false);

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCreatingInspectionFromWO(String rowID,
                                               String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String trimValue = "25th Anniversary";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.startCreatingNewInspectionfromWorkOrder(workOrderNumber, UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        final String inspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularVehicleInfoScreenSteps.setTrim(trimValue);
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(testCaseData.getInspectionData().getInsuranceCompanyData());
        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreenValidations.verifyInspectionPresent(inspectionNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyChangeStatusForWO(String rowID,
                                                   String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.changeStatusForWorkOrder(workOrderNumber, WorkOrderStatuses.ON_HOLD);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderHasApproveIcon(workOrderNumber, false);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderHasInvoiceIcon(workOrderNumber, false);
        RegularMyWorkOrdersSteps.changeStatusForWorkOrder(workOrderNumber, WorkOrderStatuses.APPROVED);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderHasApproveIcon(workOrderNumber, false);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderHasInvoiceIcon(workOrderNumber, true);
        RegularMyWorkOrdersSteps.changeStatusForWorkOrder(workOrderNumber, WorkOrderStatuses.NEW);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderHasApproveIcon(workOrderNumber, true);
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderHasInvoiceIcon(workOrderNumber, false);

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyDeleteInvoiceFunctionality(String rowID,
                                                String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularSummaryApproveScreenSteps.approveWorkOrder();
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);

        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.voidInvoice(invoiceNumber);
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, false);

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCreatingInspectionsFromInvoiceTeamInvoice(String rowID,
                                                     String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularSummaryApproveScreenSteps.approveWorkOrder();
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);

        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.startCreatingNewInspectionFromInvoice(invoiceNumber, UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        final String myInspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(testCaseData.getInspectionData().getInsuranceCompanyData());
        RegularInspectionsSteps.saveInspectionAsFinal();

        RegularMyInvoicesScreenSteps.switchToTeamView();
        RegularMyInvoicesScreenSteps.startCreatingNewInspectionFromInvoice(invoiceNumber, UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoValidations.validateVehicleInfoData(workOrderData.getVehicleInfoData());
        final String teamInspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(testCaseData.getInspectionData().getInsuranceCompanyData());
        RegularInspectionsSteps.saveInspectionAsFinal();

        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();
        RegularMyInspectionsScreenValidations.verifyInspectionPresent(myInspectionNumber, true);
        RegularMyInvoicesScreenSteps.switchToTeamView();
        RegularMyInspectionsScreenValidations.verifyInspectionPresent(teamInspectionNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }
}

package com.cyberiansoft.test.ios10_client.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
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
import com.cyberiansoft.test.enums.OrderMonitorServiceStatuses;
import com.cyberiansoft.test.enums.OrderMonitorStatuses;
import com.cyberiansoft.test.enums.WorkOrderStatuses;
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.enums.ServiceRequestAppointmentStatuses;
import com.cyberiansoft.test.ios10_client.generalvalidations.AlertsValidations;
import com.cyberiansoft.test.ios10_client.hdclientsteps.NavigationSteps;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.UATInspectionTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.UATInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.UATServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.UATWorkOrderTypes;
import com.cyberiansoft.test.ios10_client.utils.AlertsCaptions;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import com.cyberiansoft.test.ios10_client.utils.PDFReader;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
        } else {
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateNewWOUsingCopyVehicleInfoAction_VerifyNewWOOnBO(String rowID,
                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCopyingVehicleForWorkOrder(workOrdersForInvoice.get(0),
                testCaseData.getWorkOrderData().getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateNewWOUsingCopyServicesInfoAction_VerifyNewWOOnBO(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.startCopyingServicesForWorkOrder(workOrdersForInvoice.get(0),
                testCaseData.getWorkOrderData().getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
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
        RegularInvoicesSteps.saveInvoiceAsFinal();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateSRAddAppointmentCreateInspectionAndWOAndInvoiceAndVerifyDataOnBOISCorrectlyDisplayed(String rowID,
                                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(serviceRequestData.getWholesailCustomer(), UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(serviceRequestData.getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        final String serviceRequestNumber = RegularServiceRequestSteps.getFirstServiceRequestNumber();

        RegularServiceRequestSteps.startCreatingInspectionFromServiceRequest(serviceRequestNumber, UATInspectionTypes.INSP_APPROVE_MULTISELECT);
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
        RegularInvoicesSteps.saveInvoiceAsDraft();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
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
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        backOfficeHeaderPanel.clickOperationsLink();
        OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
        operationsWebPage.clickNewServiceRequestList();
        ServiceRequestsListInteractions serviceRequestsListInteractions = new ServiceRequestsListInteractions();
        serviceRequestsListInteractions.makeSearchPanelVisible();

        serviceRequestsListInteractions.setSearchFreeText(serviceRequestNumber);
        serviceRequestsListInteractions.clickFindButton();
        serviceRequestsListInteractions.selectFirstServiceRequestFromList();
        Assert.assertEquals(serviceRequestsListInteractions.getServiceRequestInspectionNumber(), inspectionNumber);
        Assert.assertEquals(serviceRequestsListInteractions.getServiceRequestWorkOrderNumber(), workOrderNumber);
        Assert.assertEquals(serviceRequestsListInteractions.getServiceRequestInvoiceNumber(), invoiceNumber);
        Assert.assertEquals(serviceRequestsListInteractions.getServiceRequestAppointmentStatus(), ServiceRequestAppointmentStatuses.SCHEDULED.getAppointmentStatus());
        webdriver.quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateRetailCustomer(String rowID,
                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();


        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        backOfficeHeaderPanel.clickCompanyLink();
        CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);
        companyWebPage.clickClientsLink();
        ClientsWebPage clientsWebPage = new ClientsWebPage(webdriver);
        clientsWebPage.searchClientByName(inspectionData.getInspectionRetailCustomer().getFullName());
        if (clientsWebPage.isClientPresentInTable(inspectionData.getInspectionRetailCustomer().getFullName()))
            clientsWebPage.deleteClient(inspectionData.getInspectionRetailCustomer().getFullName());
        DriverBuilder.getInstance().getDriver().quit();

        RegularHomeScreenSteps.navigateToStatusScreen();
        RegularStatusScreenSteps.updateMainDataBase(testuser);

        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreenSteps.switchToRetailMode();
        RegularCustomersScreenSteps.addNewRetailCustomer(inspectionData.getInspectionRetailCustomer());
        RegularCustomersScreenValidations.validateCustomerExists(inspectionData.getInspectionRetailCustomer());
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        backOfficeHeaderPanel.clickCompanyLink();
        companyWebPage = new CompanyWebPage(webdriver);
        companyWebPage.clickClientsLink();
        clientsWebPage = new ClientsWebPage(webdriver);
        clientsWebPage.isClientPresentInTable(inspectionData.getInspectionRetailCustomer().getFullName());

        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionWOInvoiceForCreatedRetailCustomer(String rowID,
                                                                      String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToCustomersScreen();
        AppCustomer appCustomer;
        if (inspectionData.getWholesailCustomer() != null) {
            appCustomer = inspectionData.getWholesailCustomer();
            RegularCustomersScreenSteps.switchToWholesaleMode();
        } else {
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
        RegularInvoicesSteps.saveInvoiceAsFinal();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCheckInUndoCheckInForSR(String rowID,
                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();


        RegularHomeScreenSteps.navigateToCustomersScreen();
        RegularCustomersScreenSteps.switchToWholesaleMode();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(serviceRequestData.getWholesailCustomer(), UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(serviceRequestData.getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        final String serviceRequestNumber = RegularServiceRequestSteps.getFirstServiceRequestNumber();
        RegularServiceRequestSteps.clickServiceRequestCheckInAction(serviceRequestNumber);
        RegularServiceRequestSteps.clickServiceRequestUndoCheckInAction(serviceRequestNumber);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCreatingSRFromInspectionTeamInspection(String rowID,
                                                                 String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAddNotesToInspection(String rowID,
                                               String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAddNotesForWorkOrder(String rowID,
                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String textNotes = "Work Order text notes";

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyDeleteWOFunctionality(String rowID,
                                                String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyArchiveFunctionalityForInspection(String rowID,
                                                            String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyApprovingInspectionUnderMyInspectionTeamInspection(String rowID,
                                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        List<String> inspectionsIDs = new ArrayList<>();
        final int inspectionsToCreate = 2;

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        for (int i = 0; i < inspectionsToCreate; i++) {
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCreatingInspectionFromWO(String rowID,
                                                   String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyChangeStatusForWO(String rowID,
                                            String description, JSONObject testData) {

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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyDeleteInvoiceFunctionality(String rowID,
                                                     String description, JSONObject testData) {

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
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.voidInvoice(invoiceNumber);
        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, false);

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCreatingInspectionsFromInvoiceTeamInvoice(String rowID,
                                                                    String description, JSONObject testData) {

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
        RegularInvoicesSteps.saveInvoiceAsFinal();
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

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyChangePOActionForInvoicesTeamInvoice(String rowID,
                                                               String description, JSONObject testData) {

        final String myInvoicePO = "myPO12345";
        final String teamInvoicePO = "teamPO12345";

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
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.changeInvoicePONumber(invoiceNumber, myInvoicePO);
        RegularMyInvoicesScreenValidations.verifyInvoicePONumber(invoiceNumber, myInvoicePO);
        RegularMyInvoicesScreenSteps.switchToTeamView();
        RegularMyInvoicesScreenSteps.changeInvoicePONumber(invoiceNumber, teamInvoicePO);
        RegularMyInvoicesScreenValidations.verifyInvoicePONumber(invoiceNumber, teamInvoicePO);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyApprovingInvoiceFromTeamInvoice(String rowID,
                                                          String description, JSONObject testData) {

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
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        BaseUtils.waitABit(15000);
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.switchToTeamView();
        RegularMyInvoicesScreenSteps.approveInvoice(invoiceNumber);
        RegularMyInvoicesScreenValidations.verifyInvoiceHasApproveIcon(invoiceNumber, false);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testCreateInspectionFromCopy(String rowID,
                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String inspectionID = createInspectionForWorkOrders(inspectionData);
        RegularMyInspectionsSteps.selectInspectionForCopy(inspectionID);
        RegularVehicleInfoValidations.validateVehicleInfoData(inspectionData.getVehicleInfo());
        final String copyInspectionNumber = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToPriceMatrixScreen();
        RegularPriceMatrixScreenValidations.verifyPriceMatrixScreenSubTotalValue(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartTotalPrice());
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();

        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelected(serviceData.getServiceName(), true);
        }
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getBundleService().getBundleServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getLaborService().getServiceName(), true);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getPriceMatrixScreenData().getVehiclePartData().getVehiclePartAdditionalService().getServiceName(), false);

        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularMyInspectionsScreenValidations.verifyInspectionTotalPrice(copyInspectionNumber, inspectionData.getInspectionTotalPrice());
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySearchFunctionalityUnderTeamInvoices(String rowID,
                                                               String description, JSONObject testData) {

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
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.switchToTeamView();

        RegularMyInvoicesScreenSteps.clickInvoicesSearchButton();
        RegularSearchScreenSteps.searchStatus("All");
        RegularSearchScreenSteps.searchCustomer(workOrderData.getWholesailCustomer());
        RegularSearchScreenSteps.searchInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);
        RegularSearchScreenSteps.setSearchText(invoiceNumber);
        RegularSearchScreenSteps.saveSearchDialog();

        RegularMyInvoicesScreenValidations.verifyInvoicePresent(invoiceNumber, true);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyChangeCustomerForWO(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Test");

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
        RegularMyWorkOrdersSteps.changeCustomerForWorkOrder(workOrderNumber, wholesailCustomer);

        RegularMyWorkOrdersSteps.openWorkOrderDetails(workOrderNumber);
        RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(wholesailCustomer);
        RegularWizardScreensSteps.cancelWizard();

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyChangeCustomerForInspection(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        WholesailCustomer wholesailCustomer = new WholesailCustomer();
        wholesailCustomer.setCompanyName("Test");

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

        RegularMyInspectionsSteps.changeCustomerForInspection(inspectionNumber, wholesailCustomer);
        BaseUtils.waitABit(15 * 1000);
        RegularMyInspectionsSteps.openInspectionDetails(inspectionNumber);

        RegularVehicleInfoValidations.verifyVehicleInfoScreenCustomerValue(wholesailCustomer);
        RegularWizardScreensSteps.cancelWizard();

        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyDecodingOfVINNumber(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        VehicleInfoData vehicleInfoData = new VehicleInfoData();
        vehicleInfoData.setVinNumber("JA4LS31H8YP047397");

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(testCaseData.getInspectionData().getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(vehicleInfoData);
        RegularVehicleInfoValidations.validateVehicleInfoData(testCaseData.getInspectionData().getVehicleInfo());
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(testCaseData.getWorkOrderData().getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(vehicleInfoData);
        RegularVehicleInfoValidations.validateVehicleInfoData(testCaseData.getWorkOrderData().getVehicleInfoData());
        RegularMyWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatDisclaimerIsShownWhenApproveInvoice(String rowID,
                                                                  String description, JSONObject testData) {

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
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.approveInvoice(invoiceNumber);
        RegularMyInvoicesScreenValidations.verifyInvoiceHasApproveIcon(invoiceNumber, false);
        RegularNavigationSteps.navigateBackScreen();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyThatPhaseIsCompletedCorrect(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String location = "All locations";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_MONITOR);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.selectSearchLocation(location);
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        for (OrderMonitorData orderMonitorData : workOrderData.getOrderMonitorsData())
            RegularOrderMonitorScreenValidations.verifyOrderPhasePresent(orderMonitorData, true);

        RegularOrderMonitorScreenSteps.selectOrderPhase(workOrderData.getOrderMonitorsData().get(1));
        RegularOrderMonitorScreenSteps.clickPhaseChangeStatus();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUS_OF_THE_PHASE);


        OrderMonitorData startPhase = workOrderData.getOrderMonitorsData().get(0);
        RegularOrderMonitorScreenSteps.selectServicePanel(startPhase.getMonitorServicesData().get(0).getMonitorService());
        RegularOrderMonitorScreenSteps.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        RegularOrderMonitorScreenSteps.clickServiceDetailsCancelButton();

        RegularOrderMonitorScreenSteps.changePhaseStatus(startPhase, OrderMonitorStatuses.COMPLETED);
        for (MonitorServiceData monitorServiceData : startPhase.getMonitorServicesData())
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);


        OrderMonitorData repairPhase = workOrderData.getOrderMonitorsData().get(1);
        for (MonitorServiceData monitorServiceData : repairPhase.getMonitorServicesData()) {
            RegularOrderMonitorScreenSteps.selectServicePanel(monitorServiceData.getMonitorService());
            RegularOrderMonitorScreenSteps.clickStartService();
            RegularOrderMonitorScreenSteps.setServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);
        }
        RegularOrderMonitorScreenValidations.verifyOrderPhaseStatus(repairPhase, OrderMonitorStatuses.COMPLETED);
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyRefreshPicturesActionForInvoices(String rowID,
                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final int imagesToAdd = 4;
        final int imagesToRemove = 2;

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
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        RegularNotesScreenSteps.addImageNotes(imagesToAdd);
        RegularNotesScreenSteps.saveNotes();
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        RegularNotesScreenSteps.switchToPhotosView();
        RegularNotesScreenSteps.deleteImageNotes(imagesToRemove);
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(imagesToRemove);
        RegularNotesScreenSteps.saveNotes();
        RegularMyInvoicesScreenSteps.refreshPicturesForInvoice(invoiceNumber);
        RegularMyInvoicesScreenSteps.selectInvoiceNotesMenu(invoiceNumber);
        RegularNotesScreenSteps.switchToPhotosView();
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(imagesToRemove);
        RegularNotesScreenSteps.saveNotes();
        RegularMyInvoicesScreenSteps.showPicturesForInvoice(invoiceNumber);
        RegularNotesScreenValidations.verifyNumberOfImagesNotes(imagesToRemove);
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCalculationForServiceBundleServiceFeeBundleDiscount(String rowID,
                                                                              String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String feeServiceName = "Fee Service Oksi";
        final String feeServicePrice = "$55.00";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_FINAL_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        for (BundleServiceData bundleServiceData : workOrderData.getBundleServices()) {
            RegularServicesScreenSteps.selectBundleService(bundleServiceData);
        }

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularSummaryApproveScreenSteps.approveWorkOrder();

        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePrice(invoiceNumber, testCaseData.getInvoiceData().getInvoiceTotal());
        RegularMyInvoicesScreenSteps.selectSendEmailMenuForInvoice(invoiceNumber);
        NadaEMailService nada = new NadaEMailService();
        RegularEmailScreenSteps.sendEmailToAddress(nada.getEmailId());
        RegularNavigationSteps.navigateBackScreen();

        final String invpoicereportfilenname = invoiceNumber + ".pdf";

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invpoicereportfilenname);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfdoc = new File(invpoicereportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
        }

        for (BundleServiceData bundleServiceData : workOrderData.getBundleServices()) {
            Assert.assertTrue(pdftext.contains(bundleServiceData.getBundleServiceName()));
        }
        Assert.assertTrue(pdftext.contains(feeServiceName));
        Assert.assertTrue(pdftext.contains(feeServicePrice));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySplitWorkForTechniciansUnderWO(String rowID,
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
        for (BundleServiceData bundleServiceData : workOrderData.getBundleServices()) {
            RegularServicesScreenSteps.selectBundleService(bundleServiceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForApprove(workOrderNumber);
        RegularSummaryApproveScreenSteps.approveWorkOrder();

        RegularMyWorkOrdersSteps.selectWorkOrderForEdit(workOrderNumber);
        RegularVehicleInfoScreenSteps.selectAdditionalTechnician(workOrderData.getVehicleInfoData().getNewTechnician());
        RegularAlertValidations.acceprAlertAndVerifyAlertMessage(AlertsCaptions.CHANGING_DEFAULT_EMPLOYEES);
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.switchToSelectedServices();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
            RegularServiceDetailsScreenSteps.saveServiceDetails();
            RegularServiceDetailsScreenSteps.saveServiceDetails();
        }
        for (BundleServiceData bundleServiceData : workOrderData.getBundleServices()) {
            RegularSelectedServicesSteps.openSelectedServiceDetails(bundleServiceData.getBundleServiceName());
            RegularServiceDetailsScreenSteps.clickServiceTechniciansIcon();
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getNewTechnician());
            RegularServiceDetailsScreenValidations.verifyServiceTechnicianIsSelected(workOrderData.getVehicleInfoData().getDefaultTechnician());
            RegularServiceDetailsScreenSteps.cancelServiceDetails();
            RegularServiceDetailsScreenSteps.cancelServiceDetails();
        }

        RegularWorkOrdersSteps.cancelCreatingWorkOrder();
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySaveInvoiceAsDraftAndSendEmail(String rowID,
                                                         String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();

        final String feeServiceName = "Fee Service Oksi";
        final String feeServicePrice = "$55.00";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_DRAFT_INVOICE);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        for (BundleServiceData bundleServiceData : workOrderData.getBundleServices()) {
            RegularServicesScreenSteps.selectBundleService(bundleServiceData);
        }

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWizardScreenValidations.verifyScreenSubTotalPrice(workOrderData.getServicesScreen().getScreenPrice());
        RegularWizardScreenValidations.verifyScreenTotalPrice(workOrderData.getServicesScreen().getScreenTotalPrice());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoicesSteps.saveInvoiceAsFinal();
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyInvoicePrice(invoiceNumber, testCaseData.getInvoiceData().getInvoiceTotal());
        RegularMyInvoicesScreenSteps.selectSendEmailMenuForInvoice(invoiceNumber);
        NadaEMailService nada = new NadaEMailService();
        RegularEmailScreenSteps.sendEmailToAddress(nada.getEmailId());

        final String invpoicereportfilenname = invoiceNumber + ".pdf";

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(invoiceNumber, invpoicereportfilenname);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(invoiceNumber);

        File pdfdoc = new File(invpoicereportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            Assert.assertTrue(pdftext.contains(serviceData.getServiceName()));
        }

        for (BundleServiceData bundleServiceData : workOrderData.getBundleServices()) {
            Assert.assertTrue(pdftext.contains(bundleServiceData.getBundleServiceName()));
        }
        Assert.assertTrue(pdftext.contains(feeServiceName));
        Assert.assertTrue(pdftext.contains(feeServicePrice));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testInspectionsMultipleInspectionsApproval(String rowID,
                                                           String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();
        final String companyInspToDecline = "BoBo";

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        for (InspectionData inspectionData : inspectionsData) {
            RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(),
                    UATInspectionTypes.valueOf(inspectionData.getInspectionType()));
            RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
            inspectionData.setInspectionNumber(RegularVehicleInfoScreenSteps.getInspectionNumber());
            if (inspectionData.getInsuranceCompanyData() != null) {
                RegularNavigationSteps.navigateToClaimScreen();
                RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());
            }

            RegularNavigationSteps.navigateToServicesScreen();
            if (inspectionData.getServicesScreen().getMoneyServices() != null) {
                for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
                    RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
                }
                RegularServicesScreenSteps.waitServicesScreenLoad();
            }
            if (inspectionData.getServicesScreen().getPercentageServices() != null) {
                RegularServicesScreenSteps.switchToSelectedServices();
                for (ServiceData serviceData : inspectionData.getServicesScreen().getPercentageServices()) {
                    RegularSelectedServicesSteps.openSelectedServiceDetails(serviceData.getServiceName());
                    RegularServiceDetailsScreenSteps.setServiceDetailsDataAndSave(serviceData);
                }
                RegularSelectedServicesSteps.waitSelectedServicesScreenLoaded();
            }


            if (inspectionData.getInspectionType().equals(UATInspectionTypes.AUTOCREATEWO.toString()))
                RegularWizardScreensSteps.clickSaveButton();
            else
                RegularInspectionsSteps.saveInspectionAsFinal();
        }

        RegularMyInspectionsSteps.clickActionButton();
        for (InspectionData inspectionData : inspectionsData)
            RegularMyInspectionsSteps.selectInspection(inspectionData.getInspectionNumber());
        RegularMyInspectionsSteps.clickActionButton();

        RegularMenuItemsScreenSteps.clickMenuItem(ReconProMenuItems.APPROVE);

        for (InspectionData inspectionData : inspectionsData) {
            RegularCustomersScreenSteps.clickOnCustomer(inspectionData.getWholesailCustomer());
            RegularApproveInspectionScreenActions.selectInspectionForApprove(inspectionData.getInspectionNumber());
            if (!inspectionData.getWholesailCustomer().getCompany().equals(companyInspToDecline)) {
                RegularApproveInspectionScreenActions.clickApproveAllServicesButton();
                RegularApproveInspectionScreenActions.saveApprovedServices();
            } else
                RegularApproveInspectionScreenActions.clickDeclinePopupButton();

            RegularApproveInspectionScreenActions.clickSingnAndDrawSignature();
        }

        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWorkWithServicesUnderAvailableSelectedTabsWhenCreateInspection(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionID = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularServicesScreenSteps.switchToSelectedServices();

        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelected(serviceData.getServiceName(), true);
        }
        RegularSelectedServicesSteps.deleteSelectedService(inspectionData.getServicesScreen().getMoneyServices().get(0).getServiceName());
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getMoneyServices().get(0).getServiceName(), false);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getServicesScreen().getMoneyServices().get(1).getServiceName(), true);

        RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(inspectionData.getInspectionTotalPrice()));

        RegularInspectionsSteps.saveInspectionAsFinal();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testSRVerifyActionsOnCalendarForSRAppointments(String rowID,
                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(serviceRequestData.getWholesailCustomer(), UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(serviceRequestData.getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        final String serviceRequestNumber = RegularServiceRequestSteps.getFirstServiceRequestNumber();
        RegularServiceRequestSteps.clickServiceRequestAppointmentsAction(serviceRequestNumber);
        RegularServiceRequestAppointmentScreenValidations.verifyAppointmentExistsForServiceRequest(true);
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUpdateApplicationFromLastDBVersionForNewOne(String rowID,
                                                                      String description, JSONObject testData) {
        DriverBuilder.getInstance().getAppiumDriver().closeApp();
        DriverBuilder.getInstance().getAppiumDriver().launchApp();
        RegularMainScreenSteps.updateMainDataBase();
        RegularMainScreenSteps.userLogin("Test User", "1111");

        RegularHomeScreenSteps.navigateToStatusScreen();
        RegularHomeScreenSteps.updateMainDataBase();
        RegularMainScreenSteps.userLogin("Test User", "1111");

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySearchForSR(String rowID,
                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        ServiceRequestData serviceRequestData = testCaseData.getServiceRequestData();
        final String serviceRequestSearchParameter = "Not Checked In Team";

        RegularHomeScreenSteps.navigateToServiceRequestScreen();
        RegularServiceRequestSteps.startCreatingServicerequest(serviceRequestData.getWholesailCustomer(), UATServiceRequestTypes.SR_TYPE_ALL_PHASES);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(serviceRequestData.getVihicleInfo());
        RegularServiceRequestSteps.saveServiceRequestWithAppointment();
        RegularServiceRequestAppointmentScreenSteps.setDefaultServiceRequestAppointment();
        final String serviceRequestNumber = RegularServiceRequestSteps.getFirstServiceRequestNumber();

        RegularServiceRequestSteps.searchServiceRequestByNumber(serviceRequestNumber);
        RegularServiceRequestsScreenValidations.verifyServiceRequestPresent(serviceRequestNumber, true);
        RegularServiceRequestSteps.clickSearchButton();
        RegularServiceRequestSteps.clearSerchText();
        RegularServiceRequestSteps.selectSearchParamaters(serviceRequestSearchParameter);
        RegularServiceRequestSteps.saveSearchParamaters();
        RegularServiceRequestsScreenValidations.verifyServiceRequestPresent(serviceRequestNumber, true);
        RegularServiceRequestSteps.clickServiceRequestCheckInAction(serviceRequestNumber);
        RegularServiceRequestsScreenValidations.verifyServiceRequestPresent(serviceRequestNumber, false);
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCalculationForServiceWithPricePolicyVehiclePricePolicyPanelOnPrintOut(String rowID,
                                                                                                String description, JSONObject testData) throws Exception {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        final String zeroPrice = "$0.00";

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        final String inspectionID = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }

        RegularServicesScreenSteps.switchToSelectedServices();

        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularSelectedServicesScreenValidations.verifyServiceIsSelectedWithServicePrice(serviceData, serviceData.getServicePrice2());
        }
        RegularWizardScreenValidations.verifyScreenTotalPrice(PricesCalculations.getPriceRepresentation(inspectionData.getInspectionTotalPrice()));

        RegularInspectionsSteps.saveInspectionAsFinal();


        RegularMyInspectionsSteps.selectSendEmailMenuForInspection(inspectionID);
        NadaEMailService nada = new NadaEMailService();
        RegularEmailScreenSteps.sendEmailToAddress(nada.getEmailId());
        RegularNavigationSteps.navigateBackScreen();

        final String inspectionreportfilenname = inspectionID + ".pdf";

        NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
                .withSubjectAndAttachmentFileName(inspectionID, inspectionreportfilenname);
        Assert.assertTrue(nada.downloadMessageAttachment(searchParametersBuilder), "Can't find invoice: " + inspectionID +
                " in mail box " + nada.getEmailId() + ". At time " +
                LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
        nada.deleteMessageWithSubject(inspectionID);

        File pdfdoc = new File(inspectionreportfilenname);
        String pdftext = PDFReader.getPDFText(pdfdoc);
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            Assert.assertTrue(pdftext.contains(PricesCalculations.getPriceRepresentation(serviceData.getServicePrice())));
        }
        Assert.assertTrue(pdftext.contains(zeroPrice));
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWorkWithIndividualServicesUnderMonitor(String rowID,
                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String location = "All locations";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_MONITOR);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.selectSearchLocation(location);
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);

        for (OrderMonitorData orderMonitorData : workOrderData.getOrderMonitorsData())
            RegularOrderMonitorScreenValidations.verifyOrderPhasePresent(orderMonitorData, true);

        RegularOrderMonitorScreenSteps.selectServicePanel(workOrderData.getServicesScreen().getMoneyServices().get(0));
        RegularOrderMonitorScreenSteps.clickServiceStatusCell();
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.YOU_CANT_CHANGE_STATUSES_OF_SERVICES_FOR_THIS_PHASE);
        RegularOrderMonitorScreenSteps.clickServiceDetailsCancelButton();

        RegularOrderMonitorScreenSteps.changePhaseStatus(workOrderData.getOrderMonitorsData().get(0), OrderMonitorStatuses.COMPLETED);
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorsData().get(0).getMonitorServicesData()) {
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);
        }

        RegularOrderMonitorScreenSteps.selectOrderPhase(workOrderData.getOrderMonitorsData().get(1));
        Assert.assertEquals(Helpers.getAlertTextAndAccept(), AlertsCaptions.YOU_CANT_CHANGE_STATUS_OF_THE_PHASE);

        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorsData().get(1).getMonitorServicesData()) {
            RegularOrderMonitorScreenSteps.selectServicePanel(monitorServiceData.getMonitorService());
            RegularOrderMonitorScreenSteps.clickStartService();
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.ORDERED);
            RegularOrderMonitorScreenSteps.setServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.RECEIVED);
            RegularOrderMonitorScreenSteps.selectServicePanel(monitorServiceData.getMonitorService());
            RegularOrderMonitorScreenValidations.verifyServiceCompletedDatePresent(true);
            RegularOrderMonitorScreenValidations.verifyServiceDurationPresent(true);
            RegularOrderMonitorScreenSteps.clickServiceDetailsCancelButton();
        }
        for (OrderMonitorData orderMonitorData : workOrderData.getOrderMonitorsData())
            RegularOrderMonitorScreenValidations.verifyOrderPhaseStatus(orderMonitorData, OrderMonitorStatuses.COMPLETED);
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMonitorOfPartServices(String rowID,
                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String location = "All locations";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_MONITOR);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.selectSearchLocation(location);
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);

        for (OrderMonitorData orderMonitorData : workOrderData.getOrderMonitorsData())
            RegularOrderMonitorScreenValidations.verifyOrderPhasePresent(orderMonitorData, true);

        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorsData().get(1).getMonitorServicesData()) {
            RegularOrderMonitorScreenSteps.selectServicePanel(monitorServiceData.getMonitorService());
            RegularOrderMonitorScreenSteps.clickStartService();
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.ORDERED);
            RegularOrderMonitorScreenSteps.setServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.RECEIVED);
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.RECEIVED);
            RegularOrderMonitorScreenValidations.verifytartFinishDateLabelPresentForService(monitorServiceData.getMonitorService(), true);
            RegularOrderMonitorScreenValidations.verifyDurationLabelPresentForService(monitorServiceData.getMonitorService(), true);


            RegularOrderMonitorScreenSteps.setServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.REORDERED);
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.REORDERED);

            RegularOrderMonitorScreenSteps.setServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.RECEIVED);
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.RECEIVED);
            RegularOrderMonitorScreenValidations.verifytartFinishDateLabelPresentForService(monitorServiceData.getMonitorService(), true);
            RegularOrderMonitorScreenValidations.verifyDurationLabelPresentForService(monitorServiceData.getMonitorService(), true);
        }
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyLinkPartsAndLaborServices(String rowID,
                                                    String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();

        RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getMoneyServiceData());
        RegularServicesScreenSteps.selectLaborServiceAndSetData(inspectionData.getLaborServiceData());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularServicesScreenSteps.switchToSelectedServices();
        RegularSelectedServicesSteps.openSelectedServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickRemoveServiceButton();
        AlertsValidations.cancelAlertAndValidateAlertMessage(String.format(AlertsCaptions.WOULD_YOU_LIKE_TO_REMOVE_SERVICE.replace("\n", " "),
                inspectionData.getMoneyServiceData().getServiceName()));
        RegularServiceDetailsScreenSteps.cancelServiceDetails();

        RegularSelectedServicesSteps.openSelectedServiceDetails(inspectionData.getLaborServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickRemoveServiceButton();
        AlertsValidations.cancelAlertAndValidateAlertMessage(String.format(AlertsCaptions.WOULD_YOU_LIKE_TO_REMOVE_SERVICE.replace("\n", " "),
                inspectionData.getLaborServiceData().getServiceName()));
        RegularServiceDetailsScreenSteps.cancelServiceDetails();

        RegularSelectedServicesSteps.openSelectedServiceDetails(inspectionData.getMoneyServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickRemoveServiceButton();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.WOULD_YOU_LIKE_TO_REMOVE_SERVICE.replace("\n", " "),
                inspectionData.getMoneyServiceData().getServiceName()));

        RegularSelectedServicesSteps.openSelectedServiceDetails(inspectionData.getLaborServiceData().getServiceName());
        RegularServiceDetailsScreenSteps.clickRemoveServiceButton();
        AlertsValidations.acceptAlertAndValidateAlertMessage(String.format(AlertsCaptions.WOULD_YOU_LIKE_TO_REMOVE_SERVICE.replace("\n", " "),
                inspectionData.getLaborServiceData().getServiceName()));

        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getMoneyServiceData().getServiceName(), false);
        RegularSelectedServicesScreenValidations.verifyServiceIsSelected(inspectionData.getLaborServiceData().getServiceName(), false);
        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCreatingInspectionAndWOWithServiceGroupingNoGroupServicesParts(String rowID,
                                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        for (InspectionData inspectionData : inspectionsData) {
            RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.valueOf(inspectionData.getInspectionType()));
            RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());

            RegularNavigationSteps.navigateToServicesScreen();
            if (inspectionData.getDamagesData() != null) {
                for (DamageData damageData : inspectionData.getDamagesData()) {
                    RegularServicesScreenSteps.selectPanelServiceData(damageData);
                    RegularServicesScreenSteps.clickServiceTypesButton();
                }
            } else if (inspectionData.getServicePanelGroups() != null) {
                for (ServicePanelGroup servicePanelGroup : inspectionData.getServicePanelGroups()) {
                    RegularServicesScreenSteps.selectServicePanelGroupData(servicePanelGroup);
                    RegularServicesScreenSteps.clickVehiclePartsButton();
                }
            } else {
                RegularServicesScreenSteps.selectServiceWithServiceData(inspectionData.getMoneyServiceData());
            }
            RegularWizardScreenValidations.verifyScreenTotalPrice(inspectionData.getInspectionTotalPrice());
            RegularInspectionsSteps.saveInspection();
        }
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyLaborTimesGettingFromDB(String rowID,
                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();

        final String defRate = "$0.00";
        final String defTimeRate = "11.00";
        final String defZeroTimeRate = "0.00";

        RegularHomeScreenSteps.navigateToMyInspectionsScreen();

        RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_APPROVE_MULTISELECT);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();

        for (LaborServiceData laborServiceData : inspectionData.getLaborServices()) {
            RegularServicesScreenSteps.openCustomServiceDetails(laborServiceData.getServiceName());
            RegularServiceDetailsScreenValidations.verifyLaborServiceRateValue(defRate);
            RegularServiceDetailsScreenValidations.verifyLaborServiceTimeValue(defTimeRate);
            RegularServiceDetailsScreenSteps.selectLaborServicePanels(laborServiceData);
            RegularServiceDetailsScreenSteps.setLaborServiceRate(laborServiceData.getLaborServiceRate());
            RegularServiceDetailsScreenSteps.saveServiceDetails();

        }

        RegularServicesScreenSteps.openCustomServiceDetails(inspectionData.getLaborServiceData().getServiceName());
        RegularServiceDetailsScreenValidations.verifyLaborServiceRateValue(defRate);
        RegularServiceDetailsScreenValidations.verifyLaborServiceTimeValue(defZeroTimeRate);
        inspectionData.getLaborServiceData().getPartServiceDataList();
        inspectionData.getLaborServiceData().getPartServiceDataList().forEach((service) -> {
            RegularServiceDetailsScreenSteps.selectLaborPartServiceData(service);
            RegularServiceDetailsScreenSteps.clickSelectedServiceDetailsDoneButton();
        });
        RegularServiceDetailsScreenSteps.setLaborServiceTime(inspectionData.getLaborServiceData().getLaborServiceTime());
        RegularServiceDetailsScreenSteps.setLaborServiceRate(inspectionData.getLaborServiceData().getLaborServiceRate());
        RegularServiceDetailsScreenSteps.saveServiceDetails();

        RegularInspectionsSteps.cancelCreatingInspection();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyPhaseEnforcementUnderMonitor(String rowID,
                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String location = "All locations";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_MONITOR_DELAY_RO_START);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        RegularServicesScreenSteps.selectServices(workOrderData.getServicesScreen().getMoneyServices());

        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularNavigationSteps.navigateToOrderSummaryScreen();
        RegularOrderSummaryScreenSteps.setTotalSale(workOrderData.getWorkOrderTotalSale());
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.selectSearchLocation(location);
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);

        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularOrderMonitorScreenValidations.verifyServiceStatus(serviceData, OrderMonitorServiceStatuses.QUEUED);
        }

        RegularOrderMonitorScreenValidations.verifyStartOrderButtonPresent(true);
        RegularOrderMonitorScreenSteps.startWorkOrder();

        final OrderMonitorData orderMonitorData1 = workOrderData.getOrderMonitorsData().get(0);
        RegularOrderMonitorScreenValidations.verifyOrderPhaseStatus(orderMonitorData1, OrderMonitorStatuses.ACTIVE);
        for (MonitorServiceData monitorServiceData : orderMonitorData1.getMonitorServicesData()) {
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.ACTIVE);
        }

        final OrderMonitorData orderMonitorData2 = workOrderData.getOrderMonitorsData().get(1);
        RegularOrderMonitorScreenValidations.verifyOrderPhaseStatus(orderMonitorData2, OrderMonitorStatuses.QUEUED);
        for (MonitorServiceData monitorServiceData : orderMonitorData2.getMonitorServicesData()) {
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.QUEUED);
        }

        RegularOrderMonitorScreenSteps.selectServicePanel(orderMonitorData1.getMonitorServicesData().get(0).getMonitorService());
        RegularOrderMonitorScreenSteps.clickServiceStatusCell();
        AlertsValidations.acceptAlertAndValidateAlertMessage(AlertsCaptions.ALERT_YOU_CANNOT_CHANGE_STATUS_OF_SERVICE_FOR_THIS_PHASE);
        RegularOrderMonitorScreenSteps.clickServiceDetailsCancelButton();

        RegularOrderMonitorScreenSteps.changePhaseStatus(orderMonitorData1, OrderMonitorStatuses.COMPLETED);
        RegularOrderMonitorScreenValidations.verifyOrderPhaseStatus(orderMonitorData1, OrderMonitorStatuses.COMPLETED);
        for (MonitorServiceData monitorServiceData : orderMonitorData1.getMonitorServicesData()) {
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.COMPLETED);
        }

        RegularOrderMonitorScreenValidations.verifyOrderPhaseStatus(orderMonitorData2, OrderMonitorStatuses.QUEUED);
        for (MonitorServiceData monitorServiceData : orderMonitorData2.getMonitorServicesData()) {
            RegularOrderMonitorScreenValidations.verifyServiceStatus(monitorServiceData.getMonitorService(), OrderMonitorServiceStatuses.QUEUED);
        }

        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAssignTechUnderMonitor(String rowID,
                                                String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String location = "All locations";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_MONITOR);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.selectSearchLocation(location);
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        for (OrderMonitorData orderMonitorData : workOrderData.getOrderMonitorsData()) {
            RegularOrderMonitorScreenSteps.assignTechnicianToOrderPhase(orderMonitorData, orderMonitorData.getNewTechnician());

            for (MonitorServiceData monitorServiceData : orderMonitorData.getMonitorServicesData()) {
                RegularOrderMonitorScreenSteps.selectServicePanel(monitorServiceData.getMonitorService());
                RegularOrderMonitorScreenValidations.verifyServiceTechnicianValue(orderMonitorData.getNewTechnician());
                RegularOrderMonitorScreenSteps.clickServiceDetailsCancelButton();
            }
        }
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAssignVendorTeamForServicesUnderMonitor(String rowID,
                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        WorkOrderData workOrderData = testCaseData.getWorkOrderData();
        final String allLocations = "All locations";
        final String defLocation = "Default team";
        final String location = "location zayats";

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.startCreatingWorkOrder(workOrderData.getWholesailCustomer(), UATWorkOrderTypes.WO_MONITOR);
        RegularVehicleInfoScreenSteps.setVehicleInfoData(workOrderData.getVehicleInfoData());
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : workOrderData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularWorkOrdersSteps.saveWorkOrder();

        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.selectSearchLocation(allLocations);
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData())
            RegularOrderMonitorScreenValidations.verifyServiceTeamValue(monitorServiceData.getMonitorService(), defLocation);

        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();

        webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

        BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
        loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
        BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
        backOfficeHeaderPanel.clickMonitorLink();
        MonitorWebPage monitorWebPage = new MonitorWebPage(webdriver);
        monitorWebPage.clickRepairOrdersLink();

        RepairOrdersWebPage repairOrdersWebPage = new RepairOrdersWebPage(webdriver);
        repairOrdersWebPage.selectSearchLocation(location);
        repairOrdersWebPage.setSearchWoNumber(workOrderNumber);
        repairOrdersWebPage.clickFindButton();
        repairOrdersWebPage.clickOnWorkOrderLinkInTable(workOrderNumber);
        VendorOrderServicesWebPage vendorOrderServicesWebPage = new VendorOrderServicesWebPage(webdriver);
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData()) {
            Assert.assertEquals(vendorOrderServicesWebPage.getRepairOrderServiceVendor(monitorServiceData.getMonitorService().getServiceName()), defLocation);
            vendorOrderServicesWebPage.changeRepairOrderServiceVendor(monitorServiceData.getMonitorService().getServiceName(), monitorServiceData.getServiceVendor());
            vendorOrderServicesWebPage.waitABit(3000);
        }
        DriverBuilder.getInstance().getDriver().quit();

        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();
        RegularMyWorkOrdersSteps.switchToTeamView();
        RegularTeamWorkOrdersSteps.openTeamWorkOrderMonitor(workOrderNumber);
        for (MonitorServiceData monitorServiceData : workOrderData.getOrderMonitorData().getMonitorServicesData())
            RegularOrderMonitorScreenValidations.verifyServiceTeamValue(monitorServiceData.getMonitorService(), monitorServiceData.getServiceVendor());
        RegularNavigationSteps.navigateBackScreen();
        RegularNavigationSteps.navigateBackScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyCalculationAndCorrectDisplayOfArbitrationData(String rowID,
                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        InspectionData inspectionData = testCaseData.getInspectionData();
        final List<Integer> arbDays = new ArrayList<>();
        arbDays.add(10);
        arbDays.add(5);
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM dd, yyyy");

        for (Integer arbitrationDaysValue : arbDays) {
            webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
            WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);

            BackOfficeLoginWebPage loginWebPage = new BackOfficeLoginWebPage(webdriver);
            loginWebPage.userLogin(ReconProIOSStageInfo.getInstance().getUserStageUserName(),
                    ReconProIOSStageInfo.getInstance().getUserStageUserPassword());
            BackOfficeHeaderPanel backOfficeHeaderPanel = new BackOfficeHeaderPanel(webdriver);
            backOfficeHeaderPanel.clickCompanyLink();
            CompanyWebPage companyWebPage = new CompanyWebPage(webdriver);

            companyWebPage.clickInspectionTypesLink();
            InspectionTypesWebPage inspectionTypesWebPage = new InspectionTypesWebPage(webdriver);
            inspectionTypesWebPage.clickEditInspectionType(UATInspectionTypes.INSP_ARBITRATION_DATE.getInspectionTypeName());
            NewInspectionTypeDialogWebPage newInspectionTypeDialogWebPage = new NewInspectionTypeDialogWebPage(webdriver);
            newInspectionTypeDialogWebPage.selectOtherTab();
            newInspectionTypeDialogWebPage.setArbitrationWindowValue(arbitrationDaysValue.toString());
            newInspectionTypeDialogWebPage.clickOKButton();
            BaseUtils.waitABit(2000);
            DriverBuilder.getInstance().getDriver().quit();

            RegularHomeScreenSteps.navigateToStatusScreen();
            RegularStatusScreenSteps.updateMainDataBase(testuser);

            RegularHomeScreenSteps.navigateToMyInspectionsScreen();
            RegularMyInspectionsSteps.startCreatingInspection(inspectionData.getWholesailCustomer(), UATInspectionTypes.INSP_ARBITRATION_DATE);
            RegularVehicleInfoScreenSteps.setVehicleInfoData(inspectionData.getVehicleInfo());

            LocalDate vehicleDate = LocalDate.parse(RegularVehicleInfoScreenSteps.getDateValue(), df);
            LocalDate vehicleArbDate = LocalDate.parse(RegularVehicleInfoScreenSteps.getArbitrationDateValue(), df);
            Period period = Period.between(vehicleDate, vehicleArbDate);
            Assert.assertEquals(Integer.valueOf(period.getDays()), arbitrationDaysValue);

            RegularInspectionsSteps.cancelCreatingInspection();
            NavigationSteps.navigateBackScreen();
        }
    }
}

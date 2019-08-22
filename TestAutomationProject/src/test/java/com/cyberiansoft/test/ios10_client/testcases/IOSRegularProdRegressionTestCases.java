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
import com.cyberiansoft.test.ios10_client.config.ReconProIOSStageInfo;
import com.cyberiansoft.test.ios10_client.data.IOSReconProTestCasesDataPaths;
import com.cyberiansoft.test.ios10_client.enums.ReconProMenuItems;
import com.cyberiansoft.test.ios10_client.regularclientsteps.*;
import com.cyberiansoft.test.ios10_client.regularvalidations.*;
import com.cyberiansoft.test.ios10_client.templatepatterns.DeviceRegistrator;
import com.cyberiansoft.test.ios10_client.types.inspectionstypes.UATInspectionTypes;
import com.cyberiansoft.test.ios10_client.types.invoicestypes.UATInvoiceTypes;
import com.cyberiansoft.test.ios10_client.types.servicerequeststypes.UATServiceRequestTypes;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.UATWorkOrderTypes;
import com.cyberiansoft.test.ios10_client.utils.iOSInternalProjectConstants;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IOSRegularProdRegressionTestCases extends ReconProBaseTestCase {

    private List<String> workOrdersForInvoice = new ArrayList<>();

    @BeforeClass
    public void setUpSuite() {
        JSONDataProvider.dataFile = IOSReconProTestCasesDataPaths.getInstance().getProdRegressionSuiteTestCasesDataPath();
        mobilePlatform = MobilePlatform.IOS_REGULAR;
        initTestUser(iOSInternalProjectConstants.USERSIMPLE_LOGIN, iOSInternalProjectConstants.USER_PASSWORD);
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
        RegularApproveInspectionScreenActions.clickApproveAllServicessButton();
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

        RegularMyWorkOrdersSteps.clickCopyVehicleMenu(workOrdersForInvoice.get(0));
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

        RegularMyWorkOrdersSteps.clickCopyServicesMenu(workOrdersForInvoice.get(0));
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
        OperationsWebPage operationsWebPage = backOfficeHeaderPanel.clickOperationsLink();
        InvoicesWebPage invoicesWebPage = operationsWebPage.clickInvoicesLink();
        invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
        invoicesWebPage.clickFindButton();
        String mainWindowHandle = webdriver.getWindowHandle();
        InvoicePaymentsTabWebPage invoicePaymentsTabWebPage = invoicesWebPage.clickInvoicePayments(invoiceNumber);
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
        invoicePaymentsTabWebPage = invoicesWebPage.clickInvoicePayments(invoiceNumber);
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
        CompanyWebPage companyWebPage = backOfficeHeaderPanel.clickCompanyLink();
        ClientsWebPage clientsWebPage = companyWebPage.clickClientsLink();

        //clientsWebPage.deleteUserViaSearch(inspectionData.getInspectionRetailCustomer().getFullName());

        DriverBuilder.getInstance().getDriver().quit();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testCreateInspectionWOInvoiceForCreatedRetailCustomer(String rowID,
                                               String description, JSONObject testData) {

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
        final String inspectionID = RegularVehicleInfoScreenSteps.getInspectionNumber();
        RegularNavigationSteps.navigateToClaimScreen();
        RegularClaimScreenSteps.setClaimData(inspectionData.getInsuranceCompanyData());

        RegularNavigationSteps.navigateToServicesScreen();
        for (ServiceData serviceData : inspectionData.getServicesScreen().getMoneyServices()) {
            RegularServicesScreenSteps.selectServiceWithServiceData(serviceData);
        }
        RegularServicesScreenSteps.waitServicesScreenLoad();
        RegularInspectionsSteps.saveInspectionAsFinal();

        RegularMyInspectionsSteps.selectInspectionForApprovaViaAction(inspectionID);
        RegularApproveInspectionScreenActions.clickApproveAllServicessButton();
        RegularApproveInspectionScreenActions.saveApprovedServices();
        RegularApproveInspectionScreenActions.clickSingnAndDrawSignature();

        RegularMyInspectionsSteps.createWorkOrderFromInspection(inspectionID, UATWorkOrderTypes.WO_FINAL_INVOICE);
        final String workOrderNumber = RegularVehicleInfoScreenSteps.getWorkOrderNumber();
        RegularWizardScreensSteps.clickSaveButton();
        RegularMyInspectionsSteps.waitMyInspectionsScreenLoaded();
        RegularNavigationSteps.navigateBackScreen();
        RegularHomeScreenSteps.navigateToMyWorkOrdersScreen();

        RegularMyWorkOrdersSteps.clickCreateInvoiceIconForWO(workOrderNumber);
        RegularMyWorkOrdersSteps.clickCreateInvoiceIconAndSelectInvoiceType(UATInvoiceTypes.INVOICE_TEST_CUSTOM1_NEW);
        RegularInvoiceInfoScreenSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = RegularInvoiceInfoScreenSteps.getInvoiceNumber();
        RegularInvoiceInfoScreenSteps.saveInvoiceAsFinal();
        RegularMyWorkOrdersScreenValidations.verifyWorkOrderPresent(workOrderNumber, false);
        RegularNavigationSteps.navigateBackScreen();

        RegularHomeScreenSteps.navigateToMyInvoicesScreen();
        RegularMyInvoicesScreenValidations.verifyWorkOrderPresent(invoiceNumber, true);
        RegularNavigationSteps.navigateBackScreen();

    }
}

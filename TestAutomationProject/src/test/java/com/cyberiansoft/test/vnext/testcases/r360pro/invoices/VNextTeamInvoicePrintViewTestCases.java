package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceSteps;
import com.cyberiansoft.test.vnext.steps.invoices.VNextInvoiceViewScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.invoices.VNextInvoiceViewScreenValidations;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInvoicePrintViewTestCases extends BaseTestClass {

    private static final String PRECONDITIONS_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360pro/invoices/team-invoice-print-view-common-testcases-data.json";
    private String invoiceNumber;
    private TestCaseData testCaseData;

    @BeforeClass(description = "Team Invoice Print View Test Cases")
    public void settingUp() throws Exception {

        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicePrintViewTestCasesDataPath();
        testCaseData = JSonDataParser.getTestDataFromJson(JSONDataProvider.extractData_JSON(PRECONDITIONS_FILE), TestCaseData.class);

        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExist(testcustomer);
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.startInvoiceCreation();
        invoiceNumber = createWorkOrderWithInvoice(testCaseData.getWorkOrderData());
        TopScreenPanelSteps.searchData(invoiceNumber);
        TopScreenPanelSteps.cancelSearch();
        InvoiceSteps.approveInvoice(invoiceNumber);
        InvoiceSteps.viewInvoice(invoiceNumber);
        VNextInvoiceViewScreenSteps.switchToPrintViewScreenFrame();
    }

    @AfterClass()
    public void goToTheHomeScreen() {

        VNextInvoiceViewScreenSteps.switchToDefaultContent();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.resetSearch();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeCorrectCustomerSignature(String rowID, String description, JSONObject testData) {

        VNextInvoiceViewScreenValidations.verifySignatureIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeCorrectServiceAndVehicleInfo(String rowID, String description, JSONObject testData) {

        VNextInvoiceViewScreenValidations.verifyServiceIsDisplayed(testCaseData.getWorkOrderData().getServicesList().get(0).getServiceName());
        VNextInvoiceViewScreenValidations.verifyServiceIsDisplayed(testCaseData.getWorkOrderData().getServicesList().get(1).getServiceName());
        VNextInvoiceViewScreenValidations.verifyVehicleInfoIsDisplayed(testCaseData.getWorkOrderData().getVehicleInfoData());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeCorrectCustomerInfo(String rowID, String description, JSONObject testData) {

        VNextInvoiceViewScreenValidations.verifyCustomerInfoIsDisplayed(testcustomer.getFullName(), "US");
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeCorrectInvoiceDate(String rowID, String description, JSONObject testData) {

        VNextInvoiceViewScreenValidations.verifyInvoiceDateIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeInvoicePrintViewScreen(String rowID, String description, JSONObject testData) {

        VNextInvoiceViewScreenValidations.verifyInvoicePrintViewScreenIsDisplayed();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeCorrectInvoiceNumber(String rowID, String description, JSONObject testData) {

        VNextInvoiceViewScreenValidations.verifyInvoiceNumberIsDisplayed(invoiceNumber);
    }

    public String createWorkOrderWithInvoice(WorkOrderData woData) {

        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, woData);
        if (woData.getServicesList() != null) {
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            AvailableServicesScreenSteps.selectServices(woData.getServicesList());
        }
        TopScreenPanelSteps.goToTheNextScreen();
        TopScreenPanelSteps.goToTheNextScreen();
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR);
        String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        return invoiceNumber;
    }
}

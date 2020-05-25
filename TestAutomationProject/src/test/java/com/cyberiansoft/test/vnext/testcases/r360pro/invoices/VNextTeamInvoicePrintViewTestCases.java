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
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.invoices.VNextInvoiceViewScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInvoicePrintViewTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Invoice Print View Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicePrintViewTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserSeeCorrectCustomerSignatureOnInvoicePrinting(String rowID, String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        HomeScreenSteps.startInvoiceCreation();
        final String invoiceNumber = createWorkOrderWithInvoice(testCaseData.getWorkOrderData());
        TopScreenPanelSteps.searchData(invoiceNumber);
        TopScreenPanelSteps.cancelSearch();
        InvoiceSteps.approveInvoice(invoiceNumber);
        InvoiceSteps.viewInvoice(invoiceNumber);
        VNextInvoiceViewScreenValidations.verifySignatureIsDisplayed();
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.resetSearch();
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

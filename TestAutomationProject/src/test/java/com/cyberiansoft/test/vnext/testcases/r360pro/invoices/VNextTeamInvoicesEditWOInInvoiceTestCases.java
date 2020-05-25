package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InformationDialogValidations;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnext.validations.invoices.InvoiceInfoScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamInvoicesEditWOInInvoiceTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Invoice Editing Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicesEditWOInvoiceTestCasesDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditWOInDraftInvoice(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getMoneyServiceData());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();

        WorkOrderSteps.createInvoiceFromWorkOrder(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.clickOnWorkOrder(workOrderNumber);
        WizardScreenSteps.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String message = informationDialog.clickInformationDialogYesButtonAndGetMessage();
        Assert.assertEquals(message, VNextAlertMessages.CANCEL_ETING_WORK_ORDER);
        InvoiceSteps.saveInvoiceAsFinal();
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWOTotalAmountChangedIfUserAddServiceToWOInDraftInvoice(String rowID,
                                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getMoneyServiceData());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();

        WorkOrderSteps.createInvoiceFromWorkOrder(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoScreenValidations.validateInvoiceTotal(testCaseData.getWorkOrderData().getWorkOrderPrice());
        InvoiceInfoSteps.clickOnWorkOrder(workOrderNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getServiceData());
        WorkOrderSteps.trySaveWorkOrder();

        InvoiceInfoScreenValidations.validateInvoiceTotal(testCaseData.getInvoiceData().getInvoiceTotal());
        InvoiceInfoSteps.clickOnWorkOrder(workOrderNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.switchToSelectedService();
        ListServicesValidations.verifyServiceSelected(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName(), true);
        ListServicesValidations.verifyServiceSelected(testCaseData.getWorkOrderData().getServiceData().getServiceName(), true);
        InspectionSteps.trySaveInspection();
        InvoiceSteps.saveInvoiceAsFinal();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddWOToDraftInvoiceAndEditThisWO(String rowID,
                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrders = new ArrayList<>();
        final int workOrdersToCreate = 2;

        for (int i = 0; i < workOrdersToCreate; i++) {
            workOrders.add(WorkOrderSteps.createSimpleWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData()));
            ScreenNavigationSteps.pressBackButton();
        }

        HomeScreenSteps.openWorkOrders();
        WorkOrderSteps.createInvoiceFromWorkOrder(workOrders.get(0));
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.addWorkOrderToInvoice(workOrders.get(1));
        workOrders.forEach(workOrderId -> InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderId, true));

        WizardScreenSteps.clickCancelMenuItem();
        InformationDialogValidations.clickYesAndVerifyMessage(VNextAlertMessages.CANCEL_ETING_INVOICE);
        ScreenNavigationSteps.pressBackButton();
    }

}

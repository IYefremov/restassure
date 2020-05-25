package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.WorkOrdersScreenValidations;
import com.cyberiansoft.test.vnext.validations.customers.CustomersScreenValidation;
import com.cyberiansoft.test.vnext.validations.invoices.InvoicesScreenValidations;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class VNextTeamWorkOrdersChangeCustomerTestCases extends BaseTestClass {

    final RetailCustomer testCustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    final RetailCustomer testCustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Work Orders Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersChangeCustomerTestCasesDataPath();
        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExist(testCustomer1);
        CustomerServiceSteps.createCustomerIfNotExist(testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerIfAllowClientChangingEqualsON(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.changeCustomer(woNumber, testCustomer2);
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantChangeCustomerIfAllowClientChangingEqualsOFF(String rowID,
                                                                               String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR2, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(woNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CHANGE_CUSTOMER, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyIfChangedCustomerIsSavedAfterEditWO(String rowID,
                                                              String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrderAsDraft();
        WorkOrderSteps.changeCustomer(woNumber, testCustomer2);
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        BaseUtils.waitABit(1000 * 30);
        WorkOrderSteps.openMenu(woNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        //HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getServiceData());
        WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        WorkOrdersScreenValidations.validateWorkOrderPriceValue(woNumber, workOrderData.getWorkOrderPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantCreateNewCustomerOnChangeCustomerScreen(String rowID,
                                                                          String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(woNumber);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_CUSTOMER);
        CustomersScreenValidation.validateAddCustomerButtonDisplayed(false);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeRetailCustomerToWholesaleForWO(String rowID,
                                                                      String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        WholesailCustomer testWholesaleCustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json"), WholesailCustomer.class);
        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.changeCustomer(woNumber, testWholesaleCustomer);
        WorkOrdersScreenValidations.validateWorkOrderExists(woNumber, false);
        WorkOrderSteps.switchToTeamWorkOrdersView();
        SearchSteps.textSearch(woNumber);
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testWholesaleCustomer);
        WorkOrderSteps.switchToMyWorkOrdersView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSelectCustomerUsingSearch(String rowID,
                                                           String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.changeCustomer(woNumber, testCustomer2);
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyInvoiceCreatedWithChangedCustomer(String rowID,
                                                            String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.changeCustomer(woNumber, testCustomer2);
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        WorkOrderSteps.openMenu(woNumber);
        MenuSteps.selectMenuItem(MenuItems.CREATE_INVOICE);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoicesScreenValidations.validateInvoiceCustomer(invoiceNumber, testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerForTeamWO(String rowID,
                                                         String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateTeamWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.switchToMyWorkOrdersView();
        WorkOrderSteps.switchToTeamWorkOrdersView();
        WorkOrderSteps.changeCustomer(woNumber, testCustomer2);

        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(30 * 1000);
        HomeScreenSteps.openWorkOrders();
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        WorkOrderSteps.switchToMyWorkOrdersView();
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateCustomerAfterChangingCustomerForWO(String rowID,
                                                                          String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.changeCustomer(woNumber, testCustomer2);
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(woNumber, testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }
}
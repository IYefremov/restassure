package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InformationDialogValidations;
import com.cyberiansoft.test.vnext.validations.InvoiceInfoScreenValidations;
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

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditWOInDraftInvoice(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();

        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        WizardScreenSteps.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        String message = informationDialog.clickInformationDialogYesButtonAndGetMessage();
        Assert.assertEquals(message, VNextAlertMessages.CANCEL_ETING_WORK_ORDER);
        InvoiceSteps.saveInvoiceAsFinal();
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyWOTotalAmountChangedIfUserAddServiceToWOInDraftInvoice(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
        final String workOrderNumber = WorkOrderSteps.saveWorkOrder();

        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        InvoiceInfoScreenValidations.validateInvoiceTotal(testCaseData.getWorkOrderData().getWorkOrderPrice());
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        WorkOrderSteps.trySaveWorkOrder();

        invoiceInfoScreen = new VNextInvoiceInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        InvoiceInfoScreenValidations.validateInvoiceTotal(testCaseData.getInvoiceData().getInvoiceTotal());
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName()));
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(testCaseData.getWorkOrderData().getServiceData().getServiceName()));
        InspectionSteps.trySaveInspection();
        InvoiceSteps.saveInvoiceAsFinal();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanAddWOToDraftInvoiceAndEditThisWO(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrders = new ArrayList<>();
        final int workOrdersToCreate = 2;

        for (int i = 0; i < workOrdersToCreate; i++ ) {
            workOrders.add(WorkOrderSteps.createSimpleWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData()));
            VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
            workOrdersScreen.clickBackButton();
        }

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrders.get(0));
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        VNextSelectWorkOrdersScreen selectWorkOrdersScreen = invoiceInfoScreen.clickAddWorkOrdersButton();
        selectWorkOrdersScreen.selectWorkOrder(workOrders.get(1));
        selectWorkOrdersScreen.clickAddWorkOrders();
        workOrders.forEach(workOrderId -> {
            InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderId, true);
        });

        WizardScreenSteps.clickCancelMenuItem();
        InformationDialogValidations.clickYesAndVerifyMessage(VNextAlertMessages.CANCEL_ETING_WORK_ORDER);
        InvoiceSteps.saveInvoiceAsFinal();
        ScreenNavigationSteps.pressBackButton();
    }

}

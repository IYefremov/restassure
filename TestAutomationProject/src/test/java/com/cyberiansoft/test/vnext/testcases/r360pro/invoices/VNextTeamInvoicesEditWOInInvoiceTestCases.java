package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
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
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamInvoicesEditWOInInvoiceTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Invoice Editing Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicesEditWOInvoiceTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditWOInDraftInvoice(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(testCaseData.getWorkOrderData().getVinNumber());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
        workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();

        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        String message = informationDialog.clickInformationDialogYesButtonAndGetMessage();
        Assert.assertEquals(message, VNextAlertMessages.CANCEL_ETING_WORK_ORDER);
        invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.saveInvoiceAsFinal();
        invoicesScreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyWOTotalAmountChangedIfUserAddServiceToWOInDraftInvoice(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(testCaseData.getWorkOrderData().getVinNumber());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
        workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();

        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        Assert.assertEquals(invoiceInfoScreen.getInvoiceTotalAmount(), testCaseData.getWorkOrderData().getWorkOrderPrice());
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        availableServicesScreen.clickSaveWorkOrderMenuButton();

        invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(invoiceInfoScreen.getInvoiceTotalAmount(), testCaseData.getInvoiceData().getInvoiceTotal());
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName()));
        Assert.assertTrue(selectedServicesScreen.isServiceSelected(testCaseData.getWorkOrderData().getServiceData().getServiceName()));
        selectedServicesScreen.clickSaveWorkOrderMenuButton();
        invoiceInfoScreen.saveInvoiceAsFinal();
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanAddWOToDraftInvoiceAndEditThisWO(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrders = new ArrayList<>();
        final int workOrdersToCreate = 2;

        for (int i = 0; i < workOrdersToCreate; i++ ) {
            workOrders.add(WorkOrderSteps.createSimpleWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR));
            VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
            workOrdersScreen.clickBackButton();
        }

        VNextHomeScreen homeScreen = new VNextHomeScreen();
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrders.get(0));
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        VNextSelectWorkOrdersScreen selectWorkOrdersScreen = invoiceInfoScreen.clickAddWorkOrdersButton();
        selectWorkOrdersScreen.selectWorkOrder(workOrders.get(1));
        selectWorkOrdersScreen.clickAddWorkOrders();
        for (String workOrderNumber : workOrders)
            Assert.assertTrue(invoiceInfoScreen.isWorkOrderSelectedForInvoice(workOrderNumber));

        VNextVehicleInfoScreen vehicleInfoScreen = invoiceInfoScreen.clickOnWorkOrder(workOrders.get(1));
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        String message = informationDialog.clickInformationDialogYesButtonAndGetMessage();
        Assert.assertEquals(message, VNextAlertMessages.CANCEL_ETING_WORK_ORDER);
        invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.saveInvoiceAsFinal();
        invoicesScreen.clickBackButton();

    }

}

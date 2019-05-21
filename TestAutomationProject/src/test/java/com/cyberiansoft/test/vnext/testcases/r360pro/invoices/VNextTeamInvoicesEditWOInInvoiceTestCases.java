package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInvoicesEditWOInInvoiceTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-invoices-edit-wo-invoice-testcases-data.json";

    @BeforeClass(description = "Team Invoice Editing Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditWOInDraftInvoice(String rowID,
                                                          String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workOrdersScreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(invoiceData.getWorkOrderData().getVinNumber());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(invoiceData.getWorkOrderData().getMoneyServiceName());
        workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();

        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        String message = informationdlg.clickInformationDialogYesButtonAndGetMessage();
        Assert.assertEquals(message, VNextAlertMessages.CANCEL_ETING_WORK_ORDER);
        invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.saveInvoiceAsFinal();
        invoicesScreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyWOTotalAmountChangedIfUserAddServiceToWOInDraftInvoice(String rowID,
                                                      String description, JSONObject testData) {

        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workOrdersScreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(invoiceData.getWorkOrderData().getVinNumber());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(invoiceData.getWorkOrderData().getMoneyServiceName());
        workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();

        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesList = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesList.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        Assert.assertEquals(invoiceInfoScreen.getInvoiceTotalAmount(), invoiceData.getWorkOrderData().getWorkOrderPrice());
        invoiceInfoScreen.clickOnWorkOrder(workOrderNumber);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(invoiceData.getWorkOrderData().getServiceName());
        availableServicesScreen.clickSaveWorkOrderMenuButton();

        invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(invoiceInfoScreen.getInvoiceTotalAmount(), invoiceData.getInvoiceData().getInvoiceTotal());
        invoiceInfoScreen.saveInvoiceAsFinal();
        invoicesScreen.clickBackButton();

    }

}

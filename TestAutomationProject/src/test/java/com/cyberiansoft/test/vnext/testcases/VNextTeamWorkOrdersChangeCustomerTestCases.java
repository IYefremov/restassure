package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextChangeCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class VNextTeamWorkOrdersChangeCustomerTestCases extends BaseTestCaseTeamEditionRegistration {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-workorders-change-customer-testcases-data.json";

    final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Work Orders Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        customersscreen.switchToRetailMode();
        if (!customersscreen.isCustomerExists(testcustomer1)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer1);
            customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        }
        if (!customersscreen.isCustomerExists(testcustomer2)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer2);
            customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        }
        customersscreen.clickBackButton();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerIfAllowClientChangingEqualsON(String rowID,
                                                                                          String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantChangeCustomerIfAllowClientChangingEqualsOFF(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        Assert.assertFalse(workOrdersMenuScreen.isChangeCustomerMenuPresent());
        workOrdersMenuScreen.clickCloseWorkOrdersMenuButton();
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyIfChangedCustomerIsSavedAfterEditWO(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderAsDraft();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableservicesscreen.selectService(workOrderData.getServiceName());
        workordersscreen = availableservicesscreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        Assert.assertEquals(workordersscreen.getWorkOrderPriceValue(woNumber), workOrderData.getWorkOrderPrice());

        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantCreateNewCustomerOnChangeCustomerScreen(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        VNextChangeCustomerScreen changeCustomerScreen = workOrdersMenuScreen.clickChangeCustomerMenuItem();
        Assert.assertFalse(changeCustomerScreen.isAddCustomerButtonDisplayed());
        changeCustomerScreen.clickBackButton();
        workordersscreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeRetailCustomerToWholesaleForWO(String rowID,
                                                                             String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        WholesailCustomer testwholesailcustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json"), WholesailCustomer.class);
        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerToWholesailForWorkOrder(woNumber, testwholesailcustomer);
        Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber), "Can't find work order: " + woNumber);
        workordersscreen.switchToTeamWorkordersView();
        workordersscreen.searchWorkOrderByFreeText(woNumber);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testwholesailcustomer.getFullName());
        workordersscreen.switchToMyWorkordersView();
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSelectCustomerUsingSearch(String rowID,
                                                                      String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrderViaSearch(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyInvoiceCreatedWithChangedCustomer(String rowID,
                                                           String description, JSONObject testData) throws IOException {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        VNextInvoiceTypesList invoiceTypesScreen = workOrdersMenuScreen.clickCreateInvoiceMenuItem();
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesscreen.getInvoiceCustomerValue(invoiceNumber), testcustomer2.getFullName());
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerForTeamWO(String rowID,
                                                            String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        workordersscreen.switchToMyWorkordersView();
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());

        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCreateCustomerAfterChangingCustomerForWO(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        wotypes.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        workordersscreen.clickBackButton();
    }
}
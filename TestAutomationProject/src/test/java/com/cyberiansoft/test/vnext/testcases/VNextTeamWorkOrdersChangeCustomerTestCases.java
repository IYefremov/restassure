package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextTeamWorkOrdersChangeCustomerTestCases extends BaseTestCaseTeamEditionRegistration {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-workorders-change-customer-testcases-data.json";

    final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Work Orders Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        if (!customersscreen.isCustomerExists(testcustomer1)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer1);
            customersscreen = new VNextCustomersScreen(appiumdriver);
        }
        if (!customersscreen.isCustomerExists(testcustomer2)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer2);
            customersscreen = new VNextCustomersScreen(appiumdriver);
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

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        VNextInspectionsMenuScreen inspectionsMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        Assert.assertFalse(inspectionsMenuScreen.isChangeCustomerMenuPresent());
        inspectionsMenuScreen.clickCloseInspectionMenuButton();
        workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyIfChangedCustomerIsSavedAfterEditWO(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        VNextInspectionsMenuScreen inspectionsMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        vehicleinfoscreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        vehicleinfoscreen.changeScreen("Services");
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.selectService(workOrderData.getServiceName());
        workordersscreen = inpsctionservicesscreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        Assert.assertEquals(workordersscreen.getWorkOrderPriceValue(woNumber), workOrderData.getWorkOrderPrice());

        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantCreateNewCustomerOnChangeCustomerScreen(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        VNextInspectionsMenuScreen inspectionsMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
        Assert.assertFalse(customersscreen.isAddCustomerButtonExists());
        customersscreen.clickBackButton();
        workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeRetailCustomerToWholesaleForWO(String rowID,
                                                                             String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        WholesailCustomer testwholesailcustomer = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json", WholesailCustomer.class);
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerToWholesailForWorkOrder(woNumber, testwholesailcustomer);
        Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
        workordersscreen.switchToTeamWorkordersView();
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testwholesailcustomer.getFullName());
        workordersscreen.switchToMyWorkordersView();
        workordersscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSelectCustomerUsingSearch(String rowID,
                                                                      String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        WholesailCustomer testwholesailcustomer = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json", WholesailCustomer.class);
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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

        WholesailCustomer testwholesailcustomer = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json", WholesailCustomer.class);
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(invoice.getWorkOrderData().getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        VNextInspectionsMenuScreen inspectionsMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
        VNextInspectionTypesList inspectionTypesList = inspectionsMenuScreen.clickCreateInvoiceMenuItem();
        inspectionTypesList.selectInspectionType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
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

        WholesailCustomer testwholesailcustomer = JSonDataParser.getTestDataFromJson("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json", WholesailCustomer.class);
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
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

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
        wotypes.selectWorkOrderType(workOrderData.getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(workOrderData.getVinNumber());
        final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
        workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.changeCustomerForWorkOrder(woNumber, testcustomer2);
        Assert.assertEquals(workordersscreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        workordersscreen.clickBackButton();
    }
}
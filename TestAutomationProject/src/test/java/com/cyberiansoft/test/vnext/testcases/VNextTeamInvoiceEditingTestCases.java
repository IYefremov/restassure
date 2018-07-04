package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInvoiceEditingTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-invoice-editing-testcases-data.json";

    //final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    //final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");

    @BeforeClass(description = "Team Invoice Editing Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInvoiceIfDraftModeEqualsON(String rowID,
                                                          String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getWorkOrderData().getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.changeScreen("Services");
        VNextInspectionServicesScreen servicesScreen = new VNextInspectionServicesScreen(appiumdriver);
        servicesScreen.selectServices(invoice.getWorkOrderData().getServicesList());
        workordersscreen = servicesScreen.saveWorkOrderViaMenu();
        homescreen = workordersscreen.clickBackButton();

        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
        insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInvoiceIfDraftModeEqualsOFF(String rowID,
                                                                     String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getWorkOrderData().getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.changeScreen("Services");
        VNextInspectionServicesScreen servicesScreen = new VNextInspectionServicesScreen(appiumdriver);
        servicesScreen.selectServices(invoice.getWorkOrderData().getServicesList());
        workordersscreen = servicesScreen.saveWorkOrderViaMenu();
        homescreen = workordersscreen.clickBackButton();

        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
        insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoice();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
        invoicesscreen.clickBackButton();
    }
}

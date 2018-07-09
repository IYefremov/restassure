package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
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
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        invoicesscreen.switchToMyInvoicesView();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoice();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditDraftInvoice(String rowID,
                                                                     String description, JSONObject testData) {
        LocalDate date = LocalDate.now();
        date = date.plusDays(1);
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMM dd");
        DateTimeFormatter dateFormatlong =
                DateTimeFormatter.ofPattern("MMMM d, yyyy");
        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        final String workOrderNumber = createWorkOrder(invoice);
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);

        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceinfoscreen.setInvoicePONumber(invoice.getNewPONumber());
        invoiceinfoscreen.changeInvoiceDayValue(date);
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();

        Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber),invoice.getNewPONumber());
        Assert.assertEquals(invoicesscreen.getInvoiceDateValue(invoicenumber), date.format(dateFormat));
        invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        Assert.assertEquals(invoiceinfoscreen.getInvoicePONumberValue(),invoice.getNewPONumber());
        Assert.assertEquals(invoiceinfoscreen.getInvoiceDateValue(), date.format(dateFormatlong));
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantEditFinalInvoice(String rowID,
                                                                     String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);

        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        Assert.assertFalse(invoiceMenuScreen.isInvoiceEditMenuItemExists());
        invoiceMenuScreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCreateDraftInvoiceWhenEditInvoice(String rowID,
                                                  String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceinfoscreen.setInvoicePONumber(invoice.getNewPONumber());
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber),invoice.getNewPONumber());
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        invoicesscreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveFinalInvoiceWhenEditInvoice(String rowID,
                                                                   String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceinfoscreen.setInvoicePONumber(invoice.getNewPONumber());
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber),invoice.getNewPONumber());
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantApproveDraftInvoice(String rowID,
                                                                 String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        Assert.assertFalse(invoiceMenuScreen.isApproveInvoiceMenuItemExists());
        invoiceMenuScreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanApproveFinalInvoice(String rowID,
                                                                 String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceinfoscreen.setInvoicePONumber(invoice.getNewPONumber());
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber),invoice.getNewPONumber());
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);

        invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        VNextApproveScreen approveScreen = invoiceMenuScreen.clickApproveInvoiceMenuItem();
        approveScreen.drawSignature();
        approveScreen.saveApprovedInspection();
        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditTeamDraftInvoice(String rowID,
                                                                   String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        invoicesscreen.switchToTeamInvoicesView();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceinfoscreen.setInvoicePONumber(invoice.getNewPONumber());
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber),invoice.getNewPONumber());
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        invoicesscreen.switchToMyInvoicesView();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanAddNotesWhenEditInvoice(String rowID,
                                                      String description, JSONObject testData) {

        final String  txtNotes = "Test notes";

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceinfoscreen.addTextNoteToInvoice(txtNotes);
        invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        Assert.assertTrue(invoicesscreen.isInvoiceHasNotesIcon(invoicenumber));
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelInvoiceCreation(String rowID,
                                                         String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoiceinfoscreen.cancelInvoice();
        workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
        homescreen = workordersscreen.clickBackButton();
        invoicesscreen = homescreen.clickInvoicesMenuItem();
        Assert.assertFalse(invoicesscreen.isInvoiceExists(invoicenumber));
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelInvoiceEditing(String rowID,
                                                                     String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
        final String workOrderNumber = createWorkOrder(invoice);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
        workordersscreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getInvoiceData().getInvoiceType());

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
        invoicesscreen = invoiceinfoscreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
        invoiceinfoscreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceinfoscreen.setInvoicePONumber(invoice.getNewPONumber());
        invoiceinfoscreen.clickInvoiceInfoBackButton();
        VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationdialog.clickInformationDialogYesButtonAndGetMessage(),
                VNextAlertMessages.CANCEL_ETING_INVOICE);
        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), invoice.getInvoiceData().getInvoicePONumber());
        invoicesscreen.clickBackButton();
    }

    public String createWorkOrder(Invoice invoice) {
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(invoice.getWorkOrderData().getWorkOrderType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        final String workOrderNumber = vehicleinfoscreen.getNewInspectionNumber();
        if (invoice.getWorkOrderData().getServicesList() != null) {
            vehicleinfoscreen.changeScreen("Services");
            VNextInspectionServicesScreen servicesScreen = new VNextInspectionServicesScreen(appiumdriver);
            servicesScreen.selectServices(invoice.getWorkOrderData().getServicesList());
            workordersscreen = servicesScreen.saveWorkOrderViaMenu();
        } else
            workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
        workordersscreen.clickBackButton();
        return workOrderNumber;
    }
}

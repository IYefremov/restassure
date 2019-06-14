package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextApproveScreen;
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
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VNextTeamInvoiceEditingTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Invoice Editing Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoiceEditingTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInvoiceIfDraftModeEqualsON(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInvoiceIfDraftModeEqualsOFF(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        invoicesScreen.switchToMyInvoicesView();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoice();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditDraftInvoice(String rowID,
                                                                     String description, JSONObject testData) {
        LocalDate date = LocalDate.now();
        if (date.plusDays(1).getDayOfMonth() != 1)
            date = date.plusDays(1);
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMM dd");
        DateTimeFormatter dateFormatlong =
                DateTimeFormatter.ofPattern("MMMM d, yyyy");
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());
        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        invoiceInfoScreen.changeInvoiceDayValue(date);
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();

        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber),testCaseData.getInvoiceData().getNewPoNumber());
        Assert.assertEquals(invoicesScreen.getInvoiceDateValue(invoiceNumber), date.format(dateFormat));
        invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        Assert.assertEquals(invoiceInfoScreen.getInvoicePONumberValue(),testCaseData.getInvoiceData().getNewPoNumber());
        Assert.assertEquals(invoiceInfoScreen.getInvoiceDateValue(), date.format(dateFormatlong));
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantEditFinalInvoice(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());
        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        Assert.assertFalse(invoiceMenuScreen.isInvoiceEditMenuItemExists());
        invoiceMenuScreen.clickCloseInvoiceMenuButton();
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCreateDraftInvoiceWhenEditInvoice(String rowID,
                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber),testCaseData.getInvoiceData().getNewPoNumber());
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        invoicesScreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveFinalInvoiceWhenEditInvoice(String rowID,
                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        final String invoicePONumber = testCaseData.getInvoiceData().getNewPoNumber();
        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), invoicePONumber,
                "PO number is " + invoicePONumber);
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantApproveDraftInvoice(String rowID,
                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        Assert.assertFalse(invoiceMenuScreen.isApproveInvoiceMenuItemExists());
        invoiceMenuScreen.clickCloseInvoiceMenuButton();
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanApproveFinalInvoice(String rowID,
                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber),testCaseData.getInvoiceData().getNewPoNumber());
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);

        invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextApproveScreen approveScreen = invoiceMenuScreen.clickApproveInvoiceMenuItem();
        approveScreen.drawSignature();
        approveScreen.saveApprovedInspection();
        invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditTeamDraftInvoice(String rowID,
                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        invoicesScreen.switchToTeamInvoicesView();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber),testCaseData.getInvoiceData().getNewPoNumber());
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        invoicesScreen.switchToMyInvoicesView();
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanAddNotesWhenEditInvoice(String rowID,
                                                      String description, JSONObject testData) {

        final String  txtNotes = "Test notes";

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.addTextNoteToInvoice(txtNotes);
        invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        Assert.assertTrue(invoicesScreen.isInvoiceHasNotesIcon(invoiceNumber));
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelInvoiceCreation(String rowID,
                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoiceInfoScreen.cancelInvoice();
        workOrdersScreen = new VNextWorkOrdersScreen(DriverBuilder.getInstance().getAppiumDriver());
        homeScreen = workOrdersScreen.clickBackButton();
        invoicesScreen = homeScreen.clickInvoicesMenuItem();
        Assert.assertFalse(invoicesScreen.isInvoiceExists(invoiceNumber));
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelInvoiceEditing(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        invoiceInfoScreen.clickInvoiceInfoBackButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogYesButtonAndGetMessage(),
                VNextAlertMessages.CANCEL_ETING_INVOICE);
        invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), testCaseData.getInvoiceData().getPoNumber());
        invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanAttachWOWithSameCustomers(String rowID,
                                                      String description, JSONObject testData)  {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        List<String> workOrders = new ArrayList<>();
            for (WorkOrderData woData : testCaseData.getWorkOrdersData())
                workOrders.add(createWorkOrder(woData));

            VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
            VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
            VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
            workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrders.get(0));
            VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
            invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

            VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
            invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
            final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
            invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
            Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
            VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
            invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
            List<String> workOrdersToAdd = workOrders.subList(1, workOrders.size());
            invoiceInfoScreen.addWorkOrdersToInvoice(workOrdersToAdd);
            invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
            invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
            invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
            for (String woNumber : workOrders)
                invoiceInfoScreen.isWorkOrderSelectedForInvoice(woNumber);
            invoiceInfoScreen.clickInvoiceInfoBackButton();
            VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
            Assert.assertEquals(informationDialog.clickInformationDialogYesButtonAndGetMessage(),
                    VNextAlertMessages.CANCEL_ETING_INVOICE);
            invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
            for (String woNumber : workOrders)
                Assert.assertTrue(invoicesScreen.getInvoiceWorkOrders(invoiceNumber).contains(woNumber));
            invoicesScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanDeattachWOsFromInvoice(String rowID,
                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrders = new ArrayList<>();

        for (WorkOrderData woData : testCaseData.getWorkOrdersData())
            workOrders.add(createWorkOrder(woData));

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
        VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
        workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrders.get(0));
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.DRAFT);
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        final List<String> workOrdersToAdd = workOrders.subList(1, workOrders.size());
        invoiceInfoScreen.addWorkOrdersToInvoice(workOrdersToAdd);
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceInfoScreen = invoiceMenuScreen.clickEditInvoiceMenuItem();
        for (String woNumber : workOrders)
            invoiceInfoScreen.isWorkOrderSelectedForInvoice(woNumber);

        invoiceInfoScreen.deattechWorkOrdersFromInvoice(workOrders);
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOU_CANNOT_DEATTACH_THE_LAST_WORK_ORDER_FROM_INVOICE);
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsDraft();
        invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);

        invoiceMenuScreen.clickVoidInvoiceMenuItem();
        informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogVoidButtonAndGetMessage(),
                String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));
        invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoicesScreen.waitUntilInvoiceDisappearsFromList(invoiceNumber);

        homeScreen = invoicesScreen.clickBackButton();
        workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        final List<String> workOrdersDeleted = workOrders.subList(0, workOrders.size()-1);
        for (String woNumber : workOrdersDeleted)
            Assert.assertTrue(workOrdersScreen.isWorkOrderExists(woNumber), "Can't find work order: " + woNumber);
        workOrdersScreen.clickBackButton();
    }

    public String createWorkOrder(WorkOrderData woData) {
        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(woData.getVinNumber());
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
        if (woData.getServicesList() != null) {
            vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
            VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
            servicesScreen.selectServices(woData.getServicesList());
            workOrdersScreen = servicesScreen.saveWorkOrderViaMenu();
        } else
            workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
        workOrdersScreen.clickBackButton();
        return workOrderNumber;
    }


}

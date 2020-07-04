package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InvoiceStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.invoices.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceInfoSteps;
import com.cyberiansoft.test.vnext.steps.invoices.InvoiceSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.WorkOrdersScreenValidations;
import com.cyberiansoft.test.vnext.validations.invoices.InvoiceInfoScreenValidations;
import com.cyberiansoft.test.vnext.validations.invoices.InvoicesScreenValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VNextTeamInvoiceEditingTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Invoice Editing Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoiceEditingTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInvoiceIfDraftModeEqualsON(String rowID,
                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInvoiceIfDraftModeEqualsOFF(String rowID,
                                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR2, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoice();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditDraftInvoice(String rowID,
                                                  String description, JSONObject testData) {
        LocalDate date = LocalDate.now();
        if (date.minusDays(1).getDayOfMonth() != 1)
            date = date.minusDays(2);
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("MMM dd", Locale.US);
        DateTimeFormatter dateFormatlong =
                DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US);
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());
        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen();
        invoiceInfoScreen.changeInvoiceDayValue(date);
        InvoiceSteps.saveInvoiceAsDraft();

        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), testCaseData.getInvoiceData().getNewPoNumber());
        date = date.minusDays(1);
        Assert.assertEquals(invoicesScreen.getInvoiceDateValue(invoiceNumber), date.format(dateFormat));
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoScreenValidations.validateInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        InvoiceInfoScreenValidations.validateInvoiceDate(date.format(dateFormatlong));
        InvoiceSteps.saveInvoiceAsDraft();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantEditFinalInvoice(String rowID,
                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());
        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.EDIT, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateDraftInvoiceWhenEditInvoice(String rowID,
                                                                   String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, testCaseData.getInvoiceData().getNewPoNumber());
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSaveFinalInvoiceWhenEditInvoice(String rowID,
                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        InvoiceSteps.saveInvoiceAsFinal();
        final String invoicePONumber = testCaseData.getInvoiceData().getNewPoNumber();
        InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, invoicePONumber);
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantApproveDraftInvoice(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.APPROVE, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanApproveFinalInvoice(String rowID,
                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        InvoiceSteps.saveInvoiceAsFinal();
        InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, testCaseData.getInvoiceData().getNewPoNumber());
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);

        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamDraftInvoice(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.switchToTeamInvoicesView();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        InvoiceSteps.saveInvoiceAsDraft();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(10000);
        HomeScreenSteps.openInvoices();
        InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, testCaseData.getInvoiceData().getNewPoNumber());
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.switchToMyInvoicesView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAddNotesWhenEditInvoice(String rowID,
                                                         String description, JSONObject testData) {

        final String txtNotes = "Test notes";

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceSteps.addTextNoteToInvoice(txtNotes);
        InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoicesScreenValidations.validateInvoiceHasNotesIcon(invoiceNumber, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCancelInvoiceEditing(String rowID,
                                                      String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String workOrderNumber = createWorkOrder(testCaseData.getWorkOrderData());

        HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getNewPoNumber());
        ScreenNavigationSteps.pressBackButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogYesButtonAndGetMessage(),
                VNextAlertMessages.CANCEL_ETING_INVOICE);
        InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, testCaseData.getInvoiceData().getPoNumber());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAttachWOWithSameCustomers(String rowID,
                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        List<String> workOrders = new ArrayList<>();
        for (WorkOrderData woData : testCaseData.getWorkOrdersData())
            workOrders.add(createWorkOrder(woData));

        HomeScreenSteps.openCreateMyInvoice(workOrders.get(0));
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        List<String> workOrdersToAdd = workOrders.subList(1, workOrders.size());
        InvoiceInfoSteps.addWorkOrdersToInvoice(workOrdersToAdd);
        InvoiceSteps.saveInvoiceAsDraft();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        workOrders.forEach(workOrderId -> InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderId, true));
        ScreenNavigationSteps.pressBackButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogYesButtonAndGetMessage(),
                VNextAlertMessages.CANCEL_ETING_INVOICE);
        workOrders.forEach(workOrderId ->
                InvoicesScreenValidations.validateInvoiceHasWorkOrder(invoiceNumber, workOrderId, true));
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanDeattachWOsFromInvoice(String rowID,
                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        List<String> workOrders = new ArrayList<>();

        for (WorkOrderData woData : testCaseData.getWorkOrdersData())
            workOrders.add(createWorkOrder(woData));

        HomeScreenSteps.openCreateMyInvoice(workOrders.get(0));
        InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();
        InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.DRAFT);
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        final List<String> workOrdersToAdd = workOrders.subList(1, workOrders.size());
        InvoiceInfoSteps.addWorkOrdersToInvoice(workOrdersToAdd);
        InvoiceSteps.saveInvoiceAsDraft();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        workOrders.forEach(workOrderId -> InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderId, true));

        InvoiceInfoSteps.removeWorkOrdersFromInvoice(workOrders);
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOU_CANNOT_DEATTACH_THE_LAST_WORK_ORDER_FROM_INVOICE);
        InvoiceSteps.saveInvoiceAsDraft();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.VOID);
        Assert.assertEquals(informationDialog.clickInformationDialogVoidButtonAndGetMessage(),
                String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));

        ScreenNavigationSteps.pressBackButton();
        HomeScreenSteps.openWorkOrders();
        final List<String> workOrdersDeleted = workOrders.subList(0, workOrders.size() - 1);
        workOrdersDeleted.forEach(workOrderID -> {
            WorkOrdersScreenValidations.validateWorkOrderExists(workOrderID, true);
        });
        ScreenNavigationSteps.pressBackButton();
    }

    public String createWorkOrder(WorkOrderData woData) {
        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, woData);
        if (woData.getServicesList() != null) {
            WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
            AvailableServicesScreenSteps.selectServices(woData.getServicesList());
        }
        String workOrderNumber = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
        return workOrderNumber;
    }
}

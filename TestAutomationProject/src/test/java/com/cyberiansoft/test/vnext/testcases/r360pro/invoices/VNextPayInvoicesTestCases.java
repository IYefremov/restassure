package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextPayInvoicesTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Invoice Payment Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicePaymentTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyErrorMessageIsDisplayedIfCardInfoIsNotPopulatedOnPayTeamInvoice(String rowID, String description, JSONObject testData) {


        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        BaseUtils.waitABit(3000);
        payInvoicesScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogOKButtonAndGetMessageUsingJs();
        Assert.assertTrue(payInvoicesScreen.isCardNumberRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationMonthRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationYearRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isCVCRequiredErrorDisplayed());
        payInvoicesScreen.clickBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardWasDeclinedOnPayTeamInvoiceWith4000000000000002(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARD_WAS_DECLINED);

        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardsSecurityCodeIsIncorrectOnPayTeamInvoiceWith4000000000000127(String rowID,
                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARDs_SECURITY_CODE_IS_INCORRECT);

        payInvoicesScreen.clickBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPayTeamInvoiceAfterCorrectingValidationErrors(String rowID,
                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getIncorrectCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARDs_SECURITY_CODE_IS_INCORRECT);
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.clickPayButton();
        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPayTeamInvoiceWithValidVisaCard(String rowID,
                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeePOROPaymentMethodIfPOROMethodEqualsON(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        VNextInvoiceMenuScreen invoiceMenuScreen = new VNextInvoiceMenuScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY_PO_RO, true);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeePOROPaymentActionForPaidInvoices(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        InvoiceSteps.openMenu(invoiceNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY_PO_RO, true);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeePOROPaymentActionForNotFullyPaidInvoice(String rowID,
                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        final String payAmount = "8";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.setAmauntValue(payAmount);
        payCashCheckScreen.payInvoice();

        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY_PO_RO, true);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPaymentPOROIsEqualToInvoicePONumber(String rowID,
                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_PO_RO);
        VNextPayPOROScreen poroScreen = new VNextPayPOROScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(poroScreen.getPaymentPOROValue(), testCaseData.getInvoiceData().getPoNumber());
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPOROFieldIsRequired(String rowID,
                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String payPOROValue = "";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_PO_RO);
        VNextPayPOROScreen payPOROScreen = new VNextPayPOROScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payPOROScreen.setPaymentPOROValue(payPOROValue);
        payPOROScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.PORO_FIELDIS_REQUIRED);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyNotesFieldIsOptionalForPOROPayment(String rowID,
                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_PO_RO);
        VNextPayPOROScreen payPOROScreen = new VNextPayPOROScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payPOROScreen.setPaymentPOROValue(testCaseData.getInvoiceData().getPoNumber());
        payPOROScreen.payForInvoice();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanGoBackToInvoiceListWithoutSavePayment(String rowID,
                                                             String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_PO_RO);
        VNextPayPOROScreen payPOROScreen = new VNextPayPOROScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payPOROScreen.setPaymentPOROValue(testCaseData.getInvoiceData().getPoNumber());
        payPOROScreen.clickPayButton();

        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertFalse(invoicesScreen.isInvoiceHasPaymentIcon(invoiceNumber));
        Assert.assertFalse(invoicesScreen.isInvoiceHasNotesIcon(invoiceNumber));
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY_PO_RO, true);
        MenuSteps.closeMenu();

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyEnteredPOOnPOROPaymentScreenIsSavedForInvoice(String rowID,
                                                                       String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_PO_RO);
        VNextPayPOROScreen payPOROScreen = new VNextPayPOROScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payPOROScreen.setPaymentPOROValue(testCaseData.getInvoiceData().getPoNumber());
        payPOROScreen.payForInvoice();

        //???Assert.assertTrue(invoicesScreen.isInvoiceHasPaymentIcon(invoiceNumber));
        InvoiceSteps.openMenu(invoiceNumber);
        VNextInvoiceMenuScreen invoiceMenuScreen = new VNextInvoiceMenuScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoiceMenuScreen.clickInvoiceChangePONumberMenuItem();
        Assert.assertEquals(changeInvoicePONumberDialog.getInvoicePreviousPONumber(), testCaseData.getInvoiceData().getPoNumber());
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogDontSaveButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSeePaymentTypePOROForInvoiceIfAmountIsZero(String rowID,
                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.openServiceDetailsScreen(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice("0");
        ServiceDetailsScreenSteps.saveServiceDetails();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeeCashCheckPaymentMethodIfCashCheckMethodEqualsON(String rowID,
                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY_CASH_CHECK, true);

        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSeeCashCheckPaymentActionForPaidInvoices(String rowID,
                                                                         String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.payInvoice();

        InvoiceSteps.openMenu(invoiceNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY, false);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeeCashCheckPaymentActionForNotFullyPaidInvoice(String rowID,
                                                                           String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String payAmount = "8";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.setAmauntValue(payAmount);
        payCashCheckScreen.payInvoice();

        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuValidations.menuItemShouldBeVisible(MenuItems.PAY_CASH_CHECK, true);
        MenuSteps.closeMenu();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanGoBackFromCachCheckScreenToInvoiceListWithoutSavePayment(String rowID,
                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        payCashCheckScreen.clickScreenBackButton();
        ScreenNavigationSteps.pressBackButton();
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyAmountFieldIsRequiredForCashCheckMethod(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.clearAmauntValue();

        payCashCheckScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.AMOUNT_MAST_BE_GREATER_THEN_ZERO);
        payCashCheckScreen.clickScreenBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyNotesFieldIsOptionalForCashCheckType(String rowID,
                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String paymentCheckNumber = "0123456789";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.setPaymentCheckNumber(paymentCheckNumber);
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCheckFieldIsOptionalForCashCheckMethod(String rowID,
                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        ScreenNavigationSteps.pressBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPaymentAmountIsUpdatedAfterPartPaymentUsingCashCheckMethod(String rowID,
                                                                 String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String payAmount = "5";
        final String paymentCheckNumber = "100";
        final String paymentNotes = "PaymentNotes";
        final String paymentTotal= "4.00";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.setAmauntValue(payAmount);
        payCashCheckScreen.setPaymentCheckNumber(paymentCheckNumber);
        payCashCheckScreen.setPaymentNotes(paymentNotes);
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertFalse(invoicesScreen.isInvoiceHasPaymentIcon(invoiceNumber));
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        Assert.assertEquals(payCashCheckScreen.getAmauntValue(), paymentTotal);
        payCashCheckScreen.payInvoice();
        Assert.assertTrue(invoicesScreen.isInvoiceHasPaymentIcon(invoiceNumber));

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanPayOneInvoiceUsingCCAndCashCheckPaymentMethod(String rowID,
                                                                                     String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String payAmount = "5";
        final String paymentCheckNumber = "100";
        final String paymentNotes = "PaymentNotes";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.setAmauntValue(payAmount);
        payCashCheckScreen.setPaymentCheckNumber(paymentCheckNumber);
        payCashCheckScreen.setPaymentNotes(paymentNotes);
        payCashCheckScreen.payInvoice();


        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertTrue(invoicesScreen.isInvoiceHasPaymentIcon(invoiceNumber));

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanPayInvoiceUsingCashCheckAndPOROPaymentMethod(String rowID,
                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String payAmount = "5";
        final String paymentCheckNumber = "100";
        final String paymentNotes = "PaymentNotes";
        final String paymentPOROValue = "123";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.setAmauntValue(payAmount);
        payCashCheckScreen.setPaymentCheckNumber(paymentCheckNumber);
        payCashCheckScreen.setPaymentNotes(paymentNotes);
        payCashCheckScreen.payInvoice();

        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_PO_RO);
        VNextPayPOROScreen poroScreen = new VNextPayPOROScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        poroScreen.setPaymentPOROValue(paymentPOROValue);
        poroScreen.payForInvoice();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertTrue(invoicesScreen.isInvoiceHasPaymentIcon(invoiceNumber));
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantPayAmountLessThen0UsingCashCheckMethod(String rowID,
                                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
        final String payAmount = "-5";
        final String paymentCheckNumber = "100";
        final String paymentNotes = "PaymentNotes";

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CASH_CHECK);
        VNextPayCashCheckScreen payCashCheckScreen = new VNextPayCashCheckScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payCashCheckScreen.setAmauntValue(payAmount);
        payCashCheckScreen.setPaymentCheckNumber(paymentCheckNumber);
        payCashCheckScreen.setPaymentNotes(paymentNotes);
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.AMOUNT_MAST_BE_GREATER_THEN_ZERO);
        payCashCheckScreen.clickScreenBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyErrorMessageIsDisplayedIfCardInfoIsNotPopulatedOnPayMyInvoice(String rowID,
                                                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        BaseUtils.waitABit(3000);
        payInvoicesScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        informationDialog.clickInformationDialogOKButtonAndGetMessageUsingJs();
        Assert.assertTrue(payInvoicesScreen.isCardNumberRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationMonthRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationYearRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isCVCRequiredErrorDisplayed());
        payInvoicesScreen.clickBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardWasDeclinedOnPayInvoiceWith4000000000000002(String rowID,
                                                                                  String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARD_WAS_DECLINED);

        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardsSecurityCodeIsIncorrectOnPayInvoiceWith4000000000000127(String rowID,
                                                                                               String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARDs_SECURITY_CODE_IS_INCORRECT);

        payInvoicesScreen.clickBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPayInvoiceAfterCorrectingValidationErrors(String rowID,
                                                                        String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getIncorrectCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARDs_SECURITY_CODE_IS_INCORRECT);
        payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.clickPayButton();
        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);
        payInvoicesScreen.clickBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPayInvoiceWithValidVisaCard(String rowID,
                                                          String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        servicesScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        InvoiceSteps.openMenu(invoiceNumber);
        MenuSteps.selectMenuItem(MenuItems.PAY);
        MenuSteps.selectMenuItem(MenuItems.PAY_CREDIT_CARD);
        VNextPayInvoicesScreen payInvoicesScreen = new VNextPayInvoicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        payInvoicesScreen.setCardNumber(testCaseData.getInvoiceData().getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(testCaseData.getInvoiceData().getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(testCaseData.getInvoiceData().getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(testCaseData.getInvoiceData().getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        Assert.assertEquals(payInvoicesScreen.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }
}

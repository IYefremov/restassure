package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextPayMenu;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextPayInvoicesTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-invoice-payment-testcases-data.json";

    @BeforeClass(description = "Team Invoice Payment Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyErrorMessageIsDisplayedIfCardInfoIsNotPopulatedOnPayTeamInvoice(String rowID,
                                                                                                               String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        BaseUtils.waitABit(3000);
        payInvoicesScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertTrue(payInvoicesScreen.isCardNumberRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationMonthRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationYearRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isCVCRequiredErrorDisplayed());
        invoicesscreen = payInvoicesScreen.clickBackButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardWasDeclinedOnPayTeamInvoiceWith4000000000000002(String rowID,
                                                                                          String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();


        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARD_WAS_DECLINED);

        invoicesscreen = payInvoicesScreen.clickBackButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardsSecurityCodeIsIncorrectOnPayTeamInvoiceWith4000000000000127(String rowID,
                                                                                  String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();


        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARDs_SECURITY_CODE_IS_INCORRECT);

        invoicesscreen = payInvoicesScreen.clickBackButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPayTeamInvoiceAfterCorrectingValidationErrors(String rowID,
                                                                                               String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getIncorrectCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();


        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARDs_SECURITY_CODE_IS_INCORRECT);
        payInvoicesScreen = new VNextPayInvoicesScreen(appiumdriver);
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.clickPayButton();
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPayTeamInvoiceWithValidVisaCard(String rowID,
                                                                        String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeePOROPaymentMethodIfPOROMethodEqualsON(String rowID,
                                                                                          String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayMenu payMenu = invoicemenuscreen.clickPayInvoiceMenuItem();
        Assert.assertTrue(payMenu.isInvoicePayPOROMenuItemExists());
        invoicemenuscreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeePOROPaymentActionForPaidInvoices(String rowID,
                                                          String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        Assert.assertTrue(invoicemenuscreen.isInvoicePayPOROMenuItemExists());
        invoicemenuscreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeePOROPaymentActionForNotFullyPaidInvoice(String rowID,
                                                                  String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.setAmauntValue("8");
        payCashCheckScreen.payInvoice();

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayMenu payMenu = invoicemenuscreen.clickPayInvoiceMenuItem();
        Assert.assertTrue(payMenu.isInvoicePayPOROMenuItemExists());
        invoicemenuscreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPaymentPOROIsEqualToInvoicePONumber(String rowID,
                                                                         String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayPOROScreen poroScreen = invoicemenuscreen.clickPayPOROMenuItem();
        Assert.assertEquals(poroScreen.getPaymentPOROValue(), invoice.getInvoiceData().getInvoicePONumber());
        poroScreen.clickScreenBackButton();
        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPOROFieldIsRequired(String rowID,
                                                              String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayPOROScreen payPOROScreen = invoicemenuscreen.clickPayPOROMenuItem();
        payPOROScreen.setPaymentPOROValue("");
        payPOROScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.PORO_FIELDIS_REQUIRED);
        payPOROScreen.clickScreenBackButton();

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyNotesFieldIsOptionalForPOROPayment(String rowID,
                                              String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayPOROScreen payPOROScreen = invoicemenuscreen.clickPayPOROMenuItem();
        payPOROScreen.setPaymentPOROValue(invoice.getInvoiceData().getInvoicePONumber());
        invoicesscreen = payPOROScreen.payForInvoice();
        Assert.assertTrue(invoicesscreen.isInvoiceHasPaymentIcon(invoiceNumber));
        Assert.assertFalse(invoicesscreen.isInvoiceHasNotesIcon(invoiceNumber));
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanGoBackToInvoiceListWithoutSavePayment(String rowID,
                                                             String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayPOROScreen payPOROScreen = invoicemenuscreen.clickPayPOROMenuItem();
        payPOROScreen.setPaymentPOROValue(invoice.getInvoiceData().getInvoicePONumber());
        payPOROScreen.clickScreenBackButton();

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        Assert.assertFalse(invoicesscreen.isInvoiceHasPaymentIcon(invoiceNumber));
        Assert.assertFalse(invoicesscreen.isInvoiceHasNotesIcon(invoiceNumber));
        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayMenu payMenu = invoicemenuscreen.clickPayInvoiceMenuItem();
        Assert.assertTrue(payMenu.isInvoicePayPOROMenuItemExists());
        invoicemenuscreen.clickCloseInvoiceMenuButton();

        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyEnteredPOOnPOROPaymentScreenIsSavedForInvoice(String rowID,
                                                                       String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayPOROScreen payPOROScreen = invoicemenuscreen.clickPayPOROMenuItem();
        payPOROScreen.setPaymentPOROValue(invoice.getInvoiceData().getInvoicePONumber());
        payPOROScreen.payForInvoice();

        Assert.assertTrue(invoicesscreen.isInvoiceHasPaymentIcon(invoiceNumber));
        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
        Assert.assertEquals(changeInvoicePONumberDialog.getInvoicePreviousPONumber(), invoice.getInvoiceData().getInvoicePONumber());
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogDontSaveButton();
        invoicesscreen = new VNextInvoicesScreen(appiumdriver);

        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSeePaymentTypePOROForInvoiceIfAmountIsZero(String rowID,
                                                                        String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());

        vehicleinfoscreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        Assert.assertFalse(invoicemenuscreen.isPayInvoiceMenuItemExists());
        invoicemenuscreen.clickCloseInvoiceMenuButton();

        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeeCashCheckPaymentMethodIfCashCheckMethodEqualsON(String rowID,
                                                                         String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayMenu payMenu = invoicemenuscreen.clickPayInvoiceMenuItem();
        Assert.assertTrue(payMenu.isInvoicePayCashCheckMenuItemExists());

        invoicemenuscreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSeeCashCheckPaymentActionForPaidInvoices(String rowID,
                                                                         String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.payInvoice();

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        Assert.assertFalse(invoicemenuscreen.isPayInvoiceMenuItemExists());
        invoicemenuscreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserSeeCashCheckPaymentActionForNotFullyPaidInvoice(String rowID,
                                                                           String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.setAmauntValue("8");
        payCashCheckScreen.payInvoice();

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayMenu payMenu = invoicemenuscreen.clickPayInvoiceMenuItem();
        Assert.assertTrue(payMenu.isInvoicePayCashCheckMenuItemExists());
        invoicemenuscreen.clickCloseInvoiceMenuButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanGoBackFromCachCheckScreenToInvoiceListWithoutSavePayment(String rowID,
                                                                              String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();

        payCashCheckScreen.clickScreenBackButton();
        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyAmountFieldIsRequiredForCashCheckMethod(String rowID,
                                                                                          String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.clearAmauntValue();

        payCashCheckScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), VNextAlertMessages.AMOUNT_MAST_BE_GREATER_THEN_ZERO);
        payCashCheckScreen.clickScreenBackButton();
        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyNotesFieldIsOptionalForCashCheckType(String rowID,
                                                                 String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.setPaymentCheckNumber("0123456789");
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyCheckFieldIsOptionalForCashCheckMethod(String rowID,
                                                                  String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPaymentAmountIsUpdatedAfterPartPaymentUsingCashCheckMethod(String rowID,
                                                                 String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.setAmauntValue("5");
        payCashCheckScreen.setPaymentCheckNumber("100");
        payCashCheckScreen.setPaymentNotes("Test Notes");
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        Assert.assertFalse(invoicesscreen.isInvoiceHasPaymentIcon(invoiceNumber));
        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        Assert.assertEquals(payCashCheckScreen.getAmauntValue(), "4.00");
        payCashCheckScreen.payInvoice();
        Assert.assertTrue(invoicesscreen.isInvoiceHasPaymentIcon(invoiceNumber));

        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanPayOneInvoiceUsingCCAndCashCheckPaymentMethod(String rowID,
                                                                                     String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.setAmauntValue("5");
        payCashCheckScreen.setPaymentCheckNumber("100");
        payCashCheckScreen.setPaymentNotes("Test Notes");
        payCashCheckScreen.payInvoice();


        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_PAYMENT_HAS_BEEN_SUCCESSFULLY_COMPLETED);

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        Assert.assertTrue(invoicesscreen.isInvoiceHasPaymentIcon(invoiceNumber));

        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanPayInvoiceUsingCashCheckAndPOROPaymentMethod(String rowID,
                                                                               String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.setAmauntValue("5");
        payCashCheckScreen.setPaymentCheckNumber("100");
        payCashCheckScreen.setPaymentNotes("Test Notes");
        payCashCheckScreen.payInvoice();

        invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayPOROScreen poroScreen = invoicemenuscreen.clickPayPOROMenuItem();
        poroScreen.setPaymentPOROValue("4");
        poroScreen.payForInvoice();

        invoicesscreen = new VNextInvoicesScreen(appiumdriver);
        Assert.assertTrue(invoicesscreen.isInvoiceHasPaymentIcon(invoiceNumber));

        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantPayAmountLessThen0UsingCashCheckMethod(String rowID,
                                                                              String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToTeamWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayCashCheckScreen payCashCheckScreen = invoicemenuscreen.clickPayCachCheckMenuItem();
        payCashCheckScreen.setAmauntValue("-5");
        payCashCheckScreen.setPaymentCheckNumber("100");
        payCashCheckScreen.setPaymentNotes("Test Notes");
        payCashCheckScreen.clickPayButton();

        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.AMOUNT_MAST_BE_GREATER_THEN_ZERO);
        payCashCheckScreen.clickScreenBackButton();
        invoicesscreen = new VNextInvoicesScreen(appiumdriver);

        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyErrorMessageIsDisplayedIfCardInfoIsNotPopulatedOnPayMyInvoice(String rowID,
                                                                                          String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        BaseUtils.waitABit(3000);
        payInvoicesScreen.clickPayButton();
        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        informationDialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertTrue(payInvoicesScreen.isCardNumberRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationMonthRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isExpirationYearRequiredErrorDisplayed());
        Assert.assertTrue(payInvoicesScreen.isCVCRequiredErrorDisplayed());
        invoicesscreen = payInvoicesScreen.clickBackButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardWasDeclinedOnPayInvoiceWith4000000000000002(String rowID,
                                                                                  String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();


        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARD_WAS_DECLINED);

        invoicesscreen = payInvoicesScreen.clickBackButton();
        invoicesscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyYourCardsSecurityCodeIsIncorrectOnPayInvoiceWith4000000000000127(String rowID,
                                                                                               String description, JSONObject testData) {

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.switchToMyWorkordersView();
        VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
        customersscreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
        servicesScreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);

        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayCreditCardMenuItem();
        payInvoicesScreen.setCardNumber(invoice.getCreditCardData().getCardNumber());
        payInvoicesScreen.selectExpirationMonth(invoice.getCreditCardData().getExpirationMonth());
        payInvoicesScreen.selectExpirationYear(invoice.getCreditCardData().getExpirationYear());
        payInvoicesScreen.setCVC(invoice.getCreditCardData().getCVC());
        payInvoicesScreen.clickPayButton();


        VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.YOUR_CARDs_SECURITY_CODE_IS_INCORRECT);

        invoicesscreen = payInvoicesScreen.clickBackButton();
        invoicesscreen.clickBackButton();
    }
}

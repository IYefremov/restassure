package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
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
        wosummaryscreen.clickCreateInvoiceOption();
        wosummaryscreen.clickWorkOrderSaveButton();

        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayInvoiceMenuItem();
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
        wosummaryscreen.clickCreateInvoiceOption();
        wosummaryscreen.clickWorkOrderSaveButton();

        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayInvoiceMenuItem();
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
        wosummaryscreen.clickCreateInvoiceOption();
        wosummaryscreen.clickWorkOrderSaveButton();

        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayInvoiceMenuItem();
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
        wosummaryscreen.clickCreateInvoiceOption();
        wosummaryscreen.clickWorkOrderSaveButton();

        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayInvoiceMenuItem();
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
        wosummaryscreen.clickCreateInvoiceOption();
        wosummaryscreen.clickWorkOrderSaveButton();

        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
        invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
        invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
        final String invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
        VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        VNextPayInvoicesScreen payInvoicesScreen = invoicemenuscreen.clickPayInvoiceMenuItem();
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
}

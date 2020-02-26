package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextChangeCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class VNextTeamWorkOrdersChangeCustomerTestCases extends BaseTestClass {

    final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Work Orders Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersChangeCustomerTestCasesDataPath();
        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextCustomersScreen customersScreen = homeScreen.clickCustomersMenuItem();
        customersScreen.switchToRetailMode();
        if (!customersScreen.isCustomerExists(testcustomer1)) {
            VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
            newCustomerScreen.createNewCustomer(testcustomer1);
            customersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        }
        if (!customersScreen.isCustomerExists(testcustomer2)) {
            VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
            newCustomerScreen.createNewCustomer(testcustomer2);
            customersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        }
        ScreenNavigationSteps.pressBackButton();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerIfAllowClientChangingEqualsON(String rowID,
                                                                                          String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        workOrdersScreen.changeCustomerForWorkOrderViaSearch(woNumber, testcustomer2);
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantChangeCustomerIfAllowClientChangingEqualsOFF(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        WorkOrderSteps.switchToMyWorkOrdersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR2, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        WaitUtils.elementShouldBeVisible(workOrdersMenuScreen.getChangecustomerbtn(), false);
        workOrdersMenuScreen.clickCloseWorkOrdersMenuButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyIfChangedCustomerIsSavedAfterEditWO(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrderAsDraft();
        workOrdersScreen.changeCustomerForWorkOrderViaSearch(woNumber, testcustomer2);
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        BaseUtils.waitABit(1000*30);
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        workOrdersScreen = availableServicesScreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        Assert.assertEquals(workOrdersScreen.getWorkOrderPriceValue(woNumber), workOrderData.getWorkOrderPrice());

        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantCreateNewCustomerOnChangeCustomerScreen(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        VNextChangeCustomerScreen changeCustomerScreen = workOrdersMenuScreen.clickChangeCustomerMenuItem();
        Assert.assertFalse(changeCustomerScreen.isAddCustomerButtonDisplayed());
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeRetailCustomerToWholesaleForWO(String rowID,
                                                                             String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        WholesailCustomer testWholesaleCustomer = JSonDataParser.getTestDataFromJson(new File("src/test/java/com/cyberiansoft/test/vnext/data/test-wholesail-customer.json"), WholesailCustomer.class);
        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        workOrdersScreen.changeCustomerToWholesailForWorkOrder(woNumber, testWholesaleCustomer);
        Assert.assertFalse(workOrdersScreen.isWorkOrderExists(woNumber), "Can't find work order: " + woNumber);
        workOrdersScreen.switchToTeamWorkordersView();
        workOrdersScreen.searchWorkOrderByFreeText(woNumber);
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testWholesaleCustomer.getFullName());
        workOrdersScreen.switchToMyWorkordersView();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSelectCustomerUsingSearch(String rowID,
                                                                      String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        workOrdersScreen.changeCustomerForWorkOrderViaSearch(woNumber, testcustomer2);
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyInvoiceCreatedWithChangedCustomer(String rowID,
                                                           String description, JSONObject testData) throws IOException {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, testCaseData.getWorkOrderData());
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        workOrdersScreen.changeCustomerForWorkOrderViaSearch(woNumber, testcustomer2);
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        VNextInvoiceTypesList invoiceTypesScreen = workOrdersMenuScreen.clickCreateInvoiceMenuItem();
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        Assert.assertEquals(invoicesScreen.getInvoiceCustomerValue(invoiceNumber), testcustomer2.getFullName());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerForTeamWO(String rowID,
                                                            String description, JSONObject testData) throws IOException {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToTeamWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        workOrdersScreen.changeCustomerForWorkOrderViaSearch(woNumber, testcustomer2);
        workOrdersScreen.clickBackButton();
        BaseUtils.waitABit(30*1000);
        homeScreen.clickWorkOrdersMenuItem();
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        workOrdersScreen.switchToMyWorkordersView();
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCreateCustomerAfterChangingCustomerForWO(String rowID,
                                                                             String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        WorkOrderSteps.clickAddWorkOrderButton();
        WorkOrderSteps.createWorkOrder(testcustomer1, WorkOrderTypes.O_KRAMAR, workOrderData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getMoneyServiceData().getServiceName());
        final String woNumber = WorkOrderSteps.saveWorkOrder();
        workOrdersScreen.changeCustomerForWorkOrderViaSearch(woNumber, testcustomer2);
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(woNumber), testcustomer2.getFullName());
        ScreenNavigationSteps.pressBackButton();
    }
}
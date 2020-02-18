package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.WorkOrderStatuses;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextInvoiceInfoScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamDraftWOTestCases extends BaseTestClass {

    @BeforeClass(description="Team Draft Work Ordres Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getDraftWorkOrdersTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftWOWithoutPopulateRequiredFields(String rowID,
                                                                       String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        workOrdersScreen.clearSearchField();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderAsDraft();
        Assert.assertEquals(workOrdersScreen.getWorkOrderStatusValue(woNumber),
                WorkOrderStatuses.DRAFT.getWorkOrderStatusValue());

        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSaveWOIfDraftModeEqualsOFF(String rowID,
                                                                          String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        InspectionSteps.trySaveInspection();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        final String msg = informationDialog.clickInformationDialogOKButtonAndGetMessage();
        Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.cancelWorkOrder();
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSaveDraftWOIfCreateInvoiceToggleEqualsON(String rowID,
                                                             String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
        vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        InvoiceSteps.cancelInvoice();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSaveWOAsDraftWhenEditWOInInvoice(String rowID,
                                                                           String description, JSONObject testData) {
        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
        vehicleInfoScreen.saveWorkOrderViaMenu();

        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        VNextInvoiceTypesList invoiceTypesScreen = workOrdersMenuScreen.clickCreateInvoiceMenuItem();

        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = InvoiceSteps.saveInvoiceAsDraft();

        VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
        VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
        invoiceMenuScreen.clickEditInvoiceMenuItem();
        invoiceInfoScreen.clickOnWorkOrder(woNumber);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
        InspectionSteps.trySaveInspection();
        InvoiceSteps.saveInvoiceAsFinal();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditDraftWO(String rowID,
                                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_INVOICE);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
        baseWizardScreen.saveWorkOrderAsDraft();
        Assert.assertEquals(workOrdersScreen.getWorkOrderStatusValue(woNumber),
                WorkOrderStatuses.DRAFT.getWorkOrderStatusValue());

        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        workOrdersMenuScreen.clickEditWorkOrderMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        availableServicesScreen.saveWorkOrderViaMenu();

        Assert.assertEquals(workOrdersScreen.getWorkOrderStatusValue(woNumber),
                WorkOrderStatuses.APPROVED.getWorkOrderStatusValue());
        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantEditWOInStateFinal(String rowID,
                                                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        VNextWorkOrdersMenuScreen workOrdersMenuScreen = workOrdersScreen.clickOnWorkOrderByNumber(woNumber);
        WaitUtils.elementShouldBeVisible(workOrdersMenuScreen.getEditinspectionbtn(), false);
        workOrdersMenuScreen.clickCloseWorkOrdersMenuButton();
        Assert.assertEquals(workOrdersScreen.getWorkOrderStatusValue(woNumber),
                WorkOrderStatuses.APPROVED.getWorkOrderStatusValue());

        workOrdersScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanEditWOIfDraftModeEqualsOFF(String rowID,
                                                     String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_NO_DRAFT);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();

        SearchSteps.textSearch(woNumber);
        WorkOrderSteps.openMenu(woNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        availableServicesScreen.saveWorkOrderViaMenu();

        Assert.assertEquals(workOrdersScreen.getWorkOrderStatusValue(woNumber),
                WorkOrderStatuses.APPROVED.getWorkOrderStatusValue());
        workOrdersScreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSaveFinalWOWithoutPopulateRequiredField(String rowID,
                                                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.switchToMyWorkordersView();
        VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
        customersScreen.selectCustomer(testcustomer);
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        final String woNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.selectService(workOrderData.getServiceData().getServiceName());
        InspectionSteps.trySaveInspection();
        vehicleInfoScreen.clcikSaveViaMenuAsFinal();
        VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.VIN_REQUIRED_MSG);
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        vehicleInfoScreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workOrdersScreen.getWorkOrderStatusValue(woNumber),
                WorkOrderStatuses.APPROVED.getWorkOrderStatusValue());

        workOrdersScreen.clickBackButton();
    }
}

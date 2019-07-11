package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class VNextTeamInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Inspections Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsTestCasesDataPath();
    }

    @BeforeMethod(description = "Send all messages")
    public void beforeTestCase() {
        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        if (homeScreen.isQueueMessageVisible()) {
            VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
            settingsScreen.setManualSendOff();
            settingsScreen.clickBackButton();
            homeScreen.waitUntilQueueMessageInvisible();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateInvoiceFromInspections(String rowID,
                                                              String description, JSONObject testData) {

        TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, testCaseData.getInspectionData());
        final String inspectionNumber = InspectionSteps.saveInspection();

        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.NEW);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickApproveInspectionMenuItem();
        VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        approveScreen.drawSignature();
        Assert.assertTrue(approveScreen.isClearButtonVisible());
        approveScreen.saveApprovedInspection();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.APPROVED);
        inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.ALL_AUTO_PHASES);
        BaseUtils.waitABit(60 * 1000);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
        VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);

        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceInfoScreen.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
        final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoice();
        invoicesScreen.switchToMyInvoicesView();
        Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);

        invoicesScreen.clickBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTeamInspectionDisplaysOnTheScreen(String rowID,
                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();

        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWhenUserGoBackFromInspectionsScreenToHomeWeSaveLastSelectedMode(String rowID,
                                                                                          String description, JSONObject testData) {

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
        homeScreen = inspectionsScreen.clickBackButton();
        inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
        inspectionsScreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionsScreen.isMyInspectionsViewActive());
        homeScreen = inspectionsScreen.clickBackButton();
        inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        Assert.assertTrue(inspectionsScreen.isMyInspectionsViewActive());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateInspectionWithoutTeamSharing(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
        claimInfoScreen.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
        inspectionsScreen = claimInfoScreen.saveInspectionViaMenu();

        inspectionsScreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertFalse(inspectionsScreen.isInspectionExists(inspectionNumber), "Team inspection exists: " + inspectionNumber);
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyOnlyWhenUserTapSearchButtonWePerformSearchAndRefreshTeamInspectionsList(String rowID,
                                                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();

        inspectionsScreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        inspectionsScreen.searchInpectionByFreeText(testwholesailcustomer.getFullName());
        Assert.assertTrue(inspectionsScreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
        List<String> inspsCustomers = inspectionsScreen.getAllInspectionsCustomers();
        for (String inspCustomers : inspsCustomers) {
            Assert.assertTrue(inspCustomers.contains(testwholesailcustomer.getFullName()));
        }
        final String inspSubNumber = inspectionNumber.substring(6, inspectionNumber.length());
        inspectionsScreen.searchInpectionByFreeText(inspSubNumber);
        Assert.assertTrue(inspectionsScreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
        List<String> inspsNumbers = inspectionsScreen.getAllInspectionsNumbers();
        for (String inspectionNumbers : inspsNumbers) {
            Assert.assertTrue(inspectionNumbers.contains(inspSubNumber));
        }

        Assert.assertTrue(inspectionsScreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);

        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanViewTeamInspection(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();

        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        VNextViewScreen viewScreen = inspectionsMenuScreen.clickViewInspectionMenuItem();
        viewScreen.clickScreenBackButton();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateTeamInspection(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        homeScreen = inspectionsScreen.clickBackButton();
        Assert.assertFalse(homeScreen.isQueueMessageVisible());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTeamInspectionSavedIntoMobileDeviceAndBOLaterViaOutgoingMessageIfThereIsNoConnection(String rowID,
                                                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String expectedQueue = "1";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
        settingsScreen.setManualSendOff();
        settingsScreen.clickBackButton();

        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        AppiumUtils.setNetworkOff();
        inspectionsScreen.hidePickerWheel();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogOKButton();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.clickSaveInspectionMenuButton();
        informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogOKButton();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());

        homeScreen = inspectionsScreen.clickBackButton();
        Assert.assertEquals(homeScreen.getQueueMessageValue(), expectedQueue);
        AppiumUtils.setAndroidNetworkOn();
        homeScreen.waitUntilQueueMessageInvisible();
        Assert.assertFalse(homeScreen.isQueueMessageVisible());
        inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        inspectionsScreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
        homeScreen = inspectionsScreen.clickBackButton();

        VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
        statusScreen.updateMainDB();

        inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        inspectionsScreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySavingTeamInspectionOnlineDoesntAffectedToSettingsManualSendOption(String rowID,
                                                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
        homeScreen = settingsScreen.setManualSendOn().clickBackButton();

        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        inspectionsScreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
        homeScreen = inspectionsScreen.clickBackButton();

        VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
        statusScreen.updateMainDB();
        //homeScreen = statusScreen.clickBackButton();

        inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditInspectionIfWeHaveNoInternetConnection(String rowID,
                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String newVinNumber = "1FMCU0DG4BK830800";
        final String queueMessage = "1";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        vehicleInfoScreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        AppiumUtils.setNetworkOff();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,newVinNumber);
        vehicleInfoScreen.clickSaveInspectionMenuButton();
        BaseUtils.waitABit(10000);
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogOKButton();
        inspectionsScreen.switchToMyInspectionsView();
        homeScreen = inspectionsScreen.clickBackButton();
        Assert.assertEquals(homeScreen.getQueueMessageValue(), queueMessage);
        AppiumUtils.setAndroidNetworkOn();
        homeScreen.waitUntilQueueMessageInvisible();
        Assert.assertFalse(homeScreen.isQueueMessageVisible());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditMyInspectionsIfWeHaveNoInternetConnection(String rowID,
                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String newVinNumber = "1FMCU0DG4BK830800";
        final String queueMessage = "1";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.switchToMyInspectionsView();
        AppiumUtils.setNetworkOff();

        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        vehicleInfoScreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,newVinNumber);
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        homeScreen = inspectionsScreen.clickBackButton();
        Assert.assertEquals(homeScreen.getQueueMessageValue(), queueMessage);
        AppiumUtils.setAndroidNetworkOn();
        homeScreen.waitUntilQueueMessageInvisible();
        Assert.assertFalse(homeScreen.isQueueMessageVisible());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamInspection(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String newVinNumber = "1FMCU0DG4BK830800";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

        vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, newVinNumber);

        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.saveInspectionViaMenu();

        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

        vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);

        VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
        vehicleInfoData.setVinNumber(newVinNumber);
        VehicleInfoScreenValidations.validateVehicleInfo(vehicleInfoData);

        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(claimInfoScreen.getInsuranceCompany(), inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.cancelInspection();
        inspectionsScreen.clickBackButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        BackOfficeHeaderPanel backofficeHeader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        OperationsWebPage operationsWebPage = backofficeHeader.clickOperationsLink();
        InspectionsWebPage inspectionspage = operationsWebPage.clickInspectionsLink();
        inspectionspage.makeSearchPanelVisible()
                .selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName())
                .setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted())
                .searchInspectionByNumber(inspectionNumber);
        inspectionspage.verifyVINIsPresentForInspection(inspectionNumber, newVinNumber);
        webdriver.quit();
    }

    //TODO: removed until we need it
    //@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySendingMoreThen100MessagesAfterReconnectInternet(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final int fakeImagesNumber = 50;
        final String imageSummaryValue = "+47";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        AppiumUtils.setNetworkOff();
        VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
        homeScreen = settingsScreen.setManualSendOn().clickBackButton();

        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claimInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        //TODO: REWRITE
//        for (ServiceData serviceData : inspectionData.getServicesList()) {
//            VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(serviceData.getServiceName());
//            for (int i = 0; i < fakeImagesNumber; i++)
//                notesScreen.addFakeImageNote();
//            notesScreen.clickScreenBackButton();
//            selectedServicesScreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
//        }

        selectedServicesScreen.saveInspectionViaMenu();
        availableServicesScreen.clickScreenBackButton();
        homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
        statusScreen.clickUpdateAppdata();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogStartSyncButton();
        BaseUtils.waitABit(10000);
        informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogOKButton();
        AppiumUtils.setAndroidNetworkOn();
        statusScreen.clickUpdateAppdata();
        informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogStartSyncButton();
        BaseUtils.waitABit(10000);
        informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogOKButton();

        homeScreen = statusScreen.clickBackButton();
        inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
        vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());

        claimInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            selectedServicesScreen.isServiceSelected(serviceData.getServiceName());
            Assert.assertEquals(selectedServicesScreen.getSelectedServiceImageSummaryValue(serviceData.getServiceName()),
                    imageSummaryValue);
        }
        inspectionsScreen = availableServicesScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMessageYourEmailMessageHasBeenAddedtoTheQueueDisplaysAfterSending(String rowID,
                                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String customereMail = "test.cyberiansoft@gmail.com";

        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();

        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspectionNumber);
        emailScreen.sentToEmailAddress(customereMail);
        emailScreen.sendEmail();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyItIsNotPossibleToEditTeamInspectionWithDeviceOnFlyMode(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();

        vehicleInfoScreen.saveInspectionAsDraft();
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
        AppiumUtils.setNetworkOff();
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        VNextInformationDialog informationDialog = inspectionsMenuScreen.clickEditInspectionMenuItemWithAlert();
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.CONNECTION_IS_NOT_AVAILABLE);
        AppiumUtils.setAndroidNetworkOn();
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWorkOrderAddAndRemoveServices(String rowID,
                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final int amountToSelect = 3;
        final int defaultCountForMoneyService = 1;


        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();

        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        VNextVehicleInfoScreen vehicleInfoScreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        for (int i = 0; i < amountToSelect; i++)
            availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getMoneyServiceData().getServiceName()), amountToSelect);
        for (int i = 0; i < amountToSelect; i++)
            availableServicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getPercentageServiceData().getServiceName()), amountToSelect);
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceData().getServiceName()), amountToSelect);

        selectedServicesScreen.saveInspectionViaMenu();
        inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        vehicleInfoScreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getPercentageServiceData().getServiceName()), amountToSelect);
        selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceData().getServiceName()), amountToSelect);
        selectedServicesScreen.uselectService(inspectionData.getPercentageServiceData().getServiceName());
        selectedServicesScreen.uselectService(inspectionData.getPercentageServiceData().getServiceName());
        selectedServicesScreen.uselectService(inspectionData.getMoneyServiceData().getServiceName());
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceData().getServiceName()), defaultCountForMoneyService);
        selectedServicesScreen.switchToAvalableServicesView();
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getMoneyServiceData().getServiceName()), 2);
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getPercentageServiceData().getServiceName()), defaultCountForMoneyService);
        availableServicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceData().getServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());

        availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getMoneyServiceData().getServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceData().getServiceName()), amountToSelect);
        availableServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCloseMyInspectionActionScreenInAndroid(String rowID,
                                                                        String description, JSONObject testData) {
        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        AppiumUtils.clickHardwareBackButton();
        new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanNavigateInMyInspectionWizardUsingActionScreenWwizardMenuInAndroid(String rowID,
                                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        homeScreen.clickInspectionsMenuItem();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickAddInspectionButton();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.changeScreen(ScreenType.SERVICES);

        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        availableServicesScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.clickScreenForwardButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.isServiceSelected(inspectionData.getServiceData().getServiceName());
        selectedServicesScreen.clickScreenBackButton();
        claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.clickScreenBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.clickMenuButton();
        Assert.assertTrue(vehicleInfoScreen.isSaveButtonVisible());
        Assert.assertTrue(vehicleInfoScreen.isCancelButtonVisible());
        Assert.assertTrue(vehicleInfoScreen.isNotesButtonVisible());
        AppiumUtils.clickHardwareBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.clickScreenTitleCaption();
        AppiumUtils.clickHardwareBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.clickMenuButton();
        Assert.assertTrue(availableServicesScreen.isSaveButtonVisible());
        Assert.assertTrue(availableServicesScreen.isCancelButtonVisible());
        Assert.assertTrue(availableServicesScreen.isNotesButtonVisible());
        AppiumUtils.clickHardwareBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMyInspectionActionScreenWorksCorrectly(String rowID,
                                                                 String description, JSONObject testData) {

        final String inspectionNote = "Test Note";

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        homeScreen.clickInspectionsMenuItem();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickAddInspectionButton();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
        BaseUtils.waitABit(1000 * 20);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        ;
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
        availableServicesScreen.saveInspectionViaMenu();

        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        Assert.assertTrue(inspectionsMenuScreen.isApproveMenuPresent());
        Assert.assertTrue(inspectionsMenuScreen.isChangeCustomerMenuPresent());
        Assert.assertTrue(inspectionsMenuScreen.isEditInspectionMenuButtonExists());
        Assert.assertTrue(inspectionsMenuScreen.isArchivwMenuPresent());
        Assert.assertTrue(inspectionsMenuScreen.isViewInspectionMenuPresent());
        Assert.assertTrue(inspectionsMenuScreen.isNotesMenuPresent());
        Assert.assertTrue(inspectionsMenuScreen.isEmailInspectionMenuPresent());
        VNextViewScreen viewScreen = inspectionsMenuScreen.clickViewInspectionMenuItem();
        viewScreen.clickScreenBackButton();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();

        VehicleInfoScreenInteractions.selectColor(inspectionData.getVehicleInfo().getVehicleColor());

        VehicleInfoScreenInteractions.setYear(inspectionData.getVehicleInfo().getVehicleYear());
        vehicleInfoScreen.clickMenuButton();
        Assert.assertTrue(vehicleInfoScreen.isSaveButtonVisible());
        Assert.assertTrue(vehicleInfoScreen.isCancelButtonVisible());
        Assert.assertTrue(vehicleInfoScreen.isNotesButtonVisible());
        AppiumUtils.clickHardwareBackButton();
        vehicleInfoScreen.clickCancelMenuItem();
        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickInformationDialogNoButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen();
        GeneralSteps.dismissHelpingScreenIfPresent();
        vehicleInfoScreen.clickScreenForwardButton();
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.clickScreenForwardButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.clickScreenForwardButton();
        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());

        int markerCount = 0;
        for (DamageData damageData : inspectionData.getDamagesData()) {
            VNextSelectDamagesScreen selectDamagesScreen = visualScreen.clickAddServiceButton();
            selectDamagesScreen.clickDefaultDamageType(damageData.getDamageGroupName());
            visualScreen.clickCarImageACoupleTimes(markerCount + 1);
            markerCount++;
        }

        Assert.assertEquals(visualScreen.getNumberOfImageMarkers(), inspectionData.getDamagesData().size());
        visualScreen.changeScreen(ScreenType.SERVICES);
        ;
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.clickMenuButton();
        Assert.assertTrue(availableServicesScreen.isSaveButtonVisible());
        Assert.assertTrue(availableServicesScreen.isCancelButtonVisible());
        Assert.assertTrue(availableServicesScreen.isNotesButtonVisible());
        AppiumUtils.clickHardwareBackButton();
        VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
        notesScreen.setNoteText(inspectionNote);
        ScreenNavigationSteps.pressBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.saveInspectionViaMenu();
        inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickNotesInspectionMenuItem();
        NotesSteps.verifyNoteIsPresent(inspectionNote);
        ScreenNavigationSteps.pressBackButton();

        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        VNextEmailScreen emailScreen = inspectionsMenuScreen.clickEmailInspectionMenuItem();
        emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
        emailScreen.sendEmail();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickBackButton();
    }

}

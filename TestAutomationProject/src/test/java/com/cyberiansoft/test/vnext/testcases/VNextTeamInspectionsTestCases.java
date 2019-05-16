package com.cyberiansoft.test.vnext.testcases;

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
import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
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
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class VNextTeamInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-inspections-testcases-data.json";
    private List<InspectionDTO> inspectionDTOs = new ArrayList<>();

    @BeforeClass(description = "Team Inspections Test Cases")
    public void beforeClass() throws Exception {
        JSONDataProvider.dataFile = DATA_FILE;

		/*inspectionDTOs = VNextAPIUtils.getInstance().generateInspections("team-base-inspection-data1.json",
				InspectionTypes.O_KRAMAR, testcustomer, employee, licenseID, deviceID, appID,
				appLicenseEntity, 30
		);
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextStatusScreen statusScreen = homescreen.clickStatusMenuItem();
		statusScreen.updateMainDB();*/

    }

    @BeforeMethod(description = "Send all messages")
    public void beforeTestCase() {
        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        if (homescreen.isQueueMessageVisible()) {
            VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
            settingsscreen.setManualSendOff();
            settingsscreen.clickBackButton();
            homescreen.waitUntilQueueMessageInvisible();
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateInvoiceFromInspections(String rowID,
                                                              String description, JSONObject testData) {

        Inspection inspectionData = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
        Invoice invoiceData = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData.getInspectionData().getVinNumber());

        VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
        VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
        inspmenuscreen.clickApproveInspectionMenuItem();
        VNextApproveScreen approvescreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        approvescreen.drawSignature();
        Assert.assertTrue(approvescreen.isClearButtonVisible());
        approvescreen.saveApprovedInspection();
        inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
        inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
        inspmenuscreen.clickCreateWorkOrderInspectionMenuItem();
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.ALL_AUTO_PHASES);
        BaseUtils.waitABit(60 * 1000);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.changeScreen("Summary");
        VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
        wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
        VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
        invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);

        VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        invoiceinfoscreen.setInvoicePONumber(invoiceData.getInvoiceData().getInvoicePONumber());
        final String invoicNnumber = invoiceinfoscreen.getInvoiceNumber();
        VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
        invoicesscreen.switchToMyInvoicesView();
        Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicNnumber), VNextInspectionStatuses.NEW);

        invoicesscreen.clickBackButton();
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTeamInspectionDisplaysOnTheScreen(String rowID,
                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData.getVinNumber());
        VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionscreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
        inspectionscreen.searchInpectionByFreeText(inspnumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
        inspectionscreen.switchToMyInspectionsView();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyWhenUserGoBackFromInspectionsScreenToHomeWeSaveLastSelectedMode(String rowID,
                                                                                          String description, JSONObject testData) {

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
        homescreen = inspectionscreen.clickBackButton();
        inspectionscreen = homescreen.clickInspectionsMenuItem();
        Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
        inspectionscreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionscreen.isMyInspectionsViewActive());
        homescreen = inspectionscreen.clickBackButton();
        inspectionscreen = homescreen.clickInspectionsMenuItem();
        Assert.assertTrue(inspectionscreen.isMyInspectionsViewActive());
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateInspectionWithoutTeamSharing(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreenLeft();
        VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claiminfoscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claiminfoscreen.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
        claiminfoscreen.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
        inspectionscreen = claiminfoscreen.saveInspectionViaMenu();

        inspectionscreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertFalse(inspectionscreen.isInspectionExists(inspNumber), "Team inspection exists: " + inspNumber);
        inspectionscreen.switchToMyInspectionsView();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyOnlyWhenUserTapSearchButtonWePerformSearchAndRefreshTeamInspectionsList(String rowID,
                                                                                                  String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();

        inspectionscreen.switchToTeamInspectionsView();
        Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        inspectionscreen.searchInpectionByFreeText(testwholesailcustomer.getFullName());
        Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
        List<String> inspsCustomers = inspectionscreen.getAllInspectionsCustomers();
        for (String inspCustomers : inspsCustomers) {
            Assert.assertTrue(inspCustomers.contains(testwholesailcustomer.getFullName()));
        }
        final String inspSubNumber = inspNumber.substring(6, inspNumber.length());
        inspectionscreen.searchInpectionByFreeText(inspSubNumber);
        Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
        List<String> inspsNumbers = inspectionscreen.getAllInspectionsNumbers();
        for (String inspNumbers : inspsNumbers) {
            Assert.assertTrue(inspNumbers.contains(inspSubNumber));
        }

        Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);

        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanViewTeamInspection(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();

        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        VNextViewScreen viewscreen = inspmenuscreen.clickViewInspectionMenuItem();
        viewscreen.clickScreenBackButton();
        inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateTeamInspection(String rowID,
                                                      String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        homescreen = inspectionscreen.clickBackButton();
        Assert.assertFalse(homescreen.isQueueMessageVisible());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyTeamInspectionSavedIntoMobileDeviceAndBOLaterViaOutgoingMessageIfThereIsNoConnection(String rowID,
                                                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
        settingsscreen.setManualSendOff();
        settingsscreen.clickBackButton();

        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        AppiumUtils.setNetworkOff();
        inspectionscreen.hidePickerWheel();
        inspectionscreen.switchToTeamInspectionsView();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogOKButton();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.clickSaveInspectionMenuButton();
        informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogOKButton();
        inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());

        homescreen = inspectionscreen.clickBackButton();
        Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
        AppiumUtils.setAndroidNetworkOn();
        homescreen.waitUntilQueueMessageInvisible();
        Assert.assertFalse(homescreen.isQueueMessageVisible());
        inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        inspectionscreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber));
        homescreen = inspectionscreen.clickBackButton();

        VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
        statusscreen.updateMainDB();
        //homescreen = statusscreen.clickBackButton();

        inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        inspectionscreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber));
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifySavingTeamInspectionOnlineDoesntAffectedToSettingsManualSendOption(String rowID,
                                                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
        homescreen = settingsscreen.setManualSendOn().clickBackButton();

        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        inspectionscreen.switchToMyInspectionsView();
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber));
        homescreen = inspectionscreen.clickBackButton();

        VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
        statusscreen.updateMainDB();
        //homescreen = statusscreen.clickBackButton();

        inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        inspectionscreen.switchToMyInspectionsView();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditInspectionIfWeHaveNoInternetConnection(String rowID,
                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String newVinNumber = "1FMCU0DG4BK830800";
        final String queueMessage = "1";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        vehicleinfoscreen = inspmenuscreen.clickEditInspectionMenuItem();
        AppiumUtils.setNetworkOff();
        vehicleinfoscreen.setVIN(newVinNumber);
        vehicleinfoscreen.clickSaveInspectionMenuButton();
        BaseUtils.waitABit(10000);
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogOKButton();
        inspectionscreen.switchToMyInspectionsView();
        homescreen = inspectionscreen.clickBackButton();
        Assert.assertEquals(homescreen.getQueueMessageValue(), queueMessage);
        AppiumUtils.setAndroidNetworkOn();
        homescreen.waitUntilQueueMessageInvisible();
        Assert.assertFalse(homescreen.isQueueMessageVisible());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditMyInspectionsIfWeHaveNoInternetConnection(String rowID,
                                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String newVinNumber = "1FMCU0DG4BK830800";
        final String queueMessage = "1";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.switchToMyInspectionsView();
        AppiumUtils.setNetworkOff();

        VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        vehicleinfoscreen = inspmenuscreen.clickEditInspectionMenuItem();
        vehicleinfoscreen.setVIN(newVinNumber);
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        homescreen = inspectionscreen.clickBackButton();
        Assert.assertEquals(homescreen.getQueueMessageValue(), queueMessage);
        AppiumUtils.setAndroidNetworkOn();
        homescreen.waitUntilQueueMessageInvisible();
        Assert.assertFalse(homescreen.isQueueMessageVisible());
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamInspection(String rowID,
                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String newVinNumber = "1FMCU0DG4BK830800";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);

        vehicleinfoscreen = inspectionscreen.clickOpenInspectionToEdit(inspNumber);
        vehicleinfoscreen.setVIN(newVinNumber);
        //vehicleinfoscreen.selectModelColor(color);
        vehicleinfoscreen.setLicPlate(inspectionData.getVehicleInfo().getVehicleLicensePlate());
        vehicleinfoscreen.setMilage(inspectionData.getVehicleInfo().getMileage());
        vehicleinfoscreen.setStockNo(inspectionData.getVehicleInfo().getStockNumber());
        vehicleinfoscreen.setRoNo(inspectionData.getVehicleInfo().getRoNumber());
        vehicleinfoscreen.changeScreen("Claim");
        VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claiminfoscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claiminfoscreen.saveInspectionViaMenu();

        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);

        vehicleinfoscreen = inspectionscreen.clickOpenInspectionToEdit(inspNumber);
        Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), newVinNumber);
        Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
        Assert.assertEquals(vehicleinfoscreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
        Assert.assertEquals(vehicleinfoscreen.getYear(), inspectionData.getVehicleInfo().getVehicleYear());
        Assert.assertEquals(vehicleinfoscreen.getLicPlate(), inspectionData.getVehicleInfo().getVehicleLicensePlate());
        Assert.assertEquals(vehicleinfoscreen.getMilage(), inspectionData.getVehicleInfo().getMileage());
        Assert.assertEquals(vehicleinfoscreen.getStockNo(), inspectionData.getVehicleInfo().getStockNumber());
        Assert.assertEquals(vehicleinfoscreen.getRoNo(), inspectionData.getVehicleInfo().getRoNumber());
        vehicleinfoscreen.changeScreen("Claim");
        claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(claiminfoscreen.getInsuranceCompany(), inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
        claiminfoscreen.cancelInspection();
        inspectionscreen.clickBackButton();

        WebDriver
                webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
        WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);
        BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
                BackOfficeLoginWebPage.class);
        loginpage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
                VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
        BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
                BackOfficeHeaderPanel.class);
        OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
        InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
        inspectionspage.makeSearchPanelVisible()
                .selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName())
                .setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted())
                .searchInspectionByNumber(inspNumber);
        inspectionspage.verifyVINIsPresentForInspection(inspNumber, newVinNumber);
        webdriver.quit();
    }

    //@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifySendingMoreThen100MessagesAfterReconnectInternet(String rowID,
                                                                           String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final int fakeImagesNumber = 50;
        final String imageSummaryValue = "+47";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        AppiumUtils.setNetworkOff();
        VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
        homescreen = settingsscreen.setManualSendOn().clickBackButton();

        VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
        inspectionsscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = inspinfoscreen.getNewInspectionNumber();
        inspinfoscreen.swipeScreenLeft();
        VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
        claiminfoscreen.swipeScreenLeft();
        VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspservicesscreen.selectServices(inspectionData.getServicesList());
        VNextSelectedServicesScreen selectedservicesscreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            VNextNotesScreen notesscreen = selectedservicesscreen.clickServiceNotesOption(serviceData.getServiceName());
            for (int i = 0; i < fakeImagesNumber; i++)
                notesscreen.addFakeImageNote();
            notesscreen.clickScreenBackButton();
            selectedservicesscreen = new VNextSelectedServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        }

        selectedservicesscreen.saveInspectionViaMenu();
        inspservicesscreen.clickScreenBackButton();
        homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
        statusscreen.clickUpdateAppdata();
        VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogStartSyncButton();
        BaseUtils.waitABit(10000);
        informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogOKButton();
        AppiumUtils.setAndroidNetworkOn();
        statusscreen.clickUpdateAppdata();
        informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogStartSyncButton();
        BaseUtils.waitABit(10000);
        informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationdlg.clickInformationDialogOKButton();

        homescreen = statusscreen.clickBackButton();
        inspectionsscreen = homescreen.clickInspectionsMenuItem();
        Assert.assertTrue(inspectionsscreen.isInspectionExists(inspNumber));
        inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspNumber);
        inspinfoscreen.swipeScreenLeft();
        claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());

        claiminfoscreen.swipeScreenLeft();
        inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        selectedservicesscreen = inspservicesscreen.switchToSelectedServicesView();
        for (ServiceData serviceData : inspectionData.getServicesList()) {
            selectedservicesscreen.isServiceSelected(serviceData.getServiceName());
            Assert.assertEquals(selectedservicesscreen.getSelectedServiceImageSummaryValue(serviceData.getServiceName()),
                    imageSummaryValue);
        }
        inspectionsscreen = inspservicesscreen.cancelInspection();
        inspectionsscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyMessageYourEmailMessageHasBeenAddedtoTheQueueDisplaysAfterSending(String rowID,
                                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final String customereMail = "test.cyberiansoft@gmail.com";

        final String inspNumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData.getVinNumber());
        VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspNumber);
        emailscreen.sentToEmailAddress(customereMail);
        emailscreen.sendEmail();
        inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyItIsNotPossibleToEditTeamInspectionWithDeviceOnFlyMode(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToWholesaleMode();
        customersscreen.selectCustomer(testwholesailcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();

        vehicleinfoscreen.saveInspectionAsDraft();
        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspNumber);
        Assert.assertTrue(inspectionscreen.isInspectionExists(inspNumber), "Can't find inspection: " + inspNumber);
        AppiumUtils.setNetworkOff();
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspNumber);
        VNextInformationDialog informationDialog = inspectionsMenuScreen.clickEditInspectionMenuItemWithAlert();
        Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
                VNextAlertMessages.CONNECTION_IS_NOT_AVAILABLE);
        AppiumUtils.setAndroidNetworkOn();
        inspectionscreen.switchToMyInspectionsView();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanEditTeamWorkOrderAddAndRemoveServices(String rowID,
                                                                       String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        final int amountToSelect = 3;
        final int defaultCountForMoneyService = 1;


        new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData.getVinNumber());

        VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
        VNextVehicleInfoScreen vehicleinfoscreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        vehicleinfoscreen.swipeScreenLeft();
        vehicleinfoscreen.swipeScreenLeft();
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        for (int i = 0; i < amountToSelect; i++)
            availableServicesScreen.selectService(inspectionData.getMoneyServiceName());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getMoneyServiceName()), amountToSelect);
        for (int i = 0; i < amountToSelect; i++)
            availableServicesScreen.selectService(inspectionData.getPercentageServiceName());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getPercentageServiceName()), amountToSelect);
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getMoneyServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceName()), amountToSelect);

        selectedServicesScreen.saveInspectionViaMenu();
        inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
        vehicleinfoscreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        vehicleinfoscreen.swipeScreenLeft();
        vehicleinfoscreen.swipeScreenLeft();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getMoneyServiceName()), amountToSelect);
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getPercentageServiceName()), amountToSelect);
        selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getMoneyServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceName()), amountToSelect);
        selectedServicesScreen.uselectService(inspectionData.getPercentageServiceName());
        selectedServicesScreen.uselectService(inspectionData.getPercentageServiceName());
        selectedServicesScreen.uselectService(inspectionData.getMoneyServiceName());
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceName()), defaultCountForMoneyService);
        selectedServicesScreen.switchToAvalableServicesView();
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getMoneyServiceName()), 2);
        Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(inspectionData.getPercentageServiceName()), defaultCountForMoneyService);
        availableServicesScreen.selectService(inspectionData.getPercentageServiceName());
        availableServicesScreen.selectService(inspectionData.getPercentageServiceName());
        availableServicesScreen.selectService(inspectionData.getMoneyServiceName());

        availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getMoneyServiceName()), amountToSelect);
        Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(inspectionData.getPercentageServiceName()), amountToSelect);
        availableServicesScreen.saveInspectionViaMenu();
        inspectionscreen.switchToMyInspectionsView();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCloseMyInspectionActionScreenInAndroid(String rowID,
                                                                        String description, JSONObject testData) {

        InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR);
        final String inspNumber = InspectionSteps.saveInspection();
        VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspNumber);
        AppiumUtils.clickHardwareBackButton();
        ;
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
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen("Visual");
        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        visualScreen.changeScreen("Services");
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.selectService(inspectionData.getServiceName());
        availableServicesScreen.changeScreen("Claim");
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.clickScreenForwardButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        selectedServicesScreen.isServiceSelected(inspectionData.getServiceName());
        selectedServicesScreen.clickScreenBackButton();
        claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.clickScreenBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.clickMenuButton();
        Assert.assertTrue(vehicleInfoScreen.isSaveButtonVisible());
        Assert.assertTrue(vehicleInfoScreen.isCancelButtonVisible());
        Assert.assertTrue(vehicleInfoScreen.isNotesButtonVisible());
        AppiumUtils.clickHardwareBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleInfoScreen.getVINFieldValue(), inspectionData.getVinNumber());
        vehicleInfoScreen.clickScreenTitleCaption();
        AppiumUtils.clickHardwareBackButton();
        vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(vehicleInfoScreen.getVINFieldValue(), inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen("Services");
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

    private String createSimpleInspection(AppCustomer inspcustomer, InspectionTypes insptype, String vinnumber) {

        String inspnumber = "";

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        if (insptype.equals(InspectionTypes.INSP_TYPE_APPROV_REQUIRED)) {
            inspectionscreen.switchToMyInspectionsView();
            VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
            customersscreen.switchToWholesaleMode();
            customersscreen.selectCustomer(inspcustomer);
            VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
            insptypeslist.selectInspectionType(insptype);
            VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
            vehicleinfoscreen.setVIN(vinnumber);
            inspnumber = vehicleinfoscreen.getNewInspectionNumber();
            vehicleinfoscreen.saveInspectionViaMenu();

        } else if ((insptype.equals(InspectionTypes.O_KRAMAR) & (inspectionDTOs.size() > 0))) {
            InspectionDTO inspectionDTO = inspectionDTOs.remove(0);
            inspnumber = "E-" + appLicenseEntity + "-0" + inspectionDTO.getLocalNo();
        } else {
            inspectionscreen.switchToMyInspectionsView();
            VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
            customersscreen.switchToWholesaleMode();
            customersscreen.selectCustomer(inspcustomer);
            VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
            insptypeslist.selectInspectionType(insptype);
            VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
            vehicleinfoscreen.setVIN(vinnumber);
            inspnumber = vehicleinfoscreen.getNewInspectionNumber();
            vehicleinfoscreen.saveInspectionViaMenu();
        }
        return inspnumber;
    }

}

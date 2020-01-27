package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
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
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class VNextTeamInspectionsTestCases extends BaseTestClass {

	@BeforeClass(description = "Team Inspections Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsTestCasesDataPath();
	}

	@BeforeMethod(description = "Send all messages")
	public void beforeTestCase() {
		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
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

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, testCaseData.getInspectionData());
		final String inspectionNumber = InspectionSteps.saveInspection();

		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approveScreen = new VNextApproveScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ApproveSteps.drawSignature();
		Assert.assertTrue(approveScreen.isClearButtonVisible());
		ApproveSteps.saveApprove();
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), VNextInspectionStatuses.APPROVED);
		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickCreateWorkOrderInspectionMenuItem();
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.ALL_AUTO_PHASES);
		BaseUtils.waitABit(60 * 1000);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
		vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
		VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);

		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
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

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();

		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
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

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
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

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
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

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
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

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();

		inspectionsScreen.switchToTeamInspectionsView();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextViewScreen viewScreen = inspectionsMenuScreen.clickViewInspectionMenuItem();
		viewScreen.clickScreenBackButton();
		inspectionsScreen.waitForInspectionsListIsVisibile();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateTeamInspection(String rowID,
													  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		homeScreen = inspectionsScreen.clickBackButton();
		//???Assert.assertFalse(homeScreen.isQueueMessageVisible());
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifySavingTeamInspectionOnlineDoesntAffectedToSettingsManualSendOption(String rowID,
																							 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOn().clickBackButton();

		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
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
	public void testVerifyUserCanEditTeamInspection(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String newVinNumber = "1FMCU0DG4BK830800";

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
		//VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, newVinNumber);

		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.saveInspectionViaMenu();

		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);

		VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
		vehicleInfoData.setVinNumber(newVinNumber);
		VehicleInfoScreenValidations.validateVehicleInfo(vehicleInfoData);

		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(claimInfoScreen.getInsuranceCompany(), inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();

		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(BrowserType.CHROME);
		webdriver.get(deviceOfficeUrl);
		BackOfficeLoginWebPage loginPage = new BackOfficeLoginWebPage(webdriver);
		loginPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeHeader = new BackOfficeHeaderPanel(webdriver);
		OperationsWebPage operationsWebPage = new OperationsWebPage(webdriver);
		backofficeHeader.clickOperationsLink();
		InspectionsWebPage inspectionspage = new InspectionsWebPage(webdriver);
		operationsWebPage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible();
		inspectionspage.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName());
		inspectionspage.setTimeFrame(CustomDateProvider.getPreviousLocalizedDateFormattedShort(), CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		inspectionspage.searchInspectionByNumber(inspectionNumber);
		inspectionspage.verifyVINIsPresentForInspection(inspectionNumber, newVinNumber);
		DriverBuilder.getInstance().getDriver().quit();
	}

	//TODO: removed until we need it
	//@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifySendingMoreThen100MessagesAfterReconnectInternet(String rowID,
																		   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final int fakeImagesNumber = 50;
		final String imageSummaryValue = "+47";

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		AppiumUtils.setNetworkOff();
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		homeScreen = settingsScreen.setManualSendOn().clickBackButton();

		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToWholesaleMode();
		customersScreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		//TODO: REWRITE
//        for (ServiceData serviceData : inspectionData.getServicesList()) {
//            VNextNotesScreen notesScreen = selectedServicesScreen.clickServiceNotesOption(serviceData.getServiceName());
//            for (int i = 0; i < fakeImagesNumber; i++)
//                notesScreen.addFakeImageNote();
//            notesScreen.clickScreenBackButton();
//            selectedServicesScreen = new VNextSelectedServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
//        }

		selectedServicesScreen.saveInspectionViaMenu();
		availableServicesScreen.clickScreenBackButton();
		homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
		statusScreen.clickUpdateAppdata();
		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickInformationDialogStartSyncButton();
		BaseUtils.waitABit(10000);
		informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickInformationDialogOKButton();
		AppiumUtils.setAndroidNetworkOn();
		statusScreen.clickUpdateAppdata();
		informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickInformationDialogStartSyncButton();
		BaseUtils.waitABit(10000);
		informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickInformationDialogOKButton();

		homeScreen = statusScreen.clickBackButton();
		inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		claimInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
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

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();

		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspectionNumber);
		emailScreen.sentToEmailAddress(customereMail);
		emailScreen.sendEmail();
		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCloseMyInspectionActionScreenInAndroid(String rowID,
																		String description, JSONObject testData) {
		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR);
		final String inspectionNumber = InspectionSteps.saveInspection();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		MenuSteps.closeMenu();
		new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanNavigateInMyInspectionWizardUsingActionScreenWwizardMenuInAndroid(String rowID,
																								   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		homeScreen.clickInspectionsMenuItem();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.switchToMyInspectionsView();
		inspectionsScreen.clickAddInspectionButton();
		VNextCustomersScreen customersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
		VNextVisualScreen visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		visualScreen.changeScreen(ScreenType.SERVICES);

		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		availableServicesScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		claimInfoScreen.clickScreenForwardButton();
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.isServiceSelected(inspectionData.getServiceData().getServiceName());
		selectedServicesScreen.clickScreenBackButton();
		claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		claimInfoScreen.clickScreenBackButton();
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		vehicleInfoScreen.clickMenuButton();
		Assert.assertTrue(vehicleInfoScreen.isSaveButtonVisible());
		Assert.assertTrue(vehicleInfoScreen.isCancelButtonVisible());
		Assert.assertTrue(vehicleInfoScreen.isNotesButtonVisible());
		MenuSteps.closeMenu();
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		//vehicleInfoScreen.clickScreenTitleCaption();
		//ScreenNavigationSteps.pressBackButton();
		//vehicleInfoScreen = new VNextVehicleInfoScreen();
		//HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		//VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.clickMenuButton();
		Assert.assertTrue(availableServicesScreen.isSaveButtonVisible());
		Assert.assertTrue(availableServicesScreen.isCancelButtonVisible());
		Assert.assertTrue(availableServicesScreen.isNotesButtonVisible());
		MenuSteps.closeMenu();
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyMyInspectionActionScreenWorksCorrectly(String rowID,
																 String description, JSONObject testData) {

		final String inspectionNote = "Test Note";

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		homeScreen.clickInspectionsMenuItem();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.switchToMyInspectionsView();
		inspectionsScreen.clickAddInspectionButton();
		VNextCustomersScreen customersScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
		BaseUtils.waitABit(1000 * 20);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();

		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		availableServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.waitForInspectionsListIsVisibile();
		SearchSteps.textSearch(inspectionNumber);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		MenuValidations.menuItemShouldBeVisible(MenuItems.APPROVE, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CHANGE_CUSTOMER, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.EDIT, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.ARCHIVE, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.VIEW, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.EMAIL, true);

		VNextViewScreen viewScreen = inspectionsMenuScreen.clickViewInspectionMenuItem();
		viewScreen.clickScreenBackButton();
		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickEditInspectionMenuItem();

		VehicleInfoScreenInteractions.selectColor(inspectionData.getVehicleInfo().getVehicleColor());

		VehicleInfoScreenInteractions.setYear(inspectionData.getVehicleInfo().getVehicleYear());
		vehicleInfoScreen.clickMenuButton();
		MenuValidations.menuItemShouldBeVisible(MenuItems.SAVE, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CANCEL, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);

		Assert.assertTrue(vehicleInfoScreen.isSaveButtonVisible());
		Assert.assertTrue(vehicleInfoScreen.isCancelButtonVisible());
		Assert.assertTrue(vehicleInfoScreen.isNotesButtonVisible());
		MenuSteps.closeMenu();
		vehicleInfoScreen.clickCancelMenuItem();
		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickInformationDialogNoButton();
		vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		vehicleInfoScreen.clickScreenForwardButton();
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		BaseUtils.waitABit(2000);
		claimInfoScreen.clickScreenForwardButton();
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.clickScreenForwardButton();
		VNextVisualScreen visualScreen = new VNextVisualScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		int markerCount = 0;
		for (DamageData damageData : inspectionData.getDamagesData()) {
			VNextSelectDamagesScreen selectDamagesScreen = visualScreen.clickAddServiceButton();
			BaseUtils.waitABit(1000);
			selectDamagesScreen.clickDefaultDamageType(damageData.getDamageGroupName());
			visualScreen.clickCarImageACoupleTimes(markerCount + 1);
			markerCount++;
		}

		Assert.assertEquals(visualScreen.getNumberOfImageMarkers(), inspectionData.getDamagesData().size());
		visualScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.clickMenuButton();
		MenuValidations.menuItemShouldBeVisible(MenuItems.SAVE, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CANCEL, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuSteps.closeMenu();
		VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
		notesScreen.setNoteText(inspectionNote);
		ScreenNavigationSteps.pressBackButton();
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.saveInspectionViaMenu();
		inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickNotesInspectionMenuItem();
		NotesSteps.verifyNoteIsPresent(inspectionNote);
		ScreenNavigationSteps.pressBackButton();

		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextEmailScreen emailScreen = inspectionsMenuScreen.clickEmailInspectionMenuItem();
		emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		emailScreen.sendEmail();
		inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		inspectionsScreen.clickBackButton();
	}

}

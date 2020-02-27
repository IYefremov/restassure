package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

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
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
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
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
		WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoice();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		invoicesScreen.switchToMyInvoicesView();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		ScreenNavigationSteps.pressBackButton();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyTeamInspectionDisplaysOnTheScreen(String rowID,
															String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();

		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		InspectionSteps.switchToTeamInspections();
		Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		InspectionSteps.switchToMyInspections();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyWhenUserGoBackFromInspectionsScreenToHomeWeSaveLastSelectedMode(String rowID,
																						  String description, JSONObject testData) {

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		InspectionSteps.switchToTeamInspections();
		Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
		ScreenNavigationSteps.pressBackButton();
		inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
		InspectionSteps.switchToMyInspections();
		Assert.assertTrue(inspectionsScreen.isMyInspectionsViewActive());
		ScreenNavigationSteps.pressBackButton();
		homeScreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionsScreen.isMyInspectionsViewActive());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateInspectionWithoutTeamSharing(String rowID,
																	String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);


		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR_NO_SHARING, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
		claimInfoScreen.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
		final String inspectionNumber = InspectionSteps.saveInspection();

		InspectionSteps.switchToTeamInspections();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertFalse(inspectionsScreen.isInspectionExists(inspectionNumber), "Team inspection exists: " + inspectionNumber);
		InspectionSteps.switchToMyInspections();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyOnlyWhenUserTapSearchButtonWePerformSearchAndRefreshTeamInspectionsList(String rowID,
																								  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();

		InspectionSteps.switchToTeamInspections();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		inspectionsScreen.searchInpectionByFreeText(testwholesailcustomer.getFullName());
		Assert.assertTrue(inspectionsScreen.getNumberOfInspectionsInList() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		List<String> inspsCustomers = inspectionsScreen.getAllInspectionsCustomers();
		for (String inspCustomers : inspsCustomers) {
			Assert.assertTrue(inspCustomers.contains(testwholesailcustomer.getFullName()));
		}
		final String inspSubNumber = inspectionNumber.substring(6, inspectionNumber.length());
		inspectionsScreen.searchInpectionByFreeText(inspSubNumber);
		Assert.assertTrue(inspectionsScreen.getNumberOfInspectionsInList() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		List<String> inspsNumbers = inspectionsScreen.getAllInspectionsNumbers();
		for (String inspectionNumbers : inspsNumbers) {
			Assert.assertTrue(inspectionNumbers.contains(inspSubNumber));
		}

		Assert.assertTrue(inspectionsScreen.getNumberOfInspectionsInList() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanViewTeamInspection(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();

		InspectionSteps.switchToTeamInspections();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		VNextViewScreen viewScreen = inspectionsMenuScreen.clickViewInspectionMenuItem();
		ScreenNavigationSteps.pressBackButton();
		inspectionsScreen.waitForInspectionsListIsVisibile();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateTeamInspection(String rowID,
													  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateTeamInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		ScreenNavigationSteps.pressBackButton();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifySavingTeamInspectionOnlineDoesntAffectedToSettingsManualSendOption(String rowID,
																							 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextSettingsScreen settingsScreen = homeScreen.clickSettingsMenuItem();
		settingsScreen.setManualSendOn().clickBackButton();

		HomeScreenSteps.openCreateTeamInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		InspectionSteps.switchToMyInspections();
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
		ScreenNavigationSteps.pressBackButton();

		VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
		statusScreen.updateMainDB();

		homeScreen.clickInspectionsMenuItem();
		InspectionSteps.switchToTeamInspections();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		InspectionSteps.switchToMyInspections();
		ScreenNavigationSteps.pressBackButton();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanEditTeamInspection(String rowID,
													String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String newVinNumber = "1FMCU0DG4BK830800";

		HomeScreenSteps.openCreateTeamInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, newVinNumber);

		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.saveInspectionViaMenu();

		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);

		inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);

		VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
		vehicleInfoData.setVinNumber(newVinNumber);
		VehicleInfoScreenValidations.validateVehicleInfo(vehicleInfoData);

		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
		Assert.assertEquals(claimInfoScreen.getInsuranceCompany(), inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.cancelInspection();
		ScreenNavigationSteps.pressBackButton();

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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyMessageYourEmailMessageHasBeenAddedtoTheQueueDisplaysAfterSending(String rowID,
																							String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		final String customerEMail = "test.cyberiansoft@gmail.com";

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();

		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextEmailScreen emailScreen = inspectionsScreen.clickOnInspectionToEmail(inspectionNumber);
		emailScreen.sentToEmailAddress(customerEMail);
		emailScreen.sendEmail();
		ScreenNavigationSteps.pressBackButton();
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
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanNavigateInMyInspectionWizardUsingActionScreenWwizardMenuInAndroid(String rowID,
																								   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR_NO_SHARING, inspectionData);

		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		claimInfoScreen.clickScreenForwardButton();
		availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.isServiceSelected(inspectionData.getServiceData().getServiceName());
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
		VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
		baseWizardScreen.clickMenuButton();
		MenuValidations.menuItemShouldBeVisible(MenuItems.SAVE_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CANCEL_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuSteps.closeMenu();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		baseWizardScreen.clickMenuButton();
		MenuValidations.menuItemShouldBeVisible(MenuItems.SAVE_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CANCEL_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuSteps.closeMenu();
		InspectionSteps.saveInspection();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyMyInspectionActionScreenWorksCorrectly(String rowID,
																 String description, JSONObject testData) {

		final String inspectionNote = "Test Note";

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR_NO_SHARING, inspectionData);

		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuValidations.menuItemShouldBeVisible(MenuItems.APPROVE, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CHANGE_CUSTOMER, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.EDIT, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.ARCHIVE, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.VIEW, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.EMAIL_INPSECTION, true);

		MenuSteps.selectMenuItem(MenuItems.VIEW);
		VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		viewScreen.waitViewScreenLoaded();
		ScreenNavigationSteps.pressBackButton();
		InspectionSteps.openInspectionToEdit(inspectionNumber);

		VehicleInfoScreenInteractions.selectColor(inspectionData.getVehicleInfo().getVehicleColor());

		VehicleInfoScreenInteractions.setYear(inspectionData.getVehicleInfo().getVehicleYear());
		VNextBaseWizardScreen baseWizardScreen = new VNextBaseWizardScreen();
		baseWizardScreen.clickMenuButton();
		MenuValidations.menuItemShouldBeVisible(MenuItems.SAVE_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CANCEL_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuSteps.closeMenu();
		WizardScreenSteps.navigateToWizardScreen(ScreenType.VISUAL);
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
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		baseWizardScreen.clickMenuButton();
		MenuValidations.menuItemShouldBeVisible(MenuItems.SAVE_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CANCEL_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuSteps.closeMenu();
		VNextNotesScreen notesScreen = availableServicesScreen.clickInspectionNotesOption();
		notesScreen.setNoteText(inspectionNote);
		ScreenNavigationSteps.pressBackButton();
		availableServicesScreen.saveInspectionViaMenu();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.verifyNoteIsPresent(inspectionNote);
		ScreenNavigationSteps.pressBackButton();

		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
		VNextEmailScreen emailScreen = new VNextEmailScreen();
		emailScreen.sentToEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		emailScreen.sendEmail();
		ScreenNavigationSteps.pressBackButton();
	}

}

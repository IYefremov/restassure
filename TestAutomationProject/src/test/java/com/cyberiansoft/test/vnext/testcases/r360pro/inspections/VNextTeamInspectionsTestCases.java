package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InspectionsWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataclasses.VehicleInfoData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.enums.InvoiceStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextSettingsScreen;
import com.cyberiansoft.test.vnext.screens.VNextViewScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextBaseWizardScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.*;
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
		VNextHomeScreen homeScreen = new VNextHomeScreen();
		if (homeScreen.isQueueMessageVisible()) {
			HomeScreenSteps.openSettings();
			VNextSettingsScreen settingsScreen = new VNextSettingsScreen();
			settingsScreen.setManualSendOff();
			ScreenNavigationSteps.pressBackButton();
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

		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		ApproveSteps.drawSignature();
		ApproveValidations.verifyClearButtonVisible(true);
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.CREATE_WORK_ORDER);
		WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
		BaseUtils.waitABit(60 * 1000);
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getMoneyServiceData());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
		WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR2, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoice();
		InvoiceSteps.switchToMyInvoicesView();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
		ScreenNavigationSteps.pressBackButton();
	}


	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyTeamInspectionDisplaysOnTheScreen(String rowID,
															String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();

		InspectionSteps.switchToTeamInspections();
		InspectionsValidations.verifyTeamTabActive(true);
		SearchSteps.searchByText(inspectionNumber);
		InspectionsValidations.verifyInspectionExists(inspectionNumber, true);
		InspectionSteps.switchToMyInspections();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyWhenUserGoBackFromInspectionsScreenToHomeWeSaveLastSelectedMode(String rowID,
																						  String description, JSONObject testData) {

		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		HomeScreenSteps.openInspections();
		InspectionSteps.switchToTeamInspections();
		Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openInspections();
		Assert.assertTrue(inspectionsScreen.isTeamInspectionsViewActive());
		InspectionSteps.switchToMyInspections();
		Assert.assertTrue(inspectionsScreen.isMyInspectionsViewActive());
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openInspections();
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
		ClaimInfoSteps.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		ClaimInfoSteps.setClaimNumber(inspectionData.getInsuranceCompanyData().getClaimNumber());
		ClaimInfoSteps.setPolicyNumber(inspectionData.getInsuranceCompanyData().getPolicyNumber());
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
		final String inspSubNumber = inspectionNumber.substring(6);
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
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.VIEW);
		VNextViewScreen viewScreen = new VNextViewScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		viewScreen.waitViewScreenLoaded();
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

		VNextHomeScreen homeScreen = new VNextHomeScreen();
		HomeScreenSteps.openSettings();
		VNextSettingsScreen settingsScreen = new VNextSettingsScreen();
		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openCreateTeamInspection();
		InspectionSteps.createInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		final String inspectionNumber = InspectionSteps.saveInspection();
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		InspectionSteps.switchToMyInspections();
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber));
		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openStatus();
		StatusScreenSteps.updateMainDB();

		HomeScreenSteps.openInspections();
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
		InspectionSteps.openInspectionToEdit(inspectionNumber);
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN, newVinNumber);

		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
		ClaimInfoSteps.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		InspectionSteps.saveInspection();

		inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
		Assert.assertTrue(inspectionsScreen.isInspectionExists(inspectionNumber), "Can't find inspection: " + inspectionNumber);
		InspectionSteps.openInspectionToEdit(inspectionNumber);

		VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
		vehicleInfoData.setVinNumber(newVinNumber);
		VehicleInfoScreenValidations.validateVehicleInfo(vehicleInfoData);

		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
		ClaimInfoScreenValidations.validateInsuranceCompanyValue(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		InspectionSteps.cancelInspection();
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

		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EMAIL_INPSECTION);
		VNextEmailScreen emailScreen = new VNextEmailScreen();
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
		InspectionSteps.openInspectionMenu(inspectionNumber);
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

		AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
		ScreenNavigationSteps.pressForwardButton();
		SelectedServicesScreenSteps.switchToSelectedService();
		ListServicesValidations.verifyServiceSelected(inspectionData.getServiceData().getServiceName(), true);
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
		AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
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
		inspectionData.getDamagesData().forEach(VisualScreenSteps::addDefaultDamage);

		VisualScreenValidations.numberOfMarksShouldBeEqualTo(inspectionData.getDamagesData().size());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		baseWizardScreen.clickMenuButton();
		MenuValidations.menuItemShouldBeVisible(MenuItems.SAVE_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CANCEL_INPSECTION, true);
		MenuValidations.menuItemShouldBeVisible(MenuItems.NOTES, true);
		MenuSteps.closeMenu();
		WizardScreenSteps.clickNotesMenuButton();
		NotesSteps.setNoteText(inspectionNote);
		ScreenNavigationSteps.pressBackButton();
		InspectionSteps.saveInspection();
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

package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.baseutils.CustomDateProvider;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.core.BrowserType;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.TestCaseData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import com.cyberiansoft.test.vnext.validations.InvoiceInfoScreenValidations;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VNextTeamInvoicesTestCases extends BaseTestClass {

	private final RetailCustomer testCustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
	private final RetailCustomer testCustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");

	@BeforeClass(description = "Team Invoices Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicesTestCasesDataPath();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextCustomersScreen customersScreen = homeScreen.clickCustomersMenuItem();
		customersScreen.switchToRetailMode();
		if (!customersScreen.isCustomerExists(testCustomer1)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testCustomer1);
		}
		if (!customersScreen.isCustomerExists(testCustomer2)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testCustomer2);
		}

		if (!customersScreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testcustomer);
		}
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceInStatusNew(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		ScreenNavigationSteps.pressBackButton();
		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(BrowserType.CHROME);
		webdriver.get(deviceOfficeUrl);


		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();
		Assert.assertEquals(invoicesWebPage.getInvoiceStatus(invoiceNumber), VNextInspectionStatuses.NEW);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanVoidInvoice(String rowID,
											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.VOID);
		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(informationDialog.clickInformationDialogDontVoidButtonAndGetMessage(),
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));
		Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber));
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.VOID);
		informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(informationDialog.clickInformationDialogVoidButtonAndGetMessage(),
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));
		invoicesScreen.waitUntilInvoiceDisappearsFromList(invoiceNumber);
		ScreenNavigationSteps.pressBackButton();
		VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
		statusScreen.updateMainDB();

		HomeScreenSteps.openInvoices();

		Assert.assertFalse(invoicesScreen.isInvoiceExists(invoiceNumber));
		ScreenNavigationSteps.pressBackButton();
		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(BrowserType.CHROME);
		webdriver.get(deviceOfficeUrl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = new OperationsWebPage(webdriver);
		backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicesWebPage = new InvoicesWebPage(webdriver);
		operationsPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(CustomDateProvider.getPreviousLocalizedDateFormattedShort());
		invoicesWebPage.setSearchToDate(CustomDateProvider.getTomorrowLocalizedDateFormattedShort());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
		invoicesWebPage.clickFindButton();

		Assert.assertTrue(invoicesWebPage.isInvoiceDisplayed(invoiceNumber));
		Assert.assertEquals(invoicesWebPage.getInvoiceStatus(invoiceNumber),
				WebConstants.InvoiceStatuses.INVOICESTATUS_VOID.getName());
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifySavedPODisplaysOnCChangePONumberPopup(String rowID,
																String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final String newponumber = "123TEST";
		final String longponumber = "12345678901234567890123456789012345678901234567890123";


		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = new VNextChangeInvoicePONumberDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		changeInvoicePONumberDialog.setInvoicePONumber(newponumber);
		changeInvoicePONumberDialog.clickDontSaveButton();
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), testCaseData.getInvoiceData().getPoNumber());

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		changeInvoicePONumberDialog.changeInvoicePONumber(newponumber);
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), newponumber);

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		changeInvoicePONumberDialog.changeInvoicePONumber(longponumber);
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), longponumber.substring(0, 50));
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyOnActionChangePONumberUserCantErasePONumber(String rowID,
																	  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final String newPONumber = "abcd123";

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoice();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = new VNextChangeInvoicePONumberDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		changeInvoicePONumberDialog.setInvoicePONumber(newPONumber);
		changeInvoicePONumberDialog.clickDontSaveButton();
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), testCaseData.getInvoiceData().getPoNumber());

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		changeInvoicePONumberDialog.setInvoicePONumber("");
		changeInvoicePONumberDialog.clickSaveButton();
		Assert.assertTrue(changeInvoicePONumberDialog.isPONUmberShouldntBeEmptyErrorAppears());
		changeInvoicePONumberDialog.changeInvoicePONumber(newPONumber);
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), newPONumber.toUpperCase());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyChangePOIsNotAvailableIfOptionPOVisibleEqualsNOOnInvoiceType(String rowID,
																					   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR_AUTO);
		final String invoiceNumber = InvoiceSteps.saveInvoice();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuValidations.menuItemShouldBeVisible(MenuItems.CHANGE_PO, false);
		MenuSteps.closeMenu();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanRefreshDeletedPicturesFromMyInvoiceList(String rowID,
																		 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		InvoiceSteps.refreshPictures(invoiceNumber);

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		NotesSteps.addPhotoFromCamera();
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.deleteAllPictures();
		NotesSteps.verifyNoPicturesPresent();
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.refreshPictures(invoiceNumber);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.verifyNoPicturesPresent();
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.waitInvoicesScreenLoaded();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyDeletedPicturesDoesntDisplaysAfterUpdatingApplicationDB_MyInvoiceList(String rowID,
																								String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final int picturesToAdd = 4;
		final int picturesToDelete = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		for (int i = 0; i < picturesToAdd; i++)
			NotesSteps.addPhotoFromCamera();
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		NotesSteps.deletePictures(picturesToDelete);
		NotesSteps.verifyNumberOfPicturesPresent(picturesToAdd - picturesToDelete);
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.waitInvoicesScreenLoaded();
		ScreenNavigationSteps.pressBackButton();

		VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
		statusScreen.updateMainDB();

		homeScreen.clickInvoicesMenuItem();
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.verifyNumberOfPicturesPresent(picturesToAdd - picturesToDelete);
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.waitInvoicesScreenLoaded();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanRefreshDeletedPicturesAfterApprovingInvoice(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		NotesSteps.addPhotoFromCamera();
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.deleteAllPictures();
		NotesSteps.verifyNoPicturesPresent();
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.verifyNoPicturesPresent();
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.refreshPictures(invoiceNumber);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.verifyNoPicturesPresent();
		ScreenNavigationSteps.pressBackButton();

		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanAddNotesForTeamInvoice(String rowID,
														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final String notetext = "Test";
		final String quicknote = "Warranty expired";

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		InvoiceSteps.switchToTeamInvoicesView();
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.setNoteText(notetext);
		NotesSteps.addQuickNote(quicknote);
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.verifyNoteIsPresent(notetext + "\n" + quicknote);
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserDoesntSeeInvoiceWithTeamSharingEqualsNO(String rowID,
																	  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoice();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		invoicesScreen.switchToTeamInvoicesView();
		BaseUtils.waitABit(4000);
		Assert.assertFalse(invoicesScreen.isInvoiceExists(invoiceNumber), "Invoice shouldn't exists: " + invoiceNumber);
		invoicesScreen.switchToMyInvoicesView();
		Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber), "Can't find invoice: " + invoiceNumber);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																		  String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());

		final String msg = emailScreen.sendEmail();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (String invoiceNumber : invoices) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoiceNumber, invoiceNumber + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyCancelEmailInvoice(String rowID,
											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ScreenNavigationSteps.pressBackButton();
		invoicesScreen.unselectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailInvoiceWithDifferentCustomer(String rowID,
															String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		for (int i = 0; i < invoices.length; i++) {
			VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
			workOrdersScreen.switchToTeamWorkordersView();
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.switchToRetailMode();
			if (i == 0) {
				CustomersScreenSteps.selectCustomer(testCustomer1);
			} else {
				CustomersScreenSteps.selectCustomer(testCustomer2);
			}
			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
			HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
			vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
			WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices[i] = InvoiceSteps.saveInvoiceAsFinal();
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		SelectCustomerScreenSteps.selectCustomer(testCustomer2);
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (int i = 1; i < invoices.length; i++) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoices[i], invoices[i] + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoices[i] +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailMyInvoiceWithPopulatedCCAddress(String rowID,
															   String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			WorkOrderSteps.switchToMyWorkOrdersView();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());

		emailScreen.sendEmail();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.clickBackButton();

		for (String invoiceNumber : invoices) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoiceNumber, invoiceNumber + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyToFieldIsRequiredToEmailMyInvoice(String rowID,
															String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		emailScreen.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);

		emailScreen.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		ScreenNavigationSteps.pressBackButton();
		invoicesScreen.unselectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailMyInvoiceList(String rowID,
																	  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final String mailSymbol = "@";
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailSymbol));
		emailScreen.sentToEmailAddress(wrongMail);
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		ScreenNavigationSteps.pressBackButton();
		invoicesScreen.unselectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																			  String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
			workOrdersScreen.switchToTeamWorkordersView();
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.switchToRetailMode();
			CustomersScreenSteps.selectCustomer(testCustomer1);

			VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
			HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
			vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
			WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices) {
			invoicesScreen.selectInvoice(invoiceNumber);
		}
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (String invoiceNumber : invoices) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoiceNumber, invoiceNumber + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyCancelEmailTeamInvoice(String rowID,
												 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			invoices.add(createSimpleInvoice(testCaseData));
		}
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();

		invoicesScreen.switchToTeamInvoicesView();
		invoicesScreen.clickBackButton();
		homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices) {
			//SearchSteps.searchByText(invoiceNumber);
			invoicesScreen.selectInvoice(invoiceNumber);
		}
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ScreenNavigationSteps.pressBackButton();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyToFieldIsRequiredToEmailTeamInvoice(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		invoicesScreen.clickBackButton();
		homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		emailScreen.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);

		emailScreen.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailScreen.clickScreenBackButton();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailTeamInvoiceList(String rowID,
																		String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final String mailSymbol = "@";

		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		invoicesScreen.clickBackButton();
		homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailSymbol));
		emailScreen.sentToEmailAddress(wrongMail);
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailScreen.clickScreenBackButton();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToTeamInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceWithDifferentCustomer(String rowID,
																String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		for (int i = 0; i < invoices.length; i++) {
			VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
			workOrdersScreen.switchToTeamWorkordersView();
			WorkOrderSteps.clickAddWorkOrderButton();
			CustomersScreenSteps.switchToRetailMode();
			if (i == 0) {
				CustomersScreenSteps.selectCustomer(testCustomer1);
			} else {
				CustomersScreenSteps.selectCustomer(testCustomer2);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
			HelpingScreenInteractions.dismissHelpingScreenIfPresent();
			VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			availableServicesScreen.selectService(testCaseData.getWorkOrderData().getMoneyServiceData().getServiceName());
			vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
			WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
			invoices[i] = InvoiceSteps.saveInvoiceAsFinal();
			ScreenNavigationSteps.pressBackButton();
		}

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		invoicesScreen.clickBackButton();
		homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		SelectCustomerScreenSteps.selectCustomer(testCustomer2);
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (int i = 1; i < invoices.length; i++) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoices[i], invoices[i] + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoices[i] +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyApproveIconDoesntDisplaysAfterApproveInvoice(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		WorkOrderSteps.clickAddWorkOrderButton();
		CustomersScreenSteps.switchToRetailMode();
		CustomersScreenSteps.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		servicesScreen.selectService(testCaseData.getWorkOrderData().getServiceData().getServiceName());
		final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
		servicesScreen.saveWorkOrderViaMenu();
		ScreenNavigationSteps.pressBackButton();

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InvoiceSteps.openMenu(invoiceNumber);
		MenuValidations.menuItemShouldBeVisible(MenuItems.APPROVE, false);
		MenuSteps.closeMenu();
		InvoiceSteps.waitInvoicesScreenLoaded();
		ScreenNavigationSteps.pressBackButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateSeparateInvoicesFromOneWO(String rowID,
																 String description, JSONObject testData) {

		final int invoiceStringLenght = 6;

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.createSeparateInvoice(workOrderNumber);
		ScreenNavigationSteps.pressBackButton();

		homeScreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int newLastInvoiceNuber = Integer.parseInt(newInvoiceNumber.substring(invoiceStringLenght));
		Assert.assertEquals(newLastInvoiceNuber, lastInvoiceNumber + 1);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateSeparateInvoicesFromSeveralWOs(String rowID,
																	  String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final int numberInvoicesToCreate = 3;
		ArrayList<String> workOrders = new ArrayList<>();

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData));

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		invoicesScreen.clickBackButton();
		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.createSeparateInvoices(workOrders);
		workOrdersScreen.clickBackButton();

		homeScreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int newLastInvoiceNumber = Integer.parseInt(newInvoiceNumber.substring(invoiceStringLenght));
		Assert.assertEquals(newLastInvoiceNumber, lastInvoiceNumber + numberInvoicesToCreate);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateSingleInvoicesFromOneWO(String rowID,
															   String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final String poNumber = "123po";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
		InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderNumber, true);
		InvoiceInfoSteps.setInvoicePONumber(poNumber);
		final String newInvoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
		final int newLastInvoiceNuber = Integer.parseInt(newInvoiceNumber.substring(invoiceStringLenght));
		Assert.assertEquals(newLastInvoiceNuber, lastInvoiceNumber + 1);
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateSingleInvoicesFromSeveralWOs(String rowID,
																	String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final String ponumber = "123po";
		final int numberInvoicesToCreate = 3;
		ArrayList<String> workOrders = new ArrayList<>();

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (int i = 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData));

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		ScreenNavigationSteps.pressBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		for (String workOrderNumber : workOrders)
			workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSingleInvoiceButton();
		workOrders.forEach(workOrderId -> InvoiceInfoScreenValidations.validateWorkOrderSelectedForInvoice(workOrderId, true));
		InvoiceInfoSteps.setInvoicePONumber(ponumber);
		final String newInvoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
		final int newLastInvoiceNuber = Integer.parseInt(newInvoiceNumber.substring(invoiceStringLenght));
		Assert.assertEquals(newLastInvoiceNuber, lastInvoiceNumber + 1);
		ScreenNavigationSteps.pressBackButton();
	}

	//@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCancelInvoiceCreationUsingHardwareBackButton(String rowID,
																			  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData);

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		ScreenNavigationSteps.pressBackButton();
		workOrdersScreen.unselectWorkOrder(workOrderNumber);
		ScreenNavigationSteps.pressBackButton();

	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCantCreateSeparateInvoiceIfInvoiceTypeIsNotAssignedToWO(String rowID,
																					  String description, JSONObject testData) {

		final String NO_AVAILABLE_WO_MESSAGE = "Please make sure that all selected work orders can be used for auto invoice creation";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSeparateInvoicesButton();
		Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), NO_AVAILABLE_WO_MESSAGE);
		workOrdersScreen.unselectWorkOrder(workOrderNumber);
		workOrdersScreen.clickBackButton();
		homeScreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		Assert.assertEquals(newInvoiceNumber, invoiceNumber);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyMotesDisplaysOnTeamInvoiceListAfterAddingOnMyInvoiceList(String rowID,
																				   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final String notetext = "Test";
		final String quicknote = "Warranty expired";

		final int picturesToAdd = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		InvoiceSteps.switchToMyInvoicesView();
		InvoiceSteps.switchToTeamInvoicesView();
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.setNoteText(notetext);
		NotesSteps.addQuickNote(quicknote);
		NotesSteps.verifyNoteIsPresent(notetext + "\n" + quicknote);
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		for (int i = 0; i < picturesToAdd; i++)
			NotesSteps.addPhotoFromCamera();
		ScreenNavigationSteps.pressBackButton();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		NotesSteps.verifyNumberOfPicturesPresent(picturesToAdd);
		ScreenNavigationSteps.pressBackButton();


		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);
		NotesSteps.verifyNoteIsPresent(notetext + "\n" + quicknote);
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanAddSeveralQuickNotes(String rowID,
													  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		String result = NotesSteps.addQuickNotesFromListByCount(10);
		ScreenNavigationSteps.pressBackButton();
		NotesSteps.verifyNoteIsPresent(result);
		ScreenNavigationSteps.pressBackButton();
	}

	public String createSimpleWorkOrder(WorkOrderTypes wotype, TestCaseData testCaseData) {
		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		homeScreen.clickWorkOrdersMenuItem();
		WorkOrderSteps.clickAddWorkOrderButton();
		CustomersScreenSteps.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		workOrderTypesList.selectWorkOrderType(wotype);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		HelpingScreenInteractions.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(testCaseData.getWorkOrderData().getVehicleInfoData());
		final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		servicesScreen.selectServices(testCaseData.getWorkOrderData().getServicesList());
		servicesScreen.saveWorkOrderViaMenu();
		ScreenNavigationSteps.pressBackButton();
		return workOrderNumber;
	}


	private String createSimpleInvoice(TestCaseData testCaseData) {
		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		InvoiceInfoSteps.setInvoicePONumber(testCaseData.getInvoiceData().getPoNumber());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		ScreenNavigationSteps.pressBackButton();
		return invoiceNumber;
	}
}

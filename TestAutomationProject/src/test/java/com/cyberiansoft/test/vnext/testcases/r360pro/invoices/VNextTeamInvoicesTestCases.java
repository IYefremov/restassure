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
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.enums.InvoiceStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.VNextChangeInvoicePONumberDialog;
import com.cyberiansoft.test.vnext.screens.VNextEmailScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.customers.CustomersScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InvoiceInfoScreenValidations;
import com.cyberiansoft.test.vnext.validations.InvoicesScreenValidations;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
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

		HomeScreenSteps.openCustomers();
		CustomerServiceSteps.createCustomerIfNotExist(testCustomer1);
		CustomerServiceSteps.createCustomerIfNotExist(testCustomer2);
		CustomerServiceSteps.createCustomerIfNotExist(testcustomer);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceInStatusNew(String rowID,
														  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
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
		Assert.assertEquals(invoicesWebPage.getInvoiceStatus(invoiceNumber), InspectionStatus.NEW);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanVoidInvoice(String rowID,
											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.VOID);
		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertEquals(informationDialog.clickInformationDialogDontVoidButtonAndGetMessage(),
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));
		InvoicesScreenValidations.validateInvoiceExists(invoiceNumber, true);
		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.VOID);
		Assert.assertEquals(informationDialog.clickInformationDialogVoidButtonAndGetMessage(),
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));
		//invoicesScreen.waitUntilInvoiceDisappearsFromList(invoiceNumber);
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openStatus();
		StatusScreenSteps.updateMainDB();

		HomeScreenSteps.openInvoices();

		InvoicesScreenValidations.validateInvoiceExists(invoiceNumber, false);
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

		final String newPONumber = "123TEST";
		final String longPONumber = "12345678901234567890123456789012345678901234567890123";


		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = new VNextChangeInvoicePONumberDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		changeInvoicePONumberDialog.setInvoicePONumber(newPONumber);
		changeInvoicePONumberDialog.clickDontSaveButton();
		InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, testCaseData.getInvoiceData().getPoNumber());

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		changeInvoicePONumberDialog.changeInvoicePONumber(newPONumber);
		InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, newPONumber);

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		changeInvoicePONumberDialog.changeInvoicePONumber(longPONumber);
		InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, longPONumber.substring(0, 50));
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyOnActionChangePONumberUserCantErasePONumber(String rowID,
																	  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final String newPONumber = "abcd123";

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR2, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoice();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = new VNextChangeInvoicePONumberDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		changeInvoicePONumberDialog.setInvoicePONumber(newPONumber);
		changeInvoicePONumberDialog.clickDontSaveButton();
		InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, testCaseData.getInvoiceData().getPoNumber());

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.CHANGE_PO);
		changeInvoicePONumberDialog.setInvoicePONumber("");
		changeInvoicePONumberDialog.clickSaveButton();
		Assert.assertTrue(changeInvoicePONumberDialog.isPONUmberShouldntBeEmptyErrorAppears());
		changeInvoicePONumberDialog.changeInvoicePONumber(newPONumber);
		InvoicesScreenValidations.validateInvoicePONumber(invoiceNumber, newPONumber.toUpperCase());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyChangePOIsNotAvailableIfOptionPOVisibleEqualsNOOnInvoiceType(String rowID,
																					   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR_AUTO);
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);
		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
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

		HomeScreenSteps.openStatus();
		StatusScreenSteps.updateMainDB();

		HomeScreenSteps.openInvoices();
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoice();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
		InvoiceSteps.switchToTeamInvoicesView();
		BaseUtils.waitABit(4000);
		InvoicesScreenValidations.validateInvoiceExists(invoiceNumber, false);
		InvoiceSteps.switchToMyInvoicesView();
		InvoicesScreenValidations.validateInvoiceExists(invoiceNumber, true);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																		  String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());

		final String msg = emailScreen.sendEmail();
		InvoiceSteps.unSelectAllSelectedInvoices();
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
	public void testVerifyCancelEmailInvoice(String rowID,
											 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);
			HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.unSelectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailInvoiceWithDifferentCustomer(String rowID,
															String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			HomeScreenSteps.openCreateTeamWorkOrder();
			CustomersScreenSteps.switchToRetailMode();
			if (i == 0) {
				CustomersScreenSteps.selectCustomer(testCustomer1);
			} else {
				CustomersScreenSteps.selectCustomer(testCustomer2);
			}
			WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getMoneyServiceData());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
			WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}
		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		SelectCustomerScreenSteps.selectCustomer(testCustomer2);
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		InvoiceSteps.unSelectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (int i = 1; i < invoices.size(); i++) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoices.get(i), invoices.get(i) + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoices.get(i) +
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

		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());

		emailScreen.sendEmail();
		InvoiceSteps.unSelectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();

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

		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		emailScreen.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);

		emailScreen.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.unSelectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailMyInvoiceList(String rowID,
																	  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final String mailSymbol = "@";
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailSymbol));
		emailScreen.sentToEmailAddress(wrongMail);
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.unSelectAllSelectedInvoices();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																			  String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			HomeScreenSteps.openCreateMyWorkOrder();
			WorkOrderSteps.createWorkOrder(testCustomer1, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getMoneyServiceData());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
			WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToTeamInvoicesView();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		InvoiceSteps.unSelectAllSelectedInvoices();
		InvoiceSteps.switchToMyInvoicesView();
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

		for (int i = 0; i < invoicesToCreate; i++) {
			invoices.add(createSimpleInvoice(testCaseData));
		}
		HomeScreenSteps.openInvoices();

		InvoiceSteps.switchToTeamInvoicesView();
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		ScreenNavigationSteps.pressBackButton();
		InvoiceSteps.unSelectAllSelectedInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyToFieldIsRequiredToEmailTeamInvoice(String rowID,
															  String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToTeamInvoicesView();
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		emailScreen.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);

		emailScreen.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailScreen.clickScreenBackButton();
		InvoiceSteps.unSelectAllSelectedInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailTeamInvoiceList(String rowID,
																		String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		final String mailSymbol = "@";

		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

			HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToTeamInvoicesView();
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailSymbol));
		emailScreen.sentToEmailAddress(wrongMail);
		String msg = emailScreen.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailScreen.clickScreenBackButton();
		InvoiceSteps.unSelectAllSelectedInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceWithDifferentCustomer(String rowID,
																String description, JSONObject testData) throws Exception {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		for (int i = 0; i < invoicesToCreate; i++) {
			HomeScreenSteps.openCreateTeamWorkOrder();
			CustomersScreenSteps.switchToRetailMode();
			if (i == 0) {
				CustomersScreenSteps.selectCustomer(testCustomer1);
			} else {
				CustomersScreenSteps.selectCustomer(testCustomer2);
			}
			WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getMoneyServiceData());
			WizardScreenSteps.navigateToWizardScreen(ScreenType.WORKORDER_SUMMARY);
			WorkOrderSummarySteps.createInvoiceOptionAndSaveWO();

			InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
			invoices.add(InvoiceSteps.saveInvoiceAsFinal());
			ScreenNavigationSteps.pressBackButton();
		}

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToTeamInvoicesView();
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openInvoices();
		invoices.forEach(invoiceNumber -> InvoiceSteps.selectInvoice(invoiceNumber));
		InvoiceSteps.clickMultiSelectActionsSendEmail();
		SelectCustomerScreenSteps.selectCustomer(testCustomer2);
		VNextEmailScreen emailScreen = new VNextEmailScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		InvoiceSteps.unSelectAllSelectedInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		ScreenNavigationSteps.pressBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (int i = 1; i < invoices.size(); i++) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoices.get(i), invoices.get(i) + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoices.get(i) +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
		}
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyApproveIconDoesntDisplaysAfterApproveInvoice(String rowID,
																	   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		HomeScreenSteps.openCreateMyWorkOrder();
		WorkOrderSteps.createWorkOrder(testcustomer, WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData.getWorkOrderData());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		AvailableServicesScreenSteps.selectService(testCaseData.getWorkOrderData().getServiceData());
		final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		InvoicesScreenValidations.validateInvoiceStatus(invoiceNumber, InvoiceStatus.NEW);
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData);

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openWorkOrders();
		VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
		workOrdersScreen.createSeparateInvoice(workOrderNumber);
		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openInvoices();
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

		for (int i = 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData));

		HomeScreenSteps.openInvoices();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		InvoiceSteps.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openWorkOrders();
		VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
		workOrdersScreen.createSeparateInvoices(workOrders);
		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openInvoices();
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData);

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openWorkOrders();
		VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
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
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCanCreateSingleInvoicesFromSeveralWOs(String rowID,
																	String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final String ponumber = "123po";
		final int numberInvoicesToCreate = 3;
		ArrayList<String> workOrders = new ArrayList<>();

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		for (int i = 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, testCaseData));

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.parseInt(invoiceNumber.substring(invoiceStringLenght));

		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openWorkOrders();
		VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
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

	@Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
	public void testVerifyUserCantCreateSeparateInvoiceIfInvoiceTypeIsNotAssignedToWO(String rowID,
																					  String description, JSONObject testData) {

		final String NO_AVAILABLE_WO_MESSAGE = "Please make sure that all selected work orders can be used for auto invoice creation";

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openInvoices();
		InvoiceSteps.switchToMyInvoicesView();
		VNextInvoicesScreen invoicesScreen = new VNextInvoicesScreen();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();

		ScreenNavigationSteps.pressBackButton();

		HomeScreenSteps.openWorkOrders();
		VNextWorkOrdersScreen workOrdersScreen = new VNextWorkOrdersScreen();
		workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		informationDialog.clickSeparateInvoicesButton();
		Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(), NO_AVAILABLE_WO_MESSAGE);
		workOrdersScreen.unselectWorkOrder(workOrderNumber);
		ScreenNavigationSteps.pressBackButton();
		HomeScreenSteps.openInvoices();
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
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

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);

		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();

		InvoiceSteps.openMenu(invoiceNumber);
		MenuSteps.selectMenuItem(MenuItems.NOTES);

		String result = NotesSteps.addQuickNotesFromListByCount(10);
		ScreenNavigationSteps.pressBackButton();
		NotesSteps.verifyNoteIsPresent(result);
		ScreenNavigationSteps.pressBackButton();
	}

	public String createSimpleWorkOrder(WorkOrderTypes wotype, TestCaseData testCaseData) {
		HomeScreenSteps.openCreateMyWorkOrder();
		WorkOrderSteps.createWorkOrder(testcustomer, wotype, testCaseData.getWorkOrderData());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		AvailableServicesScreenSteps.selectServices(testCaseData.getWorkOrderData().getServicesList());
		final String workOrderNumber = WorkOrderSteps.saveWorkOrder();
		ScreenNavigationSteps.pressBackButton();
		return workOrderNumber;
	}

	private String createSimpleInvoice(TestCaseData testCaseData) {
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_CREATE_INVOICE, testCaseData);
		HomeScreenSteps.openCreateMyInvoice(workOrderNumber);
		InvoiceSteps.createInvoice(InvoiceTypes.O_KRAMAR, testCaseData.getInvoiceData());
		final String invoiceNumber = InvoiceSteps.saveInvoiceAsFinal();
		ScreenNavigationSteps.pressBackButton();
		return invoiceNumber;
	}
}

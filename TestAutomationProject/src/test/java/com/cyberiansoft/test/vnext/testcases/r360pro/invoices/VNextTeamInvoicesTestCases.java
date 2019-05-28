package com.cyberiansoft.test.vnext.testcases.r360pro.invoices;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.WebDriverUtils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeLoginWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.InvoicesWebPage;
import com.cyberiansoft.test.bo.pageobjects.webpages.OperationsWebPage;
import com.cyberiansoft.test.bo.utils.BackOfficeUtils;
import com.cyberiansoft.test.bo.utils.WebConstants;
import com.cyberiansoft.test.dataclasses.Invoice;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.r360.InvoiceDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.invoicestypes.InvoiceTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VNextTeamInvoicesTestCases extends BaseTestCaseTeamEditionRegistration {

	private final RetailCustomer testCustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
	private final RetailCustomer testCustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");

	@BeforeClass(description="Team Invoices Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicesTestCasesDataPath();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextCustomersScreen customersScreen = homeScreen.clickCustomersMenuItem();
		customersScreen.switchToRetailMode();
		if (!customersScreen.isCustomerExists(testCustomer1)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testCustomer1);
			customersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		}
		if (!customersScreen.isCustomerExists(testCustomer2)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testCustomer2);
			customersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		}

		if (!customersScreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
			newCustomerScreen.createNewCustomer(testcustomer);
			customersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		}
		customersScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceInStatusNew(String rowID,
														  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		VNextApproveScreen approveScreen = invoiceMenuScreen.clickApproveInvoiceMenuItem();
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		invoicesScreen.clickBackButton();
		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicesWebPage = operationsPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		invoicesWebPage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.clickFindButton();
		Assert.assertEquals(invoicesWebPage.getInvoiceStatus(invoiceNumber), VNextInspectionStatuses.NEW);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanVoidInvoice(String rowID,
														  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		invoiceMenuScreen.clickVoidInvoiceMenuItem();
		VNextInformationDialog informationdialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(informationdialog.clickInformationDialogDontVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber));
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		invoiceMenuScreen.clickVoidInvoiceMenuItem();
		informationdialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(informationdialog.clickInformationDialogVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoiceNumber));
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.waitUntilInvoiceDisappearsFromList(invoiceNumber);
		homeScreen = invoicesScreen.clickBackButton();
		VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
		statusScreen.updateMainDB();
		//homeScreen = statusScreen.clickBackButton();
		
		invoicesScreen = homeScreen.clickInvoicesMenuItem();
		Assert.assertFalse(invoicesScreen.isInvoiceExists(invoiceNumber));
		invoicesScreen.clickBackButton();
		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);

		BackOfficeLoginWebPage loginPage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginPage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backOfficeHeader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationsPage = backOfficeHeader.clickOperationsLink();

		InvoicesWebPage invoicesWebPage = operationsPage.clickInvoicesLink();
		invoicesWebPage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicesWebPage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		invoicesWebPage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoicesWebPage.setSearchInvoiceNumber(invoiceNumber);
		invoicesWebPage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
		invoicesWebPage.clickFindButton();
		
		Assert.assertTrue(invoicesWebPage.isInvoiceDisplayed(invoiceNumber));
		Assert.assertEquals(invoicesWebPage.getInvoiceStatus(invoiceNumber), 
				WebConstants.InvoiceStatuses.INVOICESTATUS_VOID.getName());
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifySavedPODisplaysOnCChangePONumberPopup(String rowID,
											 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		final String newponumber = "123TEST";
		final String longponumber = "12345678901234567890123456789012345678901234567890123";
		

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoiceMenuScreen.clickInvoiceChangePONumberMenuItem();
		changeInvoicePONumberDialog.setInvoicePONumber(newponumber);
		changeInvoicePONumberDialog.clickDontSaveButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), invoice.getInvoiceData().getInvoicePONumber());
		
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		changeInvoicePONumberDialog = invoiceMenuScreen.clickInvoiceChangePONumberMenuItem();
		invoicesScreen = changeInvoicePONumberDialog.changeInvoicePONumber(newponumber);
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), newponumber);
		
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		changeInvoicePONumberDialog = invoiceMenuScreen.clickInvoiceChangePONumberMenuItem();
		invoicesScreen = changeInvoicePONumberDialog.changeInvoicePONumber(longponumber);
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), longponumber.substring(0, 50) );
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyWeHidePopupAndKeyboardIfUserClickHardwareBackButton(String rowID,
																String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoiceMenuScreen.clickInvoiceChangePONumberMenuItem();
		changeInvoicePONumberDialog.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());

		AppiumUtils.clickHardwareBackButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyOnActionChangePONumberUserCantErasePONumber(String rowID,
																			  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final String newponumber = "abcd123";
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoice();
		
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoiceMenuScreen.clickInvoiceChangePONumberMenuItem();
		changeInvoicePONumberDialog.setInvoicePONumber(newponumber);
		changeInvoicePONumberDialog.clickDontSaveButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), invoice.getInvoiceData().getInvoicePONumber());
		
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		changeInvoicePONumberDialog = invoiceMenuScreen.clickInvoiceChangePONumberMenuItem();
		changeInvoicePONumberDialog.setInvoicePONumber("");
		changeInvoicePONumberDialog.clickSaveButton();
		Assert.assertTrue(changeInvoicePONumberDialog.isPONUmberShouldntBeEmptyErrorAppears());
		invoicesScreen = changeInvoicePONumberDialog.changeInvoicePONumber(newponumber);
		
		Assert.assertEquals(invoicesScreen.getInvoicePONumberValue(invoiceNumber), newponumber.toUpperCase());
		
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyChangePOIsNotAvailableIfOptionPOVisibleEqualsNOOnInvoiceType(String rowID,
																	  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR_AUTO);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoice();
		
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		Assert.assertFalse(invoiceMenuScreen.isInvoiceChangePONumberMenuItemExists());
		invoicesScreen = invoiceMenuScreen.clickCloseInvoiceMenuButton();
		
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanRefreshDeletedPicturesFromMyInvoiceList(String rowID,
																					   String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		invoiceMenuScreen.refreshInvoicePictures();
		
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);		
		VNextNotesScreen notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesScreen.addFakeImageNote();
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesScreen.deletePictureFromNotes();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		invoiceMenuScreen.refreshInvoicePictures();
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyDeletedPicturesDoesntDisplaysAfterUpdatingApplicationDB_MyInvoiceList(String rowID,
																		 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);	
		VNextNotesScreen notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesScreen.addFakeImageNote();
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesScreen.deletePictureFromNotes();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		homeScreen = invoicesScreen.clickBackButton();

		VNextStatusScreen statusScreen = homeScreen.clickStatusMenuItem();
		statusScreen.updateMainDB();
		//homeScreen = statusScreen.clickBackButton();
		
		invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanRefreshDeletedPicturesAfterApprovingInvoice(String rowID,
																								String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);	
		VNextNotesScreen notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesScreen.addFakeImageNote();
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesScreen.deletePictureFromNotes();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		VNextApproveScreen approveScreen = invoiceMenuScreen.clickApproveInvoiceMenuItem();
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		invoiceMenuScreen.refreshInvoicePictures();
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesScreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddNotesForTeamInvoice(String rowID,
																			 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final String notetext = "Test";
		final String quicknote = "Warranty expired";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.switchToMyWorkordersView();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		invoicesScreen.switchToTeamInvoicesView();
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);	
		VNextNotesScreen notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		notesScreen.setNoteText(notetext);
		notesScreen.addQuickNote(quicknote);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		notesScreen = invoiceMenuScreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesScreen.getSelectedNotes(), notetext + "\n" + quicknote);
		notesScreen.clickScreenBackButton();
		
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.switchToMyInvoicesView();
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserDoesntSeeInvoiceWithTeamSharingEqualsNO(String rowID,
														String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);
		
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoice();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		invoicesScreen.switchToTeamInvoicesView();
		Assert.assertFalse(invoicesScreen.isInvoiceExists(invoiceNumber), "Invoice shouldn't exists: " + invoiceNumber);
		invoicesScreen.switchToMyInvoicesView();
		Assert.assertTrue(invoicesScreen.isInvoiceExists(invoiceNumber), "Can't find invoice: " + invoiceNumber);
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																	  String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());

		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());

		final String msg = emailScreen.sendEmail();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyCancelEmailInvoice(String rowID,
																		  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailInvoiceWithDifferentCustomer(String rowID,
											 String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
	
		for (int i = 0; i < invoices.length; i ++) {
			VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
			workOrdersScreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
			customersScreen.switchToRetailMode();
			if (i == 0) {
				customersScreen.selectCustomer(testCustomer1);
			} else {
				customersScreen.selectCustomer(testCustomer2);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleInfoScreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
			VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
			workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices[i] = invoiceInfoScreen.getInvoiceNumber();
			VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextCustomersScreen customersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersScreen.selectCustomer(testCustomer2);
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailMyInvoiceWithPopulatedCCAddress(String rowID,
															String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		
		emailScreen.sendEmail();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyToFieldIsRequiredToEmailMyInvoice(String rowID,
															   String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		emailScreen.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg= emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailScreen.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg= emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailScreen.clickScreenBackButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailMyInvoiceList(String rowID,
															String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		final String mailsymbol = "@";
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailsymbol));
		emailScreen.sentToEmailAddress(wrongMail);		
		String msg= emailScreen.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailScreen.clickScreenBackButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																	  String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
			workOrdersScreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(testCustomer1);

			VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
			workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleInfoScreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
			VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
			workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		invoicesScreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (String invoiceNumber : invoices) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoiceNumber, invoiceNumber + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoiceNumber +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());;
		}
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyCancelEmailTeamInvoice(String rowID,
																			  String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			invoices.add(createSimpleInvoice(invoice));
		}
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices) {
			//invoicesScreen.searchInvoiceByFreeText(invoiceNumber);
			invoicesScreen.selectInvoice(invoiceNumber);
		}
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyToFieldIsRequiredToEmailTeamInvoice(String rowID,
												 String description, JSONObject testData)  {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		emailScreen.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg= emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailScreen.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg= emailScreen.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailScreen.clickScreenBackButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailTeamInvoiceList(String rowID,
															  String description, JSONObject testData)  {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final String mailSymbol = "@";
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
			invoicesScreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
			workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceInfoScreen.getInvoiceNumber());
			invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailScreen.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailSymbol));
		emailScreen.sentToEmailAddress(wrongMail);		
		String msg= emailScreen.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailScreen.clickScreenBackButton();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToTeamInvoicesView();
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceWithDifferentCustomer(String rowID,
																		String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
	
		for (int i = 0; i < invoices.length; i ++) {
			VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
			workOrdersScreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
			customersScreen.switchToRetailMode();
			if (i == 0) {
				customersScreen.selectCustomer(testCustomer1);
			} else {
				customersScreen.selectCustomer(testCustomer2);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleInfoScreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleInfoScreen.changeScreen(ScreenType.WORKORDER_SUMMARY);
			VNextWorkOrderSummaryScreen workOrderSummaryScreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
			workOrderSummaryScreen.clickCreateInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices[i] = invoiceInfoScreen.getInvoiceNumber();
			VNextInvoicesScreen invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
			homeScreen = invoicesScreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesScreen.selectInvoice(invoiceNumber);
		invoicesScreen.clickOnSelectedInvoicesMailButton();
		VNextCustomersScreen customersScreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersScreen.selectCustomer(testCustomer2);
		VNextEmailScreen emailScreen = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailScreen.sentToEmailAddress(nada.getEmailId());
		final String msg = emailScreen.sendEmail();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesScreen.unselectAllSelectedInvoices();
		invoicesScreen.switchToMyInvoicesView();
		invoicesScreen.clickBackButton();
		Assert.assertEquals(msg, VNextAlertMessages.YOUR_EMAIL_MESSAGES_HAVE_BEEEN_ADDDED_TO_THE_QUEUE);

		for (int i = 1; i < invoices.length; i++) {
			NadaEMailService.MailSearchParametersBuilder searchParametersBuilder = new NadaEMailService.MailSearchParametersBuilder()
					.withSubjectAndAttachmentFileName("Invoice " + invoices[i], invoices[i] + ".pdf");
			Assert.assertTrue(nada.waitForMessage(searchParametersBuilder), "Can't find invoice: " + invoices[i] +
					" in mail box " + nada.getEmailId() + ". At time " +
					LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());;
		}
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyApproveIconDoesntDisplaysAfterApproveInvoice(String rowID,
																String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
		wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
		final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
		workOrdersScreen = servicesScreen.saveWorkOrderViaMenu();
		homeScreen = workOrdersScreen.clickBackButton();

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getInvoiceStatusValue(invoiceNumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		VNextApproveScreen approveScreen = invoiceMenuScreen.clickApproveInvoiceMenuItem();
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		invoicesScreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceMenuScreen = invoicesScreen.clickOnInvoiceByInvoiceNumber(invoiceNumber);
		Assert.assertFalse(invoiceMenuScreen.isApproveInvoiceMenuItemExists());
		invoiceMenuScreen.clickCloseInvoiceMenuButton();
		invoicesScreen.clickBackButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateSeparateInvoicesFromOneWO(String rowID,
																	   String description, JSONObject testData) {

		final int invoiceStringLenght = 6;

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.createSeparateInvoice(workOrderNumber);
		workOrdersScreen.clickBackButton();

		invoicesScreen = homeScreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNumber+1);
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateSeparateInvoicesFromSeveralWOs(String rowID,
																 String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final int numberInvoicesToCreate = 3;
		ArrayList<String> workOrders = new ArrayList<>();

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i< numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice));

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.createSeparateInvoices(workOrders);
		workOrdersScreen.clickBackButton();

		invoicesScreen = homeScreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNumber+numberInvoicesToCreate);
		invoicesScreen.clickBackButton();
	}

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCreateSingleInvoicesFromOneWO(String rowID,
                                                                 String description, JSONObject testData) {

        final int invoiceStringLenght = 6;
        final String poNumber = "123po";

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice);

        VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
        final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
        final int lastInvoiceNumber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

        invoicesScreen.clickBackButton();

        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        workOrdersScreen.selectWorkOrder(workOrderNumber);
        workOrdersScreen.clickCreateInvoiceIcon();

        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickSingleInvoiceButton();
        VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(invoiceInfoScreen.isWorkOrderSelectedForInvoice(workOrderNumber));
        invoiceInfoScreen.setInvoicePONumber(poNumber);
        final String newInvoiceNumber = invoiceInfoScreen.getInvoiceNumber();
        invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
        final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
        Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNumber+1);
        invoicesScreen.clickBackButton();
    }

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateSingleInvoicesFromSeveralWOs(String rowID,
															   String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final String ponumber = "123po";
		final int numberInvoicesToCreate = 3;
		ArrayList<String> workOrders = new ArrayList<>();

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i= 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice));

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		for (String workOrderNumber : workOrders)
			workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String workOrderNumber : workOrders)
			Assert.assertTrue(invoiceInfoScreen.isWorkOrderSelectedForInvoice(workOrderNumber));
		invoiceInfoScreen.setInvoicePONumber(ponumber);
		final String newInvoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoicesScreen = invoiceInfoScreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNumber+1);
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCancelInvoiceCreationUsingHardwareBackButton(String rowID,
															   String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice);

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		AppiumUtils.clickHardwareBackButton();
		new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

	}

	//@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCancelInvoiceCreationUsingPopup_SeparateInvoices(String rowID,
																			  String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final int numberInvoicesToCreate = 3;
		ArrayList<String> workOrders = new ArrayList<>();

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i= 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice));

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNumber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.switchToMyWorkordersView();
		for (String workOrderNumber : workOrders)
			workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSeparateInvoicesButton();

        workOrdersScreen.cancelCreatingSeparateInvoice();

		workOrdersScreen.clickBackButton();
		invoicesScreen = homeScreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();

		Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNumber+1);
		invoicesScreen.clickBackButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateSeparateInvoiceIfInvoiceTypeIsNotAssignedToWO(String rowID,
															   String description, JSONObject testData) {

		final String NO_AVAILABLE_WO_MESSAGE = "No available Work Order for Invoice creation";

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		workOrdersScreen.selectWorkOrder(workOrderNumber);
		workOrdersScreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSeparateInvoicesButton();
		workOrdersScreen.waitForWorkOrderScreenInfoMessage(NO_AVAILABLE_WO_MESSAGE);

		workOrdersScreen.clickBackButton();
		homeScreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		Assert.assertEquals( newInvoiceNumber, invoiceNumber);
		invoicesScreen.clickBackButton();
	}

	public String createSimpleWorkOrder(WorkOrderTypes wotype, Invoice invoice) {
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersScreen = workOrdersScreen.clickAddWorkOrderButton();
		customersScreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
		workOrderTypesList.selectWorkOrderType(wotype);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();
		workOrdersScreen = vehicleInfoScreen.saveWorkOrderViaMenu();
		workOrdersScreen.clickBackButton();
		return workOrderNumber;
	}


	private String createSimpleInvoice(Invoice invoice) {
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String workOrderNumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

		VNextInvoicesScreen invoicesScreen = homeScreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workOrdersScreen = invoicesScreen.clickAddInvoiceButton();
		workOrdersScreen.clickCreateInvoiceFromWorkOrder(workOrderNumber);

		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		VNextInvoiceInfoScreen invoiceInfoScreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceInfoScreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoiceNumber = invoiceInfoScreen.getInvoiceNumber();
		invoiceInfoScreen.saveInvoiceAsFinal();
		invoicesScreen.clickBackButton();
		return invoiceNumber;
	}
}

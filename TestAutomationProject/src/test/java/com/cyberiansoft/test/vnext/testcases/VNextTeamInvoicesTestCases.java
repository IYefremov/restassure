package com.cyberiansoft.test.vnext.testcases;

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
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.email.getnada.NadaEMailService;
import com.cyberiansoft.test.vnext.config.VNextConfigInfo;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInvoiceMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInvoiceTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInvoicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class VNextTeamInvoicesTestCases extends BaseTestCaseTeamEditionRegistration {
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-invoices-test-cases-data.json";

	final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
	final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");

	@BeforeClass(description="Team Invoices Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);

		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToRetailMode();
		if (!customersscreen.isCustomerExists(testcustomer1)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer1);
			customersscreen = new VNextCustomersScreen(appiumdriver);
		}
		if (!customersscreen.isCustomerExists(testcustomer2)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer2);
			customersscreen = new VNextCustomersScreen(appiumdriver);
		}
		customersscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceInStatusNew(String rowID,
														  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		invoicesscreen.clickBackButton();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicespage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoicespage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.clickFindButton();
		Assert.assertEquals(invoicespage.getInvoiceStatus(invoicenumber), VNextInspectionStatuses.NEW);
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanVoidInvoice(String rowID,
														  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.clickVoidInvoiceMenuItem();
		VNextInformationDialog informationdialog = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdialog.clickInformationDialogDontVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoicenumber));
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber));
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.clickVoidInvoiceMenuItem();
		informationdialog = new VNextInformationDialog(appiumdriver);
		Assert.assertEquals(informationdialog.clickInformationDialogVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoicenumber));
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.waitUntilInvoiceDisappearsFromList(invoicenumber);
		homescreen = invoicesscreen.clickBackButton();
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
		
		invoicesscreen = homescreen.clickInvoicesMenuItem();
		Assert.assertFalse(invoicesscreen.isInvoiceExists(invoicenumber));
		invoicesscreen.clickBackButton();
		
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingURL());

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicespage.setSearchFromDate(BackOfficeUtils.getCurrentDateFormatted());
		invoicespage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.isInvoiceNumberExists(invoicenumber));
		Assert.assertEquals(invoicespage.getInvoiceStatus(invoicenumber), 
				WebConstants.InvoiceStatuses.INVOICESTATUS_VOID.getName());
		DriverBuilder.getInstance().getDriver().quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifySavedPODisplaysOnCChangePONumberPopup(String rowID,
											 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		final String newponumber = "123TEST";
		final String longponumber = "12345678901234567890123456789012345678901234567890123";
		

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber(newponumber);
		changeinvoiceponumberdialog.clickDontSaveButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), invoice.getInvoiceData().getInvoicePONumber());
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		invoicesscreen = changeinvoiceponumberdialog.changeInvoicePONumber(newponumber);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), newponumber);
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		invoicesscreen = changeinvoiceponumberdialog.changeInvoicePONumber(longponumber);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), longponumber.substring(0, 50) );
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyWeHidePopupAndKeyboardIfUserClickHardwareBackButton(String rowID,
																String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeInvoicePONumberDialog.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		//AppiumUtils.clickHardwareBackButton();
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyOnActionChangePONumberUserCantErasePONumber(String rowID,
																			  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final String newponumber = "abcd123";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoice();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber(newponumber);
		changeinvoiceponumberdialog.clickDontSaveButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), invoice.getInvoiceData().getInvoicePONumber());
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber("");
		changeinvoiceponumberdialog.clickSaveButton();
		Assert.assertTrue(changeinvoiceponumberdialog.isPONUmberShouldntBeEmptyErrorAppears());
		invoicesscreen = changeinvoiceponumberdialog.changeInvoicePONumber(newponumber);
		
		Assert.assertEquals(invoicesscreen.getInvoicePONumberValue(invoicenumber), newponumber.toUpperCase());
		
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyChangePOIsNotAvailableIfOptionPOVisibleEqualsNOOnInvoiceType(String rowID,
																	  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.switchToMyWorkordersView();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoice();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		Assert.assertFalse(invoicemenuscreen.isInvoiceChangePONumberMenuItemExists());
		invoicesscreen = invoicemenuscreen.clickCloseInvoiceMenuButton();
		
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanRefreshDeletedPicturesFromMyInvoiceList(String rowID,
																					   String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);		
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addFakeImageNote();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyDeletedPicturesDoesntDisplaysAfterUpdatingApplicationDB_MyInvoiceList(String rowID,
																		 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addImageToNotesFromGallery();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		homescreen = invoicesscreen.clickBackButton();

		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
		
		invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanRefreshDeletedPicturesAfterApprovingInvoice(String rowID,
																								String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addFakeImageNote();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddNotesForTeamInvoice(String rowID,
																			 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final String notetext = "Test";
		final String quicknote = "Z note";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		invoicesscreen.switchToTeamInvoicesView();
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.addQuickNote(quicknote);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknote);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.switchToMyInvoicesView();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserDoesntSeeInvoiceWithTeamSharingEqualsNO(String rowID,
														String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoice();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		invoicesscreen.switchToTeamInvoicesView();
		Assert.assertFalse(invoicesscreen.isInvoiceExists(invoicenumber), "Invoice shouldn't exists: " + invoicenumber);
		invoicesscreen.switchToMyInvoicesView();
		Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber), "Can't find invoice: " + invoicenumber);
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																	  String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);

		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());

		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();
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

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailInvoiceWithDifferentCustomer(String rowID,
											 String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
	
		for (int i = 0; i < invoices.length; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (i == 0) {
				customersscreen.selectCustomer(testcustomer1);
			} else {
				customersscreen.selectCustomer(testcustomer2);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices[i] = invoiceinfoscreen.getInvoiceNumber();
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextCustomersScreen customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(testcustomer2);
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();
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

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		
		emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();

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

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		emailscren.sentToCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailscren.sentToBCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailMyInvoiceList(String rowID,
															String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		final String mailsymbol = "@";
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextConfigInfo.getInstance().getUserCapiMail().substring(0, VNextConfigInfo.getInstance().getUserCapiMail().indexOf(mailsymbol));
		emailscren.sentToEmailAddress(wrongMail);		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																	  String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		invoicesscreen.clickBackButton();
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
																			  String description, JSONObject testData)  {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyToFieldIsRequiredToEmailTeamInvoice(String rowID,
												 String description, JSONObject testData)  {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		emailscren.sentToCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailscren.sentToBCCEmailAddress(VNextConfigInfo.getInstance().getUserCapiMail());		
		msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailAddressIsValidatedOnEmailTeamInvoiceList(String rowID,
															  String description, JSONObject testData)  {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final String mailsymbol = "@";
		
		final int invoicesToCreate = 1;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		emailscren.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextConfigInfo.getInstance().getUserCapiMail().substring(0, VNextConfigInfo.getInstance().getUserCapiMail().indexOf(mailsymbol));
		emailscren.sentToEmailAddress(wrongMail);		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToTeamInvoicesView();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceWithDifferentCustomer(String rowID,
																		String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
	
		for (int i = 0; i < invoices.length; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			if (i == 0) {
				customersscreen.selectCustomer(testcustomer1);
			} else {
				customersscreen.selectCustomer(testcustomer2);
			}

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
			wosummaryscreen.clickCreateInvoiceOption();
			wosummaryscreen.clickWorkOrderSaveButton();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
			invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices[i] = invoiceinfoscreen.getInvoiceNumber();
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextCustomersScreen customersscreen = new VNextCustomersScreen(appiumdriver);
		customersscreen.selectCustomer(testcustomer2);
		VNextEmailScreen emailscren = new VNextEmailScreen(appiumdriver);
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.switchToMyInvoicesView();
		invoicesscreen.clickBackButton();
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

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(appiumdriver);
		wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		vehicleinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
		workordersscreen = servicesScreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();

		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		final String wonumber = workordersscreen.getFirstWorkOrderNumber();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoice.getInvoiceData().getInvoiceType());

		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(appiumdriver);
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		Assert.assertFalse(invoicemenuscreen.isApproveInvoiceMenuItemExists());
		invoicemenuscreen.clickCloseInvoiceMenuButton();
		invoicesscreen.clickBackButton();

	}

}

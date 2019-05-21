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
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-invoices-test-cases-data.json";

	private final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
	private final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");
	private List<WorkOrderDTO> workOrderDTOS = new ArrayList<>();
	List<InvoiceDTO> invoiceDTOS = new ArrayList<>();

	@BeforeClass(description="Team Invoices Test Cases")
	public void beforeClass() throws Exception {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInvoicesTestCasesDataPath();

		/*workOrderDTOS = VNextAPIUtils.getInstance().generateWorkOrders("team-base-workorder-data1.json",
				WorkOrderTypes.O_KRAMAR, testcustomer, employee, licenseID, deviceID, appID,
				appLicenseEntity, 20
				);


		List<WorkOrderDTO> workOrderDTOSForInvoices = VNextAPIUtils.getInstance().generateWorkOrders("team-base-workorder-data1.json",
				WorkOrderTypes.O_KRAMAR, testcustomer, employee, licenseID, deviceID, appID,
				appLicenseEntity, 20
		);
*/

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		/*VNextInvoicesScreen invoicesScreen = homescreen.clickInvoicesMenuItem();
		String lastInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		invoicesScreen.clickBackButton();
		lastInvoiceNumber = lastInvoiceNumber.substring(lastInvoiceNumber.lastIndexOf("-")+1, lastInvoiceNumber.length());
		lastInvoiceNumber = lastInvoiceNumber.replaceFirst("^0+(?!$)", "");


		invoiceDTOS = VNextAPIUtils.getInstance().generateInvoices("team-base-invoice-data.json",
				InvoiceTypes.O_KRAMAR, workOrderDTOSForInvoices, appID, Integer.valueOf(lastInvoiceNumber)
		);

		VNextStatusScreen statusScreen = homescreen.clickStatusMenuItem();
		statusScreen.updateMainDB();
*/
		VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
		customersscreen.switchToRetailMode();
		if (!customersscreen.isCustomerExists(testcustomer1)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer1);
			customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		}
		if (!customersscreen.isCustomerExists(testcustomer2)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer2);
			customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		}

		if (!customersscreen.isCustomerExists(testcustomer)) {
			VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
			newcustomerscreen.createNewCustomer(testcustomer);
			customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		}
		customersscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateInvoiceInStatusNew(String rowID,
														  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());

		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		invoicesscreen.clickBackButton();
		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicespage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.clickVoidInvoiceMenuItem();
		VNextInformationDialog informationdialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(informationdialog.clickInformationDialogDontVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoicenumber));
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(invoicesscreen.isInvoiceExists(invoicenumber));
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.clickVoidInvoiceMenuItem();
		informationdialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(informationdialog.clickInformationDialogVoidButtonAndGetMessage(), 
				String.format(VNextAlertMessages.ARE_YOU_SURE_YOU_WANT_VOID_INVOICE, invoicenumber));
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.waitUntilInvoiceDisappearsFromList(invoicenumber);
		homescreen = invoicesscreen.clickBackButton();
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
		
		invoicesscreen = homescreen.clickInvoicesMenuItem();
		Assert.assertFalse(invoicesscreen.isInvoiceExists(invoicenumber));
		invoicesscreen.clickBackButton();
		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceOfficeUrl);

		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.userLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver, BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();

		InvoicesWebPage invoicespage = operationspage.clickInvoicesLink();
		invoicespage.selectSearchTimeFrame(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM);
		invoicespage.setSearchFromDate(BackOfficeUtils.getPreviousDateFormatted());
		invoicespage.setSearchToDate(BackOfficeUtils.getTomorrowDateFormatted());
		invoicespage.setSearchInvoiceNumber(invoicenumber);
		invoicespage.selectSearchStatus(WebConstants.InvoiceStatuses.INVOICESTATUS_VOID);
		invoicespage.clickFindButton();
		
		Assert.assertTrue(invoicespage.isInvoiceDisplayed(invoicenumber));
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
		

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber(newponumber);
		changeinvoiceponumberdialog.clickDontSaveButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeInvoicePONumberDialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeInvoicePONumberDialog.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());

		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyOnActionChangePONumberUserCantErasePONumber(String rowID,
																			  String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		final String newponumber = "abcd123";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoice();
		
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextChangeInvoicePONumberDialog changeinvoiceponumberdialog = invoicemenuscreen.clickInvoiceChangePONumberMenuItem();
		changeinvoiceponumberdialog.setInvoicePONumber(newponumber);
		changeinvoiceponumberdialog.clickDontSaveButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR_AUTO);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyDeletedPicturesDoesntDisplaysAfterUpdatingApplicationDB_MyInvoiceList(String rowID,
																		 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addFakeImageNote();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		homescreen = invoicesscreen.clickBackButton();

		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
		
		invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanRefreshDeletedPicturesAfterApprovingInvoice(String rowID,
																								String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int numberOfImageNotes = 4;
		final int numberOfImageToDelete = 2;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		
		for (int i = 0; i < numberOfImageNotes; i++)
			notesscreen.addFakeImageNote();
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		for (int i = 0; i < numberOfImageToDelete; i++)
			notesscreen.deletePictureFromNotes();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes-numberOfImageToDelete);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		invoicemenuscreen.refreshInvoicePictures();
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getNumberOfAddedNotesPictures(), numberOfImageNotes);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddNotesForTeamInvoice(String rowID,
																			 String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final String notetext = "Test";
		final String quicknote = "Warranty expired";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		invoicesscreen.switchToTeamInvoicesView();
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);	
		VNextNotesScreen notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		notesscreen.setNoteText(notetext);
		notesscreen.addQuickNote(quicknote);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		notesscreen = invoicemenuscreen.clickInvoiceNotesMenuItem();
		Assert.assertEquals(notesscreen.getSelectedNotes(), notetext + "\n" + quicknote);
		notesscreen.clickScreenBackButton();
		
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.switchToMyInvoicesView();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserDoesntSeeInvoiceWithTeamSharingEqualsNO(String rowID,
														String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR2);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());

		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());

		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailInvoiceWithDifferentCustomer(String rowID,
											 String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		String[] invoices = new String[invoicesToCreate];

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
	
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

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
			wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices[i] = invoiceinfoscreen.getInvoiceNumber();
			VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextCustomersScreen customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(testcustomer2);
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		
		emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailscren.clickToEmailAddressRemoveButton();
		emailscren.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailscren.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailscren.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailsymbol));
		emailscren.sentToEmailAddress(wrongMail);		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicesscreen.unselectAllSelectedInvoices();
		invoicesscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyEmailTeamInvoiceForMultipleItemsWithTheSameCustomer(String rowID,
																	  String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			workordersscreen.switchToTeamWorkordersView();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer1);

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
			wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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
																			  String description, JSONObject testData) throws Exception {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);
		
		final int invoicesToCreate = 3;
		ArrayList<String> invoices = new ArrayList<>();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			invoices.add(createSimpleInvoice(invoice));
		}
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices) {
			//invoicesscreen.searchInvoiceByFreeText(invoiceNumber);
			invoicesscreen.selectInvoice(invoiceNumber);
		}
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailscren.clickToEmailAddressRemoveButton();
		emailscren.sentToCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		
		emailscren.sentToBCCEmailAddress(VNextFreeRegistrationInfo.getInstance().getR360UserMail());
		msg= emailscren.sendEmail();
		Assert.assertEquals(msg, VNextAlertMessages.THE_TO_BOX_IS_EMPTY);
		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i < invoicesToCreate; i ++) {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoices.add(invoiceinfoscreen.getInvoiceNumber());
			invoiceinfoscreen.saveInvoiceAsFinal();
			homescreen = invoicesscreen.clickBackButton();
		}
		
		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToTeamInvoicesView();
		for (String invoiceNumber : invoices)
			invoicesscreen.selectInvoice(invoiceNumber);
		invoicesscreen.clickOnSelectedInvoicesMailButton();
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		emailscren.clickToEmailAddressRemoveButton();
		final String wrongMail = VNextFreeRegistrationInfo.getInstance().getR360UserMail().
				substring(0, VNextFreeRegistrationInfo.getInstance().getR360UserMail().indexOf(mailsymbol));
		emailscren.sentToEmailAddress(wrongMail);		
		String msg= emailscren.sendEmail();
		Assert.assertEquals(msg, String.format(VNextAlertMessages.THE_EMAIL_ADDRESS_IS_NOT_VALID, wrongMail));

		emailscren.clickScreenBackButton();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
	
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

			VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
			wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			vehicleinfoscreen.changeScreen("Summary");
			VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(DriverBuilder.getInstance().getAppiumDriver());
			wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
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
		VNextCustomersScreen customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
		customersscreen.selectCustomer(testcustomer2);
		VNextEmailScreen emailscren = new VNextEmailScreen(DriverBuilder.getInstance().getAppiumDriver());
		NadaEMailService nada = new NadaEMailService();
		emailscren.sentToEmailAddress(nada.getEmailId());
		final String msg = emailscren.sendEmail();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypeslist = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
		wotypeslist.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
		vehicleinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen servicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		servicesScreen.selectService(invoice.getWorkOrderData().getServiceName());
		final String wonumber = vehicleinfoscreen.getNewInspectionNumber();
		workordersscreen = servicesScreen.saveWorkOrderViaMenu();
		homescreen = workordersscreen.clickBackButton();

		VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
		invoicesscreen.switchToMyInvoicesView();
		workordersscreen = invoicesscreen.clickAddInvoiceButton();
		workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
		invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);

		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		invoicesscreen = invoiceinfoscreen.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		VNextInvoiceMenuScreen invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		VNextApproveScreen approvescreen = invoicemenuscreen.clickApproveInvoiceMenuItem();
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		invoicesscreen = new VNextInvoicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		invoicemenuscreen = invoicesscreen.clickOnInvoiceByInvoiceNumber(invoicenumber);
		Assert.assertFalse(invoicemenuscreen.isApproveInvoiceMenuItemExists());
		invoicemenuscreen.clickCloseInvoiceMenuButton();
		invoicesscreen.clickBackButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateSeparateInvoicesFromOneWO(String rowID,
																	   String description, JSONObject testData) {

		final int invoiceStringLenght = 6;

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice);

		VNextInvoicesScreen invoicesScreen = homescreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNuber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.createSeparateInvoice(wonumber);
		workordersscreen.clickBackButton();

		invoicesScreen = homescreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNuber+1);
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCreateSeparateInvoicesFromSeveralWOs(String rowID,
																 String description, JSONObject testData) {

		final int invoiceStringLenght = 6;
		final int numberInvoicesToCreate = 3;
		ArrayList<String> workOrders = new ArrayList<>();

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i = 0; i< numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice));

		VNextInvoicesScreen invoicesScreen = homescreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNuber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.createSeparateInvoices(workOrders);
		workordersscreen.clickBackButton();

		invoicesScreen = homescreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNuber+numberInvoicesToCreate);
		invoicesScreen.clickBackButton();
	}

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCreateSingleInvoicesFromOneWO(String rowID,
                                                                 String description, JSONObject testData) {

        final int invoiceStringLenght = 6;
        final String ponumber = "123po";

        Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice);

        VNextInvoicesScreen invoicesScreen = homescreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
        final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
        final int lastInvoiceNuber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

        invoicesScreen.clickBackButton();

        VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
        workordersscreen.selectWorkOrder(wonumber);
        workordersscreen.clickCreateInvoiceIcon();

        VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
        informationDialog.clickSingleInvoiceButton();
        VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber));
        invoiceinfoscren.setInvoicePONumber(ponumber);
        final String newInvoiceNumber = invoiceinfoscren.getInvoiceNumber();
        invoicesScreen = invoiceinfoscren.saveInvoiceAsFinal();
        Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
        final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
        Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNuber+1);
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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i= 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice));

		VNextInvoicesScreen invoicesScreen = homescreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNuber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		for (String wonumber : workOrders)
			workordersscreen.selectWorkOrder(wonumber);
		workordersscreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSingleInvoiceButton();
		VNextInvoiceInfoScreen invoiceinfoscren = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (String wonumber : workOrders)
			Assert.assertTrue(invoiceinfoscren.isWorkOrderSelectedForInvoice(wonumber));
		invoiceinfoscren.setInvoicePONumber(ponumber);
		final String newInvoiceNumber = invoiceinfoscren.getInvoiceNumber();
		invoicesScreen = invoiceinfoscren.saveInvoiceAsFinal();
		Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNuber+1);
		invoicesScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanCancelInvoiceCreationUsingHardwareBackButton(String rowID,
															   String description, JSONObject testData) {

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice);

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.selectWorkOrder(wonumber);
		workordersscreen.clickCreateInvoiceIcon();

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

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (int i= 0; i < numberInvoicesToCreate; i++)
			workOrders.add(createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR_INVOICE, invoice));

		VNextInvoicesScreen invoicesScreen = homescreen.clickInvoicesMenuItem();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		final int lastInvoiceNuber = Integer.valueOf(invoiceNumber.substring(invoiceStringLenght, invoiceNumber.length()));

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.switchToMyWorkordersView();
		for (String wonumber : workOrders)
			workordersscreen.selectWorkOrder(wonumber);
		workordersscreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSeparateInvoicesButton();

        workordersscreen.cancelCreatingSeparateInvoice();

		workordersscreen.clickBackButton();
		invoicesScreen = homescreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();

		Assert.assertEquals(invoicesScreen.getFirstInvoiceNumber(), newInvoiceNumber);
		final int newLastInvoiceNuber = Integer.valueOf(newInvoiceNumber.substring(invoiceStringLenght, newInvoiceNumber.length()));
		Assert.assertEquals( newLastInvoiceNuber, lastInvoiceNuber+1);
		invoicesScreen.clickBackButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateSeparateInvoiceIfInvoiceTypeIsNotAssignedToWO(String rowID,
															   String description, JSONObject testData) {

		final String NO_AVAILABLE_WO_MESSAGE = "No available Work Order for Invoice creation";

		Invoice invoice = JSonDataParser.getTestDataFromJson(testData, Invoice.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

		VNextInvoicesScreen invoicesScreen = homescreen.clickInvoicesMenuItem();
		invoicesScreen.switchToMyInvoicesView();
		final String invoiceNumber = invoicesScreen.getFirstInvoiceNumber();

		invoicesScreen.clickBackButton();

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.selectWorkOrder(wonumber);
		workordersscreen.clickCreateInvoiceIcon();

		VNextInformationDialog informationDialog = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		informationDialog.clickSeparateInvoicesButton();
		workordersscreen.waitForWorkOrderScreenInfoMessage(NO_AVAILABLE_WO_MESSAGE);

		workordersscreen.clickBackButton();
		homescreen.clickInvoicesMenuItem();
		final String newInvoiceNumber = invoicesScreen.getFirstInvoiceNumber();
		Assert.assertEquals( newInvoiceNumber, invoiceNumber);
		invoicesScreen.clickBackButton();
	}

	public String createSimpleWorkOrder(WorkOrderTypes wotype, Invoice invoice) {
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String wonumber = "";
		if (workOrderDTOS.size() > 0) {
			WorkOrderDTO workOrderDTO = workOrderDTOS.remove(0);
			wonumber = "O-" + appLicenseEntity + "-" +
					getEntityStringNumber(workOrderDTO.getLocalNo());
		} else {
			VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
			VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
			customersscreen.selectCustomer(testcustomer);
			VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(DriverBuilder.getInstance().getAppiumDriver());
			workOrderTypesList.selectWorkOrderType(wotype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			vehicleinfoscreen.setVIN(invoice.getWorkOrderData().getVinNumber());
			wonumber = vehicleinfoscreen.getNewInspectionNumber();
			workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
			workordersscreen.clickBackButton();
		}
		return wonumber;
	}


	private String createSimpleInvoice(Invoice invoice) {
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		String invoiceNumber = "";
		if (invoiceDTOS.size() > 0) {
			InvoiceDTO invoiceDTO = invoiceDTOS.remove(0);
			invoiceNumber = "I-" + appLicenseEntity + "-" +
					getEntityStringNumber(invoiceDTO.getLocalNo());
		} else {
			String wonumber = createSimpleWorkOrder(WorkOrderTypes.O_KRAMAR, invoice);

			VNextInvoicesScreen invoicesscreen = homescreen.clickInvoicesMenuItem();
			invoicesscreen.switchToMyInvoicesView();
			VNextWorkOrdersScreen workordersscreen = invoicesscreen.clickAddInvoiceButton();
			workordersscreen.clickCreateInvoiceFromWorkOrder(wonumber);

			VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(DriverBuilder.getInstance().getAppiumDriver());
			invoiceTypesScreen.selectInvoiceType(InvoiceTypes.O_KRAMAR);
			VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
			invoiceinfoscreen.setInvoicePONumber(invoice.getInvoiceData().getInvoicePONumber());
			invoiceNumber = invoiceinfoscreen.getInvoiceNumber();
			invoiceinfoscreen.saveInvoiceAsFinal();
			invoicesscreen.clickBackButton();
		}
		return invoiceNumber;
	}

	private String getEntityStringNumber(int entityNumber) {
		String returnNumber = "";
		final int maxLenght = 5;
		String origNumber = Integer.toString(entityNumber);
		int origNumberLenght = origNumber.length();
		for (int i = 0; i < maxLenght - origNumberLenght; i++)
			returnNumber = returnNumber + "0";
		return returnNumber + origNumber;

	}
}

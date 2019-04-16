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
import com.cyberiansoft.test.dataclasses.AppCustomer;
import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.vnext.config.VNextTeamRegistrationInfo;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
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
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderSummaryScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.utils.VNextInspectionStatuses;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class VNextTeamInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {

	private List<InspectionDTO> inspectionDTOs = new ArrayList<>();
	
	@BeforeClass(description="Team Inspections Test Cases")
	public void beforeClass() throws Exception {
		/*inspectionDTOs = VNextAPIUtils.getInstance().generateInspections("team-base-inspection-data1.json",
				InspectionTypes.O_KRAMAR, testcustomer, employee, licenseID, deviceID, appID,
				appLicenseEntity, 30
		);
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusScreen = homescreen.clickStatusMenuItem();
		statusScreen.updateMainDB();*/

	}
	
	@BeforeMethod(description="Send all messages")
	public void beforeTestCase() {
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		if (homescreen.isQueueMessageVisible()) {		
			VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
			settingsscreen.setManualSendOff();
			settingsscreen.clickBackButton();		
			homescreen.waitUntilQueueMessageInvisible();
		}
	}
	
	@Test(testName= "Test Case 64494:Verify user can approve Invoice after creating, "
			+ "Test Case 64497:Verify user can create Invoice from Inspection, "
			+ "Test Case 64266:Verify user can create Invoice in status 'New'", 
			description = "Verify user can approve Invoice after creating, "
					+ "Verify user can create Invoice from Inspection, "
					+ "Verify user can create Invoice in status 'New'")
	public void testVerifyUserCanCreateInvoiceFromInspections() {

		final String invoiceType = "O_Kramar2";
		final String vinnumber = "TEST";
		final String ponumber = "12345";

		final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.INSP_TYPE_APPROV_REQUIRED, vinnumber);

		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.NEW);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenuscreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		Assert.assertTrue(approvescreen.isClearButtonVisible());
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), VNextInspectionStatuses.APPROVED);
		inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(appiumdriver);
		workOrderTypesList.selectWorkOrderType(WorkOrderTypes.ALL_AUTO_PHASES);
		BaseUtils.waitABit(60*1000);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.changeScreen("Summary");
		VNextWorkOrderSummaryScreen wosummaryscreen = new VNextWorkOrderSummaryScreen(appiumdriver);
		wosummaryscreen.clickCreateInvoiceOptionAndSaveWO();
		VNextInvoiceTypesList invoiceTypesScreen = new VNextInvoiceTypesList(appiumdriver);
		invoiceTypesScreen.selectInvoiceType(invoiceType);
		
		VNextInvoiceInfoScreen invoiceinfoscreen = new VNextInvoiceInfoScreen(appiumdriver);
		invoiceinfoscreen.setInvoicePONumber(ponumber);
		final String invoicenumber = invoiceinfoscreen.getInvoiceNumber();
		VNextInvoicesScreen invoicesscreen = invoiceinfoscreen.saveInvoice();
		invoicesscreen.switchToMyInvoicesView();
		Assert.assertEquals(invoicesscreen.getInvoiceStatusValue(invoicenumber), VNextInspectionStatuses.NEW);
		
		invoicesscreen.clickBackButton();
	}

	
	@Test(testName= "Test Case 66276:Verify Team Inspection displays on the screen", 
			description = "Verify Team Inspection displays on the screen")
	public void testVerifyTeamInspectionDisplaysOnTheScreen() {

		final String vinnumber = "TEST";

		final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66286:Verify when user go back from inspectiontypes screen to Home we save last selected mode",
			description = "Verify when user go back from inspectiontypes screen to Home we save last selected mode")
	public void testVerifyWhenUserGoBackFromInspectionsScreenToHomeWeSaveLastSelectedMode() {

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
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
	
	@Test(testName= "Test Case 66295:Verify user can create Inspection without Team Sharing", 
			description = "Verify user can create Inspection without Team Sharing")
	public void testVerifyUserCanCreateInspectionWithoutTeamSharing() {
		
		final String vinnumber = "TEST";
		final String insuranceCompany = "Oranta";
		final String claimNumber = "123";
		final String policyNumber = "099";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR_NO_SHARING);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		inspectionscreen = claiminfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertFalse(inspectionscreen.isInspectionExists(inspnumber), "Team inspection exists: " + inspnumber);
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66284:Verify only when user tap 'search' button we perform search and refresh team inspectiontypes list",
			description = "Verify only when user tap 'search' button we perform search and refresh team inspectiontypes list")
	public void testVerifyOnlyWhenUserTapSearchButtonWePerformSearchAndRefreshTeamInspectionsList() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		Assert.assertTrue(inspectionscreen.isTeamInspectionsViewActive());
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		inspectionscreen.searchInpectionByFreeText(testwholesailcustomer.getFullName());
		Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		List<WebElement> inspections = inspectionscreen.getInspectionsList();
		for (WebElement inspcell : inspections) {
			Assert.assertTrue(inspectionscreen.getInspectionCustomerValue(inspcell).contains(testwholesailcustomer.getFullName()));
		}
		final String inspSubNumber = inspnumber.substring(6, inspnumber.length());
		inspectionscreen.searchInpectionByFreeText(inspSubNumber);
		Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		inspections = inspectionscreen.getInspectionsList();
		for (WebElement inspcell : inspections) {
			Assert.assertTrue(inspectionscreen.getInspectionNumberValue(inspcell).contains(inspSubNumber));
		}

		Assert.assertTrue(inspectionscreen.getNumberOfInspectionsOnTheScreen() <= VNextInspectionsScreen.MAX_NUMBER_OF_INPECTIONS);
		
		inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 66858:Verify user can view Team Inspection", 
			description = "Verify user can view Team Inspection")
	public void testVerifyUserCanViewTeamInspection() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextViewScreen viewscreen = inspmenuscreen.clickViewInspectionMenuItem();
		viewscreen.clickScreenBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67290:Verify user can create Team Inspection, "
			+ "Test Case 67291:Verify Team Inspection save into mobile device and BO immediately if internet connection is available", 
			description = "Verify user can create Team Inspection, "
					+ "Verify Team Inspection save into mobile device and BO immediately if internet connection is available")
	public void testVerifyUserCanCreateTeamInspection() {

		final String vinnumber = "123";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}
	
	@Test(testName= "Test Case 67292:Verify Team Inspection saved into mobile deviceand BO later via outgoing message if there is no connection, "
			+ "Test Case 67299:Verify Inspection displays on the list after DB update and after reconnect Internet," +
			"Test Case 67320:Verify user can't send sms if internet connection is lost",
			description = "Verify Team Inspection saved into mobile deviceand BO later via outgoing message if there is no connection, "
					+ "Verify Inspection displays on the list after DB update and after reconnect Internet," +
					"Verify user can't send sms if internet connection is lost")
	public void testVerifyTeamInspectionSavedIntoMobileDeviceAndBOLaterViaOutgoingMessageIfThereIsNoConnection() {

		final String vinnumber = "123";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		settingsscreen.setManualSendOff();
		settingsscreen.clickBackButton();

		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		AppiumUtils.setNetworkOff();
		inspectionscreen.hidePickerWheel();
		inspectionscreen.switchToTeamInspectionsView();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
	
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		AppiumUtils.setNetworkOn();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
		inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		inspectionscreen.switchToMyInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));	
		homescreen = inspectionscreen.clickBackButton();
		
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
		
		inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		inspectionscreen.switchToMyInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		inspectionscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67293:Verify saving team inspection online doesn't affected to Settings > Manual send optionб" +
			"Test Case 67295:Verify Inspection displays in the list after DB updating",
			description = "Verify saving team inspection online doesn't affected to Settings > Manual send optionб" +
					"Verify Inspection displays in the list after DB updating")
	public void testVerifySavingTeamInspectionOnlineDoesntAffectedToSettingsManualSendOption() {

		final String vinnumber = "123";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();		
		
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		inspectionscreen.switchToMyInspectionsView();
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		homescreen = inspectionscreen.clickBackButton();
		
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		//homescreen = statusscreen.clickBackButton();
		
		inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();
	}

	@Test(testName= "Test Case 67330:Verify user can edit Inspection if we have no internet connection", 
			description = "Verify user can edit Inspection if we have no internet connection")
	public void testVerifyUserCanEditInspectionIfWeHaveNoInternetConnection() {

		final String vinnumber = "123";
		final String newvinnumber = "TEST456";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenuscreen.clickEditInspectionMenuItem();
		AppiumUtils.setNetworkOff();
		vehicleinfoscreen.setVIN(newvinnumber);
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		BaseUtils.waitABit(10000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		inspectionscreen.switchToMyInspectionsView();
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		AppiumUtils.setNetworkOn();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}
	
	@Test(testName= "Test Case 67331:Verify user can edit 'My inspectiontypes' if we have no internet connection",
			description = "Verify user can edit 'My inspectiontypes' if we have no internet connection")
	public void testVerifyUserCanEditMyInspectionsIfWeHaveNoInternetConnection() {

		final String vinnumber = "123";
		final String newvinnumber = "TEST456";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
 		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.switchToMyInspectionsView();
		AppiumUtils.setNetworkOff();

		VNextInspectionsMenuScreen inspmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenuscreen.clickEditInspectionMenuItem();		
		vehicleinfoscreen.setVIN(newvinnumber);
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		homescreen = inspectionscreen.clickBackButton();
		Assert.assertEquals(homescreen.getQueueMessageValue(), "1");
		AppiumUtils.setNetworkOn();
		homescreen.waitUntilQueueMessageInvisible();
		Assert.assertFalse(homescreen.isQueueMessageVisible());
	}
	
	@Test(testName= "Test Case 67326:Verify user can edit Team Inspection, "
			+ "Test Case 67332:Verify immediately saving on BO if user edit Team Inspection", 
			description = "Verify user can edit Team Inspection, "
					+ "Verify immediately saving on BO if user edit Team Inspection")
	public void testVerifyUserCanEditTeamInspection() {

		final String vinnumber = "123";
		
		final String newVIN = "1FMCU0DG4BK830800";
		final String _make = "Ford";
		final String _model = "Escape";
		final String _year = "2011";
		final String licPlate = "123";
		final String mileage = "100000";
		final String stockNumber = "123ABC";
		final String roNumber = "123ASD";
		final String insuranceCompany = "Miami Beach Insurance";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		
		vehicleinfoscreen = inspectionscreen.clickOpenInspectionToEdit(inspnumber);
		vehicleinfoscreen.setVIN(newVIN);
		//vehicleinfoscreen.selectModelColor(color);
		vehicleinfoscreen.setLicPlate(licPlate);
		vehicleinfoscreen.setMilage(mileage);
		vehicleinfoscreen.setStockNo(stockNumber);
		vehicleinfoscreen.setRoNo(roNumber);
		vehicleinfoscreen.changeScreen("Claim");
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.saveInspectionViaMenu();

		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);

		vehicleinfoscreen = inspectionscreen.clickOpenInspectionToEdit(inspnumber);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), newVIN);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), _make);
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), _model);
		Assert.assertEquals(vehicleinfoscreen.getYear(), _year);
		Assert.assertEquals(vehicleinfoscreen.getLicPlate(), licPlate);
		Assert.assertEquals(vehicleinfoscreen.getMilage(), mileage);
		Assert.assertEquals(vehicleinfoscreen.getStockNo(), stockNumber);
		Assert.assertEquals(vehicleinfoscreen.getRoNo(), roNumber);
		vehicleinfoscreen.changeScreen("Claim");
		claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		Assert.assertEquals(claiminfoscreen.getInsuranceCompany(), insuranceCompany);
		claiminfoscreen.cancelInspection();
		inspectionscreen.clickBackButton();

		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		WebDriverUtils.webdriverGotoWebPage(deviceofficeurl);
		BackOfficeLoginWebPage loginpage = PageFactory.initElements(webdriver,
				BackOfficeLoginWebPage.class);
		loginpage.UserLogin(VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserName(),
				VNextTeamRegistrationInfo.getInstance().getBackOfficeStagingUserPassword());
		BackOfficeHeaderPanel backofficeheader = PageFactory.initElements(webdriver,
				BackOfficeHeaderPanel.class);
		OperationsWebPage operationspage = backofficeheader.clickOperationsLink();
		InspectionsWebPage inspectionspage = operationspage.clickInspectionsLink();
		inspectionspage.makeSearchPanelVisible()
				.selectSearchTimeframe(WebConstants.TimeFrameValues.TIMEFRAME_CUSTOM.getName())
				.setTimeFrame(BackOfficeUtils.getPreviousDateFormatted(), BackOfficeUtils.getTomorrowDateFormatted())
				.searchInspectionByNumber(inspnumber);
		inspectionspage.verifyVINIsPresentForInspection(inspnumber, newVIN);
		webdriver.quit();
	}
	
	//@Test(testName= "Test Case 68042:Verify sending >100 messages after reconnect Internet",
	//		description = "Verify sending >100 messages after reconnect Internet")
	public void testVerifySendingMoreThen100MessagesAfterReconnectInternet() {

		final String vinnumber = "123";
		final int fakeimagescount = 50;
		final String imagesummaryvalue = "+47";
		final String[] services = { "Battery Installation", "Aluminum Panel", "Damage Service" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		AppiumUtils.setNetworkOff();
		VNextSettingsScreen settingsscreen = homescreen.clickSettingsMenuItem();
		homescreen = settingsscreen.setManualSendOn().clickBackButton();

		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		inspectionsscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		inspinfoscreen.setVIN(vinnumber);
		final String inspnumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		claiminfoscreen.swipeScreenLeft();		
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		for (String srv : services)
			inspservicesscreen.selectService(srv);
		VNextSelectedServicesScreen selectedservicesscreen = inspservicesscreen.switchToSelectedServicesView();
		for (String srv : services) {
			VNextNotesScreen notesscreen = selectedservicesscreen.clickServiceNotesOption(srv);
			for (int i = 0; i < fakeimagescount; i++)
				notesscreen.addFakeImageNote();
			notesscreen.clickScreenBackButton();
			selectedservicesscreen = new VNextSelectedServicesScreen(appiumdriver);
		}

		selectedservicesscreen.saveInspectionViaMenu();
		inspservicesscreen.clickScreenBackButton();
		homescreen = new VNextHomeScreen(appiumdriver);
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.clickUpdateAppdata();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		BaseUtils.waitABit(10000);
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		AppiumUtils.setNetworkOn();	
		statusscreen.clickUpdateAppdata();	
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogStartSyncButton();
		BaseUtils.waitABit(10000);
		informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
		
		homescreen = statusscreen.clickBackButton();
		inspectionsscreen = homescreen.clickInspectionsMenuItem();
		Assert.assertTrue(inspectionsscreen.isInspectionExists(inspnumber));
		inspinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		inspinfoscreen.swipeScreenLeft();
		claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);

		claiminfoscreen.swipeScreenLeft();		
		inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		selectedservicesscreen = inspservicesscreen.switchToSelectedServicesView();
		for (String srv : services) {
			selectedservicesscreen.isServiceSelected(srv);
			Assert.assertEquals(selectedservicesscreen.getSelectedServiceImageSummaryValue(srv), imagesummaryvalue);
		}
		inspectionsscreen = inspservicesscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67756:Verify message 'Your email message has been added to the queue.' displays after sending", 
			description = "Verify message 'Your email message has been added to the queue.' displays after sending")
	public void testVerifyMessageYourEmailMessageHasBeenAddedtoTheQueueDisplaysAfterSending() {
		
		final String customereMail = "test.cyberiansoft@gmail.com";
		final String vinnumber = "TEST";

		final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.INSP_TYPE_APPROV_REQUIRED, vinnumber);
		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		VNextEmailScreen emailscreen = inspectionscreen.clickOnInspectionToEmail(inspnumber);
		emailscreen.sentToEmailAddress(customereMail);
		emailscreen.sendEmail();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();	
	}

	@Test(testName= "Verify it is not possible to edit Team Inspection with device on Fly-mode",
			description = "Verify it is not possible to edit Team Inspection with device on Fly-mode")
	public void testVerifyItIsNotPossibleToEditTeamInspectionWithDeviceOnFlyMode() {

		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToWholesaleMode();
		customersscreen.selectCustomer(testwholesailcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();

		vehicleinfoscreen.saveInspectionAsDraft();
		inspectionscreen.switchToTeamInspectionsView();
		inspectionscreen.searchInpectionByFreeText(inspnumber);
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber), "Can't find inspection: " + inspnumber);
		AppiumUtils.setNetworkOff();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextInformationDialog informationDialog = inspectionsMenuScreen.clickEditInspectionMenuItemWithAlert();
		Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
				VNextAlertMessages.CONNECTION_IS_NOT_AVAILABLE);
		AppiumUtils.setNetworkOn();
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();
	}

	@Test(testName= "Automate Verify user can edit team Work Order (add and remove services)",
			description = "Automate Verify user can edit team Work Order (add and remove services)")
	public void testVerifyUserCanEditTeamWorkOrderAddAndRemoveServices() {

		final String vinnumber = "TEST";
		final String moneyservice = "Battery Installation";
		final String percentageservice = "Bundle Discount";

		final int amountToSelect = 3;
		final int defaultCountForMoneyService = 1;


		new VNextHomeScreen(appiumdriver);
		final String inspnumber = createSimpleInspection(testwholesailcustomer, InspectionTypes.O_KRAMAR, vinnumber);

		VNextInspectionsScreen inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.switchToTeamInspectionsView();
        inspectionscreen.searchInpectionByFreeText(inspnumber);
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		VNextVehicleInfoScreen vehicleinfoscreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		for (int i = 0; i < amountToSelect; i++)
			availableServicesScreen.selectService(moneyservice);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(moneyservice), amountToSelect);
		for (int i = 0; i < amountToSelect; i++)
			availableServicesScreen.selectService(percentageservice);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(percentageservice), amountToSelect);
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(moneyservice), amountToSelect);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), amountToSelect);

		selectedServicesScreen.saveInspectionViaMenu();
		inspectionsMenuScreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		vehicleinfoscreen.swipeScreenLeft();
		availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(moneyservice), amountToSelect);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(percentageservice), amountToSelect);
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(moneyservice), amountToSelect);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), amountToSelect);
		selectedServicesScreen.uselectService(percentageservice);
		selectedServicesScreen.uselectService(percentageservice);
		selectedServicesScreen.uselectService(moneyservice);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), defaultCountForMoneyService);
		selectedServicesScreen.switchToAvalableServicesView();
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(moneyservice), 2);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(percentageservice), defaultCountForMoneyService);
		availableServicesScreen.selectService(percentageservice);
		availableServicesScreen.selectService(percentageservice);
		availableServicesScreen.selectService(moneyservice);

		availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(moneyservice), amountToSelect);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), amountToSelect);
		availableServicesScreen.saveInspectionViaMenu();
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();
	}
	
	private String createSimpleInspection(AppCustomer inspcustomer, InspectionTypes insptype, String vinnumber) {

		String inspnumber = "";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		if (insptype.equals(InspectionTypes.INSP_TYPE_APPROV_REQUIRED)) {
			inspectionscreen.switchToMyInspectionsView();
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToWholesaleMode();
			customersscreen.selectCustomer(inspcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(insptype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			inspnumber = vehicleinfoscreen.getNewInspectionNumber();
			/*vehicleinfoscreen.swipeScreenLeft();
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			VNextQuestionsScreen questionsScreen = selectedServicesScreen.
					clickServiceQuestionSection("Test_Service_PP_Panel", "zayats section1");
			questionsScreen.selectAllRequiredQuestions(0);
			questionsScreen.setAllRequiredQuestions("test 1");
			questionsScreen.saveQuestions();
			selectedServicesScreen.switchToAvalableServicesView();
			availableServicesScreen.switchToSelectedServicesView();
			questionsScreen = selectedServicesScreen.
					clickServiceQuestionSection("Vlad_Money", "Vovan Test 5");
			questionsScreen.selectRequiredQuestion();
			questionsScreen.clickDoneButton();
*/
			vehicleinfoscreen.saveInspectionViaMenu();

		} else if ((insptype.equals(InspectionTypes.O_KRAMAR) & (inspectionDTOs.size() > 0))) {
			InspectionDTO inspectionDTO = inspectionDTOs.remove(0);
			inspnumber = "E-" + appLicenseEntity + "-0" + inspectionDTO.getLocalNo();
		} else {
			inspectionscreen.switchToMyInspectionsView();
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToWholesaleMode();
			customersscreen.selectCustomer(inspcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(insptype);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(vinnumber);
			inspnumber = vehicleinfoscreen.getNewInspectionNumber();
			vehicleinfoscreen.saveInspectionViaMenu();
		}
		return inspnumber;
	}

}

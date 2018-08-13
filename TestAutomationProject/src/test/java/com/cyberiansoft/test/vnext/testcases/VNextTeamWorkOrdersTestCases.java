package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextWorkOrdersMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextWorkOrderClaimInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersTestCases extends BaseTestCaseTeamEditionRegistration {
	
	@BeforeClass(description="Team Work Orders Test Cases")
	public void beforeClass() {
	}
	
	@Test(testName= "Test Case 67042:Verify user can delete WO if 'Allow Delete=ON'", 
			description = "Verify user can delete WO if 'Allow Delete=ON'")
	public void testVerifyUserCanDeleteWOIfAllowDeleteON() { 
	
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickNewWorkOrderPopupMenu();
		
		//VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		//VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		VNextWorkOrdersMenuScreen womenu = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		womenu.clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogDontDeleteButton();
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		womenu = workordersscreen.clickOnWorkOrderByNumber(woNumber);
				
		workordersscreen = womenu.deleteWorkOrder();
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		homescreen = workordersscreen.clickBackButton();
		
		VNextStatusScreen statusscreen = homescreen.clickStatusMenuItem();
		statusscreen.updateMainDB();
		homescreen = statusscreen.clickBackButton();
		workordersscreen = homescreen.clickWorkOrdersMenuItem();
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.clickBackButton();
	}
	
	@Test(testName= "Test Case 67043:Verify user can't delete WO if 'Allow Delete=OFF'", 
			description = "Verify user can't delete WO if 'Allow Delete=OFF'")
	public void testVerifyUserCantDeleteWOIfAllowDeleteOFF() { 
	
		final String workorderType = "Kramar_auto2";
		final String vinnumber = "TEST";
		final String insuranceCompany = "Miami Beach Insurance";
		final String claimNumber = "4Bc";
		final String policyNumber = "12345";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickNewWorkOrderPopupMenu();
		
		//VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		//VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		servicesscreen.swipeScreenLeft();
		VNextWorkOrderClaimInfoScreen claiminfoscreen = new VNextWorkOrderClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany(insuranceCompany);
		claiminfoscreen.setClaimNumber(claimNumber);
		claiminfoscreen.setPolicyNumber(policyNumber);
		VNextWorkOrdersScreen workordersscreen = claiminfoscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		VNextWorkOrdersMenuScreen womenu = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		Assert.assertFalse(womenu.isDeleteWorkOrderMenuButtonExists());
		womenu.clickCloseWorkOrdersMenuButton();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.clickBackButton();
		
	}
	
	@Test(testName= "Test Case 67329:Verify Team WO displays in My WO list if WO was created from Team Inspection", 
			description = "Verify Team WO displays in My WO list if WO was created from Team Inspection")
	public void testVerifyTeamWODisplaysInMyWOsListIfWOWasCreatedFromTeamInspection() { 
	
		final String searchtext = "E-357-00295";
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		inspectionscreen.searchInpectionByFreeText(searchtext);
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspectionscreen.getFirstInspectionNumber());
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber), "Can't find work order: " + woNumber);
		
		workordersscreen.switchToTeamWorkordersView();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickBackButton();
		
	}
	
	@Test(testName= "Test Case 68376:Verify user can create WO from Team Inspection", 
			description = "Verify user can create WO from Team Inspection")
	public void testVerifyUserCanCreateWOFromTeamInspection() { 

		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		final String servicesToSelect[] = { "Aluminum Panel", "Damage Service" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		inspectionscreen.switchToTeamInspectionsView();
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		for (String service : servicesToSelect)
			servicesscreen.selectService(service);
		VNextWorkOrdersScreen workordersscreen = servicesscreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		
		workordersscreen.switchToTeamWorkordersView();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		VNextWorkOrdersMenuScreen womenuscreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		vehicleinfoscreen = womenuscreen.clickEditWorkOrderMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		for (String service : servicesToSelect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service));
		servicesscreen.saveWorkOrderViaMenu();
		workordersscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 68377:Verify user can create WO only from Approved Team Inspection", 
			description = "Verify user can create WO only from Approved Team Inspection")
	public void testVerifyUserCanCreateWOOnlyFromApprovedTeamInspection() { 
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();		
		
		inspectionscreen.switchToTeamInspectionsView();
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnFirstInspectionWithStatus("New");
		Assert.assertFalse(inspectionmenuscreen.isCreateWorkOrderMenuPresent());
		inspectionmenuscreen.clickCloseInspectionMenuButton();
		
		inspectionmenuscreen = inspectionscreen.clickOnFirstInspectionWithStatus("Approved");
		Assert.assertTrue(inspectionmenuscreen.isCreateWorkOrderMenuPresent());
		inspectionmenuscreen.clickCloseInspectionMenuButton();
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();		
	}
	
	@Test(testName= "Test Case 68379:Verify all selected services from Inspection displays when user create WO", 
			description = "Verify all selected services from Inspection displays when user create WO")
	public void testVerifyAllSelectedServicesFromInspectionDisplaysWhenUserCreateWO() { 
	
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		final String servicesToSelect[] = { "Aluminum Panel", "Damage Service" };
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();		
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(appiumdriver);
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		claiminfoscreen.swipeScreenLeft();		
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		for (String service : servicesToSelect)
			inspservicesscreen.selectService(service);
		inspservicesscreen.saveInspection();
		
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen servicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectedServicesScreen = servicesscreen.switchToSelectedServicesView();
		for (String service : servicesToSelect)
			Assert.assertTrue(selectedServicesScreen.isServiceSelected(service));
		VNextWorkOrdersScreen workordersscreen = selectedServicesScreen.saveWorkOrderViaMenu();
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.clickBackButton();		
	}

	
	@Test(testName= "Test Case 68380:Verify user can delete WO if this WO was created from Team Inspection screen, "
			+ "Test Case 68378:Verify deleted WO doesn't displays in Team/My WO list", 
			description = "Verify user can delete WO if this WO was created from Team Inspection screen, "
					+ "Verify deleted WO doesn't displays in Team/My WO list")
	public void testVerifyUserCanDeleteWOIfThisWOWasCreatedFromTeamInspectionScreen() { 
	
		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToTeamInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();				
		vehicleinfoscreen.saveInspectionViaMenu();
		
		VNextInspectionsMenuScreen inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickApproveInspectionMenuItem();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionmenuscreen = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspectionmenuscreen.clickCreateWorkOrderInspectionMenuItem();
		
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		
		Assert.assertTrue(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		workOrdersMenuScreen.clickDeleteWorkOrderMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogDeleteButton();
		workordersscreen = new VNextWorkOrdersScreen(appiumdriver);
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		
		workordersscreen.switchToTeamWorkordersView();
		Assert.assertFalse(workordersscreen.isWorkOrderExists(woNumber));
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickBackButton();		
	}

	@Test(testName= "Verify it is not possible to edit Team Work Order with device on Fly-mode",
			description = "Verify it is not possible to edit Team Work Order with device on Fly-mode")
	public void testVerifyItIsNotPossibleToEditTeamWorkOrderWithDeviceOnFlyMode() {

		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextCustomersScreen customersscreen = homescreen.clickNewWorkOrderPopupMenu();

		//VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		//VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		VNextWorkOrdersScreen workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();

		workordersscreen.switchToTeamWorkordersView();
		AppiumUtils.setNetworkOff();
		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		VNextInformationDialog informationDialog = workOrdersMenuScreen.clickEditWorkOrderMenuItemWithAlerr();
		Assert.assertEquals(informationDialog.clickInformationDialogOKButtonAndGetMessage(),
				VNextAlertMessages.CONNECTION_IS_NOT_AVAILABLE);
		AppiumUtils.setNetworkOn();
		workordersscreen.switchToTeamWorkordersView();
		workordersscreen.clickBackButton();
	}

	@Test(testName= "Verify user can edit Team Work Order",
			description = "Verify user can edit Team Work Order")
	public void testVerifyUserCanEditTeamWorkOrder() {

		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		final String newvinnumber = "77777777777777777";

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.switchToMyWorkordersView();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();

		workordersscreen.switchToTeamWorkordersView();

		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleinfoscreen.setVIN(newvinnumber);
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), newvinnumber);
		vehicleinfoscreen.cancelWorkOrder();
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickBackButton();
	}

	@Test(testName= "Automate Verify user can edit team Work Order (add and remove services)",
			description = "Automate Verify user can edit team Work Order (add and remove services)")
	public void testVerifyUserCanEditTeamWorkOrderAddAndRemoveServices() {

		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		final String moneyservice = "Battery Installation";
		final String percentageservice = "Aluminum Panel";

		final int amountToSelect = 3;
		final int defaultCountForMoneyService = 1;


		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.switchToMyWorkordersView();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();

		workordersscreen.switchToTeamWorkordersView();

		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		for (int i = 0; i < amountToSelect; i++)
			availableServicesScreen.selectService(moneyservice);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(moneyservice), defaultCountForMoneyService);
		for (int i = 0; i < amountToSelect; i++)
			availableServicesScreen.selectService(percentageservice);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(percentageservice), amountToSelect);
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(moneyservice), defaultCountForMoneyService);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), amountToSelect);

		selectedServicesScreen.saveWorkOrderViaMenu();
		workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(moneyservice), defaultCountForMoneyService);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(percentageservice), amountToSelect);
		selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(moneyservice), defaultCountForMoneyService);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), amountToSelect);
		selectedServicesScreen.uselectService(percentageservice);
		selectedServicesScreen.uselectService(percentageservice);
		selectedServicesScreen.uselectService(moneyservice);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), defaultCountForMoneyService);
		selectedServicesScreen.switchToAvalableServicesView();
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(moneyservice), 0);
		Assert.assertEquals(availableServicesScreen.getServiceAmountSelectedValue(percentageservice), defaultCountForMoneyService);
		availableServicesScreen.selectService(percentageservice);
		availableServicesScreen.selectService(percentageservice);
		availableServicesScreen.selectService(moneyservice);

		availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(moneyservice), defaultCountForMoneyService);
		Assert.assertEquals(selectedServicesScreen.getNumberOfServicesSelectedByName(percentageservice), amountToSelect);
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();
		workordersscreen.switchToMyWorkordersView();
		workordersscreen.clickBackButton();
	}

	@Test(testName= "Verify it is possible to add service by opening service details screen and save",
			description = "Verify it is possible to add service by opening service details screen and save")
	public void testVerifyItIsPossibleToAddServiceByOpeningServiceDetailsScreenAndSave() {

		final String workorderType = "Kramar_auto";
		final String vinnumber = "TEST";
		final String moneyservice = "Battery Installation";
		final String moneyserviceamaunt = "9.99";
		final String moneyservicequantity = "0.99";
		final String percentageservice = "Aluminum Panel";
		final String percentageserviceamaunt = "1.99";
		final String totalprice = "$10.09";


		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);

		VNextWorkOrdersScreen workordersscreen = homescreen.clickWorkOrdersMenuItem();
		workordersscreen.switchToMyWorkordersView();
		VNextCustomersScreen customersscreen = workordersscreen.clickAddWorkOrderButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextWorkOrderTypesList wotypes = new VNextWorkOrderTypesList(appiumdriver);
		wotypes.selectWorkOrderType(workorderType);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(vinnumber);
		final String woNumber = vehicleinfoscreen.getNewInspectionNumber();
		workordersscreen = vehicleinfoscreen.saveWorkOrderViaMenu();

		workordersscreen.switchToTeamWorkordersView();

		VNextWorkOrdersMenuScreen workOrdersMenuScreen = workordersscreen.clickOnWorkOrderByNumber(woNumber);
		vehicleinfoscreen = workOrdersMenuScreen.clickEditWorkOrderMenuItem();
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		VNextServiceDetailsScreen serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(moneyservice);
		serviceDetailsScreen.setServiceAmountValue(moneyserviceamaunt);
		serviceDetailsScreen.setServiceQuantityValue(moneyservicequantity);
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		serviceDetailsScreen = availableServicesScreen.openServiceDetailsScreen(percentageservice);
		serviceDetailsScreen.setServiceAmountValue(percentageserviceamaunt);
		serviceDetailsScreen.clickServiceDetailsDoneButton();
		availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
		Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), totalprice);
		availableServicesScreen.saveWorkOrderViaMenu();
		Assert.assertEquals(workordersscreen.getWorkOrderPriceValue(woNumber), totalprice);
		workordersscreen.clickBackButton();
	}
}

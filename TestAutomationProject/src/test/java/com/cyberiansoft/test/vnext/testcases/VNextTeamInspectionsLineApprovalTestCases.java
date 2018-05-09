package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamInspectionsLineApprovalTestCases extends BaseTestCaseTeamEditionRegistration {
	
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-inspections-line-approval-data.json";
	
	@BeforeClass(description = "Team Inspections Line Approval Test Cases")
	public void settingUp() throws Exception {
		JSONDataProvider.dataFile = DATA_FILE;	
	}
	
	@AfterClass()
	public void settingDown() throws Exception {	
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveServiceForInspectionIfLineApprovalEqualsON(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		Assert.assertTrue(approveservicesscreen.isApproveAllButtonDisplayed());
		Assert.assertTrue(approveservicesscreen.isDeclineAllButtonDisplayed());
		Assert.assertTrue(approveservicesscreen.isSkipAllButtonDisplayed());
		for (ServiceData service : services) {
			Assert.assertTrue(approveservicesscreen.isServicePresentInTheList(service.getServiceName()));
			approveservicesscreen.clickApproveButtonForService(service.getServiceName());
			approveservicesscreen.clickDeclineButtonForService(service.getServiceName());
			approveservicesscreen.clickSkipButtonForService(service.getServiceName());
		}
		approveservicesscreen.clickScreenBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveDeclineAndSkipDifferentServicesInOneInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickApproveButtonForService(services.get(0).getServiceName());
		approveservicesscreen.clickDeclineButtonForService(services.get(1).getServiceName());
		approveservicesscreen.clickSkipButtonForService(services.get(2).getServiceName());
		approveservicesscreen.clickSaveButton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
				
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
		Assert.assertEquals(inspectionscreen.getInspectionApprovedPriceValue(inspnumber), inspdata.getInspectionApprovedPrice());	
		
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeclineAllServiceIfLineApprovalEqualsON(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickDeclineAllButton();
		approveservicesscreen.clickSaveButton();
		VNextDeclineReasonScreen declinereasonsscreen = new VNextDeclineReasonScreen(appiumdriver);
		declinereasonsscreen.selectDeclineReason(inspdata.getDeclineReason());
		
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.DECLINED.getInspectionStatusValue());
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantApproveOrDeclineServicesIfLineApprovalEqualsOFF(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.selectService(inspdata.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();		
		
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		
		homescreen = inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyStateChangedToNewIfUserEditInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();		
		
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickApproveAllButton();
		approveservicesscreen.clickSaveButton();

		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickEditInspectionMenuItem();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.NEW.getInspectionStatusValue());
		homescreen = inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyInspectionHaveStateApproveIfUserSelectOnlyOneServiceAsApproved(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());

		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickDeclineAllButton();
		approveservicesscreen.clickApproveButtonForService(services.get(0).getServiceName());
		approveservicesscreen.clickSaveButton();

		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		homescreen = inspectionscreen.clickBackButton();
	}

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanGoBackFromApproveScreenUsingHardwareBackButton(String rowID,
                                                                                   String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

        List<ServiceData> services = inspdata.getServicesList();
        for (ServiceData service : services)
            inpsctionservicesscreen.selectService(service.getServiceName());

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
        inspmenu.clickApproveInspectionMenuItem();
        VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
        approveservicesscreen.clickApproveAllButton();
        approveservicesscreen.clickSaveButton();
        VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
        AppiumUtils.clickHardwareBackButton();
        approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
        AppiumUtils.clickHardwareBackButton();
        inspectionscreen = new VNextInspectionsScreen(appiumdriver);
        Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.NEW.getInspectionStatusValue());
        homescreen = inspectionscreen.clickBackButton();
    }

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountIncludeOnlyApprovedServices(String rowID,
																				String description, JSONObject testData) {

		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			inpsctionservicesscreen.selectService(service.getServiceName());

		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickApproveButtonForService(inspdata.getServiceNameByIndex(0));
		approveservicesscreen.clickDeclineButtonForService(inspdata.getServiceNameByIndex(1));
		approveservicesscreen.clickSkipButtonForService(inspdata.getServiceNameByIndex(2));

		approveservicesscreen.clickSaveButton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();

		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
		Assert.assertEquals(inspectionscreen.getInspectionApprovedPriceValue(inspnumber), inspdata.getInspectionApprovedPrice());
		homescreen = inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserSeeOnlyTotalAmountForSelectedMatrixWhenApproveInspectionWithLineApprovalEqualsON(String rowID,
																						String description, JSONObject testData) {

		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(inspdata.getInspectionType());
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
		inpsctionservicesscreen.switchToAvalableServicesView();

		VNextVehiclePartsScreen vehiclepartsscreen = inpsctionservicesscreen.openSelectedMatrixServiceDetails( inspdata.getMatrixServiceData().getMatrixServiceName());
		List<HailMatrixService>  hailMatrixServices = inspdata.getMatrixServiceData().getHailMatrixServices();
		for (HailMatrixService  hailMatrixService : hailMatrixServices) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(hailMatrixService.getHailMatrixServiceName());
			vehiclepartinfoscreen.selectVehiclePartSize(hailMatrixService.getHailMatrixSize());
			vehiclepartinfoscreen.selectVehiclePartSeverity(hailMatrixService.getHailMatrixSeverity());
			if (hailMatrixService.getMatrixAdditionalServices() != null) {
				List<ServiceData> additionalServices = hailMatrixService.getMatrixAdditionalServices();
				for (ServiceData additionalService : additionalServices)
					vehiclepartinfoscreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
			}
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		}
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		inpsctionservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

		Assert.assertEquals(inpsctionservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.isServicePresentInTheList(inspdata.getMatrixServiceData().getMatrixServiceName());
		Assert.assertEquals(approveservicesscreen.getServicePriceValue(inspdata.getMatrixServiceData().getMatrixServiceName()),
				inspdata.getInspectionPrice());
		approveservicesscreen.clickApproveAllButton();
		approveservicesscreen.clickSaveButton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();

		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		homescreen = inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveSeveralInspectionsWithOneCustomersWithLineApproveAndWithoutLineApprove(String rowID,
																 String description, JSONObject testData) {

		List<String> inspNumbers = new ArrayList<String>();
		List<String> inspServives = new ArrayList<String>();
		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(inspdata.getInspectionType());
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspNumbers.add(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services) {
				inpsctionservicesscreen.selectService(service.getServiceName());
				inspServives.add(service.getServiceName());
			}
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (String inspNumber : inspNumbers)
			inspectionscreen.selectInspection(inspNumber);
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.clickMultiselectInspectionsApproveButton();

		for (String inspNumber : inspNumbers)
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspNumber));
		Assert.assertTrue(approveInspectionsScreen.isApproveIconForInspectionSelected(inspNumbers.get(0)));
		Assert.assertFalse(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspNumbers.get(0)));
		Assert.assertFalse(approveInspectionsScreen.isApproveIconForInspectionSelected(inspNumbers.get(1)));
		Assert.assertTrue(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspNumbers.get(1)));
		inspectionscreen = approveInspectionsScreen.clickBackButton();
		for (String inspNumber : inspNumbers)
			inspectionscreen.unselectInspection(inspNumber);
		inspectionscreen.clickBackButton();
	}

}

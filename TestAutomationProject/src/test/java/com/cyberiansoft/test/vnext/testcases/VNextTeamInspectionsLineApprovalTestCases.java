package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamInspectionsLineApprovalTestCases extends BaseTestCaseTeamEditionRegistration {
	
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-inspections-line-approval-data.json";
	
	@BeforeClass(description = "Team Inspections Line Approval Test Cases")
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		inspectionscreen.switchToMyInspectionsView();
		inspectionscreen.clickBackButton();
	}
	
	@AfterClass()
	public void settingDown() {
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			availableservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		Assert.assertTrue(approveservicesscreen.isApproveAllButtonDisplayed());
		Assert.assertTrue(approveservicesscreen.isDeclineAllButtonDisplayed());
		Assert.assertTrue(approveservicesscreen.isSkipAllButtonDisplayed());
		for (ServiceData service : services) {
			Assert.assertTrue(approveservicesscreen.isServicePresentInTheList(service.getServiceName()));
			approveservicesscreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}
		approveservicesscreen.clickScreenBackButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			availableservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		for (ServiceData service : services) {
			approveservicesscreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}
		approveservicesscreen.clickSaveButton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
				
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
		Assert.assertEquals(inspectionscreen.getInspectionApprovedPriceValue(inspnumber), inspdata.getInspectionApprovedPrice());	
		
		inspectionscreen.clickBackButton();
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			availableservicesscreen.selectService(service.getServiceName());
		
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
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.DECLINED.getInspectionStatusValue());
		inspectionscreen.clickBackButton();
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		availableservicesscreen.selectService(inspdata.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();		
		
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		Assert.assertEquals(approvescreen.getApprovePriceValue(), inspdata.getInspectionApprovedPrice());
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		Assert.assertEquals(inspectionscreen.getInspectionApprovedPriceValue(inspnumber), inspdata.getInspectionApprovedPrice());
		inspectionscreen.clickBackButton();
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			availableservicesscreen.selectService(service.getServiceName());
		
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();

		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickEditInspectionMenuItem();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.NEW.getInspectionStatusValue());
		inspectionscreen.clickBackButton();
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			availableservicesscreen.selectService(service.getServiceName());

		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickDeclineAllButton();
		for (ServiceData service : services)
			if (service.getServiceStatus() != null)
				approveservicesscreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		approveservicesscreen.clickSaveButton();

		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
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
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

        List<ServiceData> services = inspdata.getServicesList();
        for (ServiceData service : services)
            availableservicesscreen.selectService(service.getServiceName());

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
        inspectionscreen.clickBackButton();
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			availableservicesscreen.selectService(service.getServiceName());

		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		for (ServiceData service : services) {
			approveservicesscreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}

		approveservicesscreen.clickSaveButton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();

		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(inspectionscreen.getInspectionPriceValue(inspnumber), inspdata.getInspectionPrice());
		Assert.assertEquals(inspectionscreen.getInspectionApprovedPriceValue(inspnumber), inspdata.getInspectionApprovedPrice());
		inspectionscreen.clickBackButton();
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
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		availableservicesscreen.switchToAvalableServicesView();

		VNextPriceMatrixesScreen pricematrixesscreen = availableservicesscreen.openMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectPriceMatrix(inspdata.getMatrixServiceData().getHailMatrixName());
		MatrixServiceData matrixServiceData = inspdata.getMatrixServiceData();
		List<MatrixPartData> matrixPartDatas = matrixServiceData.getMatrixPartsData();

		for (MatrixPartData  matrixPartData : matrixPartDatas) {
			VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(matrixPartData.getMatrixPartName());
			vehiclepartinfoscreen.selectVehiclePartSize(matrixPartData.getPartSize());
			vehiclepartinfoscreen.selectVehiclePartSeverity(matrixPartData.getPartSeverity());
			if (matrixPartData.getMatrixAdditionalServices() != null) {
				List<ServiceData> additionalServices = matrixPartData.getMatrixAdditionalServices();
				vehiclepartinfoscreen.selectVehiclePartAdditionalServices(additionalServices);
			}
			vehiclepartinfoscreen.clickSaveVehiclePartInfo();
			vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		}
		vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
		availableservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

		Assert.assertEquals(availableservicesscreen.getTotalPriceValue(), inspdata.getInspectionPrice());
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
		inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveSeveralInspectionsWithOneCustomersWithLineApproveAndWithoutLineApprove(String rowID,
																 String description, JSONObject testData) {

		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(InspectionTypes.valueOf(inspdata.getInspectionType()));
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspdata.setInspectionNumber(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services)
				availableservicesscreen.selectService(service.getServiceName());
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.selectInspection(inspdata.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspdata: inspectionsData)
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspdata.getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isApproveIconForInspectionSelected(inspectionsData.get(0).getInspectionNumber()));
		Assert.assertFalse(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspectionsData.get(0).getInspectionNumber()));
		Assert.assertFalse(approveInspectionsScreen.isApproveIconForInspectionSelected(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspectionsData.get(1).getInspectionNumber()));
		inspectionscreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.unselectInspection(inspdata.getInspectionNumber());
		inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanSelectServiceForApproveOnMultiLineApproveScreenIfLineApprovalEqualsONForThisInspection(String rowID,
																											   String description, JSONObject testData) {

		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspdata.setInspectionNumber(vehicleinfoscreen.getNewInspectionNumber());
			//inspNumbers.add(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services)
				availableservicesscreen.selectService(service.getServiceName());

			VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.selectInspection(inspdata.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspdata: inspectionsData) {
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspdata.getInspectionNumber()));
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspdata.getInspectionNumber());
			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services)
				Assert.assertTrue(approveServicesScreen.isServicePresentInTheList(service.getServiceName()));
			approveInspectionsScreen = approveServicesScreen.clickBackButton();
		}
		inspectionscreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.unselectInspection(inspdata.getInspectionNumber());
		inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeleteInspectionFromLineApproveList(String rowID,
																	 String description, JSONObject testData) {

		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspdata.setInspectionNumber(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services)
				availableservicesscreen.selectService(service.getServiceName());

			VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.selectInspection(inspdata.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.clickMultiselectInspectionsApproveButton();
		approveInspectionsScreen.clickInspectionDeleteButton(inspectionsData.get(1).getInspectionNumber());
		Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(0).getInspectionNumber()));
		approveInspectionsScreen.clickInspectionDeleteButton(inspectionsData.get(0).getInspectionNumber());
		Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(0).getInspectionNumber()));

		inspectionscreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.unselectInspection(inspdata.getInspectionNumber());
		inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalApprovalAmountIncludeOnlySUMOfApprovedService(String rowID,
																														String description, JSONObject testData) {

		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspdata.setInspectionNumber(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services) {
				availableservicesscreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.selectInspection(inspdata.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspdata: inspectionsData) {
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspdata.getInspectionNumber()));
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspdata.getInspectionNumber());
			List<ServiceData> services = inspdata.getServicesList();

			for (ServiceData service : services) {
				approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
			}
			approveServicesScreen.clickSaveButton();
			approveInspectionsScreen = new VNextApproveInspectionsScreen(appiumdriver);
			Assert.assertEquals(approveInspectionsScreen.getInspectionApprovedAmaunt(inspdata.getInspectionNumber()), inspdata.getInspectionApprovedPrice());
		}
		approveInspectionsScreen.clickSaveutton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();

		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserNeedToSelectReasonForAllDeclinedInspection(String rowID,
																			 String description, JSONObject testData) {

		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspdata.setInspectionNumber(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services) {
				availableservicesscreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.selectInspection(inspdata.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspdata: inspectionsData) {
			if (inspdata.getInspectionStatus().equals(InspectionStatus.DECLINED)) {
				VNextDeclineReasonScreen declineReasonScreen = approveInspectionsScreen.clickInspectionDeclineButton(inspdata.getInspectionNumber());
				declineReasonScreen.selectDeclineReason(inspdata.getDeclineReason());
				approveInspectionsScreen = new VNextApproveInspectionsScreen(appiumdriver);

			}
		}
		approveInspectionsScreen.clickSaveutton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		for (InspectionData inspdata: inspectionsData) {
			Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspdata.getInspectionNumber()), inspdata.getInspectionStatus().getStatus());
		}
		inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyInspectionStatusUpdatedAfterApproveDecline(String rowID,
																			 String description, JSONObject testData) {

		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(testcustomer);
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspdata.setInspectionNumber(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services) {
				availableservicesscreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.selectInspection(inspdata.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspdata: inspectionsData) {
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspdata.getInspectionNumber());
			List<ServiceData> services = inspdata.getServicesList();

			for (ServiceData service : services) {
				approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
			}
			approveServicesScreen.clickSaveButton();
			if (inspdata.getDeclineReason() != null) {
				VNextDeclineReasonScreen declineReasonScreen = new VNextDeclineReasonScreen(appiumdriver);
				declineReasonScreen.selectDeclineReason(inspdata.getDeclineReason());
			}
			approveInspectionsScreen = new VNextApproveInspectionsScreen(appiumdriver);
			if (!inspdata.getInspectionStatus().equals(InspectionStatus.DECLINED))
				Assert.assertEquals(approveInspectionsScreen.getInspectionApprovedAmaunt(inspdata.getInspectionNumber()), inspdata.getInspectionApprovedPrice());
		}
		approveInspectionsScreen.clickSaveutton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.saveApprovedInspection();

		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}



	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantAprroveInspectionsWithDifferentCustomers(String rowID,
																	 String description, JSONObject testData) {

		Inspection inspection = JSonDataParser.getTestDataFromJson(testData, Inspection.class);
		List<InspectionData> inspectionsData = inspection.getInspectionDatasList();

		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		for (InspectionData inspdata: inspectionsData) {
			VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
			customersscreen.switchToRetailMode();
			customersscreen.selectCustomer(inspdata.getInspectionRetailCustomer());
			VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
			insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
			vehicleinfoscreen.setVIN(inspdata.getVinNumber());
			inspdata.setInspectionNumber(vehicleinfoscreen.getNewInspectionNumber());
			vehicleinfoscreen.swipeScreensLeft(2);
			VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);

			List<ServiceData> services = inspdata.getServicesList();
			for (ServiceData service : services) {
				availableservicesscreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		}

		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.selectInspection(inspdata.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionscreen.
				clickMultiselectInspectionsApproveButtonAndSelectCustomer(inspectionsData.get(1).getInspectionRetailCustomer());
		for (InspectionData inspdata: inspectionsData) {
			if (inspdata.getInspectionRetailCustomer().getLastName().
					equals(inspectionsData.get(1).getInspectionRetailCustomer().getLastName()))
				Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspdata.getInspectionNumber()));
			else
				Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspdata.getInspectionNumber()));
		}
		inspectionscreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspdata: inspectionsData)
			inspectionscreen.unselectInspection(inspdata.getInspectionNumber());
		inspectionscreen.clickBackButton();
	}


}

package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.InspectionStatuses;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamSupplementsTestCases extends BaseTestCaseTeamEditionRegistration {
	
	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-supplements-testcases-data.json";

    @BeforeClass(description = "Team Supplements Test Cases")
	public void settingUp() {
		JSONDataProvider.dataFile = DATA_FILE;
	}
	
	@AfterClass()
	public void settingDown() {
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementIfAllowSupplementsSetToON(String rowID,
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
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.setVIN(inspdata.getNewVinNumber());
		vehicleinfoscreen.clickSaveInspectionMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantCreateSupplementIfAllowSupplementsSetToOff(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
		VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
		customersscreen.switchToRetailMode();
		customersscreen.selectCustomer(testcustomer);
		VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
		insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR2);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
		vehicleinfoscreen.setVIN(inspdata.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementWhenEditInspection(String rowID,
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
		availableservicesscreen.selectService(inspdata.getServiceNameByIndex(0));
		inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickEditInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(inspdata.getServiceNameByIndex(0), inspdata.getServicePriceByIndex(0));
		availableservicesscreen = selectedServicesScreen.switchToAvalableServicesView();
		availableservicesscreen.selectService(inspdata.getServiceNameByIndex(1));

		selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(inspdata.getServiceNameByIndex(1), inspdata.getServicePriceByIndex(1));
		selectedServicesScreen.setServiceQuantityValue(inspdata.getServiceNameByIndex(1), inspdata.getServiceQuantityByIndex(1));
		inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
		inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertTrue(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementAfterApproveInspection(String rowID,
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
		inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
	
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		availableservicesscreen.selectService(inspdata.getServiceName());
		VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(inspdata.getServiceName(), inspdata.getServicePrice());
		inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();

		Assert.assertEquals(inspectionscreen.getInspectionStatusValue(inspnumber), inspdata.getServiceStatus().getStatus());
		inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifySupplementWillNOTCreatedIfUserDontChangePriceOrQuantity(String rowID,
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
		inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
		
		Assert.assertTrue(inspectionscreen.isInspectionExists(inspnumber));
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		availableservicesscreen.clickSaveInspectionMenuButton();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.NEW_SUPPLEMENT_WILL_NOT_BE_ADDED);
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertTrue(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanAddSupplementsOnlyForApprovedOrNewInspection(String rowID,
            String description, JSONObject testData) throws Exception {

		/*final String DATA_FILE1 = "src/test/java/com/cyberiansoft/test/vnext/data/team-base-inspection-data.json";

		JSONObject jso = JSONDataProvider.extractData_JSON(DATA_FILE1);
		Gson gson = new Gson();
        Inspection inspection = gson.fromJson(jso.toString(), Inspection.class);

		System.out.println("+++++" + inspection.getDevice().getLicenceId());
		System.out.println("+++++" + inspection.getDevice().getEmployeeId());
		System.out.println("+++++" + inspection.getDevice().getDeviceId());
		appID = "f6a0d1a8-0d5d-4850-9930-5670dfa26403";
		System.out.println("+++++" + appID);


		String estimationId =  UUID.randomUUID().toString();
		String vehicleID =  UUID.randomUUID().toString();
		inspection.setEstimationId(estimationId);
		inspection.setVehicleID(vehicleID);
		inspection.getVehicle().setVehicleID(vehicleID);

        ApplicationEndpoints app = new RestClient().createClient();
		System.out.println("++++=================" + vehicleID);
		System.out.println("!!!!=================" + inspection.getVehicleID());
		System.out.println("!!!!=================" + inspection.getVehicle().getVehicleID());



		Response<BasicResponse> res = app.saveInspection(estimationId, inspection.getDevice().getLicenceId(), inspection.getDevice().getDeviceId(),
             appID, inspection.getDevice().getEmployeeId(), true, inspection).execute();

		System.out.println("=================" + res.body().getErrorCode());
		System.out.println("=================" + res.body().getResult());
		System.out.println("=================" + res.body().getErrorDescription());*/
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
		availableservicesscreen.selectService(inspdata.getServiceName());
		inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
		
		VNextInspectionsMenuScreen inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickApproveAllButton();
		approveservicesscreen.clickSaveButton();
		VNextApproveScreen approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);		
		
		Assert.assertEquals(InspectionStatuses.APPROVED.getInspectionStatusValue(), inspectionscreen.getInspectionStatusValue(inspnumber));	
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		vehicleinfoscreen = inspmenu.clickAddSupplementInspectionMenuItem();
		vehicleinfoscreen.swipeScreensLeft(2);
		availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
		List<ServiceData> services = inspdata.getServicesList();
		for (ServiceData service : services)
			availableservicesscreen.selectService(service.getServiceName());
		inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
		
		Assert.assertEquals(InspectionStatuses.NEW.getInspectionStatusValue(), inspectionscreen.getInspectionStatusValue(inspnumber));	
		inspmenu = inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		inspmenu.clickApproveInspectionMenuItem();
		approveservicesscreen = new VNextApproveServicesScreen(appiumdriver);
		approveservicesscreen.clickDeclineAllButton();
		approveservicesscreen.clickSaveButton();
		VNextDeclineReasonScreen declinereasonscreen = new VNextDeclineReasonScreen(appiumdriver);
		declinereasonscreen.selectDeclineReason("Too expensive");
		approvescreen = new VNextApproveScreen(appiumdriver);
		approvescreen.drawSignature();
		approvescreen.clickSaveButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		Assert.assertEquals(InspectionStatuses.DECLINED.getInspectionStatusValue(), inspectionscreen.getInspectionStatusValue(inspnumber));
		
		inspectionscreen.clickOnInspectionByInspNumber(inspnumber);
		Assert.assertFalse(inspmenu.isAddSupplementInspectionMenuItemPresent());
		inspmenu.clickCloseInspectionMenuButton();
		inspectionscreen = new VNextInspectionsScreen(appiumdriver);
		inspectionscreen.clickBackButton();
	}
}

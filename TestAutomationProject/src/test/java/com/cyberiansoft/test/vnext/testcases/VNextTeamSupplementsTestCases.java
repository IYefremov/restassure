package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.InspectionStatuses;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.r360.InspectionDTO;
import com.cyberiansoft.test.dataclasses.r360.WorkOrderDTO;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.dataprovider.JsonUtils;
import com.cyberiansoft.test.objectpoolsi.InspPool;
import com.cyberiansoft.test.objectpoolsi.InspectionFactory;
import com.cyberiansoft.test.objectpoolsi.WOPool;
import com.cyberiansoft.test.objectpoolsi.WorkOrderFactory;
import com.cyberiansoft.test.vnext.config.VNextDataInfo;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
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


		final String apiPath = VNextDataInfo.getInstance().getPathToAPIDataFiles();
		final String DATA_FILE2 = "team-base-inspection-data1.json";
		InspectionDTO inspectionDTO = JsonUtils.getInspectionDTO(new File(apiPath + DATA_FILE2),
				new File(apiPath + VNextDataInfo.getInstance().getDefaultVehicleInfoDataFileName()),
				new File(apiPath + VNextDataInfo.getInstance().getDefaultDeviceInfoDataFileName()));



		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxIdle(1);
		config.setMaxTotal(10);


		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		InspPool inspPool = new InspPool(new InspectionFactory(inspectionDTO, InspectionTypes.O_KRAMAR, testcustomer.getClientId(),
				employee.getEmployeeID(), licenseID, deviceID, appID, appLicenseEntity), config);



		final GenericObjectPool<InspectionDTO> inspectionGenericObjectPool =
				new GenericObjectPool<InspectionDTO>(new InspectionFactory(inspectionDTO, InspectionTypes.O_KRAMAR, testcustomer.getClientId(),
						employee.getEmployeeID(), licenseID, deviceID, appID, appLicenseEntity), config);
		for (int i = 0; i < inspectionGenericObjectPool.getMaxTotal(); i++)
			inspectionGenericObjectPool.addObject();


		for (int i = 0; i < inspectionGenericObjectPool.getMaxTotal(); i++) {
			InspectionDTO inspectionDTO1 = inspectionGenericObjectPool.borrowObject();
			System.out.println("Active [" + inspectionGenericObjectPool.getNumActive() + "]"); //Return the number of instances currently borrowed from this pool
			System.out.println("Idle [" + inspectionGenericObjectPool.getNumIdle() + "]"); //The number of instances currently idle in this pool
			System.out.println("Total Created [" + inspectionGenericObjectPool.getCreatedCount() + "]");
			System.out.println("Number! [" + inspectionDTO1.getLocalNo());
			inspectionGenericObjectPool.invalidateObject(inspectionDTO1);
		}



		final String DATA_FILE1 = "team-base-workorder-data1.json";
		WorkOrderDTO workOrderDTO = JsonUtils.getWorkOrderDTO(new File(apiPath + DATA_FILE1),
				new File(apiPath + VNextDataInfo.getInstance().getDefaultVehicleInfoDataFileName()),
				new File(apiPath + VNextDataInfo.getInstance().getDefaultDeviceInfoDataFileName()));



		config = new GenericObjectPoolConfig();
		config.setMaxIdle(1);
		config.setMaxTotal(10);


		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		WOPool woPool = new WOPool(new WorkOrderFactory(workOrderDTO, WorkOrderTypes.O_KRAMAR, testcustomer.getClientId(),
				employee.getEmployeeID(), licenseID, deviceID, appID, appLicenseEntity), config);



		final GenericObjectPool<WorkOrderDTO> woGenericObjectPool =
				new GenericObjectPool<WorkOrderDTO>(new WorkOrderFactory(workOrderDTO, WorkOrderTypes.O_KRAMAR,
						testcustomer.getClientId(), employee.getEmployeeID(), licenseID, deviceID, appID, appLicenseEntity), config);
		for (int i = 0; i < inspectionGenericObjectPool.getMaxTotal(); i++)
			inspectionGenericObjectPool.addObject();


		for (int i = 0; i < inspectionGenericObjectPool.getMaxTotal(); i++) {
			WorkOrderDTO workOrder1 = woGenericObjectPool.borrowObject();
			System.out.println("Active [" + inspectionGenericObjectPool.getNumActive() + "]"); //Return the number of instances currently borrowed from this pool
			System.out.println("Idle [" + inspectionGenericObjectPool.getNumIdle() + "]"); //The number of instances currently idle in this pool
			System.out.println("Total Created [" + inspectionGenericObjectPool.getCreatedCount() + "]");
			System.out.println("Number! [" + workOrder1.getLocalNo());
			woGenericObjectPool.invalidateObject(workOrder1);
		}


/*
		final String DATA_FILE0 = "src/test/java/com/cyberiansoft/test/vnext/data/team-base-inspection-data1.json";
		jso = JSONDataProvider.extractData_JSON(DATA_FILE0);
		gson = new Gson();
		Inspection inspection = gson.fromJson(jso.toString(), Inspection.class);

		GenericObjectPoolConfig config1 = new GenericObjectPoolConfig();
		config.setMaxIdle(1);
		config.setMaxTotal(10);


		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		final GenericObjectPool<Inspection> inspectionGenericObjectPool1 = new GenericObjectPool<Inspection>(new InspectionFactory(InspectionTypes.O_KRAMAR, inspection, device, employee, appID, appLicenseEntity), config1);
		for (int i = 0; i < inspectionGenericObjectPool1.getMaxTotal(); i++)
			inspectionGenericObjectPool1.addObject();



		for (int i = 0; i < inspectionGenericObjectPool1.getMaxTotal(); i++) {
			Inspection inspectionq = inspectionGenericObjectPool1.borrowObject();
			System.out.println("Active [" + inspectionGenericObjectPool1.getNumActive() + "]"); //Return the number of instances currently borrowed from this pool
			System.out.println("Idle [" + inspectionGenericObjectPool1.getNumIdle() + "]"); //The number of instances currently idle in this pool
			System.out.println("Total Created [" + inspectionGenericObjectPool1.getCreatedCount() + "]");
			System.out.println("Number! [" + inspectionq.getLocalNo());
			inspectionGenericObjectPool1.invalidateObject(inspectionq);
		}*/


		/*InspPool pool1 = new InspPool(new com.cyberiansoft.test.objectpoolsi.InspectionFactory(InspectionTypes.O_KRAMAR,inspection, device, employee, appID, appLicenseEntity), config);
for (int i = 0; i < pool1.getMaxTotal(); i++) {
	pool1.borrowObject();
	System.out.println("Active [" + pool1.getNumActive() + "]"); //Return the number of instances currently borrowed from this pool
	System.out.println("Idle [" + pool1.getNumIdle() + "]"); //The number of instances currently idle in this pool
	System.out.println("Total Created [" + pool1.getCreatedCount() + "]");
}*/





		/*final String DATA_FILE1 = "src/test/java/com/cyberiansoft/test/vnext/data/team-base-workorder-data1.json";
		JSONObject jso = JSONDataProvider.extractData_JSON(DATA_FILE1);
		Gson gson = new Gson();
		WorkOrder workOrder = gson.fromJson(jso.toString(), WorkOrder.class);

		Pool<WorkOrder> pool =
				new BoundedPool< WorkOrder >(
						10,
						new TypeValidator(),
						new WOFactory(WorkOrderTypes.O_KRAMAR, workOrder, device, employee, appID, appLicenseEntity)
				);
		System.out.println("==============================================");

		for (int i = 0; i < 9; i++) {
			System.out.println("+++++" + pool.size());
			WorkOrder wo = pool.get();
			System.out.println("++++++++=" + wo.getLocalNo());
		}*/
		/*Iterator it = pool.iterator();
		while (it.hasNext()) {
			System.out.println("+++++" + pool.size());
			WorkOrder wo = pool.get();
			System.out.println("++++++++=" + wo.getLocalNo());
		}*/



		/*Pool< Inspection > pool =
				new BoundedBlockingPool< Inspection >(
						10,
						new InspectionValidator(),
						new InspectionFactory(InspectionTypes.O_KRAMAR,inspection, device, employee, appID, appLicenseEntity)
				);
        System.out.println("==============================================");
		Iterator it = pool.iterator();
		while (it.hasNext()) {
			System.out.println("+++++" + pool.size());
			Inspection insp = pool.get();
			System.out.println("++++++++=" + insp.getEstimationId());
            System.out.println("++++++++=" + insp.getLocalNo());
		}*/



		//VNextAPIHelper.createInspection(InspectionTypes.O_KRAMAR,inspection, device, employee, appID, appLicenseEntity);


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

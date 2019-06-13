package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextInformationDialog;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextInspectionsTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	@BeforeClass(description="R360 Inspections Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInspectionsTestCasesDataPath();
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyVINIsDecodedCorrectlyForInspection(String rowID,
															   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		BaseUtils.waitABit(2000);
		Assert.assertEquals(vehicleInfoScreen.getVINFieldValue(), inspectionData.getVinNumber());
		Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
		Assert.assertEquals(vehicleInfoScreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
		Assert.assertEquals(vehicleInfoScreen.getYear(), inspectionData.getVehicleInfo().getVehicleYear());
		inspectionsScreen = vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveInspectionUsingOptionFromHamburgerMenu_GeneralCase(String rowID,
															 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectServices(inspectionData.getServicesList());
		availableServicesScreen.clickSaveInspectionMenuButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNavigateToScreenWithRequiredFieldsOnSaveInspectionCalledFromHumburgerMenu_FirstStep(String rowID,
																		   String description, JSONObject testData) {

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.clickCancelMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleInfoScreen.swipeScreenLeft();
		informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNavigateBySwipeThroughWizardStepsForInspeciton(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.clickSaveButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getFirstInspectionNumber(), inspectionNumber);
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNavigateThroughWizardStepsForInspectionByHardwareBackButton(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String insuranceCompany = "Test Insurance Company";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claimInfoScreen.selectInsuranceCompany(insuranceCompany);
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
		availableServicesScreen.changeScreen(ScreenType.VEHICLE_INFO);
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		BaseUtils.waitABit(3000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.ARE_YOU_SURE_STOP_CREATING_INSPECTION);
		vehicleInfoScreen.clickSaveInspectionMenuButton();

		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getFirstInspectionNumber(), inspectionNumber);
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testEditInspectionVehicleInfo(String rowID,
																				String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String VIN = "1D7HW48NX6S507810";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(VIN);
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		inspectionsScreen = claimInfoScreen.saveInspectionViaMenu();
		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		vehicleInfoScreen.setMilage(inspectionData.getVehicleInfo().getMileage());
		vehicleInfoScreen.setStockNo(inspectionData.getVehicleInfo().getStockNumber());
		vehicleInfoScreen.setRoNo(inspectionData.getVehicleInfo().getRoNumber());
		vehicleInfoScreen.setLicPlate(inspectionData.getVehicleInfo().getVehicleLicensePlate());
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();

		vehicleInfoScreen = inspectionsScreen.clickOpenInspectionToEdit(inspectionNumber);
		Assert.assertEquals(vehicleInfoScreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
		Assert.assertEquals(vehicleInfoScreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
		Assert.assertEquals(vehicleInfoScreen.getYear(), inspectionData.getVehicleInfo().getVehicleYear());
		Assert.assertEquals(vehicleInfoScreen.getVINFieldValue(), inspectionData.getVehicleInfo().getVINNumber());
		Assert.assertEquals(vehicleInfoScreen.getMilage(), inspectionData.getVehicleInfo().getMileage());
		Assert.assertEquals(vehicleInfoScreen.getStockNo(), inspectionData.getVehicleInfo().getStockNumber().toUpperCase());
		Assert.assertEquals(vehicleInfoScreen.getRoNo(), inspectionData.getVehicleInfo().getRoNumber().toUpperCase());
		Assert.assertEquals(vehicleInfoScreen.getLicPlate(), inspectionData.getVehicleInfo().getVehicleLicensePlate().toUpperCase());
		inspectionsScreen = vehicleInfoScreen.cancelInspection();
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateCustomerAlongWithInspection(String rowID,
											  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		RetailCustomer inspCustomer = inspectionData.getInspectionRetailCustomer();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		VNextNewCustomerScreen newCustomerScreen = customersScreen.clickAddCustomerButton();
		newCustomerScreen.setCustomerFirstName(inspCustomer.getFirstName());
		newCustomerScreen.setCustomerLastName(inspCustomer.getLastName());
		newCustomerScreen.setCustomerEmail(inspCustomer.getMailAddress());
		newCustomerScreen.setCustomerAddress(inspCustomer.getCustomerAddress1());
		newCustomerScreen.setCustomerCity(inspCustomer.getCustomerCity());
		newCustomerScreen.setCustomerZIP(inspCustomer.getCustomerZip());
		newCustomerScreen.clickSaveCustomerButton();
		BaseUtils.waitABit(7000);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(vehicleInfoScreen.getCustomercontextValue(), inspCustomer.getFullName());
		final String inspnum = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claimInfoScreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claimInfoScreen.changeScreen(ScreenType.VEHICLE_INFO);
		vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsScreen.getFirstInspectionNumber(), inspnum);
		Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspnum), inspCustomer.getFullName());
		homeScreen = inspectionsScreen.clickBackButton();
		customersScreen = homeScreen.clickCustomersMenuItem();
		newCustomerScreen = customersScreen.openCustomerForEdit(inspCustomer);
		Assert.assertEquals(newCustomerScreen.getCustomerFirstName(), inspCustomer.getFirstName());
		Assert.assertEquals(newCustomerScreen.getCustomerLastName(), inspCustomer.getLastName());
		Assert.assertEquals(newCustomerScreen.getCustomerAddress(), inspCustomer.getCustomerAddress1());
		Assert.assertEquals(newCustomerScreen.getCustomerCity(), inspCustomer.getCustomerCity());
		Assert.assertEquals(newCustomerScreen.getCustomerZIP(), inspCustomer.getCustomerZip());
		customersScreen = newCustomerScreen.clickBackButton();
		customersScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanArchiveCreatedInspection(String rowID,
													  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		final String inspectionNumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);

		InspectionSteps.archiveInspection(inspectionNumber);
		Assert.assertFalse(inspectionsScreen.isInspectionExists(inspectionNumber), "Inspection: " + inspectionNumber +
			" still exists, but shouldn't");
		inspectionsScreen.clickBackButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanArchiveSeveralInspections(String rowID,
														  String description, JSONObject testData) {

		final int inspToArchive = 3;

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		int inspectionNumbers = inspectionsScreen.getNumberOfInspectionsInList();
		
		for (int i = 0; i < inspToArchive; i++) {
			String inspectionNumber = inspectionsScreen.getFirstInspectionNumber();
			InspectionSteps.archiveInspection(inspectionNumber);
			Assert.assertFalse(inspectionsScreen.isInspectionExists(inspectionNumber));
		}
		Assert.assertEquals(inspectionsScreen.getNumberOfInspectionsInList(),inspectionNumbers - inspToArchive);
		
		inspectionsScreen.clickBackButton();
		
	}

}

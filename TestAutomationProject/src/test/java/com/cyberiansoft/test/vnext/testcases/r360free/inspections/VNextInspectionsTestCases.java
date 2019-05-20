package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
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

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360-inspections-testcases-data.json";


	@BeforeClass(description="R360 Inspections Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}


	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyVINIsDecodedCorrectlyForInspection(String rowID,
															   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
		BaseUtils.waitABit(2000);
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), inspectionData.getVinNumber());
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
		Assert.assertEquals(vehicleinfoscreen.getYear(), inspectionData.getVehicleInfo().getVehicleYear());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testSaveInspectionUsingOptionFromHamburgerMenu_GeneralCase(String rowID,
															 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectServices(inspectionData.getServicesList());
		inspservicesscreen.clickSaveInspectionMenuButton();
		inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNavigateToScreenWithRequiredFieldsOnSaveInspectionCalledFromHumburgerMenu_FirstStep(String rowID,
																		   String description, JSONObject testData) {

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.clickCancelMenuItem();
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.CANCEL_INSPECTION_ALERT));
		vehicleinfoscreen.swipeScreenLeft();
		informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		msg = informationdlg.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.VIN_REQUIRED_MSG);
		vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNavigateBySwipeThroughWizardStepsForInspeciton(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
		VNextAvailableServicesScreen inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.clickSaveButton();
		inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnumber);
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testNavigateThroughWizardStepsForInspectionByHardwareBackButton(String rowID,
																   String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claiminfoscreen.selectInsuranceCompany("Test Insurance Company");
		vehicleinfoscreen.swipeScreenLeft();
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspservicesscreen = vehicleinfoscreen.goToInspectionServicesScreen();
		inspservicesscreen.selectService(inspectionData.getMoneyServiceName());
		inspservicesscreen.swipeScreensRight(2);
		vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		AppiumUtils.clickHardwareBackButton();
		BaseUtils.waitABit(3000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(DriverBuilder.getInstance().getAppiumDriver());
		String msg = informationdlg.clickInformationDialogNoButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.ARE_YOU_SURE_STOP_CREATING_INSPECTION);
		vehicleinfoscreen.clickSaveInspectionMenuButton();

		inspectionsscreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnumber);
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testEditInspectionVehicleInfo(String rowID,
																				String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String VIN = "1D7HW48NX6S507810";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		vehicleinfoscreen.setVIN(VIN);
		final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claiminfoscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		inspectionsscreen = claiminfoscreen.saveInspectionViaMenu();
		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
		vehicleinfoscreen.setMilage(inspectionData.getVehicleInfo().getMileage());
		vehicleinfoscreen.setStockNo(inspectionData.getVehicleInfo().getStockNumber());
		vehicleinfoscreen.setRoNo(inspectionData.getVehicleInfo().getRoNumber());
		vehicleinfoscreen.setLicPlate(inspectionData.getVehicleInfo().getVehicleLicensePlate());
		inspectionsscreen = vehicleinfoscreen.saveInspectionViaMenu();

		vehicleinfoscreen = inspectionsscreen.clickOpenInspectionToEdit(inspnumber);
		Assert.assertEquals(vehicleinfoscreen.getMakeInfo(), inspectionData.getVehicleInfo().getVehicleMake());
		Assert.assertEquals(vehicleinfoscreen.getModelInfo(), inspectionData.getVehicleInfo().getVehicleModel());
		Assert.assertEquals(vehicleinfoscreen.getYear(), inspectionData.getVehicleInfo().getVehicleYear());
		Assert.assertEquals(vehicleinfoscreen.getVINFieldValue(), inspectionData.getVehicleInfo().getVINNumber());
		Assert.assertEquals(vehicleinfoscreen.getMilage(), inspectionData.getVehicleInfo().getMileage());
		Assert.assertEquals(vehicleinfoscreen.getStockNo(), inspectionData.getVehicleInfo().getStockNumber().toUpperCase());
		Assert.assertEquals(vehicleinfoscreen.getRoNo(), inspectionData.getVehicleInfo().getRoNumber().toUpperCase());
		Assert.assertEquals(vehicleinfoscreen.getLicPlate(), inspectionData.getVehicleInfo().getVehicleLicensePlate().toUpperCase());
		inspectionsscreen = vehicleinfoscreen.cancelInspection();
		inspectionsscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testCreateCustomerAlongWithInspection(String rowID,
											  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		RetailCustomer inspCustomer = inspectionData.getInspectionRetailCustomer();

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
		newcustomerscreen.setCustomerFirstName(inspCustomer.getFirstName());
		newcustomerscreen.setCustomerLastName(inspCustomer.getLastName());
		newcustomerscreen.setCustomerEmail(inspCustomer.getMailAddress());
		newcustomerscreen.setCustomerAddress(inspCustomer.getCustomerAddress1());
		newcustomerscreen.setCustomerCity(inspCustomer.getCustomerCity());
		newcustomerscreen.setCustomerZIP(inspCustomer.getCustomerZip());
		newcustomerscreen.clickSaveCustomerButton();
		BaseUtils.waitABit(7000);
		VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(vehicleinfoscreen.getCustomercontextValue(), inspCustomer.getFullName());
		final String inspnum = vehicleinfoscreen.getNewInspectionNumber();
		vehicleinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		claiminfoscreen.selectInsuranceCompany(inspectionData.getInsuranceCompanyData().getInsuranceCompanyName());
		claiminfoscreen.swipeScreenRight();
		vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsscreen = vehicleinfoscreen.saveInspectionfromFirstScreen();
		Assert.assertEquals(inspectionsscreen.getFirstInspectionNumber(), inspnum);
		Assert.assertEquals(inspectionsscreen.getInspectionCustomerValue(inspnum), inspCustomer.getFullName());
		homescreen = inspectionsscreen.clickBackButton();
		customersscreen = homescreen.clickCustomersMenuItem();
		newcustomerscreen = customersscreen.openCustomerForEdit(inspCustomer);
		Assert.assertEquals(newcustomerscreen.getCustomerFirstName(), inspCustomer.getFirstName());
		Assert.assertEquals(newcustomerscreen.getCustomerLastName(), inspCustomer.getLastName());
		Assert.assertEquals(newcustomerscreen.getCustomerAddress(), inspCustomer.getCustomerAddress1());
		Assert.assertEquals(newcustomerscreen.getCustomerCity(), inspCustomer.getCustomerCity());
		Assert.assertEquals(newcustomerscreen.getCustomerZIP(), inspCustomer.getCustomerZip());
		customersscreen = newcustomerscreen.clickBackButton();
		customersscreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanArchiveCreatedInspection(String rowID,
													  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		final String inspnumber = InspectionSteps.createR360Inspection(testcustomer, inspectionData);

		InspectionSteps.archiveInspection(inspnumber);
		Assert.assertFalse(inspectionsscreen.isInspectionExists(inspnumber), "Inspection: " + inspnumber +
			" still exists, but shouldn't");
		inspectionsscreen.clickBackButton();

	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanArchiveSeveralInspections(String rowID,
														  String description, JSONObject testData) {

		final int inspToArchive = 3;

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		int inspnumbers = inspectionsscreen.getNumberOfInspectionsInList();
		
		for (int i = 0; i < inspToArchive; i++) {
			String inspnumber = inspectionsscreen.getFirstInspectionNumber();
			InspectionSteps.archiveInspection(inspnumber);
			Assert.assertFalse(inspectionsscreen.isInspectionExists(inspnumber));
		}
		Assert.assertEquals(inspectionsscreen.getNumberOfInspectionsInList(),inspnumbers - inspToArchive);
		
		inspectionsscreen.clickBackButton();
		
	}

}

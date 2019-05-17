package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextBOLoginScreenWebPage;
import com.cyberiansoft.test.vnextbo.screens.VNextConfirmationDialog;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextInspectionApproveOnBOTestCases extends BaseTestCaseWithDeviceRegistrationAndUserLogin {

	private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/r360-inspection-approve-on-bo-testcases-data.json";

	@BeforeClass(description="R360 Inspection Approve On BO Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = DATA_FILE;
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyPosibilityToApproveNewInspectionOnBO(String rowID,
															 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String approveNotes = "Approve Inspection via QuickAccess";
		
		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspinfoscreen.setVIN(inspectionData.getVinNumber());
		final VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
		Assert.assertEquals(inspinfoscreen.getMakeInfo(), vehicleInfoData.getVehicleMake());
		Assert.assertEquals(inspinfoscreen.getModelInfo(), vehicleInfoData.getVehicleModel());
		Assert.assertEquals(inspinfoscreen.getYear(), vehicleInfoData.getVehicleYear());
		inspinfoscreen.setMilage(vehicleInfoData.getMileage());
		inspinfoscreen.setStockNo(vehicleInfoData.getStockNumber());
		inspinfoscreen.setRoNo(vehicleInfoData.getRoNumber());
		inspinfoscreen.setPoNo(vehicleInfoData.getPoNumber());
		inspinfoscreen.setLicPlate(vehicleInfoData.getVehicleLicensePlate());
			
		final String inspNumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		final InsuranceCompanyData insuranceCompanyData = inspectionData.getInsuranceCompanyData();
		claiminfoscreen.selectInsuranceCompany(insuranceCompanyData.getInsuranceCompanyName());
		claiminfoscreen.setClaimNumber(insuranceCompanyData.getClaimNumber());
		claiminfoscreen.setPolicyNumber(insuranceCompanyData.getPolicyNumber());
		claiminfoscreen.setDeductibleValue(insuranceCompanyData.getDeductible());
		inspinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		//selectservicesscreen.selectService(percservices);
		inspservicesscreen.selectService(inspectionData.getMoneyServiceName());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
		final MatrixPartData matrixPartData = matrixServiceData.getMatrixPartData();
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(matrixPartData.getMatrixPartName());
		vehiclepartinfoscreen.selectVehiclePartSize(matrixPartData.getPartSize());
		vehiclepartinfoscreen.selectVehiclePartSeverity(matrixPartData.getPartSeverity());
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(matrixPartData.getMatrixAdditionalService().getServiceName());
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceName(), inspectionData.getMoneyServicePrice());
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceName(), inspectionData.getMoneyServiceQuantity());

		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		inspectionsscreen.clickBackButton();


		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.waitABit(1000*20);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspNumber);
		insppage.clickInspectionApproveButton();
		VNextConfirmationDialog confirmdialog = new VNextConfirmationDialog(webdriver);

		confirmdialog.clickNoButton();
		BaseUtils.waitABit(500);
		insppage.approveInspection(approveNotes);

		insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspNumber);
		Assert.assertEquals(insppage.getInspectionStatus(inspNumber), inspectionData.getInspectionStatus().getStatus());
		webdriver.quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyPosibilityToDeclineNewInspectionOnBO(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String approveNotes = "Decline Inspection via QuickAccess";

		VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsscreen = homescreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersscreen = inspectionsscreen.clickAddInspectionButton();
		customersscreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen inspinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspinfoscreen.setVIN(inspectionData.getVinNumber());
		final VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
		Assert.assertEquals(inspinfoscreen.getMakeInfo(), vehicleInfoData.getVehicleMake());
		Assert.assertEquals(inspinfoscreen.getModelInfo(), vehicleInfoData.getVehicleModel());
		Assert.assertEquals(inspinfoscreen.getYear(), vehicleInfoData.getVehicleYear());
		inspinfoscreen.setMilage(vehicleInfoData.getMileage());
		inspinfoscreen.setStockNo(vehicleInfoData.getStockNumber());
		inspinfoscreen.setRoNo(vehicleInfoData.getRoNumber());
		inspinfoscreen.setPoNo(vehicleInfoData.getPoNumber());
		inspinfoscreen.setLicPlate(vehicleInfoData.getVehicleLicensePlate());

		final String inspNumber = inspinfoscreen.getNewInspectionNumber();
		inspinfoscreen.swipeScreenLeft();
		VNextClaimInfoScreen claiminfoscreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		final InsuranceCompanyData insuranceCompanyData = inspectionData.getInsuranceCompanyData();
		claiminfoscreen.selectInsuranceCompany(insuranceCompanyData.getInsuranceCompanyName());
		claiminfoscreen.setClaimNumber(insuranceCompanyData.getClaimNumber());
		claiminfoscreen.setPolicyNumber(insuranceCompanyData.getPolicyNumber());
		claiminfoscreen.setDeductibleValue(insuranceCompanyData.getDeductible());
		inspinfoscreen.changeScreen("Services");
		VNextAvailableServicesScreen inspservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		//selectservicesscreen.selectService(percservices);
		inspservicesscreen.selectService(inspectionData.getMoneyServiceName());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		VNextPriceMatrixesScreen pricematrixesscreen = inspservicesscreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
		VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
		final MatrixPartData matrixPartData = matrixServiceData.getMatrixPartData();
		VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(matrixPartData.getMatrixPartName());
		vehiclepartinfoscreen.selectVehiclePartSize(matrixPartData.getPartSize());
		vehiclepartinfoscreen.selectVehiclePartSeverity(matrixPartData.getPartSeverity());
		vehiclepartinfoscreen.selectVehiclePartAdditionalService(matrixPartData.getMatrixAdditionalService().getServiceName());
		vehiclepartinfoscreen.clickSaveVehiclePartInfo();
		vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = inspservicesscreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceName(), inspectionData.getMoneyServicePrice());
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceName(), inspectionData.getMoneyServiceQuantity());

		inspectionsscreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsscreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		inspectionsscreen.clickBackButton();

		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginpage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginpage.waitABit(1000*15);
		loginpage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftmenu = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspNumber);
		insppage.clickInspectionApproveButton();
		VNextConfirmationDialog confirmdialog = new VNextConfirmationDialog(webdriver);
		confirmdialog.clickNoButton();
		BaseUtils.waitABit(1000);
		insppage.declineInspection(approveNotes);
		
		insppage = leftmenu.selectInspectionsMenu();
		insppage.selectInspectionInTheList(inspNumber);
		Assert.assertEquals(insppage.getInspectionStatus(inspNumber), inspectionData.getInspectionStatus().getStatus());
		webdriver.quit();
	}

}

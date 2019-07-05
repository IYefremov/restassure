package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextPriceMatrixesScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
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

	@BeforeClass(description="R360 Inspection Approve On BO Test Cases")
	public void beforeClass() {
		JSONDataProvider.dataFile = VNextFreeTestCasesDataPaths.getInstance().getInspectionApproveOnBOTestCasesDataPath();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyPosibilityToApproveNewInspectionOnBO(String rowID,
															 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String approveNotes = "Approve Inspection via QuickAccess";
		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();

		VehicleInfoScreenValidations.validateVehicleInfo(vehicleInfoData);
		VehicleInfoScreenSteps.setVehicleInfo(vehicleInfoData);

		final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		final InsuranceCompanyData insuranceCompanyData = inspectionData.getInsuranceCompanyData();
		claimInfoScreen.selectInsuranceCompany(insuranceCompanyData.getInsuranceCompanyName());
		claimInfoScreen.setClaimNumber(insuranceCompanyData.getClaimNumber());
		claimInfoScreen.setPolicyNumber(insuranceCompanyData.getPolicyNumber());
		claimInfoScreen.setDeductibleValue(insuranceCompanyData.getDeductible());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
		VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
		final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
		vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		inspectionsScreen.clickBackButton();


		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginPage.waitABit(1000*20);
		loginPage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspNumber);
		inspectionsWebPage.clickInspectionApproveButton();
		VNextConfirmationDialog confirmationDialog = new VNextConfirmationDialog(webdriver);

		confirmationDialog.clickNoButton();
		BaseUtils.waitABit(500);
		inspectionsWebPage.approveInspection(approveNotes);

		inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspNumber), inspectionData.getInspectionStatus().getStatus());
		webdriver.quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyPosibilityToDeclineNewInspectionOnBO(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String approveNotes = "Decline Inspection via QuickAccess";

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.selectCustomer(testcustomer);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
		final VehicleInfoData vehicleInfoData = inspectionData.getVehicleInfo();
		VehicleInfoScreenValidations.validateVehicleInfo(vehicleInfoData);
		VehicleInfoScreenSteps.setVehicleInfo(vehicleInfoData);

		final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
		VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
		final InsuranceCompanyData insuranceCompanyData = inspectionData.getInsuranceCompanyData();
		claimInfoScreen.selectInsuranceCompany(insuranceCompanyData.getInsuranceCompanyName());
		claimInfoScreen.setClaimNumber(insuranceCompanyData.getClaimNumber());
		claimInfoScreen.setPolicyNumber(insuranceCompanyData.getPolicyNumber());
		claimInfoScreen.setDeductibleValue(insuranceCompanyData.getDeductible());
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		//selectservicesscreen.selectService(percservices);
		availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(matrixServiceData.getMatrixServiceName());
		VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(matrixServiceData.getHailMatrixName());
		final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
		vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		vehiclePartInfoScreen.clickSaveVehiclePartInfo();
		vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		Assert.assertEquals(selectedServicesScreen.getSelectedPriceMatrixValueForPriceMatrixService(matrixServiceData.getMatrixServiceName()), matrixServiceData.getHailMatrixName());

		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

		inspectionsScreen = selectedServicesScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspNumber), PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		inspectionsScreen.clickBackButton();

		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginScreenWebPage loginPage = PageFactory.initElements(webdriver,
				VNextBOLoginScreenWebPage.class);
		loginPage.waitABit(1000*15);
		loginPage.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspNumber);
		inspectionsWebPage.clickInspectionApproveButton();
		VNextConfirmationDialog confirmationDialog = new VNextConfirmationDialog(webdriver);
		confirmationDialog.clickNoButton();
		BaseUtils.waitABit(1000);
		inspectionsWebPage.declineInspection(approveNotes);
		
		inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspNumber), inspectionData.getInspectionStatus().getStatus());
		webdriver.quit();
	}

}

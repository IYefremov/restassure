package com.cyberiansoft.test.vnext.testcases.r360free.inspections;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.InsuranceCompanyData;
import com.cyberiansoft.test.dataclasses.MatrixServiceData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.driverutils.WebdriverInicializator;
import com.cyberiansoft.test.ios10_client.utils.PricesCalculations;
import com.cyberiansoft.test.vnext.config.VNextFreeRegistrationInfo;
import com.cyberiansoft.test.vnext.data.r360free.VNextFreeTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.InspectionStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360free.BaseTestCaseWithDeviceRegistrationAndUserLogin;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import com.cyberiansoft.test.vnextbo.screens.VNexBOLeftMenuPanel;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.dialogs.VNextBOModalDialogSteps;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.steps.login.VNextBOLoginSteps;
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

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());
		
		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        final InsuranceCompanyData insuranceCompanyData = inspectionData.getInsuranceCompanyData();
		ClaimInfoSteps.selectInsuranceCompany(insuranceCompanyData.getInsuranceCompanyName());
		ClaimInfoSteps.setClaimNumber(insuranceCompanyData.getClaimNumber());
		ClaimInfoSteps.setPolicyNumber(insuranceCompanyData.getPolicyNumber());
		ClaimInfoSteps.setDeductibleValue(insuranceCompanyData.getDeductible());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
		vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		vehiclePartInfoScreen.clickScreenBackButton();
        availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();

		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		ScreenNavigationSteps.pressBackButton();


		WebDriver
				webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
		VNextBOInspectionsPageSteps.clickInspectionApproveButton();
		VNextBOModalDialogSteps.clickNoButton();
		BaseUtils.waitABit(500);
		inspectionsWebPage.approveInspection(approveNotes);

		inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), InspectionStatus.APPROVED.getStatusString());
		webdriver.quit();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyPosibilityToDeclineNewInspectionOnBO(String rowID,
																	  String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		final String approveNotes = "Decline Inspection via QuickAccess";

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, inspectionData);
		VehicleInfoScreenValidations.validateVehicleInfo(inspectionData.getVehicleInfo());

		WizardScreenSteps.navigateToWizardScreen(ScreenType.CLAIM);
        final InsuranceCompanyData insuranceCompanyData = inspectionData.getInsuranceCompanyData();
		ClaimInfoSteps.selectInsuranceCompany(insuranceCompanyData.getInsuranceCompanyName());
		ClaimInfoSteps.setClaimNumber(insuranceCompanyData.getClaimNumber());
		ClaimInfoSteps.setPolicyNumber(insuranceCompanyData.getPolicyNumber());
		ClaimInfoSteps.setDeductibleValue(insuranceCompanyData.getDeductible());
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		availableServicesScreen.selectService(inspectionData.getMoneyServiceData().getServiceName());
		final MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		final VehiclePartData vehiclePartData = matrixServiceData.getVehiclePartData();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
		vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
		vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
		vehiclePartInfoScreen.selectVehiclePartAdditionalService(vehiclePartData.getVehiclePartAdditionalService().getServiceName());
		vehiclePartInfoScreen.clickScreenBackButton();
        vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();
		VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
		selectedServicesScreen.setServiceAmountValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServicePrice());
		selectedServicesScreen.setServiceQuantityValue(inspectionData.getMoneyServiceData().getServiceName(), inspectionData.getMoneyServiceData().getServiceQuantity());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, PricesCalculations.getPriceRepresentation(inspectionData.getInspectionPrice()));
		ScreenNavigationSteps.pressBackButton();

		WebDriver
		webdriver = WebdriverInicializator.getInstance().initWebDriver(browsertype);
		webdriver.get(deviceOfficeUrl);
		VNextBOLoginSteps.userLogin(VNextFreeRegistrationInfo.getInstance().getR360UserUserName(),
				VNextFreeRegistrationInfo.getInstance().getR360UserPassword());
		VNexBOLeftMenuPanel leftMenuPanel = PageFactory.initElements(webdriver,
				VNexBOLeftMenuPanel.class);
		VNextBOInspectionsWebPage inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
		VNextBOInspectionsPageSteps.clickInspectionApproveButton();
		VNextBOModalDialogSteps.clickNoButton();
		BaseUtils.waitABit(1000);
		inspectionsWebPage.declineInspection(approveNotes);
		
		inspectionsWebPage = leftMenuPanel.selectInspectionsMenu();
		inspectionsWebPage.selectInspectionInTheList(inspectionNumber);
		Assert.assertEquals(inspectionsWebPage.getInspectionStatus(inspectionNumber), InspectionStatus.DECLINED.getStatusString());
		webdriver.quit();
	}

}

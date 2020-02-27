package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.validations.ApproveValidations;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamInspectionsLineApprovalTestCases extends BaseTestClass {

	@BeforeClass(description = "Team Inspections Line Approval Test Cases")
	public void settingUp() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsLineApprovalTestCasesDataPath();
		VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		inspectionsScreen.clickBackButton();
	}
	
	@AfterClass()
	public void settingDown() {
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveServiceForInspectionIfLineApprovalEqualsON(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		Assert.assertTrue(approveServicesScreen.isApproveAllButtonDisplayed());
		Assert.assertTrue(approveServicesScreen.isDeclineAllButtonDisplayed());
		Assert.assertTrue(approveServicesScreen.isSkipAllButtonDisplayed());
		for (ServiceData service : services) {
			Assert.assertTrue(approveServicesScreen.isServicePresentInTheList(service.getServiceName()));
			approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}
		ScreenNavigationSteps.pressBackButton();
		ScreenNavigationSteps.pressBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveDeclineAndSkipDifferentServicesInOneInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (ServiceData service : services) {
			if (service.getServiceStatus() != null)
				approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}
		approveServicesScreen.clickSaveButton();
		ApproveValidations.verifyApprovePriceValue(inspectionData.getInspectionApprovedPrice());
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
		InspectionsValidations.verifyInspectionApprovedPrice(inspectionNumber, inspectionData.getInspectionApprovedPrice());

		ScreenNavigationSteps.pressBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeclineAllServiceIfLineApprovalEqualsON(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		approveServicesScreen.clickDeclineAllButton();
		approveServicesScreen.clickSaveButton();
		VNextDeclineReasonScreen declineReasonsScreen = new VNextDeclineReasonScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		declineReasonsScreen.selectDeclineReason(inspectionData.getDeclineReason());

		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.DECLINED);
		ScreenNavigationSteps.pressBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantApproveOrDeclineServicesIfLineApprovalEqualsOFF(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);

		ApproveValidations.verifyApprovePriceValue(inspectionData.getInspectionApprovedPrice());
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		InspectionsValidations.verifyInspectionApprovedPrice(inspectionNumber, inspectionData.getInspectionApprovedPrice());
		ScreenNavigationSteps.pressBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyStateChangedToNewIfUserEditInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);

		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.EDIT);
		InspectionSteps.saveInspection();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.NEW);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyInspectionHaveStateApproveIfUserSelectOnlyOneServiceAsApproved(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		approveServicesScreen.clickDeclineAllButton();
		approveServicesScreen.setServiceStatus(inspectionData.getServicesList().get(0).getServiceName(), inspectionData.getServicesList().get(0).getServiceStatus());
		approveServicesScreen.clickSaveButton();

		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountIncludeOnlyApprovedServices(String rowID,
																				String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (ServiceData service : services) {
			approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}

		approveServicesScreen.clickSaveButton();
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();

		InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
		InspectionsValidations.verifyInspectionApprovedPrice(inspectionNumber, inspectionData.getInspectionApprovedPrice());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserSeeOnlyTotalAmountForSelectedMatrixWhenApproveInspectionWithLineApprovalEqualsON(String rowID,
																						String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		HomeScreenSteps.openCreateMyInspection();
		InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR3, inspectionData);
		WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		availableServicesScreen.switchToAvalableServicesView();

		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		AvailableServicesScreenSteps.selectMatrixService(matrixServiceData);
		List<VehiclePartData> vehiclePartsData = matrixServiceData.getVehiclePartsData();
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		for (VehiclePartData  vehiclePartData : vehiclePartsData) {
			VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
			vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
			vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());
			if (vehiclePartData.getVehiclePartAdditionalServices() != null) {
				List<ServiceData> additionalServices = vehiclePartData.getVehiclePartAdditionalServices();
				for (ServiceData additionalService : additionalServices) {
					if (additionalService.getServicePrice() != null) {
						VNextServiceDetailsScreen serviceDetailsScreen = vehiclePartInfoScreen.openServiceDetailsScreen(additionalService.getServiceName());
						serviceDetailsScreen.setServiceAmountValue(additionalService.getServicePrice());
						serviceDetailsScreen.setServiceQuantityValue(additionalService.getServiceQuantity());
						serviceDetailsScreen.clickServiceDetailsDoneButton();
					} else
						vehiclePartInfoScreen.selectVehiclePartAdditionalService(additionalService.getServiceName());
				}

			}
			vehiclePartInfoScreen.clickScreenBackButton();
		}
		vehiclePartsScreen.clickVehiclePartsSaveButton();

		Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
		final String inspectionNumber = InspectionSteps.saveInspection();
		InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
		InspectionSteps.openInspectionMenu(inspectionNumber);
		MenuSteps.selectMenuItem(MenuItems.APPROVE);
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
		approveServicesScreen.isServicePresentInTheList(inspectionData.getMatrixServiceData().getMatrixServiceName());
		Assert.assertEquals(approveServicesScreen.getServicePriceValue(inspectionData.getMatrixServiceData().getMatrixServiceName()),
				inspectionData.getInspectionPrice());
		approveServicesScreen.clickApproveAllButton();
		approveServicesScreen.clickSaveButton();
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();

		InspectionsValidations.verifyInspectionStatus(inspectionNumber, InspectionStatus.APPROVED);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveSeveralInspectionsWithOneCustomersWithLineApproveAndWithoutLineApprove(String rowID,
																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		HomeScreenSteps.openInspections();
		for (InspectionData inspectionData: inspectionsData) {
			VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
			inspectionsScreen.clickAddInspectionButton();
			InspectionSteps.createInspection(testcustomer, InspectionTypes.valueOf(inspectionData.getInspectionType()), inspectionData);
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

			inspectionData.getServicesList().forEach(service -> availableServicesScreen.selectService(service.getServiceName()));
			inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
		}

		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData)
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isApproveIconForInspectionSelected(inspectionsData.get(0).getInspectionNumber()));
		Assert.assertFalse(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspectionsData.get(0).getInspectionNumber()));
		Assert.assertFalse(approveInspectionsScreen.isApproveIconForInspectionSelected(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspectionsData.get(1).getInspectionNumber()));
		approveInspectionsScreen.clickBackButton();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.unselectInspection(inspectionData.getInspectionNumber());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanSelectServiceForApproveOnMultiLineApproveScreenIfLineApprovalEqualsONForThisInspection(String rowID,
																											   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		HomeScreenSteps.openInspections();
		for (InspectionData inspectionData: inspectionsData) {
			VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
			inspectionsScreen.clickAddInspectionButton();
			InspectionSteps.createInspection(testcustomer,InspectionTypes.O_KRAMAR3, inspectionData);
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

			inspectionData.getServicesList().forEach(service -> availableServicesScreen.selectService(service.getServiceName()));
			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : inspectionData.getServicesList())
				selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
		}
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspectionData.getInspectionNumber());
			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services)
				if (service.getServiceStatus() != null)
					Assert.assertTrue(approveServicesScreen.isServicePresentInTheList(service.getServiceName()));
			approveInspectionsScreen = approveServicesScreen.clickBackButton();
		}
		approveInspectionsScreen.clickBackButton();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.unselectInspection(inspectionData.getInspectionNumber());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeleteInspectionFromLineApproveList(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		HomeScreenSteps.openInspections();
		for (InspectionData inspectionData: inspectionsData) {
			VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
			inspectionsScreen.clickAddInspectionButton();
			InspectionSteps.createInspection(testcustomer,InspectionTypes.O_KRAMAR3, inspectionData);
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

			inspectionData.getServicesList().forEach(service -> availableServicesScreen.selectService(service.getServiceName()));
			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : inspectionData.getServicesList())
				selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
		}
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();
		approveInspectionsScreen.clickInspectionDeleteButton(inspectionsData.get(1).getInspectionNumber());
		Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(0).getInspectionNumber()));
		approveInspectionsScreen.clickInspectionDeleteButton(inspectionsData.get(0).getInspectionNumber());
		Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(0).getInspectionNumber()));

		approveInspectionsScreen.clickBackButton();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.unselectInspection(inspectionData.getInspectionNumber());
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalApprovalAmountIncludeOnlySUMOfApprovedService(String rowID,
																														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();
		final String approveTotal = "$111.15";

		HomeScreenSteps.openInspections();
		for (InspectionData inspectionData: inspectionsData) {
			VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
			inspectionsScreen.clickAddInspectionButton();
			InspectionSteps.createInspection(testcustomer,InspectionTypes.O_KRAMAR3, inspectionData);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

			inspectionData.getServicesList().forEach(service -> availableServicesScreen.selectService(service.getServiceName()));

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : inspectionData.getServicesList())
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
		}
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspectionData.getInspectionNumber());
			List<ServiceData> services = inspectionData.getServicesList();

			for (ServiceData service : services) {
				if (service.getServiceStatus() != null)
					approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
			}
			approveServicesScreen.clickSaveButton();
			approveInspectionsScreen = new VNextApproveInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			Assert.assertEquals(approveInspectionsScreen.getInspectionApprovedAmaunt(inspectionData.getInspectionNumber()), inspectionData.getInspectionApprovedPrice());
		}
		approveInspectionsScreen.clickSaveutton();
		ApproveValidations.verifyApprovePriceValue(approveTotal);
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();

		WaitUtils.elementShouldBeVisible(inspectionsScreen.getRootElement(), true);
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserNeedToSelectReasonForAllDeclinedInspection(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		HomeScreenSteps.openInspections();
		for (InspectionData inspectionData: inspectionsData) {
			VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
			inspectionsScreen.clickAddInspectionButton();
			InspectionSteps.createInspection(testcustomer,InspectionTypes.O_KRAMAR3, inspectionData);
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

			inspectionData.getServicesList().forEach(service -> availableServicesScreen.selectService(service.getServiceName()));

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : inspectionData.getServicesList())
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
		}
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			if (inspectionData.getInspectionStatus().equals(InspectionStatus.DECLINED)) {
				VNextDeclineReasonScreen declineReasonScreen = approveInspectionsScreen.clickInspectionDeclineButton(inspectionData.getInspectionNumber());
				declineReasonScreen.selectDeclineReason(inspectionData.getDeclineReason());
				approveInspectionsScreen = new VNextApproveInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

			}
		}
		approveInspectionsScreen.clickSaveutton();
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		for (InspectionData inspectionData: inspectionsData) {
			Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionData.getInspectionNumber()), inspectionData.getInspectionStatus().getStatus());
		}
		ScreenNavigationSteps.pressBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyInspectionStatusUpdatedAfterApproveDecline(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		HomeScreenSteps.openInspections();
		for (InspectionData inspectionData: inspectionsData) {
			VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
			inspectionsScreen.clickAddInspectionButton();
			InspectionSteps.createInspection(testcustomer,InspectionTypes.O_KRAMAR3, inspectionData);
			WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			inspectionData.getServicesList().forEach(service -> availableServicesScreen.selectService(service.getServiceName()));

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : inspectionData.getServicesList())
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionData.setInspectionNumber(InspectionSteps.saveInspection());
		}
		VNextInspectionsScreen inspectionsScreen = new VNextInspectionsScreen();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspectionData.getInspectionNumber());
			List<ServiceData> services = inspectionData.getServicesList();

			for (ServiceData service : services) {
				if (service.getServiceStatus() != null)
					approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
			}
			approveServicesScreen.clickSaveButton();
			if (inspectionData.getDeclineReason() != null) {
				VNextDeclineReasonScreen declineReasonScreen = new VNextDeclineReasonScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
				declineReasonScreen.selectDeclineReason(inspectionData.getDeclineReason());
			}
			approveInspectionsScreen = new VNextApproveInspectionsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
			if (!inspectionData.getInspectionStatus().equals(InspectionStatus.DECLINED))
				Assert.assertEquals(approveInspectionsScreen.getInspectionApprovedAmaunt(inspectionData.getInspectionNumber()), inspectionData.getInspectionApprovedPrice());
		}
		approveInspectionsScreen.clickSaveutton();
		ApproveSteps.drawSignature();
		ApproveSteps.saveApprove();
		ScreenNavigationSteps.pressBackButton();
	}

}

package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.baseutils.AppiumUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextTeamInspectionsLineApprovalTestCases extends BaseTestCaseTeamEditionRegistration {

	@BeforeClass(description = "Team Inspections Line Approval Test Cases")
	public void settingUp() {
		JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsLineApprovalTestCasesDataPath();
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
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
		 		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());
		
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertTrue(approveServicesScreen.isApproveAllButtonDisplayed());
		Assert.assertTrue(approveServicesScreen.isDeclineAllButtonDisplayed());
		Assert.assertTrue(approveServicesScreen.isSkipAllButtonDisplayed());
		for (ServiceData service : services) {
			Assert.assertTrue(approveServicesScreen.isServicePresentInTheList(service.getServiceName()));
			approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}
		approveServicesScreen.clickScreenBackButton();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveDeclineAndSkipDifferentServicesInOneInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());
		
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (ServiceData service : services) {
			approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}
		approveServicesScreen.clickSaveButton();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
				
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		Assert.assertEquals(inspectionsScreen.getInspectionApprovedPriceValue(inspectionNumber), inspectionData.getInspectionApprovedPrice());	
		
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeclineAllServiceIfLineApprovalEqualsON(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());
		
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveServicesScreen.clickDeclineAllButton();
		approveServicesScreen.clickSaveButton();
		VNextDeclineReasonScreen declineReasonsScreen = new VNextDeclineReasonScreen(DriverBuilder.getInstance().getAppiumDriver());
		declineReasonsScreen.selectDeclineReason(inspectionData.getDeclineReason());
		
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), InspectionStatuses.DECLINED.getInspectionStatusValue());
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantApproveOrDeclineServicesIfLineApprovalEqualsOFF(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.selectService(inspectionData.getServiceData().getServiceName());
		
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();		
		
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(approveScreen.getApprovePriceValue(), inspectionData.getInspectionApprovedPrice());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		Assert.assertEquals(inspectionsScreen.getInspectionApprovedPriceValue(inspectionNumber), inspectionData.getInspectionApprovedPrice());
		inspectionsScreen.clickBackButton();
	}
	
	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyStateChangedToNewIfUserEditInspection(String rowID,
            String description, JSONObject testData) {
		
		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
		 		
		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());
		
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();

		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickEditInspectionMenuItem();
		WaitUtils.elementShouldBeVisible(vehicleInfoScreen.getRootElement(), true);
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), InspectionStatuses.NEW.getInspectionStatusValue());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyInspectionHaveStateApproveIfUserSelectOnlyOneServiceAsApproved(String rowID,
																		 String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveServicesScreen.clickDeclineAllButton();
		for (ServiceData service : services)
			if (service.getServiceStatus() != null)
				approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		approveServicesScreen.clickSaveButton();

		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanGoBackFromApproveScreenUsingHardwareBackButton(String rowID,
                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
       VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        List<ServiceData> services = inspectionData.getServicesList();
        for (ServiceData service : services)
            availableServicesScreen.selectService(service.getServiceName());

        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickApproveInspectionMenuItem();
        VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        approveServicesScreen.clickApproveAllButton();
        approveServicesScreen.clickSaveButton();
        VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
        AppiumUtils.clickHardwareBackButton();
        approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        AppiumUtils.clickHardwareBackButton();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), InspectionStatuses.NEW.getInspectionStatusValue());
        inspectionsScreen.clickBackButton();
    }

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalAmountIncludeOnlyApprovedServices(String rowID,
																				String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

		List<ServiceData> services = inspectionData.getServicesList();
		for (ServiceData service : services)
			availableServicesScreen.selectService(service.getServiceName());

		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (ServiceData service : services) {
			approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
		}

		approveServicesScreen.clickSaveButton();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();

		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		Assert.assertEquals(inspectionsScreen.getInspectionApprovedPriceValue(inspectionNumber), inspectionData.getInspectionApprovedPrice());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserSeeOnlyTotalAmountForSelectedMatrixWhenApproveInspectionWithLineApprovalEqualsON(String rowID,
																						String description, JSONObject testData) {

		InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		inspectionsScreen.switchToMyInspectionsView();
		VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
		customersScreen.switchToRetailMode();
		customersScreen.selectCustomer(testcustomer);
		VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
		inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
		VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
		VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
		final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
		vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
		VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		availableServicesScreen.switchToAvalableServicesView();

		VNextPriceMatrixesScreen priceMatrixesScreen = availableServicesScreen.openMatrixServiceDetails(  inspectionData.getMatrixServiceData().getMatrixServiceName());
		//VNextVehiclePartInfoPage vehiclePartInfoScreen = priceMatrixesScreen.selectPriceMatrix1(inspectionData.getMatrixServiceData().getHailMatrixName());
		MatrixServiceData matrixServiceData = inspectionData.getMatrixServiceData();
		List<VehiclePartData> vehiclePartsData = matrixServiceData.getVehiclePartsData();

		for (VehiclePartData  vehiclePartData : vehiclePartsData) {
			VNextVehiclePartInfoPage vehiclePartInfoScreen = priceMatrixesScreen.selectPriceMatrix(vehiclePartData.getVehiclePartName());
			//VNextVehiclePartInfoPage vehiclePartInfoScreen = vehiclePartsScreen.selectVehiclePart(matrixPartData.getMatrixPartName());
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
			vehiclePartInfoScreen.clickSaveVehiclePartInfo(); }
		VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());

		availableServicesScreen = vehiclePartsScreen.clickVehiclePartsSaveButton();

		Assert.assertEquals(availableServicesScreen.getTotalPriceValue(), inspectionData.getInspectionPrice());
		inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());
		VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
		inspectionsMenuScreen.clickApproveInspectionMenuItem();
		VNextApproveServicesScreen approveServicesScreen = new VNextApproveServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveServicesScreen.isServicePresentInTheList(inspectionData.getMatrixServiceData().getMatrixServiceName());
		Assert.assertEquals(approveServicesScreen.getServicePriceValue(inspectionData.getMatrixServiceData().getMatrixServiceName()),
				inspectionData.getInspectionPrice());
		approveServicesScreen.clickApproveAllButton();
		approveServicesScreen.clickSaveButton();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();

		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionNumber), InspectionStatuses.APPROVED.getInspectionStatusValue());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanApproveSeveralInspectionsWithOneCustomersWithLineApproveAndWithoutLineApprove(String rowID,
																 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		for (InspectionData inspectionData: inspectionsData) {
			VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(testcustomer);
			VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
			inspectionTypesList.selectInspectionType(InspectionTypes.valueOf(inspectionData.getInspectionType()));
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
			inspectionData.setInspectionNumber(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services)
				availableServicesScreen.selectService(service.getServiceName());
			inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		}

		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData)
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isApproveIconForInspectionSelected(inspectionsData.get(0).getInspectionNumber()));
		Assert.assertFalse(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspectionsData.get(0).getInspectionNumber()));
		Assert.assertFalse(approveInspectionsScreen.isApproveIconForInspectionSelected(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionServicesStatusesCanBeChanged(inspectionsData.get(1).getInspectionNumber()));
		inspectionsScreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.unselectInspection(inspectionData.getInspectionNumber());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanSelectServiceForApproveOnMultiLineApproveScreenIfLineApprovalEqualsONForThisInspection(String rowID,
																											   String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		for (InspectionData inspectionData: inspectionsData) {
			VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(testcustomer);
			VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
			inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
			inspectionData.setInspectionNumber(vehicleInfoScreen.getNewInspectionNumber());
			//inspNumbers.add(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services)
				availableServicesScreen.selectService(service.getServiceName());

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		}

		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspectionData.getInspectionNumber());
			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services)
				Assert.assertTrue(approveServicesScreen.isServicePresentInTheList(service.getServiceName()));
			approveInspectionsScreen = approveServicesScreen.clickBackButton();
		}
		inspectionsScreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.unselectInspection(inspectionData.getInspectionNumber());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCanDeleteInspectionFromLineApproveList(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		for (InspectionData inspectionData: inspectionsData) {
			VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(testcustomer);
			VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
			inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
			inspectionData.setInspectionNumber(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services)
				availableServicesScreen.selectService(service.getServiceName());

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		}

		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();
		approveInspectionsScreen.clickInspectionDeleteButton(inspectionsData.get(1).getInspectionNumber());
		Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(0).getInspectionNumber()));
		approveInspectionsScreen.clickInspectionDeleteButton(inspectionsData.get(0).getInspectionNumber());
		Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(1).getInspectionNumber()));
		Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionsData.get(0).getInspectionNumber()));

		inspectionsScreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.unselectInspection(inspectionData.getInspectionNumber());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyTotalApprovalAmountIncludeOnlySUMOfApprovedService(String rowID,
																														String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		for (InspectionData inspectionData: inspectionsData) {
			VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(testcustomer);
			VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
			inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
			inspectionData.setInspectionNumber(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services) {
				availableServicesScreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		}

		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspectionData.getInspectionNumber());
			List<ServiceData> services = inspectionData.getServicesList();

			for (ServiceData service : services) {
				approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
			}
			approveServicesScreen.clickSaveButton();
			approveInspectionsScreen = new VNextApproveInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
			Assert.assertEquals(approveInspectionsScreen.getInspectionApprovedAmaunt(inspectionData.getInspectionNumber()), inspectionData.getInspectionApprovedPrice());
		}
		approveInspectionsScreen.clickSaveutton();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();

		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserNeedToSelectReasonForAllDeclinedInspection(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		for (InspectionData inspectionData: inspectionsData) {
			VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(testcustomer);
			VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
			inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
			inspectionData.setInspectionNumber(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services) {
				availableServicesScreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		}

		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			if (inspectionData.getInspectionStatus().equals(InspectionStatus.DECLINED)) {
				VNextDeclineReasonScreen declineReasonScreen = approveInspectionsScreen.clickInspectionDeclineButton(inspectionData.getInspectionNumber());
				declineReasonScreen.selectDeclineReason(inspectionData.getDeclineReason());
				approveInspectionsScreen = new VNextApproveInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());

			}
		}
		approveInspectionsScreen.clickSaveutton();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();
		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		for (InspectionData inspectionData: inspectionsData) {
			Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspectionData.getInspectionNumber()), inspectionData.getInspectionStatus().getStatus());
		}
		inspectionsScreen.clickBackButton();
	}

	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyInspectionStatusUpdatedAfterApproveDecline(String rowID,
																			 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		for (InspectionData inspectionData: inspectionsData) {
			VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(testcustomer);
			VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
			inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
			inspectionData.setInspectionNumber(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services) {
				availableServicesScreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		}

		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.clickMultiselectInspectionsApproveButton();

		for (InspectionData inspectionData: inspectionsData) {
			VNextApproveServicesScreen approveServicesScreen = approveInspectionsScreen.openApproveServicesScreenForInspection(inspectionData.getInspectionNumber());
			List<ServiceData> services = inspectionData.getServicesList();

			for (ServiceData service : services) {
				approveServicesScreen.setServiceStatus(service.getServiceName(), service.getServiceStatus());
			}
			approveServicesScreen.clickSaveButton();
			if (inspectionData.getDeclineReason() != null) {
				VNextDeclineReasonScreen declineReasonScreen = new VNextDeclineReasonScreen(DriverBuilder.getInstance().getAppiumDriver());
				declineReasonScreen.selectDeclineReason(inspectionData.getDeclineReason());
			}
			approveInspectionsScreen = new VNextApproveInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
			if (!inspectionData.getInspectionStatus().equals(InspectionStatus.DECLINED))
				Assert.assertEquals(approveInspectionsScreen.getInspectionApprovedAmaunt(inspectionData.getInspectionNumber()), inspectionData.getInspectionApprovedPrice());
		}
		approveInspectionsScreen.clickSaveutton();
		VNextApproveScreen approveScreen = new VNextApproveScreen(DriverBuilder.getInstance().getAppiumDriver());
		approveScreen.drawSignature();
		approveScreen.saveApprovedInspection();

		inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
		inspectionsScreen.clickBackButton();
	}



	@Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
	public void testVerifyUserCantApproveInspectionsWithDifferentCustomers(String rowID,
																	 String description, JSONObject testData) {

		TestCaseData testCaseData = JSonDataParser.getTestDataFromJson(testData, TestCaseData.class);
		List<InspectionData> inspectionsData = testCaseData.getInspectionsData();

		VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
		VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
		for (InspectionData inspectionData: inspectionsData) {
			VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
			customersScreen.switchToRetailMode();
			customersScreen.selectCustomer(inspectionData.getInspectionRetailCustomer());
			VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
			inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
			VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
			VehicleInfoScreenInteractions.setDataFiled(VehicleDataField.VIN,inspectionData.getVinNumber());
			inspectionData.setInspectionNumber(vehicleInfoScreen.getNewInspectionNumber());
			vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
			VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

			List<ServiceData> services = inspectionData.getServicesList();
			for (ServiceData service : services) {
				availableServicesScreen.selectService(service.getServiceName());
			}

			VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
			for (ServiceData service : services)
				if (service.getServicePrice() != null)
					selectedServicesScreen.setServiceAmountValue(service.getServiceName(), service.getServicePrice());
			inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
		}

		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.selectInspection(inspectionData.getInspectionNumber());
		VNextApproveInspectionsScreen approveInspectionsScreen = inspectionsScreen.
				clickMultiselectInspectionsApproveButtonAndSelectCustomer(inspectionsData.get(1).getInspectionRetailCustomer());
		for (InspectionData inspectionData: inspectionsData) {
			if (inspectionData.getInspectionRetailCustomer().getLastName().
					equals(inspectionsData.get(1).getInspectionRetailCustomer().getLastName()))
				Assert.assertTrue(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
			else
				Assert.assertFalse(approveInspectionsScreen.isInspectionExistsForApprove(inspectionData.getInspectionNumber()));
		}
		inspectionsScreen = approveInspectionsScreen.clickBackButton();
		for (InspectionData inspectionData: inspectionsData)
			inspectionsScreen.unselectInspection(inspectionData.getInspectionNumber());
		inspectionsScreen.clickBackButton();
	}


}

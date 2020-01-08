package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.VehiclePartData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextServiceDetailsScreen;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartInfoPage;
import com.cyberiansoft.test.vnext.screens.VNextVehiclePartsScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePanelsList;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePartsList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextLaborServiceTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Labor Service Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getLaborServiceTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSelectPanelAndPartsForLaborService(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.
                openLaborServiceDetails(inspectionData.getLaborServiceData().getServiceName());
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickSelectPanelsAndParts();
        VNextLaborServicePanelsList laborServicePanelsList = new VNextLaborServicePanelsList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(inspectionData.getLaborServiceData().getLaborServicePanel());
        laborServicePartsList.selectServiceLaborPart(inspectionData.getLaborServiceData().getLaborServicePart());
        laborServicePartsList.saveLaborServiceParts();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(inspectionData.getLaborServiceData().getServiceName()),
                inspectionData.getLaborServiceData().getLaborServicePrice());
        inspectionScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCantSelectPartWithoutPanel(String rowID,
                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.
                selectLaborService(inspectionData.getLaborServiceData().getServiceName());
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickSelectPanelsAndParts();
        VNextLaborServicePanelsList laborServicePanelsList = new VNextLaborServicePanelsList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertFalse(laborServicePanelsList.isPartsTabEnabled());
        laborServicePanelsList.clickBackButton();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        inspectionScreen = availableServicesScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserDoesntNeedToSelectPanelWhenAddLaborServiceInMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.switchToAvalableServicesView();

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        VehiclePartData vehiclePartData = inspectionData.getMatrixServiceData().getVehiclePartData();
        VNextVehiclePartsScreen priceMatrixesScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextVehiclePartInfoPage vehiclePartInfoScreen = priceMatrixesScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());

        vehiclePartInfoScreen.openVehiclePartLaborServiceDetails(vehiclePartData.getMatrixAdditionalLaborService().getServiceName());
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickSelectPanelsAndParts();
        VNextLaborServicePartsList laborServicePartsList = new VNextLaborServicePartsList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.clickBackButton();
        vehiclePartInfoScreen.clickScreenBackButton();
        serviceDetailsScreen.clickScreenBackButton();
        vehiclePartInfoScreen = new VNextVehiclePartInfoPage(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();

        inspectionScreen = availableServicesScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSavePartServiceInMatrix(String rowID,
                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.switchToAvalableServicesView();

        AvailableServicesScreenSteps.selectMatrixService(inspectionData.getMatrixServiceData());
        VNextVehiclePartsScreen priceMatrixesScreen = new VNextVehiclePartsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VehiclePartData vehiclePartData = inspectionData.getMatrixServiceData().getVehiclePartData();
        VNextVehiclePartInfoPage vehiclePartInfoScreen = priceMatrixesScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());

        vehiclePartInfoScreen.openVehiclePartLaborServiceDetails(vehiclePartData.getMatrixAdditionalLaborService().getServiceName());
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickSelectPanelsAndParts();
        VNextLaborServicePartsList laborServicePartsList = new VNextLaborServicePartsList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.selectServiceLaborPart(vehiclePartData.getMatrixAdditionalLaborService().getLaborServicePart());
        laborServicePartsList.saveLaborServiceParts();
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        vehiclePartInfoScreen.switchToSelectedServicesView();
        vehiclePartInfoScreen.expandSelectedServiceDetails(vehiclePartData.getMatrixAdditionalLaborService().getServiceName());
        Assert.assertEquals(serviceDetailsScreen.getServiceAmountValue(), vehiclePartData.getMatrixAdditionalLaborService().getLaborServiceRate());
        Assert.assertEquals(serviceDetailsScreen.getServiceQuantityValue(),
                vehiclePartData.getMatrixAdditionalLaborService().getLaborServiceTime());
        Assert.assertEquals(serviceDetailsScreen.getServiceNotesValue(),
                vehiclePartData.getMatrixAdditionalLaborService().getLaborServiceNotes());
        serviceDetailsScreen.clickServiceDetailsDoneButton();
        Assert.assertEquals(vehiclePartInfoScreen.getMatrixServiceTotalPriceValue(),
                inspectionData.getInspectionPrice());
        vehiclePartInfoScreen.clickScreenBackButton();
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();

        inspectionScreen = availableServicesScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCancelAddPanelAndParts(String rowID,
                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        availableServicesScreen.
                selectLaborService(inspectionData.getLaborServiceData().getServiceName());
        VNextServiceDetailsScreen serviceDetailsScreen = new VNextServiceDetailsScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        serviceDetailsScreen.clickSelectPanelsAndParts();
        VNextLaborServicePanelsList laborServicePanelsList = new VNextLaborServicePanelsList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(inspectionData.getLaborServiceData().getLaborServicePanel());
        laborServicePartsList.clickBackButton();
        laborServicePanelsList = new VNextLaborServicePanelsList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        laborServicePanelsList.clickBackButton();
        serviceDetailsScreen.clickScreenBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        inspectionScreen = availableServicesScreen.cancelInspection();
        inspectionScreen.clickBackButton();
    }
}
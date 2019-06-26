package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePanelsList;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePartsList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextSelectedServicesScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextLaborServiceTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description = "Team Labor Service Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getLaborServiceTestCasesDataPath();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSelectPanelAndPartsForLaborService(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.
                openLaborServiceDetails(inspectionData.getLaborServiceData().getServiceName());
        VNextLaborServicePanelsList laborServicePanelsList = new VNextLaborServicePanelsList(DriverBuilder.getInstance().getAppiumDriver());
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(inspectionData.getLaborServiceData().getLaborServicePanel());
        laborServicePartsList.selectServiceLaborPart(inspectionData.getLaborServiceData().getLaborServicePart());
        laborServicePartsList.saveLaborServiceParts();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableServicesScreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(inspectionData.getLaborServiceData().getServiceName()),
                inspectionData.getLaborServiceData().getLaborServicePrice());
        inspectionScreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSelectPartWithoutPanel(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.
                selectLaborService(inspectionData.getLaborServiceData().getServiceName());
        VNextLaborServicePanelsList laborServicePanelsList = new VNextLaborServicePanelsList(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertFalse(laborServicePanelsList.isPartsTabEnabled());
        laborServicePanelsList.clickBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        inspectionScreen = availableServicesScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();
    }

   @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserDoesntNeedToSelectPanelWhenAddLaborServiceInMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());

        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.switchToAvalableServicesView();

        VNextVehiclePartsScreen priceMatrixesScreen = availableServicesScreen.openSelectedMatrixServiceDetails(  inspectionData.getMatrixServiceData().getMatrixServiceName());
        //VNextVehiclePartsScreen vehiclePartsScreen = priceMatrixesScreen.selectHailMatrix(inspectionData.getMatrixServiceData().getHailMatrixName());

        VehiclePartData vehiclePartData = inspectionData.getMatrixServiceData().getVehiclePartData();
        VNextVehiclePartInfoPage vehiclePartInfoScreen = priceMatrixesScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());

        vehiclePartInfoScreen.openVehiclePartLaborServiceDetails(vehiclePartData.getMatrixAdditionalLaborService().getServiceName());
       VNextLaborServicePartsList laborServicePartsList = new VNextLaborServicePartsList(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.clickBackButton();
       VNextLaborServiceDetailsScreen laborServiceDetailsScreen = new VNextLaborServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());

        laborServiceDetailsScreen.saveLaborServiceDetails();
        vehiclePartInfoScreen = new VNextVehiclePartInfoPage(DriverBuilder.getInstance().getAppiumDriver());
        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehiclePartsScreen.clickVehiclePartsSaveButton();
        inspectionScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSavePartServiceInMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.switchToAvalableServicesView();

        VNextVehiclePartsScreen priceMatrixesScreen = availableServicesScreen.openSelectedMatrixServiceDetails(  inspectionData.getMatrixServiceData().getMatrixServiceName());

        VehiclePartData vehiclePartData = inspectionData.getMatrixServiceData().getVehiclePartData();
        VNextVehiclePartInfoPage vehiclePartInfoScreen = priceMatrixesScreen.selectVehiclePart(vehiclePartData.getVehiclePartName());
        vehiclePartInfoScreen.selectVehiclePartSize(vehiclePartData.getVehiclePartSize());
        vehiclePartInfoScreen.selectVehiclePartSeverity(vehiclePartData.getVehiclePartSeverity());

        vehiclePartInfoScreen.openVehiclePartLaborServiceDetails(vehiclePartData.getMatrixAdditionalLaborService().getServiceName());
        VNextLaborServicePartsList laborServicePartsList = new VNextLaborServicePartsList(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.selectServiceLaborPart(vehiclePartData.getMatrixAdditionalLaborService().getLaborServicePart());
        laborServicePartsList.saveLaborServiceParts();
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = new VNextLaborServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(laborServiceDetailsScreen.getLaborServiceRate(vehiclePartData.getMatrixAdditionalLaborService()),
                vehiclePartData.getMatrixAdditionalLaborService().getLaborServiceRate());
        Assert.assertEquals(laborServiceDetailsScreen.getLaborServiceTime(vehiclePartData.getMatrixAdditionalLaborService()),
                vehiclePartData.getMatrixAdditionalLaborService().getLaborServiceTime());
        Assert.assertEquals(laborServiceDetailsScreen.getLaborServiceNotes(vehiclePartData.getMatrixAdditionalLaborService()),
                vehiclePartData.getMatrixAdditionalLaborService().getLaborServiceNotes());
        laborServiceDetailsScreen.saveLaborServiceDetails();
        Assert.assertEquals(vehiclePartInfoScreen.getMatrixServiceTotalPriceValue(),
                inspectionData.getInspectionPrice());

        vehiclePartInfoScreen.clickSaveVehiclePartInfo();
        VNextVehiclePartsScreen vehiclePartsScreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehiclePartsScreen.clickVehiclePartsSaveButton();

        inspectionScreen = availableServicesScreen.saveInspectionViaMenu();
        inspectionScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelAddPanelAndParts(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
 
        vehicleInfoScreen.changeScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableServicesScreen.
                selectLaborService(inspectionData.getLaborServiceData().getServiceName());
        VNextLaborServicePanelsList laborServicePanelsList = new VNextLaborServicePanelsList(DriverBuilder.getInstance().getAppiumDriver());
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(inspectionData.getLaborServiceData().getLaborServicePanel());
        laborServicePartsList.clickBackButton();
        laborServicePanelsList = new VNextLaborServicePanelsList(DriverBuilder.getInstance().getAppiumDriver());
        laborServicePanelsList.clickBackButton();
        availableServicesScreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionScreen = availableServicesScreen.cancelInspection();
        inspectionScreen.clickBackButton();
    }
}
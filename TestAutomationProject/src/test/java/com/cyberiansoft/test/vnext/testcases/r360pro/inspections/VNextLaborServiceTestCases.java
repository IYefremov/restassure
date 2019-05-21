package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
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

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = availableservicesscreen.
                openLaborServiceDetailsScreen(inspdata.getLaborServiceData().getServiceName());
        laborServiceDetailsScreen.selectPanelAndPart(inspdata.getLaborServiceData().getLaborServicePanel(),
                inspdata.getLaborServiceData().getLaborServicePart());
        laborServiceDetailsScreen.saveLaborServiceDetails();
        availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextSelectedServicesScreen selectedServicesScreen = availableservicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(inspdata.getLaborServiceData().getServiceName()),
                inspdata.getLaborServiceData().getLaborServicePrice());
        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSelectPartWithoutPanel(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = availableservicesscreen.
                selectLaborService(inspdata.getLaborServiceData().getServiceName());
        VNextLaborServicePanelsList laborServicePanelsList = laborServiceDetailsScreen.clickSelectPanelCell();
        Assert.assertFalse(laborServicePanelsList.isPartsTabEnabled());
        laborServicePanelsList.clickBackButton();
        laborServiceDetailsScreen = new VNextLaborServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());
                laborServiceDetailsScreen.clickScreenBackButton();
        availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());

        inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserDoesntNeedToSelectPanelWhenAddLaborServiceInMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());

        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableservicesscreen.switchToAvalableServicesView();

        VNextVehiclePartsScreen pricematrixesscreen = availableservicesscreen.openSelectedMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
        //VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(inspdata.getMatrixServiceData().getHailMatrixName());

        MatrixPartData matrixPartData = inspdata.getMatrixServiceData().getMatrixPartData();
        VNextVehiclePartInfoPage vehiclepartinfoscreen = pricematrixesscreen.selectVehiclePart(matrixPartData.getMatrixPartName());
        vehiclepartinfoscreen.selectVehiclePartSize(matrixPartData.getPartSize());
        vehiclepartinfoscreen.selectVehiclePartSeverity(matrixPartData.getPartSeverity());

        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = vehiclepartinfoscreen.openVehiclePartLaborServiceDetails(matrixPartData.getMatrixAdditionalLaborService().getServiceName());
        VNextLaborServicePartsList laborServicePartsList = laborServiceDetailsScreen.clickSelectPanelsAndPartsForLaborService(matrixPartData.getMatrixAdditionalLaborService());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.clickBackButton();
        laborServiceDetailsScreen = new VNextLaborServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());

        laborServiceDetailsScreen.saveLaborServiceDetails();
        vehiclepartinfoscreen = new VNextVehiclePartInfoPage(DriverBuilder.getInstance().getAppiumDriver());
        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        VNextVehiclePartsScreen vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehiclepartsscreen.clickVehiclePartsSaveButton();
        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSavePartServiceInMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableservicesscreen.switchToAvalableServicesView();

        VNextVehiclePartsScreen pricematrixesscreen = availableservicesscreen.openSelectedMatrixServiceDetails(  inspdata.getMatrixServiceData().getMatrixServiceName());
        //VNextVehiclePartsScreen vehiclepartsscreen = pricematrixesscreen.selectHailMatrix(inspdata.getMatrixServiceData().getHailMatrixName());

        MatrixPartData matrixPartData = inspdata.getMatrixServiceData().getMatrixPartData();
        VNextVehiclePartInfoPage vehiclepartinfoscreen = pricematrixesscreen.selectVehiclePart(matrixPartData.getMatrixPartName());
        vehiclepartinfoscreen.selectVehiclePartSize(matrixPartData.getPartSize());
        vehiclepartinfoscreen.selectVehiclePartSeverity(matrixPartData.getPartSeverity());

        VNextLaborServiceDetailsScreen laborServiceDetailsScreen =vehiclepartinfoscreen.openVehiclePartLaborServiceDetails(matrixPartData.getMatrixAdditionalLaborService().getServiceName());
        VNextLaborServicePartsList laborServicePartsList = laborServiceDetailsScreen.clickSelectPanelsAndPartsForLaborService(matrixPartData.getMatrixAdditionalLaborService());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.selectServiceLaborPart(matrixPartData.getMatrixAdditionalLaborService().getLaborServicePart());
        laborServiceDetailsScreen = laborServicePartsList.saveLaborServiceParts();
                Assert.assertEquals(laborServiceDetailsScreen.getLaborServiceRate(matrixPartData.getMatrixAdditionalLaborService()),
                matrixPartData.getMatrixAdditionalLaborService().getLaborServiceRate());
        Assert.assertEquals(laborServiceDetailsScreen.getLaborServiceTime(matrixPartData.getMatrixAdditionalLaborService()),
                matrixPartData.getMatrixAdditionalLaborService().getLaborServiceTime());
        Assert.assertEquals(laborServiceDetailsScreen.getLaborServiceNotes(matrixPartData.getMatrixAdditionalLaborService()),
                matrixPartData.getMatrixAdditionalLaborService().getLaborServiceNotes());
        laborServiceDetailsScreen.saveLaborServiceDetails();
        Assert.assertEquals(vehiclepartinfoscreen.getMatrixServiceTotalPriceValue(),
                inspdata.getInspectionPrice());

        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        VNextVehiclePartsScreen vehiclepartsscreen = new VNextVehiclePartsScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehiclepartsscreen.clickVehiclePartsSaveButton();

        inspectionscreen = availableservicesscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelAddPanelAndParts(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        insptypeslist.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = availableservicesscreen.
                selectLaborService(inspdata.getLaborServiceData().getServiceName());
        VNextLaborServicePanelsList laborServicePanelsList = laborServiceDetailsScreen.clickSelectPanelCell();
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(inspdata.getLaborServiceData().getLaborServicePanel());
        laborServicePartsList.clickBackButton();
        laborServicePanelsList = new VNextLaborServicePanelsList(DriverBuilder.getInstance().getAppiumDriver());
        laborServicePanelsList.clickBackButton();
        laborServiceDetailsScreen = new VNextLaborServiceDetailsScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableservicesscreen = laborServiceDetailsScreen.clickBackButton();
        inspectionscreen = availableservicesscreen.cancelInspection();
        inspectionscreen.clickBackButton();
    }
}
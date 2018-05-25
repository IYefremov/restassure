package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.*;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.screens.*;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextBasePanelPartsList;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePanelsList;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePartsList;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class VNextLaborServiceTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-labor-service-testcases-data.json";

    @BeforeClass(description = "Team Inspections Line Approval Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSelectPanelAndPartsForLaborService(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = inpsctionservicesscreen.
                selectLaborService(inspdata.getLaborServiceData().getServiceName());
        laborServiceDetailsScreen.selectPanelAndPart(inspdata.getLaborServiceData().getLaborServicePanel(),
                inspdata.getLaborServiceData().getLaborServicePart());
        inpsctionservicesscreen = laborServiceDetailsScreen.saveLaborServiceDetails();
        VNextSelectedServicesScreen selectedServicesScreen = inpsctionservicesscreen.switchToSelectedServicesView();
        Assert.assertEquals(selectedServicesScreen.getSelectedServicePriceValue(inspdata.getLaborServiceData().getServiceName()),
                inspdata.getLaborServiceData().getLaborServicePrice());
        inspectionscreen = selectedServicesScreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();

    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSelectPartWithoutPanel(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = inpsctionservicesscreen.
                selectLaborService(inspdata.getLaborServiceData().getServiceName());
        VNextLaborServicePanelsList laborServicePanelsList = laborServiceDetailsScreen.clickSelectPanelCell();
        Assert.assertFalse(laborServicePanelsList.isPartsTabEnabled());
        laborServicePanelsList.clickBackButton();
        laborServiceDetailsScreen = new VNextLaborServiceDetailsScreen(appiumdriver);
                laborServiceDetailsScreen.clickScreenBackButton();
        inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);

        inspectionscreen = inpsctionservicesscreen.saveInspectionViaMenu();
        inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserDoesntNeedToSelectPanelWhenAddLaborServiceInMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.switchToAvalableServicesView();

        VNextVehiclePartsScreen vehiclepartsscreen = inpsctionservicesscreen.openSelectedMatrixServiceDetails( inspdata.getMatrixServiceData().getMatrixServiceName());
        MatrixPartData matrixPartData = inspdata.getMatrixServiceData().getMatrixPartData();
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(matrixPartData.getMatrixPartName());
        vehiclepartinfoscreen.selectVehiclePartSize(matrixPartData.getPartSize());
        vehiclepartinfoscreen.selectVehiclePartSeverity(matrixPartData.getPartSeverity());

        vehiclepartinfoscreen.selectVehiclePartAdditionalService(matrixPartData.getMatrixAdditionalLaborService().getServiceName());
        VNextLaborServicePartsList laborServicePartsList = vehiclepartinfoscreen.clickSelectPanelsAndPartsForLaborService(matrixPartData.getMatrixAdditionalLaborService());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.clickBackButton();
        vehiclepartinfoscreen = new VNextVehiclePartInfoPage(appiumdriver);

        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
        inpsctionservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        homescreen = inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSavePartServiceInMatrix(String rowID,
                                                                                 String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        inpsctionservicesscreen.switchToAvalableServicesView();

        VNextVehiclePartsScreen vehiclepartsscreen = inpsctionservicesscreen.openSelectedMatrixServiceDetails( inspdata.getMatrixServiceData().getMatrixServiceName());
        MatrixPartData matrixPartData = inspdata.getMatrixServiceData().getMatrixPartData();
        VNextVehiclePartInfoPage vehiclepartinfoscreen = vehiclepartsscreen.selectVehiclePart(matrixPartData.getMatrixPartName());
        vehiclepartinfoscreen.selectVehiclePartSize(matrixPartData.getPartSize());
        vehiclepartinfoscreen.selectVehiclePartSeverity(matrixPartData.getPartSeverity());

        vehiclepartinfoscreen.selectVehiclePartAdditionalService(matrixPartData.getMatrixAdditionalLaborService().getServiceName());
        VNextLaborServicePartsList laborServicePartsList = vehiclepartinfoscreen.clickSelectPanelsAndPartsForLaborService(matrixPartData.getMatrixAdditionalLaborService());
        Assert.assertTrue(laborServicePartsList.isPartsTabEnabled());
        laborServicePartsList.selectServiceLaborPart(matrixPartData.getMatrixAdditionalLaborService().getLaborServicePart());
        vehiclepartinfoscreen = new VNextVehiclePartInfoPage(appiumdriver);
        Assert.assertEquals(vehiclepartinfoscreen.getLaborServiceRate(matrixPartData.getMatrixAdditionalLaborService()),
                matrixPartData.getMatrixAdditionalLaborService().getLaborServiceRate());
        Assert.assertEquals(vehiclepartinfoscreen.getLaborServiceTime(matrixPartData.getMatrixAdditionalLaborService()),
                matrixPartData.getMatrixAdditionalLaborService().getLaborServiceTime());
        Assert.assertEquals(vehiclepartinfoscreen.getLaborServiceNotes(matrixPartData.getMatrixAdditionalLaborService()),
                matrixPartData.getMatrixAdditionalLaborService().getLaborServiceNotes());
        Assert.assertEquals(vehiclepartinfoscreen.getMatrixServiceTotalPriceValue(),
                inspdata.getInspectionPrice());

        vehiclepartinfoscreen.clickSaveVehiclePartInfo();
        vehiclepartsscreen = new VNextVehiclePartsScreen(appiumdriver);
        inpsctionservicesscreen = vehiclepartsscreen.clickVehiclePartsSaveButton();

        inspectionscreen = vehicleinfoscreen.saveInspectionViaMenu();
        homescreen = inspectionscreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelAddPanelAndParts(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspdata = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionscreen = homescreen.clickInspectionsMenuItem();
        inspectionscreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionscreen.clickAddInspectionButton();
        customersscreen.switchToRetailMode();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypeslist = new VNextInspectionTypesList(appiumdriver);
        insptypeslist.selectInspectionType(inspdata.getInspectionType());
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspdata.getVinNumber());
        final String inspnumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.swipeScreensLeft(2);
        VNextInspectionServicesScreen inpsctionservicesscreen = new VNextInspectionServicesScreen(appiumdriver);
        VNextLaborServiceDetailsScreen laborServiceDetailsScreen = inpsctionservicesscreen.
                selectLaborService(inspdata.getLaborServiceData().getServiceName());
        VNextLaborServicePanelsList laborServicePanelsList = laborServiceDetailsScreen.clickSelectPanelCell();
        VNextLaborServicePartsList laborServicePartsList = laborServicePanelsList.selectServiceLaborPanel(inspdata.getLaborServiceData().getLaborServicePanel());
        laborServicePartsList.clickBackButton();
        laborServicePanelsList = new VNextLaborServicePanelsList(appiumdriver);
        laborServicePanelsList.clickBackButton();
        laborServiceDetailsScreen = new VNextLaborServiceDetailsScreen(appiumdriver);
        inpsctionservicesscreen = laborServiceDetailsScreen.clickBackButton();
        inspectionscreen = inpsctionservicesscreen.cancelInspection();
        inspectionscreen.clickBackButton();
    }
}
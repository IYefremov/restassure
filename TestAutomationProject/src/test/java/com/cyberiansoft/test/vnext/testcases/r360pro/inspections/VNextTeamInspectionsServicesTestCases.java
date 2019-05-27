package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.DamageData;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextSelectDamagesScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVisualScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInspectionsServicesTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description="Team Inspection Notest Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsServicesTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testAddSeveralServicesFromAllListOnVisualsScreen(String rowID,
                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionScreen = homeScreen.clickInspectionsMenuItem();
        inspectionScreen.switchToTeamInspectionsView();
        VNextCustomersScreen customersScreen = inspectionScreen.clickAddInspectionButton();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR2);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
        VNextVisualScreen visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        for (DamageData damageData : inspectionData.getDamagesData()) {
            VNextSelectDamagesScreen selectDamagesScreen = visualScreen.clickAddServiceButton();
            selectDamagesScreen.clickDefaultDamageType(damageData.getDamageGroupName());
            visualScreen.clickCarImage();
        }
        Assert.assertEquals(visualScreen.getNumberOfImageMarkers(), inspectionData.getDamagesData().size());

        visualScreen.saveInspectionViaMenu();
        inspectionScreen.clickOpenInspectionToEdit(inspectionNumber);
        vehicleInfoScreen.changeScreen(ScreenType.VISUAL);
        visualScreen = new VNextVisualScreen(DriverBuilder.getInstance().getAppiumDriver());
        Assert.assertEquals(visualScreen.getNumberOfImageMarkers(), inspectionData.getDamagesData().size());
        visualScreen.cancelInspection();
    }
}

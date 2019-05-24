package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextClaimInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamVehicleInfoTestCases extends BaseTestCaseTeamEditionRegistration {

    @BeforeClass(description="Team Vehicle Info Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getVehicleInfoTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyLettersIOQAreTrimmedWhileManualEntry(String rowID,
                                                                        String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String redRGBColor = "rgba(239, 83, 78, 1)";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getVinNumber());
        Assert.assertTrue(vehicleInfoScreen.isVINValidationMessageExists());
        Assert.assertEquals(vehicleInfoScreen.getVINValidationMessageBackgroundColor(), redRGBColor);
        vehicleInfoScreen.setVIN(inspectionData.getNewVinNumber());
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyVINWithMoreThan17ValidDigitsIsTrimmedToFirst17CharactersWhileManualEntry(String rowID,
                                                               String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);
        final String greyRGBColor = "rgba(211, 211, 211, 1)";

        VNextHomeScreen homeScreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersScreen = inspectionsScreen.clickAddInspectionButton();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        vehicleInfoScreen.setVIN(inspectionData.getNewVinNumber());
        Assert.assertTrue(vehicleInfoScreen.isVINValidationMessageExists());
        Assert.assertEquals(vehicleInfoScreen.getVINValidationMessageBackgroundColor(), greyRGBColor);
        Assert.assertEquals(vehicleInfoScreen.getVINFieldValue(),inspectionData.getVinNumber().substring(0, inspectionData.getVinNumber().length()-1));
        vehicleInfoScreen.changeScreen(ScreenType.CLAIM);
        VNextClaimInfoScreen claimInfoScreen = new VNextClaimInfoScreen(DriverBuilder.getInstance().getAppiumDriver());
        claimInfoScreen.cancelInspection();
        inspectionsScreen.clickBackButton();
    }

}

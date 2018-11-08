package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.InspectionStatuses;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamDraftInspectionsTestCases extends BaseTestCaseTeamEditionRegistration {

    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-draft-inspections-testcases-data.json";

    @BeforeClass(description="Team Draft Inspections Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = DATA_FILE;
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSaveDraftInspectionWithoutPopulateRequiredFields(String rowID,
                                                                          String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypes = new VNextInspectionTypesList(appiumdriver);
        insptypes.selectInspectionType(InspectionTypes.O_KRAMAR3);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen availableServicesScreen = new VNextAvailableServicesScreen(appiumdriver);
        availableServicesScreen.selectService(inspectionData.getServiceName());


        inspectionsScreen = availableServicesScreen.saveInspectionAsDraft();
        Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspNumber),
                InspectionStatuses.DRAFT.getInspectionStatusValue());

        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantSaveInspectionIfDraftModeEqualsOFF(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer);
        VNextInspectionTypesList insptypes = new VNextInspectionTypesList(appiumdriver);
        insptypes.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        final String inspNumber = vehicleinfoscreen.getNewInspectionNumber();
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        vehicleinfoscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionStatusValue(inspNumber),
                InspectionStatuses.NEW.getInspectionStatusValue());
        inspectionsScreen.clickBackButton();
    }
}

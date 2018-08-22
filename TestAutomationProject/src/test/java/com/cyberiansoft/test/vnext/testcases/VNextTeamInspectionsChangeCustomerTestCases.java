package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.screens.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInspectionsChangeCustomerTestCases extends BaseTestCaseTeamEditionRegistration {
    private static final String DATA_FILE = "src/test/java/com/cyberiansoft/test/vnext/data/team-inspections-change-customer-testcases-data.json";

    final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Work Orders Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = DATA_FILE;
        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        customersscreen.switchToRetailMode();
        if (!customersscreen.isCustomerExists(testcustomer1)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer1);
            customersscreen = new VNextCustomersScreen(appiumdriver);
        }
        if (!customersscreen.isCustomerExists(testcustomer2)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer2);
            customersscreen = new VNextCustomersScreen(appiumdriver);
        }
        customersscreen.clickBackButton();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerForInspection(String rowID,
                                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(appiumdriver);
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspectionNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionsScreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionsScreen.changeCustomerForInspection(inspectionNumber, testcustomer2);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyIfChangedCustomerIsSavedAfterEditInspection(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(appiumdriver);
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(appiumdriver);
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleinfoscreen = new VNextVehicleInfoScreen(appiumdriver);
        vehicleinfoscreen.setVIN(inspectionData.getVinNumber());
        final String inspectionNumber = vehicleinfoscreen.getNewInspectionNumber();
        inspectionsScreen = vehicleinfoscreen.saveInspectionViaMenu();
        inspectionsScreen.changeCustomerForInspection(inspectionNumber, testcustomer2);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());

        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        VNextVehicleInfoScreen vehicleInfoScreen = inspectionsMenuScreen.clickEditInspectionMenuItem();
        vehicleinfoscreen.changeScreen("Services");
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(appiumdriver);
        availableservicesscreen.selectService(inspectionData.getServiceName());
        availableservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());

        inspectionsScreen.clickBackButton();
    }
}

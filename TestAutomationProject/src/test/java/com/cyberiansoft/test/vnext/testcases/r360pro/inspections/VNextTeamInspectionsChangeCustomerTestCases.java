package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.enums.VehicleDataField;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.GeneralWizardInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.VNextInspectionsMenuScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.steps.GeneralSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInspectionsChangeCustomerTestCases extends BaseTestCaseTeamEditionRegistration {

    final RetailCustomer testcustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Work Orders Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsChangeCustomersTestCasesDataPath();
        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextCustomersScreen customersscreen = homescreen.clickCustomersMenuItem();
        customersscreen.switchToRetailMode();
        if (!customersscreen.isCustomerExists(testcustomer1)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer1);
            customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
        }
        if (!customersscreen.isCustomerExists(testcustomer2)) {
            VNextNewCustomerScreen newcustomerscreen = customersscreen.clickAddCustomerButton();
            newcustomerscreen.createNewCustomer(testcustomer2);
            customersscreen = new VNextCustomersScreen(DriverBuilder.getInstance().getAppiumDriver());
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

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.changeCustomerForInspection(inspectionNumber, testcustomer2);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyIfChangedCustomerIsSavedAfterEditInspection(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.changeCustomerForInspection(inspectionNumber, testcustomer2);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());

        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        inspectionsMenuScreen.clickEditInspectionMenuItem();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        VNextAvailableServicesScreen availableservicesscreen = new VNextAvailableServicesScreen(DriverBuilder.getInstance().getAppiumDriver());
        availableservicesscreen.selectService(inspectionData.getServiceData().getServiceName());
        availableservicesscreen.saveInspectionViaMenu();
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());
        Assert.assertEquals(inspectionsScreen.getInspectionPriceValue(inspectionNumber), inspectionData.getInspectionPrice());

        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantCreateNewCustomerOnChangeCustomerScreen(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        VNextInspectionsMenuScreen inspectionsMenuScreen = inspectionsScreen.clickOnInspectionByInspNumber(inspectionNumber);
        customersscreen = inspectionsMenuScreen.clickChangeCustomerMenuItem();
        Assert.assertFalse(customersscreen.isAddCustomerButtonDisplayed());
        customersscreen.clickBackButton();
        inspectionsScreen = new VNextInspectionsScreen(DriverBuilder.getInstance().getAppiumDriver());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeRetailCustomerToWholesaleForInspection(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.changeCustomerToWholesailForInspection(inspectionNumber, testwholesailcustomer);
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testwholesailcustomer.getFullName());
        inspectionsScreen.switchToMyInspectionsView();

        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSelectCustomerUsingSearch(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.changeCustomerForWorkOrderViaSearch(inspectionNumber, testcustomer2);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());
        inspectionsScreen.clickBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerForTeamInspection(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homescreen = new VNextHomeScreen(DriverBuilder.getInstance().getAppiumDriver());
        VNextInspectionsScreen inspectionsScreen = homescreen.clickInspectionsMenuItem();
        inspectionsScreen.switchToMyInspectionsView();
        VNextCustomersScreen customersscreen = inspectionsScreen.clickAddInspectionButton();
        customersscreen.selectCustomer(testcustomer1);
        VNextInspectionTypesList inspectionTypesList = new VNextInspectionTypesList(DriverBuilder.getInstance().getAppiumDriver());
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
		GeneralSteps.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspectionNumber = vehicleInfoScreen.getNewInspectionNumber();
        inspectionsScreen = vehicleInfoScreen.saveInspectionViaMenu();
        inspectionsScreen.changeCustomerForInspection(inspectionNumber, testcustomer2);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());
        inspectionsScreen.switchToTeamInspectionsView();
        inspectionsScreen.searchInpectionByFreeText(inspectionNumber);
        Assert.assertEquals(inspectionsScreen.getInspectionCustomerValue(inspectionNumber), testcustomer2.getFullName());
        inspectionsScreen.switchToMyInspectionsView();
        inspectionsScreen.clickBackButton();
    }
}

package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.CustomersScreenValidation;
import com.cyberiansoft.test.vnext.validations.InspectionsValidations;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamInspectionsChangeCustomerTestCases extends BaseTestClass {

    final RetailCustomer testCustomer1 = new RetailCustomer("RetailCustomer", "RetailLast");
    final RetailCustomer testCustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Work Orders Change Customer Test Cases")
    public void settingUp() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getInspectionsChangeCustomersTestCasesDataPath();
        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExist(testCustomer1);
        CustomerServiceSteps.createCustomerIfNotExist(testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @AfterClass()
    public void settingDown() {
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerForInspection(String rowID,
                                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testCustomer1, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.changeCustomerForInspection(inspectionNumber, testCustomer2);
        InspectionsValidations.verifyInspectionCustomer(inspectionNumber, testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyIfChangedCustomerIsSavedAfterEditInspection(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testCustomer1, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.changeCustomerForInspection(inspectionNumber, testCustomer2);
        InspectionsValidations.verifyInspectionCustomer(inspectionNumber, testCustomer2);

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(inspectionData.getServiceData());
        InspectionSteps.saveInspection();
        InspectionsValidations.verifyInspectionCustomer(inspectionNumber, testCustomer2);
        InspectionsValidations.verifyInspectionTotalPrice(inspectionNumber, inspectionData.getInspectionPrice());
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCantCreateNewCustomerOnChangeCustomerScreen(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testCustomer1, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_CUSTOMER);
        CustomersScreenValidation.validateAddCustomerButtonDisplayed(false);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeRetailCustomerToWholesaleForInspection(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testCustomer1, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.changeCustomerForInspection(inspectionNumber, testwholesailcustomer);
        InspectionSteps.switchToTeamInspections();
        SearchSteps.textSearch(inspectionNumber);
        InspectionsValidations.verifyInspectionCustomer(inspectionNumber, testwholesailcustomer);
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanSelectCustomerUsingSearch(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testCustomer1, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.changeCustomerForInspection(inspectionNumber, testCustomer2);
        InspectionsValidations.verifyInspectionCustomer(inspectionNumber, testCustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangeCustomerForTeamInspection(String rowID,
                                                             String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testCustomer1, InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.changeCustomerForInspection(inspectionNumber, testCustomer2);
        InspectionsValidations.verifyInspectionCustomer(inspectionNumber, testCustomer2);
        InspectionSteps.switchToTeamInspections();
        SearchSteps.textSearch(inspectionNumber);
        InspectionsValidations.verifyInspectionCustomer(inspectionNumber, testCustomer2);
        InspectionSteps.switchToMyInspections();
        ScreenNavigationSteps.pressBackButton();
    }
}

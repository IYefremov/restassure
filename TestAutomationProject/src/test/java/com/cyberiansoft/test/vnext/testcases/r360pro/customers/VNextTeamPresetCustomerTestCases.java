package com.cyberiansoft.test.vnext.testcases.r360pro.customers;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WholesailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.screens.VNextHomeScreen;
import com.cyberiansoft.test.vnext.screens.VNextNewCustomerScreen;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextInspectionTypesList;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextInspectionsScreen;
import com.cyberiansoft.test.vnext.screens.typesscreens.VNextWorkOrdersScreen;
import com.cyberiansoft.test.vnext.screens.wizardscreens.VNextVehicleInfoScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.ScreenNavigationSteps;
import com.cyberiansoft.test.vnext.steps.VehicleInfoScreenSteps;
import com.cyberiansoft.test.vnext.steps.WizardScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.CustomersScreenValidation;
import com.cyberiansoft.test.vnext.validations.HomeScreenValidation;
import com.cyberiansoft.test.vnext.validations.InspectionsScreenValidation;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamPresetCustomerTestCases extends BaseTestClass {

    RetailCustomer retailCustomer1 = new RetailCustomer("Preset1", "RetailCustomer1");
    RetailCustomer retailCustomer2 = new RetailCustomer("Preset2", "RetailCustomer2");

    RetailCustomer defaultRetailCustomer = new RetailCustomer("Retail", "");

    WholesailCustomer defaultWholesailCustomer = new WholesailCustomer("Wholesale", "Wholesale", "Wholesale");

    @BeforeClass(description="Team Preset Customer Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPresetCustomerTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelPresetCustomerIfUserGoFromRetailToWholesaleCustomerScreen(String rowID,
                                                                                                 String description, JSONObject testData) {

        HomeScreenSteps.openCustomers();
        VNextCustomersScreen customerScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        customerScreen.switchToWholesaleMode();
        CustomersScreenValidation.validateDefaultCustomerValue(defaultWholesailCustomer.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(defaultWholesailCustomer.getFullName());
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelPresetCustomer(String rowID,
                                                      String description, JSONObject testData) {

        HomeScreenSteps.openCustomers();
        VNextCustomersScreen customerScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        HomeScreenSteps.openCustomers();
        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        customerScreen.resetPresetCustomer();
        CustomersScreenValidation.validateDefaultCustomerValue(defaultRetailCustomer.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(defaultRetailCustomer.getFullName().trim());
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanChangePresetCustomer(String rowID,
                                                      String description, JSONObject testData) {

        List<RetailCustomer> retailCustomers = new ArrayList<RetailCustomer>() {{
            add(retailCustomer1);
            add(retailCustomer2);
        }};

        for (RetailCustomer retailCustomer : retailCustomers) {
            HomeScreenSteps.openCustomers();
            CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer);

            CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer.getFullName());
            ScreenNavigationSteps.pressBackButton();
            HomeScreenValidation.validateDefaultCustomerValue(retailCustomer.getFullName());
        }
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCreateInspectionWithPresetCustomer(String rowID,
                                                                    String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        HomeScreenSteps.openCustomers();

        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());

        VNextInspectionsScreen inspectionsScreen = homeScreen.clickInspectionsMenuItem();
        VNextInspectionTypesList inspectionTypesList = inspectionsScreen.clickAddInspectionWithPreselectedCustomerButton();
        inspectionTypesList.selectInspectionType(InspectionTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(inspectionData.getVehicleInfo());
        final String inspNumber = vehicleInfoScreen.getNewInspectionNumber();
        vehicleInfoScreen.saveInspectionViaMenu();
        InspectionsScreenValidation.validateInspectionCustomerValueByInspectionNumber(inspNumber, retailCustomer1.getFullName());
        inspectionsScreen.clickBackButton();

    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateWOWithPresetCustomer(String rowID,
                                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        VNextHomeScreen homeScreen = new VNextHomeScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        HomeScreenSteps.openCustomers();

        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());

        VNextWorkOrdersScreen workOrdersScreen = homeScreen.clickWorkOrdersMenuItem();
        VNextWorkOrderTypesList workOrderTypesList = workOrdersScreen.clickAddWorkOrdernWithPreselectedCustomerButton();
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.O_KRAMAR);
        VNextVehicleInfoScreen vehicleInfoScreen = new VNextVehicleInfoScreen();
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenSteps.setVehicleInfo(workOrderData.getVehicleInfoData());
        VehicleInfoScreenSteps.openTechnicianMenu();
        final String workOrderNumber = vehicleInfoScreen.getNewInspectionNumber();

        final String SERVICE_NAME = "Battery Installation";
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.clickAddServiceButton(SERVICE_NAME);

        vehicleInfoScreen.saveWorkOrderViaMenu();
        Assert.assertEquals(workOrdersScreen.getWorkOrderCustomerValue(workOrderNumber), retailCustomer1.getFullName());
        workOrdersScreen.clickBackButton();
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanPresetRetailCustomer(String rowID,
                                                      String description, JSONObject testData) {
        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanPresetWholesaleCustomer(String rowID,
                                                         String description, JSONObject testData) {
        VNextCustomersScreen customerScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        HomeScreenSteps.openCustomers();
        customerScreen.switchToWholesaleMode();
        customerScreen.setCustomerAsDefault(testwholesailcustomer);
        WaitUtilsWebDriver.waitForTextToBePresentInElement(customerScreen.getPresetcustomerpanel(), testwholesailcustomer.getFullName());

        CustomersScreenValidation.validateDefaultCustomerValue(testwholesailcustomer.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(testwholesailcustomer.getFullName());
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyPresetCustomerCanceledIfUserGoFromRetailToWholesaleCustomerScreen(String rowID,
                                                      String description, JSONObject testData) {

        WholesailCustomer defaultWholesailCustomer = new WholesailCustomer();
        defaultWholesailCustomer.setCompanyName("Wholesale");
        HomeScreenSteps.openCustomers();
        VNextCustomersScreen customerScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());

        customerScreen.switchToRetailMode();
        if (!customerScreen.isCustomerExists(retailCustomer1)) {
            VNextNewCustomerScreen newCustomerScreen = customerScreen.clickAddCustomerButton();
            newCustomerScreen.createNewCustomer(retailCustomer1);
            customerScreen = new VNextCustomersScreen(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        }
        customerScreen.setCustomerAsDefault(retailCustomer1);
        WaitUtilsWebDriver.waitForTextToBePresentInElement(customerScreen.getPresetcustomerpanel(), retailCustomer1.getFullName());

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        HomeScreenSteps.openCustomers();

        customerScreen.switchToWholesaleMode();
        CustomersScreenValidation.validateDefaultCustomerValue(defaultWholesailCustomer.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(defaultWholesailCustomer.getFullName());

        HomeScreenSteps.openCustomers();
        customerScreen.switchToRetailMode();
        CustomersScreenValidation.validateDefaultCustomerValue(defaultRetailCustomer.getFullName().trim());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(defaultRetailCustomer.getFullName());
    }

}

package com.cyberiansoft.test.vnext.testcases.r360pro.customers;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.CustomersScreenValidation;
import com.cyberiansoft.test.vnext.validations.HomeScreenValidation;
import com.cyberiansoft.test.vnext.validations.InspectionsScreenValidation;
import com.cyberiansoft.test.vnext.validations.WorkOrdersScreenValidations;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamPresetCustomerTestCases extends BaseTestClass {

    RetailCustomer retailCustomer1 = new RetailCustomer("Preset1", "RetailCustomer1");
    RetailCustomer retailCustomer2 = new RetailCustomer("Preset2", "RetailCustomer2");

    @BeforeClass(description="Team Preset Customer Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getPresetCustomerTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelPresetCustomerIfUserGoFromRetailToWholesaleCustomerScreen(String rowID,
                                                                                                 String description, JSONObject testData) {

        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenSteps.switchToWholesaleMode();
        CustomersScreenValidation.validateDefaultCustomerValue("");
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue("");
    }


    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void testVerifyUserCanCancelPresetCustomer(String rowID,
                                                      String description, JSONObject testData) {

        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        HomeScreenSteps.openCustomers();
        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        CustomersScreenSteps.resetPresetCustomer();
        CustomersScreenValidation.validateDefaultCustomerValue("");
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue("");
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

        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(InspectionTypes.O_KRAMAR, inspectionData);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionsScreenValidation.validateInspectionCustomerValueByInspectionNumber(inspectionId, retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();

    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateWOWithPresetCustomer(String rowID,
                                                            String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCustomers();

        CustomerServiceSteps.createCustomerIfNotExistAndSetAsDefault(retailCustomer1);

        CustomersScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(retailCustomer1.getFullName());

        HomeScreenSteps.openCreateMyWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR, workOrderData);
        VehicleInfoScreenSteps.openTechnicianMenu();

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData());

        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderCustomerValue(workOrderId, retailCustomer1);
        ScreenNavigationSteps.pressBackButton();
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
        HomeScreenSteps.openCustomers();
        CustomersScreenSteps.switchToWholesaleMode();
        CustomersScreenSteps.setCustomerAsDefault(testwholesailcustomer);
        CustomersScreenValidation.validateDefaultCustomerValue(testwholesailcustomer.getFullName());
        ScreenNavigationSteps.pressBackButton();
        HomeScreenValidation.validateDefaultCustomerValue(testwholesailcustomer.getFullName());
    }

}

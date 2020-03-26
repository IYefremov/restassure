package com.cyberiansoft.test.vnext.testcases.r360pro.inspections;

import com.cyberiansoft.test.dataclasses.InspectionData;
import com.cyberiansoft.test.dataclasses.RetailCustomer;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.globalutils.GlobalUtils;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.HelpingScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.screens.customers.VNextCustomersScreen;
import com.cyberiansoft.test.vnext.screens.typeselectionlists.VNextWorkOrderTypesList;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.VehicleInfoScreenValidations;
import com.cyberiansoft.test.vnextbo.steps.users.CustomerServiceSteps;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextVehicleOwnerSupportTestCases extends BaseTestClass {

    final RetailCustomer testcustomer2 = new RetailCustomer("RetailCustomer2", "RetailLast2");


    @BeforeClass(description = "Vehicle Owner Support Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getVehicleOwnerSupportTestCasesDataPath();
        HomeScreenSteps.openCustomers();
        CustomerServiceSteps.createCustomerIfNotExist(testcustomer2);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCreateNewCustomerWhenSelectRetailOwner_Inspection(String rowID,
                                                                String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);

        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        //CustomersScreenSteps.switchToRetailMode();
        //CustomersScreenSteps.clickAddCustomerButton();

        //VNextNewCustomerScreen nextNewCustomerScreen = new VNextNewCustomerScreen();
        final String customerFirstName = StringUtils.capitalize(GlobalUtils.getUUID());
        final String customerLastName = StringUtils.capitalize(GlobalUtils.getUUID());
       // nextNewCustomerScreen.createNewCustomer(new RetailCustomer(customerFirstName, customerLastName));
        CustomerServiceSteps.createCustomerIfNotExist(new RetailCustomer(customerFirstName, customerLastName));
        VehicleInfoScreenValidations.ownerShouldBe(customerFirstName + " " + customerLastName);
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSelectCreatedRetailCustomerWhenAssignOwner(String rowID,
                                                                                   String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer2.getFullName());
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeRetailOwnerToWholesale(String rowID,
                                                                            String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer2.getFullName());
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VehicleInfoScreenValidations.ownerShouldBe(testwholesailcustomer.getFullName());
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeWholesaleOwnerToRetail(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToWholesaleMode();
        customersScreen.selectCustomer(testwholesailcustomer);
        VehicleInfoScreenValidations.ownerShouldBe(testwholesailcustomer.getFullName());

        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer2.getFullName());
        InspectionSteps.saveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyAssignedOwnerForInspectionDisplaysAsSelectedWhenUserCreateWOFromThisInspection(String rowID,
                                                              String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer2.getFullName());
        final String inspectionNumber = InspectionSteps.saveInspection();

        SearchSteps.textSearch(inspectionNumber);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.selectCreateWorkOrder();
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer2.getFullName());
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanChangeOwnerWhenCreateWOFromInspection(String rowID,
                                                                                                         String description, JSONObject testData) {

        InspectionData inspectionData = JSonDataParser.getTestDataFromJson(testData, InspectionData.class);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR, inspectionData);
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        VNextCustomersScreen customersScreen = new VNextCustomersScreen();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer2);
        HelpingScreenInteractions.dismissHelpingScreenIfPresent();
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer2.getFullName());
        final String inspectionNumber = InspectionSteps.saveInspection();

        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuSteps.selectMenuItem(MenuItems.APPROVE);
        ApproveSteps.drawSignature();
        ApproveSteps.saveApprove();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.selectCreateWorkOrder();
        VNextWorkOrderTypesList workOrderTypesList = new VNextWorkOrderTypesList(ChromeDriverProvider.INSTANCE.getMobileChromeDriver());
        workOrderTypesList.selectWorkOrderType(WorkOrderTypes.KRAMAR_AUTO);
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer2.getFullName());
        VehicleInfoScreenInteractions.clickSelectOwnerCell();
        customersScreen.switchToRetailMode();
        customersScreen.selectCustomer(testcustomer);
        VehicleInfoScreenValidations.ownerShouldBe(testcustomer.getFullName());

        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}

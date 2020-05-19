package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.TechnicianScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.commonobjects.dialogs.WarningDialogSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.TechScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextTeamWorkOrdersTechniciansTestCases extends BaseTestClass {

    @BeforeClass(description = "Team Work Orders Technicians Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getWorkOrdersTestCasesDataPath();
        //HomeScreenSteps.openWorkOrders();
        //WorkOrderSteps.switchToTeamWorkOrdersView();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanIncreaseTechniciansQuantityForCustomTechSplit(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        HomeScreenSteps.openCreateTeamWorkOrder();
        WorkOrderSteps.createWorkOrder(testwholesailcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectCustomOption();
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage(employee.getEmployeeName(), "100");
        TechnicianScreenSteps.searchAndSelectTechnician("Test Test");
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage(employee.getEmployeeName(), "100");
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage("Test Test", "0");
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickYesButton();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSeparateAmountOfWOWithLaborServiceForTwoTechnicians(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        HomeScreenSteps.openCreateTeamWorkOrder();
        WorkOrderSteps.createWorkOrder(testwholesailcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenSteps.searchAndSelectTechnician("Test Test");
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage(employee.getEmployeeName(), "50");
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage("Test Test", "50");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.goToTheNextScreen();
        AvailableServicesScreenSteps.openServicesList();
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServices().get(0));
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToTheNextScreen();
        TopScreenPanelSteps.goToTheNextScreen();
        String wONumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(wONumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage(employee.getEmployeeName(), "50");
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage("Test Test", "50");
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickYesButton();
        TopScreenPanelSteps.goToThePreviousScreen();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSplitValueCustomAndSeeConfirmationWindow(String rowID, String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        HomeScreenSteps.openCreateTeamWorkOrder();
        WorkOrderSteps.createWorkOrder(testwholesailcustomer, WorkOrderTypes.AUTOMATION_WO_MONITOR, workOrderData);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenSteps.searchAndSelectTechnician("Test Test");
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage(employee.getEmployeeName(), "50");
        TechScreenValidations.verifyTechnicianIsSelectedWithCorrectPercentage("Test Test", "50");
        TopScreenPanelSteps.saveChanges();
        TopScreenPanelSteps.goToTheNextScreen();
        AvailableServicesScreenSteps.openServicesList();
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServices().get(0));
        TopScreenPanelSteps.goToThePreviousScreen();
        TopScreenPanelSteps.goToTheNextScreen();
        TopScreenPanelSteps.goToTheNextScreen();
        String wONumber = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(wONumber);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectCustomOption();
        TopScreenPanelSteps.saveChanges();
        WarningDialogSteps.clickOkButton();
        TopScreenPanelSteps.goToThePreviousScreen();
        WarningDialogSteps.clickYesButton();
        TopScreenPanelSteps.goToThePreviousScreen();
    }
}

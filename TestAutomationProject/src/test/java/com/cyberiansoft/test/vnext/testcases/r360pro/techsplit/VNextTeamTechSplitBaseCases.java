package com.cyberiansoft.test.vnext.testcases.r360pro.techsplit;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.TechnicianScreenInteractions;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.TechScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class VNextTeamTechSplitBaseCases extends BaseTestClass {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Tech split base test cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getTechSplitDataPath();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanAssignFullAmountOfWOFor1TechnicianEvenlyTeam(String rowID,
                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();

        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        TechScreenValidations.validateTechnicianSelected(employee);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectEvenlyOption();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        TechnicianScreenInteractions.acceptScreen();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());

        workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        TechScreenValidations.validateTechnicianSelected(employee);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectEvenlyOption();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        ScreenNavigationSteps.pressBackButton();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeparateAmountOfWOFor3TechniciansEvenlyTeam(String rowID,
                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<Employee> employeeList = workOrderData.getTechnicianList();
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        TechScreenValidations.validateTechnicianSelected(employee);
        VehicleInfoScreenSteps.selectTechnicians(employeeList);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectEvenlyOption();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);

        ScreenNavigationSteps.pressBackButton();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());

        workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        TechScreenValidations.validateTechnicianSelected(employee);
        VehicleInfoScreenInteractions.openTechnicianList();
        TechnicianScreenInteractions.selectEvenlyOption();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);

        ScreenNavigationSteps.pressBackButton();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanChangeTechAmountViaEdit(String rowID,
                                                                   String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<Employee> employeeList = workOrderData.getTechnicianList();

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        TechScreenValidations.validateTechnicianSelected(employee);
        VehicleInfoScreenSteps.selectTechnicians(employeeList);
        TechScreenValidations.validateTechnicianSelected(employee);
        TechScreenValidations.validateTechniciansSelected(employeeList);

        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());

        workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.deselectTechnicians(employeeList);
        GeneralSteps.confirmDialog();
        TechScreenValidations.validateTechnicianSelected(employee);
        WorkOrderSteps.saveWorkOrder();

        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.selectTechnicians(employeeList);
        GeneralSteps.confirmDialog();
        TechScreenValidations.validateTechnicianSelected(employee);
        TechScreenValidations.validateTechniciansSelected(employeeList);
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }
}

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
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.InformationDialogValidations;
import com.cyberiansoft.test.vnext.validations.TechScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNextTeamTechSplitTeamCases extends BaseTestClass {

    @BeforeClass(description = "Tech split team test cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getTechSplitDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeparateAmountOfWOForTechniciansManually(String rowID,
                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<Employee> technicianList = workOrderData.getTechnicianList();
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();

        HomeScreenSteps.openCreateTeamInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        VehicleInfoScreenSteps.selectTechnicians(technicianList);
        VehicleInfoScreenSteps.setTechniciansPercentage(technicianSplitData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        TechnicianScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeparateAmountOfWOForTechniciansManuallyEditingWO(String rowID,
                                                                         String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();
        List<Employee> technicianList = workOrderData.getTechnicianList();
        Map<String, String> changedTechnicianSplitData = new HashMap<>(workOrderData.getTechnicianSplitData());
        changedTechnicianSplitData.forEach((key, val) -> changedTechnicianSplitData.put(key, "50"));

        HomeScreenSteps.openCreateTeamInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        VehicleInfoScreenSteps.selectTechnicians(technicianList);
        VehicleInfoScreenSteps.setTechniciansPercentage(technicianSplitData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.setTechniciansPercentage(changedTechnicianSplitData);
        GeneralSteps.confirmDialog();
        WorkOrderSteps.saveWorkOrder();

        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(changedTechnicianSplitData);
        TechnicianScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanSeparateAmountOfWOForTechniciansManuallyEditingWOAndCancelIt(String rowID,
                                                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();
        List<Employee> technicianList = workOrderData.getTechnicianList();
        Map<String, String> changedTechnicianSplitData = new HashMap<>(workOrderData.getTechnicianSplitData());
        changedTechnicianSplitData.forEach((key, val) -> changedTechnicianSplitData.put(key, "50"));

        HomeScreenSteps.openCreateTeamInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        VehicleInfoScreenSteps.selectTechnicians(technicianList);
        VehicleInfoScreenSteps.setTechniciansPercentage(technicianSplitData);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.setTechniciansPercentage(changedTechnicianSplitData);
        GeneralSteps.declineDialog();
        TechScreenValidations.validateTechniciansPercentage(changedTechnicianSplitData);
        TechnicianScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(changedTechnicianSplitData);
        TechnicianScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCantSpecifyValueNotEqualTo100PercentOnSplit(String rowID,
                                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();
        List<Employee> technicianList = workOrderData.getTechnicianList();
        List<String> customTechPercentages = new ArrayList<>();
        customTechPercentages.add("30");
        customTechPercentages.add("51");
        customTechPercentages.add("0");

        HomeScreenSteps.openCreateTeamInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        VehicleInfoScreenSteps.selectTechnicians(technicianList);
        VehicleInfoScreenSteps.setTechniciansPercentage(technicianSplitData);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        TechnicianScreenInteractions.selectCustomOption();
        customTechPercentages.forEach(techPercentages -> {
            TechnicianScreenInteractions.setTechnicianPercentage(technicianList.get(0).getEmployeeName(), techPercentages);
            TechnicianScreenSteps.closeTechnicianMenu();
            InformationDialogValidations.clickOKAndVerifyMessage("Total amount is not equals 100%.");
        });

        ScreenNavigationSteps.pressBackButton();
        WorkOrderSteps.cancelWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanRecalculateAmountOfTheServiceManuallyBetweenTechniciansWhenUserChangeQtyOfTechniciansDifferentThenQtyOfWOTechnicians(String rowID,
                                                              String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        List<Employee> technicianList = workOrderData.getTechnicianList();
        final String defEmployeePrice = "70";
        final String newEmployeePrice = "30";

        HomeScreenSteps.openCreateTeamInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.openServiceDetails(workOrderData.getServiceData());
        ServiceDetailsScreenSteps.changeServicePrice(workOrderData.getServiceData().getServicePrice());
        ServiceDetailsScreenSteps.saveServiceDetails();
        SelectedServicesScreenSteps.switchToSelectedService();
        final String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechnicianScreenSteps.selectTechnicians(technicianList);
        TechnicianScreenInteractions.selectCustomOption();
        TechnicianScreenInteractions.setTechnicianPercentage(employee.getEmployeeName(), defEmployeePrice);
        TechnicianScreenInteractions.setTechnicianPercentage(technicianList.get(0).getEmployeeName(), newEmployeePrice);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.saveServiceDetails();

        WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(workOrderData.getServiceData().getServiceName());
        ServiceDetailsScreenSteps.openTechniciansScreen();

        TechScreenValidations.validateTechnicianValue(employee.getEmployeeName(), defEmployeePrice);
        TechScreenValidations.validateTechnicianValue(technicianList.get(0).getEmployeeName(), newEmployeePrice);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.saveServiceDetails();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }
}


package com.cyberiansoft.test.vnext.testcases.r360pro.techsplit;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.ServiceData;
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
import com.cyberiansoft.test.vnext.validations.TechScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class VNextTeamTechSplitServiceCases extends BaseTestClass {

    @BeforeClass(description = "Tech split service test cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getTechSplitServiceDataPath();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void serviceLevelTechSplitValidation(String rowID,
                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData service = workOrderData.getServiceData();
        List<Employee> employeeList = workOrderData.getTechnicianList();
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();
        final String servicePrice = "100";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(servicePrice);
        ServiceDetailsScreenSteps.saveServiceDetails();

        SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechnicianValue(employee.getEmployeeName(), servicePrice);

        TechnicianScreenSteps.selectTechnicians(employeeList);
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void amountOfServiceRecalculatedBetweenTechniciansWhenUserChangeServicePrice(String rowID,
                                                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();
        ServiceData service = workOrderData.getServiceData();
        List<Employee> employeeList = workOrderData.getTechnicianList();
        final String servicePrice = "100";
        final String newServicePrice = "200";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(servicePrice);
        ServiceDetailsScreenSteps.saveServiceDetails();

        SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechnicianValue(employee.getEmployeeName(), servicePrice);

        TechnicianScreenSteps.selectTechnicians(employeeList);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.changeServicePrice(newServicePrice);

        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void amountOfServiceIsRecalculatedAutomaticallyBetweenTechniciansWhenUserChangeQuantityOfTheServiceEvenly(String rowID,
                                                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();
        ServiceData service = workOrderData.getServiceData();
        List<Employee> employeeList = workOrderData.getTechnicianList();
        final String servicePrice = "100";
        final String serviceQuantity = "10";

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

        AvailableServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(servicePrice);
        ServiceDetailsScreenSteps.saveServiceDetails();

        SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechnicianValue(employee.getEmployeeName(), servicePrice);

        TechnicianScreenSteps.selectTechnicians(employeeList);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.changeServiceQuantity(serviceQuantity);

        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void amountOfServiceIsRecalculatedAutomaticallyBetweenTechniciansWhenUserChangeQuantityOfTheServiceCustom(String rowID,
                                                                                                                     String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<Employee> technicianList = workOrderData.getTechnicianList();
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();
        ServiceData service = workOrderData.getServiceData();
        final String defEmployeePrice = "140";
        final String newEmployeePrice = "60";

        HomeScreenSteps.openCreateMyInspection();
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
        AvailableServicesScreenSteps.selectService(service);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();

        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        TechnicianScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(service.getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(service.getServiceQuantity());

        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechnicianValue(employee.getEmployeeName(), defEmployeePrice);
        TechScreenValidations.validateTechnicianValue(technicianList.get(0).getEmployeeName(), newEmployeePrice);

        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void userCanResetSplitToDefault(String rowID,
                                           String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);

        List<Employee> technicianList = workOrderData.getTechnicianList();
        Map<String, String> technicianSplitData = workOrderData.getTechnicianSplitData();
        ServiceData service = workOrderData.getServiceData();
        final String defEmployeePrice = "140";
        final String newEmployeePrice = "60";
        final String defEmployeeNewPrice = "150";
        final String newEmployeeNewPrice = "50";

        HomeScreenSteps.openCreateMyInspection();
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
        AvailableServicesScreenSteps.selectService(service);
        final String workOrderId = WorkOrderSteps.saveWorkOrder();

        WorkOrderSteps.openMenu(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        VehicleInfoScreenSteps.openTechnicianMenu();
        TechScreenValidations.validateTechniciansPercentage(technicianSplitData);
        TechnicianScreenSteps.closeTechnicianMenu();
        GeneralSteps.confirmDialog();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        SelectedServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice(service.getServicePrice());
        ServiceDetailsScreenSteps.changeServiceQuantity(service.getServiceQuantity());

        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechnicianValue(employee.getEmployeeName(), defEmployeePrice);
        TechScreenValidations.validateTechnicianValue(technicianList.get(0).getEmployeeName(), newEmployeePrice);
        TechnicianScreenInteractions.setTechnicianPercentage(employee.getEmployeeName(), defEmployeeNewPrice);
        TechnicianScreenInteractions.setTechnicianPercentage(technicianList.get(0).getEmployeeName(), newEmployeeNewPrice);
        TechnicianScreenInteractions.selectDefault();

        TechScreenValidations.validateTechnicianValue(employee.getEmployeeName(), defEmployeePrice);
        TechScreenValidations.validateTechnicianValue(technicianList.get(0).getEmployeeName(), newEmployeePrice);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.saveServiceDetails();
        InspectionSteps.cancelInspection();
        ScreenNavigationSteps.pressBackButton();
    }
}

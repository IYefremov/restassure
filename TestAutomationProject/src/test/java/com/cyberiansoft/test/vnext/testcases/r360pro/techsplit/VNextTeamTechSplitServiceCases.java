package com.cyberiansoft.test.vnext.testcases.r360pro.techsplit;

import com.cyberiansoft.test.dataclasses.Employee;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestCaseTeamEditionRegistration;
import com.cyberiansoft.test.vnext.validations.TechScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class VNextTeamTechSplitServiceCases extends BaseTestCaseTeamEditionRegistration {
    private String inspectionId = "";
    private String workOrderId = "";

    @BeforeClass(description = "Tech split service test cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getTechSplitServiceDataPath();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);

    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void serviceLevelTechSplitValidation(String rowID,
                                                String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData service = workOrderData.getServiceData();
        List<Employee> employeeList = workOrderData.getTechnicianList();
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();

        AvailableServicesScreenSteps.openServiceDetails(service.getServiceName());
        ServiceDetailsScreenSteps.changeServicePrice("100");
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechnicianScreenSteps.selectTechnicians(employeeList);
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "serviceLevelTechSplitValidation")
    public void amountOfServiceRecalculatedBetweenTechniciansWhenUserChangeServicePrice(String rowID,
                                                                                        String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();

        ServiceDetailsScreenSteps.changeServicePrice("200");
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "amountOfServiceRecalculatedBetweenTechniciansWhenUserChangeServicePrice")
    public void amountOfServiceIsRecalculatedWhenQuantityOfServiceIncreased(String rowID,
                                                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();

        ServiceDetailsScreenSteps.increaseServiceQuantity();
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "amountOfServiceIsRecalculatedWhenQuantityOfServiceIncreased")
    public void amountOfServiceIsRecalculatedWhenQuantityOfServiceDecreased(String rowID,
                                                                            String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();

        ServiceDetailsScreenSteps.decreaseServiceQuantity();
        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "amountOfServiceIsRecalculatedWhenQuantityOfServiceDecreased")
    public void userCanResetSplitToDefault(String rowID,
                                           String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<Employee> employeeList = workOrderData.getTechnicianList();
        Map<String, String> expectedTechSplit = workOrderData.getTechnicianSplitData();

        ServiceDetailsScreenSteps.openTechniciansScreen();
        TechnicianScreenSteps.selectTechnicians(employeeList);
        TechnicianScreenSteps.selectDefault();
        TechScreenValidations.validateTechniciansValues(expectedTechSplit);
        TechnicianScreenSteps.closeTechnicianMenu();
        ServiceDetailsScreenSteps.closeServiceDetailsScreen();
        WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }
}

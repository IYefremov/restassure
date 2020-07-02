package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.MonitoringDataUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderInfoDto;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.*;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.restclient.VNextAPIHelper;
import com.cyberiansoft.test.vnext.restclient.monitorrolessettings.RoleSettingsDTO;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.commonobjects.TopScreenPanelSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.PhaseScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.ServiceDetailsScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.MonitorValidations;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import com.cyberiansoft.test.vnext.validations.ServiceDetailsValidations;
import com.cyberiansoft.test.vnext.validations.monitor.RepairOrderInfoValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class VNextMonitorEditROTestCases extends BaseTestClass {
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitor Edit RO Test")
    public void beforeClass() throws IOException {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringEditRODataPath();

        RoleSettingsDTO roleSettingsDTO = new RoleSettingsDTO();
        roleSettingsDTO.setMonitorCanAddService(false);
        roleSettingsDTO.setMonitorCanEditService(true);
        roleSettingsDTO.setMonitorCanRemoveService(false);
        VNextAPIHelper.updateEmployeeRoleSettings(MonitorRole.EMPLOYEE, roleSettingsDTO);

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(MonitoringDataUtils.getTestSerivceData());
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSwitchToInfoMode(String rowID,
                                                  String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderInfoDto expectedOrderInfo = workOrderData.getMonitoring().getOrderInfoDto();

        EditOrderSteps.switchToInfo();
        RepairOrderInfoValidations.verifyOrderInfo(expectedOrderInfo);
        EditOrderSteps.setOrderPriority(OrderPriority.HIGH);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.HIGH, true);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.LOW, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.NORMAL, false);

        EditOrderSteps.setOrderPriority(OrderPriority.LOW);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.HIGH, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.LOW, true);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.NORMAL, false);

        EditOrderSteps.setOrderPriority(OrderPriority.NORMAL);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.HIGH, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.LOW, false);
        RepairOrderInfoValidations.validateRepairOrderPriority(OrderPriority.NORMAL, true);

        TopScreenPanelSteps.saveChanges();
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanStartTheService(String rowID,
                                                 String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();

        EditOrderSteps.openServiceMenu(serviceData);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.START, true);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.ASSIGN_TECH, true);
        MenuValidations.menuItemShouldBeEnabled(MenuItems.CHANGE_STATUS, false);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
        PhaseScreenValidations.validateServiceStatus(serviceData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanTypeTheNoteForService(String rowID,
                                                       String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();
        final String notesText = "Test note 1";

        EditOrderSteps.openServiceMenu(serviceData);
        MenuSteps.selectMenuItem(MenuItems.NOTES);
        NotesSteps.addNote();
        NotesSteps.setRepairOrderNoteText(notesText);
        ScreenNavigationSteps.pressBackButton();
        NotesSteps.waitUntilNotesListScreenIsLoaded();
        ScreenNavigationSteps.pressBackButton();
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
        PhaseScreenValidations.validateServiceNotes(serviceData, notesText);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAssignTheTechOnTheServiceLevel(String rowID,
                                                                String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();

        EditOrderSteps.openServiceMenu(serviceData);
        MenuSteps.selectMenuItem(MenuItems.ASSIGN_TECH);
        PhaseScreenSteps.selectTechnician(serviceData.getServiceDefaultTechnician());
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
        PhaseScreenValidations.validateServiceTechnician(serviceData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanAssignTheTechOnPhaseLevel(String rowID,
                                                           String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        OrderPhaseDto orderPhaseDto = workOrderData.getMonitoring().getOrderPhaseDto();
        ServiceData serviceData = workOrderData.getServiceData();

        EditOrderSteps.openPhaseMenu(orderPhaseDto);
        MenuSteps.selectMenuItem(MenuItems.ASSIGN_TECH);
        PhaseScreenSteps.selectTechnician(serviceData.getServiceDefaultTechnician());
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
        PhaseScreenValidations.validateServiceTechnician(serviceData);
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanSetFlagToGreenFoRo(String rowID,
                                                    String description, JSONObject testData) {
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
        TopScreenPanelSteps.saveChanges();
        MonitorSteps.waitUntilScreenIsOpenedWithOrders();
        MonitorSteps.setRepairOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        MonitorValidations.verifyOrderFlag(workOrderId, RepairOrderFlag.GREEN);
        MonitorValidations.verifyOrderBackGroundColor(workOrderId, RepairOrderBackGroundColors.GREEN);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testVerifyUserCanCloseEditServicePage(String rowID,
                                                      String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        ServiceData serviceData = workOrderData.getServiceData();

        EditOrderSteps.openServiceDetails(serviceData);
        ServiceDetailsScreenSteps.waitUntilScreenIsLoaded();
        ServiceDetailsValidations.verifyServiceDetailsPageIsOpened();
        ServiceDetailsScreenSteps.saveDetailsChanges();
        PhaseScreenSteps.waitUntilPhaseScreenIsLoaded();
        PhaseScreenValidations.verifyPhaseScreenIsDisplayed();
    }
}

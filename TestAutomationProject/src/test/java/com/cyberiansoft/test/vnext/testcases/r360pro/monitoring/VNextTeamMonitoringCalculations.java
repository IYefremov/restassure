package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataclasses.partservice.PartName;
import com.cyberiansoft.test.dataclasses.partservice.PartServiceData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.enums.monitor.OrderPhaseStatuses;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.EditOrderSteps;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.MonitorValidations;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamMonitoringCalculations extends BaseTestClass {
    private String workOrderId = "";

    @BeforeClass(description = "Team Monitoring Basic Flow Test")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringBaseCaseDataPath();

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        final String inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_WO_MONITOR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServiceGroup("Other");
        AvailableServicesScreenSteps.selectService("olrom");
        AvailableServicesScreenSteps.selectService("Orom_Money_Service");
        PartServiceData partServiceData = new PartServiceData();
        partServiceData.setServiceName("Part");
        partServiceData.setCategory("Brake");
        partServiceData.setSubCategory("Bearings");
        PartName partName = new PartName();
        List<String> list = new ArrayList<>();
        list.add("Air Brake Compressor Crank Shaft Bearing");
        partName.setPartNameList(list);
        partName.setIsMultiSelect(true);
        partServiceData.setPartName(partName);
        partServiceData.setPartPosition("Rear");
        SearchSteps.textSearch(partServiceData.getServiceName());
        PartServiceSteps.selectPartService(partServiceData);
        PartServiceSteps.confirmPartInfo();
        ScreenNavigationSteps.pressBackButton();
        BaseUtils.waitABit(1000);
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyThatSkippedRefusedAndPartsServicesAreNotInvolvedToTheCompletePercentageCalculation(String rowID,
                                                         String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        RepairOrderDto repairOrderDto = workOrderData.getMonitoring().getRepairOrderData();
        ServiceData serviceData1 = workOrderData.getServicesList().get(0);
        ServiceData serviceData2 = workOrderData.getServicesList().get(1);
        ServiceData serviceData3 = workOrderData.getServicesList().get(2);

        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        //SearchSteps.clearAllFilters();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorValidations.verifyRepairOrderValues(workOrderId, repairOrderDto);
        MonitorSteps.startRepairOrder(workOrderId);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openPhaseMenu(workOrderData.getMonitoring().getOrderPhaseDto());
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        PhaseScreenValidations.validatePhasePresent(workOrderData.getMonitoring().getOrderPhaseDto(), false);
        WizardScreenSteps.saveAction();
        repairOrderDto.setCompletePercentage("33%");
        repairOrderDto.setPhaseName("PHASEANDSERVICELEVEL");
        MonitorValidations.verifyRepairOrderValues(workOrderId, repairOrderDto);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);

        EditOrderSteps.switchToParts();
        EditOrderSteps.openServiceMenu(serviceData1);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.PROBLEM);
        WizardScreenSteps.saveAction();
        MonitorValidations.verifyRepairOrderValues(workOrderId, repairOrderDto);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        EditOrderSteps.openServiceMenu(serviceData2);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceData2);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.SKIPPED);
        WizardScreenSteps.saveAction();
        repairOrderDto.setCompletePercentage("50%");
        repairOrderDto.setPhaseName("PHASEANDSERVICELEVEL");
        MonitorValidations.verifyRepairOrderValues(workOrderId, repairOrderDto);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);

        EditOrderSteps.openServiceMenu(serviceData3);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        EditOrderSteps.openServiceMenu(serviceData3);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.SKIPPED);
        WizardScreenSteps.saveAction();
        repairOrderDto.setCompletePercentage("100%");
        repairOrderDto.setPhaseName("PHASEANDSERVICELEVEL");
        MonitorValidations.verifyRepairOrderValues(workOrderId, repairOrderDto);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);

        MonitorSteps.clickQuickActionsButton();
        MenuValidations.menuItemShouldBeVisible(MenuItems.SCAN, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.FOCUS_MODE, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.TIME_REPORT, true);
        MenuValidations.menuItemShouldBeVisible(MenuItems.HOME, true);
        MenuSteps.closeMenu();
        EditOrderSteps.openPhaseMenu(workOrderData.getMonitoring().getOrderPhaseDto());
        MenuSteps.selectMenuItem(MenuItems.CHANGE_STATUS);
        MenuSteps.selectStatus(ServiceStatus.REFUSED);

        PhaseScreenValidations.validatePhaseStatus(workOrderData.getMonitoring().getOrderPhaseDto(), OrderPhaseStatuses.REFUSED);
        WizardScreenSteps.saveAction();
        repairOrderDto.setCompletePercentage("0%");
        repairOrderDto.setPhaseName("PHASEANDSERVICELEVEL");

        ScreenNavigationSteps.pressBackButton();
    }
}

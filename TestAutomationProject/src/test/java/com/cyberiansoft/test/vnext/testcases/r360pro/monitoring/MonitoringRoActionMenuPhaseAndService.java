package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.dto.OrderPhaseDto;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.GeneralListValidations;
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class MonitoringRoActionMenuPhaseAndService extends BaseTestClass {
    String workOrderId;
    String inspectionId;

    @BeforeClass(description = "Monitoring Ro Action Menu Phase And Service")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getMonitoringRoActionMenu();
        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        inspectionId = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.approveInspection();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanStartServiceFromRoActionMenuForPhaseAndServiceLevel(String rowID,
                                                                                 String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> serviceDataList = workOrderData.getServicesList();

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(serviceDataList);
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByText(workOrderId);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralListSteps.selectListItems(serviceDataList.stream().map(ServiceData::getServiceName).collect(Collectors.toList()));
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        serviceDataList.forEach(PhaseScreenValidations::validateServiceStatus);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyUserCanCompleteServiceFromRoActionMenuForPhaseAndServiceLevel(String rowID,
                                                                                    String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> serviceDataList = workOrderData.getServicesList();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByText(workOrderId);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralListSteps.selectListItems(serviceDataList.stream().map(ServiceData::getServiceName).collect(Collectors.toList()));
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        serviceDataList.forEach(PhaseScreenValidations::validateServiceStatus);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void verifyPhaseIsCompletedWhenAllServicesCompleted(String rowID,
                                                               String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> serviceDataList = workOrderData.getServicesList();
        OrderPhaseDto orderPhaseDto = workOrderData.getMonitoring().getOrderPhaseDto();
        ServiceData firstService = workOrderData.getServicesList().get(0);
        ServiceData secondService = workOrderData.getServicesList().get(1);

        HomeScreenSteps.openInspections();
        InspectionSteps.openInspectionMenu(inspectionId);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.AUTOMATION_MONITORING);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(serviceDataList);
        workOrderId = WorkOrderSteps.saveWorkOrder();
        ScreenNavigationSteps.pressBackButton();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByText(workOrderId);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralListSteps.selectListItem(firstService.getServiceName());
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralListValidations.elementShouldBePresent(firstService.getServiceName(), false);
        GeneralListValidations.elementShouldBePresent(secondService.getServiceName(), true);
        GeneralListSteps.selectListItem(secondService.getServiceName());
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralListValidations.elementShouldBePresent(orderPhaseDto.getPhaseName(), false);
        GeneralListSteps.selectListItem(firstService.getServiceName());
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenValidations.validatePhaseStatus(orderPhaseDto, ServiceStatus.ACTIVE);
        ScreenNavigationSteps.pressBackButton();
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralListValidations.elementShouldBePresent(firstService.getServiceName(), false);
        GeneralListValidations.elementShouldBePresent(secondService.getServiceName(), true);
        GeneralListSteps.selectListItem(secondService.getServiceName());
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validatePhaseStatus(orderPhaseDto, ServiceStatus.COMPLETED);
        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

}

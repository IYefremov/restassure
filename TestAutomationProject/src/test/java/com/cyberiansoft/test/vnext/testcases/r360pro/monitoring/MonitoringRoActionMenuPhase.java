package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.ServiceData;
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
import com.cyberiansoft.test.vnext.validations.PhaseScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class MonitoringRoActionMenuPhase extends BaseTestClass {
    String workOrderId;
    String inspectionId;

    @BeforeClass(description = "Tech split base test cases")
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
    public void verifyUserCanStartPhaseFromRoActionMenuForPhase(String rowID,
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
        GeneralListSteps.selectListItem(serviceDataList.get(0).getServiceName());
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralListSteps.selectListItem(serviceDataList.get(0).getServiceName());

        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.START);
        GeneralSteps.confirmDialog();
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        PhaseScreenValidations.validateServiceStatus(serviceDataList.get(1));

        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class
            , dependsOnMethods = "verifyUserCanStartPhaseFromRoActionMenuForPhase")
    public void verifyUserCanCompletePhaseOnRoActionScreen(String rowID,
                                                           String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> serviceDataList = workOrderData.getServicesList();
        OrderPhaseDto orderPhaseDto = workOrderData.getMonitoring().getOrderPhaseDto();

        HomeScreenSteps.openMonitor();
        SearchSteps.searchByText(workOrderId);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.COMPLETE);
        GeneralSteps.confirmDialog();
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.EDIT);
        MonitorSteps.toggleFocusMode(MenuItems.FOCUS_MODE_ON);
        PhaseScreenValidations.validateServiceStatus(serviceDataList.get(0));
        PhaseScreenValidations.validatePhaseStatus(orderPhaseDto);

        ScreenNavigationSteps.pressBackButton();
        ScreenNavigationSteps.pressBackButton();
    }
}

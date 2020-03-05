package com.cyberiansoft.test.vnext.testcases.r360pro.monitoring;

import com.cyberiansoft.test.dataclasses.WorkOrderData;
import com.cyberiansoft.test.dataprovider.JSONDataProvider;
import com.cyberiansoft.test.dataprovider.JSonDataParser;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.data.r360pro.VNextProTestCasesDataPaths;
import com.cyberiansoft.test.vnext.enums.ScreenType;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.interactions.VehicleInfoScreenInteractions;
import com.cyberiansoft.test.vnext.steps.*;
import com.cyberiansoft.test.vnext.steps.monitoring.MonitorSteps;
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.cyberiansoft.test.vnext.validations.InformationDialogValidations;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.WorkOrdersScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VNextChangeDepartment extends BaseTestClass {

    @BeforeClass(description = "Team Monitoring Common Filters")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getChangeDepartment();

    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void testUserCanChangeDepartment(String rowID,
                                          String description, JSONObject testData) {
        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);


        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, false);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        MenuValidations.menuItemShouldBeVisible(MenuItems.CREATE_WORK_ORDER, true);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        VehicleInfoScreenInteractions.waitPageLoaded();
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectService(workOrderData.getMoneyServiceData().getServiceName());
        String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderId, true);
        ScreenNavigationSteps.pressBackButton();


        HomeScreenSteps.openMonitor();
        MonitorSteps.changeLocation("automationMonitoring");
        SearchSteps.clearAllFilters();
        SearchSteps.textSearch(workOrderId);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.START_RO);
        InformationDialogValidations.clickStartAndVerifyMessage(VNextAlertMessages.START_REPAIR_ORDER);
        MonitorSteps.openItem(workOrderId);
        MenuSteps.selectMenuItem(MenuItems.CHANGE_DEPARTMENT);
        MonitorSteps.changeDepartment("Default");
        ScreenNavigationSteps.pressBackButton();
    }

}

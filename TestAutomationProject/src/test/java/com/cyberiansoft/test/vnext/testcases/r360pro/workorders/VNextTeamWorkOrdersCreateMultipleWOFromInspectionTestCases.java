package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

import com.cyberiansoft.test.dataclasses.ServiceData;
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
import com.cyberiansoft.test.vnext.steps.services.AvailableServicesScreenSteps;
import com.cyberiansoft.test.vnext.steps.services.SelectedServicesScreenSteps;
import com.cyberiansoft.test.vnext.testcases.r360pro.BaseTestClass;
import com.cyberiansoft.test.vnext.validations.ListServicesValidations;
import com.cyberiansoft.test.vnext.validations.MenuValidations;
import com.cyberiansoft.test.vnext.validations.WorkOrdersScreenValidations;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamWorkOrdersCreateMultipleWOFromInspectionTestCases extends BaseTestClass {

    @BeforeClass(description="Team Work Orders Create Multiple WO From Inspection Test Cases")
    public void beforeClass() {
        JSONDataProvider.dataFile = VNextProTestCasesDataPaths.getInstance().getCreateMultipleWOFromInspectionTestCasesDataPath();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void userCanSelectUnselectServicesInWorkorder(String rowID,
                                                      String description, JSONObject testData) {

        WorkOrderData workOrderData = JSonDataParser.getTestDataFromJson(testData, WorkOrderData.class);
        List<ServiceData> summaryServiceList = new ArrayList<>();
        summaryServiceList.addAll(workOrderData.getInspectionData().getServicesList());
        summaryServiceList.addAll(workOrderData.getServicesList());

        HomeScreenSteps.openCreateMyInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        AvailableServicesScreenSteps.selectServices(workOrderData.getInspectionData().getServicesList());
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        WizardScreenSteps.navigateToWizardScreen(ScreenType.SERVICES);
        ListServicesValidations.verifySelectedServices(workOrderData.getInspectionData().getServicesList());
        AvailableServicesScreenSteps.selectServices(workOrderData.getServicesList());
        ListServicesValidations.verifySelectedServices(summaryServiceList);
        SelectedServicesScreenSteps.unselectServices(workOrderData.getInspectionData().getServicesList());
        ListServicesValidations.verifySelectedServices(workOrderData.getServicesList());
        String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrdersScreenValidations.validateWorkOrderExists(workOrderId, true);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void userCanCreateWoFromApprovedInspection(String rowID,
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
    }
}

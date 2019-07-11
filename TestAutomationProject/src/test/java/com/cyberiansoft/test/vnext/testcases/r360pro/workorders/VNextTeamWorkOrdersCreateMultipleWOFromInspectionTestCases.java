package com.cyberiansoft.test.vnext.testcases.r360pro.workorders;

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
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextTeamWorkOrdersCreateMultipleWOFromInspectionTestCases extends BaseTestCaseTeamEditionRegistration {

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

        HomeScreenSteps.openCreateNewInspection();
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
        SelectedServicesScreenSteps.verifySelectedServices(workOrderData.getInspectionData().getServicesList());
        AvailableServicesScreenSteps.selectServices(workOrderData.getServicesList());
        SelectedServicesScreenSteps.verifySelectedServices(summaryServiceList);
        SelectedServicesScreenSteps.unselectServices(workOrderData.getInspectionData().getServicesList());
        SelectedServicesScreenSteps.verifySelectedServices(workOrderData.getServicesList());
        String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.workOrderShouldBePresent(workOrderId);
        ScreenNavigationSteps.pressBackButton();
    }

    @Test(dataProvider="fetchData_JSON", dataProviderClass=JSONDataProvider.class)
    public void userCanCreateWoFromApprovedInspection(String rowID,
                                                         String description, JSONObject testData) {
        HomeScreenSteps.openCreateNewInspection();
        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.createWorkOrderMenuItemShouldBeVisible(false);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.createWorkOrderMenuItemShouldBeVisible(true);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.workOrderShouldBePresent(workOrderId);
        ScreenNavigationSteps.pressBackButton();
    }
}

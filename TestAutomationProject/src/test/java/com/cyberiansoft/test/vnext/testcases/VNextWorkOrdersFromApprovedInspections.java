package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.InspectionMenuSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import org.testng.annotations.Test;

public class VNextWorkOrdersFromApprovedInspections extends BaseTestCaseTeamEditionRegistration {
    @Test
    public void userCanCreateWoFromApprovedInspection() {
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
    }
}

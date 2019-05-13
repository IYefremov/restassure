package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.InspectionMenuSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import com.cyberiansoft.test.vnext.testcases.BaseTestCaseTeamEditionRegistration;
import org.testng.annotations.Test;

public class VNextWorkOrdersFromApprovedInspections extends BaseTestCaseTeamEditionRegistration {
    @Test
    public void userCanCreateWoFromApprovedInspection() {
        String inspectionNumber =
                InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.createWorkOrderMenuItemShouldBeVisible(false);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.createWorkOrderMenuItemShouldBeVisible(true);
        InspectionMenuSteps.selectCreateWorkOrder();
        String workOrderId =
                WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        WorkOrderSteps.workOrderShouldBePresent(workOrderId);
    }
}

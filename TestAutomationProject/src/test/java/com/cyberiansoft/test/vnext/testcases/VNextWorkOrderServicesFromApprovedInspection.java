package com.cyberiansoft.test.vnext.testcases;

import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.factories.inspectiontypes.InspectionTypes;
import com.cyberiansoft.test.vnext.factories.workordertypes.WorkOrderTypes;
import com.cyberiansoft.test.vnext.steps.InspectionMenuSteps;
import com.cyberiansoft.test.vnext.steps.InspectionSteps;
import com.cyberiansoft.test.vnext.steps.WorkOrderSteps;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class VNextWorkOrderServicesFromApprovedInspection extends BaseTestCaseTeamEditionRegistration {
    @Test
    public void userCanSelectUnselectServicesInWorkorder() {
        List<ServiceData> inspectionServiceList = getInspectionServiceList();
        List<ServiceData> workOrderServiceList = getWorkOrderServiceList();
        List<ServiceData> summaryServiceList = new ArrayList<>();
        summaryServiceList.addAll(inspectionServiceList);
        summaryServiceList.addAll(workOrderServiceList);

        InspectionSteps.createInspection(testcustomer, InspectionTypes.O_KRAMAR);
        InspectionSteps.openServiceScreen();
        InspectionSteps.selectServices(inspectionServiceList);
        String inspectionNumber = InspectionSteps.saveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.approveInspection();
        InspectionSteps.openInspectionMenu(inspectionNumber);
        InspectionMenuSteps.selectCreateWorkOrder();
        WorkOrderSteps.createWorkOrder(WorkOrderTypes.O_KRAMAR);
        WorkOrderSteps.openServiceScreen();
        WorkOrderSteps.verifySelectedOrders(inspectionServiceList);
        WorkOrderSteps.selectServices(workOrderServiceList);
        WorkOrderSteps.verifySelectedOrders(summaryServiceList);
        WorkOrderSteps.unselectServices(inspectionServiceList);
        WorkOrderSteps.verifySelectedOrders(workOrderServiceList);
        String workOrderId = WorkOrderSteps.saveWorkOrder();
        WorkOrderSteps.workOrderShouldBePresent(workOrderId);
    }

    private List<ServiceData> getInspectionServiceList() {
        ServiceData batteryInstallationService = new ServiceData();
        batteryInstallationService.setServiceName("Battery Installation");
        ServiceData aluminumPanelService = new ServiceData();
        aluminumPanelService.setServiceName("Aluminum Panel");
        List<ServiceData> serviceDataList = new ArrayList<>();
        serviceDataList.add(batteryInstallationService);
        serviceDataList.add(aluminumPanelService);
        return serviceDataList;
    }

    private List<ServiceData> getWorkOrderServiceList() {
        ServiceData batteryInstallationService = new ServiceData();
        batteryInstallationService.setServiceName("Damage Service");
        ServiceData aluminumPanelService = new ServiceData();
        aluminumPanelService.setServiceName("Labor");
        List<ServiceData> serviceDataList = new ArrayList<>();
        serviceDataList.add(batteryInstallationService);
        serviceDataList.add(aluminumPanelService);
        return serviceDataList;
    }
}


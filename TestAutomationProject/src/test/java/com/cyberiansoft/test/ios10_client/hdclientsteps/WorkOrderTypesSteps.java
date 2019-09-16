package com.cyberiansoft.test.ios10_client.hdclientsteps;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typespopups.WorkOrderTypesPopup;
import com.cyberiansoft.test.ios10_client.types.workorderstypes.IWorkOrdersTypes;

public class WorkOrderTypesSteps {

    public static void selectWorkOrderType(IWorkOrdersTypes workOrderType) {
        WorkOrderTypesPopup workOrderTypesPopup = new WorkOrderTypesPopup();
        workOrderTypesPopup.selectWorkOrderType(workOrderType.getWorkOrderTypeName());
    }
}

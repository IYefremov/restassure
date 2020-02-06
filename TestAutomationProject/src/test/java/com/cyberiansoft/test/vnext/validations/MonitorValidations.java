package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class MonitorValidations {

    public static void verifyOrderFlag(String workOrderId, RepairOrderFlag repairOrderFlag) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        Assert.assertEquals(
                repairOrderScreen.getRepairOrderElement(workOrderId).getRepairOrderFlag(),
                repairOrderFlag);
    }

    public static void verifyRepairOrderValues(String repairOrderId, RepairOrderDto expectedRoValues) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRepairOrderList(), true);
        Assert.assertEquals(repairOrderScreen.getRepairOrderElement(repairOrderId).getRepairOrderDto(),
                expectedRoValues);
    }

    public static void verifyRepairOrderPresentInList(String repairOrderId) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
        Assert.assertNotNull(repairOrderScreen.getRepairOrderElement(repairOrderId));
    }
}

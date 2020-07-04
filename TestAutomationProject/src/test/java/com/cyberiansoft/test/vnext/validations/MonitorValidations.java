package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderBackGroundColors;
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

    public static void verifyOrderBackGroundColor(String workOrderId, RepairOrderBackGroundColors repairOrderBackGroundColor) {

        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        Assert.assertEquals(
                repairOrderScreen.getRepairOrderElement(workOrderId).getRootElement().getCssValue("background-color"),
                repairOrderBackGroundColor.getBackGroundColor());
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

    public static void verifyRepairOrdersScreenIsOpenedWithOrders() {

        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        Assert.assertTrue(repairOrderScreen.getRepairOrderList().isDisplayed(),
                "Repair orders screen hasn't been opened");
        Assert.assertTrue(repairOrderScreen.getRepairOrderListElements().size() > 0,
                "Repair orders records have not been displayed");
    }

    public static void verifySearchMaskContainsSearchValue(String searchFilterValue) {

        Assert.assertTrue(new RepairOrderScreen().getSearchFiltersValue().getText().contains(searchFilterValue),
                "Search mask hasn't contained vale " + searchFilterValue);
    }
}

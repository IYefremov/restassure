package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.enums.OrderPriority;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.screens.monitoring.CommonFilterScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

public class MonitorSearchValidations {

    public static void validateTimeFrameFromValue(String expectedDateValue) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getDateFrom().getRootElement().getAttribute("value"),
                expectedDateValue);
    }

    public static void validateTimeFrameToValue(String expectedDateValue) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getDateTo().getRootElement().getAttribute("value"),
                expectedDateValue);
    }

    public static void validateCommonFilterPanelVisible(boolean isVisible) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        if (isVisible)
            Assert.assertTrue(repairOrderScreen.getSearchFiltersPanel().isDisplayed());
        else {
            WaitUtils.elementShouldBeVisible(repairOrderScreen.getSearchFiltersPanel(), false);
            Assert.assertFalse(repairOrderScreen.getSearchFiltersPanel().isDisplayed());
        }
    }

    public static void validatePriorityListElementNumber(int expectedNumber) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getPriority().getListElementsNumber(), expectedNumber);
    }

    public static void validatePriorityValue(OrderPriority orderPriority) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getPriority().getRootElement().getAttribute("value"),
                StringUtils.capitalize(orderPriority.getValue()));
    }

    public static void validateStatusValue(RepairOrderStatus repairOrderStatus) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getStatus().getRootElement().getAttribute("value"),
                repairOrderStatus.getStatusString());
    }

    public static void validatePhaseValue(String phaseName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getPhase().getRootElement().getAttribute("value"),
                phaseName);
    }

    public static void validateDepartmentValue(String departmentName) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getDepartment().getRootElement().getAttribute("value"),
                departmentName);
    }

    public static void validateFlagValue(RepairOrderFlag repairOrderFlag) {
        CommonFilterScreen commonFilterScreen = new CommonFilterScreen();
        Assert.assertEquals(commonFilterScreen.getFlag().getRootElement().getAttribute("value").toLowerCase(),
                repairOrderFlag.name().toLowerCase());
    }
}

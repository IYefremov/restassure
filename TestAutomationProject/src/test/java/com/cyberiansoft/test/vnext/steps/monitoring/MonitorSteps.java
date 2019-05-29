package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectLocationScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.RepairOrderListElement;
import org.testng.Assert;

public class MonitorSteps {
    public static void verifyRepairOrderPresentInList(String repairOrderId) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
        Assert.assertNotNull(repairOrderScreen.getRepairOrderElement(repairOrderId));
    }

    public static void changeLocation(String locationPartialName) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        SelectLocationScreen selectLocationScreen = new SelectLocationScreen();

        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
        repairOrderScreen.openChangeLocationPage();
        WaitUtils.elementShouldBeVisible(selectLocationScreen.getRootElement(), true);
        WaitUtils.getGeneralFluentWait().until((webdriver) -> selectLocationScreen.getLocationList().size() > 0);
        selectLocationScreen.selectLocationByText(locationPartialName);
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void clearAllFilters() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        repairOrderScreen.getActiveFilterslabel().clearAllFilters();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void verifyRepairOrderValues(String repairOrderId, RepairOrderDto expectedRoValues) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        Assert.assertEquals(repairOrderScreen.getRepairOrderElement(repairOrderId).getRepairOrderDto(),
                expectedRoValues);
    }

    public static void openSearchFilters() {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.waitUntilElementIsClickable(repairOrderScreen.getSearchButton());
        repairOrderScreen.openSearchMenu();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getCommonFiltersToggle(), true);
        repairOrderScreen.openCommonFilters();
    }

    public static void openMenu(String workOrderId) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        RepairOrderListElement repairOrder = repairOrderScreen.getRepairOrderElement(workOrderId);
        WaitUtils.elementShouldBeVisible(repairOrder.getRootElement(), true);
        repairOrder.openMenu();
    }
}

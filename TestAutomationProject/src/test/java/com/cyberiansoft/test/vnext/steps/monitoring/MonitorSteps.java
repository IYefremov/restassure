package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectLocationScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.RepairOrderListElement;
import org.testng.Assert;

public class MonitorSteps {

    public static void changeLocation(String locationPartialName) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        SelectLocationScreen selectLocationScreen = new SelectLocationScreen();

        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
        repairOrderScreen.openChangeLocationPage();
        WaitUtils.elementShouldBeVisible(selectLocationScreen.getRootElement(), true);
        WaitUtils.getGeneralFluentWait().until((webdriver) -> selectLocationScreen.getLocationList().size() > 0);
        BaseUtils.waitABit(1000);
        selectLocationScreen.selectLocationByText(locationPartialName);
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void openItem(String workOrderId) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.getGeneralFluentWait().until((webdriver) -> repairOrderScreen.getRepairOrderListElements().size() > 0);
        RepairOrderListElement repairOrder = repairOrderScreen.getRepairOrderElement(workOrderId);
        WaitUtils.elementShouldBeVisible(repairOrder.getRootElement(), true);
        repairOrder.openMenu();
    }

    public static void setRepairOrderFlag(String workOrderId, RepairOrderFlag flag) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
        WaitUtils.getGeneralFluentWait().until((webdriver) -> repairOrderScreen.getRepairOrderListElements().size() > 0);
        repairOrderScreen.getRepairOrderElement(workOrderId).selectStatus(flag);
    }

    public static void editOrder(String workOrderId) {
        HomeScreenSteps.openMonitor();
        SearchSteps.searchByTextAndStatus(workOrderId, RepairOrderStatus.All);
        MonitorSteps.openItem(workOrderId);
    }

    public static void toggleFocusMode(MenuItems focusMode) {
        PhasesScreen phasesScreen = new PhasesScreen();
        BaseUtils.waitABit(2000);
        WaitUtils.click(phasesScreen.getPhasesMenuButton());
        MenuSteps.selectMenuItem(focusMode);
    }
}

package com.cyberiansoft.test.vnext.steps.monitoring;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.enums.RepairOrderStatus;
import com.cyberiansoft.test.vnext.screens.monitoring.DepartmentScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.PhasesScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.RepairOrderScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.SelectLocationScreen;
import com.cyberiansoft.test.vnext.steps.HomeScreenSteps;
import com.cyberiansoft.test.vnext.steps.MenuSteps;
import com.cyberiansoft.test.vnext.steps.SearchSteps;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.RepairOrderListElement;
import org.openqa.selenium.By;

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

    public static void changeDepartment(String department) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        DepartmentScreen departmentScreen = new DepartmentScreen();

        WaitUtils.elementShouldBeVisible(departmentScreen.getRootElement(), true);
        WaitUtils.getGeneralFluentWait().until((webdriver) -> departmentScreen.getDepartmentList().size() > 0);
        BaseUtils.waitABit(1000);
        departmentScreen.selectDepartmentByText(department);
        WaitUtils.elementShouldBeVisible(repairOrderScreen.getRootElement(), true);
    }

    public static void openItem(String workOrderId) {
        RepairOrderScreen repairOrderScreen = new RepairOrderScreen();
        WaitUtils.getGeneralFluentWait().until((webdriver) -> repairOrderScreen.getRepairOrderListElements().size() > 0);
        BaseUtils.waitABit(1000);
        RepairOrderListElement repairOrder = repairOrderScreen.getRepairOrderElement(workOrderId);
        WaitUtils.waitUntilElementIsClickable(repairOrder.getRootElement());
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
        clickQuickActionsButton();
        MenuSteps.selectMenuItem(focusMode);
        WaitUtils.waitUntilElementInvisible(By.xpath("//*[@data-autotests-id='preloader']"));
    }

    public static void clickQuickActionsButton() {
        PhasesScreen phasesScreen = new PhasesScreen();
        BaseUtils.waitABit(2000);
        WaitUtils.click(phasesScreen.getPhasesMenuButton());
        BaseUtils.waitABit(1000);
    }
}

package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.StatusSelectScreen;
import com.cyberiansoft.test.vnext.screens.menuscreens.GeneralMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class MenuSteps {
    public static void selectMenuItem(MenuItems menuItem) {
        BaseUtils.waitABit(3000);
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.elementShouldBeVisible(repairOrderMenuScreen.getRootElement(), true);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            repairOrderMenuScreen.selectMenuItem(menuItem);
            return true;
        });
    }

    public static void selectStatus(ServiceStatus serviceStatus) {
        StatusSelectScreen statusSelectScreen = new StatusSelectScreen();
        BaseUtils.waitABit(3000);
        WaitUtils.elementShouldBeVisible(statusSelectScreen.getStatusItemByText(serviceStatus.getStatus()), true);
        WaitUtils.waitUntilElementIsClickable(statusSelectScreen.getRootElement());
        WaitUtils.click(statusSelectScreen.getStatusItemByText(serviceStatus.getStatus()));
    }

    public static void closeMenu() {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.click(repairOrderMenuScreen.getCloseButton());
    }
}

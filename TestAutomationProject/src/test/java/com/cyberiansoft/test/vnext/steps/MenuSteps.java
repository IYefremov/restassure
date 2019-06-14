package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.monitoring.GeneralMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;

public class MenuSteps {
    public static void selectMenuItem(MenuItems menuItem) {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> repairOrderMenuScreen.getMenuItems().size() > 0);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            repairOrderMenuScreen.selectMenuItem(menuItem);
            return true;
        });
    }
}

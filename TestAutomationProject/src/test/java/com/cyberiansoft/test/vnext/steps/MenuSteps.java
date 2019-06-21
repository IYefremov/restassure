package com.cyberiansoft.test.vnext.steps;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.dataclasses.ServiceStatus;
import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.StatusSelectScreen;
import com.cyberiansoft.test.vnext.screens.monitoring.GeneralMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.jsoup.Connection;

public class MenuSteps {
    public static void selectMenuItem(MenuItems menuItem) {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.getGeneralFluentWait().until(driver -> repairOrderMenuScreen.getMenuItems().size() > 0);
        WaitUtils.getGeneralFluentWait().until(driver -> {
            repairOrderMenuScreen.selectMenuItem(menuItem);
            return true;
        });
    }

    public static void selectStatus(ServiceStatus serviceStatus) {
        StatusSelectScreen statusSelectScreen = new StatusSelectScreen();
        BaseUtils.waitABit(2000);
        WaitUtils.click(statusSelectScreen.getStatusItemByText(serviceStatus.getStatus()));
    }
}

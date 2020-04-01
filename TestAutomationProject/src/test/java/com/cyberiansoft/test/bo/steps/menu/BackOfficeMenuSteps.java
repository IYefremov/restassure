package com.cyberiansoft.test.bo.steps.menu;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.enums.menu.Menu;
import com.cyberiansoft.test.bo.enums.menu.SubMenu;
import com.cyberiansoft.test.bo.pageobjects.webpages.BackOfficeHeaderPanel;
import org.openqa.selenium.By;

public class BackOfficeMenuSteps {

    public static void open(Menu menu, SubMenu subMenu) {
        openTab(menu);
        openSubMenu(subMenu);
    }

    private static void openSubMenu(SubMenu subMenu) {
        Utils.clickElement(By.xpath("//span[@class='navLinkTitle' and text()='" + subMenu.getValue() + "']"));
        WaitUtilsWebDriver.waitUntilTitleContainsIgnoringException(subMenu.getValue(), 4);
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitABit(1000);
    }

    private static void openTab(Menu tab) {
        Utils.clickElement(new BackOfficeHeaderPanel().getTab(tab.getValue()));
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        if (tab.getValue().equals(Menu.TIMESHEETS.getValue())) {
            WaitUtilsWebDriver.waitUntilTitleContains("TimeSheets");
        } else if (tab.getValue().equals(Menu.FAVORITES.getValue())) {
            WaitUtilsWebDriver.waitUntilTitleIs("ReconPro");
        } else {
            WaitUtilsWebDriver.waitUntilTitleContains(tab.getValue());
        }
    }
}

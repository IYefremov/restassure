package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.interactions.MenuScreenInteractions;
import com.cyberiansoft.test.vnext.screens.menuscreens.GeneralMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.testng.Assert;

public class MenuValidations {
    public static void menuItemShouldBeVisible(MenuItems menuItem, Boolean shouldBeVisible) {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(repairOrderMenuScreen.getMenuItems(), 0);
        WaitUtils.elementShouldBeVisible(MenuScreenInteractions.getMenuItem(menuItem), shouldBeVisible);
    }

    public static void menuItemShouldBeEnabled(MenuItems menuItem, Boolean shouldBeVisible) {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(repairOrderMenuScreen.getMenuItems(), 0);
        Assert.assertEquals((boolean) MenuScreenInteractions.getMenuItem(menuItem).getAttribute("class").contains("disabled-action"), !shouldBeVisible);
    }
}

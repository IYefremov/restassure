package com.cyberiansoft.test.vnext.validations;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.interactions.MenuScreenInteractions;
import com.cyberiansoft.test.vnext.screens.menuscreens.GeneralMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class MenuValidations {
    public static void menuItemShouldBeVisible(MenuItems menuItem, Boolean shouldBeVisible) {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(repairOrderMenuScreen.getMenuItems(), 1);
        if (shouldBeVisible) {
            WaitUtils.getGeneralFluentWait().until(__ -> MenuScreenInteractions.getMenuItem(menuItem).isDisplayed());
            Assert.assertTrue(MenuScreenInteractions.getMenuItem(menuItem).isDisplayed(),
                    menuItem.getMenuItemDataName() + "menu item hasn't been displayed");
        } else
            Assert.assertNull(MenuScreenInteractions.getMenuItem(menuItem));
    }

    public static void menuItemShouldBeEnabled(MenuItems menuItem, Boolean shouldBeVisible) {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(repairOrderMenuScreen.getMenuItems(), 0);
        WaitUtils.waitUntilElementIsClickable(MenuScreenInteractions.getMenuItem(menuItem));
        if (shouldBeVisible)
            Assert.assertEquals((boolean) MenuScreenInteractions.getMenuItem(menuItem).getAttribute("class").contains("disabled-action"), !shouldBeVisible);
        else {
            WaitUtils.getGeneralFluentWait().until(ExpectedConditions.attributeContains(MenuScreenInteractions.getMenuItem(menuItem), "class", "disabled-action"));
            Assert.assertEquals((boolean) MenuScreenInteractions.getMenuItem(menuItem).getAttribute("class").contains("disabled-action"), !shouldBeVisible);
        }
    }

    public static void verifyMenuItemIsVisible(String menuItem) {

        WaitUtils.elementShouldBeVisible(new GeneralMenuScreen().getRootElement(), true);
        Assert.assertTrue(MenuScreenInteractions.getMenuItem(menuItem).isDisplayed(), menuItem + " Menu item hasn't been displayed");
    }

    public static void verifyMenuScreenIsOpened() {

        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        Assert.assertTrue(repairOrderMenuScreen.getRootElement().isDisplayed(),
                "Action menu hasn't been displayed");
        Assert.assertTrue(repairOrderMenuScreen.getMenuItems().size() > 0,
                "Action menu buttons haven't been displayed");
    }
}

package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.menuscreens.GeneralMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebElement;

public class MenuScreenInteractions {
    public static WebElement getMenuItem(MenuItems menuItem) {
        GeneralMenuScreen repairOrderMenuScreen = new GeneralMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(repairOrderMenuScreen.getMenuItems(), 0);
        return repairOrderMenuScreen.getMenuItems().stream()
                .filter(WebElement::isDisplayed)
                .filter((element) ->
                        element.getText().equals(menuItem.getMenuItemDataName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu element not found " + menuItem.getMenuItemDataName()));
    }
}

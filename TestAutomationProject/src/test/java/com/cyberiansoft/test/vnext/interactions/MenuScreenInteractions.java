package com.cyberiansoft.test.vnext.interactions;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.menuscreens.GeneralMenuScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.WebElement;

public class MenuScreenInteractions {
    public static WebElement getMenuItem(MenuItems menuItem) {
        GeneralMenuScreen menuScreen = new GeneralMenuScreen();
        WaitUtils.collectionSizeIsGreaterThan(menuScreen.getMenuItems(), 0);
        return WaitUtils.getGeneralFluentWait(10, 300).until(driver -> menuScreen.getMenuItems().stream()
                .filter(WebElement::isDisplayed)
                .filter((element) ->
                        element.getText().equals(menuItem.getMenuItemDataName()))
                .findFirst()).orElse(null);
    }
}

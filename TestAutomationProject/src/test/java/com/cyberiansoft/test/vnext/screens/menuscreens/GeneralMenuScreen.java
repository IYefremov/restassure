package com.cyberiansoft.test.vnext.screens.menuscreens;

import com.cyberiansoft.test.enums.MenuItems;
import com.cyberiansoft.test.vnext.screens.VNextBaseScreen;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class GeneralMenuScreen extends VNextBaseScreen {
    @FindBy(xpath = "//div[@class='actions-menu-layout opened']")
    private WebElement rootElement;

    @FindBy(xpath = "//*[contains(@class,'action-tile')]")
    private List<WebElement> menuItems;

    @FindBy(xpath = "//*[contains(@class,'actions-menu-close')]")
    private WebElement closeButton;

    public void selectMenuItem(MenuItems menuItem) {
        WaitUtils.click(menuItems.stream()
                .filter(WebElement::isDisplayed)
                .filter((element) ->
                        element.getText()
                                .trim().equals(menuItem.getMenuItemDataName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu element not found " + menuItem.getMenuItemDataName()))
        );
    }
}

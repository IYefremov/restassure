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
    @FindBy(xpath = "//a[contains(@class,'action-item')]")
    private List<WebElement> menuItems;

    @FindBy(xpath = "//div[contains(@class,'close-actions')]")
    private WebElement closeButton;

    public void selectMenuItem(MenuItems menuItem) {
        WaitUtils.click(menuItems.stream()
                .filter(WebElement::isDisplayed)
                .filter((element) ->
                        element.getText().trim().equals(menuItem.getMenuItemDataName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu element not found " + menuItem.getMenuItemDataName()))
        );
    }
}

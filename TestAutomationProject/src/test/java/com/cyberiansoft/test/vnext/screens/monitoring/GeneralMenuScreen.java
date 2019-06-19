package com.cyberiansoft.test.vnext.screens.monitoring;

import com.cyberiansoft.test.enums.MenuItems;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class GeneralMenuScreen extends MonitorScreen {
    @FindBy(xpath = "//a[contains(@class,\"action-item\")]")
    private List<WebElement> menuItems;

    public void selectMenuItem(MenuItems menuItem) {
        menuItems.stream()
                .filter(WebElement::isDisplayed)
                .filter((element) ->
                        element.getText().equals(menuItem.getMenuItemDataName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu element not found " + menuItem.getMenuItemDataName()))
                .click();
    }
}

package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.WebElement;

public class Button implements IWebElement {
    public WebElement rootElement;

    public Button(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void click() {
        WaitUtils.getGeneralFluentWait(5, 500).until(driver -> {
            rootElement.click();
            return true;
        });
    }

    public boolean isDisplayed() {
        return rootElement.isDisplayed();
    }
}

package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.WebElement;

public class Button implements IWebElement {
    private WebElement button;

    public Button(WebElement button) {
        this.button = button;
    }

    public void click() {
        WaitUtils.getGeneralFluentWait(5, 500).until(driver -> {
            button.click();
            return true;
        });
    }

    public boolean isDisplayed() {
        return button.isDisplayed();
    }
}

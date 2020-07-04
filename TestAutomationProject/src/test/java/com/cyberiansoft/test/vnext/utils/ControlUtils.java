package com.cyberiansoft.test.vnext.utils;

import org.openqa.selenium.WebElement;

public class ControlUtils {
    public static void setValue(WebElement element, String value) {
        WaitUtils.getGeneralFluentWait().until(driver -> {
            WaitUtils.elementShouldBeVisible(element, true);
            element.clear();
            element.sendKeys(value);
            return true;
        });
    }

    public static String getElementValue(WebElement element) {
        WaitUtils.elementShouldBeVisible(element, true);
        return element.getAttribute("value");
    }
}

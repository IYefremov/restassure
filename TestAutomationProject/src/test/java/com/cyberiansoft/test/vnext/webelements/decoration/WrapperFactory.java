package com.cyberiansoft.test.vnext.webelements.decoration;

import org.openqa.selenium.WebElement;

//TODO: COMPLETE REFACTORING NEEDED!!!
public class WrapperFactory {
    public static IWebElement createInstance(Class<IWebElement> clazz, WebElement element) {
        try {
            return clazz.getConstructor(WebElement.class)
                    .newInstance(element);
        } catch (Exception e) {
            throw new AssertionError(
                    "WebElement can't be represented as " + clazz
            );
        }
    }
}

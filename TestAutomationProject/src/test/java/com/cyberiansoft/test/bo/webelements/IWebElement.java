package com.cyberiansoft.test.bo.webelements;

import org.openqa.selenium.WebElement;

public interface IWebElement {
    boolean isDisplayed();
    WebElement getWrappedElement();
}

package com.cyberiansoft.test.bo.containers;

import org.openqa.selenium.WebElement;

import com.cyberiansoft.test.bo.webelements.IWebElement;

public interface IWebContainer extends IWebElement {
    void init(WebElement wrappedElement);
}
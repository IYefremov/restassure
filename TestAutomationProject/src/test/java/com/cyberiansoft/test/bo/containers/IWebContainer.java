package com.cyberiansoft.test.bo.containers;

import com.cyberiansoft.test.bo.webelements.IWebElement;
import org.openqa.selenium.WebElement;

public interface IWebContainer extends IWebElement {
    void init(WebElement wrappedElement);
}
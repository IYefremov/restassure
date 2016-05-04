package com.cyberiansoft.test.bo.webelements.impl;

import org.openqa.selenium.WebElement;

import com.cyberiansoft.test.bo.webelements.IWebElement;

abstract class AbstractWebElement implements IWebElement {
    protected final WebElement wrappedElement;

    protected AbstractWebElement(final WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    @Override
    public boolean isDisplayed() {
        return wrappedElement.isDisplayed();
    }
    
    @Override
    public WebElement getWrappedElement() {
        return wrappedElement;
    }
}
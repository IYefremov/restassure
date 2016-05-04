package com.cyberiansoft.test.bo.containers;

import org.openqa.selenium.WebElement;


public abstract class AbstractContainer implements IWebContainer {
    private WebElement wrappedElement;

    @Override
    public final void init(final WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    @Override
    public boolean isDisplayed() {
        return wrappedElement.isDisplayed();
    }
}
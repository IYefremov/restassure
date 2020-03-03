package com.cyberiansoft.test.bo.webelements.impl;

import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.WebElement;

public class TextFieldImpl extends AbstractWebElement implements TextField {
    public TextFieldImpl(final WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public void typeValue(final String value) {
        wrappedElement.sendKeys(value);
    }

    @Override
    public void clear() {
        wrappedElement.clear();
    }

    @Override
    public void clearAndType(final String value) {
        clear();
        typeValue(value);
    }
    
    @Override
    public String getValue() {
    	return wrappedElement.getAttribute("value");
    }
    
    @Override
    public void click() {
        wrappedElement.click();
    }

    @Override
    public void sendKeys(CharSequence... var1) {
        wrappedElement.sendKeys(var1);
    }
}

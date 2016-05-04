package com.cyberiansoft.test.bo.webelements.impl;

import org.openqa.selenium.WebElement;

import com.cyberiansoft.test.bo.webelements.ComboBox;

public class ComboBoxImpl extends AbstractWebElement implements ComboBox {

	public ComboBoxImpl(final WebElement wrappedElement) {
        super(wrappedElement);
    }
	
	@Override
    public void click() {
        wrappedElement.click();
    }
	
	@Override
    public String getSelectedValue() {
    	return wrappedElement.getAttribute("value");
    }
}

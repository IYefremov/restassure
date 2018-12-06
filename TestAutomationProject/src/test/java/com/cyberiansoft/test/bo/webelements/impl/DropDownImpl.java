package com.cyberiansoft.test.bo.webelements.impl;

import com.cyberiansoft.test.bo.webelements.DropDown;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class DropDownImpl extends AbstractWebElement implements DropDown {

	public DropDownImpl(final WebElement wrappedElement) {
        super(wrappedElement);
    }
	
	@Override
    public void selectByVisibleText(String value) {
		List<WebElement> options = wrappedElement.findElements(By.tagName("li"));
        try {
            new WebDriverWait(DriverBuilder.getInstance().getDriver(), 20)
                    .until(ExpectedConditions.visibilityOfAllElements(options));
        } catch (Exception e) {
            e.printStackTrace();
        }
        options.stream().filter(o -> o.getText().equals(value)).findFirst().ifPresent(WebElement::click);
    }
}

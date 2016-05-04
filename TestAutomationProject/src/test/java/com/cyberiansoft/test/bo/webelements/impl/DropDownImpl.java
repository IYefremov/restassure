package com.cyberiansoft.test.bo.webelements.impl;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.waitABit;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.DropDown;

public class DropDownImpl extends AbstractWebElement implements DropDown {

	public DropDownImpl(final WebElement wrappedElement) {
        super(wrappedElement);
    }
	
	@Override
    public void selectByVisibleText(String value) {
		//wrappedElement.findElement(By.xpath("./div/ul/li[text()='" + value + "']")).click();
		boolean valuefinded = false;
		List<WebElement> options = wrappedElement.findElements(By.tagName("li"));
		for (WebElement option : options) {
	        if (value.equals(option.getText())) {
	            click(option);
	            valuefinded = true;
	            break;
	        }
	    }
		if (!valuefinded) {
			Assert.assertFalse(false, "Can't find combobox " + value + " value");
		}
    }
}

package com.cyberiansoft.test.bo.webelements;

import org.openqa.selenium.WebElement;


public interface WebElementFactory {
	 <E extends IWebElement> E create(Class<E> elementClass, WebElement wrappedElement);
}

package com.cyberiansoft.test.bo.containers;

import org.openqa.selenium.WebElement;

public interface ContainerFactory {
	<C extends IWebContainer> C create(Class<C> containerClass, WebElement wrappedElement);
}

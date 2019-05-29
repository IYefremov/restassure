package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ListPicker implements IWebElement {
    private WebElement rootElement;
    private String elementsPageSelector = "//div[@data-page=\"filters-values-list\"]";
    private String elementsLocator = "//div[@action=\"select-item\"]";

    public ListPicker(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void selectOption(String elementName) {
        rootElement.click();
        WaitUtils.getGeneralFluentWait().until((webdriver) -> webdriver.findElements(By.xpath(elementsLocator)).size() > 0);
        DriverBuilder.getInstance().getAppiumDriver().findElements(By.xpath(elementsLocator))
                .stream()
                .filter(element -> element.getText().contains(elementName))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("element not found in list " + elementName))
                .click();
    }
}

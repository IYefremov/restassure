package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.driverutils.ChromeDriverProvider;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class ListPicker implements IWebElement {
    private WebElement rootElement;
    private String elementsPageSelector = "//div[@data-page=\"filters-values-list\"]";
    private String elementsLocator = "//*[@action=\"select-item\"]";

    public ListPicker(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void selectOption(String elementName) {
        WaitUtils.click(rootElement);
        WaitUtils.getGeneralFluentWait().until((webdriver) -> webdriver.findElements(By.xpath(elementsLocator)).size() > 0);
        selectListElement(elementName);
    }

    public void selectListElement(String elementName) {
        ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(By.xpath(elementsLocator))
                .stream()
                .filter(element -> element.getText().toLowerCase().contains(elementName.toLowerCase()))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("element not found in list " + elementName))
                .click();
    }

    public int getListElementsNumber() {
        return ChromeDriverProvider.INSTANCE.getMobileChromeDriver().findElements(By.xpath(elementsLocator)).size();
    }
}

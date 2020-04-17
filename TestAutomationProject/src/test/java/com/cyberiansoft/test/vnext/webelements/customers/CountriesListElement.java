package com.cyberiansoft.test.vnext.webelements.customers;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class CountriesListElement implements IWebElement {
    private WebElement rootElement;
    private String nameFieldLocator = ".//div[@class='simple-item-main-content']";

    public CountriesListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getCountryName() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(nameFieldLocator)).getText().trim());
    }
}

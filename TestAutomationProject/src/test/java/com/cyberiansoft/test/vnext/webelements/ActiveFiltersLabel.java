package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ActiveFiltersLabel implements IWebElement {
    private WebElement rootElement;
    private String clearFilterButton = "//div[@class=\"searchlist-filters-values-remove\"]";

    public ActiveFiltersLabel(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void clearAllFilters() {
        rootElement.findElement(By.xpath(clearFilterButton)).click();
    }
}

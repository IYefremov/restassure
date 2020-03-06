package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ActiveFiltersLabel implements IWebElement {
    private WebElement rootElement;
    private String clearFilterButton = "//div[contains(@class, 'common-filters-clear')]";

    public ActiveFiltersLabel(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void clearAllFilters() {
        WaitUtils.click(rootElement.findElement(By.xpath(clearFilterButton)));
    }
}

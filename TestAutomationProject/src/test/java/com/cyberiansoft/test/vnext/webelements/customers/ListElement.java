package com.cyberiansoft.test.vnext.webelements.customers;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class ListElement implements IWebElement {
    private WebElement rootElement;
    private String recordTextLocator = ".//*[@class='simple-item-main-content' or contains(@class,'list-item')]";

    public ListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getRecordText() {
        return WaitUtils.getGeneralFluentWait().until(driver -> rootElement.findElement(By.xpath(recordTextLocator)).getText().trim());
    }
}

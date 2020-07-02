package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PaintCodeListItem implements IWebElement {
    private WebElement rootElement;
    private String colorValue = ".//div[contains(@class,'simple-item-main-content')]";
    private String paintCodeValue = ".//div[contains(@class,'simple-item-inner-content')]";

    public PaintCodeListItem(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public void selectColor() {
        rootElement.click();
    }

    public String getColorValue() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(colorValue)).getText().trim());
    }

    public String getPaintCodeValue() {
        return WaitUtils.getGeneralFluentWait().until((driver) -> rootElement.findElement(By.xpath(paintCodeValue)).getText().trim());
    }
}

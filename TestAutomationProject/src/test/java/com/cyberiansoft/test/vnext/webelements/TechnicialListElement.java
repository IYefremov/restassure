package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TechnicialListElement implements IWebElement {
    private WebElement rootElement;
    private String selectionCheckboxLocator = ".//input[@action='check-item']";
    private String titleLocator = ".//div[@class='checkbox-item-title']";
    private String amountLocator = "//input[@data-name='Amount']";

    public TechnicialListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public Boolean isElementChecked() {
        return rootElement.findElement(By.xpath(selectionCheckboxLocator)).getAttribute("checked") != null;
    }

    public void checkElement() {
        rootElement.findElement(By.xpath(selectionCheckboxLocator)).click();
    }

    public String getTechnicianName() {
        return rootElement.findElements(By.xpath(titleLocator)).get(0).getText()
                + ' ' +
                rootElement.findElements(By.xpath(titleLocator)).get(1).getText();
    }
}

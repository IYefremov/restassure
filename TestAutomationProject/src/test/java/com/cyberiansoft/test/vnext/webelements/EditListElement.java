package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public class EditListElement implements IWebElement {
    private WebElement rootElement;
    private String statusLocator = ".//div[@class=\"icon-item-status-title\"]";
    private String nameLocator = ".//div[contains(@class,\"icon-item-content-title\")]";
    private String expandElementLocator = ".//div[@action=\"open-phase-services\"]";
    private String clockIconLocator = ".//div[contains(@class,\"icon-item-content-title\")][3]//span[@class='icon-item-arrow']";


    public EditListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getName() {
        BaseUtils.waitABit(1000);
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(nameLocator)), true);
        return rootElement.findElement(By.xpath(nameLocator)).getText();
    }

    public String getStatus() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(statusLocator)), true);
        return rootElement.findElement(By.xpath(statusLocator)).getText();
    }

    public void openMenu() {
        WaitUtils.click(rootElement);
    }

    public void expandElement() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(expandElementLocator)), true);
        rootElement.findElement(By.xpath(expandElementLocator)).click();
    }

    public Boolean isClockIconPresent() {
        return rootElement.findElements(By.xpath(clockIconLocator)).size() > 0;
    }
}

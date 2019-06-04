package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PhaseListElement implements IWebElement {
    private WebElement rootElement;
    private String phaseStatusLocator = "//div[@class=\"icon-item-status-title\"]";
    private String phaseNameLocator = "//div[@class=\"icon-item-content-title highlited-title\"]";

    public PhaseListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getPhaseName() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(phaseNameLocator)),true);
        return rootElement.findElement(By.xpath(phaseNameLocator)).getText();
    }

    public String getPhaseStatus() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(phaseStatusLocator)),true);
        return rootElement.findElement(By.xpath(phaseStatusLocator)).getText();
    }

    public void selectPhase() {
        rootElement.click();
    }
}

package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class RepairOrderListElement implements IWebElement {
    private WebElement rootElement;
    private String expandButtonLocator = ".//div[@class=\"status-item-content-toggle\"]";
    private String repairOrderIdLocator = ".//div[@class=\"accordion-item-content\"]//div[@class=\"status-item-content-subtitle\"]";
    private String statusValue = ".//div[@class=\"status-item\"]//div[@class=\"status-item-chart\"]";
    private String phaseTextLocator = "//div[@class=\"status-item-content\"]//div[@class=\"status-item-content-row\"][2]";
    private String vinTextLocator = "//div[@class=\"status-item-content\"]//div[@class=\"status-item-content-row\"][3]";

    public RepairOrderListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public Boolean isExpanded() {
        return rootElement.getAttribute("class").contains("expanded");
    }

    public void expand() {
        if (!isExpanded())
            rootElement.findElement(By.xpath(expandButtonLocator)).click();
    }

    public String getRepairOrderId() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderIdLocator)), true);
        return rootElement.findElement(By.xpath(repairOrderIdLocator)).getText();
    }

    public String getStatusValue() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderIdLocator)), true);
        return rootElement.findElement(By.xpath(statusValue)).getText();
    }

    public String getPhase() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderIdLocator)), true);
        return rootElement.findElement(By.xpath(phaseTextLocator)).getText();
    }

    public String getVin() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderIdLocator)), true);
        return rootElement.findElement(By.xpath(vinTextLocator)).getText().split(" ")[1];
    }

    public RepairOrderDto getRepairOrderDto() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderIdLocator)), true);
        return new RepairOrderDto(getPhase(), getVin(), getStatusValue());
    }
}

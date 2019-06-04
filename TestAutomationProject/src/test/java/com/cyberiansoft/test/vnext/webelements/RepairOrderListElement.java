package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.vnext.dto.RepairOrderDto;
import com.cyberiansoft.test.vnext.enums.RepairOrderFlag;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnext.webelements.decoration.IWebElement;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class RepairOrderListElement implements IWebElement {
    private WebElement rootElement;
    private String expandButtonLocator = ".//div[@class=\"status-item-content-toggle\"]";
    private String repairOrderIdLocator = ".//div[@class=\"accordion-item-content\"]//div[@class=\"status-item-content-subtitle\"]";
    private String statusValue = ".//div[@class=\"status-item\"]//div[@class=\"status-item-chart\"]";
    private String phaseTextLocator = "//div[@class=\"status-item-content\"]//div[@class=\"status-item-content-row\"][2]";
    private String vinTextLocator = "//div[@class=\"status-item-content\"]//div[@class=\"status-item-content-row\"][3]";
    private String statusesListLocator = "//div[@action=\"change-flag\"]";

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
        return rootElement.findElement(By.xpath(phaseTextLocator)).getText().toUpperCase();
    }

    public String getVin() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderIdLocator)), true);
        return rootElement.findElement(By.xpath(vinTextLocator)).getText().split(" ")[1];
    }

    public RepairOrderDto getRepairOrderDto() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderIdLocator)), true);
        return new RepairOrderDto(getPhase(), getVin(), getStatusValue());
    }

    public void openMenu() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(vinTextLocator)), true);
        rootElement.findElement(By.xpath(vinTextLocator)).click();
    }

    public void selectStatus(RepairOrderFlag flag) {
        this.expand();
        rootElement.findElements(By.xpath(statusesListLocator))
                .stream()
                .filter((element) -> element.getAttribute("class").contains(flag.getFlagData()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Repair order flag not found " + flag.getFlagData()))
                .click();
    }

    public RepairOrderFlag getRepairOrderFlag() {
        Pattern pattern = Pattern.compile("\\w+-flag-selected");
        Matcher matcher = pattern.matcher(rootElement.getAttribute("class"));
        matcher.find();
        String flagString = matcher.group();
        return RepairOrderFlag.getFlagFromString(flagString.substring(0,flagString.lastIndexOf("-")));
    }
}

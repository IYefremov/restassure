package com.cyberiansoft.test.vnext.webelements;

import com.cyberiansoft.test.baseutils.BaseUtils;
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
    //String expandButtonLocator = ".//div[@class=\"status-item-content-toggle\"]";
    String expandButtonLocator = ".//*[@action=\"toggle_item\"]";
    private String repairOrderIdLocator = ".//div[contains(@class,'order-num')]";
    private String statusValue = "//div[contains(@class,'ro-item-progress')]";
    private String stockValue = "//span[@class='content-subtitle-name' and contains(text(), 'Stock')]/..";
    private String phaseTextLocator = "//div[contains(@class,'active-phase')]";
    private String vinTextLocator = "//div[contains(@class,'right-vin')]";
    private String statusesListLocator = "//*[@action=\"change-flag\"]";
    private String repairOrderLocator = ".//div[contains(@class,'ro-item accordion-item')]";

    public RepairOrderListElement(WebElement rootElement) {
        this.rootElement = rootElement;
    }

    public String getRepairOrderId() {
        WaitUtils.waitUntilElementIsClickable(rootElement);
        if (!rootElement.getAttribute("class").contains("expanded")) {
            rootElement.findElement(By.xpath(expandButtonLocator)).click();
            BaseUtils.waitABit(500);
        }
        return rootElement.findElement(By.xpath(".//div[@class='accordion-item-content']")).
                findElement(By.xpath(repairOrderIdLocator)).getText();
    }

    public String getStockValue() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderLocator)), true);
        return rootElement.findElement(By.xpath(stockValue)).getText().split(" ")[1];
    }

    public String getStatusValue() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderLocator)), true);
        return rootElement.findElement(By.xpath(statusValue)).getText();
    }

    public String getPhase() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderLocator)), true);
        return rootElement.findElement(By.xpath(phaseTextLocator)).getText().toUpperCase();
    }

    public String getVin() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderLocator)), true);
        return rootElement.findElement(By.xpath(vinTextLocator)).getText().split(" ")[1];
    }

    public RepairOrderDto getRepairOrderDto() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderLocator)), true);
        return new RepairOrderDto(getPhase(), getVin(), getStatusValue());
    }

    public void openMenu() {
        WaitUtils.elementShouldBeVisible(rootElement.findElement(By.xpath(repairOrderLocator)), true);
        WaitUtils.click(rootElement.findElement(By.xpath(repairOrderLocator)));
    }

    public void selectStatus(RepairOrderFlag flag) {
        BaseUtils.waitABit(500);
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
        return RepairOrderFlag.getFlagFromString(flagString.substring(0, flagString.lastIndexOf("-")));
    }
}

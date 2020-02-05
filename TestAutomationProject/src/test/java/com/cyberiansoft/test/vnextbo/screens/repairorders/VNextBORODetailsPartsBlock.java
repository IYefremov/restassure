package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VNextBORODetailsPartsBlock extends VNextBOBaseWebPage {

    @FindBy(id = "reconmonitordetails-parts")
    private WebElement partsBlock;

    public VNextBORODetailsPartsBlock() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getPartsByName(String partName) {
        List<WebElement> parts = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibility(partsBlock, 10);
        final List<WebElement> partNamesElements = partsBlock.findElements(By.xpath(".//b"));
        final List<String> partNamesList = Utils.getText(partNamesElements);

        for (int i = 0; i < partNamesList.size(); i++) {
            if (partNamesList.get(i).equals(partName)) {
                parts.add(partsBlock.findElements(By.xpath(".//tbody/tr")).get(i));
            }
        }

        return parts;
    }

    public List<WebElement> getQuantity(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//td[contains(@class, 'grid__number')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getPartPrice(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//span[contains(@data-bind, 'amountFormatted')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getVendorPrice(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//span[contains(@data-bind, 'vendorPriceF')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getOrderedFrom(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//td[contains(@class, 'grid__centered')][4]/div")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getStatus(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//div[contains(@data-bind, 'orderServiceStatusName')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getPhase(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//td[contains(@data-bind, 'phaseName')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getOrderedDate(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//td[contains(@data-bind, 'startDate')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getReceived(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//td[contains(@data-bind, 'finishDate')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getActions(List<WebElement> parts) {
        return parts.stream().map(part -> part
                .findElement(By.xpath(".//div[contains(@data-bind, 'partActionsVisible')]")))
                .collect(Collectors.toList());
    }

    public List<WebElement> getPartFieldsByName(List<WebElement> parts) {
        return Arrays.asList(
                getQuantity(parts).get(0),
                getPartPrice(parts).get(0),
                getVendorPrice(parts).get(0),
                getOrderedFrom(parts).get(0),
                getStatus(parts).get(0),
                getPhase(parts).get(0),
                getOrderedDate(parts).get(0),
                getReceived(parts).get(0)
        );
    }
}

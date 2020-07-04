package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class VNextBOOtherPanel extends VNextBOBaseWebPage {

    private VNextBOROWebPage roPage;

    public VNextBOOtherPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);

        roPage = new VNextBOROWebPage();
    }

    public WebElement getOtherPanel(String orderNumber) {
        final WebElement tableRowWithWorkOrder = roPage.getTableRowWithWorkOrder(orderNumber);
        return tableRowWithWorkOrder.findElement(
                By.xpath(".//div[contains(@data-bind, 'menuVisible')]"));
    }

    public WebElement getRedPriorityButton(String orderNumber) {
        final WebElement tableRowWithWorkOrder = roPage.getTableRowWithWorkOrder(orderNumber);
        return tableRowWithWorkOrder.findElement(
                By.xpath(".//span[contains(@class, 'text-red priority-button')]"));
    }

    public WebElement getGreenPriorityButton(String orderNumber) {
        final WebElement tableRowWithWorkOrder = roPage.getTableRowWithWorkOrder(orderNumber);
        return tableRowWithWorkOrder.findElement(
                By.xpath(".//span[contains(@class, 'text-green priority-button')]"));
    }

    public WebElement getOrangePriorityButton(String orderNumber) {
        final WebElement tableRowWithWorkOrder = roPage.getTableRowWithWorkOrder(orderNumber);
        return tableRowWithWorkOrder.findElement(
                By.xpath(".//span[contains(@class, 'text-orange priority-button')]"));
    }

    public WebElement getFlagButton(String orderNumber, String flagColor) {
        final WebElement tableRowWithWorkOrder = roPage.getTableRowWithWorkOrder(orderNumber);
        return tableRowWithWorkOrder.findElement(
                By.xpath(".//div[contains(@data-bind, 'allowEdit')]//span[@title='" + flagColor + "']"));
    }
}

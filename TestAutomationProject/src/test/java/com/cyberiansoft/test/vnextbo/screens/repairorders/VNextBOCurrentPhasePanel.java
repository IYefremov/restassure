package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOCurrentPhasePanel extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[contains(@data-bind, 'phaseMenuVisible')]")
    private WebElement currentPhasePanel;

    @FindBy(xpath = "//p[contains(@data-bind, 'orderPhaseServices')]/div[contains(@class, 'menu-item')]")
    private List<WebElement> services;

    public VNextBOCurrentPhasePanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getStartPhaseServicesOption(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//div[contains(@data-bind, 'startActivePhase')]"));
    }

    public WebElement getCompleteCurrentPhaseOption(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//div[contains(@data-bind, 'completeActivePhase')]"));
    }

    public List<WebElement> getChangePhaseStatusOptions(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElements(By.xpath(".//div[contains(@data-bind, 'changeServiceStatus')]"));
    }

    public List<WebElement> getStartServicesIcons(String orderNumber) {
        WebElement woTableRow = WaitUtilsWebDriver.waitForElementNotToBeStale(
                new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber));
        return woTableRow.findElements(By.xpath(".//i[@title='Start']"));
    }

    public List<WebElement> getCompleteServicesIcons(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElements(By.xpath(".//i[@title='Complete']"));
    }
}

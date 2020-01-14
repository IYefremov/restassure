package com.cyberiansoft.test.vnextbo.screens.repairorders;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class VNextBOCurrentPhasePanel extends VNextBOBaseWebPage {

    public VNextBOCurrentPhasePanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getStartPhaseServicesOption(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//div[contains(@data-bind, 'startPhaseServices')]"));
    }

    public WebElement getCompleteCurrentPhaseOption(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElement(By.xpath(".//div[contains(@data-bind, 'completeActivePhase')]"));
    }

    public List<WebElement> getChangePhaseStatusOptions(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElements(By.xpath(".//*[@data-bind='click: changeServiceStatus']"));
    }

    public List<WebElement> getStartServicesIcons(String orderNumber) {
        WebElement woTableRow = new VNextBOROWebPage().getTableRowWithWorkOrder(orderNumber);
        return woTableRow.findElements(By.xpath(".//i[@class='icon-start-ro' and contains(@data-bind, 'canStartService')]"));
    }
}

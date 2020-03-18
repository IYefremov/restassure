package com.cyberiansoft.test.vnextbo.screens.partsmanagement;

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
public class VNextBODashboardPanel extends VNextBOBaseWebPage {

    @FindBy(id = "parts-view-dashboard")
    private WebElement dashboardPanel;

    public VNextBODashboardPanel() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getDashboardItems() {
        return dashboardPanel.findElements(By.xpath(".//div[@role]"));
    }

    public WebElement getItemByName(String name) {
        return dashboardPanel.findElement(By.xpath(".//div[text()='" + name + "']"));
    }

    public WebElement getItemCounterByName(String name) {
        return getItemByName(name).findElement(By.xpath(".//preceding-sibling::div[contains(@class, 'counter')]"));
    }
}
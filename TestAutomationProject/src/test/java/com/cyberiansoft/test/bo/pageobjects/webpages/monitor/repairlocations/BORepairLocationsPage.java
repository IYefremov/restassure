package com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairlocations;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.WebTable;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class BORepairLocationsPage extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_gvLocations_ctl00")
    private WebTable repairLocationsTable;

    public BORepairLocationsPage() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getEditButtonsList() {
        return repairLocationsTable.getWrappedElement().findElements(By.xpath(".//input[contains(@title, 'Edit')]"));
    }

    public List<WebElement> getPhasesList() {
        return repairLocationsTable.getWrappedElement().findElements(By.xpath(".//a[contains(text(), 'Phases')]"));
    }

    public List<WebElement> getLocationsList() {
        return repairLocationsTable.getWrappedElement().findElements(
                By.xpath(".//td[" + repairLocationsTable.getTableColumnIndex("Location") + "]"));
    }
}

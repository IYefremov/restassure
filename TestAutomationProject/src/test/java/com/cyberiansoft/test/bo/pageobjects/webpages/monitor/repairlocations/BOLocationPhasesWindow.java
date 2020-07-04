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
public class BOLocationPhasesWindow extends BaseWebPage {

    @FindBy(xpath = "//td[@id='ctl00_tdContent']//h2[text()='Phases']")
    private WebElement phasesHeader;

    @FindBy(id = "ctl00_Content_gv_ctl00")
    private WebTable phasesTable;

    public BOLocationPhasesWindow() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public List<WebElement> getEditButtonsList() {
        return phasesTable.getWrappedElement().findElements(By.xpath(".//input[contains(@title, 'Edit')]"));
    }

    public List<WebElement> getPhasesList() {
        return phasesTable.getWrappedElement().findElements(
                By.xpath(".//td[" + phasesTable.getTableColumnIndex("Phase") + "]"));
    }
}

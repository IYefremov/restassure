package com.cyberiansoft.test.bo.pageobjects.webpages.company.companyinfo;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOCompanyInfoTab extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_Card_tabs")
    private WebElement tab;

    public BOCompanyInfoTab() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }

    public WebElement getTabByName(String tab) {
        return this.tab.findElement(By.xpath(".//span[text()='" + tab + "']"));
    }

    public WebElement getOpenedTabByName(String tab) {
        return this.tab.findElement(By.xpath(".//a[contains(@class, 'rtsSelected')]//span[text()='" + tab + "']"));
    }
}

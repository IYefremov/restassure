package com.cyberiansoft.test.bo.pageobjects.webpages.monitor.repairlocations;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOLocationDialog extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_ctl01_pHead")
    private WebElement dialogHeader;

    public BOLocationDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

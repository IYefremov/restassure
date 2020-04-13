package com.cyberiansoft.test.bo.pageobjects.webpages.company.companyinfo;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BOIntegrationSettingsBlock extends BaseWebPage {

    @FindBy(id = "ctl00_ctl00_Content_Main_Card_linkPartsProvidersSettings")
    private WebElement partsProvidersConfigureButton;

    public BOIntegrationSettingsBlock() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

package com.cyberiansoft.test.bo.enums.companyinfo;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class PartProvidersDialog extends BaseWebPage {

    @FindBy(id = "ctl00_Content_comboProviders_Input")
    private WebElement providerField;

    @FindBy(xpath = "//div[@id='ctl00_Content_comboProviders_DropDown']//li")
    private List<WebElement> providerOptions;

    public PartProvidersDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

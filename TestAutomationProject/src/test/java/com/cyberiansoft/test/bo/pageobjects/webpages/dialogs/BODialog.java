package com.cyberiansoft.test.bo.pageobjects.webpages.dialogs;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public abstract class BODialog extends BaseWebPage {

    @FindBy(xpath="//input[contains(@id, 'BtnOk')]")
    private WebElement okButton;

    @FindBy(xpath="//input[contains(@id, 'BtnCancel')]")
    private WebElement cancelButton;

    public BODialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

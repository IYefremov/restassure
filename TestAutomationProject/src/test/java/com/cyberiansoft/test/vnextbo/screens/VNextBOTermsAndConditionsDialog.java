package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOTermsAndConditionsDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='dialogModal']//h4[contains(text(), 'Terms and Conditions')]")
    private WebElement termsAndConditionsDialog;

    @FindBy(xpath = "//section[@id='termsAndConditionsDoc']/ol[last()]/li[last()]")
    private WebElement lastElement;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
    private WebElement closeButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalConfirmButton']")
    private WebElement okButton;

    public VNextBOTermsAndConditionsDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
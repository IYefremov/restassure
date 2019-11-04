package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOPrivacyPolicyDialog extends VNextBOBaseWebPage {

    @FindBy(xpath = "//div[@id='dialogModal']//h4[text()='Privacy Policy']")
    private WebElement privacyPolicyDialog;

    @FindBy(xpath = "//div[@id='dialogModal']//div[@class='modal-body__content']/div/p[last()]")
    private WebElement lastElement;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
    private WebElement closeButton;

    @FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalConfirmButton']")
    private WebElement okButton;

    public VNextBOPrivacyPolicyDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}
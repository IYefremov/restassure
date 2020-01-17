package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOUserProfileDialog extends BaseWebPage {

    @FindBy(id = "user-profile")
    private WebElement userProfileDialog;

    @FindBy(id = "userProfile-email")
    private WebElement emailInputField;

    @FindBy(id = "userProfile-password")
    private WebElement passwordInputField;

    @FindBy(id = "userProfile-passwordConfirm")
    private WebElement confirmPasswordInputField;

    @FindBy(id = "userProfile-firstName")
    private WebElement firstNameInputField;

    @FindBy(id = "userProfile-lastName")
    private WebElement lastNameInputField;

    @FindBy(id = "userProfile-phone")
    private WebElement phoneInputField;

    @FindBy(xpath = "//div[@id='user-profile']//button[contains(@data-bind, 'submit')]")
    private WebElement saveButton;

    @FindBy(xpath = "//div[@id='user-profile']//button[@data-dismiss]")
    private WebElement xButton;

    public VNextBOUserProfileDialog() {
        super(DriverBuilder.getInstance().getDriver());
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
    }
}

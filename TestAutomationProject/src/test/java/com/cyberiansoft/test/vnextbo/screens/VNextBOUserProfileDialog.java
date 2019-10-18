package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.rmi.CORBA.Util;
import java.util.concurrent.TimeUnit;

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

    public VNextBOUserProfileDialog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public boolean isUserProfileDialogDisplayed() {
        return Utils.isElementDisplayed(userProfileDialog);
    }

    public boolean isUserProfileDialogClosed() {
        return Utils.isElementNotDisplayed(userProfileDialog);
    }

    public boolean isEmailInputFieldDisplayed() {
        return isElementDisplayed(emailInputField);
    }

    public boolean isPasswordInputFieldDisplayed() {
        return isElementDisplayed(passwordInputField);
    }

    public boolean isConfirmPasswordInputFieldDisplayed() {
        return isElementDisplayed(confirmPasswordInputField);
    }

    public boolean isFirstNameInputFieldDisplayed() {
        return isElementDisplayed(firstNameInputField);
    }

    public boolean isLastNameInputFieldDisplayed() {
        return isElementDisplayed(lastNameInputField);
    }

    public boolean isPhoneInputFieldDisplayed() {
        return isElementDisplayed(phoneInputField);
    }

    public boolean isSaveButtonDisplayed() {
        return isElementDisplayed(saveButton);
    }

    public boolean isXButtonDisplayed() {
        return isElementDisplayed(xButton);
    }

    public void closeDialog() { Utils.clickElement(xButton); }
}

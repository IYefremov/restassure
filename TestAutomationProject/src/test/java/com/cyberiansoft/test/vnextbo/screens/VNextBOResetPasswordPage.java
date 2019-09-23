package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextBOResetPasswordPage extends VNextBOBaseWebPage {

    @FindBy(id = "loginChangePasswordButton")
    private WebElement submitBtn;

    @FindBy(id = "newPassword")
    private TextField newPasswordField;

    @FindBy(id = "newPassword2")
    private TextField confirmNewPasswordField;

    @FindBy(xpath = "//div[@data-bind = 'text: invintationUserProfile.email']")
    private WebElement userEmail;

    public VNextBOResetPasswordPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOf(submitBtn));
    }

    public String getUserEmail() {
        return Utils.getText(userEmail);
    }

    public void setNewPassword(String newPassword)
    {
        newPasswordField.clearAndType(newPassword);
        confirmNewPasswordField.clearAndType(newPassword);
        submitBtn.click();
    }
}

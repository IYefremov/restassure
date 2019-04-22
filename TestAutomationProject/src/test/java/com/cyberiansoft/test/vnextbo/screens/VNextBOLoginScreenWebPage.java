package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

public class VNextBOLoginScreenWebPage extends VNextBOBaseWebPage {

	@FindBy(xpath = "//div[@class='loginForm']")
	private WebElement loginForm;
	
	@FindBy(id = "email")
	private TextField emailField;
	
	@FindBy(id = "password")
	private TextField passwordField;
	
	@FindBy(xpath = "//input[@value='Login']")
	private WebElement loginButton;
	
	@FindBy(xpath = "//a[text()='Forgot password?']")
	private WebElement forgotPasswordLink;
	
	public VNextBOLoginScreenWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		waitLong.until(ExpectedConditions.visibilityOf(loginForm));
	}
	
	public VNextBOHeaderPanel userLogin(String username, String userpsw) {
		clearAndType(emailField.getWrappedElement(), username);
		clearAndType(passwordField.getWrappedElement(), userpsw);
		click(loginButton);
		waitABit(2000);
		return PageFactory.initElements(
				driver, VNextBOHeaderPanel.class); 
	}
	
	public VNextBOForgotPasswordWebPage clickForgotPasswordLink() {
		forgotPasswordLink.click();
		return PageFactory.initElements(
				driver, VNextBOForgotPasswordWebPage.class);
	}

    public boolean isLoginFormDisplayed() {
        return isElementDisplayed(loginForm);
    }

    public boolean isEmailFieldDisplayed() {
        return isElementDisplayed(emailField.getWrappedElement());
    }

    public boolean isPasswordFieldDisplayed() {
        return isElementDisplayed(passwordField.getWrappedElement());
    }

    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }

    public boolean isForgotPasswordLinkDisplayed() {
        return isElementDisplayed(forgotPasswordLink);
    }
}

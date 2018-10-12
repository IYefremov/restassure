package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

public class VNextBOLoginScreenWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@class='loginForm']")
	private WebElement loginform;
	
	@FindBy(id = "email")
	private TextField emailfld;
	
	@FindBy(id = "password")
	private TextField passwordfld;
	
	@FindBy(xpath = "//input[@value='Login']")
	private WebElement loginbtn;
	
	@FindBy(xpath = "//a[text()='Forgot password?']")
	private WebElement forgotpswlink;
	
	public VNextBOLoginScreenWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(loginform));
	}
	
	public VNextBOHeaderPanel userLogin(String username, String userpsw) {
		clearAndType(emailfld, username);
		clearAndType(passwordfld, userpsw);
		click(loginbtn);
		waitABit(2000);
		return PageFactory.initElements(
				driver, VNextBOHeaderPanel.class); 
	}
	
	public VNextBOForgotPasswordWebPage clickForgotPasswordLink() {
		forgotpswlink.click();
		return PageFactory.initElements(
				driver, VNextBOForgotPasswordWebPage.class);
	}
}

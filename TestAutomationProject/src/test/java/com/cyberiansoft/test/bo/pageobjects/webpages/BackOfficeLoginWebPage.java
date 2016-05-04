package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class BackOfficeLoginWebPage extends BaseWebPage {

	@FindBy(id = "UserName")
	private TextField usernamefld;

	@FindBy(id = "Password")
	private TextField userpasswordfld;
	
	@FindBy(id = "ctl00_Content_Login1_LoginButton")
	private WebElement loginbtn;

	public BackOfficeLoginWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void UserLogin(String userName, String userPassword) {
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.visibilityOf(loginbtn));
		clearAndType(usernamefld, userName);
		clearAndType(userpasswordfld, userPassword);
		click(loginbtn);
	}
	
	public WebElement getLoginButton() {
		return loginbtn;
	}

}

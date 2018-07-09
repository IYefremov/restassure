package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;

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
	}

	public void UserLogin(String userName, String userPassword) {
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		driver.navigate().refresh();
		executor.executeScript("window.focus();");
		clearAndType(usernamefld, userName);
		clearAndType(userpasswordfld, userPassword);
		executor.executeScript("arguments[0].click();", loginbtn);
	}
	
	public WebElement getLoginButton() {
		return loginbtn;
	}
}

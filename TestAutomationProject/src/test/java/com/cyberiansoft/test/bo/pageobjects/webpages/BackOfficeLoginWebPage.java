package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	}

	public void UserLogin(String userName, String userPassword) {
//		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_Content_Login1_LoginButton")));
//		wait.until(ExpectedConditions.elementToBeClickable(loginbtn));
//		wait.until(ExpectedConditions.visibilityOf(loginbtn));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		driver.navigate().refresh();
		executor.executeScript("window.focus();");
		clearAndType(usernamefld, userName);
		clearAndType(userpasswordfld, userPassword);
		executor.executeScript("arguments[0].click();", loginbtn);
//		click(loginbtn);
	}
	
	public WebElement getLoginButton() {
		return loginbtn;
	}
	
	

}

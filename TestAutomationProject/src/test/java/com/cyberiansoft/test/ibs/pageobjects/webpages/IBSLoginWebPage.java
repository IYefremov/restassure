package com.cyberiansoft.test.ibs.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.pageobjects.webpages.BaseWebPage;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.vnextbo.screens.VNextBOApproveAccountWebPage;

public class IBSLoginWebPage extends BaseWebPage {
	
	@FindBy(id = "txtUserName")
	private TextField usernamefld;
	
	@FindBy(id = "txtPassword")
	private TextField userpasswordfld;
	
	@FindBy(id = "btn-login")
	private WebElement loginbtn;
	
	public IBSLoginWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public IBSDashboardPage UserLogin(String userName, String userPassword) {
		wait.until(ExpectedConditions.visibilityOf(loginbtn));
		clearAndType(usernamefld, userName);
		clearAndType(userpasswordfld, userPassword);
		click(loginbtn);
		return new IBSDashboardPage(driver);
	}

}

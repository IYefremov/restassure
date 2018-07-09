package com.cyberiansoft.test.ibsBo;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;
import com.cyberiansoft.test.ibs.pageobjects.webpages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.clearAndType;
import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

public class IbsBoLoginWebPage extends BasePage {

	@FindBy(id = "Login1_UserName")
	private TextField userNameField;

	@FindBy(id = "Login1_Password")
	private TextField userPasswordField;

	@FindBy(id = "Login1_LoginButton")
	private WebElement loginButton;

	public IbsBoLoginWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}
	
	public IbsBoEditClientPage login(String userName, String userPassword) {
		wait.until(ExpectedConditions.visibilityOf(loginButton));
		clearAndType(userNameField, userName);
		clearAndType(userPasswordField, userPassword);
		click(loginButton);
        return PageFactory.initElements(driver, IbsBoEditClientPage.class);
	}
}

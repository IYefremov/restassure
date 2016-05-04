package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class SuperUserWebPage  extends BaseWebPage {
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Applications']")
	private WebElement applicationslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='All Users']")
	private WebElement alluserslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='All Employees']")
	private WebElement allemployeeslink;
	
	public SuperUserWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public ApplicationsWebPage clickApplicationsLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(applicationslink)));
		return PageFactory.initElements(
				driver, ApplicationsWebPage.class);
	}
	
	public AllUsersWebPage clickAllUsersLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(alluserslink)));
		return PageFactory.initElements(
				driver, AllUsersWebPage.class);
	}
	
	public AllEmployeesWebPage clickAllEmployeesLink() {
		click(new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.elementToBeClickable(allemployeeslink)));
		return PageFactory.initElements(
				driver, AllEmployeesWebPage.class);
	}

}

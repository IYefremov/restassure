package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

@Getter
public class SuperUserWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Applications']")
	private WebElement applicationslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='All Users']")
	private WebElement alluserslink;
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='All Employees']")
	private WebElement allemployeeslink;

	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='Subscribe']")
	private WebElement subscribeLink;

	public SuperUserWebPage() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}
	
	public void clickApplicationsLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(applicationslink)));
	}
	
	public void clickAllUsersLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(alluserslink)));
	}
	
	public void clickAllEmployeesLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(allemployeeslink)));
	}

}

package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.click;

public class HomeWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='New Service Requests']")
	private WebElement newservicerequestlink;
	
	public HomeWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public ServiceRequestsListInteractions clickNewServiceRequestLink() {
		click(wait.until(ExpectedConditions.elementToBeClickable(newservicerequestlink)));
		return PageFactory.initElements(
				driver, ServiceRequestsListInteractions.class);
	}
}

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

public class HomeWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[@class='navLinkTitle' and text()='New Service Requests']")
	private WebElement newservicerequestlink;
	
	public HomeWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public ServiceRequestsListWebPage clickNewServiceRequestLink() {
		click(new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.elementToBeClickable(newservicerequestlink)));
		return PageFactory.initElements(
				driver, ServiceRequestsListWebPage.class);
	}

}

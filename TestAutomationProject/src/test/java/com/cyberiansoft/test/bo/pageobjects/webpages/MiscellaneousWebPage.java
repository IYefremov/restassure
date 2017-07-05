package com.cyberiansoft.test.bo.pageobjects.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class MiscellaneousWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[text()='Events']")
	private WebElement eventstab;

	public MiscellaneousWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		
	}

	public EventsWebPage clickEventsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(eventstab)).click();
		return PageFactory.initElements(
				driver, EventsWebPage.class);
	}

}

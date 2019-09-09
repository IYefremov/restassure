package com.cyberiansoft.test.bo.pageobjects.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class TimesheetsWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl00_ctl00_childNodes_ctl00_Label1")
	WebElement timesheetsBTN;

	public TimesheetsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickTimesheets() {
		wait.until(ExpectedConditions.elementToBeClickable(timesheetsBTN)).click();
	}
}

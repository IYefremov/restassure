package com.cyberiansoft.test.bo.pageobjects.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.testng.Assert;

public class ReportsWebPage extends BaseWebPage {

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl07_Label1")
	private WebElement technicianCommissionLink;

	@FindBy(id = "ctl00_ctl00_Content_Main_NavigationItem1_rLinks_ctl01_ctl00_childNodes_ctl66_Label1")
	private WebElement apadStatementLink;

	public ReportsWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickTechnicianCommissionsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(technicianCommissionLink)).click();
	}

	public void clickAPADStatementLink() {
		wait.until(ExpectedConditions.elementToBeClickable(apadStatementLink)).click();
	}
}

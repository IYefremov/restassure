package com.cyberiansoft.test.bo.pageobjects.webpages;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MiscellaneousWebPage extends BaseWebPage {
	
	@FindBy(xpath = "//span[text()='Events']")
	private WebElement eventstab;

	public MiscellaneousWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
	}

	public void clickEventsLink() {
        WaitUtilsWebDriver.elementShouldBeVisible(eventstab, true);
        Utils.clickElement(eventstab);
	}
}

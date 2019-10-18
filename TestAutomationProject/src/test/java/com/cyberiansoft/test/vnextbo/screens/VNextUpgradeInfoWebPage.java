package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextUpgradeInfoWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//a[@class='upgrade-info__banner']")
	private WebElement upgradeinfobanner;
	
	@FindBy(xpath = "//a[@data-automation-id='upgradeInfoSubmit']")
	private WebElement unlockr360btn;
	
	public VNextUpgradeInfoWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public VNextPaymentInfoWebPage clickUnlockRepair360EditionButton() {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);",unlockr360btn);
		waitABit(3000);
		unlockr360btn.click();
		return PageFactory.initElements(
				driver, VNextPaymentInfoWebPage.class);
	}

}

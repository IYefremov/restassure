package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
        WaitUtilsWebDriver.waitABit(3000);
		unlockr360btn.click();
		return PageFactory.initElements(
				driver, VNextPaymentInfoWebPage.class);
	}

}

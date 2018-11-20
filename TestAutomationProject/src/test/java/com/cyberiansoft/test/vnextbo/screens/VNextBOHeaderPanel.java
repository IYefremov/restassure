package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class VNextBOHeaderPanel extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//a[@data-bind='click: logout']")
	private WebElement logoutlink;
	
	@FindBy(xpath = "//div[@class='user']/span")
	private WebElement userprofilelink;
	
	@FindBy(id = "upgrade-banner")
	private WebElement upgradenowbtn;
	
	public VNextBOHeaderPanel(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickLogout() {
		try {
		    waitShort
                    .until(ExpectedConditions.elementToBeClickable(logoutlink))
                    .click();
		} catch (WebDriverException ignored) {}
	}
	
	public boolean logOutLinkExists() {
		return driver.findElements(By.xpath("//div[@class='login']/a")).size() > 0;
	}
	
	public VNextBOLoginScreenWebPage userLogout() {
		clickLogout();
		waitABit(1000);
		return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class);
	}
	
	public VNextUpgradeInfoWebPage clickUpgradeNowBanner() {
		waitShort.until(ExpectedConditions.elementToBeClickable(upgradenowbtn)).click();
		return PageFactory.initElements(
				driver, VNextUpgradeInfoWebPage.class);
	}
}
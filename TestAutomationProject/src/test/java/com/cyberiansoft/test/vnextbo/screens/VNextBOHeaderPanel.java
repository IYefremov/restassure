package com.cyberiansoft.test.vnextbo.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;

public class VNextBOHeaderPanel extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@class='login']/a")
	private WebElement logoutlink;
	
	@FindBy(xpath = "//div[@class='user']/span")
	private WebElement userprofilelink;
	
	@FindBy(xpath = "//a[@class='btn btn-promo']")
	private WebElement upgradenowbtn;
	
	public VNextBOHeaderPanel(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickLogout() {
		logoutlink.click();
	}
	
	public boolean isLogOutLinkExists() {
		return driver.findElements(By.xpath("//div[@class='login']/a")).size() > 0;
	}
	
	public VNextBOLoginScreenWebPage userLogout() {
		clickLogout();
		return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class); 
	}

}

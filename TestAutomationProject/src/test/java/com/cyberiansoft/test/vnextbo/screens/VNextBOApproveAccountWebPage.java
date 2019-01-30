package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextBOApproveAccountWebPage extends VNextBOBaseWebPage {

	@FindBy(id = "loginLogin2")
	private WebElement loginlink;
		
	@FindBy(xpath = "//div[text()='has been approved!']")
	private WebElement approvedmsg;
	
	
	public VNextBOApproveAccountWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(approvedmsg));
	}

	public VNextBOLoginScreenWebPage clickLoginLink() {
		loginlink.click();
		return PageFactory.initElements(
				driver, VNextBOLoginScreenWebPage.class);
	}

}

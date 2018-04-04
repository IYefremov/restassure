package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextDeclineReasonScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='services-add']")
	private WebElement approveservicesscreen;
	
	public VNextDeclineReasonScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(approveservicesscreen));
	}
	
	public void selectDeclineReason(String declineReason) {
		tap(approveservicesscreen.findElement(By.
				xpath(".//ul/li[@action='select-item']/span[text()='" + declineReason + "']")));
		log(LogStatus.INFO, "Select Decline reason: " + declineReason);
	}

}

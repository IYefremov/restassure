package com.cyberiansoft.test.vnext.screens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class VNextInspectionTypesList extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='entity-types']")
	private WebElement insptypeslist;
	
	public VNextInspectionTypesList(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		//PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver, 15, TimeUnit.SECONDS), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(insptypeslist));
	}
	
	public void selectInspectionType(String inspectionType) {
		tap(insptypeslist.findElement(By.xpath(".//div[@class='item-title']/div[text()='" + inspectionType + "']")));
		log(LogStatus.INFO, "Select Inspection type: " + inspectionType);
	}
	

}

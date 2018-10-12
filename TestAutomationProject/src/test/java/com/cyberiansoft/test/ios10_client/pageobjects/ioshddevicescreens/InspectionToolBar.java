package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InspectionToolBar extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "SubtotalAmount")
    private IOSElement inspsubtotal;
	
	@iOSFindBy(accessibility = "TotalAmount")
    private IOSElement insptotal;
	
	public InspectionToolBar() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public String getInspectionSubTotalPrice() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);

		wait.until(ExpectedConditions.elementToBeClickable(By.name("SubtotalAmount")));
		return inspsubtotal.getAttribute("value");
	}
	
	public String getInspectionTotalPrice() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 20);

		wait.until(ExpectedConditions.elementToBeClickable(By.name("TotalAmount")));
		return insptotal.getAttribute("value");
	}

}

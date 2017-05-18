package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class RegularSelectedServiceBundleScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "services")
	private IOSElement tollbarservicesbtn;
	
	@iOSFindBy(accessibility = "Close")
	private IOSElement tollbarcloseservicesbtn;
	
	public RegularSelectedServiceBundleScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void assertBundleIsSelected(String bundle) {
		WebElement par = getTableParentCell(bundle);	
		Assert.assertTrue(par.findElement(By.xpath("//XCUIElementTypeButton[@name='selected']")).isDisplayed());
	}

	public void assertBundleIsNotSelected(String bundle) {
		WebElement par = getTableParentCell(bundle);		
		Assert.assertTrue(par.findElement(By.xpath("//XCUIElementTypeButton[@name='unselected']")).isDisplayed());
	}

	public void selectBundle(String bundle) {
		WebElement par = getTableParentCell(bundle);		
		par.findElement(By.xpath("//XCUIElementTypeButton[@name='unselected']")).click();
	}

	public void openBundleInfo(String bundle) {
		WebElement par = getTableParentCell(bundle);		
		par.findElement(By.xpath("//XCUIElementTypeButton[@name='custom detail button']")).click();
	}
	
	public void clickServicesIcon() {
		tollbarservicesbtn.click();
	}
	
	public void clickCloseServicesPopup() {
		tollbarcloseservicesbtn.click();
	}
	
	public boolean isBundleServiceExists(String bundle) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeStaticText[@label='" + bundle + "']")).isDisplayed();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}

package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularTeamInvoicesScreen extends RegularBaseTypeScreenWithTabs {
	
	public RegularTeamInvoicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void selectInvoice(String invoice) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId(invoice)));
		appiumdriver.findElement(MobileBy.AccessibilityId(invoice)).click();
	}

	public void clickChangePOPopup() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Change\nPO#")));
		appiumdriver.findElementByAccessibilityId("Change\nPO#").click();
	}

	public void changePO(String newpo) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.className("XCUIElementTypeCollectionView")));
		appiumdriver.findElementByClassName("XCUIElementTypeCollectionView").findElement(By.className("XCUIElementTypeTextField")).clear();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(newpo);
		appiumdriver.switchTo().alert().accept();
	}

	public IOSElement getFirstInvoice() {
		return (IOSElement) appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]"));
	}

	public String getFirstInvoiceValue() {
		return getFirstInvoice().getAttribute("name");
	}

}

package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularTeamInvoicesScreen extends RegularBaseTypeScreenWithTabs {

	@iOSXCUITFindBy(accessibility = "InvoicesTable")
	private IOSElement invoicesTable;
	
	public RegularTeamInvoicesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void selectInvoice(String invoiceNumber) {
		WaitUtils.elementShouldBeVisible(invoicesTable, true);
		invoicesTable.findElementByAccessibilityId(invoiceNumber).click();
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
		appiumdriver.findElementByClassName("XCUIElementTypeCollectionView").findElement(By.className("XCUIElementTypeTextField")).sendKeys(newpo);
		appiumdriver.switchTo().alert().accept();
	}

	public boolean isInvoiceExists(String invoiceID) {
		WaitUtils.elementShouldBeVisible(invoicesTable, true);
		return invoicesTable.findElementsByAccessibilityId(invoiceID).size() > 0;
	}

}

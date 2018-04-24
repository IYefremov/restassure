package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class RegularClaimScreen extends iOSRegularBaseScreen {
	
	public RegularClaimScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectInsuranceCompany(String insurancecompany) {
		WebElement par = getTableParentCell("Insurance Company");
		new TouchAction(appiumdriver).tap(par.findElement(MobileBy.AccessibilityId("custom detail button"))).perform() ;
		if (!appiumdriver.findElementByAccessibilityId(insurancecompany).isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + insurancecompany + "']")));
		appiumdriver.findElementByAccessibilityId(insurancecompany).click();
	}

	public void setClaim(String claim) {
		appiumdriver.findElementByAccessibilityId("Claim#").click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(claim);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");

	}
	
	public void selectInsuranceCompanyAndSetClaim(String insurancecompany, String claim) {
		selectInsuranceCompany(insurancecompany);
		setClaim(claim);
	}
	

	public void setDeductible(String deductible)
			throws InterruptedException {
		WebElement par = getTableParentCell("Deductible");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		((IOSDriver) appiumdriver).getKeyboard().pressKey(deductible);
		((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public String getDeductibleValue() {
		WebElement par = getTableParentCell("Deductible");
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}

	public void setAccidentDate() {
		WebElement par = getTableParentCell("Accident Date");
		new TouchAction(appiumdriver).tap(par.findElement(MobileBy.AccessibilityId("custom detail button"))).perform() ;
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String clickSaveWithAlert() {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}

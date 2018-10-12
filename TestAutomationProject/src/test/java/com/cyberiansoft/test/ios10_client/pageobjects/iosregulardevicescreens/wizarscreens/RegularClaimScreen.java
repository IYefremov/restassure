package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegularClaimScreen extends RegularBaseWizardScreen {
	
	public RegularClaimScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitClaimScreenLoad() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.iOSNsPredicateString("name = 'ClaimInfoView'")));
	}
	
	public void selectInsuranceCompany(String insurancecompany) {
		WebElement par = getTableParentCell("Insurance Company");
		par.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		if (!appiumdriver.findElementByAccessibilityId(insurancecompany).isDisplayed())
			swipeToElement(appiumdriver.findElement(By.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + insurancecompany + "']")));
		appiumdriver.findElementByAccessibilityId(insurancecompany).click();
	}

	public void setClaim(String claim) {
		appiumdriver.findElementByAccessibilityId("Claim#").click();
		WebElement par = getTableParentCell("Claim#");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(claim + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(claim);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");

	}

	public void setPolicy(String policyNumber) {
		appiumdriver.findElementByAccessibilityId("Policy#").click();
		WebElement par = getTableParentCell("Claim#");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(policyNumber + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey(policyNumber);
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");

	}
	
	public void selectInsuranceCompanyAndSetClaim(String insurancecompany, String claim) {
		selectInsuranceCompany(insurancecompany);
		setClaim(claim);
	}
	

	public void setDeductible(String deductible) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Deductible")));
		WebElement par = getTableParentCell("Deductible");
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).click();
		par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).sendKeys(deductible + "\n");
		//((IOSDriver) appiumdriver).getKeyboard().pressKey("\n");
	}

	public String getDeductibleValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Deductible")));
		WebElement par = getTableParentCell("Deductible");
		return par.findElement(By.xpath("//XCUIElementTypeTextField[1]")).getAttribute("value");
	}

	public void setAccidentDate() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Accident Date")));
		WebElement par = getTableParentCell("Accident Date");
		par.findElement(MobileBy.AccessibilityId("custom detail button")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public String clickSaveWithAlert() {
		clickSave();
		return Helpers.getAlertTextAndAccept();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}

}

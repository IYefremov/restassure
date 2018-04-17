package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TeamInspectionsScreen extends iOSHDBaseScreen {

	final String firstinspxpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]";
	private By discardbtnxpath = By.name("Discard");
	
	public TeamInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("TeamInspectionsPageTableLeft")));
	}

	public void clickBackServiceRequest() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Service Request"))).click();
	}

	public void assertInspectionExists(String inspection) {
		Assert.assertTrue(appiumdriver.findElementsByAccessibilityId(inspection).size() > 0);
	}

	public void selectInspectionForApprove(String inspnumber) {
		selectInspectionInTable(inspnumber);
		clickApproveInspectionButton();
	}

	public String getFirstInspectionTotalPriceValue() {
		return appiumdriver.findElementByXPath(firstinspxpath + "/XCUIElementTypeStaticText[4]").getAttribute("label");
	}

	public void selectInspectionInTable(String inspectionnumber) {
		//System.out.println("++++" + appiumdriver.findElementsByAccessibilityId(inspectionnumber).size());
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspectionnumber))).click();
		//appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspectionnumber + "']").click();
	}

	protected void clickApproveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}

	public void assertInspectionIsApproved(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(inspnumber)));
		Assert.assertTrue(appiumdriver.findElementByAccessibilityId(inspnumber).findElements(MobileBy.AccessibilityId("EntityInfoButtonUnchecked")).size() > 0);
		//Assert.assertTrue(appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspnumber + "']/XCUIElementTypeOther")).getAttribute("name").equals("EntityInfoButtonUnchecked"));
	}

	public String getFirstInspectionPriceValue() {
		return appiumdriver.findElementByXPath(firstinspxpath + "/XCUIElementTypeStaticText[3]").getAttribute("label");
	}

	public String getInspectionApprovedPriceValue(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
				.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']")));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspnumber + "']").findElement(MobileBy.name("labelInspectionApprovedAmount")).getAttribute("value");
	}

	public void clickApproveInspections() {
		clickActionButton();
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}

	public void clickActionButton() {
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
	}

	public void selectInspectionForAction(String inspnumber) {
		appiumdriver.findElementByAccessibilityId(inspnumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}

	public void selectInspectionForEdit(String inspnumber)  {
		selectInspectionInTable(inspnumber);
		clickEditInspectionButton();
	}

	public void clickEditInspectionButton() {
		TouchAction action = new TouchAction(appiumdriver);
		action.press(appiumdriver.findElementByAccessibilityId("Edit")).waitAction(Duration.ofSeconds(1)).release().perform();
	}

	public boolean isInspectionApproveButtonExists(String inspnumber) {
		return appiumdriver.findElementByAccessibilityId(inspnumber).findElements(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).size() > 0;
	}

	public boolean isApproveInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Approve").size() > 0;
	}

	public boolean isSendEmailInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Send Email").size() > 0;
	}

	public void clickDoneButton() {
		if (elementExists("Actions"))
			appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
		new TeamInspectionsScreen(appiumdriver);
	}

	public boolean isDraftIconPresentForInspection(String inspnumber) {
		return appiumdriver.findElementByAccessibilityId(inspnumber).findElements(MobileBy.AccessibilityId("ESTIMATION_DRAFT"))
				.size() > 0;
	}
}

package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.ServiceRequestdetailsScreen;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TeamInspectionsScreen extends BaseTypeScreenWithTabs {

	@iOSXCUITFindBy(accessibility = "TeamInspectionsPageTableLeft")
	private IOSElement inspectionsTable;

	final String firstinspxpath = "//XCUIElementTypeTable[1]/XCUIElementTypeCell[1]";
	
	public TeamInspectionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitTeamInspectionsScreenLoaded() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("TeamInspectionsPageTableLeft")));
	}

	public String getInspectionTypeValue(String inspectionnumber) {
		return appiumdriver.findElement(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspectionnumber + "']/XCUIElementTypeStaticText[@name='labelInfo2']")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}

	public void clickAddInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Add").click();
		if (appiumdriver.findElementsByAccessibilityId("Discard").size() > 0) {
			appiumdriver.findElementByAccessibilityId("Discard").click();
		}
	}

	public ServiceRequestdetailsScreen clickBackServiceRequest() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 25);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId("Service Request"))).click();
		return new ServiceRequestdetailsScreen();
	}

	public boolean isInspectionExists(String inspection) {
		waitTeamInspectionsScreenLoaded();
		return appiumdriver.findElementsByAccessibilityId(inspection).size() > 0;
	}

	public void selectInspectionForApprove(String inspectionNumber) {
		selectInspectionInTable(inspectionNumber);
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

	public boolean isInspectionApproved(String inspectionNumber) {
		waitTeamInspectionsScreenLoaded();
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(appiumdriver.findElementByAccessibilityId(inspectionNumber)));
		return appiumdriver.findElementByAccessibilityId(inspectionNumber).findElements(MobileBy.AccessibilityId("EntityInfoButtonUnchecked")).size() > 0;
	}

	public String getFirstInspectionPriceValue() {
		return appiumdriver.findElementByXPath(firstinspxpath + "/XCUIElementTypeStaticText[3]").getAttribute("label");
	}

	public String getInspectionApprovedPriceValue(String inspectionNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
				.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspectionNumber + "']")));
		return appiumdriver.findElementByXPath("//XCUIElementTypeTable/XCUIElementTypeCell[@name='" + inspectionNumber + "']").findElement(MobileBy.name("labelInspectionApprovedAmount")).getAttribute("value");
	}

	public void clickApproveInspections() {
		clickActionButton();
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}

	public void clickActionButton() {
		appiumdriver.findElementByClassName("XCUIElementTypeToolbar").findElement(MobileBy.iOSNsPredicateString("name CONTAINS 'Share'")).click();
	}

	public void selectInspectionForAction(String inspectionNumber) {
		waitTeamInspectionsScreenLoaded();
		inspectionsTable.findElementByAccessibilityId(inspectionNumber).findElement(MobileBy.className("XCUIElementTypeOther")).click();
	}

	public void selectInspectionForEdit(String inspectionNumber)  {
		selectInspectionInTable(inspectionNumber);
		clickEditInspectionButton();
	}

	public void clickEditInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
	}

	public boolean isInspectionApproveButtonExists(String inspectionNumber) {
		return appiumdriver.findElementByAccessibilityId(inspectionNumber).findElements(MobileBy.iOSNsPredicateString("name contains 'EntityInfoButtonUnchecked'")).size() > 0;
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
		new TeamInspectionsScreen();
	}

	public boolean isDraftIconPresentForInspection(String inspectionNumber) {
		return appiumdriver.findElementByAccessibilityId(inspectionNumber).findElements(MobileBy.AccessibilityId("ESTIMATION_DRAFT"))
				.size() > 0;
	}

	public int getNumberOfRowsInTeamInspectionsTable() {
		return appiumdriver.findElements(By.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell")).size();
	}

	public boolean isWOIconPresentForInspection(String inspectionNumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy
				.AccessibilityId(inspectionNumber)));
		return appiumdriver.findElements(MobileBy.xpath("//XCUIElementTypeTable[1]/XCUIElementTypeCell[@name='" + inspectionNumber
				+ "']/XCUIElementTypeImage[@name='ESTIMATION_WO_CREATED']")).size() > 0;
	}
}

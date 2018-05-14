package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularTeamInspectionsScreen extends RegularBaseTypeScreenWithTabs {

	private final TypeScreenContext TEAMINSPECTIONCONTEXT = TypeScreenContext.TEAMINSPECTION;
	
	/*@iOSFindBy(accessibility = "InspectionsTable")
	private IOSElement inspectiontable;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(accessibility = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;*/
	
	public RegularTeamInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("InspectionsTable")));
	}
	
	public void selectInspectionForAction(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		IOSElement inptable = (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.name(inspnumber))); 
		appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.
				AccessibilityId(inspnumber)).click();
	}
	
	public void clickOnInspection(String inspnumber) {
		appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.AccessibilityId(inspnumber)).click();
	}
	
	public void selectInspectionForEdit(String inspnumber) {
		clickOnInspection(inspnumber);
		clickEditInspectionButton();
	}
	
	public void clickEditInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Edit").click();
		RegularBaseWizardScreen.typeContext = TEAMINSPECTIONCONTEXT;
	}
	
	public boolean isInspectionIsApproveButtonExists(String inspnumber) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElements(MobileBy.AccessibilityId("EntityInfoButtonUnchecked, ButtonImageId_76")).size() > 0;
	}
	
	public boolean isDraftIconPresentForInspection(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspnumber)));
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + inspnumber + "']/XCUIElementTypeImage[@name='ESTIMATION_DRAFT']")).size() > 0;
	}
	
	public boolean isInspectionExists(String inspection) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElements(MobileBy.AccessibilityId(inspection)).size() > 0;
	}
	
	public void clickActionButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Share"))).click(); 
	}
	
	public void clickApproveInspections() {
		clickActionButton();
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}
	
	public void selectEmployeeAndTypePassword(String employee, String password) {
		selectEmployee( employee);
		((IOSElement) appiumdriver.findElementByAccessibilityId("Enter password here")).setValue(password);
		Helpers.acceptAlert();
	}
	
	public void selectEmployee(String employee) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Employees")));
		appiumdriver.findElementByAccessibilityId(employee).click();
	}
	
	public boolean isApproveInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Approve").size() > 0;
	}
	
	public boolean isSendEmailInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Send\nEmail").size() > 0;
	}
	
	public void clickDoneButton() {
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public void selectInspectionForApprove(String inspnumber) {
		clickOnInspection(inspnumber);
		clickApproveInspectionButton();
	}
	
	protected void clickApproveInspectionButton() {
		appiumdriver.findElementByAccessibilityId("Approve").click();
	}
	
	public boolean checkInspectionIsApproved(String inspnumber) {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + inspnumber + "']/XCUIElementTypeOther")).getAttribute("name").equals("EntityInfoButtonUnchecked");
	}
	
	public String getFirstInspectionAprovedPriceValue() {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionApprovedAmount']")).getAttribute("label");
	}
	
	public String getFirstInspectionPriceValue() {
		return appiumdriver.findElementByAccessibilityId("InspectionsTable").findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionAmount']")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}

	public void clickBackButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();
	}

}

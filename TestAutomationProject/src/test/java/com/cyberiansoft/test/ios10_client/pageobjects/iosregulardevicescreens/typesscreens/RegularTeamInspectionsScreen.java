package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.typesscreens;

import com.cyberiansoft.test.ios10_client.appcontexts.TypeScreenContext;
import com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens.wizarscreens.RegularBaseWizardScreen;
import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class RegularTeamInspectionsScreen extends RegularBaseTypeScreenWithTabs {

	private final TypeScreenContext TEAMINSPECTIONCONTEXT = TypeScreenContext.TEAMINSPECTION;
	
	@iOSXCUITFindBy(accessibility = "InspectionsTable")
	private IOSElement inspectiontable;

	@iOSXCUITFindBy(accessibility = "Approve")
	private IOSElement approvepopupmenu;
	
	@iOSXCUITFindBy(accessibility = "Edit")
    private IOSElement editpopupmenu;
	
	/*@iOSXCUITFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;*/
	
	public RegularTeamInspectionsScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}

	public void waitTeamInspectionsScreenLoaded() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("InspectionsTable")));
	}
	
	public void selectInspectionForAction(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		IOSElement inptable = (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.name(inspnumber)));
		inspectiontable.findElement(MobileBy.
				AccessibilityId(inspnumber)).click();
	}
	
	public void clickOnInspection(String inspnumber) {
		inspectiontable.findElement(MobileBy.AccessibilityId(inspnumber)).click();
	}
	
	public void selectInspectionForEdit(String inspnumber) {
		clickOnInspection(inspnumber);
		clickEditInspectionButton();
	}
	
	public void clickEditInspectionButton() {
		editpopupmenu.click();
		RegularBaseWizardScreen.typeContext = TEAMINSPECTIONCONTEXT;
	}
	
	public boolean isInspectionIsApproveButtonExists(String inspectionID) {
		return inspectiontable.findElements(MobileBy.AccessibilityId("EntityInfoButtonUnchecked, ButtonImageId_76")).size() > 0;
	}
	
	public boolean isDraftIconPresentForInspection(String inspectionID) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspectionID)));
		return inspectiontable.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + inspectionID + "']/XCUIElementTypeImage[@name='ESTIMATION_DRAFT']")).size() > 0;
	}
	
	public boolean isInspectionExists(String inspectionID) {
		waitTeamInspectionsScreenLoaded();
		return inspectiontable.findElements(MobileBy.AccessibilityId(inspectionID)).size() > 0;
	}
	
	public void clickActionButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("Share"))).click(); 
	}
	
	public void clickApproveInspections() {
		clickActionButton();
		approvepopupmenu.click();
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
		approvepopupmenu.click();
	}
	
	public boolean checkInspectionIsApproved(String inspnumber) {
		return inspectiontable.findElement(MobileBy.xpath("//XCUIElementTypeCell[@name='" + inspnumber + "']/XCUIElementTypeOther")).getAttribute("name").equals("EntityInfoButtonUnchecked");
	}
	
	public String getFirstInspectionAprovedPriceValue() {
		return inspectiontable.findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionApprovedAmount']")).getAttribute("label");
	}
	
	public String getFirstInspectionPriceValue() {
		return inspectiontable.findElement(By.xpath("//XCUIElementTypeCell[1]/XCUIElementTypeStaticText[@name='labelInspectionAmount']")).getAttribute("label");
		//return firstinspectionprice.getAttribute("label");
	}

	public void clickBackButton() {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);

		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.name("Back"))).click();
	}

}

package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

public class RegularTeamInspectionsScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "TeamInspectionsTable")
	private IOSElement inspectiontable;
	
	@iOSFindBy(accessibility = "Edit")
    private IOSElement editpopupmenu;
	
	@iOSFindBy(accessibility = "Approve")
    private IOSElement approvepopupmenu;
	
	@iOSFindBy(accessibility = "Done")
    private IOSElement toolbardonebtn;
	
	public RegularTeamInspectionsScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("TeamInspectionsTable"))); 
	}
	
	public void selectInspectionForAction(String inspnumber) {
		FluentWait<WebDriver> wait = new WebDriverWait(appiumdriver, 5);
		IOSElement inptable = (IOSElement) wait.until(ExpectedConditions.presenceOfElementLocated(By.name("TeamInspectionsTable"))); 
		inptable.findElementByAccessibilityId(inspnumber).findElementByClassName("XCUIElementTypeOther").click();
	}
	
	public void clickOnInspection(String inspnumber) {
		inspectiontable.findElementByAccessibilityId(inspnumber).click();
	}
	
	public void selectInspectionForEdit(String inspnumber) {
		clickOnInspection(inspnumber);
		clickEditInspectionButton();
	}
	
	public void clickEditInspectionButton() {
		editpopupmenu.click();
		//return new RegularVehicleScreen(appiumdriver);
	}
	
	public boolean isInspectionIsApproveButtonExists(String inspnumber) {
		return inspectiontable.findElementsByAccessibilityId("EntityInfoButtonUnchecked, ButtonImageId_76").size() > 0;
	}
	
	public boolean isDraftIconPresentForInspection(String inspnumber) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId(inspnumber)));
		return inspectiontable.findElements(MobileBy.xpath("//XCUIElementTypeCell[@name='" + inspnumber + "']/XCUIElementTypeImage[@name='ESTIMATION_DRAFT']")).size() > 0;
	}
	
	public void assertInspectionExists(String inspection) {
		Assert.assertTrue(inspectiontable.findElementsByName(inspection).size() > 0);
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
		appiumdriver.findElementByAccessibilityId(employee).click();
	}
	
	public boolean isApproveInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Approve").size() > 0;
	}
	
	public boolean isSendEmailInspectionMenuActionExists() {
		return appiumdriver.findElementsByAccessibilityId("Send\nEmail").size() > 0;
	}
	
	public void clickDoneButton() {
		toolbardonebtn.click();
	}
	
	

}

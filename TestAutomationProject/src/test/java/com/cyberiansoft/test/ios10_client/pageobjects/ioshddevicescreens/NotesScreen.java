package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotesScreen extends iOSHDBaseScreen {

	@iOSXCUITFindBy(accessibility = "Done")
    private IOSElement donebtn;

	@iOSXCUITFindBy(accessibility = "txtNotes")
	private IOSElement testNotesFld;

	@iOSXCUITFindBy(accessibility = "table")
	private IOSElement quickNotesTable;
	
	@iOSXCUITFindBy(accessibility  = "ImageSelectorCameraButton")
    private IOSElement camerabtn;
	
	@iOSXCUITFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;
	
	public NotesScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void setNotes(String _notes) {
		testNotesFld.click();
		testNotesFld.clear();
		testNotesFld.sendKeys(_notes);
		((IOSDriver) appiumdriver).hideKeyboard();
	}

	public void addQuickNotes(String quickNote) {
		quickNotesTable.findElementByAccessibilityId(quickNote).click();
	}
	
	public String getNotesValue() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("txtNotes")));
		return appiumdriver.findElementByAccessibilityId("txtNotes")
						.getAttribute("value");
	}
	
	public String getAddedNotesText() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("txtNotes")));
		return testNotesFld.getAttribute("value");
	}

	public void clickDoneButton() {
		donebtn.click();
	}
	
	public void clickCameraButton() {
		camerabtn.click();
	}
	
	public void addNotesCapture() {
		clickCameraButton();
		Helpers.makeCapture();
		cancelbtn.click();
	}
	
	public int getNumberOfAddedPhotos() {
		return appiumdriver.findElements(MobileBy.name("ImageSelectorImage")).size();
	}

	public void clickSaveButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("Save")));
		appiumdriver.findElementByAccessibilityId("Save").click();
		if (appiumdriver.findElementsByAccessibilityId("Connecting to Back Office").size() > 0) {
			wait = new WebDriverWait(appiumdriver, 30);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.AccessibilityId("Connecting to Back Office")));
		}
	}

}

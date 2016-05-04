package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindAll;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class NotesScreen extends iOSHDBaseScreen {

	final static String quicknotesvalue = "This is test Quick Notes";

	@iOSFindBy(xpath = "//UIAButton[@visible=\"true\" and (contains(@name,\"Done\"))] ")
    private IOSElement donebtn;
	
	@iOSFindBy(name = "ImageSelectorCameraButton")
    private IOSElement camerabtn;
	
	@iOSFindBy(name = "Cancel")
    private IOSElement cancelbtn;
	
	public NotesScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setNotes(String _notes) throws InterruptedException {
		appiumdriver.findElementByXPath(getRootXPath()  + "/UIATextView[1]").click();
		((IOSElement) appiumdriver.findElementByXPath(getRootXPath()  + "/UIATextView[1]")).setValue(_notes);
		Helpers.hideKeyboard();
	}
	
	public String getRootXPath() {
		String xpath = null;
		if (elementExists("//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]/UIATextView[1]")) {
			    xpath = "//UIAApplication[1]/UIAWindow[1]/UIAScrollView[1]";
		} else {
			xpath = "//UIAApplication[1]/UIAWindow[1]";
		}
		return xpath;
	}

	public void addQuickNotes() throws InterruptedException {
		appiumdriver.findElementByXPath(getRootXPath()  + "/UIATableView[1]/UIATableCell[@name=\""
						+ quicknotesvalue + "\"] ").click();
		//Helpers.hideKeyboard();
	}

	public void assertNotesAndQuickNotes(String _notes) {
		Assert.assertEquals(appiumdriver.findElementByXPath(getRootXPath()  + "/UIATextView[1]")
						.getText(), _notes + "\n" + quicknotesvalue);
	}

	public void clickDoneButton() {
		donebtn.click();
	}
	
	public void clickCameraButton() {
		camerabtn.click();
	}
	
	public void addNotesCapture() throws InterruptedException {
		clickCameraButton();
		Helpers.makeCapture();
		cancelbtn.click();
	}
	
	public int getNumberOfAddedPhotos() {
		return appiumdriver.findElements(MobileBy.name("ImageSelectorImage")).size();
	}

}

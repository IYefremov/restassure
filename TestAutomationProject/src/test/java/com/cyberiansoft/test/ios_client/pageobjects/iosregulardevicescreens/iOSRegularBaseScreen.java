package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class iOSRegularBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//UIAPicker";
	
	@iOSFindBy(uiAutomator = ".navigationBars()[0].buttons()[\"Back\"]")
    private IOSElement backbtn;
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(name = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = uipickerxpath)
    private IOSElement picker;
	
	@iOSFindBy(xpath = uipickerxpath + "/UIAPickerWheel[1]")
    private IOSElement pickerwheel;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[4]")
    private IOSElement changescreenbtn;
	
	public iOSRegularBaseScreen(AppiumDriver driver) {
		super(driver);
	}
	
	public RegularHomeScreen clickHomeButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.visibilityOf(backbtn)).click();
		Thread.sleep(1000);
		return new RegularHomeScreen(appiumdriver);
	}
	
	public void clickSaveButton() {
		if (!Helpers.elementExists(By.name("Save"))) {
			clickChangeScreen();
		}
		savebtn.click();
	}
	
	public void cancelOrder() {
		clickChangeScreen();
		clickCancel();
		acceptAlert();
	}
	
	public void clickCancel() {
		cancelbtn.click();
	}
	
	public void clickChangeScreen() {
		changescreenbtn.click();
	}
	
	public void acceptAlertByCoords() {
		int xx = appiumdriver.manage().window().getSize().getWidth();
		int yy = appiumdriver.manage().window().getSize().getHeight();
		appiumdriver.tap(1, xx/2+50, yy/2+50, 1000);
	}
	
	public void declineAlertByCoords() {
		int xx = appiumdriver.manage().window().getSize().getWidth();
		int yy = appiumdriver.manage().window().getSize().getHeight();
		appiumdriver.tap(1, xx/2-50, yy/2+50, 1000);
	}
	
	public void swipeScreenUp() {
		Dimension size = appiumdriver.manage().window().getSize();
		int starty = (int) (size.height * 0.80);
		//Find endy point which is at top side of screen.
		int endy = (int) (size.height * 0.20);
		//Find horizontal point where you wants to swipe. It is in middle of screen width.
		int startx = size.width / 2;
		//System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);
		//Swipe from Bottom to Top.
		appiumdriver.swipe(startx, starty, startx, endy, 3000);
	}

}

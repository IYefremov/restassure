package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens.iOSBaseScreen;
import com.cyberiansoft.test.ios_client.utils.Helpers;

public class iOSRegularBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//UIAPicker";
	
	//@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@visible=\"true\" and @name=\"Back\"]")
    //private IOSElement backbtn;
	
	@iOSFindBy(uiAutomator = ".navigationBars()[0].buttons()[\"Back\"]")
    private IOSElement backbtn;
	
	@iOSFindBy(name = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(xpath = "//UIAButton[@name='Cancel']")
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
		Thread.sleep(1000);
		//WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		//wait.until(ExpectedConditions.visibilityOf(backbtn)).click();
		backbtn.click();
		//Thread.sleep(1000);
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
		appiumdriver.tap(1, cancelbtn, 200);
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
	
	public void selectNextScreen(String screenname) {
		waitUntilVisible("//UIANavigationBar[1]/UIAButton[4]").click();
		Helpers.scroolTo(screenname);
		element(
				MobileBy.xpath("//UIATableView[1]/UIATableCell/UIAStaticText[@name=\""
						+ screenname + "\"]")).click();
		Helpers.waitABit(1000);
	}
	
	public WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

}

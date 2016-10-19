package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.ios_client.utils.Helpers;
public class iOSHDBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//UIAPicker";
	
	//@iOSFindBy(uiAutomator = ".navigationBars()[0].buttons()[\"Back\"].withValueForKey(1, \"isVisible\")")
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@visible=\"true\" and @name=\"Back\"]")
	private IOSElement backbtn;
	
	@iOSFindBy(accessibility  = "Save")
    private IOSElement savebtn;
	
	@iOSFindBy(accessibility  = "Cancel")
    private IOSElement cancelbtn;
	
	@iOSFindBy(xpath = uipickerxpath)
    private IOSElement picker;
	
	@iOSFindBy(xpath = uipickerxpath + "/UIAPickerWheel[1]")
    private IOSElement pickerwheel;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[4]")
    private IOSElement changescreenbtn;
	
	public iOSHDBaseScreen(AppiumDriver driver) {
		super(driver);
	}
	
	public HomeScreen clickHomeButton() {
		backbtn.click();		
		return new HomeScreen(appiumdriver);
	}
	
	public void clickSaveButton() {
		savebtn.click();
	}
	
	public void cancelOrder() {
		cancelbtn.click();
		acceptAlert();
	}
	
	public void selectNextScreen(String screenname) {
		waitUntilVisible("//UIANavigationBar[1]/UIAButton[4]").click();
		Helpers.scroolTo(screenname);
		element(
				MobileBy.xpath("//UIAPopover[1]/UIATableView[1]/UIATableCell/UIAStaticText[@name='" + screenname + "']")).click();
		Helpers.waitABit(1000);
	}
	
	public WebElement waitUntilVisible(String xpath) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

}

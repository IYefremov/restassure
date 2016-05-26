package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.iOSFindBy;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class iOSHDBaseScreen extends iOSBaseScreen {
	
	
	//final String uipickerxpath = ".popover().pickers()[0]";
	final String uipickerxpath = "//UIAPicker";
	
	//@iOSFindBy(uiAutomator = ".navigationBars()[0].buttons()[\"Back\"].withValueForKey(1, \"isVisible\")")
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@visible=\"true\" and @name=\"Back\"]")
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

}

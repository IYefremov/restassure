package com.cyberiansoft.test.ios_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

public class RegularInspectionToolBar extends iOSRegularBaseScreen {
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAToolbar[1]/UIAStaticText[@name='SubtotalAmount']")
    private IOSElement inspsubtotal;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIAToolbar[1]/UIAStaticText[@name='TotalAmount']")
    private IOSElement insptotal;
	
	public RegularInspectionToolBar(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String getInspectionSubTotalPrice() {
		return inspsubtotal.getAttribute("value");
	}
	
	public String getInspectionTotalPrice() {
		return insptotal.getAttribute("value");
	}

}

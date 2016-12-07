package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

public class InspectionToolBar extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "SubtotalAmount")
    private IOSElement inspsubtotal;
	
	@iOSFindBy(accessibility = "TotalAmount")
    private IOSElement insptotal;
	
	public InspectionToolBar(AppiumDriver driver) {
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

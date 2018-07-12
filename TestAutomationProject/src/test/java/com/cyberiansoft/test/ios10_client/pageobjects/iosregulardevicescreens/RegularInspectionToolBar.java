package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class RegularInspectionToolBar extends iOSRegularBaseScreen {
	
	@iOSFindBy(accessibility = "SubtotalAmount")
    private IOSElement inspsubtotal;
	
	@iOSFindBy(accessibility = "TotalAmount")
    private IOSElement insptotal;
	
	public RegularInspectionToolBar() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public String getInspectionSubTotalPrice() {
		return inspsubtotal.getAttribute("value");
	}
	
	public String getInspectionTotalPrice() {
		return insptotal.getAttribute("value");
	}

}

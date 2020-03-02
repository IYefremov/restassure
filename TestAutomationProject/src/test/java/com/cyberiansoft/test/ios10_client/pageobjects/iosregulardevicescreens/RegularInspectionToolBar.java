package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

public class RegularInspectionToolBar extends iOSRegularBaseScreen {
	
	@iOSXCUITFindBy(accessibility = "SubtotalAmount")
    private IOSElement inspsubtotal;
	
	@iOSXCUITFindBy(accessibility = "TotalAmount")
    private IOSElement insptotal;
	
	public RegularInspectionToolBar() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public String getInspectionSubTotalPrice() {
		return inspsubtotal.getAttribute("value");
	}
	
	public String getInspectionTotalPrice() {
		return insptotal.getAttribute("value");
	}

}

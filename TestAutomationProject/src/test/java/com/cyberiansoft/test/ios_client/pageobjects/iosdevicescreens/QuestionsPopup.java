package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

public class QuestionsPopup extends iOSHDBaseScreen {
	
	public QuestionsPopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void answerQuestion2(String answer) {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()[3]")).click();
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].tableViews()[0].cells()['" + answer + "']")).click();		
	}

}

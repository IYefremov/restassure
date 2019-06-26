package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class QuestionsPopup extends iOSHDBaseScreen {
	
	public QuestionsPopup() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
	}
	
	public void answerQuestion2(String answer) {
		appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Question 2")).click();
		appiumdriver.findElement(MobileBy.AccessibilityId(answer)).click();		
	}

	public String getQuestion2Value() {
		return appiumdriver.findElement(MobileBy.AccessibilityId("QuestionTypeSelect_Question 2")).getAttribute("label");
	}

}

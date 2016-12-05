package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios10_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class RegularEnterpriseBeforeDamageScreen extends iOSRegularBaseScreen {
	
	final static String enterprisebeforedamagescreencapt = "Enterprise Before Damage";
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIATableView[1]/UIATableCell[2]")
    private IOSElement vinbtn;
	
	@iOSFindBy(xpath = "//UIAScrollView[2]/UIATableView[1]/UIATableCell[4]")
    private IOSElement licenseplatebtn;
	
	@iOSFindBy(xpath = "//UIAButton[@name=\"PhotoCapture\"]")
    private IOSElement photocapturebtn;
	
	@iOSFindBy(xpath = "//UIAButton[@name=\"Use Photo\"]")
    private IOSElement usephotobtn;
	
	@iOSFindBy(xpath = "//UIANavigationBar[1]/UIAButton[@name=\"Back\"]")
    private IOSElement backbtn;
	
	public RegularEnterpriseBeforeDamageScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static String getEnterpriseBeforeDamageScreenCaption() {
		return enterprisebeforedamagescreencapt;
	}

	public void setVINCapture() throws InterruptedException {
		vinbtn.click();
		Helpers.makeCapture();
		backbtn.click();
	}

	
	public void setLicensePlateCapture() throws InterruptedException {
		licenseplatebtn.click();
		Helpers.makeCapture();
		backbtn.click();
	}
	
	public void makeCapture() {
		photocapturebtn.click();
		usephotobtn.click();
	}
	
}

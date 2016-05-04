package com.cyberiansoft.test.ios_client.pageobjects.iosdevicescreens;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class EnterpriseBeforeDamageScreen extends iOSHDBaseScreen {
	
	final static String enterprisebeforedamagescreencapt = "Enterprise Before Damage";
	
	@iOSFindBy(xpath = "//UIAButton[@name=\"VIN\"]")
    private IOSElement vinbtn;
	
	@iOSFindBy(xpath = "//UIAButton[@name=\"License Plate\"]")
    private IOSElement licenseplatebtn;
	
	@iOSFindBy(xpath = "//UIAButton[@name=\"PhotoCapture\"]")
    private IOSElement photocapturebtn;
	
	@iOSFindBy(xpath = "//UIAButton[@name=\"Use Photo\"]")
    private IOSElement usephotobtn;
	
	@iOSFindBy(xpath = "//UIAPopover[1]/UIANavigationBar[1]/UIAButton[@name=\"Close\"]")
    private IOSElement capturedonebtn;
	
	public EnterpriseBeforeDamageScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static String getEnterpriseBeforeDamageScreenCaption() {
		return enterprisebeforedamagescreencapt;
	}

	public void setVINCapture() throws InterruptedException {
		vinbtn.click();
		makeCapture();
		capturedonebtn.click();
	}

	
	public void setLicensePlateCapture() throws InterruptedException {
		licenseplatebtn.click();
		makeCapture();
		capturedonebtn.click();
	}
	
	public void makeCapture() {
		photocapturebtn.click();
		usephotobtn.click();
	}
	
}

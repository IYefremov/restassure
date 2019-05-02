package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens.wizardscreens;

import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class EnterpriseBeforeDamageScreen extends BaseWizardScreen {
	
	final static String enterprisebeforedamagescreencapt = "Enterprise Before Damage";
	
	@iOSXCUITFindBy(xpath = "//UIAButton[@name=\"VIN\"]")
    private IOSElement vinbtn;
	
	@iOSXCUITFindBy(xpath = "//UIAButton[@name=\"License Plate\"]")
    private IOSElement licenseplatebtn;
	
	@iOSXCUITFindBy(xpath = "//UIAButton[@name=\"PhotoCapture\"]")
    private IOSElement photocapturebtn;
	
	@iOSXCUITFindBy(xpath = "//UIAButton[@name=\"Use Photo\"]")
    private IOSElement usephotobtn;
	
	@iOSXCUITFindBy(xpath = "//UIAPopover[1]/UIANavigationBar[1]/UIAButton[@name=\"Close\"]")
    private IOSElement capturedonebtn;
	
	public EnterpriseBeforeDamageScreen() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public static String getEnterpriseBeforeDamageScreenCaption() {
		return enterprisebeforedamagescreencapt;
	}

	public void setVINCapture() {
		vinbtn.click();
		makeCapture();
		capturedonebtn.click();
	}

	
	public void setLicensePlateCapture() {
		licenseplatebtn.click();
		makeCapture();
		capturedonebtn.click();
	}
	
	public void makeCapture() {
		photocapturebtn.click();
		usephotobtn.click();
	}
	
}

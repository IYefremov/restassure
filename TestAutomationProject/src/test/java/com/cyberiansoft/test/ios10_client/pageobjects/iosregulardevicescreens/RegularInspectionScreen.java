package com.cyberiansoft.test.ios10_client.pageobjects.iosregulardevicescreens;

import com.cyberiansoft.test.ios10_client.utils.Helpers;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class RegularInspectionScreen extends iOSRegularBaseScreen {
	
	@iOSFindBy(uiAutomator = ".popover().navigationBar().buttons()[\"Save\"]")
    private IOSElement savechangesbtn;
	
	@iOSFindBy(accessibility = "Advisor")
    private IOSElement advisorcell;

	public RegularInspectionScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void saveChanges() {
		savechangesbtn.click();
	}

	public String clickSaveWithAlert() {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}

	public void setVIN(String vin) throws InterruptedException  {
		Helpers.setVIN(vin);
	}

	public String getMake() {
		return Helpers.getMake();
	}

	public String getModel() {
		return Helpers.getModel();

	}

	public String getYear() {
		return Helpers.getYear();
	}
	
	public void seletAdvisor(String advisor) {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Advisor']")));
		advisorcell.click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
	}

}

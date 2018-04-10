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

	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Make']/XCUIElementTypeTextField")
	private IOSElement makefld;

	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Model']/XCUIElementTypeTextField")
	private IOSElement modelfld;

	@iOSFindBy(xpath = "//XCUIElementTypeCell[@name='Year']/XCUIElementTypeTextField")
	private IOSElement yearfldvalue;
	
	@iOSFindBy(accessibility = "Advisor")
    private IOSElement advisorcell;

	public RegularInspectionScreen(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void saveChanges() {
		clickSaveButton();
	}

	public String clickSaveWithAlert() {
		clickSaveButton();
		return Helpers.getAlertTextAndAccept();
	}

	public void setVIN(String vin) throws InterruptedException  {
		Helpers.setVIN(vin);
	}

	public String getMake() {
		return makefld.getAttribute("value");
	}

	public String getModel() {
		return modelfld.getAttribute("value");

	}

	public String getYear() {
		return yearfldvalue.getAttribute("value");
	}
	
	public void seletAdvisor(String advisor) {
		WebElement table = appiumdriver.findElementByAccessibilityId("VehicleInfoTable");
		swipeToElement(table.findElement(By.xpath("//XCUIElementTypeCell[@name='Advisor']")));
		advisorcell.click();
		appiumdriver.findElementByAccessibilityId(advisor).click();
	}

}

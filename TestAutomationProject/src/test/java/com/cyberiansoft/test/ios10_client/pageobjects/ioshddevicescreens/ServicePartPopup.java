package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

public class ServicePartPopup extends iOSHDBaseScreen {
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Category']")
    private IOSElement categorycell;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Category'].staticTexts()[1]")
    private IOSElement categoryvaluecell;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Subcategory']")
    private IOSElement subcategorycell;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Subcategory'].staticTexts()[1]")
    private IOSElement subcategoryvaluecell;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Part']")
    private IOSElement partcell;
	
	@iOSFindBy(uiAutomator = ".popovers()[0].tableViews()[0].cells()['Position']")
    private IOSElement positioncell;
	
	public ServicePartPopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectServicePartCategory(String categoryname) {
		categorycell.click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public String getServicePartCategoryValue() {
		return categoryvaluecell.getAttribute("value");
	}
	
	public void selectCategory(String categoryname) {
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public void selectServicePartSubcategory(String subcategoryname) {
		subcategorycell.click();
		appiumdriver.findElementByAccessibilityId(subcategoryname).click();
	}
	
	public String getServicePartSubCategoryValue() {
		return subcategoryvaluecell.getAttribute("value");
	}
	
	public void selectServicePartSubcategoryPart(String subcategorypartname) {
		partcell.click();
		appiumdriver.findElementByAccessibilityId(subcategorypartname).click();
	}
	
	public void selectServicePartSubcategoryPosition(String subcategorypositionname) {
		positioncell.click();
		appiumdriver.findElementByAccessibilityId(subcategorypositionname).click();
	}
	
	public void saveSelectedServicePart() throws InterruptedException {
		appiumdriver.findElement(MobileBy.IosUIAutomation(".popovers()[0].navigationBars()[0].buttons()['Save']")).click();	
	}
	

}

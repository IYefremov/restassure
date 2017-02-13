package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cyberiansoft.test.ios_client.utils.Helpers;

public class ServicePartPopup extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "Category")
    private IOSElement categorycell;
	
	@iOSFindBy(accessibility = "Subcategory")
    private IOSElement subcategorycell;
	
	@iOSFindBy(accessibility = "Part")
    private IOSElement partcell;
	
	@iOSFindBy(accessibility = "Position")
    private IOSElement positioncell;
	
	public ServicePartPopup(AppiumDriver driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectServicePartCategory(String categoryname) {
		categorycell.click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public String getServicePartCategoryValue() {
		WebElement par = getTableParentCell("Category");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public void selectCategory(String categoryname) {
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public void selectServicePartSubcategory(String subcategoryname) {
		subcategorycell.click();
		appiumdriver.findElementByAccessibilityId(subcategoryname).click();
	}
	
	public String getServicePartSubCategoryValue() {
		WebElement par = getTableParentCell("Subcategory");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public void selectServicePartSubcategoryPart(String subcategorypartname) {
		partcell.click();
		appiumdriver.findElementByAccessibilityId(subcategorypartname).click();
		Helpers.waitABit(500);
	}
	
	public void selectServicePartSubcategoryPosition(String subcategorypositionname) {
		positioncell.click();
		appiumdriver.findElementByAccessibilityId(subcategorypositionname).click();
		Helpers.waitABit(500);
	}
	
	public void saveSelectedServicePart() throws InterruptedException {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeOther/XCUIElementTypeNavigationBar[@name='Service Part']/XCUIElementTypeButton[@name='Save']")).click();	
		Helpers.waitABit(500);
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}
	

}

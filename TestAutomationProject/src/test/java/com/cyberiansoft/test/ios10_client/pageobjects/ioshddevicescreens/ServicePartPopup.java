package com.cyberiansoft.test.ios10_client.pageobjects.ioshddevicescreens;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class ServicePartPopup extends iOSHDBaseScreen {
	
	@iOSFindBy(accessibility = "Category")
    private IOSElement categorycell;
	
	@iOSFindBy(accessibility = "Subcategory")
    private IOSElement subcategorycell;
	
	@iOSFindBy(accessibility = "Part")
    private IOSElement partcell;
	
	@iOSFindBy(accessibility = "Position")
    private IOSElement positioncell;
	
	public ServicePartPopup() {
		super();
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		appiumdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void selectServicePartCategory(String categoryname) {
		//categorycell.click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public String getServicePartCategoryValue() {
		WebElement par = getTableParentCell("Category");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public void selectCategory(String categoryname) {
		//categorycell.click();
		appiumdriver.findElementByAccessibilityId(categoryname).click();
	}
	
	public void selectServicePartSubcategory(String subcategoryname) {

		appiumdriver.findElementByAccessibilityId(subcategoryname).click();
	}

	public void clickSubcategoryCell() {
		subcategorycell.click();
	}
	
	public String getServicePartSubCategoryValue() {
		WebElement par = getTableParentCell("Subcategory");
		return par.findElement(By.xpath("//XCUIElementTypeStaticText[2]")).getAttribute("value");
	}
	
	public void selectServicePartSubcategoryPart(String subcategorypartname) {
		//partcell.click();
		appiumdriver.findElementByAccessibilityId(subcategorypartname).click();
		appiumdriver.findElementByAccessibilityId("Done").click();
	}
	
	public void selectServicePartSubcategoryPosition(String subcategorypositionname) {
		//positioncell.click();
		appiumdriver.findElementByAccessibilityId(subcategorypositionname).click();
	}
	
	public void saveSelectedServicePart() {
		appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeOther/XCUIElementTypeNavigationBar[@name='Selected Parts']/XCUIElementTypeButton[@name='Done']")).click();
	}
	
	public WebElement getTableParentCell(String cellname) {
		return appiumdriver.findElement(MobileBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell/XCUIElementTypeStaticText[@label='" + cellname + "']/.."));
	}
	

}

package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextInspectionTypesList extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='entity-types']")
	private WebElement insptypeslist;
	
	public VNextInspectionTypesList(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		//PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 90);
		wait.until(ExpectedConditions.visibilityOf(insptypeslist));
	}
	
	public void selectInspectionType(String inspectionType) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='item-title']/div[text()='" + inspectionType + "']")));
		tap(insptypeslist.findElement(By.xpath(".//div[@class='item-title']/div[text()='" + inspectionType + "']")));
	}
	

}

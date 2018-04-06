package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextVehiclePartsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='parts']")
	private WebElement vehiclepartsscreen;
	
	public VNextVehiclePartsScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartsscreen));
		if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed()) {
			tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
		}
	}
	
	public WebElement getVehiclePartsList() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartsscreen.findElement(By.xpath(".//*[@data-autotests-id='matrix-parts-list']"))));
		return vehiclepartsscreen.findElement(By.xpath(".//*[@data-autotests-id='matrix-parts-list']"));
	}
	
	public VNextVehiclePartInfoPage selectVehiclePart(String vehiclepartname) {
		WebElement vpcell = getVehiclePartCell(vehiclepartname);
		if (vpcell != null)
			tap(vpcell.findElement(By.xpath(".//input[@type='checkbox']")));
		else
			Assert.assertTrue(false, "Can't find Vehicle Part: " + vehiclepartname);
		return new VNextVehiclePartInfoPage(appiumdriver);
		
	}
	
	public WebElement getVehiclePartCell(String vehiclepartname) {
		WebElement vpcell = null;
		List<WebElement> vehicleparts = getVehiclePartsList().findElements(By.xpath(".//div[@action='select-item']"));
		for (WebElement vehiclepartcell : vehicleparts) {
			if (vehiclepartcell.findElements(By.xpath(".//div[@class='checkbox-list-title' and text()='" + vehiclepartname + "']")).size() > 0) {
				vpcell = vehiclepartcell;
				break;
			}
		}
		return vpcell;
	}
	
	public VNextSelectServicesScreen clickVehiclePartsBackButton() {
		clickScreenBackButton();
		return new VNextSelectServicesScreen(appiumdriver);
		
	}
}
 
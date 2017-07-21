package com.cyberiansoft.test.vnext.screens;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextVehiclePartsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='parts']")
	private WebElement vehiclepartsscreen;
	
	@FindBy(xpath="//a[@action='back']")
	private WebElement backbtn;
	
	public VNextVehiclePartsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartsscreen));
	}
	
	public WebElement getVehiclePartsList() {
		return vehiclepartsscreen.findElement(By.xpath(".//div[@class='list-selectable']"));
	}
	
	public VNextVehiclePartInfoPage selectVehiclePart(String vehiclepartname) {
		WebElement vpcell = getVehiclePartCell(vehiclepartname);
		if (vpcell != null)
			tap(vpcell.findElement(By.xpath(".//input[@type='checkbox']")));
		else
			Assert.assertTrue(false, "Can't find Vehicle Part: " + vehiclepartname);
		log(LogStatus.INFO, "Select Vehicle Part: " + vehiclepartname);
		return new VNextVehiclePartInfoPage(appiumdriver);
		
	}
	
	public WebElement getVehiclePartCell(String vehiclepartname) {
		WebElement vpcell = null;
		List<WebElement> vehicleparts = getVehiclePartsList().findElements(By.xpath(".//div[@action='select-item']"));
		for (WebElement vehiclepartcell : vehicleparts) {
			if (vehiclepartcell.findElements(By.xpath(".//div[@class='item-title' and text()='" + vehiclepartname + "']")).size() > 0) {
				vpcell = vehiclepartcell;
				break;
			}
		}
		return vpcell;
	}
	
	public VNextSelectServicesScreen clickVehiclePartsBackButton() {
		tap(backbtn);
		log(LogStatus.INFO, "Click Vehicle Parts Back button");
		return new VNextSelectServicesScreen(appiumdriver);
		
	}
}
 
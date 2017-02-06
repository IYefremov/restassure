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

public class VNextVehiclePartInfoPage extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement vehiclepartinfoscreen;
	
	@FindBy(xpath="//div[@action='size']/i")
	private WebElement vehiclepartsizeselect;
	
	@FindBy(xpath="//div[@action='severity']")
	private WebElement vehiclepartseverityselect;
	
	@FindBy(xpath="//div[@input='price']")
	private WebElement vehiclepartpricefld;
	
	@FindBy(id="additional-services")
	private WebElement additionalserviceslist;
	
	@FindBy(xpath="//a[@action='save']/i")
	private WebElement savebtn;
	
	public VNextVehiclePartInfoPage(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartinfoscreen));
	}
	
	public void selectVehiclePartSize(String vehiclepartsize) {
		tap(vehiclepartsizeselect);
		tap(appiumdriver.findElement(By.xpath("//div[@class='item-title' and text()='" + vehiclepartsize + "']")));
		log(LogStatus.INFO, "Select Vehicle Part size: " + vehiclepartsize);
	}
	
	public void selectVehiclePartSeverity(String vehiclepartseverity) {
		tap(vehiclepartseverityselect);
		tap(appiumdriver.findElement(By.xpath("//div[@class='item-title' and text()='" + vehiclepartseverity + "']")));
		log(LogStatus.INFO, "Select Vehicle Part size: " + vehiclepartseverity);
	}
	
	public void selectVehiclePartAdditionalService(String additionalservicename) {
		WebElement addservs = getVehiclePartAdditionalServiceCell(additionalservicename);
		if (addservs != null)
			tap(addservs.findElement(By.xpath(".//div[@class='item-check ']/i")));
		else
			Assert.assertTrue(false, "Can't find additional servicve: " + additionalservicename);
		log(LogStatus.INFO, "Select additional servivce: " + additionalservicename);			
	}
	
	public void selectAllAvailableAdditionalServices() {
		List<WebElement> addservs = additionalserviceslist.findElements(By.xpath(".//a[@class='item-link item-content']"));
		for (WebElement additinalservice : addservs) {
			additinalservice.findElement(By.xpath(".//div[@action='select']")).click();
			waitABit(500);
		}
	}
	
	public WebElement getVehiclePartAdditionalServiceCell(String additionalservicename) {
		WebElement addsrvc = null;
		List<WebElement> addservs = additionalserviceslist.findElements(By.xpath(".//a[@class='item-link item-content']"));
		for (WebElement additinalservice : addservs)
			if (additinalservice.findElements(By.xpath(".//div[@class='item-title' and text()='" + additionalservicename + "']")).size() > 0) {
				addsrvc = additinalservice;
				break;
			}
		return addsrvc;
	}
	
	public void clickSaveVehiclePartInfo() {
		tap(savebtn);
		log(LogStatus.INFO, "Click Save Vehicle Part Info button");
	}

}

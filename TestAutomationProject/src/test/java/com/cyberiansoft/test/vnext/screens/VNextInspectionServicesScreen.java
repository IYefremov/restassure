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

public class VNextInspectionServicesScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[contains(@class, 'page inspections-service hide-searchbar page-on-center')]")
	private WebElement servicesscreen;
	
	@FindBy(xpath="//i[@action='add']")
	private WebElement addservicesbtn;
	
	@FindBy(xpath="//i[@action='save']")
	private WebElement savebtn;
	
	@FindBy(xpath="//i[@action='back']")
	private WebElement backbtn;
	
	@FindBy(xpath="//div[@class='list-block services-added']")
	private WebElement addedserviceslist;

	public VNextInspectionServicesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(addservicesbtn));
	}
	
	public VNextSelectServicesScreen clickAddServicesButton() {
		tap(addservicesbtn);
		log(LogStatus.INFO, "Tap Add Services button");
		return new VNextSelectServicesScreen(appiumdriver);
	}
	
	public boolean isServiceAdded(String servicename) {
		return addedserviceslist.findElements(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")).size() > 0;
	}
	
	public int getQuantityOfSelectedService(String servicename) {
		return addedserviceslist.findElements(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")).size();
	}
	
	public String getSelectedServicePriceValue(String servicename) {
		String serviceprice = "";
		WebElement servicerow = getSelectedServiceListItem(servicename);
		if (servicerow != null) {
			serviceprice = servicerow.findElement(By.xpath(".//div[@class='item-price']/div/strong")).getText();
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
		return serviceprice;
	}
	
	public String getSelectedServicePriceMatrixValue(String servicename) {
		String pricematrixname = "";
		WebElement servicerow = getSelectedServiceListItem(servicename);
		if (servicerow != null) {
			pricematrixname = servicerow.findElement(By.xpath(".//div[@class='subtitle']")).getText();
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
		return pricematrixname;
	}
	
	public WebElement getSelectedServiceListItem(String servicename) {
		List<WebElement> services = getSelectedServicesListItems();
		for (WebElement srv: services)
			if (srv.findElement(By.xpath(".//div[@class='item-title']")).getText().equals(servicename))
				return srv;
		return null;
	}
	
	public List<WebElement> getSelectedServicesListItems() {	
		return addedserviceslist.findElements(By.xpath("./ul/li/a[@action='select-item']"));
	}
	
	public VNextServiceDetailsScreen openServiceDetailsScreen(String servicename) {
		tap(addedserviceslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")));
		log(LogStatus.INFO, "Open '" + servicename + "' service details");
		return new VNextServiceDetailsScreen(appiumdriver);
	}
	
	public VNextVehiclePartsScreen openMatrixServiceVehiclePartsScreen(String servicename) {
		tap(addedserviceslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")));
		log(LogStatus.INFO, "Open '" + servicename + "' service details");
		return new VNextVehiclePartsScreen(appiumdriver);
	}
	
	public void clickSaveButton() {
		tap(savebtn);
		log(LogStatus.INFO, "Click Save inspection button");
	}
	
	public VNextVehicleInfoScreen goBackToInspectionVehicleInfoScreen() {
		waitABit(5000);
		swipeScreensRight(4);
		//swipeScreenLeft();
		//swipeScreenLeft(); 
		//swipeScreenLeft();
		//swipeScreenLeft();
		return new VNextVehicleInfoScreen(appiumdriver);
	}

}

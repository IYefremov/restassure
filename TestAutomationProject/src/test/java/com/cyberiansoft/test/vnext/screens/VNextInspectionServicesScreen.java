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
	
	@FindBy(xpath="//div[@class='left']/i[@action='add']")
	private WebElement addservicesbtn;
	
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
	
	public String getSelectedservicePriceValue(String servicename) {
		String serviceprice = "";
		List<WebElement> selectedservices = addedserviceslist.findElements(By.xpath(".//li/a/div[@class='item-inner']"));
		for (WebElement servicerow : selectedservices) {
			if (servicerow.findElement(By.xpath(".//div[@class='item-title']")).getText().equals(servicename))
				serviceprice = servicerow.findElement(By.xpath(".//div[@class='item-price']/div/strong")).getText();
		}
		return serviceprice;
	}
	
	public VNextServiceDetailsScreen openServiceDetailsScreen(String servicename) {
		tap(addedserviceslist.findElement(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")));
		log(LogStatus.INFO, "Open '" + servicename + "' service details");
		return new VNextServiceDetailsScreen(appiumdriver);
	}

}

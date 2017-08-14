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

public class VNextSelectServicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='center' and text()='Select Services']")
	private WebElement selectservicesscreencapt;
	
	@FindBy(xpath="//div[@data-page='services-add']")
	private WebElement selectservicesscreen;
	
	@FindBy(xpath="//*[@action='back']")
	private WebElement backbtn;
	
	public VNextSelectServicesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(selectservicesscreencapt));
	}

	public WebElement getServicesList() {	
		waitABit(1000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		return wait.until(ExpectedConditions.visibilityOf(selectservicesscreen.findElement(By.xpath(".//div[@class='services-list-block']"))));
	}
	
	public List<WebElement> getServicesListItems() {	
		return getServicesList().findElements(By.xpath(".//div[@action='select-item']"));
	}
	
	public WebElement getServiceListItem(String servicename) {
		List<WebElement> services = getServicesListItems();
		for (WebElement srv: services)
			if (getServiceListItemName(srv).equals(servicename))
				return srv;
		return null;
	}
	
	public String getServiceListItemName(WebElement srvlistitem) {
		return srvlistitem.findElement(By.xpath(".//div[@class='item-title']")).getText().trim();
	}
	
	public void selectServices(String[] serviceslist) {
		for (String servicename: serviceslist)
			selectService(servicename);
	}
	
	public void selectService(String servicename) {
		WebElement servicerow = getServiceListItem(servicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='select']")));
		else
			Assert.assertTrue(false, "Can't find service: " + servicename);
	}
	
	public void unselectService(String servicename) {
		WebElement servicerow = getServiceListItem(servicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@class='item-checked big-checkbox green']")));	
		else
			Assert.assertTrue(false, "Can't find service: " + servicename);
		waitABit(5000);
	}
	
	public VNextPriceMatrixesScreen openMatrixServiceDetails(String matrixservicename) {
		WebElement servicerow = getServiceListItem(matrixservicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='select']")));
		else
			Assert.assertTrue(false, "Can't find service: " + matrixservicename);
		return new VNextPriceMatrixesScreen(appiumdriver);
	}
	
	public String getSelectedPriceMatrixValueForPriceMatrixService(String matrixservicename) {
		String pricematrixname = "";
		WebElement servicerow = getServiceListItem(matrixservicename);
		if (servicerow != null)
			pricematrixname = servicerow.findElement(By.xpath(".//div[@class='item-subtitle']")).getText();
		else
			Assert.assertTrue(false, "Can't find service: " + matrixservicename);
		return pricematrixname;
	}
	
	public void clickSaveSelectedServicesButton() {
		tap(selectservicesscreen.findElement(By.xpath(".//span[@action='save']")));
		log(LogStatus.INFO, "Click Save button");
	}
	
	public VNextInspectionServicesScreen clickBackButton() {
		tap(backbtn);
		log(LogStatus.INFO, "Clack Select Services Back button");
		return new VNextInspectionServicesScreen(appiumdriver);
	}
}

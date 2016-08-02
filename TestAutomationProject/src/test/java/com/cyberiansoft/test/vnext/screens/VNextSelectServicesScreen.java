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

public class VNextSelectServicesScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='center' and text()='Select Services']")
	private WebElement selectservicesscreencapt;
	
	@FindBy(xpath="//div[contains(@class, 'page inspections-service')]")
	private WebElement selectservicesscreen;
	
	public VNextSelectServicesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(selectservicesscreencapt));
	}

	public WebElement getServicesList() {	
		waitABit(1000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		return wait.until(ExpectedConditions.visibilityOf(selectservicesscreen.findElement(By.xpath(".//div[@class='list-block']"))));
	}
	
	public List<WebElement> getServicesListItems() {	
		return getServicesList().findElements(By.xpath("./ul/li/a[@action='select-item']"));
	}
	
	public WebElement getServiceListItem(String servicename) {
		List<WebElement> services = getServicesListItems();
		for (WebElement srv: services)
			if (getServiceListItemName(srv).equals(servicename))
				return srv;
		return null;
	}
	
	public String getServiceListItemName(WebElement srvlistitem) {
		return srvlistitem.findElement(By.xpath(".//div[@class='item-title']")).getText();
	}
	
	public void selectServices(String[] serviceslist) {
		for (String servicename: serviceslist)
			selectService(servicename);
	}
	
	public void selectService(String servicename) {
		WebElement servicerow = getServiceListItem(servicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//div[@action='select']/i")));
		else
			Assert.assertTrue(false, "Can't find service: " + servicename);
	}
	
	public void clickSaveSelectedServicesButton() {
		tap(selectservicesscreen.findElement(By.xpath(".//i[@action='save']")));
	}
}

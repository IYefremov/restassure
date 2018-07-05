package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class VNextInspectionServicesScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[@data-page='services-list']")
	private WebElement servicesscreen;
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addservicesbtn;
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement savebtn;
	
	@FindBy(xpath="//div[contains(@class, 'services-list-block')]")
	private WebElement addedserviceslist;
	
	@FindBy(xpath="//*[@data-autotests-id='selected-services']")
	private WebElement selectedserviceslist;
	
	@FindBy(xpath="//*[@data-autotests-id='all-services']")
	private WebElement allserviceslist;

	public VNextInspectionServicesScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='services-list']")));
		BaseUtils.waitABit(2000);
		if (checkHelpPopupPresence())
			if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
				tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
	}

	public VNextInspectionServicesScreen switchToAvalableServicesView() {
		tap(servicesscreen.findElement(By.xpath(".//*[@action='available']")));
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='available' and @class='button active']")));
		return new VNextInspectionServicesScreen(appiumdriver);
	}

	public VNextSelectedServicesScreen switchToSelectedServicesView() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//*[@action='selected']"))));
		tap(servicesscreen.findElement(By.xpath(".//*[@action='selected']")));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@action='selected' and @class='button active']")));
		return  new VNextSelectedServicesScreen(appiumdriver);
	}
	
	public VNextServiceDetailsScreen openServiceDetailsScreen(String servicename) {
		tap(addedserviceslist.findElement(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")));
		return new VNextServiceDetailsScreen(appiumdriver);
	}
	
	public void clickSaveButton() {
		tap(savebtn);
	}
	
	public VNextVehicleInfoScreen goBackToInspectionVehicleInfoScreen() {
		BaseUtils.waitABit(2000);
		swipeScreensRight(3);
		//swipeScreenLeft();
		//swipeScreenLeft(); 
		//swipeScreenLeft();
		//swipeScreenLeft();
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public VNextInspectionsScreen saveInspection() {
		tap(savebtn);
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public List<WebElement> getAllServicesListItems() {	
		return allserviceslist.findElements(By.xpath(".//div[contains(@class, 'checked-accordion-item')]"));
	}
	
	public String getUnselectedServiceListItemName(WebElement srvlistitem) {
		return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
	}
	
	public void selectService(String serviceName) {
		WebElement servicerow = getServiceListItem(serviceName);
		if (servicerow != null) {
			try {
				tap(WaitUtils.waitUntilElementIsClickable(servicerow.findElement(By.xpath(".//input[@action='select-item']"))));
				WaitUtils.waitUntilElementInvisible(By.xpath("//div[@class='notifier-contaier']"));
			} catch (WebDriverException e) {
				WaitUtils.waitUntilElementInvisible(By.xpath("//div[@data-type='approve']"));
				WaitUtils.click(servicerow.findElement(By.xpath(".//input[@action='select-item']")));
			}
			WaitUtils.waitUntilElementInvisible(By.xpath("//div[@data-type='approve']"));
		}
		else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}

	public VNextLaborServiceDetailsScreen selectLaborService(String laborServiceName) {
		WebElement servicerow = getServiceListItem(laborServiceName);
		if (servicerow != null) {
			tap(servicerow);
			return  new VNextLaborServiceDetailsScreen(appiumdriver);
		}
		else
			Assert.assertTrue(false, "Can't find service: " + laborServiceName);
		return null;
	}

	public void selectServices(String[] serviceslist) {
		for (String servicename: serviceslist)
			selectService(servicename);
	}

	public void selectServices(List<ServiceData> serviceslist) {
		for (ServiceData servicename: serviceslist)
			selectService(servicename.getServiceName());
	}
	
	public void selectAllServices() {
		int count =  50;
		int selected = 0;
		List<WebElement> servicerows = getAllServicesListItems();
		for (WebElement servicerow : servicerows) {
			tap(servicerow.findElement(By.xpath(".//input[@action='select-item']")));
			selected++;
			if (selected > count)
				break;
		}
	}

	public String getTotalPriceValue() {
		return servicesscreen.findElement(By.xpath(".//span[@id='total']")).getText().trim();
	}

	public WebElement getServicesList() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		return wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//div[@data-autotests-id='all-services']"))));
	}

	public List<WebElement> getServicesListItems() {
		return getServicesList().findElements(By.xpath(".//div[@class='checkbox-item']"));
	}

	public String getServiceListItemName(WebElement srvlistitem) {
		return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
	}

	public WebElement getServiceListItem(String servicename) {
		WebElement serviceListItem = null;
		List<WebElement> services = getServicesListItems();
		for (WebElement srv: services)
			if (getServiceListItemName(srv).equals(servicename)) {
				serviceListItem = srv;
				break;
			}
		return serviceListItem;
	}

	public VNextPriceMatrixesScreen openMatrixServiceDetails(String matrixservicename) {
		WebElement servicerow = getServiceListItem(matrixservicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='select-item']")));
		else
			Assert.assertTrue(false, "Can't find service: " + matrixservicename);
		return new VNextPriceMatrixesScreen(appiumdriver);
	}

	public VNextVehiclePartsScreen openSelectedMatrixServiceDetails(String matrixservicename) {
		WebElement servicerow = getServiceListItem(matrixservicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='select-item']")));
		else
			Assert.assertTrue(false, "Can't find service: " + matrixservicename);
		return new VNextVehiclePartsScreen(appiumdriver);
	}

	public void selectMatrixService(String matrixservicename) {
		WebElement servicerow = getServiceListItem(matrixservicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='select']")));
		else
			Assert.assertTrue(false, "Can't find service: " + matrixservicename);
	}
}

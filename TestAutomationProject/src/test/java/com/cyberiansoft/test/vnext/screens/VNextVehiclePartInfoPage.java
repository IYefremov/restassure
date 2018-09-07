package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.dataclasses.ServiceData;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePartsList;
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

public class VNextVehiclePartInfoPage extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@data-page='info']")
	private WebElement vehiclepartinfoscreen;
	
	@FindBy(xpath="//div[@action='size']")
	private WebElement vehiclepartsizeselect;
	
	@FindBy(xpath="//div[@action='severity']")
	private WebElement vehiclepartseverityselect;
	
	@FindBy(xpath="//div[@input='price']")
	private WebElement vehiclepartpricefld;
	
	@FindBy(xpath="//*[@data-autotests-id='all-services']")
	private WebElement additionalavailableserviceslist;
	
	@FindBy(xpath="//div[@action='notes']")
	private WebElement notesbutton;
	
	@FindBy(xpath="//*[@action='save']")
	private WebElement savebtn;
	
	public VNextVehiclePartInfoPage(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartinfoscreen));
	}
	
	public void selectVehiclePartSize(String vehiclepartsize) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@action='size']")));
		tap(vehiclepartsizeselect);
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-item']/div/div[contains(text(), '" + vehiclepartsize + "')]")));
	}
	
	public void selectVehiclePartSeverity(String vehiclepartseverity) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(vehiclepartseverityselect));
		tap(vehiclepartseverityselect);
		tap(appiumdriver.findElement(By.xpath("//*[@action='select-item']/div/div[contains(text(), '" + vehiclepartseverity + "')]")));
	}
	
	public void selectVehiclePartAdditionalService(String additionalservicename) {
		WebElement addservs = getVehiclePartAdditionalServiceCell(additionalservicename);
		if (addservs != null)
			tap(addservs.findElement(By.xpath(".//input[@action='select-item']")));
		else
			Assert.assertTrue(false, "Can't find additional servicve: " + additionalservicename);
	}

	public void openVehiclePartAdditionalServiceDetails(String additionalservicename) {
		WebElement addservs = getVehiclePartAdditionalServiceCell(additionalservicename);
		if (addservs != null)
			tap(addservs.findElement(By.xpath(".//div[@class='checkbox-item-title']")));
		else
			Assert.assertTrue(false, "Can't find additional servicve: " + additionalservicename);
	}

	public void selectVehiclePartAdditionalServices(List<ServiceData> additionalServices) {
		for (ServiceData additionalService : additionalServices) {
			WebElement addservs = getVehiclePartAdditionalServiceCell(additionalService.getServiceName());
			if (addservs != null)
				tap(addservs.findElement(By.xpath(".//input[@action='select']")));
			else
				Assert.fail("Can't find additional servicve: " + additionalService.getServiceName());
		}
	}

	public void selectAllAvailableAdditionalServices() {
		List<WebElement> addservs = additionalavailableserviceslist.findElements(By.xpath(".//div[@action='open-details']"));
		for (WebElement additinalservice : addservs) {
			additinalservice.findElement(By.xpath(".//input[@action='select-item']")).click();
			BaseUtils.waitABit(500);
		}
	}

	private List<WebElement> getAvailableServicesList() {
		return additionalavailableserviceslist.findElements(By.xpath(".//*[@action='open-details']"));
	}

	private String getServiceListItemName(WebElement srvlistitem) {
		return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
	}

	private WebElement getSelectedServiceCell(String serviceName) {
		WebElement serviceListItem = null;
		List<WebElement> selectedservices = getAvailableServicesList();
		for (WebElement service : selectedservices)
			if (getServiceListItemName(service).equals(serviceName)) {
				serviceListItem =  service;
				break;
			}
		return serviceListItem;
	}

	public void openSelectedServiceDetails(String serviceName) {
		WebElement servicerow = getSelectedServiceCell(serviceName);
		if (servicerow != null) {
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}
	
	public WebElement getVehiclePartAdditionalServiceCell(String additionalservicename) {
		WebElement addsrvc = null;
		List<WebElement> addservs = getAvailableServicesList();
		for (WebElement additinalservice : addservs) {
			if (additinalservice.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().equals(additionalservicename)) {
				addsrvc = additinalservice;
				break;
			}
		}
		return addsrvc;
	}
	
	public void setAdditionalServicePriceValue(String additionalservicename, String pricevalue) {
		WebElement servicecell = getVehiclePartAdditionalServiceCell(additionalservicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Price']")));
			VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
			keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='Price']")).getAttribute("value"), pricevalue);
		} else
			Assert.assertTrue(false, "Can't find service: " + additionalservicename);	
	}


	public void setAdditionalServiceAmauntValue(String additionalservicename, String pricevalue) {
		WebElement servicecell = getVehiclePartAdditionalServiceCell(additionalservicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
			VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
			keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value"), pricevalue);
		} else
			Assert.assertTrue(false, "Can't find service: " + additionalservicename);
	}

	public String getMatrixServiceTotalPriceValue() {
		return vehiclepartinfoscreen.findElement(By.xpath(".//span[@class='money-wrapper']")).getText();
	}
	
	public void clickSaveVehiclePartInfo() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(savebtn));
		tap(savebtn);
	}
	
	public VNextNotesScreen clickMatrixServiceNotesOption() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(notesbutton));
		tap(notesbutton);
		return new VNextNotesScreen(appiumdriver);
	}

	public VNextLaborServicePartsList clickSelectPanelsAndPartsForLaborService(LaborServiceData laborService) {
		//WebElement servicerow = getSelectedServiceCell(laborService.getServiceName());
		//if (servicerow != null) {
		//	tap(servicerow.findElement(By.xpath(".//div[@class='checkbox-item-title']")));
			/*if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);*/
			tap(appiumdriver.findElement(By.xpath("//div[@action='select-panel']")));
		//} else
		//	Assert.assertTrue(false, "Can't find service: " + laborService.getServiceName());
		return new VNextLaborServicePartsList(appiumdriver);
	}

	public WebElement expandLaborServiceDetails(LaborServiceData laborService) {
		WebElement servicerow = getSelectedServiceCell(laborService.getServiceName());
		if (servicerow != null) {
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
		} else
			Assert.assertTrue(false, "Can't find service: " + laborService.getServiceName());
		return servicerow;
	}

	public String getLaborServiceRate(LaborServiceData laborService) {
		WebElement servicerow =expandLaborServiceDetails(laborService);
		return servicerow.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value").trim();
	}

	public String getLaborServiceTime(LaborServiceData laborService) {
		WebElement servicerow =expandLaborServiceDetails(laborService);
		return servicerow.findElement(By.xpath(".//input[@data-name='QuantityFloat']")).getAttribute("value").trim();
	}

	public String getLaborServiceNotes(LaborServiceData laborService) {
		WebElement servicerow =expandLaborServiceDetails(laborService);
		return servicerow.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).getText().trim();
	}

}

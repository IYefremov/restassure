package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.dataclasses.LaborServiceData;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextBasePanelPartsList;
import com.cyberiansoft.test.vnext.screens.panelandparts.VNextLaborServicePanelsList;
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
	
	@FindBy(xpath="//*[@data-autotests-id='matrix-part-info-list']")
	private WebElement additionalserviceslist;
	
	@FindBy(xpath="//div[@action='notes']")
	private WebElement notesbutton;
	
	@FindBy(xpath="//a[@action='save']")
	private WebElement savebtn;
	
	public VNextVehiclePartInfoPage(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartinfoscreen));
	}
	
	public void selectVehiclePartSize(String vehiclepartsize) {
		tap(vehiclepartsizeselect);
		tap(appiumdriver.findElement(By.xpath("//a[@action='select-item' and contains(text(), '" + vehiclepartsize + "')]")));
	}
	
	public void selectVehiclePartSeverity(String vehiclepartseverity) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(vehiclepartseverityselect));
		tap(vehiclepartseverityselect);
		tap(appiumdriver.findElement(By.xpath("//a[@action='select-item' and contains(text(), '" + vehiclepartseverity + "')]")));
	}
	
	public void selectVehiclePartAdditionalService(String additionalservicename) {
		WebElement addservs = getVehiclePartAdditionalServiceCell(additionalservicename);
		if (addservs != null)
			tap(addservs.findElement(By.xpath(".//input[@action='select']")));
		else
			Assert.assertTrue(false, "Can't find additional servicve: " + additionalservicename);
	}
	
	public void selectAllAvailableAdditionalServices() {
		List<WebElement> addservs = additionalserviceslist.findElements(By.xpath(".//div[@class='accordion-item-toggle']"));
		for (WebElement additinalservice : addservs) {
			additinalservice.findElement(By.xpath(".//input[@action='select']")).click();
			BaseUtils.waitABit(500);
		}
	}

	private List<WebElement> getSelectedServicesList() {
		return additionalserviceslist.findElements(By.xpath(".//*[@action='open-item']/.."));
	}

	private String getServiceListItemName(WebElement srvlistitem) {
		return srvlistitem.findElement(By.xpath(".//div[@class='checkbox-item-title']")).getText().trim();
	}

	private WebElement getSelectedServiceCell(String serviceName) {
		WebElement serviceListItem = null;
		List<WebElement> selectedservices = getSelectedServicesList();
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
		List<WebElement> addservs = additionalserviceslist.findElements(By.xpath(".//div[contains(@class, 'checked-accordion-item')]"));
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
		WebElement servicerow = getSelectedServiceCell(laborService.getServiceName());
		if (servicerow != null) {
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
			tap(servicerow.findElement(By.xpath(".//div[@action='select-panel']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + laborService.getServiceName());
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

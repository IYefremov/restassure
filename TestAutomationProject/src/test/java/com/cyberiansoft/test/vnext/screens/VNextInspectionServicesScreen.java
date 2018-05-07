package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
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
		tap(servicesscreen.findElement(By.xpath(".//*[@action='selected']")));
		WebDriverWait wait = new WebDriverWait(appiumdriver, 5);
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
			tap(servicerow.findElement(By.xpath(".//input[@action='check-item']")));
			WebDriverWait wait = new WebDriverWait(appiumdriver, 6);
			wait.until(ExpectedConditions.numberOfElementsToBeLessThan (By.xpath("//div[@data-type='approve']"), 1));
		}
		else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}

	public void selectServices(String[] serviceslist) {
		for (String servicename: serviceslist)
			selectService(servicename);
	}
	
	public void selectAllServices() {
		int count =  50;
		int selected = 0;
		List<WebElement> servicerows = getAllServicesListItems();
		for (WebElement servicerow : servicerows) {
			tap(servicerow.findElement(By.xpath(".//input[@action='check-item']")));
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
				serviceListItem =  srv;
				break;
			}
		return serviceListItem;
	}

	public VNextPriceMatrixesScreen openMatrixServiceDetails(String matrixservicename) {
		WebElement servicerow = getServiceListItem(matrixservicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='check-item']")));
		else
			Assert.assertTrue(false, "Can't find service: " + matrixservicename);
		return new VNextPriceMatrixesScreen(appiumdriver);
	}

	public VNextVehiclePartsScreen openSelectedMatrixServiceDetails(String matrixservicename) {
		WebElement servicerow = getServiceListItem(matrixservicename);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='check-item']")));
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

	////////////////////////////////////////////////////////////////////////

	/*public VNextSelectedServicesScreen clickAddServicesButton() {
		return  new VNextSelectedServicesScreen(appiumdriver);
	}

	public void setServiceAmountValue(String serviceName, String amount) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
			VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
			keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value"), amount);
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}

	public void clickServiceAmountField(String serviceName) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}

	public void setServiceQuantityValue(String serviceName, String quantity) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")));
			VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
			keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")).getAttribute("value"), quantity);
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}

	public void addNotesToSelectedService(String serviceName, String notes) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			BaseUtils.waitABit(1000);
			WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
			wait.until(ExpectedConditions.elementToBeClickable(servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']"))));
			servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).clear();
			servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).sendKeys(notes);
			appiumdriver.hideKeyboard();
			tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}

	public String getSelectedServiceNotesValue(String serviceName) {
		String notesvalue = "";
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			notesvalue = servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).getAttribute("value");
			tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
		return notesvalue;
	}

	public VNextNotesScreen clickServiceNotesOption(String serviceName) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//div[@action='notes']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
		return new VNextNotesScreen(appiumdriver);
	}

	public VNextNotesScreen clickServiceNotesOptionFromAllServicesList(String serviceName) {
		WebElement servicerow = getSelectedServiceCell(serviceName);
		if (servicerow != null) {
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
			BaseUtils.waitABit(1000);
			tap(servicerow.findElement(By.xpath(".//*[@action='notes']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
		return new VNextNotesScreen(appiumdriver);
	}


	public void uselectService(String serviceName) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			tap(servicecell.findElement(By.xpath(".//input[@action='check-item']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}

	public String getSelectedServicePriceValue(String servicename) {
		String serviceprice = "";
		WebElement servicerow = getSelectedServiceCell(servicename);
		if (servicerow != null) {
			serviceprice = servicerow.findElement(By.xpath(".//div[@class='item-subtitle']")).getText().trim();
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
		return serviceprice;
	}

	public String getSelectedServiceImageSummaryValue(String servicename) {
		String imagesammary = "";
		WebElement servicerow = getSelectedServiceCell(servicename);
		if (servicerow != null) {
			if (!servicerow.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicerow);
			imagesammary = servicerow.findElement(By.xpath(".//div[@class='img-item summary-item']")).getText().trim();
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
		return imagesammary;
	}

	public String getSelectedServicePriceMatrixValue(String servicename) {
		String pricematrixname = "";
		WebElement servicerow = getSelectedServiceCell(servicename);
		if (servicerow != null) {
			pricematrixname = servicerow.findElement(By.xpath(".//div[@class='item-subtitle']")).getText();
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
		return pricematrixname;
	}

	public int getQuantityOfSelectedService(String servicename) {
		return getSelectedServicesList().findElements(By.xpath(".//div[@class='checkbox-item-title' and text()='" + servicename + "']")).size();
	}

	public boolean isServiceSelected(String servicename) {
		boolean selected = false;
		if (appiumdriver.findElements(By.xpath("//*[@data-autotests-id='selected-services']")).size() > 0) {
			WebElement servicecell = getSelectedServiceCell(servicename);
			if (servicecell != null) {
				if (servicecell.findElement(By.xpath(".//input[@action='check-item']" )).getAttribute("checked") != null)
					if (servicecell.findElement(By.xpath(".//input[@action='check-item']" )).getAttribute("checked").equals("true"))
						selected = true;
			}

		}
		return selected;
	}


	public WebElement getSelectedServiceCell(String servicename) {
		WebElement serviceListItem = null;
		List<WebElement> services = getServicesListItems();
		for (WebElement srv: services)
			if (getServiceListItemName(srv).equals(servicename)) {
				serviceListItem =  srv;
				break;
			}
		return serviceListItem;
	}


	public WebElement getSelectedServicesList() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		return wait.until(ExpectedConditions.visibilityOf(servicesscreen.findElement(By.xpath(".//div[@data-autotests-id='all-services']"))));
	}*/
}

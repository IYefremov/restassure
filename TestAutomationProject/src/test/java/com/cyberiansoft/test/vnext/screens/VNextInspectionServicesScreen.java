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
	
	@FindBy(xpath="//div[@data-page='services-list']")
	private WebElement servicesscreen;
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addservicesbtn;
	
	@FindBy(xpath="//span[@action='save']")
	private WebElement savebtn;
	
	@FindBy(xpath="//div[contains(@class, 'services-list-block')]")
	private WebElement addedserviceslist;
	
	@FindBy(xpath="//*[@data-autotests-id='selected-services']")
	private WebElement selectedserviceslist;
	
	@FindBy(xpath="//*[@data-autotests-id='all-services']")
	private WebElement allserviceslist;

	public VNextInspectionServicesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='services-list']")));
		waitABit(2000);
		if (checkHelpPopupPresence())
			if (appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']").isDisplayed())
				tap(appiumdriver.findElementByXPath("//div[@class='help-button' and text()='OK, got it']"));
	}
	
	public VNextSelectServicesScreen clickAddServicesButton() {
		tap(addservicesbtn);
		log(LogStatus.INFO, "Tap Add Services button");
		return new VNextSelectServicesScreen(appiumdriver);
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
	
	public int getQuantityOfSelectedService(String servicename) {
		return addedserviceslist.findElements(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")).size();
	}
	
	public String getSelectedServicePriceValue(String servicename) {
		String serviceprice = "";
		WebElement servicerow = getSelectedServiceListItem(servicename);
		if (servicerow != null) {
			serviceprice = servicerow.findElement(By.xpath(".//div[@class='item-subtitle']")).getText().trim();
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
		return serviceprice;
	}
	
	public String getSelectedServiceImageSummaryValue(String servicename) {
		String imagesammary = "";
		WebElement servicerow = getSelectedServiceListItem(servicename);
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
		WebElement servicerow = getSelectedServiceListItem(servicename);
		if (servicerow != null) {
			pricematrixname = servicerow.findElement(By.xpath(".//div[@class='item-subtitle']")).getText();
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
		return selectedserviceslist.findElements(By.xpath(".//div[contains(@class, 'checked-accordion-item')]"));
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
		waitABit(2000);
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
	
	public WebElement getSelectedServiceCell(String serviceName) {
		WebElement servicecell = null;
		List<WebElement> servitems = getSelectedServicesListItems();
		for (WebElement servcell : servitems) {
			if (servcell.findElement(By.xpath(".//div[@class='item-title']")).getText().equals(serviceName)) {
				servicecell = servcell;
				break;
			}
			
		}
		return servicecell;
	}
	
	public void uselectService(String serviceName) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			tap(servicecell.findElement(By.xpath(".//input[@action='check-item']")));
			log(LogStatus.INFO, "Unselect Service: " + serviceName);
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
	}
	
	public void setServiceAmountValue(String serviceName, String amount) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
			VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
			keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value"), amount);
			log(LogStatus.INFO, "Set Service value: " + amount);
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);	
	}
	
	public void clickServiceAmountField(String serviceName) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
			log(LogStatus.INFO, "Click service " + serviceName + " amount field");
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
			log(LogStatus.INFO, "Set Service value: " + quantity);
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);	
	}
	
	public void addNotesToSelectedService(String serviceName, String notes) {
		WebElement servicecell = getSelectedServiceCell(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			waitABit(1000);
			WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
			wait.until(ExpectedConditions.elementToBeClickable(servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']"))));
			servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).clear();
			servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).sendKeys(notes);
			appiumdriver.hideKeyboard();
			tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + serviceName + "']")));
			log(LogStatus.INFO, "Set Service notes: " + notes);
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
		WebElement servicecell = getUnselectedServiceListItem(serviceName);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			waitABit(1000);
			tap(servicecell.findElement(By.xpath(".//*[@action='notes']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + serviceName);	
		return new VNextNotesScreen(appiumdriver);
	}
	
	public List<WebElement> getAllServicesListItems() {	
		return allserviceslist.findElements(By.xpath(".//div[contains(@class, 'checked-accordion-item')]"));
	}
	
	public String getUnselectedServiceListItemName(WebElement srvlistitem) {
		return srvlistitem.findElement(By.xpath(".//div[@class='item-title']")).getText().trim();
	}
	
	public WebElement getUnselectedServiceListItem(String serviceName) {
		WebElement serviceListItem = null;
		List<WebElement> services = getAllServicesListItems();
		for (WebElement srv: services)
			if (getUnselectedServiceListItemName(srv).equals(serviceName)) {
				serviceListItem = srv;
				break;
			}
		return serviceListItem;
	}
	
	public void selectService(String serviceName) {
		WebElement servicerow = getUnselectedServiceListItem(serviceName);
		if (servicerow != null)
			tap(servicerow.findElement(By.xpath(".//input[@action='check-item']")));
		else
			Assert.assertTrue(false, "Can't find service: " + serviceName);
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

}

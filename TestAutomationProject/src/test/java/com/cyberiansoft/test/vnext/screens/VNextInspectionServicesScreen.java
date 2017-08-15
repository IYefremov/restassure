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
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInspectionServicesScreen extends VNextBaseInspectionsScreen {
	
	@FindBy(xpath="//div[@data-page='services-list']")
	private WebElement servicesscreen;
	
	@FindBy(xpath="//a[@action='add']")
	private WebElement addservicesbtn;
	
	@FindBy(xpath="//span[@action='save']")
	private WebElement savebtn;
	
	@FindBy(xpath="//span[@action='back']")
	private WebElement backbtn;
	
	@FindBy(xpath="//div[contains(@class, 'services-list-block')]")
	private WebElement addedserviceslist;
	
	@FindBy(xpath="//*[@data-autotests-id='selected-services']")
	private WebElement selectedserviceslist;

	public VNextInspectionServicesScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-page='services-list']")));
		if (appiumdriver.findElementsByXPath("//div[@class='help-button' and text()='OK, got it']").size() > 0)
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
		waitABit(5000);
		swipeScreensRight(2);
		//swipeScreenLeft();
		//swipeScreenLeft(); 
		//swipeScreenLeft();
		//swipeScreenLeft();
		return new VNextVehicleInfoScreen(appiumdriver);
	}
	
	public VNextInspectionsScreen saveInspectionViaMenu() {
		tap(savebtn);
		return new VNextInspectionsScreen(appiumdriver);
	}
	
	public WebElement getSelectedServiceCell(String servicename) {
		WebElement servicecell = null;
		List<WebElement> servitems = getSelectedServicesListItems();
		for (WebElement servcell : servitems) {
			if (servcell.findElement(By.xpath(".//div[@class='item-title']")).getText().equals(servicename)) {
				servicecell = servcell;
				break;
			}
			
		}
		return servicecell;
	}
	
	public void uselectService(String servicename) {
		WebElement servicecell = getSelectedServiceCell(servicename);
		if (servicecell != null) {
			tap(servicecell.findElement(By.xpath(".//input[@action='check-item']")));
			log(LogStatus.INFO, "Unselect Service: " + servicename);
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
	}
	
	public void setServiceAmountValue(String servicename, String amount) {
		WebElement servicecell = getSelectedServiceCell(servicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
			VNextServicePriceCustomKeyboard keyboard = new VNextServicePriceCustomKeyboard(appiumdriver);
			keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")).getAttribute("value"), amount);
			log(LogStatus.INFO, "Set Service value: " + amount);
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);	
	}
	
	public void clickServiceAmountField(String servicename) {
		WebElement servicecell = getSelectedServiceCell(servicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='Amount']")));
			log(LogStatus.INFO, "Click service " + servicename + " amount field");
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);
	}
	
	public void setServiceQuantityValue(String servicename, String quantity) {
		WebElement servicecell = getSelectedServiceCell(servicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")));
			VNextServicePriceCustomKeyboard keyboard = new VNextServicePriceCustomKeyboard(appiumdriver);
			keyboard.setFieldValue(servicecell.findElement(By.xpath(".//input[@data-name='QuantityFloat']")).getAttribute("value"), quantity);
			log(LogStatus.INFO, "Set Service value: " + quantity);
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);	
	}
	
	public void addNotesToSelectedService(String servicename, String notes) {
		WebElement servicecell = getSelectedServiceCell(servicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
			wait.until(ExpectedConditions.elementToBeClickable(servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']"))));
			servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).clear();
			servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).sendKeys(notes);
			appiumdriver.hideKeyboard();
			tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")));
			/*if (appiumdriver.findElement(By.xpath("//a[@class='link close-picker']")).isDisplayed()) {
				tap(appiumdriver.findElement(By.xpath("//a[@class='link close-picker']")));
				tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")));
			}*/
			log(LogStatus.INFO, "Set Service notes: " + notes);
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);	
	}
	
	public String getSelectedServiceNotesValue(String servicename) {
		String notesvalue = "";
		WebElement servicecell = getSelectedServiceCell(servicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			notesvalue = servicecell.findElement(By.xpath(".//textarea[@data-name='Notes.desc']")).getAttribute("value");
			tap(appiumdriver.findElement(By.xpath(".//div[@class='item-title' and text()='" + servicename + "']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);	
		return notesvalue;
	}
	
	public VNextNotesScreen clickServiceNotesOption(String servicename) {
		WebElement servicecell = getSelectedServiceCell(servicename);
		if (servicecell != null) {
			if (!servicecell.getAttribute("class").contains("accordion-item-expanded"))
				tap(servicecell);
			tap(servicecell.findElement(By.xpath(".//div[@action='notes']")));
		} else
			Assert.assertTrue(false, "Can't find service: " + servicename);	
		return new VNextNotesScreen(appiumdriver);
	}

}

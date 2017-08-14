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
	
	@FindBy(xpath="//div[contains(@class, 'picker-modal picker-keypad picker-keypad-type-numpad')]")
	private WebElement keyboard;
	
	@FindBy(xpath="//a[@action='save']")
	private WebElement savebtn;
	
	public VNextVehiclePartInfoPage(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(vehiclepartinfoscreen));
	}
	
	public void selectVehiclePartSize(String vehiclepartsize) {
		tap(vehiclepartsizeselect);
		tap(appiumdriver.findElement(By.xpath("//a[@action='select-item' and contains(text(), '" + vehiclepartsize + "')]")));
		log(LogStatus.INFO, "Select Vehicle Part size: " + vehiclepartsize);
	}
	
	public void selectVehiclePartSeverity(String vehiclepartseverity) {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(vehiclepartseverityselect));
		tap(vehiclepartseverityselect);
		tap(appiumdriver.findElement(By.xpath("//a[@action='select-item' and contains(text(), '" + vehiclepartseverity + "')]")));
		log(LogStatus.INFO, "Select Vehicle Part size: " + vehiclepartseverity);
	}
	
	public void selectVehiclePartAdditionalService(String additionalservicename) {
		WebElement addservs = getVehiclePartAdditionalServiceCell(additionalservicename);
		if (addservs != null)
			tap(addservs.findElement(By.xpath(".//input[@action='select']")));
		else
			Assert.assertTrue(false, "Can't find additional servicve: " + additionalservicename);
		log(LogStatus.INFO, "Select additional servivce: " + additionalservicename);			
	}
	
	public void selectAllAvailableAdditionalServices() {
		List<WebElement> addservs = additionalserviceslist.findElements(By.xpath(".//div[@class='accordion-item-toggle']"));
		for (WebElement additinalservice : addservs) {
			additinalservice.findElement(By.xpath(".//input[@action='select']")).click();
			waitABit(500);
		}
	}
	
	public WebElement getVehiclePartAdditionalServiceCell(String additionalservicename) {
		WebElement addsrvc = null;
		List<WebElement> addservs = additionalserviceslist.findElements(By.xpath(".//div[contains(@class, 'checked-accordion-item')]"));
		for (WebElement additinalservice : addservs) {
			if (additinalservice.findElement(By.xpath(".//div[@class='item-title']")).getText().equals(additionalservicename)) {
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
			for (int i = 0; i <= servicecell.findElement(By.xpath(".//input[@data-name='Price']")).getAttribute("value").length()+1; i++) {
				clickKeyboardBackspaceButton();
			}
			for (int i = 0; i < pricevalue.length(); i++) {
				if (Character.toString(pricevalue.charAt(i)).equals("-"))
					tap(keyboard.findElement(By.xpath("./div[@class='picker-modal-inner picker-keypad-buttons']/span/span[text()='-/+']")));
				else
					clickKeyboardButton(pricevalue.charAt(i));
			}
			clickKeyboardDoneButton();
			log(LogStatus.INFO, "Set Service price value: " + pricevalue);
		} else
			Assert.assertTrue(false, "Can't find service: " + additionalservicename);	
	}
	
	public void clickKeyboardBackspaceButton() {
		waitABit(2000);
		tap(keyboard.findElement(By.xpath(".//span[contains(@class, 'picker-keypad-delete')]")));		
	}
	
	public void clickKeyboardButton(char button) {
		tap(keyboard.findElement(By.xpath("./div[@class='picker-modal-inner picker-keypad-buttons']/span/span[text()='" + button + "']")));
	}
	
	public void clickKeyboardDoneButton() {
		tap(keyboard.findElement(By.xpath(".//a[@class='link close-picker']")));
	}
	
	public String getMatrixServiceTotalPriceValue() {
		return vehiclepartinfoscreen.findElement(By.xpath(".//span[@class='money-wrapper']")).getText();
	}
	
	public void clickSaveVehiclePartInfo() {
		tap(savebtn);
		log(LogStatus.INFO, "Click Save Vehicle Part Info button");
	}
	
	public VNextNotesScreen clickMatrixServiceNotesOption() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(notesbutton));
		tap(notesbutton);
		log(LogStatus.INFO, "Click service Notes option");
		return new VNextNotesScreen(appiumdriver);
	}

}

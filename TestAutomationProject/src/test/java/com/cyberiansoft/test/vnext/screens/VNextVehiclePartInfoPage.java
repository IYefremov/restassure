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

}

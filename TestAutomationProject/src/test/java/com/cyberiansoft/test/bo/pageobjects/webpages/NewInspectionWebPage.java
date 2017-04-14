package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class NewInspectionWebPage extends BaseWebPage {
	
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_wizard_customerType")
	private WebElement customertypecmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_wizard_comboAssignClient_Input")
	private WebElement customercmb;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_wizard_btnAddAssignedClient")
	private WebElement selectcustomerbtn;
	
	//////////////////////////
	
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_wizard_vehicleVin")
	private TextField vinfld;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_wizard_btnDecodeVin")
	private WebElement decodevinbtn;
	
	////////////////////////
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_wizard_txtInspectionDescription")
	private TextField inspectiondescription;

	////////////////////////
	@FindBy(xpath = "//input[contains(@id, 'NextButton')]")
	private WebElement nextbtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_wizard_FinishNavigationTemplateContainerID_FinishButton")
	private WebElement savebtn;
	
	@FindBy(id = "ctl00_ctl00_Content_Main_Card_ctl04_BtnClose")
	private WebElement closedlgbtn;
	
	public NewInspectionWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void selectCustomerType(String customertype) throws InterruptedException {
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(customertypecmb));
		Select selectObject = new Select(customertypecmb);
		selectObject.selectByValue(customertype);
		Thread.sleep(300);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(text(), 'Loading...')]")));
	}
	
	public void selectInspectionType(String insptype) {
		click(driver.findElement(By.xpath("//label[text()='" + insptype + "']")));
	}
	
	public void selectActionAfterInspectionCreation(String actionname) {
		click(driver.findElement(By.xpath("//label[text()='" + actionname + "']")));
	}
	
	public void selectCustomer(String customer) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(customercmb)).click();
		customercmb.sendKeys(customer);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[text()='" + customer + "']"))).click();
	}
	
	public void clickSelectCustomerButton() {
		clickAndWait(selectcustomerbtn);
	}
	
	public void setVIN(String vin) {
		clearAndType(vinfld, vin);
	}
	
	public void clickDecodeVINButton() {
		clickAndWait(decodevinbtn);
	}
	
	public void setVINAnddecode(String vin) {
		setVIN(vin);
		clickDecodeVINButton();
		click(wait.until(ExpectedConditions.elementToBeClickable(closedlgbtn)));
	}
	
	public void selectService(String servicename)  {		
		click(driver.findElement(By.xpath("//label[text()='" + servicename + "']")));
	}
	
	public void setInspectionDescription(String inspdesc) {
		wait.until(ExpectedConditions.visibilityOf(inspectiondescription.getWrappedElement()));
		clearAndType(inspectiondescription, inspdesc);
	}
	
	public void clickNextButton() {
		clickAndWait(nextbtn);
	}
	
	public String getNewInspectionNumber() {
		String inspnubercaption = "New Inspection# ";
		return driver.findElement(By.xpath("//h2[contains(text(), '" + inspnubercaption + "')]")).getText().substring(inspnubercaption.length());
	}
	
	public void clickSaveInspectionButton() {
		clickAndWait(savebtn);
	}

}

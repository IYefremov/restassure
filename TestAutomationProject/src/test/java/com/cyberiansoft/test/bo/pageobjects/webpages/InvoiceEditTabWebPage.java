package com.cyberiansoft.test.bo.pageobjects.webpages;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.*;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.bo.webelements.TextField;

public class InvoiceEditTabWebPage extends BaseWebPage {
	
	@FindBy(id = "sendPOButton")
	private WebElement addpopaybtn;
	
	@FindBy(xpath = "//div[@class='toolbar-button save-button']")
	private WebElement saveinvoicebtn;
	
	@FindBy(id = "addPOButton")
	private WebElement addpobtn;
	
	@FindBy(id = "POnotes")
	private TextField ponotesfld;
	
	@FindBy(id = "poNumberInput")
	private TextField ponumberfld;
	
	@FindBy(id = "approveNotes")
	private WebElement approvenoteschkbox;
	
	@FindBy(xpath = "//a[text()='Technicians:']")
	private WebElement technicianslink;
	
	/////////////////////
	@FindBy(id = "customerText")
	private WebElement invoicecustomer;
	
	@FindBy(name = "isWholesaler")
	private WebElement customerwholesaleradio;
	
	@FindBy(id = "customerAutoComplete")
	private WebElement entercustomerwholesalefld;
	
	public InvoiceEditTabWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void clickAddPOPayButton() {
		waitABit(3000);
		Actions act  = new Actions(driver);
		act.click(addpopaybtn).perform();
	}
	
	public void clickSaveInvoiceButton() {
		Actions act  = new Actions(driver);
		act.click(saveinvoicebtn).perform();
		waitABit(1000);
	}
	
	public String clickAddPOPayButtonAndAcceptPayment() {
		clickAddPOPayButton();
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String alerttext = alert.getText();
		alert.accept();
		return alerttext;
	}
	
	public void clcikAddPO() {		 
		if (getBrowserType().equals("chrome")) {
			clickTechniciansLink();
			try {
				WebDriverWait wait = new WebDriverWait(driver, 3);
				wait.until(ExpectedConditions.alertIsPresent());
					Alert alert = driver.switchTo().alert();
					alert.dismiss();
				} catch (Exception e) {
					
				}
			Actions act  = new Actions(driver);
			act.click(addpobtn).perform();
			/*new WebDriverWait(driver, 5)
			  .until(ExpectedConditions.alertIsPresent());*/
			try {
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = driver.switchTo().alert();
				alert.dismiss();
			} catch (Exception e) {
				
			}
			//Alert alert = driver.switchTo().alert();
			//alert.dismiss();
			waitABit(1000);
			act.click(addpobtn).perform(); 
			waitABit(1000);
			if (!ponumberfld.isDisplayed()) {		
				waitABit(1000);
				act.click(addpobtn).perform(); 
			}
		} else {
			Actions act  = new Actions(driver);
			act.click(addpobtn).perform();
		}
	}
	
	public void setPONotes(String notes) {
		clearAndType(ponotesfld, notes);
	}
	
	public WebElement getCheckApproveInvoiceAfterPayment() {
		return approvenoteschkbox;
	}
	
	public void setPONumber(String ponumber) {
		clearAndType(ponumberfld, ponumber);
	}
	
	public WebElement getPONumberField() {
		return ponumberfld.getWrappedElement();
	}
	
	public void checkApproveInvoiceAfterPayment() {
		Actions act  = new Actions(driver);
		act.click(getCheckApproveInvoiceAfterPayment()).perform();
	}
	
	public void clickTechniciansLink() {
		new WebDriverWait(driver, 60)
		 .until(ExpectedConditions.visibilityOf(technicianslink));
		click(technicianslink);
	}
	
	public void clickClickHereToAddNotes() {
		Actions act  = new Actions(driver);
		act.click(driver.findElement(By.xpath("//div[contains(@class, 'service-notes')]"))).perform();
	}
	
	public String getInvoiceNotesValue() {
		String oldvalue = driver.findElement(By.xpath("//div[contains(@class, 'service-notes')]")).getText();
		return oldvalue;
	}
	
	public void setEditableNotes(String notes) {	
		if (getBrowserType().equals("chrome")) {
			clickTechniciansLink();			
			clickSaveInvoiceButton();
			
			try {
				WebDriverWait wait = new WebDriverWait(driver, 3);
				wait.until(ExpectedConditions.alertIsPresent());
					Alert alert = driver.switchTo().alert();
					alert.dismiss();
				} catch (Exception e) {
					
				}		
		}
		waitABit(2000);
		clickClickHereToAddNotes();
		waitABit(300);
		Actions act  = new Actions(driver);
		act.sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END), notes).perform();
	}
	
	public WebElement getTechniciansPopup() {
		return driver.findElement(By.xpath("//div[@class='technicians-popup popup']"));
	}
	
	public List<WebElement> getAllTechniciansPopupItems() {
		return getTechniciansPopup().findElements(By.xpath(".//ul/li"));
	}
	
	public void unselectAllTechnicians() {
		List<WebElement> technicians = getAllTechniciansPopupItems();
		for (WebElement tech : technicians) {
			if (isCheckboxChecked(tech.findElement(By.xpath("./input")))) {
				Actions act  = new Actions(driver);
				act.click(tech.findElement(By.xpath("./input"))).perform();
				//tech.findElement(By.xpath("./span")).click();
				//tech.findElement(By.xpath("./input")).click();
				
			}
		}
	}
	
	public void changeInvoiceWholesaleCustomer(String customername) {
		invoicecustomer.click();
		customerwholesaleradio.click();
		entercustomerwholesalefld.sendKeys(customername.substring(0, 3));
		waitABit(2000);
		driver.findElement(By.xpath("//ul/li/a[text()='" + customername + "']")).click();
	}
	
	public String getInvoiceCustomer() {
		return invoicecustomer.getText();
	}

}

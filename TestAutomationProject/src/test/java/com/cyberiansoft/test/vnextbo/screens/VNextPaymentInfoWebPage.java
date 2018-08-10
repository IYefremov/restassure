package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class VNextPaymentInfoWebPage extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//span[@aria-owns='paymentFormEdition_listbox']")
	private WebElement chooseeditioncmbx;
	
	@FindBy(id = "paymentFormEdition_listbox")
	private WebElement paymentseditionslistbox;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormFullName']")
	private WebElement paymentfullnamefld;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormCardNumber']")
	private WebElement paymentcardnumberfld;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormExpirationMonth']")
	private WebElement expirationmonthcmbx;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormExpirationYear']")
	private WebElement expirationyearcmbx;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormCvc']")
	private WebElement cvccodefld;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormAddressLine1']")
	private WebElement address1fld;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormAddressLine2']")
	private WebElement address2fld;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormCity']")
	private WebElement cityfld;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormZipCode']")
	private WebElement zipfld;
	
	@FindBy(xpath = "//*[@aria-owns='payment-view-country_listbox']")
	private WebElement countrycmbx;
	
	@FindBy(id = "payment-view-country_listbox")
	private WebElement paymentcountrylistbox;
	
	@FindBy(xpath = "//*[@aria-owns='payment-view-state_listbox']")
	private WebElement statecmbx;
	
	@FindBy(id = "payment-view-state_listbox")
	private WebElement paymentcstatelistbox;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormtermsAndConditions']")
	private WebElement termandconditionschkbx;
	
	@FindBy(xpath = "//*[@data-automation-id='paymentFormPaymentTerms']")
	private WebElement paymenttermschkbx;
	
	@FindBy(xpath = "//*[@data-bind='click: submit']")
	private WebElement savebtn;
	
	public VNextPaymentInfoWebPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void setUserPaymentsInfo(String edition, String userfullname, String cardnumber,
			String expirationmonth, String expirationyear, String cvccode, String billindaddressline1,
			String billindcity, String billindzip, String billingcountry, String billingstate) {
		
		selectEdition(edition);
		setFullName(userfullname);
		setCardNumber(cardnumber);
		selectExpirationMonth(expirationmonth);
		selectExpirationYear(expirationyear);
		setCVCCode(cvccode);
		setBillingAddress1(billindaddressline1);
		setBillingAddress1(billindaddressline1);
		setBillingCity(billindcity);
		setBillingZIP(billindzip);
		selectBillingCountry(billingcountry);
		selectBillingState(billingstate);
		checkTermsAndConditions();
		checkPaymentsTerms();
		clickSaveButton();
	}
	
	public void selectEdition(String edition) {
		chooseeditioncmbx.click();
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.elementToBeClickable(paymentseditionslistbox));
		paymentseditionslistbox.findElement(By.xpath("./li[text()='" + edition + "']")).click();
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOf(paymentseditionslistbox));
	}
	
	public void setFullName(String userfullname) {
		paymentfullnamefld.clear();
		paymentfullnamefld.sendKeys(userfullname);
	}
	
	public void setCardNumber(String cardnumber) {
		paymentcardnumberfld.clear();
		paymentcardnumberfld.sendKeys(cardnumber);
	}
	
	public void selectExpirationMonth(String expirationmonth) {
		Select expirationmonthcmb = new Select(expirationmonthcmbx);
		expirationmonthcmb.selectByVisibleText(expirationmonth);
	}
	
	public void selectExpirationYear(String expirationyear) {
		Select expirationyearcmb = new Select(expirationyearcmbx);
		expirationyearcmb.selectByVisibleText(expirationyear);
	}
	
	public void setCVCCode(String cvccode) {
		cvccodefld.clear();
		cvccodefld.sendKeys(cvccode);
	}
	
	public void setBillingAddress1(String billindaddressline1) {
		address1fld.clear();
		address1fld.sendKeys(billindaddressline1);
	}
	
	public void setBillingAddress2(String billindaddressline2) {
		address2fld.clear();
		address2fld.sendKeys(billindaddressline2);
	}
	
	public void setBillingCity(String billindcity) {
		cityfld.clear();
		cityfld.sendKeys(billindcity);
	}
	
	public void setBillingZIP(String billindzip) {
		zipfld.clear();
		zipfld.sendKeys(billindzip);
	}
	
	public void selectBillingCountry(String billingcountry) {
		countrycmbx.click();
        countrycmbx.sendKeys(billingcountry);
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.elementToBeClickable(paymentcountrylistbox));
		paymentcountrylistbox.findElement(By.xpath("./li[text()='" + billingcountry + "']")).click();
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOf(paymentcountrylistbox));
	}
	
	public void selectBillingState(String billingstate) {
		statecmbx.click();
        statecmbx.sendKeys(billingstate);
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.elementToBeClickable(paymentcstatelistbox));
		paymentcstatelistbox.findElement(By.xpath("./li[text()='" + billingstate + "']")).click();
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOf(paymentcstatelistbox));
	}
	
	public void checkTermsAndConditions() {
		termandconditionschkbx.click();
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.presenceOfElementLocated(By.id("paymentTermsAndConditionsModal__agreeBtn")));
		  new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.elementToBeClickable(By.id("paymentTermsAndConditionsModal__agreeBtn"))).click();
		  new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("paymentTermsAndConditionsModal__agreeBtn"))));
	}
	
	public void checkPaymentsTerms() {
		paymenttermschkbx.click();
		new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.presenceOfElementLocated(By.id("paymentPaymentTermsModal__agreeBtn")));
		  new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.elementToBeClickable(By.id("paymentPaymentTermsModal__agreeBtn"))).click();
		  new WebDriverWait(driver, 5)
		  .until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("paymentPaymentTermsModal__agreeBtn"))));
	}
	
	public void clickSaveButton() {
		savebtn.click();
	}

    public void clickSaveAndCloseCongratsModal() {
        clickSaveButton();
        WebElement el = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("paymentCongratsModal")));
		new WebDriverWait(driver, 5)
				.until(ExpectedConditions.visibilityOf(el.findElement(By.xpath(".//button[@class='close']"))));
        el.findElement(By.xpath(".//button[@class='close']")).click();
    }

}

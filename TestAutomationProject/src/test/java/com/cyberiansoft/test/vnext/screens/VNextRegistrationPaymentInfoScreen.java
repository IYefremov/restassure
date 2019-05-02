package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.baseutils.BaseUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextRegistrationPaymentInfoScreen extends VNextBaseScreen {
	
	@FindBy(id="payment-info-view")
	private WebElement paymentinfoscreen;
	
	@FindBy(xpath="//li[@data-name='fullName']/label/input")
	private WebElement usernamefld;
	
	@FindBy(id="payment-info-view_cardNumber")
	private WebElement cardnumberfld;
	
	@FindBy(xpath="//select[contains(@data-bind, 'data.expirationMonth')]")
	private WebElement expirmonthfld;
	
	@FindBy(id="payment-expiration-date-year")
	private WebElement expiryearfld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.cvc')]")
	private WebElement securitycodefld;
	
	///////////////////////////
	
	@FindBy(xpath="//input[contains(@data-bind, 'useRegistrationAddress')]")
	private WebElement useregistrationaddresschkbox;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.addressLine1')]")
	private WebElement adress1fld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.addressLine2')]")
	private WebElement adress2fld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.city')]")
	private WebElement cityfld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'data.zipCode')]")
	private WebElement zipfld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'selectedCountryText')]")
	private WebElement countryfld;
	
	@FindBy(xpath="//input[contains(@data-bind, 'selectedStateText')]")
	private WebElement statefld;
	
	public VNextRegistrationPaymentInfoScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(usernamefld));
	}
	
	public void setUserPaiymentInfo(String userpaymentname, String usercardnumber, String expmonth, String expyear,
			String securitycode) {
		setUserPaymentInfoName(userpaymentname);
		setUserPaymentCardNumber(usercardnumber);
		setUserPaymentExpirationDate(expmonth, expyear);
		setUserPaymentSecurityCode(securitycode);
	}
	
	public void setUserPaymentInfoName(String userpaymentname) {
		tap(usernamefld);
		appiumdriver.getKeyboard().sendKeys(userpaymentname);
		appiumdriver.hideKeyboard();
	}
	
	public void setUserPaymentCardNumber(String usercardnumber) {
		tap(cardnumberfld);
		appiumdriver.getKeyboard().sendKeys(usercardnumber);
		//appiumdriver.hideKeyboard();
	}
	
	public void setUserPaymentExpirationDate(String expmonth, String expyear) {
		Select expmonthsel = new Select(expirmonthfld);
		expmonthsel.selectByValue(expmonth);
		Select expyearsel = new Select(expiryearfld);
		expyearsel.selectByValue(expyear);
	}
	
	public void setUserPaymentSecurityCode(String securitycode) {
		tap(securitycodefld);
		appiumdriver.getKeyboard().sendKeys(securitycode);
		appiumdriver.hideKeyboard();
	}
	
	public void clickUseRegistrationAddress() {
		tap(useregistrationaddresschkbox);
		BaseUtils.waitABit(1000);
	}
	
	public void clickDoneButton() {
		tap(paymentinfoscreen.findElement(By.xpath(".//div[contains(@data-bind, 'navigateNext')]")));
	}
	
	public String getUserAddress1Value() {
		return adress1fld.getAttribute("value");
	}
	
	public String getUserAddress2Value() {
		return adress2fld.getAttribute("value");
	}
	
	public String getUserCityValue() {
		return cityfld.getAttribute("value");
	}
	
	public String getUserZipValue() {
		return zipfld.getAttribute("value");
	}
	
	public String getUserCountryValue() {
		return countryfld.getAttribute("value");
	}
	
	public String getUserStateValue() {
		return statefld.getAttribute("value");
	}

}

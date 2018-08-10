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
import org.openqa.selenium.support.ui.WebDriverWait;

public class VNextRegistrationOverviewLegalInfosScreen extends VNextBaseScreen {
	
	@FindBy(id="final-view")
	private WebElement registrationoverviewlegalinfoscreen;
	
	@FindBy(xpath="//span[text()='I agree to Terms and Conditions']")
	private WebElement termsandconditionslink;
	
	@FindBy(xpath="//span[text()='I agree to Payment Terms']")
	private WebElement paymenttermslink;
	
	@FindBy(xpath="//button[contains(@data-bind, 'termsAndConditions.agree')]")
	private WebElement agreebtn;
	
	@FindBy(xpath="//button[contains(@data-bind, 'paymentTerms.agree')]")
	private WebElement paymentagreebtn;
	
	@FindBy(xpath="//button[contains(@data-bind, 'termsAndConditions.decline')]")
	private WebElement declinebtn;
	
	@FindBy(xpath="//button[contains(@data-bind, 'paymentTerms.decline')]")
	private WebElement paymentdeclinebtn;

	@FindBy(xpath="//*[text()='Submit']")
	private WebElement submitbtn;
	
	@FindBy(xpath="//*[text()='Pay now!']")
	private WebElement paynowbtn;
	
	public VNextRegistrationOverviewLegalInfosScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(registrationoverviewlegalinfoscreen));
		BaseUtils.waitABit(3000);
	}
	
	public String getPaymentPriceValue() {
		return registrationoverviewlegalinfoscreen.findElement(By.xpath(".//div[@data-bind='text: price']")).getText();
	}
	
	public void agreetermsAndconditions() {
		clickTermsAndConditionsLink();
		BaseUtils.waitABit(5000);
		clickIAgreeButton();
	}
	
	public void agreePaymentTerms() {
		clickPaymentTermsLink();
		clickPaymentIAgreeButton();
	}
	
	public void clickTermsAndConditionsLink() {
		BaseUtils.waitABit(10000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(termsandconditionslink));
		tap(termsandconditionslink);
	}
	
	public void clickPaymentTermsLink() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(paymenttermslink));
		tap(paymenttermslink);
	}
	
	public void clickPaymentIAgreeButton() {
		tap(paymentagreebtn);
	}
	
	public void clickIAgreeButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(agreebtn));
		tap(agreebtn);
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.invisibilityOf(agreebtn));
	}
	
	public void clickDeclineButton() {
		tap(declinebtn);
	}
	
	public void clickSubmitButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(submitbtn));
		tap(submitbtn);
	}
	
	public void clickPayNowButton() {
		tap(paynowbtn);
	}

}

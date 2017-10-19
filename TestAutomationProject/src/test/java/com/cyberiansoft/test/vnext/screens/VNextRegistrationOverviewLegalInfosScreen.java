package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextRegistrationOverviewLegalInfosScreen extends VNextBaseScreen {
	
	@FindBy(id="final-view")
	private WebElement registrationoverviewlegalinfoscreen;
	
	@FindBy(xpath="//span[text()='Terms and Conditions']")
	private WebElement termsandconditionslink;
	
	@FindBy(xpath="//span[text()='Payment Terms']")
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
	
	public VNextRegistrationOverviewLegalInfosScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(registrationoverviewlegalinfoscreen));
		waitABit(3000);
	}
	
	public String getPaymentPriceValue() {
		return registrationoverviewlegalinfoscreen.findElement(By.xpath(".//div[@data-bind='text: price']")).getText();
	}
	
	public void agreetermsAndconditions() {
		clickTermsAndConditionsLink();
		waitABit(5000);
		clickIAgreeButton();
	}
	
	public void agreePaymentTerms() {
		clickPaymentTermsLink();
		clickPaymentIAgreeButton();
	}
	
	public void clickTermsAndConditionsLink() {
		waitABit(4000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(termsandconditionslink));
		tap(termsandconditionslink);
		
		log(LogStatus.INFO, "Click Terms And Conditions link");
	}
	
	public void clickPaymentTermsLink() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(paymenttermslink));
		tap(paymenttermslink);
		log(LogStatus.INFO, "Click Payment Termslink link");
	}
	
	public void clickPaymentIAgreeButton() {
		tap(paymentagreebtn);
		log(LogStatus.INFO, "Click I Agree button");
	}
	
	public void clickIAgreeButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(agreebtn));
		tap(agreebtn);
		wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.invisibilityOf(agreebtn));
		log(LogStatus.INFO, "Click I Agree button");
	}
	
	public void clickDeclineButton() {
		tap(declinebtn);
		log(LogStatus.INFO, "Click Decline button");
	}
	
	public void clickSubmitButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(submitbtn));
		tap(submitbtn);
		log(LogStatus.INFO, "Click Submit button");
	}
	
	public void clickPayNowButton() {
		tap(paynowbtn);
		log(LogStatus.INFO, "Click Pay Now button");
	}

}

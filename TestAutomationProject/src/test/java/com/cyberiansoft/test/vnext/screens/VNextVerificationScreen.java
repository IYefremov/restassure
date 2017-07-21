package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.AppContexts;
import com.relevantcodes.extentreports.LogStatus;

public class VNextVerificationScreen extends VNextBaseScreen {
	
	@FindBy(id="phone-verification-view")
	private WebElement phonevereficationscreren;
	
	@FindBy(xpath="//li[@data-name='verificationCode']/label/input")
	private WebElement regfld;
	
	@FindBy(xpath="//a[contains(@data-bind,'navigateNext')]")
	private WebElement verifyBtn;
	
	@FindBy(xpath="//div[contains(@class,'download-main')]")
	private WebElement downloaddbprogressbar;
	
	@FindBy(xpath="//div[contains(@class,'download-vin')]")
	private WebElement downloadvinprogressbar;
	
	@FindBy(xpath="//*[text()='Download again']")
	private WebElement downloadagainbtn;
	
	public VNextVerificationScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 400);
		wait.until(ExpectedConditions.visibilityOf(phonevereficationscreren));
	}
	
	public void setDeviceRegistrationCode(String regcode) {
		setValue(regfld, regcode);
		switchApplicationContext(AppContexts.NATIVE_CONTEXT);		
		appiumdriver.hideKeyboard();
	    switchToWebViewContext();
		log(LogStatus.INFO, "Set registration code: " + regcode);
	}
	
	public String getEnteredDeviceRegistrationCodeValue() {
		return regfld.getAttribute("value");
	}
	
	public void clickVerifyButton() {
		/*List<WebElement> nextbtns = phonevereficationscreren.findElements(By.xpath(".//button[@data-bind='click: navigateNext']"));
		System.out.println("+++++++++" + nextbtns.size());
		for (WebElement nextbtn : nextbtns) {
			if (nextbtn.isDisplayed())
				tap(nextbtn);
		}*/
		tap(phonevereficationscreren.findElement(By.xpath(".//button[@data-bind='click: navigateNext']")));
		log(LogStatus.INFO, "Tap Verify button");
	}
	
	public boolean isDownloadDBProgressBarAppears() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.visibilityOf(downloaddbprogressbar));
		return downloaddbprogressbar.isDisplayed();
	}

	public boolean isDownloadVINProgressBarAppears() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.visibilityOf(downloadvinprogressbar));
		return downloadvinprogressbar.isDisplayed();
	}
	
	public boolean isDownloadAgainButtonAppears() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		wait.until(ExpectedConditions.visibilityOf(downloadagainbtn));
		return downloadvinprogressbar.isDisplayed();
	}
	
	public void clickDownloadAgainButton() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 30);
		tap(wait.until(ExpectedConditions.visibilityOf(downloadagainbtn)));
		log(LogStatus.INFO, "Tap Download Again button");
	}
	
}

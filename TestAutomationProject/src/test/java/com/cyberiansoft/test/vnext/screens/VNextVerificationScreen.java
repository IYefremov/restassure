package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	@FindBy(xpath="//li[@data-name='verificationCode']/div[@class='validation-message']/div")
	private WebElement regcodeerrormsg;

    public VNextVerificationScreen(WebDriver appiumdriver) {
		super(appiumdriver);
        PageFactory.initElements(appiumdriver, this);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 20);
		wait.until(ExpectedConditions.visibilityOf(phonevereficationscreren));
	}
	
	public void setDeviceRegistrationCode(String regcode) {	
		tap(regfld);
		regfld.clear();
		regfld.sendKeys(regcode);
		//appiumdriver.getKeyboard().pressKey(regcode);
		//appiumdriver.hideKeyboard();
	}
	
	public String getEnteredDeviceRegistrationCodeValue() {
		return regfld.getAttribute("value");
	}
	
	public void clickVerifyButton() {
		tap(phonevereficationscreren.findElement(By.xpath(".//button[@data-bind='click: navigateNext']")));
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
	}

	public boolean isRegCodeErrorMessageVisible() {
		return regcodeerrormsg.isDisplayed();
	}

	public String getRegCodeErrorMessage() {
		return regcodeerrormsg.getText();
	}
	
}

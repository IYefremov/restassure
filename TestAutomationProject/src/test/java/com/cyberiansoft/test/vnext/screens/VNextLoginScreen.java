package com.cyberiansoft.test.vnext.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.relevantcodes.extentreports.LogStatus;


public class VNextLoginScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//div[@class='list-block list-block-search searchbar-found virtual-list']")
	private WebElement userslist;
	
	@FindBy(xpath="//a[@action='main-db']/i")
	private WebElement updatemaindbbtn;
	
	@FindBy(xpath="//a[@action='vin-db']/i")
	private WebElement updatevindbbtn;
	
	@FindBy(xpath="//input[@type='password']")
	private WebElement passwordfld;
	
	@FindBy(xpath="//span[text()='Login']")
	private WebElement loginbtn;
	
	@FindBy(xpath="//span[text()='Cancel']")
	private WebElement cancelbtn;
	
	public VNextLoginScreen(AppiumDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		waitUserListVisibility();
	}
	
	public VNextHomeScreen userLogin(String username, String userpsw) {
		selectEmployee(username);
		setUserLoginPassword(userpsw);
		tapLoginButton();
		return new VNextHomeScreen(appiumdriver);
	}
	
	public VNextLoginScreen incorrectUserLogin(String username, String userpsw) {
		selectEmployee(username);
		setUserLoginPassword(userpsw);
		tapLoginButton();
		waitABit(300);
		VNextInformationDialog infrmdialog = new VNextInformationDialog(appiumdriver);
		String msg = infrmdialog.clickInformationDialogOKButtonAndGetMessage();
		Assert.assertEquals(msg, VNextAlertMessages.ENTERED_PASSWORD_IS_INCORRECT);
		waitUserListVisibility();
		return new VNextLoginScreen(appiumdriver);
	}
	
	public void setUserLoginPassword(String userpsw) {
		setValue(passwordfld, userpsw);
		log(LogStatus.INFO, "Set User password: " + userpsw);
		
	}
	
	public void selectEmployee(String username) {
		tapListElement(userslist, username);
		log(LogStatus.INFO, "Select employee: " + username);
	}
	
	public void tapLoginButton() {
		tap(loginbtn);
		log(LogStatus.INFO, "Tap Login button");
		
	}
	
	public void tapCancelButton() {
		tap(cancelbtn);
		log(LogStatus.INFO, "Tap Cancel button");
		waitUserListVisibility();
	}
	
	public void waitUserListVisibility() {
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(userslist));
	}
	
	public void updateMainDB() {
		tap(updatemaindbbtn);
		log(LogStatus.INFO, "Tap Update Main DB button");
		waitABit(10000);
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		informationdlg.clickInformationDialogOKButton();
	}
}

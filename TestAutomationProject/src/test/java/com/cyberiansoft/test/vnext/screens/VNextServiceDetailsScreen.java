package com.cyberiansoft.test.vnext.screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class VNextServiceDetailsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//span[@action='save']")
	private WebElement servicedtailsapplybtn;
	
	@FindBy(xpath="//div[@action='notes']/span")
	private WebElement notesbutton;
	
	@FindBy(xpath="//div[@data-page='details']")
	private WebElement servicedetailssscreen;
	
	public VNextServiceDetailsScreen(AppiumDriver<MobileElement> appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(servicedetailssscreen));
	}
	
	public VNextNotesScreen clickServiceNotesOption() {
		tap(notesbutton);
		log(LogStatus.INFO, "Click service Notes option");
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickServiceDetailsBackButton() {
		clickScreenBackButton();
		log(LogStatus.INFO, "Click Service Details screen Back button");
	}
	
	public void clickServiceDetailsDoneButton() {
		tap(servicedtailsapplybtn);
		log(LogStatus.INFO, "Click Service Details screen Done button");
	}
	
	public void clickDeleteServiceIcon() {
		tap(servicedetailssscreen.findElement(By.xpath(".//i[@action='remove']")));
		log(LogStatus.INFO, "Click Delete Service icon");
	}
	
	public VNextInspectionServicesScreen deleteService() {
		clickDeleteServiceIcon();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.ARE_YOU_SURE_REMOVE_THIS_ITEM));
		return new VNextInspectionServicesScreen(appiumdriver) ;
	}
	
	public void setServiceAmountValue(String amount) {
		clickServiceAmountField();	
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(servicedetailssscreen.findElement(By.id("serviceDetailsPrice")).getAttribute("value"), amount);	
		log(LogStatus.INFO, "Set Service value: " + amount);
	}
	
	public String getServiceAmountValue() {
		return servicedetailssscreen.findElement(By.id("serviceDetailsPrice")).getAttribute("value");
	}
	
	public String getServiceQuantityValue() {
		return servicedetailssscreen.findElement(By.id("serviceDetailsQuantityFloat")).getAttribute("value");
	}
	
	public void clickServiceAmountField() {
		tap(servicedetailssscreen.findElement(By.id("serviceDetailsPrice")));	
		log(LogStatus.INFO, "Click Service Amount Field");
	}
	
	public void clickServiceQuantityField() {
		tap(servicedetailssscreen.findElement(By.id("serviceDetailsQuantityFloat")));	
		log(LogStatus.INFO, "Click Service Amount Field");
	}
	
	public void setServiceQuantityValue(String quantity) {
		clickServiceQuantityField();
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(getServiceQuantityValue(), quantity);
		log(LogStatus.INFO, "Set Service quantity value: " + quantity);
	}

}

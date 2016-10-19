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

public class VNextServiceDetailsScreen extends VNextBaseScreen {
	
	@FindBy(xpath="//i[@action='apply']")
	private WebElement servicedtailsapplybtn;
	
	@FindBy(xpath="//i[@action='notes']")
	private WebElement notesbutton;
	
	@FindBy(xpath="//div[@class='page inspections-service inspections-service-details page-on-center']")
	private WebElement servicedetailssscreen;
	
	@FindBy(xpath="//a[@action='back']/i")
	private WebElement backbtn;
	
	@FindBy(xpath="//div[@class='picker-modal picker-keypad picker-keypad-type-numpad remove-on-close modal-in']")
	private WebElement keyboard;
	
	public VNextServiceDetailsScreen(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 15);
		wait.until(ExpectedConditions.visibilityOf(notesbutton));
	}
	
	public VNextNotesScreen clickServiceNotesOption() {
		tap(notesbutton);
		log(LogStatus.INFO, "Click service Notes option");
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickServiceDetailsBackButton() {
		tap(backbtn);
		log(LogStatus.INFO, "Click Service Details Back button");
	}
	
	public void clickServiceDetailsDoneButton() {
		tap(servicedtailsapplybtn);
		log(LogStatus.INFO, "Click Service Details Done button");
	}
	
	public void clickDeleteServiceIcon() {
		tap(servicedetailssscreen.findElement(By.xpath(".//i[@action='delete']")));
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
		tap(servicedetailssscreen.findElement(By.xpath(".//i[@class='icon-clear']")));
		for (int i = 0; i < amount.length(); i++) {
			clickKeyboardButton(amount.charAt(i));
		}
		clickKeyboardDoneButton();
		log(LogStatus.INFO, "Set Service value: " + amount);
	}
	
	public String getServiceAmountValue() {
		return servicedetailssscreen.findElement(By.xpath(".//input[@name='Amount']")).getAttribute("value");
	}
	
	public String getServiceQuantityValue() {
		return servicedetailssscreen.findElement(By.xpath(".//input[@name='QuantityFloat']")).getAttribute("value");
	}
	
	public void clickServiceAmountField() {
		tap(servicedetailssscreen.findElement(By.xpath(".//input[@name='Amount']")));
		waitABit(2000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(keyboard));		
		log(LogStatus.INFO, "Click Service Amount Field");
	}
	
	public void clickServiceQuantityField() {
		tap(servicedetailssscreen.findElement(By.xpath(".//input[@name='QuantityFloat']")));
		waitABit(2000);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(keyboard));		
		log(LogStatus.INFO, "Click Service Amount Field");
	}
	
	public void clickKeyboardButton(char button) {
		tap(keyboard.findElement(By.xpath("./div[@class='picker-modal-inner picker-keypad-buttons']/span/span[text()='" + button + "']")));
	}
	
	public void clickKeyboardDoneButton() {
		tap(keyboard.findElement(By.xpath(".//a[@class='link close-picker']")));
	}
	
	public void clickKeyboardBackspaceButton() {
		tap(keyboard.findElement(By.xpath(".//i[@class='icon icon-keypad-delete']")));
	}
	
	public void setServiceQuantityValue(String quantity) {
		clickServiceQuantityField();
		final String actualvalue = getServiceQuantityValue();
		if (!actualvalue.equals(quantity)) {
			for (int i = 0; i < actualvalue.length(); i++)
				clickKeyboardBackspaceButton();
			for (int i = 0; i < quantity.length(); i++)
				clickKeyboardButton(quantity.charAt(i));
		}
		clickKeyboardDoneButton();
		log(LogStatus.INFO, "Set Service quantity value: " + quantity);
	}

}

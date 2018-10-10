package com.cyberiansoft.test.vnext.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnext.screens.wizardscreens.services.VNextAvailableServicesScreen;
import com.cyberiansoft.test.vnext.utils.VNextAlertMessages;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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
		return new VNextNotesScreen(appiumdriver);
	}
	
	public void clickServiceDetailsBackButton() {
		clickScreenBackButton();
	}
	
	public void clickServiceDetailsDoneButton() {
		tap(servicedtailsapplybtn);
		if (elementExists("//div[@class='modal-text']")) {
			VNextInformationDialog informationDialog = new VNextInformationDialog(appiumdriver);
			informationDialog.clickInformationDialogNoButton();
		}
	}
	
	public void clickDeleteServiceIcon() {
		tap(servicedetailssscreen.findElement(By.xpath(".//i[@action='remove']")));;
	}
	
	public VNextAvailableServicesScreen deleteService() {
		clickDeleteServiceIcon();
		VNextInformationDialog informationdlg = new VNextInformationDialog(appiumdriver);
		String msg = informationdlg.clickInformationDialogYesButtonAndGetMessage();
		Assert.assertTrue(msg.contains(VNextAlertMessages.ARE_YOU_SURE_REMOVE_THIS_ITEM));
		return new VNextAvailableServicesScreen(appiumdriver) ;
	}
	
	public void setServiceAmountValue(String amount) {
		clickServiceAmountField();	
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(servicedetailssscreen.findElement(By.id("serviceDetailsPrice")).getAttribute("value"), amount);
	}
	
	public String getServiceAmountValue() {
		return servicedetailssscreen.findElement(By.id("serviceDetailsPrice")).getAttribute("value");
	}
	
	public String getServiceQuantityValue() {
		return servicedetailssscreen.findElement(By.id("serviceDetailsQuantityFloat")).getAttribute("value");
	}
	
	public void clickServiceAmountField() {
		tap(servicedetailssscreen.findElement(By.id("serviceDetailsPrice")));
	}
	
	public void clickServiceQuantityField() {
		tap(servicedetailssscreen.findElement(By.id("serviceDetailsQuantityFloat")));
	}
	
	public void setServiceQuantityValue(String quantity) {
		clickServiceQuantityField();
		VNextCustomKeyboard keyboard = new VNextCustomKeyboard(appiumdriver);
		keyboard.setFieldValue(getServiceQuantityValue(), quantity);
	}

}

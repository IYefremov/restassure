package com.cyberiansoft.test.vnext.screens;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.relevantcodes.extentreports.LogStatus;

public class VNextInformationDialog extends VNextBaseScreen {
	
	@FindBy(xpath="//body/div[contains(@class, 'modal-in')]")
	private WebElement modaldlg;
	
	@FindBy(xpath="//div[@class='modal-text']")
	private WebElement modaldlgmsg;
	
	public VNextInformationDialog(SwipeableWebDriver appiumdriver) {
		super(appiumdriver);
		PageFactory.initElements(new ExtendedFieldDecorator(appiumdriver), this);	
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(modaldlg));
	}
	
	public String getInformationDialogMessage() {
		return modaldlgmsg.getText();
	}
	
	public void clickInformationDialogOKButton() {
		appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 600);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='OK']")));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//span[text()='OK']"))));
		tap(modaldlg.findElement(By.xpath(".//span[text()='OK']")));
		log(LogStatus.INFO, "Tap Information Dialog OK Button");
	}
	
	public void clickInformationDialogYesButton() {
		tap(modaldlg.findElement(By.xpath(".//span[text()='Yes']")));
		log(LogStatus.INFO, "Tap Information Dialog Yes Button");
	}
	
	public void clickInformationDialogDontSaveButton() {
		List<WebElement> dlgbtns = modaldlg.findElements(By.xpath(".//span[@class='modal-button ']"));
		for (WebElement btn : dlgbtns)
			if (btn.getText().trim().equals("Don\'t save")) {
				tap(btn);
				break;
			}
			//System.out.println("====" + btn.getText());
		//tap(modaldlg.findElement(By.xpath(".//span[text()=" + "Don\'t save" + "]")));
		log(LogStatus.INFO, "Tap Information Dialog Don't save Button");
	}
	
	public void clickInformationDialogSaveButton() {
		tap(modaldlg.findElement(By.xpath(".//span[text()='Save']")));
		log(LogStatus.INFO, "Tap Information Dialog Save Button");
	}
	
	public void clickInformationDialogCancelButton() {
		tap(modaldlg.findElement(By.xpath(".//span[text()='Cancel']")));
		log(LogStatus.INFO, "Tap Information Dialog Cancel Button");
	}
	
	public void clickInformationDialogNoButton() {
		tap(modaldlg.findElement(By.xpath(".//span[text()='No']")));
		log(LogStatus.INFO, "Tap Information Dialog No Button");
	}
	
	public void clickInformationDialogArchiveButton() {
		tap(modaldlg.findElement(By.xpath(".//span[text()='Archive']")));
		log(LogStatus.INFO, "Tap Information Dialog Archive Button");
	}
	
	public String clickInformationDialogOKButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogOKButton();
		return msg;
	}
	
	public String clickInformationDialogYesButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogYesButton();
		return msg;
	}
	
	public String clickInformationDialogNoButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogNoButton();
		return msg;
	}
	
	public String clickInformationDialogDontSaveButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogCancelButton();
		return msg;
	}
	
	public void clickInformationDialogVoidButton() {
		appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Void']")));
		wait = new WebDriverWait(appiumdriver, 5);
		wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//span[text()='Void']"))));
		tap(modaldlg.findElement(By.xpath(".//span[text()='Void']")));
		log(LogStatus.INFO, "Tap Information Dialog Void Button");
	}
	
	public void clickInformationDialogDontVoidButton() {
		appiumdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(appiumdriver, 10);
		wait.until(ExpectedConditions.visibilityOf(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']"))));
		tap(modaldlg.findElement(By.xpath(".//div[@class='modal-buttons']/span[@class='modal-button ']")));
		log(LogStatus.INFO, "Tap Information Dialog Don't Void Button");
	}
	
	public String clickInformationDialogVoidButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogVoidButton();
		return msg;
	}
	
	public String clickInformationDialogDontVoidButtonAndGetMessage() {
		String msg = getInformationDialogMessage();
		clickInformationDialogDontVoidButton();
		return msg;
	}

}

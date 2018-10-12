package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextConfirmationDialog extends VNextBOBaseWebPage {
	
	@FindBy(id = "dialogModal")
	private WebElement confirmdialog;
	
	@FindBy(xpath = "//div[@class='modal-body']/div[@class='modal-body__content']/div[contains(@data-bind, 'html: html,')]")
	private WebElement confirmdialogmessage;
	
	@FindBy(xpath = "//button[@data-automation-id='modalCancelButton']")
	private WebElement nobtn;
	
	@FindBy(xpath = "//button[@data-automation-id='modalConfirmButton']")
	private WebElement yesbtn;
	
	public VNextConfirmationDialog(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		new WebDriverWait(driver, 30)
		  .until(ExpectedConditions.visibilityOf(confirmdialog));
	}
	
	public void clickNoButton() {
		confirmdialog.
				findElement(By.xpath(".//button[@data-automation-id='modalCancelButton']")).click();
		new WebDriverWait(driver, 10)
		  .until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("dialogModal"))));
	}
	
	public void clickYesButton() {
		confirmdialog.
				findElement(By.xpath(".//button[@data-automation-id='modalConfirmButton']")).click();
		new WebDriverWait(driver, 10)
				.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("dialogModal"))));
	}
	
	public String getConfirmationDialogMessage() {
		String confirmMessage  = null;
		List<WebElement> msgs = driver.findElements(By.xpath("//div[@class='modal-body']/div[@class='modal-body__content']/div"));
		for (WebElement msg : msgs)
			if (!msg.getText().equals(""))
				confirmMessage = msg.getText();
		return confirmMessage;
	}
	
	public String clickYesAndGetConfirmationDialogMessage() {
		final String msg = getConfirmationDialogMessage();
		clickYesButton();
		return msg;
	}
	
	public String clickNoAndGetConfirmationDialogMessage() {
		final String msg = getConfirmationDialogMessage();
		clickNoButton();
		return msg;
	}
}

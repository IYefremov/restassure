package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextConfirmationDialog extends VNextBOBaseWebPage {
	
	@FindBy(id = "dialogModal")
	private WebElement confirmDialog;
	
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
		wait.until(ExpectedConditions.visibilityOf(confirmDialog));
	}
	
	public void clickNoButton() {
        clickModalDialogButton(confirmDialog
                .findElement(By.xpath(".//button[@data-automation-id='modalCancelButton']")));
	}

    public void clickYesButton() {
	    clickModalDialogButton(confirmDialog
                .findElement(By.xpath(".//button[@data-automation-id='modalConfirmButton']")));
	}

    private void clickModalDialogButton(WebElement button) {
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        try {
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("dialogModal"))));
        } catch (TimeoutException e) {
            clickWithJS(button);
        }
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

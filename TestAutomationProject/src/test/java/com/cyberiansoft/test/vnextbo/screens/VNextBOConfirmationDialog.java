package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class VNextBOConfirmationDialog extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@class='modal fade in' and @id='dialogModal']")
	private WebElement confirmDialog;
	
	@FindBy(xpath = "//div[@class='modal-body']/div[@class='modal-body__content']/div[contains(@data-bind, 'html: html,')]")
	private WebElement confirmdialogmessage;
	
	@FindBy(xpath = "//button[@data-automation-id='modalCancelButton']")
	private WebElement nobtn;
	
	@FindBy(xpath = "//button[@data-automation-id='modalConfirmButton']")
	private WebElement yesbtn;

	@FindBy(xpath = "//button[text()='No']")
	private WebElement invoiceNoButton;

	@FindBy(xpath = "//button[text()='Yes']")
	private WebElement invoiceYesButton;

	@FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
	private WebElement rejectButton;
	
	public VNextBOConfirmationDialog() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
	
	public void clickNoButton() {
        clickModalDialogButton(confirmDialog
                .findElement(By.xpath(".//button[@data-automation-id='modalCancelButton']")));
	}

	public void clickInvoiceNoButton() {
        clickModalDialogButton(invoiceNoButton);
	}

    public void clickYesButton() {
	    clickModalDialogButton(confirmDialog
                .findElement(By.xpath(".//button[@data-automation-id='modalConfirmButton']")));
	    waitForLoading();
	}

    public VNextBOInvoicesWebPage clickInvoiceYesButton() {
	    clickModalDialogButton(invoiceYesButton);
	    waitForLoading();
	    return PageFactory.initElements(driver, VNextBOInvoicesWebPage.class);
	}

    public void clickInvoiceRejectButton() {
	    clickModalDialogButton(rejectButton);
	    waitForLoading();
	}

    private void clickModalDialogButton(WebElement button) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        } catch (Exception e) {
            clickWithJS(button);
        }
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

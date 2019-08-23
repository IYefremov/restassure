package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Objects;

@Getter
public class VNextBOConfirmationDialog extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@class='modal fade in' and @id='dialogModal']")
	private WebElement confirmDialog;
	
	@FindBy(xpath = "//div[@class='modal-body']/div[@class='modal-body__content']/div[contains(@data-bind, 'html: html,')]")
	private WebElement confirmdialogmessage;
	
	@FindBy(xpath = "//button[@data-automation-id='modalCancelButton']")
	private WebElement noButton;
	
	@FindBy(xpath = "//button[@data-automation-id='modalConfirmButton']")
	private WebElement yesButton;

	@FindBy(xpath = "//button[text()='No']")
	private WebElement invoiceNoButton;

	@FindBy(xpath = "//button[text()='Yes']")
	private WebElement confirmButton;

	@FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
	private WebElement rejectButton;

	@FindBy(xpath = "//div[@class='modal-body']/div[@class='modal-body__content']/div")
	private List<WebElement> dialogMessagesList;
	
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
	    clickConfirmButton();
	    return PageFactory.initElements(driver, VNextBOInvoicesWebPage.class);
	}

    public void clickConfirmButton() {
	    clickModalDialogButton(confirmButton);
	    waitForLoading();
	}

    public void clickInvoiceRejectButton() {
	    clickModalDialogButton(rejectButton);
	    waitForLoading();
	}

    private void clickModalDialogButton(WebElement button) {
        Utils.clickElement(button);
        try {
            WaitUtilsWebDriver.waitForInvisibility(driver.findElement(By.id("dialogModal")));
        } catch (TimeoutException e) {
            clickWithJS(button);
        }
    }

	private String getConfirmationDialogMessage() {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptionsIgnoringException(dialogMessagesList, 7);
		for (WebElement message : dialogMessagesList)
			if (!message.getText().equals("")) {
                return message.getText();
            }
		return null;
	}
	
	public String clickYesAndGetConfirmationDialogMessage() {
		final String msg = Objects.requireNonNull(getConfirmationDialogMessage());
		clickYesButton();
		return msg;
	}
	
	public String clickNoAndGetConfirmationDialogMessage() {
		final String msg = Objects.requireNonNull(getConfirmationDialogMessage());
		clickNoButton();
		return msg;
	}
}

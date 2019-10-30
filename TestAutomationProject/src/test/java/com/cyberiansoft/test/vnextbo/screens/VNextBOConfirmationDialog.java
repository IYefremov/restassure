package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class VNextBOConfirmationDialog extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//div[@class='modal fade in' and @id='dialogModal']")
	private WebElement confirmDialog;
	
	@FindBy(xpath = "//div[@class='modal-body']/div[@class='modal-body__content']/div[contains(@data-bind, 'html: html,')]")
	private WebElement confirmDialogMessage;
	
	@FindBy(xpath = "//button[@data-automation-id='modalCancelButton']")
	private WebElement noButton;
	
	@FindBy(xpath = "//button[@data-automation-id='modalConfirmButton']")
	private WebElement yesButton;

	@FindBy(xpath = "//button[text()='No']")
	private WebElement invoiceNoButton;

	@FindBy(xpath = "//button[text()='Yes']")
	private WebElement confirmButton;

	@FindBy(xpath = "//div[@id='dialogModal']//button[@data-automation-id='modalCloseButton']")
	private WebElement closeButton;

	@FindBy(xpath = "//div[@class='modal-body']/div[@class='modal-body__content']/div")
	private List<WebElement> dialogMessagesList;
	
	public VNextBOConfirmationDialog() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
	}
}

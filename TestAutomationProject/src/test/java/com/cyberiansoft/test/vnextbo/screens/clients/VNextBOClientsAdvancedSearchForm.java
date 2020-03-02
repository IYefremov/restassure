package com.cyberiansoft.test.vnextbo.screens.clients;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class VNextBOClientsAdvancedSearchForm extends VNextBOBaseWebPage {

	@FindBy(xpath = "//form[@id='advSearchClients-form']")
	private WebElement advancedSearchFormContent;

	@FindBy(xpath = "//form[@id='advSearchClients-form']//button[@type='submit']")
	private WebElement searchButton;

	@FindBy(xpath = "//form[@id='advSearchClients-form']//i[contains(@class, 'icon-close')]")
	private WebElement closeButton;

	@FindBy(xpath = "//input[@id='advSearchClients-name']")
	private WebElement nameField;

	@FindBy(xpath = "//input[@id='advSearchClients-address']")
	private WebElement addressField;

	@FindBy(xpath = "//input[@id='advSearchClients-email']")
	private WebElement emailField;

	@FindBy(xpath = "//input[@id='advSearchClients-phone']")
	private WebElement phoneField;

	@FindBy(xpath = "//form[@id='advSearchClients-form']//span[@class='k-input']")
	private WebElement typeDropDownField;

	public WebElement dropDownFieldOption(String optionName) {
		return advancedSearchFormContent.findElement(By.xpath("//li[text()='" + optionName + "']"));
	}

	public VNextBOClientsAdvancedSearchForm() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		WaitUtilsWebDriver.waitForVisibility(advancedSearchFormContent);
	}
}
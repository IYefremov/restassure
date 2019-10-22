package com.cyberiansoft.test.vnextbo.screens.users;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class VNextBOUsersAdvancedSearchForm extends VNextBOBaseWebPage {

	@FindBy(xpath = "//form[@id='advSearchUsers-form']")
	public WebElement advancedSearchFormContent;

	@FindBy(xpath = "//form[@id='advSearchUsers-form']//button[@type='submit']")
	public WebElement searchButton;

	@FindBy(xpath = "//form[@id='advSearchUsers-form']//i[contains(@class, 'icon-close')]")
	public WebElement closeButton;

	@FindBy(xpath = "//form[@id='advSearchUsers-form']//label[text()='Email']/following-sibling::div//input")
	public WebElement emailField;

	@FindBy(xpath = "//form[@id='advSearchUsers-form']//label[text()='Phone']/following-sibling::div//input")
	public WebElement phoneField;

	public VNextBOUsersAdvancedSearchForm(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		WaitUtilsWebDriver.waitForVisibility(advancedSearchFormContent);
	}
}
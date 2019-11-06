package com.cyberiansoft.test.vnextbo.screens.devicemanagement;

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
public class VNextBODevicesAdvancedSearchForm extends VNextBOBaseWebPage {

	@FindBy(xpath = "//form[@id='advSearchDevices-form']")
	private WebElement advancedSearchFormContent;

	@FindBy(xpath = "//form[@id='advSearchDevices-form']//button[@type='submit']")
	private WebElement searchButton;

	@FindBy(xpath = "//form[@id='advSearchDevices-form']//i[contains(@class, 'icon-close')]")
	private WebElement closeButton;

	@FindBy(xpath = "//input[@id='advSearchDevices-name']")
	private WebElement nameField;

	@FindBy(xpath = "//input[@id='advSearchDevices-license']")
	private WebElement licenseField;

	@FindBy(xpath = "//input[@aria-owns='advSearchDevices-team_listbox']")
	private WebElement teamDropDownField;

	@FindBy(xpath = "//span[@aria-owns='advSearchDevices-platform_listbox']//span[@class='k-input']")
	private WebElement platformDropDownField;

	@FindBy(xpath = "//input[@id='advSearchDevices-version']")
	private WebElement versionField;

	@FindBy(xpath = "//ul[@id='activeLicensePopup-team_listbox']")
	private WebElement teamDropDownOptionsList;

	@FindBy(xpath = "//ul[@id='advSearchDevices-platform_listbox']")
	private WebElement platformDropDownOptionsList;

	public WebElement teamDropDownFieldOption(String optionName) {

		return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[text()='" + optionName + "']"));
	}

	public WebElement platformDropDownFieldOption(String platformName) {

		return driver.findElement(By.xpath("//ul[@aria-hidden='false']/li[text()='" + platformName + "']"));
	}

	public VNextBODevicesAdvancedSearchForm() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		WaitUtilsWebDriver.waitForVisibility(advancedSearchFormContent);
	}
}
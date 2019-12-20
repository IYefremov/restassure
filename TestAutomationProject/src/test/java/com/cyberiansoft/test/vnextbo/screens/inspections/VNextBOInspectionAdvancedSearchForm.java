package com.cyberiansoft.test.vnextbo.screens.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class VNextBOInspectionAdvancedSearchForm extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//form[@id='advSearchEstimation-form']")
	public WebElement advancedSearchFormContent;

	@FindBy(xpath = "//form[@id='advSearchEstimation-form']//button[@type='submit']")
	public WebElement searchButton;

	@FindBy(xpath = "//form[@id='advSearchEstimation-form']//i[contains(@class, 'icon-close')]")
	public WebElement closeButton;

	@FindBy(xpath = "//form[@id='advSearchEstimation-form']//span[text()='Save']")
	public WebElement saveButton;

	@FindBy(xpath = "//form[@id='advSearchEstimation-form']//span[text()='Clear']")
	public WebElement clearButton;

	@FindBy(xpath = "//form[@id='advSearchEstimation-form']//span[contains(@data-bind, 'click: deleteSavedSearch')]")
	public WebElement deleteSavedSearchButton;

	@FindBy(xpath = "//div[not(@style='display: none;')]/label[contains(@for, 'advSearchEstimation')]")
	public List<WebElement> searchFieldsTitlesList;

	public WebElement textFieldByName(String fieldLabel) {
		return advancedSearchFormContent.findElement(By.xpath("//form[@id='advSearchEstimation-form']//label[text()='" +
				fieldLabel + "']/following-sibling::div//input"));
	}

	public WebElement dropDownFieldByName(String fieldLabel) {
		return advancedSearchFormContent.findElement(By.xpath("//label[text()='" + fieldLabel +
				"']/following-sibling::div//span[contains(@class,'k-dropdown-wrap')]"));
	}

	public WebElement autoPopulatedFieldByName(String fieldLabel) {
		return advancedSearchFormContent.findElement(By.xpath("//label[text()='" +
				fieldLabel + "']/following-sibling::div//input[contains(@id,'advSearchEstimation')]"));
	}

	public List<WebElement> dropDownFieldOptionsList(String fieldLabel) {
		return advancedSearchFormContent.findElements(By.xpath("//ul[@id='advSearchEstimation-" +
				fieldLabel.toLowerCase() + "_listbox']//li"));
	}

	public WebElement dropDownFieldOption(String optionName) {
		return advancedSearchFormContent.findElement(By.xpath("//li[text()='" + optionName + "']"));
	}

	public VNextBOInspectionAdvancedSearchForm() {
		super(DriverBuilder.getInstance().getDriver());
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);
		WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
		WaitUtilsWebDriver.waitForPendingRequestsToComplete();
		WaitUtilsWebDriver.waitForVisibility(advancedSearchFormContent);
	}
}
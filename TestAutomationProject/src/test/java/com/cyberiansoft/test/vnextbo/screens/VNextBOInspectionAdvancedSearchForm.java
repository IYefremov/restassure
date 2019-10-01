package com.cyberiansoft.test.vnextbo.screens;

import com.cyberiansoft.test.bo.webelements.ExtendedFieldDecorator;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VNextBOInspectionAdvancedSearchForm extends VNextBOBaseWebPage {
	
	@FindBy(xpath = "//form[@id='advSearchEstimation-form']")
	private WebElement advancedSearchForm;

	@FindBy(id = "advSearchEstimation-Inspection")
	private WebElement inspectionInputField;

	@FindBy(xpath = "//form[@id='advSearchEstimation-form']//button[@type='submit']")
	private WebElement advancedSearchButton;
	
		
	public VNextBOInspectionAdvancedSearchForm(WebDriver driver) {
		super(driver);
		PageFactory.initElements(new ExtendedFieldDecorator(driver), this);	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOf(advancedSearchForm));
		waitABit(2000);
	}

	public boolean isAdvancedSearchFormDisplayed()
	{
		return Utils.isElementDisplayed(advancedSearchForm);
	}

	public boolean isSearchButtonDisplayed() {
		return Utils.isElementDisplayed(advancedSearchButton);
	}

	public void clickSearchButton() {Utils.clickElement(advancedSearchButton); }
	
	public void deleteSavedSearchFilter() {
		advancedSearchForm.findElement(By.xpath(".//span[contains(@data-bind, 'click: deleteSavedSearch')]")).click();
		driver.switchTo().alert().accept();
		WaitUtils.waitUntilElementInvisible(By.xpath("//form[@id='advSearchEstimation-form']"));
	}
	
	public String getAdvancedSearchInspectionNumberValue() {
		return advancedSearchForm.findElement(By.id("advSearchEstimation-Inspection")).getAttribute("value");
	}
	
	public String getAdvancedSearchVINValue() {
		return advancedSearchForm.findElement(By.id("advSearchEstimation-Vin")).getAttribute("value");
	}
	
	public void setAdvancedSearchFilterNameAndSave(String filterName) {
		WaitUtils.waitUntilElementIsClickable(advancedSearchForm.findElement(By.id("advSearchEstimation-Vin")));
		Utils.clickElement(advancedSearchForm.findElement(By.id("advSearchEstimation-Vin")));
		setAdvancedSearchFilterName(filterName);
		saveAdvancedSearchFilter();
	}
	
	public void setAdvancedSearchFilterName(String filterName) {
		Utils.clearAndType(advancedSearchForm.findElement(By.id("advSearchEstimation-SearchName")), filterName);
	}
	
	public String getAdvancedSearchFilterName() {
		WaitUtils.isElementPresent(advancedSearchForm.findElement(By.id("advSearchEstimation-SearchName")));
		return advancedSearchForm.findElement(By.id("advSearchEstimation-SearchName")).getAttribute("value");
	}
	
	public void saveAdvancedSearchFilter() {
		clickSaveButton();
		waitShort.until(ExpectedConditions.invisibilityOf(advancedSearchForm));
	}
	
	public void clickSaveButton() {
		Utils.clickElement(advancedSearchForm.findElement(By.xpath("//span[text()='Save']")));
	}
	
	public void clickClearButton() {
		Utils.clickElement(advancedSearchForm.findElement(By.xpath("//span[text()='Clear']")));
	}

	public List<String> getAllAdvancedSearchFieldsLabels()
	{
		List<String> fieldsLabels = new ArrayList<>();
		waitABit(1000);
		List<WebElement> fieldsLabelsWebElements = advancedSearchForm.findElements(
				By.xpath("//div[not(@style='display: none;')]/label[contains(@for, 'advSearchEstimation')]"));
		for (WebElement label:fieldsLabelsWebElements)
		{
			fieldsLabels.add(label.getText());
		}
		return fieldsLabels;
	}

	public void setAdvSearchTextField(String fldName, String value)
	{
		Utils.clearAndType(
				driver.findElement(By.xpath("//form[@id='advSearchEstimation-form']//label[text()='" +
						fldName + "']/following-sibling::div//input")), value);
	}

	public void setAdvSearchDropDownField(String fldName, String value)
	{
		WebElement fieldWithDropdown = advancedSearchForm.findElement(
				By.xpath("//label[text()='" + fldName + "']/following-sibling::div//span[contains(@class,'k-dropdown-wrap')]"));
		Utils.clickElement(fieldWithDropdown);
		WebElement dropDownOption =
				advancedSearchForm.findElement(By.xpath("//li[text()='" + value + "']"));
		Utils.clickWithJS(dropDownOption);
	}

	public void setAdvSearchAutocompleteField(String fldName, String value)
	{
		WebElement fieldWithAutocomplete = advancedSearchForm.findElement(
				By.xpath("//label[text()='" + fldName + "']/following-sibling::div//input[contains(@id,'advSearchEstimation')]"));
		Utils.clearAndType(fieldWithAutocomplete, value);
		WebElement foundOption =
				advancedSearchForm.findElement(By.xpath("//li[text()='" + value + "']"));
		Utils.clickWithJS(foundOption);
	}
}
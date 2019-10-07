package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnext.utils.WaitUtils;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionAdvancedSearchForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class VNextBOInspectionsAdvancedSearchSteps {

    private VNextBOInspectionAdvancedSearchForm advancedSearchForm;
    private WebDriver webDriver;

    public VNextBOInspectionsAdvancedSearchSteps(WebDriver driver) {
        webDriver = driver;
        advancedSearchForm = new VNextBOInspectionAdvancedSearchForm(driver);
    }

    public void clickSearchButton() {Utils.clickElement(advancedSearchForm.searchButton); }

    public void clickCloseButton() { Utils.clickElement(advancedSearchForm.closeButton); }

    public void clickSaveButton() { Utils.clickElement(advancedSearchForm.saveButton); }

    public void clickClearButton() { Utils.clickElement(advancedSearchForm.clearButton); }

    public void clickDeleteButton() { Utils.clickElement(advancedSearchForm.deleteSavedSearchButton); }

    public String getErrorMessage() { return Utils.getText(advancedSearchForm.errorMessage); }

    public void deleteSavedSearchFilter() {
        advancedSearchForm.deleteSavedSearchButton.click();
        webDriver.switchTo().alert().accept();
        WaitUtilsWebDriver.waitForInvisibility(advancedSearchForm.advancedSearchFormContent);
    }

    public String getValueFromTextInputField(String fieldLabel)
    {
        return advancedSearchForm.textFieldByName(fieldLabel).getAttribute("value");
    }

    public String getValueFromDropdownField(String fieldLabel)
    {
        return advancedSearchForm.dropDownFieldByName(fieldLabel).getText();
    }

    public void setAdvancedSearchFilterNameAndSave(String filterName) {
        setAdvSearchTextField("Search Name", filterName);
        saveAdvancedSearchFilter();
    }

    public void saveAdvancedSearchFilter() {
        clickSaveButton();
        WaitUtilsWebDriver.waitForInvisibility(advancedSearchForm.advancedSearchFormContent);
    }

    public List<String> getAllAdvancedSearchFieldsLabels()
    {
        List<String> fieldsLabels = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(advancedSearchForm.searchFieldsTitlesList);
        for (WebElement label: advancedSearchForm.searchFieldsTitlesList)
        {
            fieldsLabels.add(label.getText());
        }
        return fieldsLabels;
    }

    public List<String> getAllOptionsFromDropdownByName(String dropdownFieldLabel)
    {
        Utils.clickElement(advancedSearchForm.dropDownFieldByName(dropdownFieldLabel));
        List<String> statusOptionsNames = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(advancedSearchForm.dropDownFieldOptionsList(dropdownFieldLabel.toLowerCase()));
        for (WebElement optionWebElement: advancedSearchForm.dropDownFieldOptionsList(dropdownFieldLabel.toLowerCase()))
        {
            statusOptionsNames.add(optionWebElement.getText());
        }
        return statusOptionsNames;
    }

    public void setAdvSearchTextField(String fldName, String value)
    {
        Utils.clearAndType(advancedSearchForm.textFieldByName(fldName), value);
    }

    public void setAdvSearchDropDownField(String fieldLabel, String value)
    {
        WebElement fieldWithDropdown = advancedSearchForm.dropDownFieldByName(fieldLabel);
        Utils.clickElement(fieldWithDropdown);
        WebElement dropDownOption = advancedSearchForm.dropDownFieldOption(value);
        Utils.clickWithJS(dropDownOption);
    }

    public void setAdvSearchAutocompleteField(String fieldLabel, String value)
    {
        WebElement fieldWithAutocomplete = advancedSearchForm.autoPopulatedFieldByName(fieldLabel);
        Utils.clearAndType(fieldWithAutocomplete, value);
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickWithJS(advancedSearchForm.dropDownFieldOption(value));
    }

    public void setAllAdvancedSearchFields(List<String> listWithValuesForFields)
    {
        setAdvSearchAutocompleteField("Customer", listWithValuesForFields.get(0));
        setAdvSearchTextField("PO#", listWithValuesForFields.get(1));
        setAdvSearchTextField("RO#", listWithValuesForFields.get(2));
        setAdvSearchTextField("Stock#", listWithValuesForFields.get(3));
        setAdvSearchTextField("VIN", listWithValuesForFields.get(4));
        setAdvSearchDropDownField("Status", listWithValuesForFields.get(5));
        setAdvSearchTextField("Inspection#", listWithValuesForFields.get(6));
        setAdvSearchDropDownField("Timeframe", listWithValuesForFields.get(7));
        setAdvSearchTextField("Search Name", listWithValuesForFields.get(8));
    }
}
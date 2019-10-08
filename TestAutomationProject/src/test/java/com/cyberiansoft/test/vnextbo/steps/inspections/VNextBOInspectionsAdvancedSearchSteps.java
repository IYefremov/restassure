package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionAdvancedSearchForm;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VNextBOInspectionsAdvancedSearchSteps {

    public static void clickSearchButton()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.searchButton);
    }

    public static void clickCloseButton()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.closeButton);
    }

    public static void clickSaveButton()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.saveButton);
    }

    public static void clickClearButton()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.clearButton);
    }

    public static void clickDeleteButton()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.deleteSavedSearchButton);
    }

    public static void deleteSavedSearchFilter()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        advancedSearchForm.deleteSavedSearchButton.click();
        DriverBuilder.getInstance().getDriver().switchTo().alert().accept();
        WaitUtilsWebDriver.waitForInvisibility(advancedSearchForm.advancedSearchFormContent);
    }

    public static String getValueFromTextInputField(String fieldLabel)
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        return advancedSearchForm.textFieldByName(fieldLabel).getAttribute("value");
    }

    public static String getValueFromDropdownField(String fieldLabel)
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        return advancedSearchForm.dropDownFieldByName(fieldLabel).getText();
    }

    public static void setAdvancedSearchFilterNameAndSave(String filterName)
    {
        setAdvSearchTextField("Search Name", filterName);
        saveAdvancedSearchFilter();
    }

    public static void saveAdvancedSearchFilter()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        clickSaveButton();
        WaitUtilsWebDriver.waitForInvisibility(advancedSearchForm.advancedSearchFormContent);
    }

    public static List<String> getAllAdvancedSearchFieldsLabels()
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        List<String> fieldsLabels = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(advancedSearchForm.searchFieldsTitlesList);
        for (WebElement label: advancedSearchForm.searchFieldsTitlesList)
        {
            fieldsLabels.add(label.getText());
        }
        return fieldsLabels;
    }

    public static List<String> getAllOptionsFromDropdownByName(String dropdownFieldLabel)
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clickElement(advancedSearchForm.dropDownFieldByName(dropdownFieldLabel));
        List<String> statusOptionsNames = new ArrayList<>();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(advancedSearchForm.dropDownFieldOptionsList(dropdownFieldLabel.toLowerCase()));
        for (WebElement optionWebElement: advancedSearchForm.dropDownFieldOptionsList(dropdownFieldLabel.toLowerCase()))
        {
            statusOptionsNames.add(optionWebElement.getText());
        }
        return statusOptionsNames;
    }

    public static void setAdvSearchTextField(String fldName, String value)
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        Utils.clearAndType(advancedSearchForm.textFieldByName(fldName), value);
    }

    public static void setAdvSearchDropDownField(String fieldLabel, String value)
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        WebElement fieldWithDropdown = advancedSearchForm.dropDownFieldByName(fieldLabel);
        Utils.clickElement(fieldWithDropdown);
        WebElement dropDownOption = advancedSearchForm.dropDownFieldOption(value);
        Utils.clickWithJS(dropDownOption);
    }

    public static void setAdvSearchAutocompleteField(String fieldLabel, String value)
    {
        VNextBOInspectionAdvancedSearchForm advancedSearchForm =
                new VNextBOInspectionAdvancedSearchForm(DriverBuilder.getInstance().getDriver());
        WebElement fieldWithAutocomplete = advancedSearchForm.autoPopulatedFieldByName(fieldLabel);
        Utils.clearAndType(fieldWithAutocomplete, value);
        WaitUtilsWebDriver.waitForLoading();
        Utils.clickWithJS(advancedSearchForm.dropDownFieldOption(value));
    }

    public static void setAllAdvancedSearchFields(Map<String, String> listWithValuesForFields)
    {
        setAdvSearchAutocompleteField("Customer", listWithValuesForFields.get("Customer"));
        setAdvSearchTextField("PO#", listWithValuesForFields.get("PO#"));
        setAdvSearchTextField("RO#", listWithValuesForFields.get("RO#"));
        setAdvSearchTextField("Stock#", listWithValuesForFields.get("Stock#"));
        setAdvSearchTextField("VIN", listWithValuesForFields.get("VIN"));
        setAdvSearchDropDownField("Status", listWithValuesForFields.get("Status"));
        setAdvSearchTextField("Inspection#", listWithValuesForFields.get("Inspection#"));
        setAdvSearchDropDownField("Timeframe", listWithValuesForFields.get("Timeframe"));
        setAdvSearchTextField("Search Name", listWithValuesForFields.get("Search Name"));
    }
}
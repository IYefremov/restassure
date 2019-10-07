package com.cyberiansoft.test.vnextbo.verifications.Inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Map;

public class VNextBOInspectionsAdvancedSearchVerifications {

    private VNextBOInspectionAdvancedSearchForm advancedSearchForm;
    private VNextBOInspectionsAdvancedSearchSteps advancedSearchFormSteps;

    public VNextBOInspectionsAdvancedSearchVerifications(WebDriver driver) {
        advancedSearchForm = new VNextBOInspectionAdvancedSearchForm(driver);
        advancedSearchFormSteps = new VNextBOInspectionsAdvancedSearchSteps(driver);
    }

    public void isAdvancedSearchFormDisplayed() {
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been displayed\"");
    }

    public void isAdvancedSearchFormNotDisplayed() {
        Assert.assertTrue(Utils.isElementNotDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been closed");
    }

    public void isSearchButtonDisplayed() {
        Assert.assertTrue(Utils.isElementDisplayed(advancedSearchForm.searchButton),
                "Advanced Search button hasn't been displayed");
    }

    public void verifyAllAdvancedSearchFormFields(Map<String, String> listWithValuesForFields)
    {
        Assert.assertEquals(advancedSearchFormSteps.getValueFromTextInputField("Customer"),
                listWithValuesForFields.get("Customer"),  "Entered value to the \"Customer\" field hasn't been displayed");
        Assert.assertEquals(advancedSearchFormSteps.getValueFromTextInputField("PO#"),
                listWithValuesForFields.get("PO#"),  "Entered value to the \"PO#\" field hasn't been displayed");
        Assert.assertEquals(advancedSearchFormSteps.getValueFromTextInputField("RO#"),
                listWithValuesForFields.get("RO#"), "Entered value to the \"RO#\" field hasn't been displayed");
        Assert.assertEquals(advancedSearchFormSteps.getValueFromTextInputField("Stock#"),
                listWithValuesForFields.get("Stock#"), "Entered value to the \"Stock#\" field hasn't been displayed");
        Assert.assertEquals(advancedSearchFormSteps.getValueFromTextInputField("VIN"),
                listWithValuesForFields.get("VIN"), "Entered value to the \"VIN\" field hasn't been displayed");
        Assert.assertEquals(advancedSearchFormSteps.getValueFromDropdownField("Status"),
                listWithValuesForFields.get("Status"), "\"Status\" Dropdown field hasn't been correct");
        Assert.assertEquals(advancedSearchFormSteps.getValueFromTextInputField("Inspection#"),
                listWithValuesForFields.get("Inspection#"), "Entered value to the \"Inspection#\" field hasn't been displayed");
        Assert.assertEquals(advancedSearchFormSteps.getValueFromDropdownField("Timeframe"),
                listWithValuesForFields.get("Timeframe"), "\"Timeframe\" Dropdown field hasn't been correct");
    }
}
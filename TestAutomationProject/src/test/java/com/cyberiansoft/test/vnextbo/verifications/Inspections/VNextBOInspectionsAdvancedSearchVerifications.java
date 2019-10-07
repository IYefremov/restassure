package com.cyberiansoft.test.vnextbo.verifications.Inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class VNextBOInspectionsAdvancedSearchVerifications {

    private WebDriver webDriver;
    private VNextBOInspectionAdvancedSearchForm advancedSearchForm;
    private VNextBOInspectionsAdvancedSearchSteps advancedSearchFormSteps;

    public VNextBOInspectionsAdvancedSearchVerifications(WebDriver driver) {
        webDriver = driver;
        advancedSearchForm = new VNextBOInspectionAdvancedSearchForm(webDriver);
        advancedSearchFormSteps = new VNextBOInspectionsAdvancedSearchSteps(webDriver);
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

    public void verifyErrorMessageText(String expectedErrorMessage) {
        Assert.assertEquals(advancedSearchFormSteps.getErrorMessage(), expectedErrorMessage,
                "Error message hasn't been correct");
    }

    public void verifyAllAdvancedSearchFormFields(List<String> listWithValuesForFields)
    {
        VNextBOInspectionsAdvancedSearchSteps vNextBOInspectionsAdvancedSearchSteps =
                new VNextBOInspectionsAdvancedSearchSteps(webDriver);
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Customer"),
                listWithValuesForFields.get(0),  "Entered value to the \"Customer\" field hasn't been displayed");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("PO#"),
                listWithValuesForFields.get(1),  "Entered value to the \"PO#\" field hasn't been displayed");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("RO#"),
                listWithValuesForFields.get(2), "Entered value to the \"RO#\" field hasn't been displayed");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Stock#"),
                listWithValuesForFields.get(3), "Entered value to the \"Stock#\" field hasn't been displayed");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("VIN"),
                listWithValuesForFields.get(4), "Entered value to the \"VIN\" field hasn't been displayed");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromDropdownField("Status"),
                listWithValuesForFields.get(5), "\"Status\" Dropdown field hasn't been correct");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Inspection#"),
                listWithValuesForFields.get(6), "Entered value to the \"Inspection#\" field hasn't been displayed");
        Assert.assertEquals(vNextBOInspectionsAdvancedSearchSteps.getValueFromDropdownField("Timeframe"),
                listWithValuesForFields.get(7), "\"Timeframe\" Dropdown field hasn't been correct");
    }
}
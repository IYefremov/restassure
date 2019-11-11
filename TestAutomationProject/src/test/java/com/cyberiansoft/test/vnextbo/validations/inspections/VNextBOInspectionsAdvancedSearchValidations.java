package com.cyberiansoft.test.vnextbo.validations.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionAdvancedSearchForm;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsAdvancedSearchSteps;
import org.testng.Assert;

import java.util.Map;

public class VNextBOInspectionsAdvancedSearchValidations {

    public static void verifyAdvancedSearchFormIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionAdvancedSearchForm().advancedSearchFormContent),
                "Advanced search form hasn't been displayed\"");
    }

    public static void verifyAdvancedSearchFormIsNotDisplayed(VNextBOInspectionAdvancedSearchForm advancedSearchForm) {

        Assert.assertTrue(Utils.isElementNotDisplayed(advancedSearchForm.advancedSearchFormContent),
                "Advanced search form hasn't been closed");
    }

    public static void verifySearchButtonIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionAdvancedSearchForm().searchButton),
                "Advanced Search button hasn't been displayed");
    }

    public static void verifyAllAdvancedSearchFormFields(Map<String, String> listWithValuesForFields) {

        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Customer"),
                listWithValuesForFields.get("Customer"),  "Entered value to the \"Customer\" field hasn't been displayed");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("PO#"),
                listWithValuesForFields.get("PO#"),  "Entered value to the \"PO#\" field hasn't been displayed");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("RO#"),
                listWithValuesForFields.get("RO#"), "Entered value to the \"RO#\" field hasn't been displayed");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Stock#"),
                listWithValuesForFields.get("Stock#"), "Entered value to the \"Stock#\" field hasn't been displayed");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("VIN"),
                listWithValuesForFields.get("VIN"), "Entered value to the \"VIN\" field hasn't been displayed");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromDropdownField("Status"),
                listWithValuesForFields.get("Status"), "\"Status\" Dropdown field hasn't been correct");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromTextInputField("Inspection#"),
                listWithValuesForFields.get("Inspection#"), "Entered value to the \"Inspection#\" field hasn't been displayed");
        Assert.assertEquals(VNextBOInspectionsAdvancedSearchSteps.getValueFromDropdownField("Timeframe"),
                listWithValuesForFields.get("Timeframe"), "\"Timeframe\" Dropdown field hasn't been correct");
    }
}
package com.cyberiansoft.test.vnextbo.verifications.Inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOInspectionsPageVerifications {

    private VNextBOInspectionsWebPage inspectionsPage;

    public VNextBOInspectionsPageVerifications(WebDriver driver) {
        inspectionsPage = new VNextBOInspectionsWebPage(driver);
    }

    public void isClearFilterIconDisplayed() {
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.clearFilterBtn),
                "Clear filter button hasn't been displayed");
    }

    public void isClearFilterIconNotDisplayed() {
        Assert.assertTrue(Utils.isElementNotDisplayed(inspectionsPage.clearFilterBtn),
                "Clear filter button has been displayed");
    }

    public boolean isSearchOptionTextNotDisplayed() {
        return Utils.isElementNotDisplayed(inspectionsPage.filterInfoText);
    }

    public void isEditAdvancedSearchIconDisplayed() {
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.editAdvancedSearchIcon),
                "Edit advanced search pencil icon hasn't been displayed");
    }

    public boolean isSavedAdvancedSearchFilterExists(String filterName) {
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(inspectionsPage.savedSearchesList);
        for (WebElement searchName: inspectionsPage.savedSearchesList
        ) {
            if (Utils.getText(searchName).equals(filterName))
                return true;
        }
        return false;
    }

    public void verifySearchFieldContainsText(String value) {
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(inspectionsPage.searchFld, value);
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSearchFieldValue(), value,
                "Search field hasn't contained " + value);
    }

    public void isCustomerNameCorrect(String expectedCustomerName) {
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionCustomerName(),
                expectedCustomerName, "Customer name hasn't been correct");
    }

    public void isHowToCreateInspectionLinkTextCorrect(String actualResult) {
        Assert.assertEquals(actualResult,  "Click here to learn how to create your first inspection",
                "\"Click here to learn how to create your first inspection\" link hasn't been displayed");
    }

    public boolean isHowToCreateInspectionLinkDisplayed() {
        return Utils.isElementDisplayed(inspectionsPage.howToCreateInspectionLink);
    }
}
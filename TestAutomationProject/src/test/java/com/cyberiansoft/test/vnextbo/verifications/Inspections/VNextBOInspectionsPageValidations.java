package com.cyberiansoft.test.vnextbo.verifications.Inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOInspectionsPageValidations {

    public static void isClearFilterIconDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.clearFilterBtn),
                "Clear filter button hasn't been displayed");
    }

    public static void isClearFilterIconNotDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementNotDisplayed(inspectionsPage.clearFilterBtn),
                "Clear filter button has been displayed");
    }

    public static boolean isSearchOptionTextNotDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.isElementNotDisplayed(inspectionsPage.filterInfoText);
    }

    public static void isEditAdvancedSearchIconDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.editAdvancedSearchIcon),
                "Edit advanced search pencil icon hasn't been displayed");
    }

    public static boolean isSavedAdvancedSearchFilterExists(String filterName)
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(inspectionsPage.savedSearchesList);
        for (WebElement searchName: inspectionsPage.savedSearchesList
        ) {
            if (Utils.getText(searchName).equals(filterName))
                return true;
        }
        return false;
    }

    public static void verifySearchFieldContainsText(String value)
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(inspectionsPage.searchFld, value);
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSearchFieldValue(), value,
                "Search field hasn't contained " + value);
    }

    public static void isCustomerNameCorrect(String expectedCustomerName)
    {
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionCustomerName(),
                expectedCustomerName, "Customer name hasn't been correct");
    }

    public static void isHowToCreateInspectionLinkTextCorrect(String actualResult)
    {
        Assert.assertEquals(actualResult,  "Click here to learn how to create your first inspection",
                "\"Click here to learn how to create your first inspection\" link hasn't been displayed");
    }

    public static boolean isHowToCreateInspectionLinkDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.isElementDisplayed(inspectionsPage.howToCreateInspectionLink);
    }
}
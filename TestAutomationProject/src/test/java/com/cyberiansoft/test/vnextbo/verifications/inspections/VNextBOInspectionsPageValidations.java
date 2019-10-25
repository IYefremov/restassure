package com.cyberiansoft.test.vnextbo.verifications.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOInspectionsPageValidations extends VNextBOBaseWebPageValidations {

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

    public static void isInspectionStatusCorrect(String inspectionNumber, String expectedStatus)
    {
        Assert.assertEquals(VNextBOInspectionsPageSteps.getInspectionStatus(inspectionNumber),
                expectedStatus, "Inspection status hasn't been changed to " + expectedStatus);
    }

    public static void isArchiveIconDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.archiveIcon), "Archive icon hasn't been displayed.");
    }

    public static void isUnArchiveIconDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.unArchiveIcon), "Unarchive icon hasn't been displayed.");
    }

    public static void isInspectionImageZoomIconDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.inspectionImageZoomIcon),
                "Inspection's image hasn't had Zoom icon");
    }

    public static void isInspectionNotesIconDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.inspectionNotesIcon),
                "Notes icon hasn't been displayed");
    }

    public static void isPrintSupplementButtonDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.printSupplementIcon),
                "Print supplement button hasn't been displayed");
    }

    public static void isPrintInspectionButtonDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.printInspectionIcon),
                "Print inspection button hasn't been displayed");
    }

    public static void isPrintWindowOpened()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        String parentHandle = Utils.getParentTab();
        inspectionsPage.waitForNewTab();
        String newWindow = Utils.getNewTab(parentHandle);
        DriverBuilder.getInstance().getDriver().switchTo().window(parentHandle);
        boolean isPrintWindowOpened = false;
        if (!parentHandle.equals(newWindow)) isPrintWindowOpened = true;
        Assert.assertTrue(isPrintWindowOpened, "Print supplement window hasn't been opened");
    }

    public static void isInspectionsListDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.inspectionsList), "Inspection list hasn't been displayed");
    }

    public static void isSearchFieldDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.searchFld), "Search field hasn't been displayed");
    }

    public static void isInspectionDetailsPanelDisplayed()
    {
        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(inspectionsPage.inspectionDetailsPanel),
                "Inspection details panel hasn't been displayed");
    }
}
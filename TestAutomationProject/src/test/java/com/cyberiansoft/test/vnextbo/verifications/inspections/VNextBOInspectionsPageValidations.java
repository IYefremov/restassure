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

    public static void isClearFilterIconDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().clearFilterBtn),
                "Clear filter button hasn't been displayed");
    }

    public static void isClearFilterIconNotDisplayed() {

        Assert.assertTrue(Utils.isElementNotDisplayed(new VNextBOInspectionsWebPage().clearFilterBtn),
                "Clear filter button has been displayed");
    }

    public static boolean isSearchOptionTextNotDisplayed() {

        return Utils.isElementNotDisplayed(new VNextBOInspectionsWebPage().filterInfoText);
    }

    public static void isEditAdvancedSearchIconDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().editAdvancedSearchIcon),
                "Edit advanced search pencil icon hasn't been displayed");
    }

    public static boolean isSavedAdvancedSearchFilterExists(String filterName) {

        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage();
        WaitUtilsWebDriver.waitForVisibilityOfAllOptions(inspectionsPage.savedSearchesList);
        for (WebElement searchName: inspectionsPage.savedSearchesList
                ) {
            if (Utils.getText(searchName).equals(filterName))
                return true;
        }
        return false;
    }

    public static void verifySearchFieldContainsText(String value) {

        VNextBOInspectionsWebPage inspectionsPage =
                new VNextBOInspectionsWebPage();
        WaitUtilsWebDriver.waitForInputFieldValueIgnoringException(inspectionsPage.searchFld, value);
        Assert.assertEquals(VNextBOInspectionsPageSteps.getSearchFieldValue(), value,
                "Search field hasn't contained " + value);
    }

    public static void isCustomerNameCorrect(String expectedCustomerName) {

        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionCustomerName(),
                expectedCustomerName, "Customer name hasn't been correct");
    }

    public static void isHowToCreateInspectionLinkTextCorrect(String actualResult) {

        Assert.assertEquals(actualResult,  "Click here to learn how to create your first inspection",
                "\"Click here to learn how to create your first inspection\" link hasn't been displayed");
    }

    public static boolean isHowToCreateInspectionLinkDisplayed() {

        return Utils.isElementDisplayed(new VNextBOInspectionsWebPage().howToCreateInspectionLink);
    }

    public static void isInspectionStatusCorrect(String inspectionNumber, String expectedStatus) {

        Assert.assertEquals(VNextBOInspectionsPageSteps.getInspectionStatus(inspectionNumber),
                expectedStatus, "Inspection status hasn't been changed to " + expectedStatus);
    }

    public static void isArchiveIconDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().archiveIcon),
                "Archive icon hasn't been displayed.");
    }

    public static void isUnArchiveIconDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().unArchiveIcon),
                "Unarchive icon hasn't been displayed.");
    }

    public static void isInspectionImageZoomIconDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionImageZoomIcon),
                "Inspection's image hasn't had Zoom icon");
    }

    public static void isInspectionNotesIconDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionNotesIcon),
                "Notes icon hasn't been displayed");
    }

    public static void isPrintSupplementButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().printSupplementIcon),
                "Print supplement button hasn't been displayed");
    }

    public static void isPrintInspectionButtonDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().printInspectionIcon),
                "Print inspection button hasn't been displayed");
    }

    public static void isPrintWindowOpened() {

        String parentHandle = Utils.getParentTab();
        new VNextBOInspectionsWebPage().waitForNewTab();
        String newWindow = Utils.getNewTab(parentHandle);
        DriverBuilder.getInstance().getDriver().switchTo().window(parentHandle);
        boolean isPrintWindowOpened = false;
        if (!parentHandle.equals(newWindow)) isPrintWindowOpened = true;
        Assert.assertTrue(isPrintWindowOpened, "Print supplement window hasn't been opened");
    }

    public static void isInspectionsListDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionsList),
                "Inspection list hasn't been displayed");
    }

    public static void isSearchFieldDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().searchFld),
                "Search field hasn't been displayed");
    }

    public static void isInspectionDetailsPanelDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionDetailsPanel),
                "Inspection details panel hasn't been displayed");
    }
}
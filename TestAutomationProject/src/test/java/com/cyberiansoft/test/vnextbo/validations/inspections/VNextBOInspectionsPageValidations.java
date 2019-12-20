package com.cyberiansoft.test.vnextbo.validations.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOBaseWebPage;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsWebPage;
import com.cyberiansoft.test.vnextbo.steps.inspections.VNextBOInspectionsPageSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class VNextBOInspectionsPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyClearFilterIconIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().clearFilterBtn),
                "Clear filter button hasn't been displayed");
    }

    public static void verifyClearFilterIconIsNotDisplayed() {

        Assert.assertFalse(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().clearFilterBtn),
                "Clear filter button has been displayed");
    }

    public static void verifyEditAdvancedSearchIconIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(3).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().editAdvancedSearchIcon));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().editAdvancedSearchIcon),
                "Edit advanced search pencil icon hasn't been displayed");
    }

    public static boolean verifySavedAdvancedSearchFilterExists(String filterName) {

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

    public static void verifyCustomerNameIsCorrect(String expectedCustomerName) {

        Assert.assertEquals(VNextBOInspectionsPageSteps.getSelectedInspectionCustomerName(),
                expectedCustomerName, "Customer name hasn't been correct");
    }

    public static void verifyHowToCreateInspectionLinkTextIsCorrect(String actualResult) {

        Assert.assertEquals(actualResult,  "Click here to learn how to create your first inspection",
                "\"Click here to learn how to create your first inspection\" link hasn't been displayed");
    }

    public static boolean verifyHowToCreateInspectionLinkIsDisplayed() {
        try {
            return Utils.isElementDisplayed(new VNextBOInspectionsWebPage().howToCreateInspectionLink);
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public static void verifyInspectionStatusIsCorrect(String inspectionNumber, String expectedStatus) {

        Assert.assertEquals(VNextBOInspectionsPageSteps.getInspectionStatus(inspectionNumber),
                expectedStatus, "Inspection status hasn't been changed to " + expectedStatus);
    }

    public static void verifyArchiveIconIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(4).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().archiveIcon));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().archiveIcon),
                "Archive icon hasn't been displayed.");
    }

    public static void verifyUnArchiveIconIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(4).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().unArchiveIcon));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().unArchiveIcon),
                "Unarchive icon hasn't been displayed.");
    }

    public static void verifyInspectionImageZoomIconIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(4).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().inspectionImageZoomIcon));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionImageZoomIcon),
                "Inspection's image hasn't had Zoom icon");
    }

    public static void verifyInspectionNotesIconIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(4).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().inspectionNotesIcon));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionNotesIcon),
                "Notes icon hasn't been displayed");
    }

    public static void verifyPrintSupplementButtonIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(4).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().printSupplementIcon));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().printSupplementIcon),
                "Print supplement button hasn't been displayed");
    }

    public static void verifyPrintInspectionButtonIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(4).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().printInspectionIcon));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().printInspectionIcon),
                "Print inspection button hasn't been displayed");
    }

    public static void verifyPrintWindowIsOpened() {

        String parentHandle = Utils.getParentTab();
        WaitUtilsWebDriver.waitForNewTab();
        String newWindow = Utils.getNewTab(parentHandle);
        DriverBuilder.getInstance().getDriver().switchTo().window(parentHandle);
        boolean isPrintWindowOpened = false;
        if (!parentHandle.equals(newWindow)) isPrintWindowOpened = true;
        Assert.assertTrue(isPrintWindowOpened, "Print supplement window hasn't been opened");
    }

    public static void verifyInspectionsListIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionsList),
                "Inspection list hasn't been displayed");
    }

    public static void verifySearchFieldIsDisplayed() {

        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().searchFld),
                "Search field hasn't been displayed");
    }

    public static void verifyInspectionDetailsPanelIsDisplayed() {
        WaitUtilsWebDriver.getWebDriverWait(5).until(ExpectedConditions.visibilityOf(new VNextBOInspectionsWebPage().inspectionDetailsPanel));
        Assert.assertTrue(Utils.isElementDisplayed(new VNextBOInspectionsWebPage().inspectionDetailsPanel),
                "Inspection details panel hasn't been displayed");
    }
}
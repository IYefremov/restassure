package com.cyberiansoft.test.vnextbo.verifications.repairOrders;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class VNextBOROPageVerifications {

    public static void verifyTechnicianIsDisplayed(String woNumber, String technician) {
        Assert.assertEquals(new VNextBOROWebPage().getTechniciansValueForWO(woNumber), technician,
                "The technician hasn't been changed");
    }

    public static void verifyAdvancedSearchDialogIsDisplayed() {
        Assert.assertTrue(isAdvancedSearchDialogDisplayed(), "The advanced search dialog is not opened");
    }

    public static void verifyAdvancedSearchDialogIsClosed() {
        Assert.assertTrue(isAdvancedSearchDialogClosed(), "The advanced search dialog is not closed");
    }

    private static boolean isAdvancedSearchDialogDisplayed() {
        return Utils.isElementWithAttributeContainingValueDisplayed(
                new VNextBOROAdvancedSearchDialog().getAdvancedSearchDialogContainer(), "style", "display: block", 5);
    }

    private static boolean isAdvancedSearchDialogClosed() {
        return Utils.isElementWithAttributeContainingValueDisplayed(
                new VNextBOROAdvancedSearchDialog().getAdvancedSearchDialogContainer(), "style", "display: none", 5);
    }

    private static boolean isArrowDisplayed(String wo, String arrow) {
        try {
            WaitUtilsWebDriver
                    .getShortWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='" +
                            wo + "']/../../.." + arrow)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNoteForWorkOrderDisplayed(String woNumber) {
        return isArrowDisplayed(woNumber, "/../../..//div[@class='dark box']");
    }

    public static void verifyNoteTextIsDisplayed(String noteMessage) {
        Assert.assertEquals(new VNextBOROWebPage().getOrderNoteText(), noteMessage,
                "The order note text hasn't been displayed");
    }

    public static boolean isSavedSearchEditIconDisplayed() {
        return Utils.isElementDisplayed(new VNextBOROWebPage().getSavedSearchEditIcon(), 5);
    }
}
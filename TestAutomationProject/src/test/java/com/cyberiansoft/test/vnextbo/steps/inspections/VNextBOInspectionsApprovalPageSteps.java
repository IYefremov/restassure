package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.interactions.general.VNextBOConfirmationDialogInteractions;
import com.cyberiansoft.test.vnextbo.screens.commonobjects.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsApprovalWebPage;
import org.testng.Assert;

import java.util.Arrays;

public class VNextBOInspectionsApprovalPageSteps {

    public static boolean isApprovePrintPageButtonDisplayed() {

        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOInspectionsApprovalWebPage().getApproveServiceButton(), true, 2);
    }

    public static void clickInspectionApprovePrintPageButton() {

        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage();
        if (isApprovePrintPageButtonDisplayed()) {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveServiceButton());
            WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getGeneralApproveWithNoteButton());
        } else {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveAndCompleteServiceButton());
            //WaitUtilsWebDriver.waitForLoading();
        }
    }

    private static void setPrintPageNotes(String notes) {

        Utils.clearAndType(new VNextBOInspectionsApprovalWebPage().getNotesTextArea(), notes);
    }

    public static void clickConfirmApprovePrintPageButtonIfDisplayed() {

        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage();
        if (WaitUtilsWebDriver.elementShouldBeVisible(inspectionsApprovalWebPage.getGeneralApproveWithNoteButton(), true, 4)) {
            Utils.clickElement(inspectionsApprovalWebPage.getGeneralApproveWithNoteButton());
            WaitUtilsWebDriver.waitForPageToBeLoaded();
        }
        WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getInspectionStatus());
    }

    public static String getPrintPageInspectionStatus() {

        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage();
        try {
            WaitUtilsWebDriver.getFluentWait().until((driver) -> !inspectionsApprovalWebPage
                    .getInspectionStatus()
                    .getText()
                    .isEmpty());
            return inspectionsApprovalWebPage.getInspectionStatus().getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    public static boolean isNotesTextAreaDisplayed() {

        return WaitUtilsWebDriver.elementShouldBeVisible(
                new VNextBOInspectionsApprovalWebPage().getNotesTextArea(), true, 2);
    }

    public static void setNotesIfDisplayed(String ...notes) {

        if (isNotesTextAreaDisplayed()) {
            if (notes.length > 0) {
                Arrays.asList(notes).forEach(VNextBOInspectionsApprovalPageSteps::setPrintPageNotes);
            } else {
                setPrintPageNotes("");
            }
        }
    }

    public static boolean isPrintPageInspectionStatusDisplayed() {

        return Utils.isElementDisplayed(new VNextBOInspectionsApprovalWebPage().getInspectionStatus());
    }

    public static void completeApprovingPrintPageInspection(String ...notes) {

        clickInspectionApprovePrintPageButton();
        setNotesIfDisplayed(notes);
        clickConfirmApprovePrintPageButtonIfDisplayed();
    }

    public static void approveInspection(String ...note) {

        String parent = Utils.getParentTab();
        VNextBOInspectionsPageSteps.clickTheApproveInspectionButton();
        VNextBOConfirmationDialogInteractions.clickModalDialogButton(new VNextBOConfirmationDialog().getYesButton());
        Utils.getNewTab(parent);

        completeApprovingPrintPageInspection(note);
        Assert.assertTrue(isPrintPageInspectionStatusDisplayed(), "The inspection status hasn't been displayed");
        Assert.assertEquals(getPrintPageInspectionStatus(), "Approved", "The inspection hasn't been approved");
        Utils.closeNewTab(parent);
        Utils.refreshPage();
    }
}
package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.inspections.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Arrays;

public class VNextBOInspectionsApprovalPageSteps {

    public static boolean isApprovePrintPageButtonDisplayed() {

        return Utils.isElementDisplayed(new VNextBOInspectionsApprovalWebPage().getApproveServiceButton());
    }

    public static void clickInspectionApprovePrintPageButton() {

        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage();
        if (isApprovePrintPageButtonDisplayed()) {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveServiceButton());
            WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getGeneralApproveButton());
        } else {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveAndCompleteServiceButton());
            WaitUtilsWebDriver.waitForLoading();
        }
    }

    private static VNextBOInspectionsApprovalWebPage setPrintPageNotes(String notes) {

        Utils.clearAndType(new VNextBOInspectionsApprovalWebPage().getNotesTextArea(), notes);
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public static VNextBOInspectionsApprovalWebPage clickConfirmApprovePrintPageButtonIfDisplayed() {

        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage();
        if (Utils.isElementDisplayed(inspectionsApprovalWebPage.getGeneralApproveButton())) {
            Utils.clickElement(inspectionsApprovalWebPage.getGeneralApproveButton());
            WaitUtilsWebDriver.waitForLoading();
        }
        WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getInspectionStatus());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
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

        return Utils.isElementDisplayed(new VNextBOInspectionsApprovalWebPage().getNotesTextArea());
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
        new VNextBOConfirmationDialog().clickYesButton();
        Utils.getNewTab(parent);

        completeApprovingPrintPageInspection(note);
        Assert.assertTrue(isPrintPageInspectionStatusDisplayed(), "The inspection status hasn't been displayed");
        Assert.assertEquals(getPrintPageInspectionStatus(), "Approved", "The inspection hasn't been approved");
        Utils.closeNewTab(parent);
        Utils.refreshPage();
    }
}
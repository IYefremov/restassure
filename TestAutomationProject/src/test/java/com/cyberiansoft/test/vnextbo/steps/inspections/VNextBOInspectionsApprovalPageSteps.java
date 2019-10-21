package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Arrays;

public class VNextBOInspectionsApprovalPageSteps {

    public static boolean isApprovePrintPageButtonDisplayed()
    {
        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.isElementDisplayed(inspectionsApprovalWebPage.getApproveServiceButton());
    }

    public static void clickInspectionApprovePrintPageButton()
    {
        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage(DriverBuilder.getInstance().getDriver());
        if (isApprovePrintPageButtonDisplayed()) {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveServiceButton());
            WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getGeneralApproveButton());
        } else {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveAndCompleteServiceButton());
            WaitUtilsWebDriver.waitForLoading();
        }
    }

    private static VNextBOInspectionsApprovalWebPage setPrintPageNotes(String notes)
    {
        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage(DriverBuilder.getInstance().getDriver());
        Utils.clearAndType(inspectionsApprovalWebPage.getNotesTextArea(), notes);
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public static VNextBOInspectionsApprovalWebPage clickConfirmApprovePrintPageButtonIfDisplayed()
    {
        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage(DriverBuilder.getInstance().getDriver());
        if (Utils.isElementDisplayed(inspectionsApprovalWebPage.getGeneralApproveButton())) {
            Utils.clickElement(inspectionsApprovalWebPage.getGeneralApproveButton());
            WaitUtilsWebDriver.waitForLoading();
        }
        WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getInspectionStatus());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public static String getPrintPageInspectionStatus()
    {
        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage(DriverBuilder.getInstance().getDriver());
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

    public static boolean isNotesTextAreaDisplayed()
    {
        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.isElementDisplayed(inspectionsApprovalWebPage.getNotesTextArea());
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

    public static boolean isPrintPageInspectionStatusDisplayed()
    {
        VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage =
                new VNextBOInspectionsApprovalWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.isElementDisplayed(inspectionsApprovalWebPage.getInspectionStatus());
    }

    public static void completeApprovingPrintPageInspection(String ...notes)
    {
        clickInspectionApprovePrintPageButton();
        setNotesIfDisplayed(notes);
        clickConfirmApprovePrintPageButtonIfDisplayed();
    }

    public static void approveInspection(String ...note)
    {
        VNextBOConfirmationDialog confirmationDialog = new VNextBOConfirmationDialog();
        String parent = Utils.getParentTab();
        VNextBOInspectionsPageSteps.clickTheApproveInspectionButton();
        confirmationDialog.clickYesButton();
        Utils.getNewTab(parent);

        completeApprovingPrintPageInspection(note);
        Assert.assertTrue(isPrintPageInspectionStatusDisplayed(), "The inspection status hasn't been displayed");
        Assert.assertEquals(getPrintPageInspectionStatus(), "Approved", "The inspection hasn't been approved");
        Utils.closeNewTab(parent);
        Utils.refreshPage();
    }
}
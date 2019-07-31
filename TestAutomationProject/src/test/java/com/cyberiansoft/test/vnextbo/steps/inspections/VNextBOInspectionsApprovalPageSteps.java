package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Arrays;

public class VNextBOInspectionsApprovalPageSteps {

    private VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage;
    private VNextBOConfirmationDialog confirmationDialog;

    public VNextBOInspectionsApprovalPageSteps() {
        WebDriver driver = DriverBuilder.getInstance().getDriver();
        inspectionsApprovalWebPage = PageFactory.initElements(driver, VNextBOInspectionsApprovalWebPage.class);
        confirmationDialog = PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
    }

    public boolean isApprovePrintPageButtonDisplayed() {
        return Utils.isElementDisplayed(inspectionsApprovalWebPage.getApproveButton());
    }

    public VNextBOInspectionsApprovalWebPage clickInspectionApprovePrintPageButton() {
        if (isApprovePrintPageButtonDisplayed()) {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveButton());
            WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getConfirmApproveButton());
        } else {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveAndCompleteButton());
            WaitUtilsWebDriver.waitForLoading();
        }

        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    private VNextBOInspectionsApprovalWebPage setPrintPageNotes(String notes) {
        Utils.clearAndType(inspectionsApprovalWebPage.getNotesTextArea(), notes);
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public VNextBOInspectionsApprovalWebPage clickConfirmApprovePrintPageButtonIfDisplayed() {
        if (Utils.isElementDisplayed(inspectionsApprovalWebPage.getConfirmApproveButton())) {
            Utils.clickElement(inspectionsApprovalWebPage.getConfirmApproveButton());
            WaitUtilsWebDriver.waitForLoading();
        }
        WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getInspectionStatus());
        return PageFactory.initElements(DriverBuilder.getInstance().getDriver(), VNextBOInspectionsApprovalWebPage.class);
    }

    public String getPrintPageInspectionStatus() {
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

    public boolean isNotesTextAreaDisplayed() {
        return Utils.isElementDisplayed(inspectionsApprovalWebPage.getNotesTextArea());
    }

    public void setNotesIfDisplayed(String ...notes) {
        if (isNotesTextAreaDisplayed()) {
            if (notes.length > 0) {
                Arrays.asList(notes).forEach(this::setPrintPageNotes);
            } else {
                setPrintPageNotes("");
            }
        }
    }

    public boolean isPrintPageInspectionStatusDisplayed() {
        return Utils.isElementDisplayed(inspectionsApprovalWebPage.getInspectionStatus());
    }

    public void completeApprovingPrintPageInspection(String ...notes) {
        clickInspectionApprovePrintPageButton();
        setNotesIfDisplayed(notes);
        clickConfirmApprovePrintPageButtonIfDisplayed();
    }

    public void approveInspection(String ...note) {
        String parent = Utils.getParentTab();
        new VNextBOInspectionsSteps().clickTheApproveInspectionButton();
        confirmationDialog.clickYesButton();
        Utils.getNewTab(parent);

        completeApprovingPrintPageInspection(note);
        Assert.assertTrue(isPrintPageInspectionStatusDisplayed(), "The inspection status hasn't been displayed");
        Assert.assertEquals(getPrintPageInspectionStatus(), "Approved", "The inspection hasn't been approved");
        Utils.closeNewTab(parent);
        Utils.refreshPage();
    }
}
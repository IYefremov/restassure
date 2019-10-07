package com.cyberiansoft.test.vnextbo.steps.inspections;

import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.VNextBOConfirmationDialog;
import com.cyberiansoft.test.vnextbo.screens.Inspections.VNextBOInspectionsApprovalWebPage;
import com.cyberiansoft.test.baseutils.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Arrays;

public class VNextBOInspectionsApprovalPageSteps {

    private VNextBOInspectionsApprovalWebPage inspectionsApprovalWebPage;
    private VNextBOConfirmationDialog confirmationDialog;
    private WebDriver driver;

    public VNextBOInspectionsApprovalPageSteps() {
        driver = DriverBuilder.getInstance().getDriver();
        inspectionsApprovalWebPage = PageFactory.initElements(driver, VNextBOInspectionsApprovalWebPage.class);
        confirmationDialog = PageFactory.initElements(driver, VNextBOConfirmationDialog.class);
    }

    public boolean isApprovePrintPageButtonDisplayed() {
        return Utils.isElementDisplayed(inspectionsApprovalWebPage.getApproveServiceButton());
    }

    public VNextBOInspectionsApprovalWebPage clickInspectionApprovePrintPageButton() {
        if (isApprovePrintPageButtonDisplayed()) {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveServiceButton());
            WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getGeneralApproveButton());
        } else {
            Utils.clickElement(inspectionsApprovalWebPage.getApproveAndCompleteServiceButton());
            WaitUtilsWebDriver.waitForLoading();
        }

        return PageFactory.initElements(driver, VNextBOInspectionsApprovalWebPage.class);
    }

    private VNextBOInspectionsApprovalWebPage setPrintPageNotes(String notes) {
        Utils.clearAndType(inspectionsApprovalWebPage.getNotesTextArea(), notes);
        return PageFactory.initElements(driver, VNextBOInspectionsApprovalWebPage.class);
    }

    public VNextBOInspectionsApprovalWebPage clickConfirmApprovePrintPageButtonIfDisplayed() {
        if (Utils.isElementDisplayed(inspectionsApprovalWebPage.getGeneralApproveButton())) {
            Utils.clickElement(inspectionsApprovalWebPage.getGeneralApproveButton());
            WaitUtilsWebDriver.waitForLoading();
        }
        WaitUtilsWebDriver.waitForVisibility(inspectionsApprovalWebPage.getInspectionStatus());
        return PageFactory.initElements(driver, VNextBOInspectionsApprovalWebPage.class);
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
        new VNextBOInspectionsPageSteps(driver).clickTheApproveInspectionButton();
        confirmationDialog.clickYesButton();
        Utils.getNewTab(parent);

        completeApprovingPrintPageInspection(note);
        Assert.assertTrue(isPrintPageInspectionStatusDisplayed(), "The inspection status hasn't been displayed");
        Assert.assertEquals(getPrintPageInspectionStatus(), "Approved", "The inspection hasn't been approved");
        Utils.closeNewTab(parent);
        Utils.refreshPage();
    }
}
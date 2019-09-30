package com.cyberiansoft.test.vnextbo.verifications;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROAdvancedSearchDialog;
import com.cyberiansoft.test.vnextbo.screens.repairOrders.VNextBOROWebPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class VNextBOROPageVerifications {

    private VNextBOROAdvancedSearchDialog advancedSearchDialog;
    private VNextBOROWebPage repairOrdersPage;

    public VNextBOROPageVerifications() {
        advancedSearchDialog = new VNextBOROAdvancedSearchDialog();
        repairOrdersPage = new VNextBOROWebPage();
    }

    public void verifyTechnicianIsDisplayed(String woNumber, String technician) {
        Assert.assertEquals(repairOrdersPage.getTechniciansValueForWO(woNumber), technician,
                "The technician hasn't been changed");
    }

    public void verifyAdvancedSearchDialogIsDisplayed() {
        Assert.assertTrue(isAdvancedSearchDialogDisplayed(), "The advanced search dialog is not opened");
    }

    public boolean isAdvancedSearchDialogDisplayed() {
        return Utils.isElementDisplayed(advancedSearchDialog.getAdvancedSearchDialog());
    }

    private boolean isArrowDisplayed(String wo, String arrow) {
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

    public boolean isNoteForWorkOrderDisplayed(String woNumber) {
        return isArrowDisplayed(woNumber, "/../../..//div[@class='dark box']");
    }

    public void verifyNoteTextIsDisplayed(String noteMessage) {
        Assert.assertEquals(repairOrdersPage.getOrderNoteText(), noteMessage,
                "The order note text hasn't been displayed");
    }
}
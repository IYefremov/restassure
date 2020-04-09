package com.cyberiansoft.test.bo.steps.company.workordertypes;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.bo.pageobjects.webpages.company.workordertypes.BOWorkOrderTypeDialog;
import com.cyberiansoft.test.bo.validations.workordertypes.BOWorkOrderTypeDialogValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static com.cyberiansoft.test.bo.utils.WebElementsBot.selectComboboxValue;

public class BOWorkOrderTypeDialogSteps {

    public static void waitForWOTypeDialogToBeOpened() {
        WaitUtilsWebDriver.waitForVisibility(new BOWorkOrderTypeDialog().getWoTypeHeader(), 4);
    }

    public static void checkApprovalRequired() {
        final WebElement approvalRequiredCheckbox = new BOWorkOrderTypeDialog().getApprovalRequiredCheckbox();
        WaitUtilsWebDriver.waitForVisibility(approvalRequiredCheckbox);
        if (!BOWorkOrderTypeDialogValidations.isApprovalRequiredChecked()) {
            Utils.clickElement(approvalRequiredCheckbox);
            WaitUtilsWebDriver.getWebDriverWait(2)
                    .ignoring(Exception.class)
                    .until(driver -> approvalRequiredCheckbox.isSelected());
            Assert.assertTrue(BOWorkOrderTypeDialogValidations.isApprovalRequiredChecked(),
                    "The approval required checkbox hasn't been checked");
        }
    }

    public static void uncheckApprovalRequired() {
        final WebElement approvalRequiredCheckbox = new BOWorkOrderTypeDialog().getApprovalRequiredCheckbox();
        WaitUtilsWebDriver.waitForVisibility(approvalRequiredCheckbox);
        if (BOWorkOrderTypeDialogValidations.isApprovalRequiredChecked()) {
            Utils.clickElement(approvalRequiredCheckbox);
            WaitUtilsWebDriver.getWebDriverWait(2)
                    .ignoring(Exception.class)
                    .until(driver -> !approvalRequiredCheckbox.isSelected());
            Assert.assertTrue(!BOWorkOrderTypeDialogValidations.isApprovalRequiredChecked(),
                    "The approval required checkbox has been checked");
        }
    }

    public static void setLineApproval(String option) {
        final BOWorkOrderTypeDialog workOrderTypeDialog = new BOWorkOrderTypeDialog();
        selectComboboxValue(workOrderTypeDialog.getLineApprovalCmb(), workOrderTypeDialog.getLineApprovalDd(), option);
    }

    public static void confirm() {
        final BOWorkOrderTypeDialog workOrderTypeDialog = new BOWorkOrderTypeDialog();
        Utils.clickElement(workOrderTypeDialog.getOkButton());
        WaitUtilsWebDriver.waitUntilPageIsLoadedWithJs();
        WaitUtilsWebDriver.waitForPendingRequestsToComplete();
    }

    public static void setApprovalRequiredAndLineApprovalOption(String option) {
        checkApprovalRequired();
        setLineApproval(option);
        confirm();
    }

    public static void removeApprovalRequiredOption() {
        uncheckApprovalRequired();
        confirm();
    }
}

package com.cyberiansoft.test.vnextbo.validations.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.validations.VNextBOBaseWebPageValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOUsersPageValidations extends VNextBOBaseWebPageValidations {

    public static void verifyAddNewUserBtnIsDisplayed() {

        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.addNewUserBtn),
                "\"Add New User\" button hasn't been displayed.");
    }

    public static void verifyUsersTableIsDisplayed() {

        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.usersTable.getWrappedElement()),
                "\"Add New User\" button hasn't been displayed.");
    }

    public static void verifyUsersNotFoundMessageIsDisplayed() {

        Assert.assertEquals(VNextBOUsersPageSteps.getUsersNotFoundMessage(), "No items to show",
                "\"No items to show\" message hasn't been displayed.");
    }

    public static boolean verifyRedTriangleWarningIconIsDisplayed() {

        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        return Utils.isElementDisplayed(vNexBOUsersWebPage.redTriangleWarningIcon);
    }

    public static boolean verifyRedTriangleWarningIconIsNotDisplayed(VNexBOUsersWebPage vNexBOUsersWebPage) {

        return Utils.isElementDisplayed(vNexBOUsersWebPage.redTriangleWarningIcon);
    }

    public static void verifyReSendButtonIsDisplayed() {

        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.reSendButton),
                "Re-send button hasn't been displayed.");
    }

    public static boolean verifyReSendButtonIsNotDisplayed(VNexBOUsersWebPage vNexBOUsersWebPage) {

        return Utils.isElementDisplayed(vNexBOUsersWebPage.reSendButton);
    }

    public static boolean verifyUserIsPresentOnCurrentPageByText(String searchText) {

        try {
            VNextBOUsersPageSteps.getTableRowWithText(searchText);
        } catch (NullPointerException ignored) {}
        WebElement row = VNextBOUsersPageSteps.getTableRowWithText(searchText);
        return row != null;
    }

    public static boolean verifyUserIsPresentInTableByText(String searchText) {

        boolean found = false;
        boolean nextPage = true;
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage();
        while (nextPage) {
            if (vNexBOUsersWebPage.nextPageBtn.getAttribute("disabled") != null) {
                found = verifyUserIsPresentOnCurrentPageByText(searchText);
                nextPage = false;
            } else if (!verifyUserIsPresentOnCurrentPageByText(searchText)) {
                Utils.clickWithJS(vNexBOUsersWebPage.nextPageBtn);
                WaitUtilsWebDriver.waitForSpinnerToDisappear();
            } else {
                found = true;
                break;
            }
        }
        return found;
    }
}
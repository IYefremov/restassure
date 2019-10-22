package com.cyberiansoft.test.vnextbo.verifications.users;

import com.cyberiansoft.test.baseutils.Utils;
import com.cyberiansoft.test.baseutils.WaitUtilsWebDriver;
import com.cyberiansoft.test.driverutils.DriverBuilder;
import com.cyberiansoft.test.vnextbo.screens.commonObjects.VNextBOPageSwitcherElements;
import com.cyberiansoft.test.vnextbo.screens.users.VNexBOUsersWebPage;
import com.cyberiansoft.test.vnextbo.steps.users.VNextBOUsersPageSteps;
import com.cyberiansoft.test.vnextbo.verifications.VNextBOBaseWebPageValidations;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class VNextBOUsersPageValidations extends VNextBOBaseWebPageValidations {

    public static void isAddNewUserBtnDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.addNewUserBtn),
                "\"Add New User\" button hasn't been displayed.");
    }

    public static void isUsersTableDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.usersTable.getWrappedElement()),
                "\"Add New User\" button hasn't been displayed.");
    }

    public static void isUsersNotFoundMessageDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertEquals(VNextBOUsersPageSteps.getUsersNotFoundMessage(), "No items to show",
                "\"No items to show\" message hasn't been displayed.");
    }

    public static boolean isRedTriangleWarningIconDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        return Utils.isElementDisplayed(vNexBOUsersWebPage.redTriangleWarningIcon);
    }

    public static boolean isRedTriangleWarningIconNotDisplayed(VNexBOUsersWebPage vNexBOUsersWebPage)
    {
        return Utils.isElementNotDisplayed(vNexBOUsersWebPage.redTriangleWarningIcon);
    }

    public static void isReSendButtonDisplayed()
    {
        VNexBOUsersWebPage vNexBOUsersWebPage = new VNexBOUsersWebPage(DriverBuilder.getInstance().getDriver());
        Assert.assertTrue(Utils.isElementDisplayed(vNexBOUsersWebPage.reSendButton),
                "Re-send button hasn't been displayed.");
    }

    public static boolean isReSendButtonNotDisplayed(VNexBOUsersWebPage vNexBOUsersWebPage)
    {
        return Utils.isElementNotDisplayed(vNexBOUsersWebPage.reSendButton);
    }

    public static boolean isUserPresentOnCurrentPageByText(String userMail) {
        try {
            VNextBOUsersPageSteps.getTableRowWithText(userMail);
        } catch (NullPointerException ignored) {}
        WebElement row = VNextBOUsersPageSteps.getTableRowWithText(userMail);
        return row != null;
    }

    public static boolean isUserPresentInTableByText(String userMail) {
        boolean found = false;
        boolean nextPage = true;
        VNextBOPageSwitcherElements switcherElements = new VNextBOPageSwitcherElements();
        while (nextPage) {
            if (switcherElements.getHeaderNextPageBtn().getAttribute("disabled") != null) {
                found = isUserPresentOnCurrentPageByText(userMail);
                nextPage = false;
            } else if (!isUserPresentOnCurrentPageByText(userMail)) {
                Utils.clickWithJS(switcherElements.getHeaderNextPageBtn());
                WaitUtilsWebDriver.waitForLoading();
            } else {
                found = true;
                break;
            }
        }
        return found;
    }
}